<template>
  <div class="User">
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
            placeholder="Tên nhân viên"
            v-model="dataSearch.name"
            :style="{ 'margin-right': '10px', width: '12%' }"
          />
          Chức vụ:
          <a-select
            default-value=""
            @change="submitSearch"
            v-model="dataSearch.idPosition"
            :style="{ 'margin-right': '10px', width: '8%' }"
          >
            <a-select-option value=""> Tất cả </a-select-option>
            <a-select-option
              v-for="(position, index) in dataPositions"
              :value="position.id"
              :key="index"
            >
              {{ position.name }}
            </a-select-option>
          </a-select>
          Trạng thái:
          <a-select
            @change="submitSearch"
            default-value=""
            v-model="dataSearch.statusDelete"
            :style="{ width: '8%' }"
          >
            <a-select-option value=""> Tất cả </a-select-option>
            <a-select-option value="false"> Đang làm </a-select-option>
            <a-select-option value="true"> Nghỉ </a-select-option>
          </a-select>
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
            >
              <template slot="image" slot-scope="text, record">
                <img
                  v-if="record.image !== null"
                  alt="example"
                  style="width: 50%"
                  :src="record.image"
                />
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
                      @click="showDetail(record.id)"
                      :style="{ 'margin-right': '100px' }"
                    >
                      <font-awesome-icon :icon="['fas', 'user']" />
                    </a-button>
                  </a-col>
                  <a-col :span="8">
                    <a-button
                      id="edit"
                      @click="showModalEdit(record)"
                      :style="{ width: '44.25px' }"
                    >
                      <font-awesome-icon :icon="['fas', 'edit']" />
                    </a-button>
                  </a-col>
                  <a-col :span="8">
                    <a-popconfirm
                      v-if="dataSourceTable.length"
                      title="Bạn có chắc chắn muốn xóa không?"
                      @confirm="deleteUser(record.id)"
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

          <!-- popup add-->
          <a-modal v-model="visibleAdd" title="Thêm nhân viên">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button
                key="submit"
                type="primary"
                :loading="loadingAdd"
                @click="checkFormAdd"
              >
                Lưu
              </a-button>
            </template>
            <div class="container">
              <a-form-model>
                <div class="row" v-if="showImage">
                  <img
                    alt="example"
                    style="width: 50%; margin-left: auto; margin-right: auto"
                    :src="url"
                  />
                </div>
                <div class="row">
                  <div class="col">
                    <span style="color: red">*</span> Họ Và Tên :
                    <a-form-model-item>
                      <a-input
                        style="width: 100%"
                        @change="inputFullNameAdd"
                        v-model="dataAdd.fullName"
                      />
                      <div
                        style="color: red"
                        v-if="checkDataInputFullName.show"
                      >
                        {{ checkDataInputFullName.message }}
                      </div>
                    </a-form-model-item>

                    <span style="color: red">*</span> Ngày Sinh :
                    <a-form-model-item>
                      <a-date-picker
                        style="width: 100%"
                        @change="inputDoBAdd"
                        v-model="dataAdd.dob"
                        format="DD/MM/YYYY"
                      >
                        <a-icon slot="suffixIcon" type="smile" />
                      </a-date-picker>
                      <div style="color: red" v-if="checkDataInputDoB.show">
                        {{ checkDataInputDoB.message }}
                      </div>
                    </a-form-model-item>

                    <span style="color: red">*</span> Giới tính :
                    <a-form-model-item>
                      <a-radio-group
                        style="width: 100%"
                        v-model="dataAdd.gender"
                      >
                        <a-radio :value="true"> Nam </a-radio>
                        <a-radio :value="false"> Nữ </a-radio>
                      </a-radio-group>
                    </a-form-model-item>

                    <span style="color: red">*</span> Chức Vụ :
                    <a-form-model-item>
                      <a-select
                        placeholder="Chức vụ"
                        style="width: 100%"
                        @change="inputPositionAdd"
                        v-model="dataAdd.idPosition"
                        id="selectPosition"
                      >
                        <a-select-option
                          v-for="(position, index) in dataPositions"
                          :value="position.id"
                          :key="index"
                        >
                          {{ position.name }}
                        </a-select-option>
                      </a-select>
                      <div
                        style="color: red"
                        v-if="checkDataInputPosition.show"
                      >
                        {{ checkDataInputPosition.message }}
                      </div>
                    </a-form-model-item>

                    <span style="color: red">*</span> Số điện thoại :
                    <a-form-model-item>
                      <a-input
                        style="width: 100%"
                        @change="inputPhoneAdd"
                        v-model="dataAdd.phone"
                      />
                      <div style="color: red" v-if="checkDataInputPhone.show">
                        {{ checkDataInputPhone.message }}
                      </div>
                    </a-form-model-item>
                  </div>
                  <div class="col">
                    <span style="color: red">*</span> Email :
                    <a-form-model-item>
                      <a-input
                        style="width: 100%"
                        @change="inputEmailAdd"
                        v-model="dataAdd.email"
                      />
                      <div style="color: red" v-if="checkDataInputEmail.show">
                        {{ checkDataInputEmail.message }}
                      </div>
                    </a-form-model-item>

                    <span style="color: red">*</span> Địa chỉ :
                    <a-form-model-item>
                      <a-input
                        style="width: 100%"
                        @change="inputAddressAdd"
                        v-model="dataAdd.address"
                      />
                      <div style="color: red" v-if="checkDataInputAddress.show">
                        {{ checkDataInputAddress.message }}
                      </div>
                    </a-form-model-item>

                    <span style="color: red">*</span> Dân tộc :
                    <a-form-model-item>
                      <a-input
                        style="width: 100%"
                        @change="inputNationAdd"
                        v-model="dataAdd.nation"
                      />
                      <div style="color: red" v-if="checkDataInputNation.show">
                        {{ checkDataInputNation.message }}
                      </div>
                    </a-form-model-item>

                    <span style="color: red">*</span> Lương :
                    <a-form-model-item>
                      <a-input-number
                        style="width: 100%"
                        @change="inputSalaryAdd"
                        v-model="dataAdd.salary"
                      />
                      <div style="color: red" v-if="checkDataInputSalary.show">
                        {{ checkDataInputSalary.message }}
                      </div>
                    </a-form-model-item>

                    Ảnh :
                    <a-form-model-item>
                      <input
                        type="file"
                        accept=".jpg, .png"
                        ref="fileupload"
                        @change="importFile($event)"
                      />
                      <!-- <a-input style="width: 100%" v-model="dataAdd.image" /> -->
                    </a-form-model-item>
                  </div>
                </div>
              </a-form-model>
            </div>
          </a-modal>
          <!-- popup add -->

          <!-- popup edit -->
          <a-modal v-model="visibleEdit" title="Chỉnh sửa nhân viên">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button
                key="submit"
                type="primary"
                :loading="loadingEdit"
                @click="checkFormEdit"
              >
                Lưu
              </a-button>
            </template>
            <div class="container">
              <a-form-model>
                <div class="row" v-if="showImage">
                  <img
                    alt="example"
                    style="width: 50%; margin-left: auto; margin-right: auto"
                    :src="url"
                  />
                </div>
                <div class="row">
                  <div class="col">
                    <span style="color: red">*</span> Họ Và Tên :
                    <a-form-model-item>
                      <a-input
                        @change="inputFullNameEdit"
                        v-model="dataEdit.fullName"
                      />
                      <div
                        style="color: red"
                        v-if="checkDataInputFullName.show"
                      >
                        {{ checkDataInputFullName.message }}
                      </div>
                    </a-form-model-item>

                    <span style="color: red">*</span> Ngày Sinh :
                    <a-form-model-item>
                      <a-date-picker
                        v-model="dataEdit.dob"
                        format="DD/MM/YYYY"
                        @change="inputDoBEdit"
                      >
                        <a-icon slot="suffixIcon" type="smile" />
                      </a-date-picker>
                      <div style="color: red" v-if="checkDataInputDoB.show">
                        {{ checkDataInputDoB.message }}
                      </div>
                    </a-form-model-item>

                    <span style="color: red">*</span> Giới tính :
                    <a-form-model-item>
                      <a-radio-group v-model="dataEdit.gender">
                        <a-radio :value="true"> Nam </a-radio>
                        <a-radio :value="false"> Nữ </a-radio>
                      </a-radio-group>
                    </a-form-model-item>

                    <span style="color: red">*</span> Chức Vụ :
                    <a-form-model-item>
                      <a-select
                        placeholder="Chức vụ"
                        style="width: 120px"
                        v-model="dataEdit.idPosition"
                        @change="inputPositionEdit"
                      >
                        <a-select-option
                          v-for="(position, index) in dataPositions"
                          :value="position.id"
                          :key="index"
                        >
                          {{ position.name }}
                        </a-select-option>
                      </a-select>
                      <div
                        style="color: red"
                        v-if="checkDataInputPosition.show"
                      >
                        {{ checkDataInputPosition.message }}
                      </div>
                    </a-form-model-item>

                    <span style="color: red">*</span> Số điện thoại :
                    <a-form-model-item>
                      <a-input
                        v-model="dataEdit.phone"
                        @change="inputPhoneEdit"
                      />
                      <div style="color: red" v-if="checkDataInputPhone.show">
                        {{ checkDataInputPhone.message }}
                      </div>
                    </a-form-model-item>
                  </div>
                  <div class="col">
                    <span style="color: red">*</span> Email :
                    <a-form-model-item>
                      <a-input
                        v-model="dataEdit.email"
                        @change="inputEmailEdit"
                      />
                      <div style="color: red" v-if="checkDataInputEmail.show">
                        {{ checkDataInputEmail.message }}
                      </div>
                    </a-form-model-item>

                    <span style="color: red">*</span> Địa chỉ :
                    <a-form-model-item>
                      <a-input
                        v-model="dataEdit.address"
                        @change="inputAddressEdit"
                      />
                      <div style="color: red" v-if="checkDataInputAddress.show">
                        {{ checkDataInputAddress.message }}
                      </div>
                    </a-form-model-item>

                    <span style="color: red">*</span> Dân tộc :
                    <a-form-model-item>
                      <a-input
                        v-model="dataEdit.nation"
                        @change="inputNationEdit"
                      />
                      <div style="color: red" v-if="checkDataInputNation.show">
                        {{ checkDataInputNation.message }}
                      </div>
                    </a-form-model-item>

                    <span style="color: red">*</span> Lương :
                    <a-form-model-item>
                      <a-input-number
                        v-model="dataEdit.salary"
                        @change="inputSalaryEdit"
                      />
                      <div style="color: red" v-if="checkDataInputSalary.show">
                        {{ checkDataInputSalary.message }}
                      </div>
                    </a-form-model-item>

                    Ảnh :
                    <a-form-model-item>
                      <input
                        type="file"
                        accept=".jpg, .png"
                        ref="fileupload"
                        @change="importFileEdit($event)"
                      />
                    </a-form-model-item>
                  </div>
                </div>
              </a-form-model>
            </div>
          </a-modal>
          <!-- popup edit -->

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
                  <p class="text-white f-w-400">
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
                      <h6 class="text-muted f-w-400">
                        {{ dataUserDetail.email }}
                      </h6>
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
                      <h6 class="text-muted f-w-400">
                        {{ dataUserDetail.address }}
                      </h6>
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
                      <h6 class="text-muted f-w-400">
                        {{ dataUserDetail.salary }}
                      </h6>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </a-modal>
        </div>
      </a-layout-content>
    </a-layout>
  </div>
</template>

<script>
import userService from "../service/userService";
import fileService from "../service/fileService";

export default {
  name: "User",
  components: {},
  data() {
    return {
      url: "",
      showImage: false,
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
        gender: true,
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
          width: 180,
          scopedSlots: { customRender: "action" },
        },
      ],
      visibleAdd: false,
      visibleEdit: false,
      visibleProfile: false,
      loadingAdd: false,
      loadingEdit: false,
      checkDataInputFullName: {
        show: false,
        message: "",
      },
      checkDataInputDoB: {
        show: false,
        message: "",
      },
      checkDataInputGender: {
        show: false,
        message: "",
      },
      checkDataInputPosition: {
        show: false,
        message: "",
      },
      checkDataInputPhone: {
        show: false,
        message: "",
      },
      checkDataInputEmail: {
        show: false,
        message: "",
      },
      checkDataInputAddress: {
        show: false,
        message: "",
      },
      checkDataInputNation: {
        show: false,
        message: "",
      },
      checkDataInputSalary: {
        show: false,
        message: "",
      },
    };
  },
  created() {
    this.submitSearch();
    this.getAllPosition();
  },
  methods: {
    importFileEdit(event1) {
      if (event1.target.files[0]) {
        this.dataEdit.image = event1.target.files[0];
        this.url = window.URL.createObjectURL(event1.target.files[0]);
        this.showImage = true;
      }
    },
    importFile(event1) {
      if (event1.target.files[0]) {
        this.dataAdd.image = event1.target.files[0];
        this.url = window.URL.createObjectURL(event1.target.files[0]);
        this.showImage = true;
      }
    },
    handleCancel() {
      this.visibleAdd = false;
      this.visibleEdit = false;
      this.visibleProfile = false;
      this.showImage = false;
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
    getAllPositionEdit(id, name) {
      userService
        .getAllPosition(this.dataPosition)
        .then((response) => {
          this.dataPositions = response.data.data;
          let check = true;
          for (let i = 0; i < this.dataPositions.length; i++) {
            if (this.dataPositions[i].id == id) {
              check = false;
              break;
            }
          }
          if (check) {
            let dataPosition = {
              id: id,
              name: name,
            };
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
    handleTableChange(pagination) {
      this.dataSearch.pageIndex = pagination.current;
      this.pagination = pagination;
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
    submitSearch() {
      this.dataSearch.total = 0;
      this.dataSearch.name = this.dataSearch.name.trim();
      userService
        .searchUser(this.dataSearch)
        .then((response) => {
          this.dataSourceTable = response.data.data;
          for (let i = 0; i < this.dataSourceTable.length; i++) {
            if (this.dataSourceTable[i].image !== null) {
              userService
                .preview(this.dataSourceTable[i].image)
                .then((response) => {
                  this.dataSourceTable[i].image = window.URL.createObjectURL(
                    response.data
                  );
                })
                .catch((e) => {
                  console.log(e);
                });
            }
          }
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
      this.url = "";
      this.dataAdd.fullName = "";
      this.dataAdd.gender = true;
      this.dataAdd.dob = "";
      this.dataAdd.idPosition = "";
      this.dataAdd.phone = "";
      this.dataAdd.email = "";
      this.dataAdd.nation = "";
      this.dataAdd.address = "";
      this.dataAdd.salary = "";
      this.dataAdd.image = "";
      if (this.$refs.fileupload != null) {
        this.$refs.fileupload.value = null;
      }
      this.showImage = false;
      this.visibleAdd = true;
      this.checkDataInputFullName.show = false;
      this.checkDataInputFullName.message = "";
      this.checkDataInputDoB.show = false;
      this.checkDataInputDoB.message = "";
      this.checkDataInputPosition.show = false;
      this.checkDataInputPosition.message = "";
      this.checkDataInputPhone.show = false;
      this.checkDataInputPhone.message = "";
      this.checkDataInputEmail.show = false;
      this.checkDataInputEmail.message = "";
      this.checkDataInputAddress.show = false;
      this.checkDataInputAddress.message = "";
      this.checkDataInputNation.show = false;
      this.checkDataInputNation.message = "";
      this.checkDataInputSalary.show = false;
      this.checkDataInputSalary.message = "";
    },
    checkFormAdd() {
      let check = true;
      if (this.dataAdd.fullName != null && this.dataAdd.fullName.trim() != "") {
        this.checkDataInputFullName.show = false;
        this.checkDataInputFullName.message = "";
      } else {
        check = false;
        this.checkDataInputFullName.show = true;
        this.checkDataInputFullName.message = "Bạn phải điền vào ô họ và tên";
      }
      if (this.dataAdd.dob != null && this.dataAdd.dob != "") {
        this.checkDataInputDoB.show = false;
        this.checkDataInputDoB.message = "";
      } else {
        check = false;
        this.checkDataInputDoB.show = true;
        this.checkDataInputDoB.message = "Bạn phải điền vào ô ngày sinh";
      }
      if (this.dataAdd.idPosition != null && this.dataAdd.idPosition != "") {
        this.checkDataInputPosition.show = false;
        this.checkDataInputPosition.message = "";
      } else {
        check = false;
        this.checkDataInputPosition.show = true;
        this.checkDataInputPosition.message = "Bạn phải chọn chức vụ";
      }
      if (this.dataAdd.phone != null && this.dataAdd.phone.trim() != "") {
        this.checkDataInputPhone.show = false;
        this.checkDataInputPhone.message = "";
      } else {
        check = false;
        this.checkDataInputPhone.show = true;
        this.checkDataInputPhone.message = "Bạn phải điền vào ô số điện thoại";
      }
      if (this.dataAdd.email != null && this.dataAdd.email.trim() != "") {
        this.checkDataInputEmail.show = false;
        this.checkDataInputEmail.message = "";
      } else {
        check = false;
        this.checkDataInputEmail.show = true;
        this.checkDataInputEmail.message = "Bạn phải điền vào ô email";
      }
      if (this.dataAdd.address != null && this.dataAdd.address.trim() != "") {
        this.checkDataInputAddress.show = false;
        this.checkDataInputAddress.message = "";
      } else {
        check = false;
        this.checkDataInputAddress.show = true;
        this.checkDataInputAddress.message = "Bạn phải điền vào ô địa chỉ";
      }
      if (this.dataAdd.nation != null && this.dataAdd.nation.trim() != "") {
        this.checkDataInputNation.show = false;
        this.checkDataInputNation.message = "";
      } else {
        check = false;
        this.checkDataInputNation.show = true;
        this.checkDataInputNation.message = "Bạn phải điền vào ô dân tộc";
      }
      if (this.dataAdd.salary != null && this.dataAdd.salary != "") {
        this.checkDataInputSalary.show = false;
        this.checkDataInputSalary.message = "";
      } else {
        check = false;
        this.checkDataInputSalary.show = true;
        this.checkDataInputSalary.message = "Bạn phải điền vào ô lương";
      }
      if (check == true) {
        this.submitAdd();
      }
    },
    inputFullNameAdd() {
      if (this.dataAdd.fullName != null && this.dataAdd.fullName.trim() != "") {
        this.checkDataInputFullName.show = false;
        this.checkDataInputFullName.message = "";
      } else {
        this.checkDataInputFullName.show = true;
        this.checkDataInputFullName.message = "Bạn phải điền vào ô họ và tên";
      }
    },
    inputDoBAdd() {
      if (this.dataAdd.dob != null && this.dataAdd.dob != "") {
        this.checkDataInputDoB.show = false;
        this.checkDataInputDoB.message = "";
      } else {
        this.checkDataInputDoB.show = true;
        this.checkDataInputDoB.message = "Bạn phải điền vào ô ngày sinh";
      }
    },
    inputPositionAdd() {
      if (this.dataAdd.idPosition != null && this.dataAdd.idPosition != "") {
        this.checkDataInputPosition.show = false;
        this.checkDataInputPosition.message = "";
      } else {
        this.checkDataInputPosition.show = true;
        this.checkDataInputPosition.message = "Bạn phải chọn chức vụ";
      }
    },
    inputPhoneAdd() {
      if (this.dataAdd.phone != null && this.dataAdd.phone.trim() != "") {
        this.checkDataInputPhone.show = false;
        this.checkDataInputPhone.message = "";
      } else {
        this.checkDataInputPhone.show = true;
        this.checkDataInputPhone.message = "Bạn phải điền vào ô số điện thoại";
      }
    },
    inputEmailAdd() {
      if (this.dataAdd.email != null && this.dataAdd.email.trim() != "") {
        this.checkDataInputEmail.show = false;
        this.checkDataInputEmail.message = "";
      } else {
        this.checkDataInputEmail.show = true;
        this.checkDataInputEmail.message = "Bạn phải điền vào ô email";
      }
    },
    inputAddressAdd() {
      if (this.dataAdd.address != null && this.dataAdd.address.trim() != "") {
        this.checkDataInputAddress.show = false;
        this.checkDataInputAddress.message = "";
      } else {
        this.checkDataInputAddress.show = true;
        this.checkDataInputAddress.message = "Bạn phải điền vào ô địa chỉ";
      }
    },
    inputNationAdd() {
      if (this.dataAdd.nation != null && this.dataAdd.nation.trim() != "") {
        this.checkDataInputNation.show = false;
        this.checkDataInputNation.message = "";
      } else {
        this.checkDataInputNation.show = true;
        this.checkDataInputNation.message = "Bạn phải điền vào ô dân tộc";
      }
    },
    inputSalaryAdd() {
      if (this.dataAdd.salary != null && this.dataAdd.salary != "") {
        this.checkDataInputSalary.show = false;
        this.checkDataInputSalary.message = "";
      } else {
        this.checkDataInputSalary.show = true;
        this.checkDataInputSalary.message = "Bạn phải điền vào ô lương";
      }
    },
    submitAdd() {
      this.loadingAdd = true;
      if (this.dataAdd.image != "") {
        fileService
          .uploadImage(this.dataAdd.image)
          .then((response) => {
            this.dataAdd.image = response.data.data;
            userService
              .addUser(this.dataAdd)
              .then((response) => {
                this.submitSearch();
                this.loadingAdd = false;
                if (response.data.data) {
                  let type = "success";
                  let message = "Thêm mới";
                  let description =
                    "Thêm mới nhân viên " +
                    this.dataAdd.username +
                    " thành công !!";
                  this.notifi(type, message, description);
                } else {
                  let type = "error";
                  let message = "Thêm mới";
                  let description =
                    "Thêm mới tài khoản " +
                    this.dataAdd.fullName +
                    " không thành công vì " +
                    response.data.message;
                  this.notifi(type, message, description);
                  userService.deleteImage(this.dataAdd.image);
                }
                this.visibleAdd = false;
              })
              .catch(() => {
                userService.deleteImage(this.dataAdd.image);
                this.loadingAdd = false;
              });
          })
          .catch((e) => {
            console.log(e);
            this.loadingAdd = false;
          });
      } else {
        userService
          .addUser(this.dataAdd)
          .then((response) => {
            this.submitSearch();
            this.loadingAdd = false;
            if (response.data.data) {
              let type = "success";
              let message = "Thêm mới";
              let description =
                "Thêm mới nhân viên " +
                this.dataAdd.username +
                " thành công !!";
              this.notifi(type, message, description);
            } else {
              let type = "error";
              let message = "Thêm mới";
              let description =
                "Thêm mới tài khoản " +
                this.dataAdd.fullName +
                " không thành công vì " +
                response.data.message;
              this.notifi(type, message, description);
            }
            this.visibleAdd = false;
          })
          .catch(() => {
            this.loadingAdd = false;
          });
      }
    },
    showModalEdit(record) {
      this.checkDataInputFullName.show = false;
      this.checkDataInputFullName.message = "";
      this.checkDataInputDoB.show = false;
      this.checkDataInputDoB.message = "";
      this.checkDataInputPosition.show = false;
      this.checkDataInputPosition.message = "";
      this.checkDataInputPhone.show = false;
      this.checkDataInputPhone.message = "";
      this.checkDataInputEmail.show = false;
      this.checkDataInputEmail.message = "";
      this.checkDataInputAddress.show = false;
      this.checkDataInputAddress.message = "";
      this.checkDataInputNation.show = false;
      this.checkDataInputNation.message = "";
      this.checkDataInputSalary.show = false;
      this.checkDataInputSalary.message = "";
      userService
        .getUserByID(record.id)
        .then((response) => {
          this.dataEdit.id = record.id;
          this.dataEdit.fullName = record.fullName;
          this.dataEdit.dob = record.dob;
          this.dataEdit.idPosition = record.idPosition;
          this.dataEdit.gender = record.gender;
          this.dataEdit.email = record.email;
          this.url = record.image;
          this.dataEdit.image = "";
          this.dataEdit.phone = response.data.data.phone;
          this.dataEdit.nation = response.data.data.nation;
          this.dataEdit.address = response.data.data.address;
          this.dataEdit.salary = response.data.data.salary;
          if (this.url != null) {
            this.showImage = true;
          } else {
            this.showImage = false;
          }
          if (this.$refs.fileupload != null) {
            this.$refs.fileupload.value = null;
          }
          this.visibleEdit = true;
        })
        .catch((e) => {
          console.log(e);
        });
      this.loadingEdit = false;
    },
    checkFormEdit() {
      let check = true;
      if (
        this.dataEdit.fullName != null &&
        this.dataEdit.fullName.trim() != ""
      ) {
        this.checkDataInputFullName.show = false;
        this.checkDataInputFullName.message = "";
      } else {
        check = false;
        this.checkDataInputFullName.show = true;
        this.checkDataInputFullName.message = "Bạn phải điền vào ô họ và tên";
      }
      if (this.dataEdit.dob != null && this.dataEdit.dob != "") {
        this.checkDataInputDoB.show = false;
        this.checkDataInputDoB.message = "";
      } else {
        check = false;
        this.checkDataInputDoB.show = true;
        this.checkDataInputDoB.message = "Bạn phải điền vào ô ngày sinh";
      }
      if (this.dataEdit.idPosition != null && this.dataEdit.idPosition != "") {
        this.checkDataInputPosition.show = false;
        this.checkDataInputPosition.message = "";
      } else {
        check = false;
        this.checkDataInputPosition.show = true;
        this.checkDataInputPosition.message = "Bạn phải chọn chức vụ";
      }
      if (this.dataEdit.phone != null && this.dataEdit.phone.trim() != "") {
        this.checkDataInputPhone.show = false;
        this.checkDataInputPhone.message = "";
      } else {
        check = false;
        this.checkDataInputPhone.show = true;
        this.checkDataInputPhone.message = "Bạn phải điền vào ô số điện thoại";
      }
      if (this.dataEdit.email != null && this.dataEdit.email.trim() != "") {
        this.checkDataInputEmail.show = false;
        this.checkDataInputEmail.message = "";
      } else {
        check = false;
        this.checkDataInputEmail.show = true;
        this.checkDataInputEmail.message = "Bạn phải điền vào ô email";
      }
      if (this.dataEdit.address != null && this.dataEdit.address.trim() != "") {
        this.checkDataInputAddress.show = false;
        this.checkDataInputAddress.message = "";
      } else {
        check = false;
        this.checkDataInputAddress.show = true;
        this.checkDataInputAddress.message = "Bạn phải điền vào ô địa chỉ";
      }
      if (this.dataEdit.nation != null && this.dataEdit.nation.trim() != "") {
        this.checkDataInputNation.show = false;
        this.checkDataInputNation.message = "";
      } else {
        check = false;
        this.checkDataInputNation.show = true;
        this.checkDataInputNation.message = "Bạn phải điền vào ô dân tộc";
      }
      if (this.dataEdit.salary != null && this.dataEdit.salary != "") {
        this.checkDataInputSalary.show = false;
        this.checkDataInputSalary.message = "";
      } else {
        check = false;
        this.checkDataInputSalary.show = true;
        this.checkDataInputSalary.message = "Bạn phải điền vào ô lương";
      }
      if (check == true) {
        this.submitUpdate();
      }
    },
    inputFullNameEdit() {
      if (
        this.dataEdit.fullName != null &&
        this.dataEdit.fullName.trim() != ""
      ) {
        this.checkDataInputFullName.show = false;
        this.checkDataInputFullName.message = "";
      } else {
        this.checkDataInputFullName.show = true;
        this.checkDataInputFullName.message = "Bạn phải điền vào ô họ và tên";
      }
    },
    inputDoBEdit() {
      if (this.dataEdit.dob != null && this.dataEdit.dob != "") {
        this.checkDataInputDoB.show = false;
        this.checkDataInputDoB.message = "";
      } else {
        this.checkDataInputDoB.show = true;
        this.checkDataInputDoB.message = "Bạn phải điền vào ô ngày sinh";
      }
    },
    inputPositionEdit() {
      if (this.dataEdit.idPosition != null && this.dataEdit.idPosition != "") {
        this.checkDataInputPosition.show = false;
        this.checkDataInputPosition.message = "";
      } else {
        this.checkDataInputPosition.show = true;
        this.checkDataInputPosition.message = "Bạn phải chọn chức vụ";
      }
    },
    inputPhoneEdit() {
      if (this.dataEdit.phone != null && this.dataEdit.phone.trim() != "") {
        this.checkDataInputPhone.show = false;
        this.checkDataInputPhone.message = "";
      } else {
        this.checkDataInputPhone.show = true;
        this.checkDataInputPhone.message = "Bạn phải điền vào ô số điện thoại";
      }
    },
    inputEmailEdit() {
      if (this.dataEdit.email != null && this.dataEdit.email.trim() != "") {
        this.checkDataInputEmail.show = false;
        this.checkDataInputEmail.message = "";
      } else {
        this.checkDataInputEmail.show = true;
        this.checkDataInputEmail.message = "Bạn phải điền vào ô email";
      }
    },
    inputAddressEdit() {
      if (this.dataEdit.address != null && this.dataEdit.address.trim() != "") {
        this.checkDataInputAddress.show = false;
        this.checkDataInputAddress.message = "";
      } else {
        this.checkDataInputAddress.show = true;
        this.checkDataInputAddress.message = "Bạn phải điền vào ô địa chỉ";
      }
    },
    inputNationEdit() {
      if (this.dataEdit.nation != null && this.dataEdit.nation.trim() != "") {
        this.checkDataInputNation.show = false;
        this.checkDataInputNation.message = "";
      } else {
        this.checkDataInputNation.show = true;
        this.checkDataInputNation.message = "Bạn phải điền vào ô dân tộc";
      }
    },
    inputSalaryEdit() {
      if (this.dataEdit.salary != null && this.dataEdit.salary != "") {
        this.checkDataInputSalary.show = false;
        this.checkDataInputSalary.message = "";
      } else {
        this.checkDataInputSalary.show = true;
        this.checkDataInputSalary.message = "Bạn phải điền vào ô lương";
      }
    },
    submitUpdate() {
      this.loadingEdit = true;
      if (this.dataEdit.image != "") {
        fileService
          .uploadImage(this.dataEdit.image)
          .then((response) => {
            this.dataEdit.image = response.data.data;
            userService
              .updateUser(this.dataEdit)
              .then((response) => {
                this.submitSearch();
                this.visibleEdit = false;
                this.loadingEdit = false;
                if (response.data.data) {
                  let type = "success";
                  let message = "Cập nhật";
                  let description =
                    "Sửa thông tin nhân viên " +
                    this.dataEdit.fullName +
                    " thành công !!";
                  this.notifi(type, message, description);
                } else {
                  let type = "error";
                  let message = "Cập nhật";
                  let description =
                    "Sửa thông tin nhân viên " +
                    this.dataEdit.fullName +
                    " không thành công vì " +
                    response.data.message;
                  this.notifi(type, message, description);
                  userService.deleteImage(this.dataEdit.image);
                }
              })
              .catch(() => {
                userService.deleteImage(this.dataEdit.image);
                this.loadingEdit = false;
              });
          })
          .catch((e) => {
            console.log(e);
            this.loadingEdit = false;
          });
      } else {
        userService
          .updateUser(this.dataEdit)
          .then((response) => {
            this.submitSearch();
            this.visibleEdit = false;
            if (response.data.data) {
              let type = "success";
              let message = "Cập nhật";
              let description =
                "Sửa thông tin nhân viên " +
                this.dataEdit.fullName +
                " thành công !!";
              this.notifi(type, message, description);
            } else {
              let type = "error";
              let message = "Cập nhật";
              let description =
                "Sửa thông tin nhân viên " +
                this.dataEdit.fullName +
                " không thành công vì " +
                response.data.message;
              this.notifi(type, message, description);
            }
          })
          .catch((e) => {
            console.log(e);
            this.loadingEdit = false;
          });
      }
    },
    showDetail(id) {
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
    notifi(type, message, description) {
      this.$notification[type]({
        message: message,
        description: description,
      });
    },
  },
};
</script>

<style>
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
</style>