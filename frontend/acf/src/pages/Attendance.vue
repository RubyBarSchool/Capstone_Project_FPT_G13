<template>
  <div class="attendance">
    <a-layout :style="{ background: 'white' }">
      <a-layout-content :style="{ margin: '30px 16px 0' }">
        <div
          :style="{
            padding: '24px',
            background: 'white',
            minHeight: '360px',
          }"
        >
          <a-back-top :style="{ width: '5vh', height: '15vh' }" />
          <div class="container">
            <div class="row">
              <div class="col">
                <a-form-model-item label="Loại chấm công">
                  <a-select v-model="typeAttendance" style="width: 100%">
                    <a-select-option key="1"> Cả ngày </a-select-option>
                    <a-select-option key="0.5"> Nửa ngày </a-select-option>
                    <a-select-option key="0"> Nghỉ </a-select-option>
                  </a-select>
                </a-form-model-item>

                <!-- Table content -->
                <a-table
                  :columns="columns"
                  :data-source="dataTable"
                  :pagination="false"
                  :scroll="{ x: 1000 }"
                  :rowKey="
                    (record, index) => {
                      return record.id;
                    }
                  "
                  :row-selection="{
                    selectedRowKeys: selectedRowKeys,
                    selectedRows: selectedRows,
                    onChange: onSelectChange,
                  }"
                  @change="handleTableChange"
                >
                  <template slot="note" slot-scope="text, record">
                    <editable-cell
                      :text="text"
                      @change="onCellChange(record.id, $event)"
                    />
                  </template>
                </a-table>
                <!-- Table content -->
                <br />
                <a-row type="flex">
                  <a-col flex="435px"></a-col>
                  <a-col flex="auto">
                    <a-button
                      type="primary"
                      :disabled="disableSave"
                      @click="saveAttendance"
                    >
                      Lưu điểm danh
                    </a-button>
                    <a-button
                      type="primary"
                      @click="reset"
                      :style="{ 'margin-left': '6px' }"
                    >
                      Cài lại
                    </a-button>
                  </a-col>
                </a-row>
              </div>
            </div>
          </div>
        </div>
      </a-layout-content>
    </a-layout>
  </div>
</template>
 <script>
import attendanceService from "@/service/attendanceService.js";
import EditableCell from "@/components/EditableCell.vue";
export default {
  name: "Attendance",
  components: {
    EditableCell,
  },
  data() {
    return {
      pagination: {
        current: 1,
        pageSize: 10,
        total: 0,
      },
      dataGetEmployee: {
        date: "",
        pageIndex: 1,
        pageSize: 10,
      },
      dataTable: [],
      typeAttendance: "1",
      columns: [
        {
          title: "STT",
          width: 100,
          dataIndex: "id",
          key: "id",
          fixed: "left",
        },
        {
          title: "Nhân viên",
          dataIndex: "name",
          key: "name``",
          width: 150,
        },
        {
          title: "Ghi chú",
          dataIndex: "note",
          key: "note",
          width: 150,
          scopedSlots: { customRender: "note" },
        },
      ],
      dataForm: {
        date: "",
        type: "",
        attendance: [],
      },
      disableSave: true,
      dataNote: [],
      selectedRowKeys: [],
      selectedRows: [],
    };
  },
  computed: {},
  created() {
    this.getDate();
    this.onChangeDate();
  },
  methods: {
    reset() {
      this.reloadSelect();
      this.typeAttendance = "1";
      this.disableSave = true;
      this.getDate();
      this.onChangeDate();
    },
    reloadSelect() {
      this.selectedRowKeys = [];
      this.selectedRows = [];
    },
    onSelectChange(selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys;
      this.selectedRows = selectedRows;
      this.dataForm.attendance = [];
      for (let i = 0; i < selectedRows.length; i++) {
        let data = {
          id: selectedRows[i].id,
          note: "",
        };
        for (let j = 0; j < this.dataNote.length; j++) {
          if (this.dataNote[j].id == selectedRows[i].id) {
            data.note = this.dataNote[j].note;
            break;
          }
        }
        this.dataForm.attendance.push(data);
      }
      if (selectedRowKeys.length != 0) {
        this.disableSave = false;
      } else {
        this.disableSave = true;
      }
    },
    onCellChange(key, value) {
      for (let i = 0; i < this.dataForm.attendance.length; i++) {
        if (this.dataForm.attendance[i].id == key) {
          this.dataForm.attendance[i].note = value;
          break;
        }
      }
      let check = true;
      for (let i = 0; i < this.dataNote.length; i++) {
        if (key == this.dataNote[i].id) {
          this.dataNote[i].note = value;
          check = false;
          break;
        }
      }
      if (check) {
        var data = {
          id: key,
          note: value,
        };
        this.dataNote.push(data);
      }
    },
    saveAttendance() {
      this.dataForm.date = this.dataGetEmployee.date;
      this.dataForm.type = this.typeAttendance;
      attendanceService
        .addAttendance(this.dataForm)
        .then(() => {
          this.onChangeDate();
        })
        .catch((e) => {
          console.log(e);
        });
    },
    handleTableChange(pagination) {
      this.dataGetEmployee.pageIndex = pagination.current;
      this.pagination = pagination;
      this.onChangeDate();
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
      this.dataGetEmployee.date =
        datex.getFullYear() + "-" + month + "-" + date;
    },
    onChangeDate() {
      this.reloadSelect();
      this.dataTable = [];
      attendanceService
        .getEmployee(this.dataGetEmployee)
        .then((response) => {
          this.dataTable = response.data.data;
          this.pagination.total = response.data.total;
        })
        .catch((e) => {
          console.log(e);
        });
    },
  },
};
</script>

<style scoped>
</style>