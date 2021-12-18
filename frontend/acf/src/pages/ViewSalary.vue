<template>
  <div class="viewluong">
    <a-layout :style="{ background: 'white' }">
      <a-layout-content :style="{ margin: '24px 16px 0' }">
         <a-back-top :style="{ width: '5vh', height: '15vh' }" />
        <a-tabs default-active-key="1" @change="changeTab">
          <!-- Hiện tại -->
          <a-tab-pane key="1">
            <span slot="tab"> <h6>Hiện tại</h6> </span>
            <a-table
              :columns="columnsSalaryNow"
              :data-source="datasalaryNow"
              :scroll="{ x: 1000 }"
              :pagination="false"
              :rowKey="
                (record, index) => {
                  return index;
                }
              "
              @change="handleTableChangeSalaryNow"
            >
              <template slot="status" slot-scope="text, record">
                <a-tag :color="record.status ? 'green' : 'blue'">
                  {{ record.status ? "Đã thanh toán" : "Chưa thanh toán" }}
                </a-tag>
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
            </a-table>
            <div class="container-fluid">
              <div class="row">
                <div class="col-6">
                  <h6>Thưởng</h6>
                  <a-table
                    :columns="columnsBonusPunish"
                    :data-source="dataBonusNow"
                    :pagination="paginationBonusNow"
                    :scroll="{ x: 1000 }"
                    :rowKey="
                      (record, index) => {
                        return index;
                      }
                    "
                    @change="handleTableChangeBonusNow"
                  >
                    <template slot="status" slot-scope="text, record">
                      <a-tag :color="record.status ? 'green' : 'blue'">
                        {{
                          record.status ? "Đã có hiệu lực" : "Chưa có hiệu lực"
                        }}
                      </a-tag>
                    </template>
                    <template slot="effectiveDate" slot-scope="text, record">
                      {{
                        new Date(record.effectiveDate).toLocaleDateString(
                          "en-GB",
                          {
                            year: "numeric",
                            month: "2-digit",
                            day: "2-digit",
                          }
                        )
                      }}
                    </template>
                  </a-table>
                </div>
                <div class="col-6">
                  <h6>Phạt</h6>
                  <a-table
                    :columns="columnsBonusPunish"
                    :data-source="dataPunishNow"
                    :pagination="paginationPunishNow"
                    :scroll="{ x: 1000 }"
                    :rowKey="
                      (record, index) => {
                        return index;
                      }
                    "
                    @change="handleTableChangePunishNow"
                  >
                    <template slot="status" slot-scope="text, record">
                      <a-tag :color="record.status ? 'green' : 'blue'">
                        {{
                          record.status ? "Đã có hiệu lực" : "Chưa có hiệu lực"
                        }}
                      </a-tag>
                    </template>
                    <template slot="effectiveDate" slot-scope="text, record">
                      {{
                        new Date(record.effectiveDate).toLocaleDateString(
                          "en-GB",
                          {
                            year: "numeric",
                            month: "2-digit",
                            day: "2-digit",
                          }
                        )
                      }}
                    </template>
                  </a-table>
                </div>
              </div>
            </div>
          </a-tab-pane>

          <!-- Quá khứ -->
          <a-tab-pane key="2">
            <span slot="tab"> <h6>Quá khứ</h6> </span>
            <a-date-picker
              v-model="dateSelect"
              :disabled-date="disabledDate"
              @change="changeDate"
            />
            <a-table
              :columns="columnsSalaryNotNow"
              :data-source="dataSalaryNotNow"
              :pagination="paginationSalaryNotNow"
              :scroll="{ x: 1000 }"
              :rowKey="
                (record, index) => {
                  return index;
                }
              "
              @change="handleTableChangeSalaryNotNow"
            >
              <template slot="status" slot-scope="text, record">
                <a-tag :color="record.status ? 'green' : 'blue'">
                  {{ record.status ? "Đã thanh toán" : "Chưa thanh toán" }}
                </a-tag>
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
            </a-table>
            <div class="container-fluid">
              <div class="row">
                <div class="col-6">
                  <h6>Thưởng</h6>
                  <a-table
                    :columns="columnsBonusPunish"
                    :data-source="dataBonusNotNow"
                    :pagination="paginationBonusNotNow"
                    :scroll="{ x: 1000 }"
                    :rowKey="
                      (record, index) => {
                        return index;
                      }
                    "
                    @change="handleTableChangeBonusNotNow"
                  >
                    <template slot="status" slot-scope="text, record">
                      <a-tag :color="record.status ? 'green' : 'blue'">
                        {{
                          record.status ? "Chưa có hiệu lực" : "Đã có hiệu lực"
                        }}
                      </a-tag>
                    </template>
                    <template slot="effectiveDate" slot-scope="text, record">
                      {{
                        new Date(record.effectiveDate).toLocaleDateString(
                          "en-GB",
                          {
                            year: "numeric",
                            month: "2-digit",
                            day: "2-digit",
                          }
                        )
                      }}
                    </template>
                  </a-table>
                </div>
                <div class="col-6">
                  <h6>Phạt</h6>
                  <a-table
                    :columns="columnsBonusPunish"
                    :data-source="dataPunishNotNow"
                    :pagination="paginationPunishNotNow"
                    :scroll="{ x: 1000 }"
                    :rowKey="
                      (record, index) => {
                        return index;
                      }
                    "
                    @change="handleTableChangePunishNotNow"
                  >
                    <template slot="status" slot-scope="text, record">
                      <a-tag :color="record.status ? 'green' : 'blue'">
                        {{
                          record.status ? "Chưa có hiệu lực" : "Đã có hiệu lực"
                        }}
                      </a-tag>
                    </template>
                    <template slot="effectiveDate" slot-scope="text, record">
                      {{
                        new Date(record.effectiveDate).toLocaleDateString(
                          "en-GB",
                          {
                            year: "numeric",
                            month: "2-digit",
                            day: "2-digit",
                          }
                        )
                      }}
                    </template>
                  </a-table>
                </div>
              </div>
            </div>
          </a-tab-pane>
        </a-tabs>
      </a-layout-content>
    </a-layout>
  </div>
</template>

<script>
import moment from "moment";
import ViewSalaryService from "@/service/viewSalaryService.js";
export default {
  name: "ViewSalary",
  components: {},
  data() {
    return {
      columnsSalaryNow: [
        {
          title: "STT",
          width: 100,
          dataIndex: "id",
          key: "id",
          fixed: "left",
        },
        {
          title: "Ngày tạo",
          dataIndex: "date",
          key: "date",
          width: 150,
          scopedSlots: { customRender: "date" },
        },
        {
          title: "Họ Và Tên",
          dataIndex: "nameEmployee",
          key: "nameEmployee",
          width: 150,
        },
        {
          title: "Chức vụ",
          dataIndex: "namePosition",
          key: "namePosition",
          width: 150,
        },
        {
          title: "Số công",
          dataIndex: "countWork",
          key: "countWork",
          width: 150,
        },
        {
          title: "Lương / 1 Công",
          dataIndex: "salary",
          key: "salary",
          width: 150,
        },
        {
          title: "Thưởng",
          dataIndex: "bonus",
          key: "bonus",
          width: 150,
        },
        {
          title: "Phạt",
          dataIndex: "penalty",
          key: "penalty",
          width: 150,
        },
        {
          title: "Tạm ứng",
          dataIndex: "advanceSalary",
          key: "advanceSalary",
          width: 150,
        },
        {
          title: "Tổng nhận",
          dataIndex: "totalMoney",
          key: "totalMoney",
          width: 150,
        },
        {
          title: "Trạng thái",
          dataIndex: "status",
          key: "status",
          width: 150,
          fixed: "right",
          scopedSlots: { customRender: "status" },
        },
      ],
      columnsSalaryNotNow: [
        {
          title: "STT",
          width: 100,
          dataIndex: "id",
          key: "id",
          fixed: "left",
        },
        {
          title: "Ngày tạo",
          dataIndex: "date",
          key: "date",
          width: 150,
          scopedSlots: { customRender: "date" },
        },
        {
          title: "Họ Và Tên",
          dataIndex: "nameEmployee",
          key: "nameEmployee",
          width: 150,
        },
        {
          title: "Chức vụ",
          dataIndex: "namePosition",
          key: "namePosition",
          width: 150,
        },
        {
          title: "Số công",
          dataIndex: "countWork",
          key: "countWork",
          width: 150,
        },
        {
          title: "Lương / 1 Công",
          dataIndex: "salary",
          key: "salary",
          width: 150,
        },
        {
          title: "Thưởng",
          dataIndex: "bonus",
          key: "bonus",
          width: 150,
        },
        {
          title: "Phạt",
          dataIndex: "penalty",
          key: "penalty",
          width: 150,
        },
        {
          title: "Tạm ứng",
          dataIndex: "advanceSalary",
          key: "advanceSalary",
          width: 150,
        },
        {
          title: "Tổng nhận",
          dataIndex: "totalMoney",
          key: "totalMoney",
          width: 150,
        },
        {
          title: "Tài khoản thanh toán",
          dataIndex: "accountAccept",
          key: "accountAccept",
          width: 150,
        },
        {
          title: "Ngày thanh toán",
          dataIndex: "acceptDate",
          key: "acceptDate",
          width: 150,
        },
        {
          title: "Trạng thái",
          dataIndex: "status",
          key: "status",
          width: 150,
          fixed: "right",
          scopedSlots: { customRender: "status" },
        },
      ],
      columnsBonusPunish: [
        {
          title: "STT",
          width: 100,
          dataIndex: "id",
          key: "id",
          fixed: "left",
        },
        {
          title: "Ngày hiệu lực",
          dataIndex: "effectiveDate",
          key: "effectiveDate",
          width: 150,
          scopedSlots: { customRender: "effectiveDate" },
        },
        {
          title: "Tiêu đề",
          dataIndex: "title",
          key: "title",
          width: 150,
        },
        {
          title: "Số tiền",
          dataIndex: "money",
          key: "money",
          width: 150,
        },
        {
          title: "Trạng thái",
          dataIndex: "status",
          key: "status",
          width: 150,
          scopedSlots: { customRender: "status" },
        },
      ],
      dateSelect: "",
      datasalaryNow: [],
      dataSalaryNotNow: [],
      dataBonusNow: [],
      dataBonusNotNow: [],
      dataPunishNow: [],
      dataPunishNotNow: [],

      paginationSalaryNow: {
        current: 1,
        pageSize: 10,
        total: 0,
      },
      paginationSalaryNotNow: {
        current: 1,
        pageSize: 10,
        total: 0,
      },
      paginationBonusNow: {
        current: 1,
        pageSize: 10,
        total: 0,
      },
      paginationPunishNow: {
        current: 1,
        pageSize: 10,
        total: 0,
      },
      paginationBonusNotNow: {
        current: 1,
        pageSize: 10,
        total: 0,
      },
      paginationPunishNotNow: {
        current: 1,
        pageSize: 10,
        total: 0,
      },
      dataSearchSalaryNow: {
        checkNow: true,
        date: "",
        pageIndex: 1,
        pageSize: 10,
        total: 0,
      },
      dataSearchSalaryNotNow: {
        checkNow: false,
        date: "",
        pageIndex: 1,
        pageSize: 10,
        total: 0,
      },
      dataSearchBonusNow: {
        checkNow: true,
        date: "",
        pageIndex: 1,
        pageSize: 10,
        total: 0,
      },
      dataSearchBonusNotNow: {
        checkNow: false,
        date: "",
        pageIndex: 1,
        pageSize: 10,
        total: 0,
      },
      dataSearchPunishNow: {
        checkNow: true,
        date: "",
        pageIndex: 1,
        pageSize: 10,
        total: 0,
      },
      dataSearchPunishNotNow: {
        checkNow: false,
        date: "",
        pageIndex: 1,
        pageSize: 10,
        total: 0,
      },
    };
  },
  created() {
    this.searchSalaryNow();
    this.searchBonusNow();
    this.searchPunishNow();
  },
  watch: {
    urlState(newValue) {
      if (newValue.indexOf("/viewsalary") != -1) {
        this.searchSalaryNow();
        this.searchBonusNow();
        this.searchPunishNow();
      }
    },
  },
  computed: {
    urlState() {
      return this.$store.state.url;
    },
  },
  methods: {
    disabledDate(current) {
      let date = moment();
      if (date.date() >= 10) {
        let date1 = moment().subtract(1, "months");
        return current.date() != 10 || current > date1.set("date", 10);
      } else {
        let date2 = moment().subtract(2, "months");
        return current.date() != 10 || current > date2.set("date", 10);
      }
    },
    changeDate() {
      this.dataSearchSalaryNotNow.date = this.dateSelect;
      this.dataSearchBonusNotNow.date = this.dateSelect;
      this.dataSearchPunishNotNow.date = this.dateSelect;

      this.dataSearchSalaryNotNow.pageIndex = 1;
      this.dataSearchBonusNotNow.pageIndex = 1;
      this.dataSearchPunishNotNow.pageIndex = 1;

      this.dataSearchSalaryNotNow.total = 0;
      this.dataSearchBonusNotNow.total = 0;
      this.dataSearchPunishNotNow.total = 0;
      this.searchSalaryNotNow();
      this.searchBonusNotNow();
      this.searchPunishNotNow();
    },
    changeTab(key) {
      if (key === 1) {
        this.searchSalaryNow();
        this.searchBonusNow();
        this.searchPunishNow();
      } else {
        this.searchSalaryNotNow();
        this.searchBonusNotNow();
        this.searchPunishNotNow();
        // this.dateSelect = moment().subtract(1, "months");
      }
    },
    handleTableChangeSalaryNow(pagination) {
      this.dataSearchSalaryNow.pageIndex = pagination.current;
      this.paginationSalaryNow = pagination;
      this.searchSalaryNow();
    },
    handleTableChangeSalaryNotNow(pagination) {
      this.dataSearchSalaryNotNow.pageIndex = pagination.current;
      this.paginationSalaryNotNow = pagination;
      this.searchSalaryNotNow();
    },
    handleTableChangeBonusNow(pagination) {
      this.dataSearchBonusNow.pageIndex = pagination.current;
      this.paginationBonusNow = pagination;
      this.searchBonusNow();
    },
    handleTableChangeBonusNotNow(pagination) {
      this.dataSearchBonusNotNow.pageIndex = pagination.current;
      this.paginationBonusNotNow = pagination;
      this.searchBonusNotNow();
    },
    handleTableChangePunishNow(pagination) {
      this.dataSearchPunishNow.pageIndex = pagination.current;
      this.paginationPunishNow = pagination;
      this.searchPunishNow();
    },
    handleTableChangePunishNotNow(pagination) {
      this.dataSearchPunishNotNow.pageIndex = pagination.current;
      this.paginationPunishNotNow = pagination;
      this.searchPunishNotNow();
    },
    searchSalaryNow() {
      ViewSalaryService.getSalary(this.dataSearchSalaryNow)
        .then((response) => {
          this.datasalaryNow = response.data.data;
          this.dataSearchSalaryNow.total = response.data.total;
          this.paginationSalaryNow.total = response.data.total;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    searchSalaryNotNow() {
      ViewSalaryService.getSalary(this.dataSearchSalaryNotNow)
        .then((response) => {
          this.dataSalaryNotNow = response.data.data;
          this.dataSearchSalaryNotNow.total = response.data.total;
          this.paginationSalaryNotNow.total = response.data.total;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    searchBonusNow() {
      ViewSalaryService.getBonus(this.dataSearchBonusNow)
        .then((response) => {
          this.dataBonusNow = response.data.data;
          this.dataSearchBonusNow.total = response.data.total;
          this.paginationBonusNow.total = response.data.total;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    searchPunishNow() {
      ViewSalaryService.getPunish(this.dataSearchPunishNow)
        .then((response) => {
          this.dataPunishNow = response.data.data;
          this.dataSearchPunishNow.total = response.data.total;
          this.paginationPunishNow.total = response.data.total;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    searchBonusNotNow() {
      ViewSalaryService.getBonus(this.dataSearchBonusNotNow)
        .then((response) => {
          this.dataBonusNotNow = response.data.data;
          this.dataSearchBonusNotNow.total = response.data.total;
          this.paginationBonusNotNow.total = response.data.total;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    searchPunishNotNow() {
      ViewSalaryService.getPunish(this.dataSearchPunishNotNow)
        .then((response) => {
          this.dataPunishNotNow = response.data.data;
          this.dataSearchPunishNotNow.total = response.data.total;
          this.paginationPunishNotNow.total = response.data.total;
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