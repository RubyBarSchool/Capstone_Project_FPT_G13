<template>
  <div class="historyluongadmin">
    <a-layout :style="{ background: 'white' }">
      <Header />
      <a-layout-content :style="{ margin: '24px 16px 0' }">
        <a-back-top>
          <div class="ant-back-top-inner">
            <font-awesome-icon
              :icon="['fas', 'angle-double-up']"
              :style="{ width: '160px', height: '50px', color: '#15AABF' }"
            />
          </div>
        </a-back-top>
        <!-- menu -->
        <a-input
          placeholder="Họ và tên"
          style="width: 150px"
        />
        <a-input
          placeholder="Chức vụ"
          style="width: 150px"
        />
        <a-date-picker placeholder="Chọn ngày"  />
        <a-button type="primary" :style="{ 'margin-left': '5px' }">
          <font-awesome-icon
            :icon="['fas', 'search']"
            @click="submitSearch"
            :style="{ 'margin-right': '5px' }"
          />
          Tìm kiếm
        </a-button>
        <!-- menu -->

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
            <template slot="date" slot-scope="text, record">
              {{ record.date }}
            </template>
            <template slot="nameEmployee" slot-scope="text, record">
              {{ record.nameEmployee }}
            </template>
            <template slot="namePosition" slot-scope="text, record">
              {{ record.namePosition }}
            </template>
            <template slot="countWork" slot-scope="text, record">
              {{ record.countWork }}
            </template>
            <template slot="salary" slot-scope="text, record">
              {{ record.salary }}
            </template>
            <template slot="bonus" slot-scope="text, record">
              {{ record.bonus }}
            </template>
            <template slot="penalty" slot-scope="text, record">
              {{ record.penalty }}
            </template>
            <template slot="advanceSalary" slot-scope="text, record">
              {{ record.advanceSalary }}
            </template>
            <template slot="totalMoney" slot-scope="text, record">
              {{ record.totalMoney }}
            </template>
            <template slot="status" slot-scope="text, record">
                <a-tag :color="record.status ? 'green' : 'blue'">
                  {{ record.status ? "Đã thanh toán" : "Chưa thanh toán" }}
                </a-tag>
              </template>
          </a-table>
        </div>
        <!-- table content -->
      </a-layout-content>
      <Footer />
    </a-layout>
  </div>
</template>

<script>
import historyLuongAdminService from "@/service/historyLuongAdmin.js";
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";
export default {
  name: "historyluongadmin",
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
        idPositons: [],
        name: "",
        pageIndex: 1,
        pageSize: 10,
        total: 0,
      },
      dataSourceTable: [],
      columns: [
        {
          title: "ID",
          width: 100,
          dataIndex: "id",
          key: "id",
          fixed: "left",
        },
        {
          title: "Tháng",
          dataIndex: "date",
          key: "date",
          width: 150,
          scopedSlots: { customRender: "date" },
        },
        {
          title: "Họ và tên",
          dataIndex: "nameEmployee",
          key: "nameEmployee",
          width: 150,
          scopedSlots: { customRender: "nameEmployee" },
        },
        {
          title: "Chức vụ",
          dataIndex: "namePosition",
          key: "namePosition",
          width: 150,
          scopedSlots: { customRender: "namePosition" },
        },
        {
          title: "Số công",
          dataIndex: "countWork",
          key: "countWork",
          width: 150,
          scopedSlots: { customRender: "countWork" },
        },
        {
          title: "Lương",
          dataIndex: "salary",
          key: "salary",
          width: 150,
          scopedSlots: { customRender: "salary" },
        },
        {
          title: "Thưởng",
          dataIndex: "bonus",
          key: "bonus",
          width: 150,
          scopedSlots: { customRender: "bonus" },
        },
        {
          title: "Phạt",
          dataIndex: "penalty",
          key: "penalty",
          width: 150,
          scopedSlots: { customRender: "penalty" },
        },
        {
          title: "Tạm ứng",
          dataIndex: "advanceSalary",
          key: "advanceSalary",
          width: 150,
          scopedSlots: { customRender: "advanceSalary" },
        },
        {
          title: "Tổng nhận",
          dataIndex: "totalMoney",
          key: "totalMoney",
          width: 150,
          scopedSlots: { customRender: "total" },
        },
        {
          title: "Trạng thái",
          dataIndex: "status",
          key: "status",
          width: 150,
          scopedSlots: { customRender: "status" },
        },
      ],
    };
  },
  created() {
    this.submitSearch();
  },
  methods: {
    handleTableChange(pagination) {
      this.dataSearch.pageIndex = pagination.current;
      this.pagination = pagination;
      historyLuongAdminService
        .salaryHistory(this.dataSearch)
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
      historyLuongAdminService
        .salaryHistory(this.dataSearch)
        .then((response) => {
          this.dataSourceTable = response.data.data;
          this.dataSearch.total = response.data.total;
          this.pagination.total = response.data.total;
        })
        .catch((e) => {
          console.log(e);
        });
    },
  },
};
</script>

<style>
</style>