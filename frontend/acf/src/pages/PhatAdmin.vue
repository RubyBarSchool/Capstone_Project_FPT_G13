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
            mode="multiple"
            v-model="dataSearch.status"
            style="width: 150px"
          >
            <a-select-option value="false"> Nháp </a-select-option>
            <a-select-option value="true"> Công khai </a-select-option>
          </a-select>
          <a-range-picker
            v-model="dataSearch.date"
            :placeholder="['Ngày bắt đầu', 'Ngày kết thúc']"
            :show-time="{ format: 'DD/MM/YYYY' }"
            format="DD/MM/YYYY"
          />
          <a-button
            type="primary"
            @click="submitSearch"
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
              <template slot="time" slot-scope="text, record">
                {{ record.username }}
              </template>
              <template slot="roles" slot-scope="text, record">
                <div v-for="(item, index) in record.roles" :key="index">
                  {{ item.name }}
                </div>
              </template>
              <template slot="status" slot-scope="text, record">
                <a-tag :color="record.status ? 'green' : 'blue'">
                  {{ record.status ? "Công khai" : "Nháp" }}
                </a-tag>
              </template>
              <template slot="time" slot-scope="text, record">
                {{
                  new Date(record.time).toLocaleDateString("en-GB", {
                    year: "numeric",
                    month: "2-digit",
                    day: "2-digit",
                  })
                }}
              </template>
              <template slot="action" slot-scope="text, record">
                <a-row>
                  <a-col :span="9">
                    <a-button
                      id="edit"
                      @click="
                        showModalEdit(
                          record.id,
                          record.username,
                          record.roles,
                          record.status
                        )
                      "
                      :style="{ width: '44.25px' }"
                    >
                      <font-awesome-icon :icon="['fas', 'edit']" />
                    </a-button>
                  </a-col>
                  <a-col :span="9">
                    <a-popconfirm
                      v-if="dataSourceTable.length"
                      title="Bạn có chắc chắn muốn xóa không?"
                      @confirm="deleteAccount(record.id)"
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
                <a-input v-model="dataAdd.username" />
              </a-form-model-item>
              <a-form-model-item label="Họ và tên">
                <a-input v-model="dataAdd.password" />
              </a-form-model-item>
              <a-form-model-item label="Lý do">
                <a-textarea
                  placeholder="Lý do"
                  :rows="4"
                  v-model="dataAdd.password"
                />
              </a-form-model-item>
              <a-form-model-item label="Số tiền">
                <a-input v-model="dataAdd.password" />
              </a-form-model-item>
              <a-form-model-item label="Trạng thái">
                <a-radio-group name="radioGroup" v-model="dataEdit.status">
                  <a-radio :value="false"> Nháp </a-radio>
                  <a-radio :value="true"> Hiệu lực </a-radio>
                </a-radio-group>
              </a-form-model-item>
              <a-form-model-item label="Ngày hiệu lực">
                <a-range-picker
                  v-model="dataSearch.date"
                  :placeholder="['Ngày bắt đầu', 'Ngày kết thúc']"
                  :show-time="{ format: 'DD/MM/YYYY' }"
                  format="DD/MM/YYYY"
                />
              </a-form-model-item>
            </a-form-model>
          </a-modal>
          <!-- popup add -->

          <!-- popup edit-->
          <a-modal v-model="visibleEdit" title="Chỉnh sửa tài khoản">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary" @click="submitUpdate">
                Lưu
              </a-button>
            </template>
            <a-form-model>
              <a-form-model-item label="Tiêu đề">
                <a-input v-model="dataAdd.username" />
              </a-form-model-item>
              <a-form-model-item label="Họ và tên">
                <a-input v-model="dataAdd.password" />
              </a-form-model-item>
              <a-form-model-item label="Lý do">
                <a-textarea
                  placeholder="Lý do"
                  :rows="4"
                  v-model="dataAdd.password"
                />
              </a-form-model-item>
              <a-form-model-item label="Số tiền">
                <a-input v-model="dataAdd.password" />
              </a-form-model-item>
              <a-form-model-item label="Trạng thái">
                <a-radio-group name="radioGroup" v-model="dataEdit.status">
                  <a-radio :value="false"> Nháp </a-radio>
                  <a-radio :value="true"> Hiệu lực </a-radio>
                </a-radio-group>
              </a-form-model-item>
              <a-form-model-item label="Ngày hiệu lực">
                <a-range-picker
                  v-model="dataSearch.date"
                  :placeholder="['Ngày bắt đầu', 'Ngày kết thúc']"
                  :show-time="{ format: 'DD/MM/YYYY' }"
                  format="DD/MM/YYYY"
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
import accountService from "@/service/accountService.js";
import roleService from "@/service/roleService.js";
import employeeService from "@/service/employeeService.js";
import adminTruongService from "../service/adminTruongService";
import punishAdminService from "../service/punishAdminService";
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";

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
      dataRoles: [],
      dataEmployees: [],
      dataAdd: {
        listRole: [],
        employee: "",
        password: "",
        username: "",
      },
      dataRole: {
        name: "",
        pageIndex: 1,
        pageSize: 10,
      },
      dataEmployee: {
        name: "",
        pageIndex: 1,
        pageSize: 10,
      },
      dataEdit: {
        id: "",
        username: "",
        listRole: [],
        status: false,
      },
      dataAccountDetail: {
        id: "",
        name: "",
        roles: [],
        image: "",
        fullname: "",
        dob: "",
        phone: "",
        gender: "",
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
          title: "Ngày hiệu lực",
          dataIndex: "time",
          key: "time",
          width: 150,
          scopedSlots: { customRender: "time" },
        },
        {
          title: "Tiêu đề",
          dataIndex: "titlee",
          key: "titlee",
          width: 150,
          scopedSlots: { customRender: "titlee" },
        },
        {
          title: "Số tiền",
          dataIndex: "money",
          key: "money",
          width: 150,
          scopedSlots: { customRender: "money" },
        },
        {
          title: "Trạng thái",
          dataIndex: "status",
          key: "status",
          width: 150,
          scopedSlots: { customRender: "status" },
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
      visibleAdd: false,
      visibleEdit: false,
      visibleProfile: false,
    };
  },
  computed: {},
  created() {
    this.submitSearch();
    this.getAllRole();
  },
  methods: {
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
    generateUsername() {
      accountService
        .generateUsername(this.dataAdd.employee)
        .then((response) => {
          this.dataAdd.username = response.data.data;
        });
    },
    fetchRoles(value) {
      this.dataRole.name = value;
      this.getAllRole();
    },
    getAllRole() {
      roleService
        .getAllRole(this.dataRole)
        .then((response) => {
          this.dataRoles = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    fetchEmployees(value) {
      this.dataEmployee.name = value;
      this.getAllEmployeeNotAccount();
    },
    getAllEmployeeNotAccount() {
      employeeService
        .getAllEmployeeNotAccount(this.dataEmployee)
        .then((response) => {
          this.dataEmployees = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    showModalAdd() {
      this.dataRole.name = "";
      this.getAllRole();
      this.dataEmployee.name = "";
      this.getAllEmployeeNotAccount();
      this.visibleAdd = true;
    },
    submitAdd() {
      accountService
        .addAccount(this.dataAdd)
        .then((response) => {
          this.dataEmployees = response.data.data;
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Thêm mới";
            let description =
              "Thêm mới tài khoản " + this.dataAdd.username + " thành công !!";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Thêm mới";
            let description =
              "Thêm mới tài khoản " +
              this.dataAdd.username +
              " không thành công vì " +
              response.data.message;
            this.notifi(type, message, description);
          }
        })
        .catch((e) => {
          console.log(e);
        });
      this.visibleAdd = false;
      this.dataAdd.username = "";
      this.dataAdd.password = "";
      this.dataAdd.employee = "";
      this.dataAdd.listRole = [];
    },
    handleCancel() {
      this.visibleAdd = false;
      this.visibleEdit = false;
      this.visibleProfile = false;
    },
    showModalEdit(id, username, roles, status) {
      this.dataEdit.id = id;
      this.dataEdit.username = username;
      this.dataEdit.listRole = [];
      for (var i = 0; i < roles.length; i++) {
        this.dataEdit.listRole.push(roles[i].id);
      }
      this.dataEdit.status = status;
      this.visibleEdit = true;
      this.dataRole.name = "";
      this.getAllRole();
      this.dataEmployee.name = "";
      this.getAllEmployeeNotAccount();
    },
    submitUpdate() {
      adminTruongService
        .updateAccount(this.dataEdit)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Cập nhật";
            let description = "Account đang đăng nhập không được xóa";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Cập nhật";
            let description = "Account đang đăng nhập không được xóa";
            this.notifi(type, message, description);
          }
        })
        .catch((e) => {
          console.log(e);
        });
      this.visibleEdit = false;
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
    getAccountByID(id) {
      adminTruongService
        .getAccountByID(id)
        .then((response) => {
          this.dataAccountDetail = response.data.data;
          this.visibleProfile = true;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    deleteAccount(id) {
      accountService
        .deleteAccount(id)
        .then((response) => {
          if (response.data.data) {
            let type = "success";
            let message = "Xóa";
            let description = "Xóa thành công";
            this.notifi(type, message, description);
            this.submitSearch();
          } else {
            let type = "error";
            let message = "Xóa";
            let description = "Account đang đăng nhập không được xóa";
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
#user {
  background-color: rgb(76, 238, 12);
  color: white;
}
#user:hover {
  background-color: rgb(42, 253, 0);
  color: white;
}
/* profile */
.bg-c-lite-green {
  border-radius: 5px;
  background: linear-gradient(to right, #000000, #000000);
}

.card-block {
  padding: 1.25rem;
}

.m-b-25 {
  margin-bottom: 30px;
}

.img-radius {
  border-radius: 5px;
}

h6 {
  font-size: 13.5px;
}

.card-block p {
  line-height: 25px;
}

.card-block {
  padding: 1.25rem;
}

.b-b-default {
  border-bottom: 1px solid #e0e0e0;
}

.m-b-20 {
  margin-bottom: 20px;
}

.p-b-5 {
  padding-bottom: 5px !important;
}

.m-b-10 {
  margin-bottom: 10px;
  color: black;
}

.text-muted {
  color: #919aa3 !important;
}

.text-white {
  color: white;
}

.f-w-600 {
  font-weight: 600;
}

.m-t-40 {
  margin-top: 20px;
}
/* profile */
</style>