<template>
  <div class="attendance">
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
          <a-table :columns="columns" :data-source="data" >
            <a slot="action" href="javascript:;"><font-awesome-icon :icon="['fas', 'eye']" /></a>
          </a-table>
          <!-- table content -->
        </div>
      </a-layout-content>
      <Footer />
    </a-layout>
  </div>
</template>

<script>
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";
const columns = [
  { title: 'Số thứ tự', width: 150, dataIndex: 'number', key: 'number', fixed: 'left' },
  { title: 'Ngày hiệu lực', dataIndex: 'date', key: 'date',  },
  { title: 'Tiêu đề', dataIndex: 'title', key: 'title' },
  { title: 'Số tiền', dataIndex: 'money', key: 'money' },
  { title: 'Trạng thái', dataIndex: 'status', key: 'status' },
  { title: 'Loại', dataIndex: 'type', key: 'type' },
  {
    title: 'Action',
    key: 'operation',
    fixed: 'right',
    width: 100,
    scopedSlots: { customRender: 'action' },
  },
];

const data = [
  {
    key: '1',
    number: '1',
    date: '21/01/2021',
    title: 'New York Park',
    money: '10000000',
    status: 'Chấp nhận',
    type: 'Nháp'
  },
  {
    key: '2',
    number: '1',
    date: '21/01/2021',
    title: 'New York Park',
    money: '10000000',
    status: 'Chấp nhận',
    type: 'Nháp'
  },
  {
    key: '3',
    number: '1',
    date: '21/01/2021',
    title: 'New York Park',
    money: '10000000',
    status: 'Chấp nhận',
    type: 'Nháp'
  },
];

export default {
  name: "Home",
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