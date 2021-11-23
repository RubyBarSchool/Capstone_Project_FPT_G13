<template>
  <div class="xinnghi">
    <a-layout :style="{ background: 'white' }">
      <Header />
      <a-layout-content :style="{ margin: '30px 16px 0' }">
        <div
          :style="{
            minHeight: '360px',
            background: 'white',
          }"
        >
          <a-back-top>
            <div class="ant-back-top-inner">
              <font-awesome-icon
                :icon="['fas', 'angle-double-up']"
                :style="{ width: '160px', height: '50px', color: '#15AABF' }"
              />
            </div>
          </a-back-top>
          <!-- menu trên -->
          <a-input
            placeholder="Tiêu đề"
            style="width: 150px"
            v-model="dataSearch.title"
          />
          Trạng thái
          <a-select
            v-model="dataSearch.status"
            @change="submitSearch"
            style="width: 150px"
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
              :icon="['fas', 'envelope-open-text']"
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
              <template slot="dateStart" slot-scope="text, record">
                {{ record.dateStart }}
              </template>
              <template slot="dateEnd" slot-scope="text, record">
                {{ record.dateEnd }}
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
                  <a-col :span="8" v-if="record.statusAccept==-1">
                    <a-button
                      id="edit"
                      @click="
                        showModalEdit(record)
                      "
                      :style="{ width: '44.25px' }"
                    >
                      <font-awesome-icon :icon="['fas', 'edit']" />
                    </a-button>
                  </a-col>
                  <a-col :span="8" v-if="record.statusAccept==-1">
                    <a-popconfirm
                      v-if="dataSourceTable.length"
                      title="Bạn có chắc chắn muốn xóa không?"
                      @confirm="deletePersonalApplication(record.idApplication)"
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
              <a-button key="submit" type="primary" @click="checkFormAdd">
                Lưu
              </a-button>
            </template>
            <a-form-model>
              <a-form-model-item label="Tiêu đề">
                <a-input v-model="dataAdd.title" />
                <div style="color: red" v-if="checkDataInput.show">
                  {{ checkDataInput.message }}
                </div>
              </a-form-model-item>
              <a-form-model-item label="Ngày">
                <a-range-picker
                  v-model="dataAdd.date"
                  :placeholder="['Ngày bắt đầu', 'Ngày kết thúc']"
                  format="DD/MM/YYYY"
                  :style="{ width: '472px' }"
                />
              </a-form-model-item>

              <a-form-model-item label="Nội dung">
                <a-textarea
                  v-model="dataAdd.content"
                  placeholder="Lý do như nào thì viết vào đây"
                  :row="4"
                />
                <div style="color: red" v-if="checkDataInput.show">
                  {{ checkDataInput.message }}
                </div>
              </a-form-model-item>
            </a-form-model>
          </a-modal>
          <!-- popup add -->

          <!-- popup edit-->
          <a-modal v-model="visibleEdit" title="Chỉnh sửa đơn">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary" @click="submitUpdate">
                Lưu
              </a-button>
            </template>
            <a-form-model>
              <a-form-model-item label="Tiêu đề">
                <a-input v-model="dataEdit.title" />
              </a-form-model-item>
              <a-form-model-item label="Ngày">
                <a-range-picker
                  v-model="dataEdit.date"
                  :placeholder="['Ngày bắt đầu', 'Ngày kết thúc']"
                  :style="{ width: '472px' }"
                />
              </a-form-model-item>
              <a-form-model-item label="Nội dung">
                <a-textarea
                  v-model="dataEdit.content"
                  placeholder="Lý do như nào thì viết vào đây"
                  :row="4"
                />
              </a-form-model-item>
            </a-form-model>
          </a-modal>
          <!-- popup edit-->

          <!-- popup view-->
          <a-modal v-model="visibleView" class="view">
            <template slot="footer">
              <a-button key="submit" @click="handleCancel">Đóng</a-button>
            </template>
            <a-form-model>
              <a-form-model-item label="Tiêu đề">
                <a-input
                  v-model="dataPersonalLeaveEmployeeDetail.title"
                  disabled
                />
              </a-form-model-item>
              <a-form-model-item label="Ngày bắt đầu">
                <a-date-picker
                  disabled
                  v-model="dataPersonalLeaveEmployeeDetail.dateStart"
                  format="DD/MM/YYYY"
                >
                </a-date-picker>
              </a-form-model-item>
              <a-form-model-item label="Ngày kết thúc">
                <a-date-picker
                  disabled
                  v-model="dataPersonalLeaveEmployeeDetail.dateEnd"
                  format="DD/MM/YYYY"
                >
                </a-date-picker>
              </a-form-model-item>
              <a-form-model-item label="Nội dung">
                <a-textarea
                  v-model="dataPersonalLeaveEmployeeDetail.content"
                  :row="4"
                  disabled
                />
              </a-form-model-item>
              <a-form-model-item label="Nội dung">
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
              </a-form-model-item>
              <a-form-model-item label="Ngày tạo">
                <a-date-picker
                  disabled
                  v-model="dataPersonalLeaveEmployeeDetail.dateCreate"
                  format="DD/MM/YYYY"
                >
                </a-date-picker>
              </a-form-model-item>
              <a-form-model-item label="Quản lý xác nhận">
                <a-input v-model="dataPersonalLeaveEmployeeDetail.nameEmployeeAccess" disabled />
              </a-form-model-item>
              <a-form-model-item label="Ngày xác nhận">
                <a-date-picker
                  disabled
                  v-model="dataPersonalLeaveEmployeeDetail.dateAccept"
                  format="DD/MM/YYYY"
                >
                </a-date-picker>
              </a-form-model-item>
              <a-form-model-item label="Ghi chú của quản lý">
                <a-textarea v-model="dataPersonalLeaveEmployeeDetail.comment" :row="4" disabled />
              </a-form-model-item>
            </a-form-model>
          </a-modal>
          <!-- popup view-->
        </div>
      </a-layout-content>
      <Footer />
    </a-layout>
  </div>
</template>
 <script>
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";
import xinNghiService from "@/service/xinNghiService";
import moment from "moment";
export default {
  name: "XinNghi",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
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
          width: 150,
          scopedSlots: { customRender: "action" },
        },
      ],
      visibleAdd: false,
      visibleEdit: false,
      visibleView: false,
      checkDataInput: {
        show: false,
        message: "",
      },
    };
  },
  created() {
    this.submitSearch();
  },
  methods: {
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
      this.visibleAdd = true;
      this.checkDataInput.show = false;
      this.checkDataInput.message = "";
    },
    submitAdd() {
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
        })
        .catch((e) => {
          console.log(e);
        });
      this.visibleAdd = false;
      this.dataAdd.title = "";
      this.dataAdd.date = [];
      this.dataAdd.content = "";
    },
    checkFormAdd() {
      if (
        this.dataAdd.title != null &&
        this.dataAdd.title != "" &&
        this.dataAdd.content != null &&
        this.dataAdd.content != ""
      ) {
        this.checkDataInput.show = false;
        this.checkDataInput.message = "";
        this.submitAdd();
      } else {
        this.checkDataInput.show = true;
        this.checkDataInput.message = "Bạn phải điền vào chỗ trống";
      }
    },
    handleCancel() {
      this.visibleAdd = false;
      this.visibleEdit = false;
      this.visibleView = false;
    },
    showModalEdit(record) {
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
      let date = [];
      date.push(moment(this.dataEdit.date[0]).add('1','days'));
      date.push(moment(this.dataEdit.date[1]).add('1','days'));
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
        })
        .catch((e) => {
          console.log(e);
        });
      this.visibleEdit = false;
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

    showModelView(record) {
      console.log("datarow", record);
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
/* back top */
.ant-back-top-inner {
  color: rgb(241, 237, 237);
  text-align: center;
}

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