<template>
  <div class="viewattendance">
    <a-layout :style="{ background: 'white' }">
      <Header />
      <a-layout-content :style="{ margin: '24px 16px 0' }">
        <div
          :style="{
            minHeight: '360px',
            background: 'white',
          }"
        >
          <a-row type="flex">
            <a-col flex="auto">
              <a-input placeholder="Tên tài khoản" style="width: 150px" />
              <a-range-picker
                :placeholder="['Ngày bắt đầu', 'Ngày kết thúc']"
                :show-time="{ format: 'DD/MM/YYYY' }"
                format="DD/MM/YYYY"
              />
              <a-dropdown-button>
                Fullday
                <a-menu slot="overlay">
                  <a-menu-item key="1">
                    <a-icon type="user" />1st menu item
                  </a-menu-item>
                  <a-menu-item key="2">
                    <a-icon type="user" />2nd menu item
                  </a-menu-item>
                  <a-menu-item key="3">
                    <a-icon type="user" />3rd item
                  </a-menu-item>
                </a-menu>
                <a-icon slot="icon" type="calendar" />
              </a-dropdown-button>
              <a-button type="primary" icon="search"> Tìm kiếm </a-button>
            </a-col>
            <a-col flex="100px">
              <a-button type="primary"> Xuất excel </a-button>
            </a-col>
          </a-row>
          <div :style="{ 'padding-top': '10px' }">
            <a-table
              :columns="columns"
              :data-source="data"
              :expanded-row-keys.sync="expandedRowKeys"
            />
          </div>
        </div>
      </a-layout-content>
      <Footer />
    </a-layout>
  </div>
</template>
 <script>
const columns = [
  {
    title: "Date",
    dataIndex: "date",
    key: "date",
  },
  {
    title: "Name",
    dataIndex: "name",
    key: "name",
  },
  {
    title: "Attendance type(Day)",
    dataIndex: "attendance",
    key: "attendance",
  },
  {
    title: "Note",
    dataIndex: "note",
    key: "note",
  },
  {
    title: "Action",
    dataIndex: "action",
    key: "action",
  },
];

const data = [
  {
    key: 1,
    date: "1/1/1111",
    name: "John Brown sr",
    attendance: "a",
    note: "a",
    action: "a",
  },
  {
    key: 2,
    date: "1/1/1111",
    name: "John Brown sr",
    attendance: "a",
    note: "a",
    action: "a",
  },
  {
    key: 3,
    date: "1/1/1111",
    name: "John Brown sr",
    attendance: "a",
    note: "a",
    action: "a",
  },
];

const rowSelection = {
  onChange: (selectedRowKeys, selectedRows) => {
    console.log(
      `selectedRowKeys: ${selectedRowKeys}`,
      "selectedRows: ",
      selectedRows
    );
  },
  onSelect: (record, selected, selectedRows) => {
    console.log(record, selected, selectedRows);
  },
  onSelectAll: (selected, selectedRows, changeRows) => {
    console.log(selected, selectedRows, changeRows);
  },
};

import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";

export default {
  name: "Attendance",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      data,
      columns,
      rowSelection,
      expandedRowKeys: [],
    };
  },
  methods: {
    onPanelChange(value, mode) {
      console.log(value, mode);
    },
  },
};
</script>

<style scoped>
.attendancetype {
  width: 636px;
  text-align: left;
  margin-left: 0px;
}
</style>