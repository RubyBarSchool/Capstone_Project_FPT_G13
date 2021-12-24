<template>
  <div class="acceptluongadmin">
    <a-layout :style="{ background: 'white' }">
      <a-layout-content :style="{ margin: '24px 16px 0' }">
        <a-back-top :style="{ width: '5vh', height: '15vh' }" />
        <!-- menu -->
        <a-input
          placeholder="Họ và tên"
          :style="{ width: '150px', 'margin-right': '5px' }"
          v-model="dataSearch.name"
        />
        <a-select
          placeholder="Chức vụ"
          :filter-option="false"
          @change="search"
          @search="fetchPosition"
          :style="{ width: '150px', 'margin-right': '5px' }"
          v-model="dataSearch.idPosition"
        >
          <a-select-option
            v-for="(position, index) in dataPositions"
            :value="position.id"
            :key="index"
          >
            {{ position.name }}
          </a-select-option>
        </a-select>
        <a-range-picker
          @change="search"
          v-model="dataSearch.date"
          :placeholder="['Ngày bắt đầu', 'Ngày kết thúc']"
          format="DD/MM/YYYY"
        />
        <a-button
          @click="search"
          type="primary"
          :style="{ 'margin-left': '5px' }"
        >
          <font-awesome-icon
            :icon="['fas', 'search']"
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
            :scroll="{ x: 1000 }"
            :rowKey="
              (record, index) => {
                return index;
              }
            "
            @change="handleTableChange"
          >
            <template slot="date" slot-scope="text, record">
              {{
                new Date(record.date).toLocaleDateString("en-GB", {
                  year: "numeric",
                  month: "2-digit",
                  day: "2-digit",
                })
              }}
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
            <template slot="action" slot-scope="text, record">
              <a-popconfirm
                :disabled="!checkShow(record)"
                title="Bạn có chắc chắn muốn thanh toán tiền lương không?"
                @confirm="bill(record.id)"
                ok-text="Đồng ý"
                cancel-text="Hủy"
              >
                <a-button :disabled="!checkShow(record)" type="primary">
                  Thanh toán
                </a-button>
              </a-popconfirm>
            </template>
          </a-table>
        </div>
        <!-- table content -->
      </a-layout-content>
    </a-layout>
  </div>
</template>

<script>
import userService from "../service/userService";
import accecptSalaryAdminService from "@/service/accecptSalaryAdminService.js";
import moment from "moment";
export default {
  name: "acceptluongadmin",
  components: {},
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
      dataPositions: [],
      dataPosition: {
        name: "",
        pageIndex: 1,
        pageSize: 10,
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
          width: 120,
          scopedSlots: { customRender: "bonus" },
        },
        {
          title: "Phạt",
          dataIndex: "penalty",
          key: "penalty",
          width: 120,
          scopedSlots: { customRender: "penalty" },
        },
        {
          title: "Tạm ứng",
          dataIndex: "advanceSalary",
          key: "advanceSalary",
          width: 120,
          scopedSlots: { customRender: "advanceSalary" },
        },
        {
          title: "Tổng nhận",
          dataIndex: "totalMoney",
          key: "totalMoney",
          width: 120,
          scopedSlots: { customRender: "total" },
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
    };
  },
  created() {
    this.submitSearch();
    this.getAllPosition();
  },
  methods: {
    checkShow(record) {
      let date = moment(record.date).add("1", "months");
      if (moment() >= date) {
        return true;
      }
      return false;
    },
    handleTableChange(pagination) {
      this.dataSearch.pageIndex = pagination.current;
      this.pagination = pagination;
      accecptSalaryAdminService
        .salaryAccept(this.dataSearch)
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
      accecptSalaryAdminService
        .salaryAccept(this.dataSearch)
        .then((response) => {
          this.dataSourceTable = response.data.data;
          this.dataSearch.total = response.data.total;
          this.pagination.total = response.data.total;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    fetchPosition(value) {
      this.dataPosition.name = value;
      this.getAllPosition();
    },
    getAllPosition() {
      userService
        .getAllPosition(this.dataPosition)
        .then((response) => {
          this.dataPositions = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    search() {
      this.dataSearch.pageIndex = 1;
      this.dataSearch.total = 0;
      this.dataSearch.name = this.dataSearch.name.trim();
      this.submitSearch();
    },
    bill(id) {
      accecptSalaryAdminService
        .salaryAcceptDone(id)
        .then(() => {
          this.search();
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