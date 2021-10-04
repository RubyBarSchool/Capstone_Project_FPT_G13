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
            <a-select-option value="Quản lý"> Quản lý </a-select-option>
            <a-select-option value="Nhân viên"> Nhân viên </a-select-option>
          </a-select>
        </a-col>

        <a-col :span="4">
          <a-select placeholder="Trạng thái" style="width: 150px">
            <a-select-option value="Nháp"> Nháp </a-select-option>
            <a-select-option value="Công khai"> Công khai </a-select-option>
          </a-select>
        </a-col>

        <a-col :span="5">
          <a-button type="primary" icon="search" @click="submit">
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
    <a-modal v-model="visiblethem" title="Thêm tài khoản" on-ok="handleOk">
      <template slot="footer">
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
          <a-select value="truongtv012">
            <a-select-option value="truongtv012"> truongtv012 </a-select-option>
            <a-select-option value="truongtv123"> truongtv123 </a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item label="Họ và tên">
          <a-input value="Trần Vũ Trường" />
        </a-form-model-item>
        <a-form-model-item label="Mật khẩu">
          <a-input value="applewatchseries6" />
        </a-form-model-item>
        <a-form-model-item label="Chức vụ">
          <a-input value="Nhân viên" />
        </a-form-model-item>
      </a-form-model>
    </a-modal>
    <a-table :columns="columns" :data-source="data">
      <p slot="id" slot-scope="id">{{ id }}</p>
      <span slot="customTitle"> ID</span>
      <span slot="status" slot-scope="status">
        <a-tag :color="status ? 'green' : 'blue'">
          {{ status ? "Công khai" : "Nháp" }}
        </a-tag>
      </span>
      <span slot="hanhdong">
        <!-- màn edit -->
        <a-button type="primary" @click="showModal" icon="edit"> Sửa </a-button>
        <a-divider type="vertical" />
        <!-- màn xóa -->
        <a-popconfirm
          title="Bạn có chắc chắn muốn xóa?"
          ok-text="Đồng ý"
          cancel-text="Hủy"
        >
          <a-button icon="delete">Xóa</a-button>
        </a-popconfirm>
      </span>
    </a-table>
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
          <a-input value="truongtv" disabled />
        </a-form-model-item>
        <a-form-model-item label="Mật khẩu">
          <a-input value="sfdsfsdfs" />
        </a-form-model-item>
        <a-form-model-item label="Chức vụ">
          <a-select value="quanly">
            <a-select-option value="nhanvien"> Nhân viên </a-select-option>
            <a-select-option value="quanly"> Quản lý </a-select-option>
          </a-select>
        </a-form-model-item>
      </a-form-model>
    </a-modal>
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

export default {
  name: "Profile",
  data() {
    return {
      name: "",
      data: [],
      columns,
      visible: false,
      visiblethem: false,
      loading: false,
      layout: "horizontal",
      allAccountList: {
        name: "",
        pageIndex: "",
        pageSize: "",
      },
      dataSearch: {
        name: "",
        pageIndex: "",
        pageSize: "",
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
  },
  methods: {
    showModal() {
      this.visible = true;
    },
    showModalThem() {
      this.visiblethem = true;
    },
    handleOk() {
      this.loading = true;
      setTimeout(() => {
        this.visible = false;
        this.loading = false;
      }, 1500);
    },
    handleCancel() {
      this.visible = false;
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
    
    submit() {
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
  },
};
</script>

<style>
</style>