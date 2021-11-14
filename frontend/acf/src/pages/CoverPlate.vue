<template>
  <div class="admin">
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
            placeholder="Mã tấm phủ"
            style="width: 150px"
            v-model="dataSearch.name"
          />
          <a-select
            placeholder="Khung"
            mode="multiple"
            v-model="dataSearch"
            :filter-option="false"
            @search="fetchRoles"
            style="width: 150px"
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
            placeholder="Nhóm tấm phủ"
            mode="multiple"
            v-model="dataSearch"
            style="width: 150px"
          >
            <a-select-option value="false"> MCF </a-select-option>
            <a-select-option value="true"> HCF </a-select-option>
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
              :icon="['fas', 'file-signature']"
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
          <a-modal v-model="visibleAdd" title="Thêm tấm phủ">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary" @click="checkFormAdd">
                Lưu
              </a-button>
            </template>
            <a-form-model>
              <a-form-model-item label="Mã tấm phủ">
                <div>
                  <template v-for="(tag, index) in tags">
                    <a-tooltip v-if="tag.length > 20" :key="tag" :title="tag">
                      <a-tag
                        :key="tag"
                        :closable="index !== 0"
                        @close="() => handleClose(tag)"
                      >
                        {{ `${tag.slice(0, 20)}...` }}
                      </a-tag>
                    </a-tooltip>
                    <a-tag
                      v-else
                      :key="tag"
                      :closable="index !== 0"
                      @close="() => handleClose(tag)"
                    >
                      {{ tag }}
                    </a-tag>
                  </template>
                  <a-input
                    v-if="inputVisible"
                    ref="input"
                    type="text"
                    size="small"
                    :style="{ width: '78px' }"
                    :value="inputValue"
                    @change="handleInputChange"
                    @blur="handleInputConfirm"
                    @keyup.enter="handleInputConfirm"
                  />
                  <a-tag
                    v-else
                    style="background: #fff; borderstyle: dashed"
                    @click="showInput"
                  >
                    <a-icon type="plus" /> New Tag
                  </a-tag>
                </div>
              </a-form-model-item>
              <a-form-model-item label="Khung">
                <a-select
                  placeholder="Khung"
                  mode="multiple"
                  v-model="dataSearch"
                  :filter-option="false"
                  @search="fetchRoles"
                  style="width: 150px"
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
              <a-form-model-item label="Chiều cao">
                <a-select
                  placeholder="Chiều cao"
                  mode="multiple"
                  v-model="dataSearch"
                  style="width: 150px"
                >
                  <a-select-option value="false"> MCF </a-select-option>
                  <a-select-option value="true"> HCF </a-select-option>
                </a-select>
              </a-form-model-item>
              <a-form-model-item label="Nhóm tấm phủ">
                <a-select
                  placeholder="Nhóm tấm phủ"
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
              <a-form-model-item label="Đơn vị">
                <a-select
                  placeholder="Đơn vị"
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
              <a-form-model-item label="Công ty">
                <a-select
                  placeholder="Công ty"
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
              <a-form-model-item label="Giá thành">
                <a-input
                  placeholder="Giá thành"
                  style="width: 150px"
                  v-model="dataSearch.name"
                />
              </a-form-model-item>
            </a-form-model>
          </a-modal>
          <!-- popup add -->

          <!-- popup edit-->
          <a-modal v-model="visibleEdit" title="Chỉnh sửa tấm phủ">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary" @click="submitUpdate">
                Lưu
              </a-button>
            </template>
            <a-form-model>
              <a-form-model-item label="Mã tấm phủ">
                <div>
                  <template v-for="(tag, index) in tags">
                    <a-tooltip v-if="tag.length > 20" :key="tag" :title="tag">
                      <a-tag
                        :key="tag"
                        :closable="index !== 0"
                        @close="() => handleClose(tag)"
                      >
                        {{ `${tag.slice(0, 20)}...` }}
                      </a-tag>
                    </a-tooltip>
                    <a-tag
                      v-else
                      :key="tag"
                      :closable="index !== 0"
                      @close="() => handleClose(tag)"
                    >
                      {{ tag }}
                    </a-tag>
                  </template>
                  <a-input
                    v-if="inputVisible"
                    ref="input"
                    type="text"
                    size="small"
                    :style="{ width: '78px' }"
                    :value="inputValue"
                    @change="handleInputChange"
                    @blur="handleInputConfirm"
                    @keyup.enter="handleInputConfirm"
                  />
                  <a-tag
                    v-else
                    style="background: #fff; borderstyle: dashed"
                    @click="showInput"
                  >
                    <a-icon type="plus" /> New Tag
                  </a-tag>
                </div>
              </a-form-model-item>
              <a-form-model-item label="Khung">
                <a-select
                  placeholder="Khung"
                  mode="multiple"
                  v-model="dataSearch"
                  :filter-option="false"
                  @search="fetchRoles"
                  style="width: 150px"
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
              <a-form-model-item label="Chiều cao">
                <a-select
                  placeholder="Chiều cao"
                  mode="multiple"
                  v-model="dataSearch"
                  style="width: 150px"
                >
                  <a-select-option value="false"> MCF </a-select-option>
                  <a-select-option value="true"> HCF </a-select-option>
                </a-select>
              </a-form-model-item>
              <a-form-model-item label="Nhóm tấm phủ">
                <a-select
                  placeholder="Nhóm tấm phủ"
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
              <a-form-model-item label="Đơn vị">
                <a-select
                  placeholder="Đơn vị"
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
              <a-form-model-item label="Công ty">
                <a-select
                  placeholder="Công ty"
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
              <a-form-model-item label="Giá thành">
                <a-input
                  placeholder="Giá thành"
                  style="width: 150px"
                  v-model="dataSearch.name"
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
import adminService from "@/service/adminService";
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";

export default {
  name: "Admin",
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
          title: "Mã tấm phủ",
          dataIndex: "coverplatecode",
          key: "coverplatecode",
          width: 150,
          scopedSlots: { customRender: "coverplatecode" },
        },
        {
          title: "Thông số",
          dataIndex: "specifications",
          key: "specifications",
          width: 150,
          scopedSlots: { customRender: "specifications" },
        },
        {
          title: "Đơn vị",
          dataIndex: "unit",
          key: "unit",
          width: 150,
          scopedSlots: { customRender: "unit" },
        },
        {
          title: "Công ty",
          dataIndex: "company",
          key: "company",
          width: 150,
          scopedSlots: { customRender: "company" },
        },
        {
          title: "Giá thành",
          dataIndex: "price",
          key: "price",
          width: 150,
          scopedSlots: { customRender: "price" },
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
      checkDataInput: {
        show: false,
        message: "",
      },
      tags: ["Unremovable", "Tag 2", "Tag 3Tag 3Tag 3Tag 3Tag 3Tag 3Tag 3"],
      inputVisible: false,
      inputValue: "",
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
      this.checkDataInput.show = false;
      this.checkDataInput.message = "";
    },
    checkFormAdd() {
      if (this.dataAdd.password != null && this.dataAdd.password != "") {
        this.checkDataInput.show = false;
        this.checkDataInput.message = "";
        this.submitAdd();
      } else {
        this.checkDataInput.show = true;
        this.checkDataInput.message = "Bạn phải điền vào chỗ trống";
      }
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
      adminService
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
    deleteAccount(id) {
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
            let description = "Account đang đăng nhập không được xóa";
            this.notifi(type, message, description);
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
    //thêm tags
    handleClose(removedTag) {
      const tags = this.tags.filter((tag) => tag !== removedTag);
      console.log(tags);
      this.tags = tags;
    },

    showInput() {
      this.inputVisible = true;
      this.$nextTick(function () {
        this.$refs.input.focus();
      });
    },

    handleInputChange(e) {
      this.inputValue = e.target.value;
    },

    handleInputConfirm() {
      const inputValue = this.inputValue;
      let tags = this.tags;
      if (inputValue && tags.indexOf(inputValue) === -1) {
        tags = [...tags, inputValue];
      }
      console.log(tags);
      Object.assign(this, {
        tags,
        inputVisible: false,
        inputValue: "",
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