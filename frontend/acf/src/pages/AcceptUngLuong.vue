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
            v-model="dataSearch.employeeName"
          />
          <a-input
            placeholder="Tiêu đề"
            style="width: 150px"
            v-model="dataSearch.title"
          />
          <a-select
            placeholder="Trạng thái"
            v-model="dataSearch.accept"
            style="width: 150px"
          >
            <a-select-option value="-1"> Chờ duyệt </a-select-option>
            <a-select-option value="1"> Đã duyệt </a-select-option>
            <a-select-option value="0"> Từ chối </a-select-option>
          </a-select>
          <a-range-picker
            v-model="dataSearch.date"
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
                <a-button
                  id="view"
                  @click="
                    showModalView(
                      record.id,
                      record.nameEmployee,
                      record.title,
                      record.advanceSalary,
                      record.content,
                      record.comment,
                      record.dateAccept
                    )
                  "
                >
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
                type="danger"
                @click="submitReject(dataDetail.id, dataDetail.comment)"
                >Loại bỏ</a-button
              >
              <a-button
                type="primary"
                @click="submitAccept(dataDetail.id, dataDetail.comment)"
              >
                Chấp nhận
              </a-button>
            </template>
            <a-form-model>
              <a-form-model-item label="Nhân viên">
                <a-input v-model="dataDetail.nameEmployee" disabled />
              </a-form-model-item>
              <a-form-model-item label="Tiêu đề">
                <a-input v-model="dataDetail.title" disabled />
              </a-form-model-item>
              <a-form-model-item label="Số tiền">
                <a-input v-model="dataDetail.advanceSalary" disabled />
              </a-form-model-item>
              <a-form-model-item label="Nội dung">
                <a-textarea v-model="dataDetail.content" :rows="4" disabled />
              </a-form-model-item>
              <a-form-model-item label="Ghi chú">
                <a-textarea
                  v-model="dataDetail.comment"
                  placeholder="Nhận xét như nào thì viết vào đây"
                  :rows="4"
                />
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
    };
  },
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
    showModalView(
      id,
      nameEmployee,
      title,
      advanceSalary,
      content,
      comment,
      dataAccept
    ) {
      this.dataDetail.id = id;
      this.dataDetail.nameEmployee = nameEmployee;
      this.dataDetail.title = title;
      this.dataDetail.advanceSalary = advanceSalary;
      this.dataDetail.content = content;
      this.dataDetail.comment = comment;
      this.dataDetail.dataAccept = dataAccept;
      this.visibleView = true;
    },
    handAccept() {
      acceptUngLuongService
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
        })
        .catch((e) => {
          console.log(e);
        });
    },
    submitAccept(id, comment) {
      this.dataAccept.id = id;
      this.dataAccept.comment = comment;
      this.handAccept();
      this.visibleView = false;
    },
    handReject() {
      acceptUngLuongService
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
        })
        .catch((e) => {
          console.log(e);
        });
    },
    submitReject(id, comment) {
      this.dataAccept.id = id;
      this.dataAccept.comment = comment;
      this.handReject();
      this.visibleView = false;
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
#view {
  background-color: rgb(76, 238, 12);
  color: white;
}
#view:hover {
  background-color: rgb(42, 253, 0);
  color: white;
}
</style>