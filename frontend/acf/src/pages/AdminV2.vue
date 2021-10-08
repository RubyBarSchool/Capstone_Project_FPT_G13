<template>
  <div class="adminv2">
    <!-- menu trên -->
    <a-row :gutter="[8, 8]">
      <a-col :span="4"
        ><a-input placeholder="Tên tài khoản" style="width: 150px"
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
        <a-button type="primary" icon="search"> Tìm kiếm </a-button>
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
            <a-button type="dashed" icon="user" @click="showModalProfile" />
          </a-col>
          <a-col :span="8">
            <a-button
              type="dashed"
              icon="edit"
              @click="showModalEdit(record.username, record.roles)"
            />
          </a-col>
          <a-col :span="8">
            <a-button type="dashed" icon="delete" @click="showModalDelete" />
            <!-- <a-popconfirm
              v-if="dataSourceTable.length"
              title="Sure to delete?"
              @confirm="() => onDelete(record.id)"
            >
              <a-button type="dashed" icon="delete" />
            </a-popconfirm> -->
          </a-col>
        </a-row>
      </template>
    </a-table>

    <!-- popup profile-->
    <a-modal
      v-model="visibleProfile"
      title="Thông tin tài khoản"
      @ok="handleProfile"
    >
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
        <a-select style="width: 472px">
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
    <a-modal
      v-model="visibleEdit"
      title="Chỉnh sửa tài khoản"
    >
      <template slot="footer">
        <a-button key="back" @click="handleCancelEdit"> Hủy </a-button>
        <a-button key="submit" type="primary" @click="submitEdit"> Lưu </a-button>
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
import accounService from "@/service/accountService.js";
import roleService from "@/service/roleService.js";
import employeeService from "@/service/employeeService.js";
export default {
  name: "AdminV2",
  data() {
    return {
      name: "",
      dataSourceTable: [],
      dataRoles: [],
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
      accounService
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
    showModalEdit(username, roles) {
      this.visibleEdit = true;
      this.dataEdit.username = username;
      this.dataEdit.roleIDs = [];
      for (var i = 0; i < roles.length; i++) {
        this.dataEdit.roleIDs.push(roles[i].id);
      }
    },
    showModalProfile() {
      this.getAllAccount();
      this.visibleProfile = true;
    },
    handleProfile(e) {
      console.log(e);
      this.visibleProfile = false;
    },
    showModalDelete() {
      this.visibleDelete = true;
      this.$confirm({
        title: "Bạn có chắc chắn muốn xóa không?",
        okText: "Yes",
        okType: "danger",
        cancelText: "No",
        onOk() {
          console.log("OK");
        },
        onCancel() {
          console.log("Cancel");
        },
      });
    },
    handleDelete(e) {
      console.log(e);
      this.visibleDelete = false;
    },
    handleCancelEdit() {
      this.visibleEdit = false;
    },
    submitEdit(){
      console.log("List: ", this.dataEdit)
    }
  },
};
</script>

<style>
</style>