<template>
  <div class="acceptxinnghi">
    <a-layout :style="{ background: 'white' }">
      <a-layout-content :style="{ margin: '24px 16px 0' }">
        <div
          :style="{
            minHeight: '360px',
            background: 'white',
          }"
        >
          <a-back-top :style="{ width: '5vh', height: '15vh' }" />
          <!-- menu trên -->
          <a-input
            placeholder="Nhân viên"
            style="width: 150px"
            v-model="dataSearch.nameEmployee"
            :style="{ 'margin-right': '5px', width: '12%' }"
          />
          <a-input
            placeholder="Tiêu đề"
            v-model="dataSearch.title"
            :style="{ 'margin-right': '10px', width: '12%' }"
          />
          Trạng thái
          <a-select
            placeholder="Trạng thái"
            @change="submitSearch"
            v-model="dataSearch.status"
            :style="{ 'margin-right': '10px', width: '8%' }"
          >
            <a-select-option value=""> Tất cả </a-select-option>
            <a-select-option value="-1"> Chờ duyệt </a-select-option>
            <a-select-option value="1"> Đã duyệt </a-select-option>
            <a-select-option value="0"> Từ chối </a-select-option>
          </a-select>
          Ngày tạo
          <a-range-picker
            v-model="dataSearch.date"
            @change="submitSearch"
            :placeholder="['Ngày bắt đầu', 'Ngày kết thúc']"
            format="DD/MM/YYYY"
          >
          </a-range-picker>
          <a-button
            type="primary"
            :style="{ 'margin-left': '5px' }"
            @click="submitSearch"
          >
            <font-awesome-icon
              :icon="['fas', 'search']"
              :style="{ 'margin-right': '5px' }"
            />
            Tìm kiếm
          </a-button>
          <!-- menu trên -->

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
              <template slot="employee" slot-scope="text, record">
                {{ record.nameEmployee }}
              </template>
              <template slot="date" slot-scope="text, record">
                {{
                  new Date(record.date).toLocaleDateString("en-GB", {
                    year: "numeric",
                    month: "2-digit",
                    day: "2-digit",
                  })
                }}
              </template>
              <template slot="statusAccept" slot-scope="text, record">
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
                      ? "Từ chối"
                      : "Đã duyệt"
                  }}
                </a-tag>
              </template>
              <template slot="titlee" slot-scope="text, record">
                {{ record.title }}
              </template>
              <template slot="comment" slot-scope="text, record">
                {{ record.comment }}
              </template>
              <template slot="action" slot-scope="text, record">
                <a-button
                  id="view"
                  @click="
                    showModelView(
                      record.idApplication,
                      record.idEmployee,
                      record.nameEmployee,
                      record.title,
                      record.date,
                      record.content,
                      record.comment,
                      record.fileAttach,
                      record.statusAccept,
                      record.dateStart,
                      record.dateEnd
                    )
                  "
                >
                  <font-awesome-icon :icon="['fas', 'eye']" />
                </a-button>
              </template>
            </a-table>
          </div>
          <!-- table content -->

          <!-- popup view -->
          <a-modal v-model="visibleView" title="Xét đơn xin nghỉ">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Đóng </a-button>
              <a-button
                v-if="dataDetail.statusAccept == -1"
                type="danger"
                :loading="loadingReject"
                :disabled="disabledReject"
                @click="checkFormReject()"
              >
                Loại bỏ
              </a-button>
              <a-button
                v-if="dataDetail.statusAccept == -1"
                type="primary"
                :loading="loadingAccept"
                :disabled="disabledAccept"
                @click="checkFormAccept()"
              >
                Chấp nhận
              </a-button>
            </template>
            <div class="container">
              <a-form-model>
                <span style="color: red">*</span> Nhân viên :
                <a-input v-model="dataDetail.nameEmployee" disabled />
                <span style="color: red">*</span> Tiêu đề :
                <a-input v-model="dataDetail.title" disabled />
                <a-row :gutter="[16, 8]">
                  <a-col :span="8">
                    <span style="color: red">*</span> Ngày tạo :
                    <a-input v-model="dataDetail.date" disabled />
                  </a-col>
                  <a-col :span="8">
                    <span style="color: red">*</span> Ngày bắt đầu :
                    <a-input v-model="dataDetail.dateStart" disabled />
                  </a-col>
                  <a-col :span="8">
                    <span style="color: red">*</span> Ngày kết thúc :
                    <a-input v-model="dataDetail.dateEnd" disabled />
                  </a-col>
                </a-row>
                <span style="color: red">*</span> Nội dung :
                <a-textarea
                  v-model="dataDetail.content"
                  :auto-size="{
                    minRows: 1,
                    maxRows: 6,
                  }"
                  disabled
                />
                <span style="color: red">*</span> Ghi chú ;
                <a-textarea
                  @change="inputNote"
                  placeholder="Viết ghi chú....."
                  :disabled="dataDetail.statusAccept != -1"
                  v-model="dataDetail.comment"
                  :rows="4"
                />
                <div style="color: red" v-if="checkDataInputNote.show">
                  {{ checkDataInputNote.message }}
                </div>
              </a-form-model>
            </div>
          </a-modal>
          <!-- popup view -->
        </div>
      </a-layout-content>
    </a-layout>
  </div>
</template>

<script>
import acceptXinNghiService from "../service/acceptLeaveApplicationService";
import moment from "moment";
export default {
  name: "AcceptLeaveApplication",
  components: {},
  data() {
    return {
      loadingReject: false,
      loadingAccept: false,
      disabledReject: false,
      disabledAccept: false,
      checkDataInputNote: {
        show: false,
        message: "",
      },
      pagination: {
        current: 1,
        pageSize: 10,
        total: 0,
      },
      dataSearch: {
        date: [],
        nameEmployee: "",
        pageIndex: 1,
        pageSize: 10,
        status: "",
        title: "",
        total: 0,
      },
      dataSourceTable: [],
      dataDetail: {
        comment: "",
        date: "",
        dateAccept: "",
        content: "",
        fileAttach: "",
        idApplication: "",
        idEmployee: "",
        nameEmployee: "",
        statusAccept: "",
        title: "",
        dateStart: "",
        dateEnd: "",
      },
      dataAccept: {
        comment: "",
        idApplication: 0,
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
          title: "Nhân viên",
          dataIndex: "employee",
          key: "employee",
          width: 150,
          scopedSlots: { customRender: "employee" },
        },
        {
          title: "Ngày tạo",
          dataIndex: "date",
          key: "date",
          width: 150,
          scopedSlots: { customRender: "date" },
        },
        {
          title: "Trạng thái",
          dataIndex: "statusAccept",
          key: "statusAccept",
          width: 150,
          scopedSlots: { customRender: "statusAccept" },
        },
        {
          title: "Tiêu đề",
          dataIndex: "titlee",
          key: "titlee",
          width: 150,
          scopedSlots: { customRender: "titlee" },
        },
        {
          title: "Ghi chú",
          dataIndex: "comment",
          key: "comment",
          width: 150,
          scopedSlots: { customRender: "comment" },
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
      visibleView: false,
      url: "",
    };
  },
  created() {
    this.submitSearch();
  },
  watch: {
    urlState(newValue) {
      if (newValue.indexOf("/acceptleaveapplication") != -1) {
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
      acceptXinNghiService
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
      this.dataSearch.nameEmployee =  this.dataSearch.nameEmployee.trim();
      this.dataSearch.title = this.dataSearch.title.trim();
      this.visibleView = false;
      acceptXinNghiService
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
    //     dateAccept: "",
    showModelView(
      idApplication,
      idEmployee,
      nameEmployee,
      title,
      date,
      content,
      comment,
      fileAttach,
      statusAccept,
      dateStart,
      dateEnd
    ) {
      this.dataDetail.nameEmployee = nameEmployee;
      this.dataDetail.title = title;
      let date1 = moment(date).format("DD/MM/YYYY");
      this.dataDetail.date = date1;
      this.dataDetail.content = content;
      this.dataDetail.comment = comment;
      this.dataDetail.fileAttach = fileAttach;
      this.dataDetail.statusAccept = statusAccept;
      this.dataDetail.idEmployee = idEmployee;
      this.dataDetail.idApplication = idApplication;
      let dateStart1 = moment(dateStart).format("DD/MM/YYYY");
      this.dataDetail.dateStart = dateStart1;
      let dateEnd1 = moment(dateEnd).format("DD/MM/YYYY");
      this.dataDetail.dateEnd = dateEnd1;
      this.visibleView = true;
    },

    handleCancel() {
      this.visibleView = false;
    },

    search() {
      this.dataSearch.pageIndex = 1;
      this.dataSearch.total = 0;
      this.submitSearch();
    },

    handAccept() {
      this.loadingAccept = true;
      this.disabledReject = true;
      acceptXinNghiService
        .acceptPersonalApplication(this.dataAccept)
        .then((response) => {
          if (response.data.data) {
            let type = "success";
            let message = "Cập nhật";
            let description = "Xác nhận đơn thành công";
            this.notifi(type, message, description);
            this.submitSearch();
          } else {
            let type = "error";
            let message = "Cập nhật";
            let description = "Xác nhận đơn không thành công";
            this.notifi(type, message, description);
            this.submitSearch();
          }
          this.loadingAccept = false;
          this.disabledReject = false;
          this.visibleView = false;
        })
        .catch((e) => {
          console.log(e);
          this.loadingAccept = false;
          this.disabledReject = false;
          this.visibleView = false;
        });
    },

    handReject() {
      this.loadingReject = true;
      this.disabledAccept = true;
      acceptXinNghiService
        .rejectPersonalApplication(this.dataAccept)
        .then((response) => {
          if (response.data.data) {
            let type = "success";
            let message = "Cập nhật";
            let description = "Hủy đơn thành công";
            this.notifi(type, message, description);
            this.submitSearch();
          } else {
            let type = "error";
            let message = "Cập nhật";
            let description = "Hủy đơn không thành công";
            this.notifi(type, message, description);
            this.submitSearch();
          }
          this.loadingReject = false;
          this.disabledAccept = false;
          this.visibleView = false;
        })
        .catch((e) => {
          console.log(e);
          this.loadingReject = false;
          this.disabledAccept = false;
          this.visibleView = false;
        });
    },

    submitAccept() {
      this.dataAccept.idApplication = this.dataDetail.idApplication;
      this.dataAccept.comment = this.dataDetail.comment;
      this.handAccept();
    },
    checkFormAccept() {
      let check = true;
      if (
        this.dataDetail.comment != null &&
        this.dataDetail.comment.trim() != ""
      ) {
        this.checkDataInputNote.show = false;
        this.checkDataInputNote.message = "";
      } else {
        check = false;
        this.checkDataInputNote.show = true;
        this.checkDataInputNote.message = "Bạn phải điền nội dung vào đây";
      }
      if (check) {
        this.submitAccept();
      }
    },

    submitReject() {
      this.dataAccept.idApplication = this.dataDetail.idApplication;
      this.dataAccept.comment = this.dataDetail.comment;
      this.handReject();
    },
    checkFormReject() {
      let check = true;
      if (
        this.dataDetail.comment != null &&
        this.dataDetail.comment.trim() != ""
      ) {
        this.checkDataInputNote.show = false;
        this.checkDataInputNote.message = "";
      } else {
        check = false;
        this.checkDataInputNote.show = true;
        this.checkDataInputNote.message = "Bạn phải điền nội dung vào đây";
      }
      if (check) {
        this.submitReject();
      }
    },

    inputNote() {
      if (
        this.dataDetail.comment != null &&
        this.dataDetail.comment.trim() != ""
      ) {
        this.checkDataInputNote.show = false;
        this.checkDataInputNote.message = "";
      } else {
        this.checkDataInputNote.show = true;
        this.checkDataInputNote.message = "Bạn phải điền nội dung vào đây";
      }
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
#view {
  background-color: rgb(76, 238, 12);
  color: white;
}
#view:hover {
  background-color: rgb(42, 253, 0);
  color: white;
}
</style>