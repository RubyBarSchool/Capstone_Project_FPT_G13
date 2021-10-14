<template>
  <div class="admin">
    <!-- menu trên -->
    <a-row :gutter="[8, 8]">
      <a-col :span="4"
        ><a-input
          placeholder="Tên tài khoản"
          style="width: 150px"
          v-model="dataSearch.name"
      /></a-col>
      <a-col :span="4">
        <a-select
          placeholder="Chức vụ"
          mode="multiple"
          v-model="dataSearch.listRole"
          :filter-option="false"
          @search="fetchRoles"
          style="width: 300px"
        >
          <a-select-option
            v-for="(role, index) in dataRoles"
            :value="role.id"
            :key="index"
          >
            {{ role.name }}
          </a-select-option>
        </a-select>
      </a-col>
      <a-col :span="4">
        <a-select
          placeholder="Trạng thái"
          mode="multiple"
          v-model="dataSearch.listStatus"
          style="width: 150px"
        >
          <a-select-option value="false"> Nháp </a-select-option>
          <a-select-option value="true"> Công khai </a-select-option>
        </a-select>
      </a-col>
      <a-col :span="4">
        <a-range-picker v-model="dataSearch.date" />
      </a-col>
      <a-col :span="4" style="width: 150px">
        <a-button type="primary" icon="search" @click="submitSearch">
          Tìm kiếm
        </a-button>
      </a-col>

      <a-col :span="4" style="width: 150px">
        <a-button type="primary" icon="usergroup-add" @click="showModalAdd">
          Thêm
        </a-button>
      </a-col>
    </a-row>
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
                  record.id,
                  record.username,
                  record.roles,
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

    <a-modal
      v-model="visibleProfile"
      title="Thông tin tài khoản"
      class="profile"
    >
      <template slot="footer">
        <a-button key="a" hidden></a-button>
        <a-button key="b" hidden></a-button>
      </template>
      <div class="av">
        <img src="../assets/logoProject.png" />
        <h2>
          <b>{{ dataAccountDetail.fullname }}</b>
        </h2>
      </div>
      <h4>
        <a-icon type="calendar" style="font-size: 18px" /> Sinh ngày:
        <span class="a">{{ dataAccountDetail.dob }}</span>
      </h4>
      <h4>
        <a-icon type="phone" style="font-size: 18px" /> Số điện thoại:
        <span class="a">{{ dataAccountDetail.phone }}</span>
      </h4>
      <h4>
        <span style="font-size: 14px">⚥</span> Giới tính:
        {{ dataAccountDetail.gender ? "Nam" : "Nữ" }}
      </h4>
      <h4>
        <a-icon type="solution" style="font-size: 18px" /><span class="a">
          Chức vụ:
        </span>
      </h4>
      <h4
        v-for="(role, index) in dataAccountDetail.roles"
        :value="role.id"
        :key="index"
      >
        <ul>
          <li>{{ role.name }}</li>
        </ul>
      </h4>
    </a-modal>
    <!-- popup profile-->

    <!-- popup add-->
    <a-modal v-model="visibleAdd" title="Thêm tài khoản">
      <template slot="footer">
        <a-button key="back" @click="handleCancel"> Hủy </a-button>
        <a-button key="submit" type="primary" @click="submitAdd">
          Lưu
        </a-button>
      </template>
      <a-form-model>
        <a-form-model-item label="Tài khoản">
          <a-input v-model="dataAdd.username" disabled />
        </a-form-model-item>
        <a-form-model-item label="Mật khẩu">
          <a-input v-model="dataAdd.password" />
        </a-form-model-item>
        <a-form-model-item label="Chức vụ">
          <a-select
            placeholder="Chức vụ"
            mode="multiple"
            v-model="dataAdd.listRole"
            :filter-option="false"
            @search="fetchRoles"
            style="width: 100%"
          >
            <a-select-option
              v-for="(role, index) in dataRoles"
              :value="role.id"
              :key="index"
            >
              {{ role.name }}
            </a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item label="Nhân viên">
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
        <a-form-model-item label="Tài khoản">
          <a-input v-model="dataEdit.username" disabled />
        </a-form-model-item>
        <a-form-model-item label="Chức vụ">
          <a-select
            placeholder="Chức vụ"
            mode="multiple"
            v-model="dataEdit.listRole"
            :filter-option="false"
            @search="fetchRoles"
            style="width: 100%"
          >
            <a-select-option
              v-for="(role, index) in dataRoles"
              :value="role.id"
              :key="index"
            >
              {{ role.name }}
            </a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item label="Trạng thái">
          <a-radio-group name="radioGroup" v-model="dataEdit.status">
            <a-radio :value="false"> Nháp </a-radio>
            <a-radio :value="true"> Công khai </a-radio>
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
import adminTruongService from "../service/adminTruongService";
export default {
  name: "AdminTruong",
  data() {
    return {
      dataSearch: {
        name: "",
        listRole: [],
        listStatus: [],
        date: [],
        pageIndex: 1,
        pageSize: 10,
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
  computed: {},
  created() {
    this.submitSearch();
    this.getAllRole();
  },
  methods: {
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
          console.log(this.dataRoles);
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
        })
        .catch((e) => {
          console.log(e);
        });
      this.visibleAdd = false;
      this.$notification["success"]({
        message: "Thông báo",
        description: "Thêm tài khoản thành công",
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
          if (response.data.data) {
            this.submitSearch();
            var task = "success";
            var text = "Sửa";
            this.notifi(task, text);
             this.submitSearch();
          }
        })
        .catch((e) => {
          console.log(e);
        });
      this.visibleEdit = false;
    },
    submitSearch() {
      adminTruongService
        .searchAccount(this.dataSearch)
        .then((response) => {
          this.dataSourceTable = response.data.data;
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
          console.log("dataAccountDetail", this.dataAccountDetail);
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
            var task = "success";
            var text = "Xóa";
            this.notifi(task, text);
            this.submitSearch();
          }
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
  },
};
</script>

<style>
img {
  width: 127px;
  height: 127px;
  border-radius: 78px;
}

.av {
  text-align: center;
}
#delete:hover {
  background-color: #f56c6c;
  color: white;
}
#edit:hover {
  background-color: #64d9d6;
  color: white;
}
#user:hover {
  background-color: #13ce66;
  color: white;
}
span.a {
  display: inline;
}
.profile h4 {
  width: 300px;
  margin: 15px;
  margin-left: auto;
}
</style>