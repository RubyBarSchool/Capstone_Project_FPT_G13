<template>
  <div class="User">
    <a-layout :style="{ background: 'white' }">
      <Header />
      <a-layout-content :style="{ margin: '24px 16px 0' }">
        <div
          :style="{
            minHeight: '360px',
            background: 'white',
          }"
        >
          <!-- menu trên -->
          <a-row type="flex">
            <a-col flex="auto">
              <a-input
                placeholder="Tên nhân viên"
                style="width: 150px"
                v-model="dataSearch.name"
              />
              <a-select
                placeholder="Chức vụ"
                :filter-option="false"
                @search="fetchPosition"
                style="width: 120px"
                v-model="dataSearch.idPosition"
              >
                <a-select-option
                  v-for="(position, index) in dataPositions"
                  :value="position.id"
                  :key="index"
                >
                  {{ position.name }}
                </a-select-option>
              </a-select>
              <a-select
                placeholder="Trạng thái"
                style="width: 150px"
                v-model="dataSearch.statusDelete"
              >
                <a-select-option value="false"> Đang làm </a-select-option>
                <a-select-option value="true"> Nghỉ </a-select-option>
              </a-select>
              <a-button type="primary" icon="search" @click="submitSearch">
                Tìm kiếm
              </a-button>
            </a-col>
            <a-col flex="100px">
              <a-button type="primary" icon="user-add" @click="showModalAdd">
                Thêm
              </a-button>
            </a-col>
          </a-row>
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
            <template slot="image" slot-scope="text, record">
              {{ record.image }}
            </template>
            <template slot="employee" slot-scope="text, record">
              {{ record.fullName }}
            </template>
            <template slot="gender" slot-scope="text, record">
              {{ record.gender ? "Nam" : "Nữ" }}
            </template>
            <template slot="position" slot-scope="text, record">
              {{ record.positionName }}
            </template>
            <template slot="status" slot-scope="text, record">
              <a-tag :color="record.statusDelete ? 'red' : 'green'">
                {{ record.statusDelete ? "Nghỉ" : "Đang làm" }}
              </a-tag>
            </template>
            <template slot="email" slot-scope="text, record">
              {{ record.email }}
            </template>
            <template slot="action" slot-scope="text, record">
              <a-row>
                <a-col :span="8">
                  <a-button
                    id="user"
                    type="dashed"
                    icon="user"
                    @click="showDetail(record.id)"
                  />
                </a-col>
                <a-col :span="8">
                  <a-button
                    id="edit"
                    type="dashed"
                    icon="edit"
                    @click="
                      showModalEdit(
                        record
                      )
                    "
                  />
                </a-col>
                <a-col :span="8">
                  <a-popconfirm
                    v-if="dataSourceTable.length"
                    title="Bạn có chắc chắn muốn xóa không?"
                    @confirm="deleteUser(record.id)"
                  >
                    <a-button id="delete" type="dashed" icon="delete"/>
                  </a-popconfirm>
                </a-col>
              </a-row>
            </template>
          </a-table>
          <!-- popup add-->
          <a-modal v-model="visibleAdd" title="Thêm nhân viên">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary" @click="submitAdd">
                Lưu
              </a-button>
            </template>
            <div class="container">
              <a-form-model>
                <div class="row">
                  <div class="col">
                    <a-form-model-item label="Họ Và Tên">
                      <a-input v-model="dataAdd.fullName" />
                    </a-form-model-item>
                    <a-form-model-item label="Ngày Sinh">
                      <a-date-picker v-model="dataAdd.dob">
                        <a-icon slot="suffixIcon" type="smile" />
                      </a-date-picker>
                    </a-form-model-item>
                    <a-form-model-item label="Giới Tính">
                      <a-radio-group
                        v-model="dataAdd.gender"
                        @change="onChange"
                      >
                        <a-radio :value="true"> Nam </a-radio>
                        <a-radio :value="false"> Nữ </a-radio>
                      </a-radio-group>
                    </a-form-model-item>
                    <a-form-model-item label="Chức Vụ">
                      <a-select
                        placeholder="Chức vụ"
                        :filter-option="false"
                        @search="fetchPosition"
                        style="width: 120px"
                        v-model="dataAdd.idPosition"
                      >
                        <a-select-option
                          v-for="(position, index) in dataPositions"
                          :value="position.id"
                          :key="index"
                        >
                          {{ position.name }}
                        </a-select-option>
                      </a-select>
                    </a-form-model-item>
                    <a-form-model-item label="Số Điện Thoại">
                      <a-input v-model="dataAdd.phone" />
                    </a-form-model-item>
                  </div>
                  <div class="col">
                    <a-form-model-item label="Email">
                      <a-input v-model="dataAdd.email" />
                    </a-form-model-item>
                    <a-form-model-item label="Quê Quán">
                      <a-input v-model="dataAdd.address" />
                    </a-form-model-item>
                    <a-form-model-item label="Dân Tộc">
                      <a-input v-model="dataAdd.nation" />
                    </a-form-model-item>
                    <a-form-model-item label="Lương">
                      <a-input v-model="dataAdd.salary" />
                    </a-form-model-item>
                    <a-form-model-item label="Ảnh">
                      <a-input v-model="dataAdd.image" />
                    </a-form-model-item>
                  </div>
                </div>
              </a-form-model>
            </div>
          </a-modal>
          <!-- popup add -->

          <a-modal v-model="visibleEdit" title="Chỉnh sửa nhân viên">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary" @click="submitUpdate">
                Lưu
              </a-button>
            </template>
            <div class="container">
              <a-form-model>
                <div class="row">
                  <div class="col">
                    <a-form-model-item label="Họ Và Tên">
                      <a-input v-model="dataEdit.fullName" />
                    </a-form-model-item>
                    <a-form-model-item label="Ngày Sinh">
                      <a-date-picker v-model="dataEdit.dob">
                        <a-icon slot="suffixIcon" type="smile" />
                      </a-date-picker>
                    </a-form-model-item>
                    <a-form-model-item label="Giới Tính">
                      <a-radio-group
                        v-model="dataEdit.gender"
                        @change="onChange"
                      >
                        <a-radio :value="true"> Nam </a-radio>
                        <a-radio :value="false"> Nữ </a-radio>
                      </a-radio-group>
                    </a-form-model-item>
                    <a-form-model-item label="Chức Vụ">
                      <a-select
                        placeholder="Chức vụ"
                        :filter-option="false"
                        @search="fetchPosition"
                        style="width: 120px"
                        v-model="dataEdit.idPosition"
                      >
                        <a-select-option
                          v-for="(position, index) in dataPositions"
                          :value="position.id"
                          :key="index"
                        >
                          {{ position.name }}
                        </a-select-option>
                      </a-select>
                    </a-form-model-item>
                    <a-form-model-item label="Số Điện Thoại">
                      <a-input v-model="dataUserDetail.phone" />
                    </a-form-model-item>
                  </div>
                  <div class="col">
                    <a-form-model-item label="Email">
                      <a-input v-model="dataEdit.email" />
                    </a-form-model-item>
                    <a-form-model-item label="Quê Quán">
                      <a-input v-model="dataUserDetail.address" />
                    </a-form-model-item>
                    <a-form-model-item label="Dân Tộc">
                      <a-input v-model="dataUserDetail.nation" />
                    </a-form-model-item>
                    <a-form-model-item label="Lương">
                      <a-input v-model="dataUserDetail.salary" />
                    </a-form-model-item>
                    <a-form-model-item label="Ảnh">
                      <a-input v-model="dataEdit.image" />
                    </a-form-model-item>
                  </div>
                </div>
              </a-form-model>
            </div>
          </a-modal>

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
                  <h6 class="text-white f-w-400">
                    {{ dataUserDetail.fullname }}
                  </h6>
                  <p
                    class="text-white f-w-400"
                  >
                    {{ dataUserDetail.positionName }}
                  </p>
                </div>
              </div>
              <!-- cột phải  -->
              <div class="col-md-8">
                <div class="card-block">
                  <h5 class="m-b-20 p-b-5 b-b-default f-w-600">
                    Thông tin nhân viên
                  </h5>
                  <div class="row">
                    <div class="col-sm-6">
                      <p class="m-b-10 f-w-600">Giới Tính</p>
                      <h6 class="text-muted f-w-400">
                        {{ dataUserDetail.gender ? "Nam" : "Nữ" }}
                      </h6>
                    </div>
                    <div class="col-sm-6">
                      <p class="m-b-10 f-w-600">Ngày Sinh</p>
                      <h6 class="text-muted f-w-400">
                        {{
                          new Date(dataUserDetail.dob).toLocaleDateString(
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
                      <h6 class="text-muted f-w-400">{{dataUserDetail.email}}</h6>
                    </div>
                    <div class="col-sm-6">
                      <p class="m-b-10 f-w-600">Điện thoại</p>
                      <h6 class="text-muted f-w-400">
                        {{ dataUserDetail.phone }}
                      </h6>
                    </div>
                  </div>
                  <h6 class="m-b-20 m-t-40 p-b-5 b-b-default f-w-600"></h6>
                  <div class="row">
                    <div class="col-sm-6">
                      <p class="m-b-10 f-w-600">Địa chỉ</p>
                      <h6 class="text-muted f-w-400">{{dataUserDetail.address}}</h6>
                    </div>
                    <div class="col-sm-6">
                      <p class="m-b-10 f-w-600">Dân tộc</p>
                      <h6 class="text-muted f-w-400">
                        {{ dataUserDetail.nation }}
                      </h6>
                    </div>
                  </div>
                  <h6 class="m-b-20 m-t-40 p-b-5 b-b-default f-w-600"></h6>
                  <div class="row">
                    <div class="col-sm-6">
                      <p class="m-b-10 f-w-600">Lương</p>
                      <h6 class="text-muted f-w-400">{{dataUserDetail.salary}}</h6>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </a-modal>
        </div>
      </a-layout-content>
      <Footer />
    </a-layout>
  </div>
</template>

<script>
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";
import userService from "../service/userService";

export default {
  name: "User",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      dataPositions: [],
      dataPosition: {
        name: "",
        pageIndex: 1,
        pageSize: 10,
      },
      dataAdd: {
        address: "",
        dob: "",
        email: "",
        fullName: "",
        gender: "",
        idPosition: "",
        image: "",
        nation: "",
        phone: "",
        salary: "",
      },
      dataSearch: {
        idPosition: "",
        name: "",
        pageIndex: 1,
        pageSize: 10,
        statusDelete: "",
        total: 0,
      },
      dataSourceTable: [],
      pagination: {
        current: 1,
        pageSize: 10,
        total: 0,
      },
      dataEdit: {
        address: "",
        dob: "",
        email: "",
        fullName: "",
        gender: "",
        id: "",
        idPosition: "",
        image: "",
        nation: "",
        phone: "",
        salary: "",
      },
      dataUserDetail: {
        id: "",
        address: "",
        dob: "",
        email: "",
        fullName: "",
        gender: "",
        idPosition: "",
        image: "",
        nation: "",
        phone: "",
        salary: "",
      },
      columns: [
        {
          title: "Ảnh",
          dataIndex: "image",
          key: "image",
          width: 150,
          scopedSlots: { customRender: "image" },
        },
        {
          title: "Nhân viên",
          dataIndex: "employee",
          key: "employee",
          width: 150,
          scopedSlots: { customRender: "employee" },
        },
        {
          title: "Giới tính",
          dataIndex: "gender",
          key: "gender",
          width: 150,
          scopedSlots: { customRender: "gender" },
        },
        {
          title: "Chức vụ",
          dataIndex: "position",
          key: "position",
          width: 150,
          scopedSlots: { customRender: "position" },
        },
        {
          title: "Trạng thái",
          dataIndex: "status",
          key: "status",
          width: 150,
          scopedSlots: { customRender: "status" },
        },
        {
          title: "Email",
          dataIndex: "email",
          key: "email",
          width: 150,
          scopedSlots: { customRender: "email" },
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
  created() {
    this.submitSearch();
    this.getAllPosition();
  },
  methods: {
    handleCancel() {
      this.visibleAdd = false;
      this.visibleEdit = false;
      this.visibleProfile = false;
    },
    getAllPosition() {
      userService
        .getAllPosition(this.dataPosition)
        .then((response) => {
          this.dataPositions = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
        getAllPositionEdit(id,name) {
      userService
        .getAllPosition(this.dataPosition)
        .then((response) => {
          this.dataPositions = response.data.data;
          let check = true;
          for(let i = 0 ; i < this.dataPositions.length ; i ++){
            if(this.dataPositions[i].id == id){
              check = false;
              break;
            }
          }
          if(check){
            let dataPosition = {
              id : id,
              name : name
            }
            this.dataPositions.push(dataPosition);
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
    fetchPosition(value) {
      this.dataPosition.name = value;
      this.getAllPosition();
    },
    notifi(task, text) {
      this.$notification[task]({
        message: "Thông báo",
        description: text + " thành công",
      });
    },
    handleTableChange(pagination) {
      this.dataSearch.pageIndex = pagination.current;
      this.pagination = pagination;
      userService
        .searchUser(this.dataSearch)
        .then((response) => {
          this.dataSourceTable = response.data.data;
          this.dataSearch.total = response.data.total;
          this.pagination.total = response.data.total;
          console.log("datasearch", this.dataSearch);
        })
        .catch((e) => {
          console.log(e);
        });
    },
    submitSearch() {
      this.dataSearch.total = 0;
      userService
        .searchUser(this.dataSearch)
        .then((response) => {
          this.dataSourceTable = response.data.data;
          this.dataSearch.total = response.data.total;
          this.pagination.total = response.data.total;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    showModalAdd() {
      this.dataPosition.name = "";
      this.getAllPosition();
      this.visibleAdd = true;
    },
    submitAdd() {
      userService
        .addUser(this.dataAdd)
        .then((response) => {
          if (response.data.data) {
            this.submitSearch();
            var task = "success";
            var text = "Thêm";
            this.notifi(task, text);
          }
          this.submitSearch();
        })
        .catch((e) => {
          console.log(e);
        });
      this.visibleAdd = false;
      this.dataAdd.fullName = "";
      this.dataAdd.gender = "";
      this.dataAdd.dob = "";
      this.dataAdd.idPosition = "";
      this.dataAdd.phone = "";
      this.dataAdd.email = "";
      this.dataAdd.nation = "";
      this.dataAdd.address = "";
      this.dataAdd.salary = "";
      this.dataAdd.image = "";
    },
    onChange(e) {
      console.log("radio checked", e.target.value);
    },
    showModalEdit(record) {
      console.log("data edit",record)
      this.dataEdit.id = record.id;
      this.dataEdit.fullName = record.fullName;
      this.dataEdit.dob = record.dob;
      this.dataEdit.idPosition= record.idPosition;
      this.dataEdit.gender = record.gender;
      this.dataEdit.email = record.email;
      this.dataEdit.image = record.image;
      this.getUserByID(record.id);
      this.dataEdit.phone = this.dataUserDetail.phone;
      this.dataEdit.nation = this.dataUserDetail.nation;
      this.dataEdit.address = this.dataUserDetail.address;
      this.dataEdit.salary = this.dataUserDetail.salary;
      this.visibleEdit = true;
      console.log("data edit",this.date);
      this.getAllPositionEdit(record.idPosition,record.positionName);
    },
    submitUpdate() {
      userService
        .updateUser(this.dataEdit)
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
    showDetail(id){
                this.visibleProfile = true;
                this.getUserByID(id);
    },
    getUserByID(id) {
      userService
        .getUserByID(id)
        .then((response) => {
          this.dataUserDetail = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    deleteUser(id) {
      userService
        .deleteUser(id)
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
  },
};
</script>

<style>
/* button icon */
#delete {
  background-color: red;
  color: white;
}
#edit {
  background-color: rgb(17, 196, 228);
  color: white;
}
#user {
  background-color: rgb(24, 216, 24);
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
</style>