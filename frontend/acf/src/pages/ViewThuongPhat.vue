<template>
  <div class="viewThuongPhat">
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
          <a-input placeholder="Tiêu đề" style="width: 150px" />
          <a-select placeholder="Trạng thái" style="width: 150px">
            <a-select-option value="false"> Chờ duyệt </a-select-option>
            <a-select-option value="true"> Đã duyệt </a-select-option>
          </a-select>
          <a-date-picker format="YYYY-MM-DD" valueFormat="YYYY-MM-DD">
          </a-date-picker>
          <a-button type="primary" :style="{ 'margin-left': '5px' }">
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
              <template slot="effectiveDate" slot-scope="text, record">
                {{ record.effectiveDate }}
              </template>
              <template slot="titlee" slot-scope="text, record">
                {{ record.title }}
              </template>
              <template slot="money" slot-scope="text, record">
                {{ record.money }}
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
                  @click="checkType(
                    record.title,
                    record.reason,
                    record.money,
                    record.status,
                    record.effectiveDate)"
                  :style="{ width: '44.25px', 'margin-right': '100px' }"
                >
                  <font-awesome-icon :icon="['fas', 'eye']" />
                </a-button>
              </template>
            </a-table>
            <!-- table content -->
          </div>

          <!-- popup view -->
          <a-modal v-model="visibleView" title="Đơn thưởng">
            <template slot="footer">
              <a-button key="a" hidden></a-button>
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
                <a-input v-model="dataDetail.status" disabled />
              </a-form-model-item>
              <a-form-model-item label="Ngày hiệu lực">
                <a-input v-model="dataDetail.effectiveDate" disabled />
              </a-form-model-item>
            </a-form-model>
          </a-modal>
          <!-- popup view -->

           <!-- popup view -->
          <a-modal v-model="visibleView1" title="Đơn phạt">
            <template slot="footer">
              <a-button key="a" hidden></a-button>
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
                <a-input v-model="dataDetail.status" disabled />
              </a-form-model-item>
              <a-form-model-item label="Ngày hiệu lực">
                <a-input v-model="dataDetail.effectiveDate" disabled />
              </a-form-model-item>
            </a-form-model>
          </a-modal>
          <!-- popup view -->
        </div>
      </a-layout-content>
      <Footer />
    </a-layout>
  </div>
</template>

<script>
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";
import viewThuongPhat from "@/service/viewThuongPhat";

export default {
  name: "viewThuongPhat",
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
        pageIndex: 1,
        pageSize: 10,
        status: "",
        title: "",
        total: 0,
      },
      dataDetail: {
        title: "",
        reason: "",
        money: "",
        status: "",
        effectiveDate: "",
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
          title: "Ngày hiệu lực",
          dataIndex: "effectiveDate",
          key: "effectiveDate",
          width: 150,
          scopedSlots: { customRender: "effectiveDate" },
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
          title: "Loại",
          dataIndex: "bonus",
          key: "bonus",
          width: 150,
          scopedSlots: { customRender: "bonus" },
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
      visibleView1: false,
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
    showModelView(title, reason, money, status, effectiveDate){
      this.dataDetail.title = title;
      this.dataDetail.reason = reason;
      this.dataDetail.money = money;
      if(status == true){
          this.dataDetail.status = 'Thưởng';
      } else {
        this.dataDetail.status = 'Phạt';
      }
      this.dataDetail.effectiveDate = effectiveDate;
      this.visibleView = true;
    },

    showModelView1(title, reason, money, status, effectiveDate){
      this.dataDetail.title = title;
      this.dataDetail.reason = reason;
      this.dataDetail.money = money;
      if(status == true){
          this.dataDetail.status = 'Thưởng';
      } else {
        this.dataDetail.status = 'Phạt';
      }
      this.dataDetail.effectiveDate = effectiveDate;
      this.visibleView1 = true;
    },

    checkType(title, reason, money, status, effectiveDate){
      if(status == true){
        this.showModelView(title, reason, money, status, effectiveDate);
      } else {
        this.showModelView1(title, reason, money, status, effectiveDate);
      }
    }
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