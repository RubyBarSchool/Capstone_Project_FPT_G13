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
            v-model="dataSearch.title"
            placeholder="Tiêu đề"
            style="width: 150px"
          />
          <a-select
            v-model="dataSearch.bonus"
            @change="submitSearch"
            placeholder="Loại đơn"
            style="width: 150px"
          >
            <a-select-option value=""> Tất cả </a-select-option>
            <a-select-option value="false"> Phạt </a-select-option>
            <a-select-option value="true"> Thưởng </a-select-option>
          </a-select>
          <a-select
            v-model="dataSearch.status"
            @change="submitSearch"
            placeholder="Trạng thái"
            style="width: 150px"
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
              :rowKey="
                (record, index) => {
                  return index;
                }
              "
              @change="handleTableChange"
            >
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
            <a-form-model>
              <a-form-model-item label="Tiêu đề">
                <a-input v-model="dataDetail.title" disabled />
              </a-form-model-item>
              <a-form-model-item label="Lý do">
                <a-textarea v-model="dataDetail.reason" disabled :row="4" />
              </a-form-model-item>
              <a-form-model-item label="Số tiền">
                <a-input v-model="dataDetail.money" disabled />
              </a-form-model-item>
              <a-form-model-item label="Trạng thái">
                <a-radio-group
                  name="radioGroup"
                  disabled
                  v-model="dataDetail.status"
                >
                  <a-radio :value="false"> Chờ duyệt </a-radio>
                  <a-radio :value="true"> Đã duyệt </a-radio>
                </a-radio-group>
              </a-form-model-item>
              <a-form-model-item label="Ngày hiệu lực">
                <a-date-picker
                  v-model="dataDetail.effectiveDate"
                  format="YYYY-MM-DD"
                  valueFormat="YYYY-MM-DD"
                  disabled
                >
                </a-date-picker>
              </a-form-model-item>
            </a-form-model>
          </a-modal>
          <!-- popup view -->
        </div>
      </a-layout-content>
    </a-layout>
  </div>
</template>

<script>
import viewThuongPhat from "@/service/viewThuongPhat";

export default {
  name: "viewThuongPhat",
  components: {
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
        },
        {
          title: "Trạng thái",
          dataIndex: "status",
          key: "status",
          width: 150,
          scopedSlots: { customRender: "status" },
        },
        {
          title: "Hành động",
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
        this.titleView = "Đơn thưởng";
      } else {
        this.titleView = "Đơn phạt";
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