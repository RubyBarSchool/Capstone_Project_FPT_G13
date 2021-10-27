<template>
  <div class="viewattendance">
    <a-layout :style="{ background: 'white' }">
      <Header />
      <a-layout-content :style="{ margin: '30px 16px 0' }">
        <div
          :style="{
            minHeight: '360px',
            background: 'white',
          }"
        >
          <a-back-top>
            <div class="ant-back-top-inner">
              <font-awesome-icon
                :icon="['fas', 'arrow-alt-circle-up']"
                :style="{ width: '100px', height: '50px' }"
              />
            </div>
          </a-back-top>
          <!-- menu trên -->
          <a-row type="flex">
            <a-col flex="auto">
              <a-input
                v-model="dataSearch.name"
                placeholder="Tên  nhân viên"
                style="width: 15%"
              />
              <a-range-picker
                @change="search"
                v-model="dataSearch.date"
                :placeholder="['Ngày bắt đầu', 'Ngày kết thúc']"
                format="DD/MM/YYYY"
              />
              <a-select
                v-model="dataSearch.type"
                @change="search"
                style="width: 10%"
              >
                <a-select-option key=""> Tất Cả </a-select-option>
                <a-select-option key="1"> Cả ngày </a-select-option>
                <a-select-option key="0.5"> Nửa ngày </a-select-option>
                <a-select-option key="0"> Nghỉ </a-select-option>
              </a-select>
              <a-input
                v-model="dataSearch.note"
                placeholder="Ghi Chú"
                style="width: 15%"
              />
              <a-button
                type="primary"
                @click="search"
                :style="{ 'margin-left': '5px' }"
              >
                <font-awesome-icon
                  :icon="['fas', 'search']"
                  :style="{ 'margin-right': '5px' }"
                />
                Tìm Kiếm
              </a-button>
            </a-col>
            <a-col flex="100px">
              <a-button type="primary" @click="showExport">
                Xuất File
                <font-awesome-icon
                  :icon="['fas', 'download']"
                  :style="{ 'margin-left': '10px' }"
                />
              </a-button>
            </a-col>
          </a-row>

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
              <template slot="nameEmpl" slot-scope="text, record">
                {{ record.nameEmpl }}
              </template>
              <template slot="type" slot-scope="text, record">
                <a-tag v-if="record.type == 1" color="green">
                  {{ record.type }}
                </a-tag>
                <a-tag v-else-if="record.type == 0.5" color="orange">
                  {{ record.type }}
                </a-tag>
                <a-tag v-else color="red">
                  {{ record.type }}
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
              <template slot="action" slot-scope="text, record">
                <a-row>
                  <a-col :span="8">
                    <a-button id="edit" @click="showModalEdit(record)">
                      <font-awesome-icon :icon="['fas', 'edit']" />
                    </a-button>
                  </a-col>
                </a-row>
              </template>
            </a-table>
          </div>
        </div>

        <a-modal v-model="visibleEdit" title="Chỉnh sửa điểm danh">
          <template slot="footer">
            <a-button key="back" @click="handleCancel"> Hủy </a-button>
            <a-button key="submit" type="primary" @click="submitUpdate">
              Lưu
            </a-button>
          </template>
          <a-form-model>
            <a-form-model-item label="Họ Và Tên">
              <a-input v-model="nameEdit" disabled />
            </a-form-model-item>
            <a-form-model-item label="Số công">
              <a-select v-model="dataEdit.type" style="width: 100%">
                <a-select-option key="1"> Cả ngày </a-select-option>
                <a-select-option key="0.5"> Nửa ngày </a-select-option>
                <a-select-option key="0"> Nghỉ </a-select-option>
              </a-select>
            </a-form-model-item>
            <a-form-model-item label="Note">
              <a-textarea
                v-model="dataEdit.note"
                placeholder="Nhập ghi chú"
                :auto-size="{ minRows: 2, maxRows: 6 }"
              />
            </a-form-model-item>
          </a-form-model>
        </a-modal>

        <!-- popup tùy chỉnh xuất -->
        <a-modal v-model="visibleExport" title="Tùy Chỉnh Xuất">
          <template slot="footer">
            <a-button key="back" @click="handleCancel"> Hủy </a-button>
            <a-button type="danger" @click="priviewExcel">
              Xem Trước
              <font-awesome-icon
                :icon="['fas', 'eye']"
                :style="{ 'margin-left': '5px' }"
              />
            </a-button>
            <a-button key="submit" type="primary" @click="submitExport">
              Xuất File
              <font-awesome-icon
                :icon="['fas', 'download']"
                :style="{ 'margin-left': '5px' }"
              />
            </a-button>
          </template>
          <a-form-model>
            <a-form-model-item label="Loại xuất">
              <a-select
                v-model="dataExport.type"
                @change="changeTypeExport"
                style="width: 100%"
              >
                <a-select-option key="1"> Chi tiết </a-select-option>
                <a-select-option key="0"> Gộp ngày </a-select-option>
              </a-select>
            </a-form-model-item>

            <a-form-model-item label="Xuất ghi chú" @change="changeExportNote">
              <a-radio-group v-model="dataExport.note">
                <a-radio-button value="true"> Có </a-radio-button>
                <a-radio-button value="false"> Không </a-radio-button>
              </a-radio-group>
            </a-form-model-item>
          </a-form-model>
        </a-modal>

        <!-- preview excel -->
        <a-modal
          v-model="visiblePriviewExport"
          height="100%"
          width="80%"
          title="Xem Trước File Excel"
        >
          <template slot="footer">
            <a-button key="back" @click="handleCancelPriview"> Hủy </a-button>
          </template>
          <template>
            <ejs-spreadsheet>
              <e-sheets>
                <e-sheet
                  v-for="(datax, index) in dataPriviewExcel"
                  :key="index"
                >
                  <e-ranges>
                    <e-range :dataSource="datax"></e-range>
                  </e-ranges>
                  <e-columns>
                    <e-column :width="100"></e-column>
                    <e-column :width="200"></e-column>
                    <e-column :width="200"></e-column>
                    <e-column :width="200"></e-column>
                    <e-column :width="200"></e-column>
                    <e-column :width="200"></e-column>
                  </e-columns>
                </e-sheet>
              </e-sheets>
            </ejs-spreadsheet>
          </template>
        </a-modal>
      </a-layout-content>
      <Footer />
    </a-layout>
  </div>
</template>
 <script>
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";
import attendanceService from "@/service/attendanceService.js";

const dataSortCommon = [
  { key: "name", value: "Họ và tên" },
  { key: "1", value: "Đi làm" },
  { key: "0.5", value: "Nghỉ" },
  { key: "0", value: "Nửa công" },
];
export default {
  name: "Attendance",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      dataPriviewExcel: [],
      visiblePriviewExport: false,
      visibleExport: false,
      visibleEdit: false,
      nameEdit: "",
      dataSourceTable: [],
      dataExport: {
        dataSearch: {},
        type: "1",
        note: "true",
      },
      dataSearch: {
        name: "",
        date: [],
        type: "",
        note: "",
        pageIndex: 1,
        pageSize: 10,
        total: 0,
      },
      dataEdit: {
        id: "",
        type: "1",
        note: "",
      },
      pagination: {
        current: 1,
        pageSize: 10,
        total: 0,
      },
      columns: [
        {
          title: "ID",
          width: 100,
          dataIndex: "id",
          key: "id",
          fixed: "left",
        },
        {
          title: "Họ Và Tên",
          dataIndex: "nameEmpl",
          key: "nameEmpl",
          width: 150,
          scopedSlots: { customRender: "nameEmpl" },
        },
        {
          title: "Ngày",
          dataIndex: "date",
          key: "date",
          width: 150,
          scopedSlots: { customRender: "date" },
        },
        {
          title: "Số công",
          dataIndex: "type",
          key: "type",
          width: 150,
          scopedSlots: { customRender: "type" },
        },
        {
          title: "Ghi chú",
          dataIndex: "note",
          key: "note",
          width: 150,
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
  computed: {},
  created() {
    this.search();
  },
  methods: {
    handleCancelPriview() {
      this.visiblePriviewExport = false;
    },
    priviewExcel() {
      this.dataPriviewExcel = [];
      this.dataExport.dataSearch = this.dataSearch;
      attendanceService
        .priviewExcel(this.dataExport)
        .then((response) => {
          this.dataPriviewExcel = response.data.data;
          this.visiblePriviewExport = true;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    changeTypeExport(value) {
      if (value == "1") {
        this.dataSort.push(dataSortCommon[0]);
      } else {
        this.dataSort = dataSortCommon;
      }
    },
    changeExportNote() {},
    showExport() {
      this.visibleExport = true;
      this.dataExport.dataSearch = {};
      this.dataExport.type = "1";
      this.dataExport.note = "true";
    },
    submitExport() {
      this.dataExport.dataSearch = this.dataSearch;
      this.visibleExport = false;
      attendanceService
        .downExcel(this.dataExport)
        .then((response) => {
          const url = window.URL.createObjectURL(new Blob([response.data]));
          const link = document.createElement("a");
          link.href = url;
          link.setAttribute("download", "Attendance.xlsx");
          document.body.appendChild(link);
          link.click();
        })
        .catch((e) => {
          console.log(e);
        });
    },
    showModalEdit(record) {
      this.visibleEdit = true;
      this.dataEdit.id = record.id;
      this.dataEdit.type = record.type;
      this.dataEdit.note = record.note;
      this.nameEdit = record.nameEmpl;
    },
    submitUpdate() {
      attendanceService
        .updateAttendance(this.dataEdit)
        .then((response) => {
          this.searchAttendance();
          if (response.data.data) {
            this.notifi("success", "Cập nhật thành công");
          }
        })
        .catch((e) => {
          console.log(e);
        });
      this.handleCancel();
    },
    notifi(task, text) {
      this.$notification[task]({
        message: "Thông báo",
        description: text + " thành công",
      });
    },
    handleCancel() {
      this.visibleExport = false;
      this.visibleEdit = false;
      this.dataEdit.id = "";
      this.dataEdit.type = "1";
      this.dataEdit.note = "";
      this.nameEdit = "";
    },
    handleTableChange(pagination) {
      this.dataSearch.pageIndex = pagination.current;
      this.pagination = pagination;
      this.searchAttendance();
    },
    searchAttendance() {
      attendanceService
        .searchAttendance(this.dataSearch)
        .then((response) => {
          this.dataSourceTable = response.data.data;
          this.dataSearch.total = response.data.total;
          this.pagination.total = response.data.total;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    search() {
      this.dataSearch.pageIndex = 1;
      this.dataSearch.total = 0;
      this.searchAttendance();
    },
  },
};
</script>

<style scoped>
@import "../../node_modules/@syncfusion/ej2-vue-spreadsheet/styles/material.css";
@import "../../node_modules/@syncfusion/ej2-base/styles/material.css";
@import "../../node_modules/@syncfusion/ej2-buttons/styles/material.css";
@import "../../node_modules/@syncfusion/ej2-dropdowns/styles/material.css";
@import "../../node_modules/@syncfusion/ej2-inputs/styles/material.css";
@import "../../node_modules/@syncfusion/ej2-navigations/styles/material.css";
@import "../../node_modules/@syncfusion/ej2-popups/styles/material.css";
@import "../../node_modules/@syncfusion/ej2-splitbuttons/styles/material.css";
@import "../../node_modules/@syncfusion/ej2-grids/styles/material.css";
@import "../../node_modules/@syncfusion/ej2-spreadsheet/styles/material.css";

.e-sheet-panel {
  height: 100% !important;
}
/* back top */
.ant-back-top-inner {
  color: rgb(241, 237, 237);
  text-align: center;
}
.attendancetype {
  width: 636px;
  text-align: left;
  margin-left: 0px;
}
/* button icon */
#delete {
  background-color: red;
  color: white;
}
#edit {
  background-color: rgb(17, 196, 228);
  color: white;
}
#user {
  background-color: rgb(24, 216, 24);
  color: white;
}

</style>