<template>
  <div class="viewThuongPhat">
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
            v-model="dataSearch.title"
            placeholder="Tiêu đề"
            :style="{ width: '150px', 'margin-right': '5px' }"
          />
          Loại đơn:
          <a-select
            v-model="dataSearch.bonus"
            @change="submitSearch"
            placeholder="Loại đơn"
            :style="{ width: '150px', 'margin-right': '5px' }"
          >
            <a-select-option value=""> Tất cả </a-select-option>
            <a-select-option value="false"> Phạt </a-select-option>
            <a-select-option value="true"> Thưởng </a-select-option>
          </a-select>
          Trạng thái:
          <a-select
            v-model="dataSearch.status"
            @change="submitSearch"
            placeholder="Trạng thái"
            :style="{ width: '150px', 'margin-right': '5px' }"
          >
            <a-select-option value=""> Tất cả </a-select-option>
            <a-select-option value="false"> Chờ duyệt </a-select-option>
            <a-select-option value="true"> Đã duyệt </a-select-option>
          </a-select>
          <a-range-picker
            @change="submitSearch"
            v-model="dataSearch.date"
            :placeholder="['Ngày hiệu lực', 'Ngày hiệu lực']"
            format="DD/MM/YYYY"
          />
          <a-button
            @click="submitSearch()"
            type="primary"
            :style="{ 'margin-left': '5px' }"
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
              <template slot="effectiveDate" slot-scope="text, record">
                {{
                  new Date(record.effectiveDate).toLocaleDateString("en-GB", {
                    year: "numeric",
                    month: "2-digit",
                    day: "2-digit",
                  })
                }}
              </template>
              <template slot="status" slot-scope="text, record">
                <a-tag :color="record.status ? 'green' : 'orange'">
                  {{ record.status ? "Đã duyệt" : "Chờ duyệt" }}
                </a-tag>
              </template>
              <template slot="bonus" slot-scope="text, record">
                <a-tag :color="record.bonus ? 'green ' : 'red '">
                  {{ record.bonus ? "THƯỞNG" : "PHẠT" }}
                </a-tag>
              </template>
              <template slot="action" slot-scope="text, record">
                <a-button
                  id="view"
                  @click="checkType(record)"
                  :style="{ width: '44.25px', 'margin-right': '100px' }"
                >
                  <font-awesome-icon :icon="['fas', 'eye']" />
                </a-button>
              </template>
            </a-table>
            <!-- table content -->
          </div>

          <!-- popup view -->
          <a-modal v-model="visibleView" :title="titleView">
            <template slot="footer">
              <a-button @click="handelCancel">Đóng</a-button>
            </template>
            <a-row type="flex">
              <a-col flex="100px"> Tiêu đề</a-col>
              <a-col flex="auto">
                <a-input v-model="dataDetail.title" disabled />
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px"> Lý do</a-col>
              <a-col flex="auto">
                <a-textarea v-model="dataDetail.reason" disabled :row="4" />
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px"> Số tiền</a-col>
              <a-col flex="auto">
                <a-input v-model="dataDetail.money" disabled />
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px"> Trạng thái</a-col>
              <a-col flex="auto">
                <a-tag :color="dataDetail.status ? 'green' : 'orange'">
                  {{ dataDetail.status ? "Đã duyệt" : "Chờ duyệt" }}
                </a-tag>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px"> Ngày hiệu lực</a-col>
              <a-col flex="auto">
                <a-date-picker
                  v-model="dataDetail.effectiveDate"
                  format="DD/MM/YYYY"
                  valueFormat="YYYY-MM-DD"
                  style="width:100%"
                  disabled
                >
                </a-date-picker>
              </a-col>
            </a-row>
            <br />
          </a-modal>
          <!-- popup view -->
        </div>
      </a-layout-content>
    </a-layout>
  </div>
</template>

<script>
import viewThuongPhat from "@/service/viewBonusPunish";

export default {
  name: "viewBonusPunish",
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
        pageIndex: 1,
        pageSize: 10,
        bonus: "",
        status: "",
        title: "",
        total: 0,
      },
      dataDetail: {
        title: "",
        reason: "",
        money: "",
        status: false,
        effectiveDate: "",
      },
      dataSourceTable: [],
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
          title: "Loại đơn",
          dataIndex: "bonus",
          key: "bonus",
          width: 150,
          scopedSlots: { customRender: "bonus" },
        },
        {
          title: "Ngày hiệu lực",
          dataIndex: "effectiveDate",
          key: "effectiveDate",
          width: 150,
          scopedSlots: { customRender: "effectiveDate" },
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
      titleView: "",
    };
  },
  created() {
    this.submitSearch();
  },
  watch: {
    urlState(newValue) {
      if (newValue.indexOf("/viewbonuspunish") != -1) {
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
      viewThuongPhat
        .getBonusAndPunish(this.dataSearch)
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
      viewThuongPhat
        .getBonusAndPunish(this.dataSearch)
        .then((response) => {
          this.dataSourceTable = response.data.data;
          this.dataSearch.total = response.data.total;
          this.pagination.total = response.data.total;
        })
        .catch((e) => {
          console.log(e);
        });
    },

    checkType(record) {
      if (record.bonus == true) {
        this.titleView = "Đơn Thưởng";
      } else {
        this.titleView = "Đơn Phạt";
      }
      this.dataDetail.title = record.title;
      this.dataDetail.reason = record.reason;
      this.dataDetail.money = record.money;
      this.dataDetail.status = record.status;
      this.dataDetail.effectiveDate = record.effectiveDate;
      this.visibleView = true;
    },
    handelCancel() {
      this.visibleView = false;
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