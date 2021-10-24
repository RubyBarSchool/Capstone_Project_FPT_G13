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
              <a-button type="primary" @click="search">
                <font-awesome-icon
                  :icon="['fas', 'search']"
                  :style="{ 'margin-right': '5px' }"
                />
                Tìm kiếm
              </a-button>
            </a-col>
            <a-col flex="100px">
              <a-button type="primary">
                Xuất excel
                <font-awesome-icon
                  :icon="['fas', 'download']"
                  :style="{ 'margin-left': '10px' }"
                />
              </a-button>
            </a-col>
          </a-row>
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
      </a-layout-content>
      <Footer />
    </a-layout>
  </div>
</template>
 <script>
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";
import attendanceService from "@/service/attendanceService.js";

export default {
  name: "Attendance",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      visibleEdit: false,
      nameEdit: "",
      dataSourceTable: [],
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
  created() {
    this.search();
  },
  methods: {
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