<template>
  <div class="ungluong">
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
          Trạng thái:
          <a-select
            placeholder="Trạng thái"
            @change="submitSearch"
            v-model="dataSearch.accept"
            :style="{ width: '150px', 'margin-right': '5px' }"
          >
            <a-select-option value=""> Tất cả </a-select-option>
            <a-select-option value="-1"> Chờ duyệt </a-select-option>
            <a-select-option value="0"> Từ chối </a-select-option>
            <a-select-option value="1"> Đã duyệt </a-select-option>
          </a-select>
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
                {{ record.dateCreate }}
              </template>
              <template slot="dateAccept" slot-scope="text, record">
                {{ record.dateAccept }}
              </template>
              <template slot="advanceSalary" slot-scope="text, record">
                {{ record.advanceSalary }}
              </template>
              <template slot="status" slot-scope="text, record">
                <a-tag
                  :color="
                    record.accept == '-1'
                      ? 'orange'
                      : record.accept == '0'
                      ? 'red'
                      : 'green'
                  "
                >
                  {{
                    record.accept == "-1"
                      ? "Chờ duyệt"
                      : record.accept == "0"
                      ? "Từ chối"
                      : "Đã duyệt"
                  }}
                </a-tag>
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
              <template slot="action" slot-scope="text, record">
                <a-row>
                  <a-col :span="8">
                    <a-button
                      id="view"
                      @click="showModalView(record)"
                      :style="{ width: '44.25px', 'margin-right': '100px' }"
                    >
                      <font-awesome-icon :icon="['fas', 'eye']" />
                    </a-button>
                  </a-col>
                  <a-col :span="8" v-if="record.accept == -1">
                    <a-button
                      id="edit"
                      @click="
                        showModalEdit(
                          record.id,
                          record.title,
                          record.content,
                          record.advanceSalary
                        )
                      "
                      :style="{ width: '44.25px' }"
                    >
                      <font-awesome-icon :icon="['fas', 'edit']" />
                    </a-button>
                  </a-col>
                  <a-col :span="8" v-if="record.accept == -1">
                    <a-popconfirm
                      v-if="dataSourceTable.length"
                      title="Bạn có chắc chắn muốn xóa không?"
                      @confirm="deleteAdvanceSalary(record.id)"
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
                :loading="loadingAdd"
                @click="checkFormAdd"
              >
                Lưu
              </a-button>
            </template>
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Tiêu đề:</a-col
              >
              <a-col flex="auto">
                <a-input v-model="dataAdd.title" @change="inputTitleAdd" />
                <div style="color: red" v-if="checkInputTitle.show">
                  {{ checkInputTitle.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Số tiền:</a-col
              >
              <a-col flex="auto">
                <a-input-number
                  style="width: 100%"
                  v-model="dataAdd.advanceSalary"
                  :min="0"
                  @change="inputAdvanceSalaryAdd"
                />
                <div style="color: red" v-if="checkInputSalary.show">
                  {{ checkInputSalary.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Nội dung:
              </a-col>
              <a-col flex="auto">
                <a-textarea
                  v-model="dataAdd.content"
                  placeholder="Lý do viết vào đây"
                  :row="4"
                  @change="inputContentAdd"
                />
                <div style="color: red" v-if="checkInputContent.show">
                  {{ checkInputContent.message }}
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
                @click="checkFormEdit"
                :loading="loadingEdit"
              >
                Lưu
              </a-button>
            </template>
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Tiêu đề:</a-col
              >
              <a-col flex="auto">
                <a-input v-model="dataEdit.title" @change="inputTitleEdit" />
                <div style="color: red" v-if="checkInputTitle.show">
                  {{ checkInputTitle.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Số tiền:</a-col
              >
              <a-col flex="auto">
                <a-input-number
                  style="width: 100%"
                  v-model="dataEdit.advanceSalary"
                  :min="0"
                  @change="inputAdvanceSalaryEdit"
                />
                <div style="color: red" v-if="checkInputSalary.show">
                  {{ checkInputSalary.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Nội dung:
              </a-col>
              <a-col flex="auto">
                <a-textarea
                  v-model="dataEdit.content"
                  placeholder="Lý do viết vào đây"
                  :row="4"
                  @change="inputContentEdit"
                />
                <div style="color: red" v-if="checkInputContent.show">
                  {{ checkInputContent.message }}
                </div>
              </a-col>
            </a-row>
          </a-modal>
          <!-- popup edit-->

          <!-- popup view-->
          <a-modal
            v-model="visibleView"
            class="view"
            title="Thông tin ứng lương"
          >
            <template slot="footer">
              <a-button key="submit" @click="handleCancel">Đóng</a-button>
            </template>
            <a-row type="flex">
              <a-col flex="100px">
                Tiêu đề:</a-col
              >
              <a-col flex="auto">
                <a-input v-model="dataDetail.title" disabled />
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                Số tiền:</a-col
              >
              <a-col flex="auto">
                <a-input v-model="dataDetail.advanceSalary" disabled />
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                Nội dung:</a-col
              >
              <a-col flex="auto">
                <a-textarea v-model="dataDetail.content" :row="4" disabled />
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                Trạng thái:</a-col
              >
              <a-col flex="auto">
                <a-tag
                  :color="
                    dataDetail.accept == '-1'
                      ? 'orange'
                      : dataDetail.accept == '0'
                      ? 'red'
                      : 'green'
                  "
                >
                  {{
                    dataDetail.accept == "-1"
                      ? "Chờ duyệt"
                      : dataDetail.accept == "0"
                      ? "Từ chối"
                      : "Đã duyệt"
                  }}
                </a-tag>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                Ngày tạo:</a-col
              >
              <a-col flex="auto">
                <a-date-picker
                  disabled
                  v-model="dataDetail.dateCreate"
                  format="DD/MM/YYYY"
                >
                </a-date-picker>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                Quản lý xác nhận:</a-col
              >
              <a-col flex="auto">
                <a-input v-model="dataDetail.employeeAccept" disabled />
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                Ngày xác nhận:</a-col
              >
              <a-col flex="auto">
                <a-date-picker
                  disabled
                  v-model="dataDetail.dateAccept"
                  format="DD/MM/YYYY"
                >
                </a-date-picker>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                Ghi chú của quản lý:</a-col
              >
              <a-col flex="auto">
                <a-textarea v-model="dataDetail.comment" :row="4" disabled />
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
import advanceSalaryService from "../service/advanceSalaryService";

export default {
  name: "AdvanceSalary",
  components: {},
  data() {
    return {
      pagination: {
        current: 1,
        pageSize: 10,
        total: 0,
      },
      dataSearch: {
        accept: "",
        content: "",
        date: [],
        pageIndex: 1,
        pageSize: 10,
        title: "",
        total: 0,
      },
      dataSourceTable: [],
      dataAdd: {
        advanceSalary: "",
        content: "",
        title: "",
      },
      dataEdit: {
        advanceSalary: "",
        content: "",
        id: 0,
        title: "",
      },
      dataDetail: {
        id: "",
        title: "",
        content: "",
        advanceSalary: "",
        accept: "",
        comment: "",
        dateAccept: "",
        dateCreate: "",
        employeeAccept: "",
      },
      checkInputTitle: {
        show: false,
        message: "",
      },
      checkInputSalary: {
        show: false,
        message: "",
      },
      checkInputContent: {
        show: false,
        message: "",
      },
      columns: [
        {
          title: "STT",
          width: 100,
          dataIndex: "id",
          key: "id",
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
          title: "Số tiền",
          dataIndex: "advanceSalary",
          key: "advanceSalary",
          width: 150,
          scopedSlots: { customRender: "advanceSalary" },
        },
        {
          title: "Trạng thái",
          dataIndex: "statusAccept",
          key: "statusAccept",
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
      loadingAdd: false,
      loadingEdit: false,
    };
  },
  created() {
    this.submitSearch();
  },
  watch: {
    urlState(newValue) {
      if (newValue.indexOf("/advancesalary") != -1) {
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
    handleTableChange(pagination) {
      this.dataSearch.pageIndex = pagination.current;
      this.pagination = pagination;
      advanceSalaryService
        .searchAdvanceSalaryEmployee(this.dataSearch)
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
      advanceSalaryService
        .searchAdvanceSalaryEmployee(this.dataSearch)
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
      this.dataAdd.advanceSalary = "";
      this.dataAdd.content = "";
      this.checkInputTitle.show = false;
      this.checkInputTitle.message = "";
      this.checkInputSalary.show = false;
      this.checkInputSalary.message = "";
      this.checkInputContent.show = false;
      this.checkInputContent.message = "";

      this.dataAdd.title = "";
      this.dataAdd.advanceSalary = "";
      this.dataAdd.content = "";
      this.visibleAdd = true;
    },
    checkFormAdd() {
      let check = true;
      if (this.dataAdd.title != null && this.dataAdd.title.trim() != "") {
        this.checkInputTitle.show = false;
        this.checkInputTitle.message = "";
      } else {
        check = false;
        this.checkInputTitle.show = true;
        this.checkInputTitle.message = "Bạn phải điền vào ô tiêu đề";
      }
      if (
        this.dataAdd.advanceSalary != null &&
        this.dataAdd.advanceSalary != ""
      ) {
        this.checkInputSalary.show = false;
        this.checkInputSalary.message = "";
      } else {
        check = false;
        this.checkInputSalary.show = true;
        this.checkInputSalary.message = "Bạn phải điền vào ô số tiền";
      }
      if (this.dataAdd.content != null && this.dataAdd.content != "") {
        this.checkInputContent.show = false;
        this.checkInputContent.message = "";
      } else {
        check = false;
        this.checkInputContent.show = true;
        this.checkInputContent.message = "Bạn phải điền vào ô nội dung";
      }
      if (check == true) {
        this.submitAdd();
      }
    },
    submitAdd() {
      this.loadingAdd = true;
      advanceSalaryService
        .addAdvanceSalaryEmployee(this.dataAdd)
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
          this.loadingAdd = false;
          this.visibleAdd = false;
        })
        .catch((e) => {
          console.log(e);
          this.loadingAdd = false;
          this.visibleAdd = false;
        });
    },
    inputTitleAdd() {
      if (this.dataAdd.title != null && this.dataAdd.title.trim() != "") {
        this.checkInputTitle.show = false;
        this.checkInputTitle.message = "";
      } else {
        this.checkInputTitle.show = true;
        this.checkInputTitle.message = "Bạn phải điền vào ô tiêu đề";
      }
    },
    inputAdvanceSalaryAdd() {
      if (
        this.dataAdd.advanceSalary != null &&
        this.dataAdd.advanceSalary != ""
      ) {
        this.checkInputSalary.show = false;
        this.checkInputSalary.message = "";
      } else {
        this.checkInputSalary.show = true;
        this.checkInputSalary.message = "Bạn phải điền vào ô số tiền";
      }
    },
    inputContentAdd() {
      if (this.dataAdd.content != null && this.dataAdd.content != "") {
        this.checkInputContent.show = false;
        this.checkInputContent.message = "";
      } else {
        this.checkInputContent.show = true;
        this.checkInputContent.message = "Bạn phải điền vào ô nội dung";
      }
    },

    handleCancel() {
      this.visibleAdd = false;
      this.visibleEdit = false;
      this.visibleView = false;
    },
    showModalEdit(id, title, content, advanceSalary) {
      this.dataEdit.id = id;
      this.dataEdit.title = title;
      this.dataEdit.content = content;
      this.dataEdit.advanceSalary = advanceSalary;
      this.checkInputTitle.show = false;
      this.checkInputTitle.message = "";
      this.checkInputSalary.show = false;
      this.checkInputSalary.message = "";
      this.checkInputContent.show = false;
      this.checkInputContent.message = "";
      this.visibleEdit = true;
    },
    checkFormEdit() {
      let check = true;
      if (this.dataEdit.title != null && this.dataEdit.title.trim() != "") {
        this.checkInputTitle.show = false;
        this.checkInputTitle.message = "";
      } else {
        check = false;
        this.checkInputTitle.show = true;
        this.checkInputTitle.message = "Bạn phải điền vào ô tiêu đề";
      }
      if (
        this.dataEdit.advanceSalary != null &&
        this.dataEdit.advanceSalary != ""
      ) {
        this.checkInputSalary.show = false;
        this.checkInputSalary.message = "";
      } else {
        check = false;
        this.checkInputSalary.show = true;
        this.checkInputSalary.message = "Bạn phải điền vào ô số tiền";
      }
      if (this.dataEdit.content != null && this.dataEdit.content != "") {
        this.checkInputContent.show = false;
        this.checkInputContent.message = "";
      } else {
        check = false;
        this.checkInputContent.show = true;
        this.checkInputContent.message = "Bạn phải điền vào ô nội dung";
      }
      if (check == true) {
        this.submitUpdate();
      }
    },
    inputTitleEdit() {
      if (this.dataEdit.title != null && this.dataEdit.title.trim() != "") {
        this.checkInputTitle.show = false;
        this.checkInputTitle.message = "";
      } else {
        this.checkInputTitle.show = true;
        this.checkInputTitle.message = "Bạn phải điền vào ô tiêu đề";
      }
    },
    inputAdvanceSalaryEdit() {
      if (
        this.dataEdit.advanceSalary != null &&
        this.dataEdit.advanceSalary != ""
      ) {
        this.checkInputSalary.show = false;
        this.checkInputSalary.message = "";
      } else {
        this.checkInputSalary.show = true;
        this.checkInputSalary.message = "Bạn phải điền vào ô số tiền";
      }
    },
    inputContentEdit() {
      if (this.dataEdit.content != null && this.dataEdit.content != "") {
        this.checkInputContent.show = false;
        this.checkInputContent.message = "";
      } else {
        this.checkInputContent.show = true;
        this.checkInputContent.message = "Bạn phải điền vào ô nội dung";
      }
    },
    submitUpdate() {
      this.loadingEdit = true;
      advanceSalaryService
        .updateAdvanceSalaryEmployee(this.dataEdit)
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
          this.loadingEdit = false;
          this.visibleEdit = false;
        })
        .catch(() => {
          this.loadingEdit = false;
          this.visibleEdit = false;
        });
    },

    deleteAdvanceSalary(id) {
      advanceSalaryService
        .deleteAdvanceSalaryEmployee(id)
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
    showModalView(record) {
      this.dataDetail.id = record.id;
      this.dataDetail.title = record.title;
      this.dataDetail.content = record.content;
      this.dataDetail.advanceSalary = record.advanceSalary;
      this.dataDetail.accept = record.accept;
      this.dataDetail.comment = record.comment;
      this.dataDetail.dateAccept = record.dateAccept;
      this.dataDetail.dateCreate = record.dateCreate;
      this.dataDetail.employeeAccept = record.employeeAccept;
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