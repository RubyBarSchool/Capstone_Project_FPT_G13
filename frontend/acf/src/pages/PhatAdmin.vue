<template>
  <div class="punish">
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
                :icon="['fas', 'angle-double-up']"
                :style="{ width: '160px', height: '50px', color: '#15AABF' }"
              />
            </div>
          </a-back-top>
          <!-- menu trên -->
          <a-input
            placeholder="Tiêu đề"
            style="width: 150px"
            v-model="dataSearch.title"
          />
          <a-select
            placeholder="Trạng thái"
            @change="search"
            v-model="dataSearch.status"
            style="width: 150px"
          >
            <a-select-option value=""> All </a-select-option>
            <a-select-option :value="false"> Nháp </a-select-option>
            <a-select-option :value="true"> Công khai </a-select-option>
          </a-select>
          <a-range-picker
            @change="search"
            v-model="dataSearch.date"
            :placeholder="['Ngày hiệu lực', 'Ngày hiệu lực']"
            format="DD/MM/YYYY"
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
            Tìm kiếm
          </a-button>
          <a-button
            type="primary"
            @click="showModalAdd"
            :style="{ 'margin-left': '5px' }"
          >
            <font-awesome-icon
              :icon="['fas', 'plus-circle']"
              :style="{ 'margin-right': '5px' }"
            />
            Thêm
          </a-button>
          <!-- menu trên -->

          <!-- table content -->
          <div :style="{ 'padding-top': '10px' }">
            <a-table
              :columns="columns"
              :data-source="dataSourceTable"
              :pagination="pagination"
              :scroll="{ x: 1500 }"
              :rowKey="
                (record, index) => {
                  return index;
                }
              "
              @change="handleTableChange"
            >
              <template slot="status" slot-scope="text, record">
                <a-tag :color="record.status ? 'green' : 'blue'">
                  {{ record.status ? "Công khai" : "Nháp" }}
                </a-tag>
              </template>
              <template slot="listIdEmployee" slot-scope="text, record">
                <div
                  :key="index"
                  v-for="(data, index) in record.listIdEmployee"
                >
                  <!-- <div v-if="index != 0">,</div> -->
                  <div>{{ data.name }}</div>
                </div>
              </template>
              <template slot="action" slot-scope="text, record">
                <a-row>
                  <a-col :span="9" v-if="checkEditOrDelete(record)">
                    <a-button
                      id="edit"
                      @click="
                        showModalEdit(
                          record.id,
                          record.effectiveDate,
                          record.listIdEmployee,
                          record.money,
                          record.reason,
                          record.status,
                          record.title
                        )
                      "
                      :style="{ width: '44.25px' }"
                    >
                      <font-awesome-icon :icon="['fas', 'edit']" />
                    </a-button>
                  </a-col>
                  <a-col :span="9" v-if="checkEditOrDelete(record)">
                    <a-popconfirm
                      v-if="dataSourceTable.length"
                      title="Bạn có chắc chắn muốn xóa không?"
                      @confirm="deletePunishAdmin(record.id)"
                    >
                      <a-button id="delete">
                        <font-awesome-icon :icon="['fas', 'trash']" />
                      </a-button>
                    </a-popconfirm>
                  </a-col>
                </a-row>
              </template>
            </a-table>
          </div>
          <!-- table content -->

          <!-- popup add-->
          <a-modal v-model="visibleAdd" title="Thêm hình phạt">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary" @click="submitAdd">
                Lưu
              </a-button>
            </template>
            <a-form-model>
              <a-form-model-item label="Tiêu đề">
                <a-input v-model="dataAdd.title" />
              </a-form-model-item>
              <a-form-model-item label="Họ và tên">
                <a-select
                  placeholder="Họ và tên"
                  mode="multiple"
                  v-model="dataAdd.listIdEmployee"
                  :filter-option="false"
                  @search="fetchEmployees"
                >
                  <a-select-option
                    v-for="(employee, index) in dataEmployees"
                    :value="employee.id"
                    :key="index"
                  >
                    {{ employee.fullName }}
                  </a-select-option>
                </a-select>
              </a-form-model-item>
              <a-form-model-item label="Lý do">
                <a-textarea
                  placeholder="Lý do"
                  :rows="4"
                  v-model="dataAdd.reason"
                />
              </a-form-model-item>
              <a-form-model-item label="Số tiền">
                <a-input-number v-model="dataAdd.money" :min="100000" />
              </a-form-model-item>
              <a-form-model-item label="Trạng thái">
                <a-radio-group name="radioGroup" v-model="dataAdd.status">
                  <a-radio value="false"> Nháp </a-radio>
                  <a-radio value="true"> Hiệu lực </a-radio>
                </a-radio-group>
              </a-form-model-item>
              <a-form-model-item label="Ngày hiệu lực">
                <a-date-picker
                  v-model="dataAdd.effectiveDate"
                  format="YYYY-MM-DD"
                  :disabled-date="disableDateStart"
                  valueFormat="YYYY-MM-DD"
                >
                </a-date-picker>
              </a-form-model-item>
            </a-form-model>
          </a-modal>
          <!-- popup add -->

          <!-- popup edit-->
          <a-modal v-model="visibleEdit" title="Chỉnh sửa hình phạt">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary" @click="submitUpdate">
                Lưu
              </a-button>
            </template>
            <a-form-model>
              <a-form-model-item label="Tiêu đề">
                <a-input v-model="dataEdit.title" />
              </a-form-model-item>
              <a-form-model-item label="Họ và tên">
                <a-select
                  placeholder="Họ và tên"
                  mode="multiple"
                  disabled
                  v-model="dataEdit.listIdEmployee"
                  :filter-option="false"
                  @search="fetchEmployees"
                >
                  <a-select-option
                    v-for="(employee, index) in dataEmployees"
                    :value="employee.id"
                    :key="index"
                  >
                    {{ employee.fullName }}
                  </a-select-option>
                </a-select>
              </a-form-model-item>
              <a-form-model-item label="Lý do">
                <a-textarea
                  placeholder="Lý do"
                  :rows="4"
                  v-model="dataEdit.reason"
                />
              </a-form-model-item>
              <a-form-model-item label="Số tiền">
                <a-input-number v-model="dataEdit.money" :min="100000" />
              </a-form-model-item>
              <a-form-model-item label="Trạng thái">
                <a-radio-group name="radioGroup" v-model="dataEdit.status">
                  <a-radio :value="false"> Nháp </a-radio>
                  <a-radio :value="true"> Hiệu lực </a-radio>
                </a-radio-group>
              </a-form-model-item>
              <a-form-model-item label="Ngày hiệu lực">
                <a-date-picker
                  v-model="dataEdit.effectiveDate"
                  :disabled-date="disableDateStart"
                  format="DD/MM/YYYY"
                  valueFormat="YYYY-MM-DD"
                />
              </a-form-model-item>
            </a-form-model>
          </a-modal>
          <!-- popup edit-->
        </div>
      </a-layout-content>
      <Footer />
    </a-layout>
  </div>
</template>
<script>
import punishAdminService from "@/service/punishAdminService";
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";
import moment from "moment";
export default {
  name: "PhatAdmin",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      pagination: {
        current: 1,
        pageSize: 10,
        total: 0,
      },
      dataSearch: {
        date: [],
        pageIndex: 1,
        pageSize: 10,
        status: "",
        title: "",
        total: 0,
      },
      dataSourceTable: [],
      dataEmployees: [],
      dataAdd: {
        effectiveDate: "",
        listIdEmployee: [],
        money: "",
        reason: "",
        status: "",
        title: "",
      },
      dataEmployee: {
        idPosition: "",
        name: "",
        pageIndex: 1,
        pageSize: 10,
        statusDelete: "",
      },
      dataEdit: {
        effectiveDate: "",
        id: 0,
        listIdEmployee: [],
        money: "",
        reason: "",
        status: "",
        title: "",
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
          title: "Tiêu đề",
          dataIndex: "title",
          key: "title",
          width: 150,
        },
        {
          title: "Số tiền",
          dataIndex: "money",
          key: "money",
          width: 150,
        },
        {
          title: "Lý do",
          dataIndex: "reason",
          key: "reason",
          width: 150,
        },
        {
          title: "Nhân viên chịu hình phạt",
          dataIndex: "listIdEmployee",
          key: "listIdEmployee",
          width: 150,
          scopedSlots: { customRender: "listIdEmployee" },
        },
        {
          title: "Ngày hiệu lực",
          dataIndex: "effectiveDate",
          key: "effectiveDate",
          width: 150,
        },
        {
          title: "Trạng thái",
          dataIndex: "status",
          key: "status",
          width: 150,
          scopedSlots: { customRender: "status" },
        },
        {
          title: "Hành động",
          dataIndex: "action",
          key: "action",
          fixed: "right",
          width: 150,
          scopedSlots: { customRender: "action" },
        },
      ],
      visibleAdd: false,
      visibleEdit: false,
    };
  },
  created() {
    this.submitSearch();
  },
  methods: {
    checkEditOrDelete(record) {
      let date = record.effectiveDate.split("-")[2];
      if (parseInt(date) > 10) {
        let dateNow = moment();
        let dateLast = moment(record.effectiveDate)
          .add(1, "months")
          .set("date", 10);
        if (dateNow <= dateLast) {
          return true;
        } else {
          return false;
        }
      } else {
        let dateNow1 = moment();
        let dateLast1 = moment(record.effectiveDate).set("date", 10);
        if (dateNow1 <= dateLast1) {
          return true;
        } else {
          return false;
        }
      }
    },
    disableDateStart(current) {
      return current < moment().subtract(1, "days");
    },
    handleTableChange(pagination) {
      this.dataSearch.pageIndex = pagination.current;
      this.pagination = pagination;
      punishAdminService
        .searchPunishAdmin(this.dataSearch)
        .then((response) => {
          this.dataSourceTable = response.data.data;
          this.dataSearch.total = response.data.total;
          this.pagination.total = response.data.total;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    submitSearch() {
      this.dataSearch.total = 0;
      punishAdminService
        .searchPunishAdmin(this.dataSearch)
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
      this.submitSearch();
    },
    fetchEmployees(value) {
      this.dataEmployee.name = value;
      this.getAllEmployee();
    },
    getAllEmployee() {
      punishAdminService
        .getAllEmployee(this.dataEmployee)
        .then((response) => {
          this.dataEmployees = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    showModalAdd() {
      this.visibleAdd = true;
      this.getAllEmployee();
    },
    submitAdd() {
      punishAdminService
        .addPunishAdmin(this.dataAdd)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Thêm đơn phạt";
            let description = "Thêm mới đơn phạt thành công !!";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Thêm đơn phạt";
            let description =
              "Thêm mới đơn phạt không thành công vì " + response.data.message;
            this.notifi(type, message, description);
          }
        })
        .catch((e) => {
          console.log(e);
        });
      this.visibleAdd = false;
      this.dataAdd.effectiveDate = "";
      this.dataAdd.listIdEmployee = [];
      this.dataAdd.money = "";
      this.dataAdd.reason = "";
      this.dataAdd.status = "";
      this.dataAdd.title = "";
    },
    showModalEdit(
      id,
      effectiveDate,
      listIdEmployees,
      money,
      reason,
      status,
      title
    ) {
      this.dataEdit.id = id;
      this.dataEdit.effectiveDate = effectiveDate;
      this.dataEdit.listIdEmployee = [];
      for (var i = 0; i < listIdEmployees.length; i++) {
        this.dataEdit.listIdEmployee.push(listIdEmployees[i].id);
      }
      this.dataEdit.status = status;
      this.dataEdit.reason = reason;
      this.dataEdit.money = money;
      this.dataEdit.title = title;
      this.visibleEdit = true;
      this.getAllEmployee();
    },
    submitUpdate() {
      // this.dataEdit.effectiveDate.
      punishAdminService
        .updatePunishAdmin(this.dataEdit)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Cập nhật";
            let description = "Cập nhật đơn phạt thành công";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Cập nhật";
            let description = "Cập nhật đơn phạt không thành công";
            this.notifi(type, message, description);
          }
        })
        .catch((e) => {
          console.log(e);
        });
      this.visibleEdit = false;
    },
    deleteThuongAdmin(id) {
      punishAdminService
        .deletePunishAdmin(id)
        .then((response) => {
          if (response.data.data) {
            let type = "success";
            let message = "Xóa";
            let description =
              "Xóa đơn phạt " + this.dataAdd.title + " thành công";
            this.notifi(type, message, description);
            this.submitSearch();
          } else {
            let type = "error";
            let message = "Xóa";
            let description =
              "Xóa đơn phạt " + this.dataAdd.title + " không thành công";
            this.notifi(type, message, description);
            this.submitSearch();
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
    notifi(type, message, description) {
      this.$notification[type]({
        message: message,
        description: description,
      });
    },
    handleCancel() {
      this.visibleAdd = false;
      this.visibleEdit = false;
      this.visibleProfile = false;
    },
  },
};
</script>

<style scoped>
/* back top */
.ant-back-top-inner {
  color: rgb(241, 237, 237);
  text-align: center;
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
</style>