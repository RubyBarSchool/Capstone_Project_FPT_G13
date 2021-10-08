<template>
  <div class="adminv2">
    <!-- menu trên -->
    <a-row :gutter="[8, 8]">
      <a-col :span="4"
        ><a-input
          placeholder="Tên tài khoản"
          style="width: 150px"
          v-model="name"
      /></a-col>
      <a-col :span="4">
        <a-select placeholder="Chức vụ" style="width: 150px">
          <a-select-option
            v-for="item in dataRoles"
            :key="item.id"
            :value="item.id"
          >
            {{ item.roleName }}
          </a-select-option>
        </a-select>
      </a-col>
      <a-col :span="4">
        <a-select placeholder="Trạng thái" style="width: 150px">
          <a-select-option value="1"> Nháp </a-select-option>
          <a-select-option value="0"> Công khai </a-select-option>
        </a-select>
      </a-col>
      <a-col :span="4">
        <a-select placeholder="ID Tăng dần" style="width: 150px">
          <a-select-option value="1"> ID Tăng dần </a-select-option>
          <a-select-option value="0"> ID Giảm dần </a-select-option>
        </a-select>
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
              type="dashed"
              icon="user"
              @click="getAccountByID(record.id)"
            />
          </a-col>
          <a-col :span="8">
            <a-button
              type="dashed"
              icon="edit"
              @click="showModalEdit(record.username, record.roles, record.id)"
            />
          </a-col>
          <a-col :span="8">
            <a-popconfirm
              v-if="dataSourceTable.length"
              title="Bạn có chắc chắn muốn xóa không?"
              @confirm="() => onDelete(record.id)"
            >
              <a-button type="dashed" icon="delete" />
            </a-popconfirm>
          </a-col>
        </a-row>
      </template>
    </a-table>

    <!-- popup profile-->
    <a-modal v-model="visibleProfile" title="Thông tin tài khoản" class="profile">
      <img src="../assets/logoProject.png">
      <h3>{{ dataAccByID.fullname }}</h3>
      <h3>{{ dataAccByID.dob }}</h3>
      <h3>{{ dataAccByID.phone }}</h3>
      <h3>{{ dataAccByID.gender ? "Nam" : "Nữ" }}</h3>
      <h3 v-for="(rol, index) in dataAccByID.roles" :value="rol.id" :key="index">
        {{ rol.name }}
      </h3>
    </a-modal>
    <!-- popup profile-->

    <!-- popup add-->
    <a-modal v-model="visibleAdd" title="Thêm tài khoản" @ok="handleAdd">
      <a-form-model-item label="Tài khoản">
        <a-input disabled />
      </a-form-model-item>
      <a-form-model-item label="Mật khẩu">
        <a-input />
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
      <a-form-model-item label="Nhân viên">
        <a-select
          show-search
          placeholder="Nhập tên nhân viên"
          option-filter-prop="children"
          style="width: 472px"
          :filter-option="filterOption"
          @change="handleChange"
        >
          <a-select-option
            v-for="item in dataEmployees"
            :key="item.id"
            :value="item.id"
          >
            {{ item.name }}
          </a-select-option>
        </a-select>
      </a-form-model-item>
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
          <a-input :value="dataEdit.username" disabled />
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
      dataUpdateAccount: {
        idAccount: "",
        idRole: [""],
        status: "",
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
      },
      dataSearch: {
        name: "",
        pageIndex: "1",
        pageSize: "10",
      },
      dataAdd: {
        idRole: [0],
        id_employee: 0,
        password: "",
        username: "",
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
    getAllEmployee() {
      employeeService
        .getAllEmployee(this.dataEmployee)
        .then((response) => {
          this.dataEmployees = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    handleChange(value) {
      console.log(`selected ${value}`);
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
      this.getAllEmployee();
      this.visibleAdd = true;
    },
    handleAdd(e) {
      console.log(e);
      this.visibleAdd = false;
    },
    showModalEdit(username, roles, id) {
      this.visibleEdit = true;
      this.dataEdit.id = id;
      this.dataEdit.username = username;
      this.dataEdit.roleIDs = [];
      for (var i = 0; i < roles.length; i++) {
        this.dataEdit.roleIDs.push(roles[i].id);
      }
    },
    onDelete(id) {
      const dataSource = [...this.dataSourceTable];
      this.dataSourceTable = dataSource.filter((item) => item.id !== id);
    },
    handleCancelEdit() {
      this.visibleEdit = false;
    },
    submitEdit() {
      this.dataUpdateAccount.idAccount = this.dataEdit.id;
      this.dataUpdateAccount.idRole = this.dataEdit.roleIDs;
      this.dataUpdateAccount.status = true;
      this.getDataUpdateAccount();
      this.visibleEdit = false;
      this.$notification["success"]({
        message: 'Thông báo',
        description: 'Sửa thành công',
      });
    },
    getDataUpdateAccount() {
      accountService
        .updateAccount(this.dataUpdateAccount)
        .then((response) => {
          if (response.data.data) {
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
      accounService
        .getAccountByID(id)
        .then((response) => {
          this.dataAccByID = response.data.data;
          console.log("Data account by id:", this.dataAccByID.fullname);
          this.visibleProfile = true;
        })
        .catch((e) => {
          console.log(e);
        });
    },
  },
};
</script>

<style>
img {
  width: 150px;
  height: 150px;
  border-radius: 78px;
}
.profile{
  text-align: center;
}
</style>