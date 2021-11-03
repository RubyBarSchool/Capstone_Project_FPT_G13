<template>
  <div class="admin">
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
          <a-date-picker format="YYYY-MM-DD" valueFormat="YYYY-MM-DD">
          </a-date-picker>
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
              <template slot="titlee" slot-scope="text, record">
                {{ record.title }}
              </template>
              <template slot="money" slot-scope="text, record">
                {{ record.advanceSalary }}
              </template>
              <template slot="status" slot-scope="text, record">
                <a-tag :color="record.accept ? 'green' : 'gray'">
                  {{ record.accept ? "Đã duyệt" : "Chờ duyệt" }}
                </a-tag>
              </template>
              <template slot="action" slot-scope="text, record">
                <a-row>
                  <a-col :span="8">
                    <a-button id="edit" @click="getDetailAdvanceSalaryAdmin(record.id)">
                      <font-awesome-icon :icon="['fas', 'eye']" />
                    </a-button>
                  </a-col>
                </a-row>
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
              <a-button type="danger">Loại bỏ</a-button>
              <a-button type="primary"> Chấp nhận </a-button>
            </template>
            <a-form-model>
              <a-form-model-item label="Nhân viên">
                <a-input v-model="dataAdvanceSalaryAdminDetail.nameEmployee" disabled />
              </a-form-model-item>
              <a-form-model-item label="Tiêu đề">
                <a-input v-model="dataAdvanceSalaryAdminDetail.title" disabled />
              </a-form-model-item>
              <a-form-model-item label="Số tiền">
                <a-input v-model="dataAdvanceSalaryAdminDetail.advanceSalary" disabled />
              </a-form-model-item>
              <a-form-model-item label="Nội dung">
                <a-textarea v-model="dataAdvanceSalaryAdminDetail.content" :rows="4" disabled />
              </a-form-model-item>
              <a-form-model-item label="Ghi chú">
                <a-textarea
                  v-model="dataAdvanceSalaryAdminDetail.note"
                  placeholder="Nhận xét như nào thì viết vào đây"
                  :rows="4"
                />
              </a-form-model-item>
            </a-form-model>
          </a-modal>
          <!-- popup profile-->
        </div>
      </a-layout-content>
      <Footer />
    </a-layout>
  </div>
</template>
 <script>
import acceptUngLuongService from "@/service/acceptUngLuongService.js";
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";

export default {
  name: "acceptungluong",
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
        employeeName: "",
        pageIndex: 1,
        pageSize: 10,
        status: "",
        title: "",
        total: 0,
      },
      dataSourceTable: [],
      dataAdvanceSalaryAdminDetail: {
        nameEmployee: "",
        title: "",
        advanceSalary: "",
        content: "",
        note:"",
      },
      columns: [
        {
          title: "ID",
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
    };
  },
  computed: {},
  created() {
    this.submitSearch();
  },
  methods: {
    handleTableChange(pagination) {
      this.dataSearch.pageIndex = pagination.current;
      this.pagination = pagination;
      acceptUngLuongService
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
      acceptUngLuongService
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

    getDetailAdvanceSalaryAdmin(id) {
      acceptUngLuongService
        .getDetailAdvanceSalaryAdmin(id)
        .then((response) => {
          this.dataAdvanceSalaryAdminDetail = response.data.data;
          this.visibleView = true;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    submitAccept() {
      acceptUngLuongService
        .acceptAdvanceSalaryAdmin()
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
            let description = "Cập nhật trạng thái đơn thành công";
            this.notifi(type, message, description);
            this.submitSearch();
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
    handleCancel() {
      this.visibleView = false;
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
#user {
  background-color: rgb(76, 238, 12);
  color: white;
}
#user:hover {
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