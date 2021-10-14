<template>
  <div class="admin">
    <!-- menu trên -->
    <a-row type="flex">
      <a-col flex="auto">
        <a-input
          placeholder="Tên tài khoản"
          style="width: 150px"
          v-model="name"
        />
        <a-select
          placeholder="Chức vụ"
          mode="multiple"
          v-model="dataEdit.roleIDs"
          style="width: 200px"
        >
          <a-select-option
            v-for="(rol, index) in dataRoles"
            :value="rol.id"
            :key="index"
          >
            {{ rol.name }}
          </a-select-option>
        </a-select>
        <a-select placeholder="Trạng thái" style="width: 150px">
          <a-select-option value="1"> Nháp </a-select-option>
          <a-select-option value="0"> Công khai </a-select-option>
        </a-select>
        <a-select placeholder="ID Tăng dần" style="width: 150px">
          <a-select-option value="1"> ID Tăng dần </a-select-option>
          <a-select-option value="0"> ID Giảm dần </a-select-option>
        </a-select>
        <a-button type="primary" icon="search" @click="submitSearch">
          Tìm kiếm
        </a-button>
      </a-col>
      <a-col flex="100px">
        <a-button type="primary" icon="usergroup-add" @click="showModalAdd">
          Thêm 
        </a-button></a-col
      >
    </a-row>
    <br />
    <!-- table content -->
    <a-table
      :columns="columns"
      :data-source="dataSourceTable"
      :rowKey="
        (record, index) => {
          return index;
        }
      "
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
        {{ record.time }}
      </template>
      <template slot="action" slot-scope="text, record">
        <a-row>
          <a-col :span="8">
            <a-button
              id="user"
              type="dashed"
              icon="user"
              @click="getAccountByID(record.id)"
            />
          </a-col>
          <a-col :span="8">
            <a-button
              id="edit"
              type="dashed"
              icon="edit"
              @click="
                showModalEdit(
                  record.username,
                  record.roles,
                  record.id,
                  record.status
                )
              "
            />
          </a-col>
          <a-col :span="8">
            <a-popconfirm
              v-if="dataSourceTable.length"
              title="Bạn có chắc chắn muốn xóa không?"
              @confirm="deleteAccount(record.id)"
            >
              <a-button id="delete" type="dashed" icon="delete" />
            </a-popconfirm>
          </a-col>
        </a-row>
      </template>
    </a-table>
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
              <a href="#"><a-icon type="camera" /></a>
            </div>
            <h6 class="text-white f-w-400">{{ dataAccByID.fullname }}</h6>
            <p
              v-for="(rol, index) in dataAccByID.roles"
              :value="rol.id"
              :key="index"
              class="text-white f-w-400"
            >
              {{ rol.name }}
            </p>
          </div>
        </div>
        <!-- cột phải   -->
        <div class="col-md-8">
          <div class="card-block">
            <h5 class="m-b-20 p-b-5 b-b-default f-w-600">
              Thông tin tài khoản
            </h5>
            <div class="row">
              <div class="col-sm-6">
                <p class="m-b-10 f-w-600">Giới Tính</p>
                <h6 class="text-muted f-w-400">
                  {{ dataAccByID.gender ? "Nam" : "Nữ" }}
                </h6>
              </div>
              <div class="col-sm-6">
                <p class="m-b-10 f-w-600">Ngày Sinh</p>
                <h6 class="text-muted f-w-400">{{ dataAccByID.dob }}</h6>
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
                  {{ dataAccByID.phone }}
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
        <a-button key="back" @click="handleCancelAdd"> Hủy </a-button>
        <a-button key="submit" type="primary" @click="submitAdd">
          Lưu
        </a-button>
      </template>
      <a-form-model>
        <a-form-model-item label="Tài khoản">
          <a-input :value="dataGenName" disabled />
        </a-form-model-item>
        <a-form-model-item label="Mật khẩu">
          <a-input v-model="dataAddInput.passwordInput" />
        </a-form-model-item>
        <a-form-model-item label="Chức vụ">
          <a-select
            mode="multiple"
            v-model="dataAddInput.idRoleInput"
            style="width: 100%"
          >
            <a-select-option
              v-for="(rol, index) in dataRoles"
              :value="rol.id"
              :key="index"
            >
              {{ rol.name }}
            </a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item label="Nhân viên">
          <a-select
            show-search
            placeholder="Nhập tên nhân viên"
            option-filter-prop="children"
            style="width: 472px"
            :filter-option="filterOption"
            v-model="dataEmployeeNotAccount.name"
            @change="generateUsername(dataEmployeeNotAccount.name)"
          >
            <a-select-option
              v-for="(nameNotAcc, index) in dataEmployeesNotAccount"
              :value="nameNotAcc.name"
              :key="index"
            >
              {{ nameNotAcc.name }}
            </a-select-option>
          </a-select>
        </a-form-model-item>
      </a-form-model>
    </a-modal>
    <!-- popup add -->

    <!-- popup edit-->
    <a-modal v-model="visibleEdit" title="Chỉnh sửa tài khoản">
      <template slot="footer">
        <a-button key="back" @click="handleCancelEdit"> Hủy </a-button>
        <a-button key="submit" type="primary" @click="submitEdit">
          Lưu
        </a-button>
      </template>
      <a-form-model>
        <a-form-model-item label="Tài khoản">
          <a-input v-model="dataEdit.username" disabled />
        </a-form-model-item>
        <a-form-model-item label="Chức vụ">
          <a-select
            mode="multiple"
            v-model="dataEdit.roleIDs"
            style="width: 100%"
          >
            <a-select-option
              v-for="(rol, index) in dataRoles"
              :value="rol.id"
              :key="index"
            >
              {{ rol.name }}
            </a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item label="Trạng thái">
          <a-radio-group name="radioGroup" v-model="dataEdit.status">
            <a-radio :value="true"> Công khai </a-radio>
            <a-radio :value="false"> Nháp </a-radio>
          </a-radio-group>
        </a-form-model-item>
      </a-form-model>
    </a-modal>
    <!-- popup edit-->
  </div>
</template>
 <script>
import accountService from "@/service/accountService.js";
import roleService from "@/service/roleService.js";
import employeeService from "@/service/employeeService.js";

export default {
  name: "Admin",
  data() {
    return {
      name: "",
      dataSourceTable: [],
      dataRoles: [],
      //tra backend
      dataUpdateAccount: {
        idAccount: "",
        idRole: [""],
        status: "",
      },
      dataAdd: {
        idRole: [],
        id_employee: "",
        password: "",
        username: "",
      },
      //huwngs input nhap vao
      dataAddInput: {
        usernameInput: "",
        passwordInput: "",
        idRoleInput: [],
        idEmployeeInput: "",
      },
      dataRole: {
        data: {},
        message: "",
        status: 0,
      },
      dataEmployees: [],
      dataEmployee: {
        name: "",
        pageIndex: "1",
        pageSize: "10",
      },
      dataEdit: {
        username: "",
        roleIDs: [],
        id: "",
        status: 0,
      },
      dataEmployeesNotAccount: [],
      dataEmployeeNotAccount: {
        id: "",
        name: "",
      },
      dataGenName: "",
      dataSearch: {
        name: "",
        pageIndex: "1",
        pageSize: "10",
      },
      dataAccByID: {
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
          title: "Tài Khoản",
          dataIndex: "username",
          key: "username",
          width: 150,
          scopedSlots: { customRender: "username" },
        },
        {
          title: "Chức vụ",
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
          width: 150,
          scopedSlots: { customRender: "action" },
        },
      ],
      visibleAdd: false,
      visibleEdit: false,
      visibleProfile: false,
    };
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  created() {
    this.getAllAccount();
    this.getAllRole();
  },
  methods: {
    getAllAccount() {
      let form = {
        pageIndex: 1,
        pageSize: 10,
      };
      accountService
        .getAllAccount(form)
        .then((response) => {
          this.dataSourceTable = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
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
    getAllEmployeeNotAccount() {
      employeeService
        .getAllEmployeeNotAccount()
        .then((response) => {
          this.dataEmployeesNotAccount = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    filterOption(input, option) {
      return (
        option.componentOptions.children[0].text
          .toLowerCase()
          .indexOf(input.toLowerCase()) >= 0
      );
    },
    showModalAdd() {
      this.getAllRole();
      this.getAllEmployeeNotAccount();
      this.visibleAdd = true;
    },
    submitAdd() {
      this.dataAdd.idRole = this.dataAddInput.idRoleInput;
      let index = this.dataEmployeesNotAccount.findIndex(
        (item) => item.name == this.dataEmployeeNotAccount.name
      );
      this.dataAdd.id_employee = this.dataEmployeesNotAccount[index].id;
      this.dataAdd.username = this.dataGenName;
      this.dataAdd.password = this.dataAddInput.passwordInput;
      this.getDataAddAccount();
      this.visibleAdd = false;
      this.$notification["success"]({
        message: "Thông báo",
        description: "Thêm tài khoản thành công",
      });
    },
    handleCancelAdd() {
      this.visibleAdd = false;
    },
    getDataAddAccount() {
      accountService
        .addAccount(this.dataAdd)
        .then((response) => {
          if (response.data.data) {
            this.getAllAccount();
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
    generateUsername(nameNotAcc) {
      accountService.generateUsername(nameNotAcc).then((response) => {
        this.dataGenName = response.data.data;
      });
    },
    showModalEdit(username, roles, id, status) {
      this.dataEdit.id = id;
      this.dataEdit.username = username;
      this.dataEdit.roleIDs = [];
      for (var i = 0; i < roles.length; i++) {
        this.dataEdit.roleIDs.push(roles[i].id);
      }
      this.dataEdit.status = status;
      this.visibleEdit = true;
    },
    handleCancelEdit() {
      this.visibleEdit = false;
    },
    submitEdit() {
      this.dataUpdateAccount.idAccount = this.dataEdit.id;
      this.dataUpdateAccount.idRole = this.dataEdit.roleIDs;

      this.dataUpdateAccount.status = this.dataEdit.status;
      this.visibleEdit = false;
      this.getDataUpdateAccount();
    },
    getDataUpdateAccount() {
      accountService
        .updateAccount(this.dataUpdateAccount)
        .then((response) => {
          if (response.data.data) {
            var task = "success";
            var text = "Sửa";
            this.notifi(task, text);
            this.getAllAccount();
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
    submitSearch() {
      this.dataSearch.name = this.name;
      this.dataSearch.pageIndex = 1;
      this.dataSearch.pageSize = 10;
      this.getDataSearch();
    },
    async getDataSearch() {
      accountService
        .searchAccount(this.dataSearch)
        .then((response) => {
          this.dataSourceTable = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    getAccountByID(id) {
      accountService
        .getAccountByID(id)
        .then((response) => {
          this.dataAccByID = response.data.data;
          this.visibleProfile = true;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    notifi(task, text) {
      this.$notification[task]({
        message: "Thông báo",
        description: text + " thành công",
      });
    },
    deleteAccount(id) {
      accountService
        .deleteAccount(id)
        .then((response) => {
          if (response.data.data) {
            var task = "success";
            var text = "Xóa";
            this.notifi(task, text);
            this.getAllAccount();
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
  },
};
</script>

<style scoped>
/* button icon */
#delete:hover {
  background-color: #f56c6c;
  color: white;
}
#edit:hover {
  background-color: #18cfc9;
  color: white;
}
#user:hover {
  background-color: #13ce66;
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
  font-size: 14px;
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