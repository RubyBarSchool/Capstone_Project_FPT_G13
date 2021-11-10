<template>
  <div class="acceptxinnghi">
    <a-layout :style="{ background: 'white' }">
      <Header />
      <a-layout-content :style="{ margin: '24px 16px 0' }">
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
            placeholder="Nhân viên"
            style="width: 150px"
            v-model="dataSearch.nameEmployee"
          />
          <a-input
            placeholder="Tiêu đề"
            style="width: 150px"
            v-model="dataSearch.title"
          />
          <a-select
            placeholder="Trạng thái"
            v-model="dataSearch.status"
            style="width: 150px"
          >
            <a-select-option value="false"> Chờ duyệt </a-select-option>
            <a-select-option value="true"> Đã duyệt </a-select-option>
          </a-select>
          <a-range-picker
            v-model="dataSearch.date"
            :placeholder="['Ngày bắt đầu', 'Ngày kết thúc']"
            format="YYYY-MM-DD"
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
                {{ record.date }}
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
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button type="danger" @click="submitReject()">
                Loại bỏ
              </a-button>
              <a-button type="primary" @click="submitAccept()">
                Chấp nhận
              </a-button>
            </template>
            <div class="container">
              <a-form-model>
                <a-form-model-item label="Nhân viên">
                  <a-input v-model="dataDetail.nameEmployee" disabled />
                </a-form-model-item>
                <a-form-model-item label="Tiêu đề">
                  <a-input v-model="dataDetail.title" disabled />
                </a-form-model-item>
                <a-row :gutter="[16, 8]">
                  <a-col :span="8">
                    <a-form-model-item label="Ngày tạo">
                      <a-input v-model="dataDetail.date" disabled />
                    </a-form-model-item>
                  </a-col>
                  <a-col :span="8">
                    <a-form-model-item label="Ngày bắt đầu">
                      <a-input v-model="dataDetail.dateStart" disabled />
                    </a-form-model-item>
                  </a-col>
                  <a-col :span="8">
                    <a-form-model-item label="Ngày kết thúc">
                      <a-input v-model="dataDetail.dateEnd" disabled />
                    </a-form-model-item>
                  </a-col>
                </a-row>
                <a-form-model-item label="Nội dung">
                  <a-textarea
                    v-model="dataDetail.content"
                    :auto-size="{
                      minRows: 1,
                      maxRows: 6,
                    }"
                    disabled
                  />
                </a-form-model-item>
                <a-form-model-item label="Ghi chú">
                  <a-textarea
                    placeholder="Viết ghi chú....."
                    v-model="dataDetail.comment"
                    :rows="4"
                  />
                </a-form-model-item>
              </a-form-model>
            </div>
          </a-modal>
          <!-- popup view -->
        </div>
      </a-layout-content>
      <Footer />
    </a-layout>
  </div>
</template>

<script>
import acceptXinNghiService from "../service/acceptXinNghiService";
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";
export default {
  name: "AcceptXinNghi",
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
    };
  },
  created() {
    this.submitSearch();
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
      this.dataDetail.date = date;
      this.dataDetail.content = content;
      this.dataDetail.comment = comment;
      this.dataDetail.fileAttach = fileAttach;
      this.dataDetail.statusAccept = statusAccept;
      this.dataDetail.idEmployee = idEmployee;
      this.dataDetail.idApplication = idApplication;
      this.dataDetail.dateStart = dateStart;
      this.dataDetail.dateEnd = dateEnd;
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
      acceptXinNghiService
        .acceptPersonalApplication(this.dataAccept)
        .then((response) => {
          if (response.data.data) {
            let type = "success";
            let message = "Cập nhật";
            let description = "Cập nhật trạng thái đơn thành công";
            this.notifi(type, message, description);
            this.submitSearch();
          } else {
            let type = "error";
            let message = "Cập nhật";
            let description = "Cập nhật trạng thái đơn không thành công";
            this.notifi(type, message, description);
            this.submitSearch();
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },

    handReject() {
      acceptXinNghiService
        .rejectPersonalApplication(this.dataAccept)
        .then((response) => {
          if (response.data.data) {
            let type = "success";
            let message = "Cập nhật";
            let description = "Cập nhật trạng thái đơn thành công";
            this.notifi(type, message, description);
            this.submitSearch();
          } else {
            let type = "error";
            let message = "Cập nhật";
            let description = "Cập nhật trạng thái đơn không thành công";
            this.notifi(type, message, description);
            this.submitSearch();
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },

    submitAccept() {
      this.dataAccept.idApplication = this.dataDetail.idApplication;
      this.dataAccept.comment = this.dataDetail.comment;
      this.handAccept();
      this.visibleView = false;
    },

    submitReject() {
      this.dataAccept.idApplication = this.dataDetail.idApplication;
      this.dataAccept.comment = this.dataDetail.comment;
      this.handReject();
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
/* back top */
.ant-back-top-inner {
  color: rgb(241, 237, 237);
  text-align: center;
}

/* button icon */
#view {
  background-color: rgb(76, 238, 12);
  color: white;
}
#view:hover {
  background-color: rgb(42, 253, 0);
  color: white;
}
/* profile */
.bg-c-lite-green {
  border-radius: 5px;
  background: linear-gradient(to right, #000000, #000000);
}

.card-block {
  padding: 1.25rem;
}

.m-b-25 {
  margin-bottom: 30px;
}

.img-radius {
  border-radius: 5px;
}

h6 {
  font-size: 13.5px;
}

.card-block p {
  line-height: 25px;
}

.card-block {
  padding: 1.25rem;
}

.b-b-default {
  border-bottom: 1px solid #e0e0e0;
}

.m-b-20 {
  margin-bottom: 20px;
}

.p-b-5 {
  padding-bottom: 5px !important;
}

.m-b-10 {
  margin-bottom: 10px;
  color: black;
}

.text-muted {
  color: #919aa3 !important;
}

.text-white {
  color: white;
}

.f-w-600 {
  font-weight: 600;
}

.m-t-40 {
  margin-top: 20px;
}
/* profile */
</style>