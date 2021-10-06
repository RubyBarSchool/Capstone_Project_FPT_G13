<template>
  <div>
    <a-input-group>
      <a-row :gutter="16">
        <a-col :span="4">
          <a-input
            placeholder="Tên tài khoản"
            style="width: 150px"
            v-model="name"
          />
        </a-col>

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

        <a-col :span="5">
          <a-button type="primary" icon="search" @click="submitSearch">
            Tìm kiếm
          </a-button>
        </a-col>

        <a-col :span="5">
          <a-button type="primary" icon="edit" @click="showModalThem">
            Thêm
          </a-button>
        </a-col>
      </a-row>
    </a-input-group>
    <br />

    <!-- màn add -->
    <a-modal v-model="visiblethem" title="Thêm tài khoản" on-ok="handleAdd">
      <template slot="footer">
        <a-button
          key="submit"
          type="primary"
          :loading="loading"
          @click="handleAdd"
        >
          Lưu
        </a-button>
      </template>
      <a-form-model :layout="form.layout" :model="form">
        <a-form-model-item label="Tài khoản">
          <a-input value="truongtv012" disabled />
        </a-form-model-item>
        <a-form-model-item label="Mật khẩu">
          <a-input />
        </a-form-model-item>
        <a-form-model-item label="Chức vụ">
          <a-select>
            <a-select-option
              v-for="item in dataRoles"
              :key="item.id"
              :value="item.id"
            >
              {{ item.roleName }}
            </a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item label="Nhân viên">
          <a-select
            show-search
            placeholder="Nhập tên nhân viên"
            option-filter-prop="children"
            :filter-option="filterOption"
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
      </a-form-model>
    </a-modal>
    <!-- màn add -->

    <a-table :columns="columns" :data-source="data">
      <p slot="id" slot-scope="id">{{ id }}</p>
      <span slot="customTitle"> ID</span>
      <span slot="status" slot-scope="status">
        <a-tag :color="status ? 'green' : 'blue'">
          {{ status ? "Công khai" : "Nháp" }}
        </a-tag>
      </span>
      <span slot="hanhdong" slot-scope="record">
        <!-- event edit -->
        <a-button
          type="primary"
          @click="showModal"
          icon="edit"
          v-model="record.id"
        >
          Sửa
        </a-button>

        <a-modal v-model="visible" title="Chỉnh sửa tài khoản" on-ok="handleOk">
          <template slot="footer">
            <a-button key="back" @click="handleCancel"> Hủy </a-button>
            <a-button
              key="submit"
              type="primary"
              :loading="loading"
              @click="handleOk"
            >
              Lưu
            </a-button>
          </template>
          <a-form-model :layout="form.layout" :model="form">
            <a-form-model-item label="Tài khoản">
              <a-input :value="record.username" disabled />
            </a-form-model-item>
            <a-form-model-item label="Chức vụ">
              <a-select :value="record.roles">
                <a-select-option :value="record.roles" v-for="(rol,index) in dataRoles" :key="index"> {{rol.roleName}} </a-select-option>
              </a-select>
            </a-form-model-item>
          </a-form-model>
        </a-modal>
        <a-divider type="vertical" />
        <!-- event xóa -->
        <a-popconfirm
          title="Bạn có chắc chắn muốn xóa?"
          ok-text="Đồng ý"
          cancel-text="Hủy"
        >
          <a-button icon="delete">Xóa</a-button>
        </a-popconfirm>
      </span>
    </a-table>

    <!-- popup edit -->
    <a-modal v-model="visible" title="Chỉnh sửa tài khoản" on-ok="handleEdit">
      <template slot="footer">
        <a-button key="back" @click="handleCancel"> Hủy </a-button>
        <a-button
          key="submit"
          type="primary"
          :loading="loading"
          @click="handleEdit"
        >
          Lưu
        </a-button>
      </template>
      <a-form-model :layout="form.layout" :model="form">
        <a-form-model-item label="Tài khoản">
          <a-input value="truongtv" disabled />
        </a-form-model-item>
        <a-form-model-item label="Chức vụ">
          <a-select value="quanly">
            <a-select-option value="nhanvien"> Nhân viên </a-select-option>
            <a-select-option value="quanly"> Quản lý </a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item label="Trạng thái">
          <a-input value="Nháp" />
        </a-form-model-item>
      </a-form-model>
    </a-modal>
    <!-- popup edit -->
  </div>
</template>

<script>
const columns = [
  {
    dataIndex: "id",
    slots: { title: "customTitle" },
    scopedSlots: { customRender: "id" },
  },
  {
    title: "Tài Khoản",
    dataIndex: "username",
  },
  {
    title: "Chức vụ",
    dataIndex: "roles",
  },
  {
    title: "Trạng thái",
    dataIndex: "status",
    scopedSlots: { customRender: "status" },
  },
  {
    title: "Thời gian",
    dataIndex: "time",
    scopedSlots: { customRender: "thoigian" },
  },
  {
    title: "Hành động",
    scopedSlots: { customRender: "hanhdong" },
  },
];

import rolesystem from "@/service/rolesystem.js";
import employeeService from "@/service/employeeService.js";
import roleService from "@/service/roleService.js";


export default {
  name: "Profile",
  data() {
    return {
      name: "",
      data: [],
      dataEmployees: [],
      dataRoles: [],
      columns,
      visible: false,
      visiblethem: false,
      loading: false,
      layout: "horizontal",
      allAccountList: {
        name: "",
        pageIndex: "1",
        pageSize: "10",
      },
      dataSearch: {
        name: "",
        pageIndex: "1",
        pageSize: "10",
      },
      dataEmployee: {
        name: "",
        pageIndex: "1",
        pageSize: "10",
      },
      dataRole: {
        data: {},
        message: "",
        status: 0,
      },
      dataUpdateAccount: {
        idAccount: 0,
        idRole: [0],
        id_employee: 0,
        password: "",
        username: "",
      },
      form: {
        name: "",
        pass: "",
        region: undefined,
        date1: undefined,
        delivery: false,
        type: [],
        resource: "",
        desc: "",
      },
    };
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  created() {
    this.getAllAccount();
    this.getAllEmployee();
    this.getAllRole();

  },
  methods: {
    showModal() {
      this.visible = true;
    },
    showModalThem() {
      this.visiblethem = true;
    },
    handleAdd() {},

    handleEdit() {},
    // handleBlur() {
    //   console.log("blur");
    // },
    // handleFocus() {
    //   console.log("focus");
    // },
    filterOption(input, option) {
      return (
        option.componentOptions.children[0].text
          .toLowerCase()
          .indexOf(input.toLowerCase()) >= 0
      );
    },
    handleCancel() {
      this.visible = false;
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

    getAllAccount() {
      let form = {
        pageIndex: 1,
        pageSize: 10,
      };
      rolesystem
        .getAllAccount(form)
        .then((response) => {
          response = response.data.data;
          for (var x = 0; x < response.length; x++) {
            let datasource = {
              key: "",
              username: "",
              time: "",
              status: "",
              roles: [],
              password: "",
              id: "",
            };
            datasource.key = response[x].id;
            datasource.id = response[x].id;
            datasource.username = response[x].username;
            datasource.time = response[x].time;
            datasource.status = response[x].status;
            datasource.password = response[x].password;
            for (var z = 0; z < response[x].roles.length; z++) {
              datasource.roles.push(response[x].roles[z].name);
            }
            this.data.push(datasource);
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
      this.getDataSearchxx();
    },
    async getDataSearchxx() {
      rolesystem
        .searchAccount(this.dataSearch)
        .then((response) => {
          this.data = [];
          response = response.data.data;
          for (var x = 0; x < response.length; x++) {
            let datasource = {
              key: "",
              username: "",
              time: "",
              status: "",
              roles: [],
              password: "",
              id: "",
            };
            datasource.key = response[x].id;
            datasource.id = response[x].id;
            datasource.username = response[x].username;
            datasource.time = response[x].time;
            datasource.status = response[x].status;
            datasource.password = response[x].password;
            for (var z = 0; z < response[x].roles.length; z++) {
              datasource.roles.push(response[x].roles[z].name);
            }
            this.data.push(datasource);
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
    submitUpdateAccount() {
      this.dataUpdateAccount.idAccount = this.id;
      this.dataUpdateAccount.idRole = 1;
      this.dataUpdateAccount.id_employee = 10;
      // this.dataUpdateAccount.password = 10;
      this.dataUpdateAccount.username = this.username;
      this.getDataSearchxx();
    },
    async getDataUpdateAccount() {
      rolesystem
        .updateAccount(this.id, this.updateAccount)
        .then((response) => {
          this.data = [];
          response = response.data.data;
          for (var x = 0; x < response.length; x++) {
            let datasource = {
              key: "",
              username: "",
              time: "",
              status: "",
              roles: [],
              password: "",
              id: "",
            };
            datasource.key = response[x].id;
            datasource.id = response[x].id;
            datasource.username = response[x].username;
            datasource.time = response[x].time;
            datasource.status = response[x].status;
            datasource.password = response[x].password;
            for (var z = 0; z < response[x].roles.length; z++) {
              datasource.roles.push(response[x].roles[z].name);
            }
            this.data.push(datasource);
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
  },
};
</script>

<style>
</style>