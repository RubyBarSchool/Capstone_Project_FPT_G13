<template>
  <div class="admin">
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
            placeholder="Tên tài khoản"
            style="width: 150px"
            v-model="dataSearch.name"
            :style="{ 'margin-right': '5px' }"
          />
          <a-select
            placeholder="Quyền"
            mode="multiple"
            v-model="dataSearch.listRole"
            :filter-option="false"
            @search="fetchRoles"
            @change="search"
            :style="{ 'margin-right': '5px', width: '150px' }"
          >
            <a-select-option
              v-for="(role, index) in dataRoles"
              :value="role.id"
              :key="index"
            >
              {{ role.name }}
            </a-select-option>
          </a-select>
          <a-select
            placeholder="Trạng thái"
            mode="multiple"
            v-model="dataSearch.listStatus"
            :style="{ 'margin-right': '5px', width: '150px' }"
            @change="search"
          >
            <a-select-option value="false"> Nháp </a-select-option>
            <a-select-option value="true"> Công khai </a-select-option>
          </a-select>
          <a-range-picker
            @change="search"
            v-model="dataSearch.date"
            :placeholder="['Ngày bắt đầu', 'Ngày kết thúc']"
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
              :icon="['fas', 'user-plus']"
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
              :scroll="{ x: 1000 }"
              :rowKey="
                (record, index) => {
                  return index;
                }
              "
              @change="handleTableChange"
              style="color: #fffff"
            >
              <template slot="username" slot-scope="text, record">
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
                <a-row :gutter="[16, 16]">
                  <a-col :span="6">
                    <a-button id="user" @click="getAccountByID(record.id)">
                      <font-awesome-icon :icon="['fas', 'user']" />
                    </a-button>
                  </a-col>
                  <a-col :span="6" v-if="checkDisable(record.username)">
                    <a-popconfirm
                      v-if="dataSourceTable.length"
                      title="Bạn có chắc chắn muốn cập nhật mật khẩu?"
                      @confirm="resetPassword(record.id)"
                      ok-text="Đồng ý"
                      cancel-text="Hủy"
                    >
                      <a-button
                        id="reset"
                      >
                        <font-awesome-icon :icon="['fas', 'retweet']" />
                      </a-button>
                    </a-popconfirm>
                  </a-col>
                  <a-col :span="6" v-if="checkDisable(record.username)">
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
                    >
                      <font-awesome-icon :icon="['fas', 'edit']" />
                    </a-button>
                  </a-col>
                  <a-col :span="6" v-if="checkDisable(record.username)">
                    <a-popconfirm
                      v-if="dataSourceTable.length"
                      title="Bạn có chắc chắn muốn xóa không?"
                      @confirm="deleteAccount(record.id)"
                      ok-text="Đồng ý"
                      cancel-text="Hủy"
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

          <!-- popup profile-->
          <a-modal v-model="visibleProfile" class="profile">
            <template slot="footer">
              <a-button key="a" hidden></a-button>
              <a-button key="b" hidden></a-button>
            </template>
            <div class="row m-l-0 m-r-0">
              <!-- cột trái   -->
              <div class="col-md-4 bg-c-lite-green user-profile">
                <div class="card-block text-center text-white">
                  <div class="m-b-25">
                    <img
                      src="https://img.icons8.com/bubbles/100/000000/user.png"
                      class="img-radius"
                    />
                  </div>
                  <h6 class="text-white f-w-400">
                    {{ dataAccountDetail.fullname }}
                  </h6>
                  <p
                    v-for="(role, index) in dataAccountDetail.roles"
                    :value="role.id"
                    :key="index"
                    class="text-white f-w-400"
                  >
                    {{ role.name }}
                  </p>
                </div>
              </div>
              <!-- cột phải  -->
              <div class="col-md-8">
                <div class="card-block">
                  <h5 class="m-b-20 p-b-5 b-b-default f-w-600">
                    Thông tin tài khoản
                  </h5>
                  <div class="row">
                    <div class="col-sm-6">
                      <p class="m-b-10 f-w-600">Giới Tính</p>
                      <h6 class="text-muted f-w-400">
                        {{ dataAccountDetail.gender ? "Nam" : "Nữ" }}
                      </h6>
                    </div>
                    <div class="col-sm-6">
                      <p class="m-b-10 f-w-600">Ngày Sinh</p>
                      <h6 class="text-muted f-w-400">
                        {{
                          new Date(dataAccountDetail.dob).toLocaleDateString(
                            "en-GB",
                            {
                              year: "numeric",
                              month: "2-digit",
                              day: "2-digit",
                            }
                          )
                        }}
                      </h6>
                    </div>
                  </div>
                  <h6 class="m-b-20 m-t-40 p-b-5 b-b-default f-w-600"></h6>
                  <div class="row">
                    <div class="col-sm-6">
                      <p class="m-b-10 f-w-600">Email</p>
                      <h6 class="text-muted f-w-400">tvt@gmail.com</h6>
                    </div>
                    <div class="col-sm-6">
                      <p class="m-b-10 f-w-600">Điện thoại</p>
                      <h6 class="text-muted f-w-400">
                        {{ dataAccountDetail.phone }}
                      </h6>
                    </div>
                  </div>
                  <h6 class="m-b-20 m-t-40 p-b-5 b-b-default f-w-600"></h6>
                  <div class="row">
                    <div class="col-sm-6">
                      <p class="m-b-10 f-w-600">Địa chỉ</p>
                      <h6 class="text-muted f-w-400">Mỹ Đình - Hà Nội</h6>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </a-modal>
          <!-- popup profile-->

          <!-- popup add-->
          <a-modal v-model="visibleAdd" title="Thêm tài khoản">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button
                key="submit"
                type="primary"
                :loading="loading"
                @click="checkFormAdd"
              >
                Lưu
              </a-button>
            </template>
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span>Nhân viên :</a-col
              >
              <a-col flex="auto">
                <a-select
                  show-search
                  placeholder="Tên nhân viên"
                  :filter-option="false"
                  v-model="dataAdd.employee"
                  @change="generateUsername"
                  @search="fetchEmployees"
                  style="width: 100%"
                >
                  <a-select-option
                    v-for="(employee, index) in dataEmployees"
                    :value="employee.id"
                    :key="index"
                  >
                    {{ employee.name }}
                  </a-select-option>
                </a-select>
                <div style="color: red" v-if="checkDataInputEmployee.show">
                  {{ checkDataInputEmployee.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Tài khoản :</a-col
              >
              <a-col flex="auto">
                <a-input
                  @change="inputUsername"
                  v-model="dataAdd.username"
                  disabled
                />
                <div style="color: red" v-if="checkDataInputUsername.show">
                  {{ checkDataInputUsername.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Chức vụ :</a-col
              >
              <a-col flex="auto">
                <a-select
                  placeholder="Chức vụ"
                  mode="multiple"
                  v-model="dataAdd.listRole"
                  :filter-option="false"
                  @search="fetchRoles"
                  style="width: 100%"
                  @change="inputNamePosition"
                >
                  <a-select-option
                    v-for="(role, index) in dataRoles"
                    :value="role.id"
                    :key="index"
                  >
                    {{ role.name }}
                  </a-select-option>
                </a-select>
                <div style="color: red" v-if="checkDataInputRole.show">
                  {{ checkDataInputRole.message }}
                </div>
              </a-col>
            </a-row>
          </a-modal>
          <!-- popup add -->

          <!-- popup edit-->
          <a-modal v-model="visibleEdit" title="Chỉnh sửa tài khoản">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button
                key="submit"
                type="primary"
                :loading="loading"
                @click="checkFormEdit"
              >
                Lưu
              </a-button>
            </template>
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Tài khoản :</a-col
              >
              <a-col flex="auto">
                <a-input v-model="dataEdit.username" disabled />
                <div style="color: red" v-if="checkDataInputUsername.show">
                  {{ checkDataInputUsername.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Chức vụ :</a-col
              >
              <a-col flex="auto">
                <a-select
                  placeholder="Chức vụ"
                  mode="multiple"
                  v-model="dataEdit.listRole"
                  :filter-option="false"
                  @search="fetchRoles"
                  style="width: 100%"
                  @change="inputEditPosition"
                >
                  <a-select-option
                    v-for="(role, index) in dataRoles"
                    :value="role.id"
                    :key="index"
                  >
                    {{ role.name }}
                  </a-select-option>
                </a-select>
                <div style="color: red" v-if="checkDataInputRole.show">
                  {{ checkDataInputRole.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Trạng thái :</a-col
              >
              <a-col flex="auto">
                <a-radio-group name="radioGroup" v-model="dataEdit.status">
                  <a-radio :value="false"> Nháp </a-radio>
                  <a-radio :value="true"> Công khai </a-radio>
                </a-radio-group>
              </a-col>
            </a-row>
          </a-modal>
          <!-- popup edit-->
        </div>
      </a-layout-content>
    </a-layout>
  </div>
</template>
 <script>
import accountService from "@/service/accountService.js";
import roleService from "@/service/roleService.js";
import employeeService from "@/service/employeeService.js";
import adminService from "@/service/adminService";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
  name: "Admin",
  components: {},
  data() {
    return {
      loading: false,
      loadingDelete: false,
      pagination: {
        current: 1,
        pageSize: 10,
        total: 0,
      },
      dataSearch: {
        name: "",
        listRole: [],
        listStatus: [],
        date: [],
        pageIndex: 1,
        pageSize: 10,
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
          title: "STT",
          width: 100,
          dataIndex: "id",
          key: "id",
          fixed: "left",
        },
        {
          title: "Tài Khoản",
          dataIndex: "username",
          key: "username",
          width: 150,
          scopedSlots: { customRender: "username" },
        },
        {
          title: "Quyền",
          dataIndex: "roles",
          key: "roles",
          width: 150,
          scopedSlots: { customRender: "roles" },
        },
        {
          title: "Trạng thái",
          dataIndex: "status",
          key: "status",
          width: 150,
          scopedSlots: { customRender: "status" },
        },
        {
          title: "Thời gian",
          dataIndex: "time",
          key: "time",
          width: 150,
          scopedSlots: { customRender: "time" },
        },
        {
          title: "",
          dataIndex: "action",
          key: "action",
          fixed: "right",
          width: 250,
          scopedSlots: { customRender: "action" },
        },
      ],
      visibleAdd: false,
      visibleEdit: false,
      visibleProfile: false,
      checkDataInput: {
        show: false,
        message: "",
      },
      checkDataInputUsername: {
        show: false,
        message: "",
      },
      checkDataInputEmployee: {
        show: false,
        message: "",
      },
      checkDataInputRole: {
        show: false,
        message: "",
      },
      connected: false,
      listUserOnline: [],
    };
  },
  computed: {},
  watch: {},
  beforeDestroy() {
    console.log("beforeDestroy");
    this.disconnect();
  },
  created() {
    this.submitSearch();
    this.getAllRole();
    this.connectWebsoket();
  },
  methods: {
    disconnect() {
      if (this.stompClient) {
        this.stompClient.disconnect();
      }
    },
    checkDisable(username) {
      return this.listUserOnline.indexOf(username) != -1 ? false : true;
    },
    connectWebsoket() {
      let username = JSON.parse(localStorage.getItem("user")).username;
      this.socket = new SockJS("http://localhost:8080/api/wse/online");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect(
        { username: username },
        () => {
          this.connected = true;
          this.stompClient.subscribe("/users/queue/online", (tick) => {
            if (this != null) {
              this.listUserOnline = JSON.parse(tick.body);
            }
          });
          if (this.stompClient && this.stompClient.connected) {
            this.stompClient.send("/ws/online");
          }
        },
        (error) => {
          console.log(error);
          this.connected = false;
        }
      );
    },
    resetPassword(id) {
      accountService
        .resetpassword(id)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Cập nhật mật khẩu";
            let description = "Tài khoản đặt lại mật khẩu thành công";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Cập nhật mật khẩu";
            let description = "Tài khoản đặt lại mật khẩu không thành công";
            this.notifi(type, message, description);
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
    handleTableChange(pagination) {
      this.dataSearch.pageIndex = pagination.current;
      this.pagination = pagination;
      adminService
        .searchAccount(this.dataSearch)
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
      if (this.dataAdd.employee != null && this.dataAdd.employee.length != 0) {
        accountService
          .generateUsername(this.dataAdd.employee)
          .then((response) => {
            this.dataAdd.username = response.data.data;
            this.inputUsername();
          });
        this.checkDataInputEmployee.show = false;
        this.checkDataInputEmployee.message = "";
      } else {
        this.checkDataInputEmployee.show = true;
        this.checkDataInputEmployee.message = "Bạn phải điền tên nhân viên";
      }
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
      this.dataAdd.username = "";
      this.dataAdd.employee = "";
      this.dataAdd.password = "";
      this.dataAdd.listRole = [];
      this.dataRole.name = "";
      this.getAllRole();
      this.dataEmployee.name = "";
      this.getAllEmployeeNotAccount();
      this.visibleAdd = true;
      this.checkDataInputUsername.show = false;
      this.checkDataInputUsername.message = "";
      this.checkDataInputEmployee.show = false;
      this.checkDataInputEmployee.message = "";
      this.checkDataInputRole.show = false;
      this.checkDataInputRole.message = "";
    },
    checkFormAdd() {
      let check = true;
      if (this.dataAdd.employee != null && this.dataAdd.employee.length != 0) {
        this.checkDataInputEmployee.show = false;
        this.checkDataInputEmployee.message = "";
      } else {
        check = false;
        this.checkDataInputEmployee.show = true;
        this.checkDataInputEmployee.message = "Bạn phải điền tên nhân viên";
      }

      if (this.dataAdd.username != null && this.dataAdd.username.trim() != "") {
        this.checkDataInputUsername.show = false;
        this.checkDataInputUsername.message = "";
      } else {
        check = false;
        this.checkDataInputUsername.show = true;
        this.checkDataInputUsername.message = "Bạn phải điền tài khoản";
      }

      if (this.dataAdd.listRole != null && this.dataAdd.listRole.length != 0) {
        this.checkDataInputRole.show = false;
        this.checkDataInputRole.message = "";
      } else {
        check = false;
        this.checkDataInputRole.show = true;
        this.checkDataInputRole.message = "Bạn phải điền chức vụ";
      }
      if (check) {
        this.submitAdd();
      }
    },

    submitAdd() {
      this.loading = true;
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
          this.loading = false;
          this.visibleAdd = false;
        })
        .catch((e) => {
          console.log(e);
          this.loading = false;
        });
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
      this.checkDataInputUsername.show = false;
      this.checkDataInputUsername.message = "";
      this.checkDataInputRole.show = false;
      this.checkDataInputRole.message = "";
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
    checkFormEdit() {
      let check = true;
      if (
        this.dataEdit.listRole != null &&
        this.dataEdit.listRole.length != 0
      ) {
        this.checkDataInputRole.show = false;
        this.checkDataInputRole.message = "";
      } else {
        check = false;
        this.checkDataInputRole.show = true;
        this.checkDataInputRole.message = "Bạn phải điền chức vụ";
      }
      if (check) {
        this.submitUpdate();
      }
    },
    submitUpdate() {
      this.loading = true;
      adminService
        .updateAccount(this.dataEdit)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Cập nhật";
            let description =
              "Cập nhật tài khoản " + this.dataEdit.username + " thành công !!";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Cập nhật";
            let description =
              "Cập nhật tài khoản " +
              this.dataEdit.username +
              " không thành công vì " +
              response.data.message;
            this.notifi(type, message, description);
          }
          this.loading = false;
          this.visibleEdit = false;
        })
        .catch((e) => {
          console.log(e);
          this.loading = false;
        });
    },

    inputEditPosition() {
      if (
        this.dataEdit.listRole != null &&
        this.dataEdit.listRole.length != 0
      ) {
        this.checkDataInputRole.show = false;
        this.checkDataInputRole.message = "";
      } else {
        this.checkDataInputRole.show = true;
        this.checkDataInputRole.message = "Bạn phải điền chức vụ";
      }
    },

    inputUsername() {
      if (this.dataAdd.username != null && this.dataAdd.username.trim() != "") {
        this.checkDataInputUsername.show = false;
        this.checkDataInputUsername.message = "";
      } else {
        this.checkDataInputUsername.show = true;
        this.checkDataInputUsername.message = "Bạn phải điền tài khoản";
      }
    },

    inputNamePosition() {
      if (this.dataAdd.listRole != null && this.dataAdd.listRole.length != 0) {
        this.checkDataInputRole.show = false;
        this.checkDataInputRole.message = "";
      } else {
        this.checkDataInputRole.show = true;
        this.checkDataInputRole.message = "Bạn phải điền chức vụ";
      }
    },
    submitSearch() {
      this.dataSearch.total = 0;
      adminService
        .searchAccount(this.dataSearch)
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
      adminService
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
      this.loadingDelete = true;
      accountService
        .deleteAccount(id)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Xóa";
            let description = "Xóa thành công";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Xóa";
            let description = "Tài khoản đang đăng nhập không được xóa";
            this.notifi(type, message, description);
          }
          this.loadingDelete = false;
        })
        .catch((e) => {
          console.log(e);
          this.loadingDelete = false;
        });
    },
    notifi(type, message, description) {
      this.$notification[type]({
        message: message,
        description: description,
      });
    },
    search() {
      this.dataSearch.pageIndex = 1;
      this.dataSearch.total = 0;
      this.handleTableChange(this.pagination);
    },
  },
};
</script>

<style scoped>
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
  margin-left: 0.5vh;
}
#user:hover {
  background-color: rgb(42, 253, 0);
  color: white;
}
#reset {
  background-color: #ff8800;
  color: white;
  margin-right: 1vh;
}
#reset:hover {
  background-color: #FF6600;
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