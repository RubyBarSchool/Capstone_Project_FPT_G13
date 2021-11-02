<template>
  <div class="viewluong">
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
        <a-tabs default-active-key="1">
          <!-- Hiện tại -->
          <a-tab-pane key="1">
            <span slot="tab"> <h6>Hiện tại</h6> </span>
            <a-table :columns="columns" :data-source="data">
              <a slot="name" slot-scope="text">{{ text }}</a>
            </a-table>
            <div class="container-fluid">
              <div class="row">
                <div class="col-6">
                  <h6>Thưởng</h6>
                  <a-table :columns="columns1" :data-source="data1">
                    <a slot="name" slot-scope="text">{{ text }}</a>
                  </a-table>
                </div>
                <div class="col-6">
                  <h6>Phạt</h6>
                  <a-table :columns="columns1" :data-source="data1">
                    <a slot="name" slot-scope="text">{{ text }}</a>
                  </a-table>
                </div>
              </div>
            </div>
          </a-tab-pane>

          <!-- Quá khứ -->
          <a-tab-pane key="2">
            <span slot="tab"> <h6>Quá khứ</h6> </span>
            <a-date-picker />
            <a-table
              :columns="columns"
              :data-source="data"
              :style="{ 'padding-top': '10px' }"
            >
              <a slot="name" slot-scope="text">{{ text }}</a>
            </a-table>
            <div class="container-fluid">
              <div class="row">
                <div class="col-6">
                  <h6>Thưởng</h6>
                  <a-table :columns="columns1" :data-source="data1">
                    <a slot="name" slot-scope="text">{{ text }}</a>
                  </a-table>
                </div>
                <div class="col-6">
                  <h6>Phạt</h6>
                  <a-table :columns="columns1" :data-source="data1">
                    <a slot="name" slot-scope="text">{{ text }}</a>
                  </a-table>
                </div>
              </div>
            </div>
          </a-tab-pane>
        </a-tabs>
      </a-layout-content>
      <Footer />
    </a-layout>
  </div>
</template>

<script>
const columns = [
  {
    title: "Họ và tên",
    dataIndex: "name",
    key: "name",
    scopedSlots: { customRender: "name" },
  },
  {
    title: "Chức vụ",
    dataIndex: "role",
    key: "role",
    ellipsis: true,
  },
  {
    title: "Số công",
    dataIndex: "workno",
    key: "workno",
    ellipsis: true,
  },
  {
    title: "Lương",
    dataIndex: "salary",
    key: "salary",
    ellipsis: true,
  },
  {
    title: "Thưởng",
    dataIndex: "bonus",
    key: "bonus",
    ellipsis: true,
  },
  {
    title: "Phạt",
    dataIndex: "punish",
    key: "punish",
    ellipsis: true,
  },
  {
    title: "Tạm ứng",
    dataIndex: "advance",
    key: "advance",
    ellipsis: true,
  },
  {
    title: "Tổng nhận",
    dataIndex: "total",
    key: "total",
    ellipsis: true,
  },
  {
    title: "Trạng thái",
    dataIndex: "status",
    key: "status",
    ellipsis: true,
  },
];

const columns1 = [
  {
    title: "Số thứ tự",
    dataIndex: "number",
    key: "number",
    scopedSlots: { customRender: "number" },
  },
  {
    title: "Ngày hiệu lực",
    dataIndex: "effectivedate",
    key: "effectivedate",
    ellipsis: true,
  },
  {
    title: "Lý do",
    dataIndex: "reason",
    key: "reason",
    ellipsis: true,
  },
  {
    title: "Số tiền",
    dataIndex: "money",
    key: "money",
    ellipsis: true,
  },
  {
    title: "Trạng thái",
    dataIndex: "status",
    key: "status",
    ellipsis: true,
  },
];

const data1 = [
  {
    number: "1",
    effectivedate: "01/01/2021",
    reason: "Ok",
    money: "1.500.000",
    status: "Nháp",
  },
];

const data = [
  {
    key: "1",
    name: "John Brown",
    role: "Quản lý",
    workno: "30",
    salary: "1.500.000",
    bonus: "500.000",
    punish: "500.000",
    advance: "500.000",
    total: "500.000",
    status: "Nháp",
  },
  {
    key: "2",
    name: "John Brown",
    role: "Quản lý",
    workno: "30",
    salary: "1.500.000",
    bonus: "500.000",
    punish: "500.000",
    advance: "500.000",
    total: "500.000",
    status: "Nháp",
  },
  {
    key: "3",
    name: "John Brown",
    role: "Quản lý",
    workno: "30",
    salary: "1.500.000",
    bonus: "500.000",
    punish: "500.000",
    advance: "500.000",
    total: "500.000",
    status: "Nháp",
  },
];

import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";
export default {
  name: "ViewLuong",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      data,
      columns,
      columns1,
      data1,
    };
  },
};
</script>

<style>
</style>