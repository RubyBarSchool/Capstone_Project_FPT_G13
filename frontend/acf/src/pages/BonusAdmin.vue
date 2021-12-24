<template>
  <div class="admin">
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
            placeholder="Tiêu đề"
            :style="{ width: '150px', 'margin-right': '5px' }"
            v-model="dataSearch.title"
          />
          Trạng thái:
          <a-select
            placeholder="Trạng thái"
            v-model="dataSearch.status"
            @change="search"
            :style="{ width: '150px', 'margin-right': '5px' }"
          >
            <a-select-option value=""> Tất cả </a-select-option>
            <a-select-option :value="false"> Nháp </a-select-option>
            <a-select-option :value="true"> Công khai </a-select-option>
          </a-select>
          <a-range-picker
            @change="search"
            v-model="dataSearch.date"
            :placeholder="['Ngày hiệu lực', 'Ngày hiệu lực']"
            format="DD/MM/YYYY"
          />
          <a-button
            type="primary"
            @click="search"
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
              :scroll="{ x: 1000 }"
              :rowKey="
                (record, index) => {
                  return index;
                }
              "
              @change="handleTableChange"
            >
              <template slot="status" slot-scope="text, record">
                <a-tag :color="record.status ? 'green' : 'blue'">
                  {{ record.status ? "Công khai" : "Nháp" }}
                </a-tag>
              </template>
              <template slot="effectiveDate" slot-scope="text, record">
                {{
                  new Date(record.effectiveDate).toLocaleDateString("en-GB", {
                    year: "numeric",
                    month: "2-digit",
                    day: "2-digit",
                  })
                }}
              </template>
              <template slot="listIdEmployee" slot-scope="text, record">
                <div
                  :key="index"
                  v-for="(data, index) in record.listIdEmployee"
                >
                  <!-- <div v-if="index != 0">,</div> -->
                  <div>{{ data.name }}</div>
                </div>
              </template>
              <template slot="action" slot-scope="text, record">
                <a-row v-if="!record.status">
                  <a-col :span="9" v-if="checkEditOrDelete(record)">
                    <a-button
                      id="edit"
                      @click="
                        showModalEdit(
                          record.id,
                          record.effectiveDate,
                          record.listIdEmployee,
                          record.money,
                          record.reason,
                          record.status,
                          record.title
                        )
                      "
                      :style="{ width: '44.25px' }"
                    >
                      <font-awesome-icon :icon="['fas', 'edit']" />
                    </a-button>
                  </a-col>
                  <a-col :span="9" v-if="checkEditOrDelete(record)">
                    <a-popconfirm
                      v-if="dataSourceTable.length"
                      title="Bạn có chắc chắn muốn xóa không?"
                      @confirm="deleteBonusAdmin(record.id)"
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
          <a-modal v-model="visibleAdd" title="Thêm khen thưởng">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button
                key="submit"
                type="primary"
                :loading="loading"
                @click="checkFormAdd"
              >
                Lưu
              </a-button>
            </template>

            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Tiêu đề :</a-col
              >
              <a-col flex="auto">
                <a-input @change="inputTitle" v-model="dataAdd.title" />
                <div style="color: red" v-if="checkDataInputTitle.show">
                  {{ checkDataInputTitle.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Họ và tên :</a-col
              >
              <a-col flex="auto">
                <a-select
                  placeholder="Họ và tên"
                  mode="multiple"
                  v-model="dataAdd.listIdEmployee"
                  :filter-option="false"
                  @search="fetchEmployees"
                  @change="inputName"
                  style="width: 100%"
                >
                  <a-select-option
                    v-for="(employee, index) in dataEmployees"
                    :value="employee.id"
                    :key="index"
                  >
                    {{ employee.fullName }}
                  </a-select-option>
                </a-select>
                <div style="color: red" v-if="checkDataInputName.show">
                  {{ checkDataInputName.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Lý do :</a-col
              >
              <a-col flex="auto">
                <a-textarea
                  placeholder="Lý do"
                  :rows="4"
                  v-model="dataAdd.reason"
                  @change="inputReason"
                />
                <div style="color: red" v-if="checkDataInputReason.show">
                  {{ checkDataInputReason.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Số tiền :</a-col
              >
              <a-col flex="auto">
                <a-input-number
                  v-model="dataAdd.money"
                  @change="inputMoney"
                  :min="100000"
                />
                <div style="color: red" v-if="checkDataInputMoney.show">
                  {{ checkDataInputMoney.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Trạng thái :</a-col
              >
              <a-col flex="auto">
                <a-radio-group
                  name="radioGroup"
                  @change="inputStatus"
                  v-model="dataAdd.status"
                >
                  <a-radio :value="false"> Nháp </a-radio>
                  <a-radio :value="true"> Hiệu lực </a-radio>
                </a-radio-group>
                <div style="color: red" v-if="checkDataInputStatus.show">
                  {{ checkDataInputStatus.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Ngày hiệu lực</a-col
              >
              <a-col flex="auto">
                <a-date-picker
                  @change="inputEffectiveDate"
                  v-model="dataAdd.effectiveDate"
                  :disabled-date="disableDateStart"
                  format="DD/MM/YYYY"
                  placeholder="Ngày hiệu lực"
                >
                </a-date-picker>
                <div style="color: red" v-if="checkDataInputEffectiveDate.show">
                  {{ checkDataInputEffectiveDate.message }}
                </div>
              </a-col>
            </a-row>
            <br />
          </a-modal>
          <!-- popup add -->

          <!-- popup edit-->
          <a-modal v-model="visibleEdit" title="Chỉnh sửa khen thưởng">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button
                key="submit"
                type="primary"
                :loading="loadingEdit"
                @click="checkFormUpdate"
              >
                Lưu
              </a-button>
            </template>
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Tiêu đề :</a-col
              >
              <a-col flex="auto">
                <a-input @change="inputEditTitle" v-model="dataEdit.title" />
                <div style="color: red" v-if="checkDataInputTitle.show">
                  {{ checkDataInputTitle.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Họ và tên :</a-col
              >
              <a-col flex="auto">
                <a-select
                  :disabled="true"
                  placeholder="Họ và tên"
                  mode="multiple"
                  v-model="dataEdit.listIdEmployee"
                  :filter-option="false"
                  @search="fetchEmployees"
                  @change="inputEditName"
                >
                  <a-select-option
                    v-for="(employee, index) in dataEmployees"
                    :value="employee.id"
                    :key="index"
                  >
                    {{ employee.fullName }}
                  </a-select-option>
                </a-select>
                <div style="color: red" v-if="checkDataInputName.show">
                  {{ checkDataInputName.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Lý do :</a-col
              >
              <a-col flex="auto">
                <a-textarea
                  placeholder="Lý do"
                  :rows="4"
                  v-model="dataEdit.reason"
                  @change="inputEditReason"
                />
                <div style="color: red" v-if="checkDataInputReason.show">
                  {{ checkDataInputReason.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Số tiền :</a-col
              >
              <a-col flex="auto">
                <a-input-number
                  v-model="dataEdit.money"
                  @change="inputEditMoney"
                  :min="100000"
                />
                <div style="color: red" v-if="checkDataInputMoney.show">
                  {{ checkDataInputMoney.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Trạng thái :</a-col
              >
              <a-col flex="auto">
                <a-radio-group
                  name="radioGroup"
                  @change="inputEditStatus"
                  v-model="dataEdit.status"
                >
                  <a-radio :value="false"> Nháp </a-radio>
                  <a-radio :value="true"> Hiệu lực </a-radio>
                </a-radio-group>
                <div style="color: red" v-if="checkDataInputStatus.show">
                  {{ checkDataInputStatus.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Ngày hiệu lực</a-col
              >
              <a-col flex="auto">
                <a-date-picker
                  @change="inputEditEffectiveDate"
                  v-model="dataEdit.effectiveDate"
                  :disabled-date="disableDateStart"
                  format="DD/MM/YYYY"
                  valueFormat="YYYY-MM-DD"
                  placeholder="Ngày hiệu lực"
                >
                </a-date-picker>
                <div style="color: red" v-if="checkDataInputEffectiveDate.show">
                  {{ checkDataInputEffectiveDate.message }}
                </div>
              </a-col>
            </a-row>
          </a-modal>
          <!-- popup edit-->
        </div>
      </a-layout-content>
      <Footer />
    </a-layout>
  </div>
</template>
 <script>
import bonusAdminService from "../service/bonusAdminService";
import moment from "moment";
export default {
  name: "BonusAdmin",
  components: {},
  data() {
    return {
      loadingEdit: false,
      loading: false,
      checkDataInputTitle: {
        show: false,
        message: "",
      },
      checkDataInputName: {
        show: false,
        message: "",
      },
      checkDataInputReason: {
        show: false,
        message: "",
      },
      checkDataInputMoney: {
        show: false,
        message: "",
      },
      checkDataInputStatus: {
        show: false,
        message: "",
      },
      checkDataInputEffectiveDate: {
        show: false,
        message: "",
      },
      pagination: {
        current: 1,
        pageSize: 10,
        total: 0,
      },
      dataSearch: {
        date: [],
        pageIndex: 1,
        pageSize: 10,
        status: "",
        title: "",
        total: 0,
      },
      dataSourceTable: [],
      dataEmployees: [],
      dataAdd: {
        effectiveDate: "",
        listIdEmployee: [],
        money: "",
        reason: "",
        status: true,
        title: "",
      },
      dataEmployee: {
        idPosition: "",
        name: "",
        pageIndex: 1,
        pageSize: 10,
        statusDelete: "",
      },
      dataEdit: {
        effectiveDate: "",
        id: 0,
        listIdEmployee: [],
        money: "",
        reason: "",
        status: true,
        title: "",
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
          title: "Tiêu đề",
          dataIndex: "title",
          key: "title",
          width: 150,
        },
        {
          title: "Số tiền",
          dataIndex: "money",
          key: "money",
          width: 150,
        },
        {
          title: "Lý do",
          dataIndex: "reason",
          key: "reason",
          width: 150,
        },
        {
          title: "Nhân viên được khen thưởng",
          dataIndex: "listIdEmployee",
          key: "listIdEmployee",
          width: 150,
          scopedSlots: { customRender: "listIdEmployee" },
        },
        {
          title: "Ngày hiệu lực",
          dataIndex: "effectiveDate",
          key: "effectiveDate",
          width: 150,
          scopedSlots: { customRender: "effectiveDate" },
        },
        {
          title: "Trạng thái",
          dataIndex: "status",
          key: "status",
          width: 150,
          scopedSlots: { customRender: "status" },
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
    };
  },
  created() {
    this.submitSearch();
  },
  methods: {
    checkEditOrDelete(record) {
      let date = record.effectiveDate.split("-")[2];
      if (parseInt(date) > 10) {
        let dateNow = moment();
        let dateLast = moment(record.effectiveDate)
          .add(1, "months")
          .set("date", 10);
        if (dateNow <= dateLast) {
          return true;
        } else {
          return false;
        }
      } else {
        let dateNow1 = moment();
        let dateLast1 = moment(record.effectiveDate).set("date", 10);
        if (dateNow1 <= dateLast1) {
          return true;
        } else {
          return false;
        }
      }
    },
    disableDateStart(current) {
      return current < moment().subtract(1, "days");
    },
    handleTableChange(pagination) {
      this.dataSearch.pageIndex = pagination.current;
      this.pagination = pagination;
      bonusAdminService
        .searchBonusAdmin(this.dataSearch)
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
      bonusAdminService
        .searchBonusAdmin(this.dataSearch)
        .then((response) => {
          this.dataSourceTable = response.data.data;
          this.dataSearch.total = response.data.total;
          this.pagination.total = response.data.total;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    search() {
      this.dataSearch.pageIndex = 1;
      this.dataSearch.total = 0;
      this.dataSearch.title = this.dataSearch.title.trim();
      this.submitSearch();
    },
    fetchEmployees(value) {
      this.dataEmployee.name = value;
      this.getAllEmployee();
    },
    getAllEmployee() {
      bonusAdminService
        .getAllEmployee(this.dataEmployee)
        .then((response) => {
          this.dataEmployees = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    showModalAdd() {
      this.checkDataInputTitle.show = false;
      this.checkDataInputTitle.message = "";
      this.checkDataInputName.show = false;
      this.checkDataInputName.message = "";
      this.checkDataInputReason.show = false;
      this.checkDataInputReason.message = "";
      this.checkDataInputMoney.show = false;
      this.checkDataInputMoney.message = "";
      this.checkDataInputStatus.show = false;
      this.checkDataInputStatus.message = "";
      this.checkDataInputEffectiveDate.show = false;
      this.checkDataInputEffectiveDate.message = "";
      this.dataAdd.effectiveDate = "";
      this.dataAdd.listIdEmployee = [];
      this.dataAdd.money = "";
      this.dataAdd.reason = "";
      this.dataAdd.status = true;
      this.dataAdd.title = "";
      this.visibleAdd = true;
      this.getAllEmployee();
    },
    submitAdd() {
      this.dataAdd.title = this.dataAdd.title.trim();
      this.dataAdd.reason = this.dataAdd.reason.trim();
      this.loading = true;
      bonusAdminService
        .addBonusAdmin(this.dataAdd)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Thêm khen thưởng";
            let description = "Thêm mới khen thưởng thành công !!";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Thêm khen thưởng";
            let description =
              "Thêm mới khen thưởng không thành công vì " +
              response.data.message;
            this.notifi(type, message, description);
          }
          this.loading = false;
          this.visibleAdd = false;
        })
        .catch((e) => {
          console.log(e);
          this.loading = false;
          this.visibleAdd = false;
        });
    },
    handleCancel() {
      this.visibleAdd = false;
      this.visibleEdit = false;
      this.visibleProfile = false;
    },
    showModalEdit(
      id,
      effectiveDate,
      listIdEmployees,
      money,
      reason,
      status,
      title
    ) {
      this.checkDataInputTitle.show = false;
      this.checkDataInputTitle.message = "";
      this.checkDataInputName.show = false;
      this.checkDataInputName.message = "";
      this.checkDataInputReason.show = false;
      this.checkDataInputReason.message = "";
      this.checkDataInputMoney.show = false;
      this.checkDataInputMoney.message = "";
      this.checkDataInputStatus.show = false;
      this.checkDataInputStatus.message = "";
      this.checkDataInputEffectiveDate.show = false;
      this.checkDataInputEffectiveDate.message = "";
      this.dataEdit.id = id;
      this.dataEdit.effectiveDate = "";
      this.dataEdit.listIdEmployee = [];
      for (var i = 0; i < listIdEmployees.length; i++) {
        this.dataEdit.listIdEmployee.push(listIdEmployees[i].id);
      }
      this.dataEdit.status = status;
      this.dataEdit.reason = reason;
      this.dataEdit.money = money;
      this.dataEdit.title = title;
      this.visibleEdit = true;
      this.getAllEmployee();
    },
    submitUpdate() {
      this.dataAdd.title = this.dataAdd.title.trim();
      this.dataAdd.reason = this.dataAdd.reason.trim();
      this.loadingEdit = true;
      bonusAdminService
        .updateBonusAdmin(this.dataEdit)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Cập nhật";
            let description = "Cập nhật thưởng thành công";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Cập nhật";
            let description = "Cập nhật thưởng không thành công";
            this.notifi(type, message, description);
          }
          this.loadingEdit = false;
          this.visibleEdit = false;
        })
        .catch((e) => {
          console.log(e);
          this.loadingEdit = false;
          this.visibleEdit = false;
        });
    },
    checkFormUpdate() {
      let check = true;
      if (this.dataEdit.title != null && this.dataEdit.title.trim() != "") {
        this.checkDataInputTitle.show = false;
        this.checkDataInputTitle.message = "";
      } else {
        check = false;
        this.checkDataInputTitle.show = true;
        this.checkDataInputTitle.message = "Bạn phải điền tiêu đề";
      }

      if (
        this.dataEdit.listIdEmployee != null &&
        this.dataEdit.listIdEmployee.length != 0
      ) {
        this.checkDataInputName.show = false;
        this.checkDataInputName.message = "";
      } else {
        check = false;
        this.checkDataInputName.show = true;
        this.checkDataInputName.message = "Bạn phải điền họ và tên";
      }

      if (this.dataEdit.reason != null && this.dataEdit.reason.trim() != "") {
        this.checkDataInputReason.show = false;
        this.checkDataInputReason.message = "";
      } else {
        check = false;
        this.checkDataInputReason.show = true;
        this.checkDataInputReason.message = "Bạn phải điền lý do";
      }

      if (this.dataEdit.money != null && this.dataEdit.money != "") {
        this.checkDataInputMoney.show = false;
        this.checkDataInputMoney.message = "";
      } else {
        check = false;
        this.checkDataInputMoney.show = true;
        this.checkDataInputMoney.message = "Bạn phải điền số tiền";
      }

      if (this.dataEdit.status != null) {
        this.checkDataInputStatus.show = false;
        this.checkDataInputStatus.message = "";
      } else {
        check = false;
        this.checkDataInputStatus.show = true;
        this.checkDataInputStatus.message = "Bạn phải chọn trạng thái";
      }

      if (
        this.dataEdit.effectiveDate != null &&
        this.dataEdit.effectiveDate.length != 0
      ) {
        this.checkDataInputEffectiveDate.show = false;
        this.checkDataInputEffectiveDate.message = "";
      } else {
        check = false;
        this.checkDataInputEffectiveDate.show = true;
        this.checkDataInputEffectiveDate.message =
          "Bạn phải chọn ngày hiệu lực";
      }

      if (check) {
        this.submitUpdate();
      }
    },
    deleteBonusAdmin(id) {
      bonusAdminService
        .deleteBonusAdmin(id)
        .then((response) => {
          if (response.data.data) {
            let type = "success";
            let message = "Xóa";
            let description =
              "Xóa khen thưởng " + this.dataAdd.title + " thành công";
            this.notifi(type, message, description);
            this.submitSearch();
          } else {
            let type = "error";
            let message = "Xóa";
            let description =
              "Xóa khen thưởng " + this.dataAdd.title + " không thành công";
            this.notifi(type, message, description);
            this.submitSearch();
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
    checkFormAdd() {
      let check = true;
      if (this.dataAdd.title != null && this.dataAdd.title.trim() != "") {
        this.checkDataInputTitle.show = false;
        this.checkDataInputTitle.message = "";
      } else {
        check = false;
        this.checkDataInputTitle.show = true;
        this.checkDataInputTitle.message = "Bạn phải điền tiêu đề";
      }

      if (
        this.dataAdd.listIdEmployee != null &&
        this.dataAdd.listIdEmployee.length != 0
      ) {
        this.checkDataInputName.show = false;
        this.checkDataInputName.message = "";
      } else {
        check = false;
        this.checkDataInputName.show = true;
        this.checkDataInputName.message = "Bạn phải điền họ và tên";
      }

      if (this.dataAdd.reason != null && this.dataAdd.reason.trim() != "") {
        this.checkDataInputReason.show = false;
        this.checkDataInputReason.message = "";
      } else {
        check = false;
        this.checkDataInputReason.show = true;
        this.checkDataInputReason.message = "Bạn phải điền lý do";
      }

      if (this.dataAdd.money != null && this.dataAdd.money != "") {
        this.checkDataInputMoney.show = false;
        this.checkDataInputMoney.message = "";
      } else {
        check = false;
        this.checkDataInputMoney.show = true;
        this.checkDataInputMoney.message = "Bạn phải điền số tiền";
      }

      if (this.dataAdd.status != null) {
        this.checkDataInputStatus.show = false;
        this.checkDataInputStatus.message = "";
      } else {
        check = false;
        this.checkDataInputStatus.show = true;
        this.checkDataInputStatus.message = "Bạn phải chọn trạng thái";
      }

      if (
        this.dataAdd.effectiveDate != null &&
        this.dataAdd.effectiveDate.length != 0
      ) {
        this.checkDataInputEffectiveDate.show = false;
        this.checkDataInputEffectiveDate.message = "";
      } else {
        check = false;
        this.checkDataInputEffectiveDate.show = true;
        this.checkDataInputEffectiveDate.message =
          "Bạn phải chọn ngày hiệu lực";
      }

      if (check) {
        this.submitAdd();
      }
    },
    inputTitle() {
      if (this.dataAdd.title != null && this.dataAdd.title.trim() != "") {
        this.checkDataInputTitle.show = false;
        this.checkDataInputTitle.message = "";
      } else {
        this.checkDataInputTitle.show = true;
        this.checkDataInputTitle.message = "Bạn phải điền tiêu đề";
      }
    },
    inputName() {
      if (
        this.dataAdd.listIdEmployee != null &&
        this.dataAdd.listIdEmployee.length != 0
      ) {
        this.checkDataInputName.show = false;
        this.checkDataInputName.message = "";
      } else {
        this.checkDataInputName.show = true;
        this.checkDataInputName.message = "Bạn phải điền họ và tên";
      }
    },
    inputReason() {
      if (this.dataAdd.reason != null && this.dataAdd.reason.trim() != "") {
        this.checkDataInputReason.show = false;
        this.checkDataInputReason.message = "";
      } else {
        this.checkDataInputReason.show = true;
        this.checkDataInputReason.message = "Bạn phải điền lý do";
      }
    },
    inputMoney() {
      if (this.dataAdd.money != null && this.dataAdd.money != "") {
        this.checkDataInputMoney.show = false;
        this.checkDataInputMoney.message = "";
      } else {
        this.checkDataInputMoney.show = true;
        this.checkDataInputMoney.message = "Bạn phải điền số tiền";
      }
    },
    inputStatus() {
      if (this.dataAdd.status != null) {
        this.checkDataInputStatus.show = false;
        this.checkDataInputStatus.message = "";
      } else {
        this.checkDataInputStatus.show = true;
        this.checkDataInputStatus.message = "Bạn phải chọn trạng thái";
      }
    },
    inputEffectiveDate() {
      if (
        this.dataAdd.effectiveDate != null &&
        this.dataAdd.effectiveDate.length != 0
      ) {
        this.checkDataInputEffectiveDate.show = false;
        this.checkDataInputEffectiveDate.message = "";
      } else {
        this.checkDataInputEffectiveDate.show = true;
        this.checkDataInputEffectiveDate.message =
          "Bạn phải chọn ngày hiệu lực";
      }
    },
    //edit
    inputEditTitle() {
      if (this.dataEdit.title != null && this.dataEdit.title.trim() != "") {
        this.checkDataInputTitle.show = false;
        this.checkDataInputTitle.message = "";
      } else {
        this.checkDataInputTitle.show = true;
        this.checkDataInputTitle.message = "Bạn phải điền tiêu đề";
      }
    },
    inputEditName() {
      if (
        this.dataEdit.listIdEmployee != null &&
        this.dataEdit.listIdEmployee.length != 0
      ) {
        this.checkDataInputName.show = false;
        this.checkDataInputName.message = "";
      } else {
        this.checkDataInputName.show = true;
        this.checkDataInputName.message = "Bạn phải điền họ và tên";
      }
    },
    inputEditReason() {
      if (this.dataEdit.reason != null && this.dataEdit.reason.trim() != "") {
        this.checkDataInputReason.show = false;
        this.checkDataInputReason.message = "";
      } else {
        this.checkDataInputReason.show = true;
        this.checkDataInputReason.message = "Bạn phải điền lý do";
      }
    },
    inputEditMoney() {
      if (this.dataEdit.money != null && this.dataEdit.money != "") {
        this.checkDataInputMoney.show = false;
        this.checkDataInputMoney.message = "";
      } else {
        this.checkDataInputMoney.show = true;
        this.checkDataInputMoney.message = "Bạn phải điền số tiền";
      }
    },
    inputEditStatus() {
      if (this.dataEdit.status != null) {
        this.checkDataInputStatus.show = false;
        this.checkDataInputStatus.message = "";
      } else {
        this.checkDataInputStatus.show = true;
        this.checkDataInputStatus.message = "Bạn phải chọn trạng thái";
      }
    },
    inputEditEffectiveDate() {
      if (
        this.dataEdit.effectiveDate != null &&
        this.dataEdit.effectiveDate.length != 0
      ) {
        this.checkDataInputEffectiveDate.show = false;
        this.checkDataInputEffectiveDate.message = "";
      } else {
        this.checkDataInputEffectiveDate.show = true;
        this.checkDataInputEffectiveDate.message =
          "Bạn phải chọn ngày hiệu lực";
      }
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

<style scoped>
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
</style>