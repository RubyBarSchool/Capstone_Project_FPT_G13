<template>
  <div class="admin">
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
            placeholder="Nhân viên"
            v-model="dataSearch.employeeName"
            :style="{'margin-right': '5px', 'width': '12%'}"
          />
          <a-input
            placeholder="Tiêu đề"
            v-model="dataSearch.title"
            :style="{'margin-right': '10px', 'width': '12%'}"
          />
          Trạng thái: 
          <a-select
            placeholder="Trạng thái"
            @change="submitSearch"
            v-model="dataSearch.accept"
            :style="{'margin-right': '10px', 'width':'8%'}"
          >
            <a-select-option value=""> Tất cả </a-select-option>
            <a-select-option value="-1"> Chờ duyệt </a-select-option>
            <a-select-option value="1"> Đã duyệt </a-select-option>
            <a-select-option value="0"> Từ chối </a-select-option>
          </a-select>
          Ngày tạo: 
          <a-range-picker
            v-model="dataSearch.date"
            @change="submitSearch"
            :placeholder="['Ngày bắt đầu', 'Ngày kết thúc']"
            format="DD/MM/YYYY"
          />
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
              <template slot="titlee" slot-scope="text, record">
                {{ record.title }}
              </template>
              <template slot="money" slot-scope="text, record">
                {{ record.advanceSalary }}
              </template>
              <template slot="status" slot-scope="text, record">
                <a-tag
                  :color="
                    record.status == '-1'
                      ? 'orange'
                      : record.status == '0'
                      ? 'red'
                      : 'green'
                  "
                >
                  {{
                    record.status == "-1"
                      ? "Chờ duyệt"
                      : record.status == "0"
                      ? "Từ chối"
                      : "Đã duyệt"
                  }}
                </a-tag>
              </template>
              <template slot="action" slot-scope="text, record">
                <a-button id="view" @click="showModalView(record)">
                  <font-awesome-icon :icon="['fas', 'eye']" />
                </a-button>
              </template>
            </a-table>
          </div>
          <!-- table content -->

          <!-- popup view-->
          <a-modal
            v-model="visibleView"
            class="view"
            title="Xem xét đơn xin ứng lương"
          >
            <template slot="footer">
              <a-button key="back" @click="handleCancel">Hủy</a-button>
              <a-button
                v-if="dataDetail.status == -1"
                type="danger"
                :loading="loadingReject"
                :disabled="disableReject"
                @click="checkFormReject(dataDetail.id, dataDetail.comment)"
                >Loại bỏ</a-button
              >
              <a-button
                v-if="dataDetail.status == -1"
                type="primary"
                :loading="loadingAccept"
                :disabled="disableAccept"
                @click="checkFormAccept(dataDetail.id, dataDetail.comment)"
              >
                Chấp nhận
              </a-button>
            </template>
            <a-form-model>
              <span style="color: red">*</span> Nhân viên:
              <a-form-model-item>
                <a-input v-model="dataDetail.nameEmployee" disabled />
              </a-form-model-item>
              <span style="color: red">*</span> Tiêu đề:
              <a-form-model-item>
                <a-input v-model="dataDetail.title" disabled />
              </a-form-model-item>
              <span style="color: red">*</span> Số tiền:
              <a-form-model-item>
                <a-input v-model="dataDetail.advanceSalary" disabled />
              </a-form-model-item>
              <span style="color: red">*</span> Nội dung:
              <a-form-model-item>
                <a-textarea v-model="dataDetail.content" :rows="4" disabled />
              </a-form-model-item>
              <span style="color: red">*</span> Ghi chú:
              <a-form-model-item>
                <a-textarea
                  v-model="dataDetail.comment"
                  :disabled="dataDetail.status != -1"
                  placeholder="Nhận xét viết vào đây"
                  :rows="4"
                  @change="inputComment"
                />
                <div style="color: red" v-if="checkDataInputComment.show">
                  {{ checkDataInputComment.message }}
                </div>
              </a-form-model-item>
            </a-form-model>
          </a-modal>
          <!-- popup view-->
        </div>
      </a-layout-content>
    </a-layout>
  </div>
</template>
 <script>
import acceptAdvanceSalaryService from "@/service/acceptAdvanceSalaryService.js";

export default {
  name: "AcceptAdvanceSalary",
  data() {
    return {
      pagination: {
        current: 1,
        pageSize: 10,
        total: 0,
      },
      dataSearch: {
        accept: "",
        date: [],
        employeeName: "",
        pageIndex: 1,
        pageSize: 10,
        title: "",
        total: 0,
      },
      dataSourceTable: [],
      dataDetail: {
        advanceSalary: "",
        comment: "",
        content: "",
        date: "",
        dateAccept: "",
        id: "",
        idEmployee: "",
        nameEmployee: "",
        status: "",
        title: "",
        dataAccept: "",
      },
      dataAccept: {
        comment: "",
        id: 0,
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
          title: "Tiêu đề",
          dataIndex: "titlee",
          key: "titlee",
          width: 150,
          scopedSlots: { customRender: "titlee" },
        },
        {
          title: "Số tiền",
          dataIndex: "money",
          key: "money",
          width: 150,
          scopedSlots: { customRender: "money" },
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
      visibleView: false,
      checkDataInputComment: {
        show: false,
        message: "",
      },
      loadingReject: false,
      loadingAccept: false,
      disableAccept: false,
      disableReject: false,
    };
  },
  created() {
    this.submitSearch();
  },
  watch: {
    urlState(newValue) {
      if (newValue.indexOf("/acceptadvancesalary") != -1) {
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
      acceptAdvanceSalaryService
        .searchAdvanceSalaryAdmin(this.dataSearch)
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
      this.dataSearch.employeeName =  this.dataSearch.employeeName.trim();
      this.dataSearch.title = this.dataSearch.title.trim();
      this.visibleView = false;
      acceptAdvanceSalaryService
        .searchAdvanceSalaryAdmin(this.dataSearch)
        .then((response) => {
          this.dataSourceTable = response.data.data;
          this.dataSearch.total = response.data.total;
          this.pagination.total = response.data.total;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    showModalView(record) {
      this.dataDetail.id = record.id;
      this.dataDetail.nameEmployee = record.nameEmployee;
      this.dataDetail.title = record.title;
      this.dataDetail.advanceSalary = record.advanceSalary;
      this.dataDetail.content = record.content;
      this.dataDetail.comment = record.comment;
      this.dataDetail.dataAccept = record.dataAccept;
      this.dataDetail.date = record.date;
      this.dataDetail.idEmployee = record.idEmployee;
      this.dataDetail.status = record.status;
      this.checkDataInputComment.show = false;
      this.checkDataInputComment.message = "";
      this.visibleView = true;
    },
    handAccept() {
      this.loadingAccept = true;
      this.disableReject = true;
      acceptAdvanceSalaryService
        .acceptAdvanceSalaryAdmin(this.dataAccept)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Cập nhật";
            let description = "Cập nhật trạng thái đơn thành công";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Cập nhật";
            let description = "Cập nhật trạng thái đơn thành công";
            this.notifi(type, message, description);
          }
          this.loadingAccept = false;
          this.disableReject = false;
          this.visibleView = false;
        })
        .catch(() => {
          this.loadingAccept = false;
          this.disableReject = false;
          this.visibleView = false;
        });
    },
    submitAccept(id, comment) {
      this.dataAccept.id = id;
      this.dataAccept.comment = comment;
      this.handAccept();
    },
    checkFormReject(id, comment) {
      if (
        this.dataDetail.comment != null &&
        this.dataDetail.comment.trim() != ""
      ) {
        this.checkDataInputComment.show = false;
        this.checkDataInputComment.message = "";
        this.submitReject(id, comment);
      } else {
        this.checkDataInputComment.show = true;
        this.checkDataInputComment.message = "Bạn phải điền vào ô ghi chú";
      }
    },
    checkFormAccept(id, comment) {
      if (
        this.dataDetail.comment != null &&
        this.dataDetail.comment.trim() != ""
      ) {
        this.checkDataInputComment.show = false;
        this.checkDataInputComment.message = "";
        this.submitAccept(id, comment);
      } else {
        this.checkDataInputComment.show = true;
        this.checkDataInputComment.message = "Bạn phải điền vào ô ghi chú";
      }
    },
    inputComment() {
      if (
        this.dataDetail.comment != null &&
        this.dataDetail.comment.trim() != ""
      ) {
        this.checkDataInputComment.show = false;
        this.checkDataInputComment.message = "";
      } else {
        this.checkDataInputComment.show = true;
        this.checkDataInputComment.message = "Bạn phải điền vào ô ghi chú";
      }
    },
    handReject() {
      this.loadingReject = true;
      this.disableAccept = true;
      acceptAdvanceSalaryService
        .rejectAdvanceSalaryAdmin(this.dataAccept)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Cập nhật";
            let description = "Cập nhật trạng thái đơn thành công";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Cập nhật";
            let description = "Cập nhật trạng thái đơn thành công";
            this.notifi(type, message, description);
          }
          this.loadingReject = false;
          this.disableAccept = false;
          this.visibleView = false;
        })
        .catch(() => {
          this.loadingReject = false;
          this.disableAccept = false;
          this.visibleView = false;
        });
    },
    submitReject(id, comment) {
      this.dataAccept.id = id;
      this.dataAccept.comment = comment;
      this.handReject();
    },
    handleCancel() {
      this.visibleView = false;
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