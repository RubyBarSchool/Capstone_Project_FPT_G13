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
        <a-input placeholder="Họ và tên" style="width: 150px" />
        <a-input placeholder="Chức vụ" style="width: 150px" />
        <a-date-picker placeholder="Chọn ngày" />
        <a-button
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
        <a-table
          :columns="columns"
          :data-source="data"
          :style="{ 'padding-top': '10px' }"
        >
          <a slot="name" slot-scope="text">{{ text }}</a>
        </a-table>
      </a-layout-content>
      <Footer />
    </a-layout>
  </div>
</template>

<script>
const columns = [
  {
    title: "Số thứ tự",
    dataIndex: "number",
    key: "number",
    scopedSlots: { customRender: "number" },
  },
  {
    title: "Tháng",
    dataIndex: "month",
    key: "month",
    ellipsis: true,
  },
  {
    title: "Họ và tên",
    dataIndex: "name",
    key: "name",
    ellipsis: true,
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

const data = [
  {
    number: "1",
    month: "12",
    name: "Le Hoang Anh",
    role: "Truong phong",
    workno: "1",
    salary: "10000000000",
    bonus: "10000000000",
    punish: "10000",
    advance: "30",
    total: "10000000000",
    status: "Nháp",
  },
  {
    number: "2",
    month: "12",
    name: "Le Hoang Anh",
    role: "Truong phong",
    workno: "1",
    salary: "10000000000",
    bonus: "10000000000",
    punish: "10000",
    advance: "30",
    total: "10000000000",
    status: "Nháp",
  },
  {
    number: "3",
    month: "12",
    name: "Le Hoang Anh",
    role: "Truong phong",
    workno: "1",
    salary: "10000000000",
    bonus: "10000000000",
    punish: "10000",
    advance: "30",
    total: "10000000000",
    status: "Nháp",
  },
  {
    number: "4",
    month: "12",
    name: "Le Hoang Anh",
    role: "Truong phong",
    workno: "1",
    salary: "10000000000",
    bonus: "10000000000",
    punish: "10000",
    advance: "30",
    total: "10000000000",
    status: "Nháp",
  },
  {
    number: "5",
    month: "12",
    name: "Le Hoang Anh",
    role: "Truong phong",
    workno: "1",
    salary: "10000000000",
    bonus: "10000000000",
    punish: "10000",
    advance: "30",
    total: "10000000000",
    status: "Nháp",
  },
];

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
      data,
      columns,
    };
  },
};
</script>

<style>
</style>