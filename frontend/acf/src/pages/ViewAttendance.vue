<template>
  <div class="viewattendance">
    <a-layout :style="{ background: 'white' }">
      <a-layout-content :style="{ margin: '30px 16px 0' }">
        <div
          :style="{
            minHeight: '360px',
            background: 'white',
          }"
        >
       <a-back-top :style="{ width: '5vh', height: '15vh' }" />
          <!-- menu trên -->
          <a-input
            v-model="dataSearch.name"
            placeholder="Tên nhân viên"
            style="width: 15%"
            :style="{'margin-right': '5px'}"
          />
          <a-range-picker
            @change="search"
            v-model="dataSearch.date"
            :placeholder="['Ngày bắt đầu', 'Ngày kết thúc']"
            format="DD/MM/YYYY"
            :style="{'margin-right': '10px'}"
          />
          Loại chấm công:
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
            :style="{'margin-left': '5px'}"
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
          <a-button
            type="primary"
            @click="submitExport"
            :style="{ 'margin-left': '5px' }"
          >
            Xuất File
            <font-awesome-icon
              :icon="['fas', 'download']"
              :style="{ 'margin-left': '10px' }"
            />
          </a-button>

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
                    <a-button
                      id="edit"
                      v-if="showEdit(record)"
                      @click="showModalEdit(record)"
                    >
                      <font-awesome-icon :icon="['fas', 'edit']" />
                    </a-button>
                  </a-col>
                </a-row>
              </template>
            </a-table>
          </div>
        </div>

        <!-- popup edit -->
        <a-modal v-model="visibleEdit" title="Chỉnh sửa điểm danh">
          <template slot="footer">
            <a-button key="back" @click="handleCancel"> Hủy </a-button>
            <a-button
              key="submit"
              type="primary"
              :loading="loading"
              @click="checkFormUpdate"
            >
              Lưu
            </a-button>
          </template>
          <a-form-model>
            <span style="color: red">*</span> Họ và tên :
            <a-input @change="inputName" v-model="nameEdit" disabled />
            <div style="color: red" v-if="checkDataInputName.show">
              {{ checkDataInputName.message }}
            </div>
            <span style="color: red">*</span> Số công :
            <a-select
              @change="inputNumber"
              v-model="dataEdit.type"
              style="width: 100%"
            >
              <a-select-option key="1"> Cả ngày </a-select-option>
              <a-select-option key="0.5"> Nửa ngày </a-select-option>
              <a-select-option key="0"> Nghỉ </a-select-option>
            </a-select>
            <div style="color: red" v-if="checkDataInputNumber.show">
              {{ checkDataInputNumber.message }}
            </div>
            <a-form-model-item label="Ghi chú">
              <a-textarea
                v-model="dataEdit.note"
                placeholder="Nhập ghi chú"
                :auto-size="{ minRows: 2, maxRows: 6 }"
              />
            </a-form-model-item>
          </a-form-model>
        </a-modal>
        <!-- popup edit -->

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
    </a-layout>
  </div>
</template>
 <script>
import attendanceService from "@/service/attendanceService.js";
import moment from "moment";
export default {
  name: "Attendance",
  components: {
  },
  data() {
    return {
      loading: false,
      dataPriviewExcel: [],
      visiblePriviewExport: false,
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
          title: "STT",
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
      checkDataInputName: {
        show: false,
        message: "",
      },
      checkDataInputNumber: {
        show: false,
        message: "",
      },
    };
  },
  watch: {
    urlState(newValue) {
      if (newValue.indexOf("/viewattendance") != -1) {
        this.getDate();
      }
    },
  },
  computed: {
    urlState() {
      return this.$store.state.url;
    },
  },
  created() {
    this.getDate();
  },
  methods: {
    showEdit(record) {
      let date = record.date.split("-")[2];
      if (parseInt(date) >= 10) {
        let dateNow = moment();
        let dateLast = moment(record.date).add(1, "months").set("date", 10);
        if (dateNow < dateLast) {
          return true;
        } else {
          return false;
        }
      } else {
        let dateNow1 = moment();
        let dateLast1 = moment(record.date).set("date", 10);
        if (dateNow1 < dateLast1) {
          return true;
        } else {
          return false;
        }
      }
    },
    getDate() {
      let datex = new Date();
      let month =
        (datex.getMonth() + 1 + "").length == 1
          ? "0" + datex.getMonth() + 1
          : datex.getMonth() + 1;
      let date =
        (datex.getDate() + "").length == 1
          ? "0" + datex.getDate()
          : datex.getDate();
      this.dataSearch.date = [
        datex.getFullYear() + "-" + month + "-" + date,
        datex.getFullYear() + "-" + month + "-" + date,
      ];
      this.search();
    },
    handleCancelPriview() {
      this.visiblePriviewExport = false;
    },
    changeExportNote() {},
    submitExport() {
      this.dataExport.type = "1";
      this.dataExport.note = "true";
      this.dataExport.dataSearch = this.dataSearch;
      this.visibleExport = false;
      attendanceService
        .downExcel(this.dataExport)
        .then((response) => {
          let  url = window.URL.createObjectURL(new Blob([response.data]));
          let link = document.createElement("a");
          link.href = url;
          link.setAttribute("download", "Attendance.xlsx");
          document.body.appendChild(link);
          link.click();
          URL.revokeObjectURL(link.href);
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
    checkFormUpdate() {
      let check = true;
      if (this.nameEdit != null && this.nameEdit.trim() != "") {
        this.checkDataInputName.show = false;
        this.checkDataInputName.message = "";
      } else {
        check = false;
        this.checkDataInputName.show = true;
        this.checkDataInputName.message = "Bạn phải điền họ và tên";
      }

      if (this.dataEdit.type != null && this.dataEdit.type.length != 0) {
        this.checkDataInputNumber.show = false;
        this.checkDataInputNumber.message = "";
      } else {
        check = false;
        this.checkDataInputNumber.show = true;
        this.checkDataInputNumber.message = "Bạn phải điền chức vụ";
      }

      if (check) {
        this.submitUpdate();
      }
    },
    submitUpdate() {
      this.loading = true;
      attendanceService
        .updateAttendance(this.dataEdit)
        .then((response) => {
          this.searchAttendance();
          if (response.data.data) {
            this.notifi("success", "Viết ghi chú");
          }
          this.loading = false;
          this.handleCancel();
        })
        .catch((e) => {
          console.log(e);
          this.loading = false;
          this.handleCancel();
        });
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
      this.dataSearch.name = this.dataSearch.name.trim();
      this.dataSearch.note = this.dataSearch.note.trim();
      this.dataSearch.pageIndex = 1;
      this.dataSearch.total = 0;
      this.searchAttendance();
    },
    inputName() {
      if (this.nameEdit != null && this.nameEdit.trim() != "") {
        this.checkDataInputName.show = false;
        this.checkDataInputName.message = "";
      } else {
        this.checkDataInputName.show = true;
        this.checkDataInputName.message = "Bạn phải điền họ và tên";
      }
    },
    inputNumber() {
      if (this.dataEdit.type != null && this.dataEdit.type.length != 0) {
        this.checkDataInputNumber.show = false;
        this.checkDataInputNumber.message = "";
      } else {
        this.checkDataInputNumber.show = true;
        this.checkDataInputNumber.message = "Bạn phải điền họ và tên";
      }
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
.attendancetype {
  width: 636px;
  text-align: left;
  margin-left: 0px;
}
/* button icon */
#delete {
  background-color: rgb(255, 0, 0);
  color: white;
}
#delete:hover {
  background-color: rgba(233, 15, 15, 0.863);
  color: white;
}
#edit {
  background-color: rgb(10, 208, 243);
  color: white;
}
#edit:hover {
  background-color: rgb(0, 181, 253);
  color: white;
}
#user {
  background-color: rgb(76, 238, 12);
  color: white;
}
#user:hover {
  background-color: rgb(42, 253, 0);
  color: white;
}
</style>