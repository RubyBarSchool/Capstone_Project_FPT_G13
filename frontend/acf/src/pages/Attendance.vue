<template>
  <div class="attendance">
    <a-layout>
      <Header />
      <a-layout-content :style="{ margin: '24px 16px 0' }">
        <div
          :style="{
            padding: '24px',
            background: '#fff',
            minHeight: '360px',
            height: '560px',
          }"
        >
          <div class="container">
            <div class="row">
              <div class="col">
                <div
                  :style="{
                    width: '500px',
                    border: '1px solid #d9d9d9',
                    borderRadius: '4px',
                  }"
                >
                  <a-calendar
                    :fullscreen="false"
                    @panelChange="onPanelChange"
                  />
                </div>
              </div>
              <div class="col">
                <a-form-model-item label="Date">
                  <a-input />
                </a-form-model-item>
                <a-form-model-item label="Attendance type">
                  <a-dropdown class="attendancetype">
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
                    <a-button style="margin-left: 8px"> Fullday </a-button>
                  </a-dropdown>
                </a-form-model-item>
                <a-table
                  :columns="columns"
                  :data-source="data"
                  :row-selection="rowSelection"
                  :expanded-row-keys.sync="expandedRowKeys"
                />
                <a-row type="flex">
                  <a-col flex="442px"></a-col>
                  <a-col flex="auto">
                    <a-button type="primary"> Save Attendance </a-button>
                    <a-button type="primary"> Reset </a-button>
                  </a-col>
                </a-row>
              </div>
            </div>
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
    title: "Name",
    dataIndex: "name",
    key: "name",
  },
];

const data = [
  {
    key: 1,
    name: "John Brown sr",
  },
  {
    key: 2,
    name: "Joe Black",
  },
];

const rowSelection = {
  onChange: (selectedRowKeys, selectedRows) => {
    console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
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