<template>
  <div class="xinnghi">
    <a-layout :style="{ background: 'white' }">
      <a-layout-content :style="{ margin: '30px 16px 0' }">
        <div
          :style="{
            minHeight: '360px',
            background: 'white',
          }"
        >
          <a-back-top :style="{ width: '5vh', height: '15vh' }" />
          <!-- menu trên -->
          <a-input
            placeholder="Tiêu đề"
            :style="{ width: '150px', 'margin-right': '5px' }"
            v-model="dataSearch.title"
          />
          Trạng thái
          <a-select
            v-model="dataSearch.status"
            @change="submitSearch"
            :style="{ width: '150px', 'margin-right': '8px' }"
            placeholder="Trạng thái"
          >
            <a-select-option value=""> Tất cả </a-select-option>
            <a-select-option value="-1"> Chờ duyệt </a-select-option>
            <a-select-option value="0"> Hủy bỏ </a-select-option>
            <a-select-option value="1"> Đã duyệt </a-select-option>
          </a-select>
          Ngày tạo
          <a-range-picker
            v-model="dataSearch.date"
            @change="submitSearch"
            :placeholder="['Ngày bắt đầu', 'Ngày kết thúc']"
            format="DD/MM/YYYY"
          />
          <a-button
            type="primary"
            @click="submitSearch"
            :style="{ 'margin-left': '5px' }"
          >
            <font-awesome-icon
              :icon="['fas', 'search']"
              :style="{ 'margin-right': '5px' }"
            />
            Tìm kiếm
          </a-button>
          <a-button
            type="primary"
            @click="showModalAdd"
            :style="{ 'margin-left': '5px' }"
          >
            <font-awesome-icon
              :icon="['fas', 'file-alt']"
              :style="{ 'margin-right': '5px' }"
            />
            Viết đơn
          </a-button>

          <!-- table content -->
          <div :style="{ 'padding-top': '10px' }">
            <a-table
              :columns="columns"
              :data-source="dataSourceTable"
              :pagination="pagination"
              :scroll="{ x: 1000 }"
              :rowKey="
                (record, index) => {
                  return index;
                }
              "
              @change="handleTableChange"
            >
              <template slot="titlee" slot-scope="text, record">
                {{ record.title }}
              </template>
              <template slot="dateCreate" slot-scope="text, record">
                {{
                  new Date(record.dateCreate).toLocaleDateString("en-GB", {
                    year: "numeric",
                    month: "2-digit",
                    day: "2-digit",
                  })
                }}
              </template>
              <template slot="dateAccept" slot-scope="text, record">
                {{ record.dateAccept }}
              </template>
              <template slot="dateStart" slot-scope="text, record">
                {{
                  new Date(record.dateStart).toLocaleDateString("en-GB", {
                    year: "numeric",
                    month: "2-digit",
                    day: "2-digit",
                  })
                }}
              </template>
              <template slot="dateEnd" slot-scope="text, record">
                {{
                  new Date(record.dateEnd).toLocaleDateString("en-GB", {
                    year: "numeric",
                    month: "2-digit",
                    day: "2-digit",
                  })
                }}
              </template>
              <template slot="status" slot-scope="text, record">
                <a-tag
                  :color="
                    record.statusAccept == '-1'
                      ? 'orange'
                      : record.statusAccept == '0'
                      ? 'red'
                      : 'green'
                  "
                >
                  {{
                    record.statusAccept == "-1"
                      ? "Chờ duyệt"
                      : record.statusAccept == "0"
                      ? "Hủy bỏ"
                      : "Đã duyệt"
                  }}
                </a-tag>
              </template>
              <template slot="action" slot-scope="text, record">
                <a-row>
                  <a-col :span="8">
                    <a-button
                      id="view"
                      @click="showModelView(record)"
                      :style="{ width: '44.25px', 'margin-right': '100px' }"
                    >
                      <font-awesome-icon :icon="['fas', 'eye']" />
                    </a-button>
                  </a-col>
                  <a-col :span="8" v-if="record.statusAccept == -1">
                    <a-button
                      id="edit"
                      @click="showModalEdit(record)"
                      :style="{ width: '44.25px' }"
                    >
                      <font-awesome-icon :icon="['fas', 'edit']" />
                    </a-button>
                  </a-col>
                  <a-col :span="8" v-if="record.statusAccept == -1">
                    <a-popconfirm
                      v-if="dataSourceTable.length"
                      title="Bạn có chắc chắn muốn xóa không?"
                      @confirm="deletePersonalApplication(record.idApplication)"
                      ok-text="Đồng ý"
                      cancel-text="Hủy"
                    >
                      <a-button id="delete">
                        <font-awesome-icon :icon="['fas', 'trash']" />
                      </a-button>
                    </a-popconfirm>
                  </a-col>
                </a-row>
              </template>
            </a-table>
          </div>
          <!-- table content -->
          <!-- popup add-->
          <a-modal v-model="visibleAdd" title="Viết đơn">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button
                key="submit"
                type="primary"
                :loading="loading"
                @click="checkFormAdd"
              >
                Lưu
              </a-button>
            </template>
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Tiêu đề :</a-col
              >
              <a-col flex="auto">
                <a-input @change="inputTitle" v-model="dataAdd.title" />
                <div style="color: red" v-if="checkDataInputTitle.show">
                  {{ checkDataInputTitle.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Ngày :</a-col
              >
              <a-col flex="auto">
                <a-range-picker
                  @change="inputDate"
                  v-model="dataAdd.date"
                  :placeholder="['Ngày bắt đầu', 'Ngày kết thúc']"
                  :disabled-date="disableDateStart"
                  format="DD/MM/YYYY"
                  :style="{ width: '100%' }"
                />
                <div style="color: red" v-if="checkDataInputDate.show">
                  {{ checkDataInputDate.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Nội dung :</a-col
              >
              <a-col flex="auto">
                <a-textarea
                  @change="inputContent"
                  v-model="dataAdd.content"
                  placeholder="Bạn hãy điền nội dung vào đây"
                  :row="4"
                />
                <div style="color: red" v-if="checkDataInputContent.show">
                  {{ checkDataInputContent.message }}
                </div>
              </a-col>
            </a-row>
          </a-modal>
          <!-- popup add -->

          <!-- popup edit-->
          <a-modal v-model="visibleEdit" title="Chỉnh sửa đơn">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button
                key="submit"
                type="primary"
                :loading="loading"
                @click="checkFormEdit"
              >
                Lưu
              </a-button>
            </template>
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Tiêu đề :</a-col
              >
              <a-col flex="auto">
                <a-input @change="inputEditTitle" v-model="dataEdit.title" />
                <div style="color: red" v-if="checkDataInputTitle.show">
                  {{ checkDataInputTitle.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Ngày :</a-col
              >
              <a-col flex="auto">
                <a-range-picker
                  @change="inputEditDate"
                  v-model="dataEdit.date"
                  :placeholder="['Ngày bắt đầu', 'Ngày kết thúc']"
                  format="DD/MM/YYYY"
                  :disabled-date="disableDateStart"
                  :style="{ width: '100%' }"
                />
                <div style="color: red" v-if="checkDataInputDate.show">
                  {{ checkDataInputDate.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Nội dung :</a-col
              >
              <a-col flex="auto">
                <a-textarea
                  @change="inputEditContent"
                  v-model="dataEdit.content"
                  placeholder="Bạn hãy điền nội dung vào đây"
                  :row="4"
                />
                <div style="color: red" v-if="checkDataInputContent.show">
                  {{ checkDataInputContent.message }}
                </div>
              </a-col>
            </a-row>
          </a-modal>
          <!-- popup edit-->

          <!-- popup view-->
          <a-modal v-model="visibleView" class="view" title="Xem đơn chi tiết">
            <template slot="footer">
              <a-button key="submit" @click="handleCancel">Đóng</a-button>
            </template>
            <a-row type="flex">
              <a-col flex="100px"> Tiêu đề</a-col>
              <a-col flex="auto">
                <a-input
                  v-model="dataPersonalLeaveEmployeeDetail.title"
                  disabled
                />
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px"> Ngày bắt đầu</a-col>
              <a-col flex="auto">
                <a-date-picker
                  disabled
                  v-model="dataPersonalLeaveEmployeeDetail.dateStart"
                  format="DD/MM/YYYY"
                  style="width:100%"
                >
                </a-date-picker>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px"> Ngày kết thúc</a-col>
              <a-col flex="auto">
                <a-date-picker
                  disabled
                  v-model="dataPersonalLeaveEmployeeDetail.dateEnd"
                  format="DD/MM/YYYY"
                  style="width:100%"
                >
                </a-date-picker>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px"> Nội dung</a-col>
              <a-col flex="auto">
                <a-textarea
                  v-model="dataPersonalLeaveEmployeeDetail.content"
                  :row="4"
                  disabled
                />
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px"> Trạng thái</a-col>
              <a-col flex="auto">
                <a-tag
                  :color="
                    dataPersonalLeaveEmployeeDetail.statusAccept == '-1'
                      ? 'orange'
                      : dataPersonalLeaveEmployeeDetail.statusAccept == '0'
                      ? 'red'
                      : 'green'
                  "
                >
                  {{
                    dataPersonalLeaveEmployeeDetail.statusAccept == "-1"
                      ? "Chờ duyệt"
                      : dataPersonalLeaveEmployeeDetail.statusAccept == "0"
                      ? "Hủy bỏ"
                      : "Đã duyệt"
                  }}
                </a-tag>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px"> Ngày tạo</a-col>
              <a-col flex="auto">
                <a-date-picker
                  disabled
                  v-model="dataPersonalLeaveEmployeeDetail.dateCreate"
                  format="DD/MM/YYYY"
                  style="width:100%"
                >
                </a-date-picker>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px"> Quản lý xác nhận</a-col>
              <a-col flex="auto">
                <a-input
                  v-model="dataPersonalLeaveEmployeeDetail.nameEmployeeAccess"
                  disabled
                />
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px"> Ngày xác nhận</a-col>
              <a-col flex="auto">
                <a-date-picker
                  disabled
                  v-model="dataPersonalLeaveEmployeeDetail.dateAccept"
                  placeholder="Ngày xác nhận"
                  format="DD/MM/YYYY"
                  style="width:100%"
                >
                </a-date-picker>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px"> Ghi chú của quản lý</a-col>
              <a-col flex="auto">
                <a-textarea
                  v-model="dataPersonalLeaveEmployeeDetail.comment"
                  :row="4"
                  disabled
                />
              </a-col>
            </a-row>
          </a-modal>
          <!-- popup view-->
        </div>
      </a-layout-content>
    </a-layout>
  </div>
</template>
 <script>
import xinNghiService from "@/service/leaveApplicationService";
import moment from "moment";
export default {
  name: "LeaveApplication",
  components: {},
  data() {
    return {
      loading: false,
      pagination: {
        current: 1,
        pageSize: 10,
        total: 0,
      },
      dataSearch: {
        date: [],
        pageIndex: 1,
        pageSize: 10,
        status: "",
        title: "",
        total: 0,
      },
      dataSourceTable: [],
      dataAdd: {
        content: "",
        date: [],
        fileAttach: "",
        title: "",
      },
      dataEdit: {
        content: "",
        date: [],
        fileAttach: "",
        idApplication: 0,
        title: "",
      },
      dataPersonalLeaveEmployeeDetail: {
        id: "",
        title: "",
        content: "",
        date: [],
        dateStart: "",
        dateEnd: "",
        comment: "",
        dateAccept: "",
        dateCreate: "",
        statusAccept: "",
        idEmployeeAccess: "",
        nameEmployeeAccess: "",
      },
      columns: [
        {
          title: "STT",
          width: 100,
          dataIndex: "idApplication",
          key: "idApplication",
          fixed: "left",
        },
        {
          title: "Tiêu đề",
          dataIndex: "titlee",
          key: "titlee",
          width: 150,
          scopedSlots: { customRender: "titlee" },
        },
        {
          title: "Ngày tạo",
          dataIndex: "dateCreate",
          key: "dateCreate",
          width: 150,
          scopedSlots: { customRender: "dateCreate" },
        },
        {
          title: "Ngày chấp nhận",
          dataIndex: "dateAccept",
          key: "dateAccept",
          width: 150,
          scopedSlots: { customRender: "dateAccept" },
        },
        {
          title: "Thời gian bắt đầu",
          dataIndex: "dateStart",
          key: "dateStart",
          width: 150,
          scopedSlots: { customRender: "dateStart" },
        },
        {
          title: "Thời gian kết thúc",
          dataIndex: "dateEnd",
          key: "dateEnd",
          width: 150,
          scopedSlots: { customRender: "dateEnd" },
        },
        {
          title: "Trạng thái",
          dataIndex: "status",
          key: "status",
          width: 150,
          scopedSlots: { customRender: "status" },
        },
        {
          title: "",
          dataIndex: "action",
          key: "action",
          fixed: "right",
          width: 180,
          scopedSlots: { customRender: "action" },
        },
      ],
      visibleAdd: false,
      visibleEdit: false,
      visibleView: false,
      checkDataInputTitle: {
        show: false,
        message: "",
      },
      checkDataInputDate: {
        show: false,
        message: "",
      },
      checkDataInputContent: {
        show: false,
        message: "",
      },
    };
  },
  created() {
    this.submitSearch();
  },
  watch: {
    urlState(newValue) {
      if (newValue.indexOf("/leaveapplication") != -1) {
        this.submitSearch();
      }
    },
  },
  computed: {
    urlState() {
      return this.$store.state.url;
    },
  },
  methods: {
    disableDateStart(current) {
      return current < moment().subtract(1, "days");
    },
    handleTableChange(pagination) {
      this.dataSearch.pageIndex = pagination.current;
      this.pagination = pagination;
      xinNghiService
        .searchPersonalLeaveApplication(this.dataSearch)
        .then((response) => {
          this.dataSourceTable = response.data.data;
          this.dataSearch.total = response.data.total;
          this.pagination.total = response.data.total;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    submitSearch() {
      this.dataSearch.total = 0;
      this.visibleAdd = false;
      this.visibleEdit = false;
      this.visibleView = false;
      xinNghiService
        .searchPersonalLeaveApplication(this.dataSearch)
        .then((response) => {
          this.dataSourceTable = response.data.data;
          this.dataSearch.total = response.data.total;
          this.pagination.total = response.data.total;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    showModalAdd() {
      this.dataAdd.title = "";
      this.dataAdd.date = [];
      this.dataAdd.content = "";
      this.checkDataInputTitle.show = false;
      this.checkDataInputTitle.message = "";
      this.checkDataInputDate.show = false;
      this.checkDataInputDate.message = "";
      this.checkDataInputContent.show = false;
      this.checkDataInputContent.message = "";
      this.visibleAdd = true;
    },
    submitAdd() {
      this.dataAdd.title = this.dataAdd.title.trim();
      this.dataAdd.content = this.dataAdd.content.trim();
      this.loading = true;
      xinNghiService
        .addPersonalApplication(this.dataAdd)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Viết đơn";
            let description =
              "Thêm mới đơn " + this.dataAdd.title + " thành công !!";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Viết đơn";
            let description =
              "Thêm mới đơn " +
              this.dataAdd.title +
              " không thành công vì " +
              response.data.message;
            this.notifi(type, message, description);
          }
          this.loading = false;
          this.visibleAdd = false;
        })
        .catch((e) => {
          console.log(e);
          this.loading = false;
          this.visibleAdd = false;
        });
    },
    checkFormAdd() {
      let check = true;
      if (this.dataAdd.title != null && this.dataAdd.title.trim() != "") {
        this.checkDataInputTitle.show = false;
        this.checkDataInputTitle.message = "";
      } else {
        check = false;
        this.checkDataInputTitle.show = true;
        this.checkDataInputTitle.message = "Bạn phải điền tiêu đề";
      }

      if (this.dataAdd.date != null && this.dataAdd.date.length != 0) {
        this.checkDataInputDate.show = false;
        this.checkDataInputDate.message = "";
      } else {
        check = false;
        this.checkDataInputDate.show = true;
        this.checkDataInputDate.message = "Bạn phải chọn ngày";
      }

      if (this.dataAdd.content != null && this.dataAdd.content != "") {
        this.checkDataInputContent.show = false;
        this.checkDataInputContent.message = "";
      } else {
        check = false;
        this.checkDataInputContent.show = true;
        this.checkDataInputContent.message = "Bạn phải điền nội dung";
      }
      if (check) {
        this.submitAdd();
      }
    },
    handleCancel() {
      this.visibleAdd = false;
      this.visibleEdit = false;
      this.visibleView = false;
    },
    showModalEdit(record) {
      this.checkDataInputTitle.show = false;
      this.checkDataInputTitle.message = "";
      this.checkDataInputDate.show = false;
      this.checkDataInputDate.message = "";
      this.checkDataInputContent.show = false;
      this.checkDataInputContent.message = "";
      this.dataEdit.idApplication = record.idApplication;
      this.dataEdit.title = record.title;
      this.dataEdit.content = record.content;
      let date = [];
      date.push(moment(record.dateStart));
      date.push(moment(record.dateEnd));
      this.dataEdit.date = date;
      this.visibleEdit = true;
    },
    submitUpdate() {
      this.dataEdit.title = this.dataEdit.title.trim();
      this.dataEdit.content = this.dataEdit.content.trim();
      this.loading = true;
      let date = [];
      date.push(moment(this.dataEdit.date[0]).add("1", "days"));
      date.push(moment(this.dataEdit.date[1]).add("1", "days"));
      this.dataEdit.date = date;
      xinNghiService
        .updatePersonalApplication(this.dataEdit)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Cập nhật";
            let description = "Cập nhật đơn thành công";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Cập nhật";
            let description = "Cập nhật đơn không thành công";
            this.notifi(type, message, description);
          }
          this.loading = false;
          this.visibleEdit = false;
        })
        .catch((e) => {
          console.log(e);
          this.loading = false;
          this.visibleEdit = false;
        });
    },
    checkFormEdit() {
      let check = true;
      if (this.dataEdit.title != null && this.dataEdit.title.trim() != "") {
        this.checkDataInputTitle.show = false;
        this.checkDataInputTitle.message = "";
      } else {
        check = false;
        this.checkDataInputTitle.show = true;
        this.checkDataInputTitle.message = "Bạn phải điền tiêu đề";
      }

      if (this.dataEdit.date != null && this.dataEdit.date.length != 0) {
        this.checkDataInputDate.show = false;
        this.checkDataInputDate.message = "";
      } else {
        check = false;
        this.checkDataInputDate.show = true;
        this.checkDataInputDate.message = "Bạn phải chọn ngày";
      }

      if (this.dataEdit.content != null && this.dataEdit.content != "") {
        this.checkDataInputContent.show = false;
        this.checkDataInputContent.message = "";
      } else {
        check = false;
        this.checkDataInputContent.show = true;
        this.checkDataInputContent.message = "Bạn phải điền nội dung";
      }
      if (check) {
        this.submitUpdate();
      }
    },
    deletePersonalApplication(id) {
      xinNghiService
        .deletePersonalApplication(id)
        .then((response) => {
          if (response.data.data) {
            let type = "success";
            let message = "Xóa";
            let description = "Xóa đơn thành công";
            this.notifi(type, message, description);
            this.submitSearch();
          } else {
            let type = "error";
            let message = "Xóa";
            let description = "Xóa đơn không thành công :(";
            this.notifi(type, message, description);
            this.submitSearch();
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
    inputTitle() {
      if (this.dataAdd.title != null && this.dataAdd.title.trim() != "") {
        this.checkDataInputTitle.show = false;
        this.checkDataInputTitle.message = "";
      } else {
        this.checkDataInputTitle.show = true;
        this.checkDataInputTitle.message = "Bạn phải điền tiêu đề";
      }
    },
    inputDate() {
      if (this.dataAdd.date != null && this.dataAdd.date.length != 0) {
        this.checkDataInputDate.show = false;
        this.checkDataInputDate.message = "";
      } else {
        this.checkDataInputDate.show = true;
        this.checkDataInputDate.message = "Bạn phải chọn ngày";
      }
    },
    inputContent() {
      if (this.dataAdd.content != null && this.dataAdd.content.trim() != "") {
        this.checkDataInputContent.show = false;
        this.checkDataInputContent.message = "";
      } else {
        this.checkDataInputContent.show = true;
        this.checkDataInputContent.message = "Bạn phải điền nội dung";
      }
    },
    inputEditTitle() {
      if (this.dataEdit.title != null && this.dataEdit.title.trim() != "") {
        this.checkDataInputTitle.show = false;
        this.checkDataInputTitle.message = "";
      } else {
        this.checkDataInputTitle.show = true;
        this.checkDataInputTitle.message = "Bạn phải điền tiêu đề";
      }
    },
    inputEditDate() {
      if (this.dataEdit.date != null && this.dataEdit.date.length != 0) {
        this.checkDataInputDate.show = false;
        this.checkDataInputDate.message = "";
      } else {
        this.checkDataInputDate.show = true;
        this.checkDataInputDate.message = "Bạn phải chọn ngày";
      }
    },
    inputEditContent() {
      if (this.dataEdit.content != null && this.dataEdit.content.trim() != "") {
        this.checkDataInputContent.show = false;
        this.checkDataInputContent.message = "";
      } else {
        this.checkDataInputContent.show = true;
        this.checkDataInputContent.message = "Bạn phải điền nội dung";
      }
    },
    showModelView(record) {
      this.dataPersonalLeaveEmployeeDetail.title = record.title;
      this.dataPersonalLeaveEmployeeDetail.dateCreate = record.dateCreate;
      this.dataPersonalLeaveEmployeeDetail.content = record.content;
      this.dataPersonalLeaveEmployeeDetail.dateStart = record.dateStart;
      this.dataPersonalLeaveEmployeeDetail.dateEnd = record.dateEnd;
      this.dataPersonalLeaveEmployeeDetail.id = record.idApplication;
      this.dataPersonalLeaveEmployeeDetail.comment = record.comment;
      this.dataPersonalLeaveEmployeeDetail.dateAccept = record.dateAccept;
      this.dataPersonalLeaveEmployeeDetail.statusAccept = record.statusAccept;
      this.dataPersonalLeaveEmployeeDetail.idEmployeeAccess =
        record.idEmployeeAccess;
      this.dataPersonalLeaveEmployeeDetail.nameEmployeeAccess =
        record.nameEmployeeAccess;
      this.visibleView = true;
    },

    notifi(type, message, description) {
      this.$notification[type]({
        message: message,
        description: description,
      });
    },
  },
};
</script>

<style scoped>
/* button icon */
#delete {
  background-color: rgb(255, 0, 0);
  color: white;
}
#delete:hover {
  background-color: rgba(233, 15, 15, 0.863);
  color: white;
}
#edit {
  background-color: rgb(10, 208, 243);
  color: white;
}
#edit:hover {
  background-color: rgb(0, 181, 253);
  color: white;
}
#view {
  background-color: rgb(76, 238, 12);
  color: white;
}
#view:hover {
  background-color: rgb(42, 253, 0);
  color: white;
}
</style>