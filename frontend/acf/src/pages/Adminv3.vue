<template>
  <div>
    <!-- màn add -->
    <a-table
      :columns="columns"
      :data-source="data"
      :rowKey="
        (record, index) => {
          return index;
        }
      "
    >
      <!-- <template slot="id" slot-scope="text, record">
        {{ record.id }}
      </template> -->
      <!-- <p slot="id" slot-scope="text, record">{{ record.id }}</p> -->
      <!-- <span slot="customTitle"> ID</span> -->
      <template slot="roles" slot-scope="text, record">
        <div v-for="item in record.roles" :key="item.id">
          {{ item.name }}
        </div>
      </template>

      <template slot="status" slot-scope="text, record">
        <a-tag :color="record.status ? 'green' : 'blue'">
          {{ record.status ? "Công khai" : "Nháp" }}
        </a-tag>
      </template>

      <template slot="actions" slot-scope="text, record">
        <!-- event edit -->
        <a-button
          type="primary"
          @click="showModal(record.username, record.roles, record.id)"
          icon="edit"
        >
          Sửa
        </a-button>
        <a-divider type="vertical" />
        <!-- event xóa -->
        <a-popconfirm
          title="Bạn có chắc chắn muốn xóa?"
          ok-text="Đồng ý"
          cancel-text="Hủy"
        >
          <a-button icon="delete">Xóa</a-button>
        </a-popconfirm>
      </template>
    </a-table>

    <a-modal v-model="visible" title="Chỉnh sửa tài khoản" on-ok="handleOk">
      <template slot="footer">
        <a-button key="back" @click="handleCancel"> Hủy </a-button>
        <a-button
          key="submit"
          type="primary"
          :loading="loading"
          @click="submitUpdateAccount"
        >
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
            placeholder="Please select"
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
  </div>
</template>

<script>
const columns = [
  {
    title: "ID",
    dataIndex: "id",
    key: "id",
    slots: { title: "customTitle" },
    scopedSlots: { customRender: "id" },
  },
  {
    title: "Tài Khoản",
    dataIndex: "username",
    key: "username",
    scopedSlots: { customRender: "username" },
  },
  {
    title: "Chức vụ",
    dataIndex: "roles",
    key: "roles",
    scopedSlots: { customRender: "roles" },
  },
  {
    title: "Trạng thái",
    dataIndex: "status",
    key: "status",
    scopedSlots: { customRender: "status" },
  },
  {
    title: "Thời gian",
    dataIndex: "time",
    key: "time",
    scopedSlots: { customRender: "time" },
  },
  {
    title: "Hành động",
    dataIndex: "actions",
    key: "actions",
    scopedSlots: { customRender: "actions" },
  },
];

import rolesystem from "@/service/rolesystem.js";
import employeeService from "@/service/employeeService.js";
import roleService from "@/service/roleService.js";

export default {
  name: "Profile",
  data() {
    return {
      dataEdit: {
        username: "",
        roleIDs: [],
        id:""
      },
      name: "",
      data: [],
      dataEmployees: [],
      dataRoles: [],
      serverData: [],
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
    showModal(username, roles, id) {
      this.visible = true;
      this.dataEdit.id= id;
      this.dataEdit.username = username;
      this.dataEdit.roleIDs = [];
      for (var i = 0; i < roles.length; i++) {
        this.dataEdit.roleIDs.push(roles[i].id);
      }
    },
    showModalThem() {
      this.visiblethem = true;
    },
    handleAdd() {},

    handleEdit() {},

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
          this.data = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    handleOk() {
      console.log("Form edit account", this.dataEdit);
      console.log(this.dataEdit.id)
    },
    submitUpdateAccount() {
      this.dataUpdateAccount.idAccount = this.dataEdit.id;
      this.dataUpdateAccount.idRole = this.dataEdit.roleIDs;
      this.dataUpdateAccount.id_employee = 10;
      this.dataUpdateAccount.username = this.dataEdit.username;
      this.getDataUpdateAccount(this.data.id);
      
    },
    getDataUpdateAccount(id) {
      rolesystem
        .updateAccount(id, this.dataUpdateAccount)
        .then((response) => {
            for(var i=0; i<this.dataUpdateAccount.length;i++){
                if(this.dataUpdateAccount.id==response.data.data.idAccount)
                this.data[i] = response.data.data;
            }
        })
        .catch((e) => {
          console.log(e);
        });
    },
  },
};
</script>

<style></style>
