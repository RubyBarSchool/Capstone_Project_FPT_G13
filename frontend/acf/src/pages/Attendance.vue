<template>
  <div class="attendance">
    <a-layout :style="{ background: 'white'}">
      <Header />
      <a-layout-content :style="{ margin: '24px 16px 0' }">
        <div
          :style="{
            padding: '24px',
            background: 'white',
            minHeight: '360px',
            height: '560px',
          }"
        >
          <div class="container">
            <div class="row">
              <div class="col">
                <a-date-picker
                  :open="true"
                  v-model="dataGetEmployee.date"
                  @change="onChangeDate"
                  format="YYYY-MM-DD"
                  valueFormat="YYYY-MM-DD"
                >
                  <a-icon slot="suffixIcon" type="smile" />
                </a-date-picker>
              </div>
              <div class="col">
                <a-form-model-item label="Attendance type">
                  <a-select v-model="typeAttendance" style="width: 120px">
                    <a-select-option key="1"> Cả ngày </a-select-option>
                    <a-select-option key="1/2"> Nửa ngày </a-select-option>
                    <a-select-option key="0"> Nghỉ </a-select-option>
                  </a-select>
                </a-form-model-item>

                <!-- Table content -->
                <a-table
                  :columns="columns"
                  :data-source="dataTable"
                  :pagination="pagination"
                  :rowKey="
                    (record, index) => {
                      return record.id;
                    }
                  "
                  :row-selection="rowSelection"
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

                <a-row type="flex">
                  <a-col flex="442px"></a-col>
                  <a-col flex="auto">
                    <a-button
                      type="primary"
                      :disabled="disableSave"
                      @click="saveAttendance"
                    >
                      Save Attendance
                    </a-button>
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
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";
import attendanceService from "@/service/attendanceService.js";
import EditableCell from "@/components/EditableCell.vue";
export default {
  name: "Attendance",
  components: {
    Header,
    Footer,
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
          title: "ID",
          width: 100,
          dataIndex: "id",
          key: "id",
        },
        {
          title: "Tên nhân viên",
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
    };
  },
  computed: {
    rowSelection() {
      return {
        onChange: (selectedRowKeys, selectedRows) => {
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
          console.log("DataNoteChangeSelect", this.dataNote);
          console.log("DataFormChangeSelect", this.dataForm);
        },
      };
    },
  },
  created() {
    this.getDate();
    this.onChangeDate();
  },
  methods: {
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
      console.log("DataNoteChangeCell", this.dataNote);
      console.log("DataFormChangeCell", this.dataForm);
    },
    saveAttendance() {
      this.dataForm.date = this.dataGetEmployee.date;
      this.dataForm.type = this.typeAttendance;
      attendanceService
        .addAttendance(this.dataForm)
        .then((response) => {
          console.log(response);
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
      var datex = new Date();
      this.dataGetEmployee.date =
        datex.getFullYear() +
        "-" +
        (datex.getMonth() + 1) +
        "-" +
        datex.getDate();
    },
    onChangeDate() {
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