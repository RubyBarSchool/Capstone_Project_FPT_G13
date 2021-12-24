<template>
  <div class="punish">
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
            v-model="dataSearch.title"
            :style="{ width: '150px', 'margin-right': '5px' }"
          />
          Trạng thái:
          <a-select
            placeholder="Trạng thái"
            @change="search"
            v-model="dataSearch.status"
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
          <!-- menu trên -->

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
              <template slot="listIdEmployee" slot-scope="text, record">
                <div
                  :key="index"
                  v-for="(data, index) in record.listIdEmployee"
                >
                  <!-- <div v-if="index != 0">,</div> -->
                  <div>{{ data.name }}</div>
                </div>
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
                      @confirm="deletePunishAdmin(record.id)"
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
          <a-modal v-model="visibleAdd" title="Thêm hình thức kỷ luật">
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
            <a-form-model>
              <a-row type="flex">
                <a-col flex="120px">
                  <span style="color: red">*</span> Tiêu đề:
                </a-col>
                <a-col flex="auto">
                  <a-input
                    v-model="dataAdd.title"
                    @change="inputTitleAdd"
                    placeholder="Tiêu đề"
                  />
                  <div style="color: red" v-if="checkInputTitle.show">
                    {{ checkInputTitle.message }}
                  </div>
                </a-col>
              </a-row>
              <br />
              <a-row type="flex">
                <a-col flex="120px">
                  <span style="color: red">*</span> Họ và tên:
                </a-col>
                <a-col flex="auto">
                  <a-select
                    placeholder="Họ và tên"
                    mode="multiple"
                    v-model="dataAdd.listIdEmployee"
                    :filter-option="false"
                    @search="fetchEmployees"
                    @change="inputListEmployeeAdd"
                  >
                    <a-select-option
                      v-for="(employee, index) in dataEmployees"
                      :value="employee.id"
                      :key="index"
                    >
                      {{ employee.fullName }}
                    </a-select-option>
                  </a-select>
                  <div style="color: red" v-if="checkInputListIdEmployee.show">
                    {{ checkInputListIdEmployee.message }}
                  </div>
                </a-col>
              </a-row>
              <br />
              <a-row type="flex">
                <a-col flex="120px">
                  <span style="color: red">*</span> Lý do:
                </a-col>
                <a-col flex="auto">
                  <a-textarea
                    placeholder="Lý do"
                    :rows="4"
                    v-model="dataAdd.reason"
                    @change="inputReasonAdd"
                  />
                  <div style="color: red" v-if="checkInputReason.show">
                    {{ checkInputReason.message }}
                  </div></a-col
                >
              </a-row>
              <br />
              <a-row type="flex">
                <a-col flex="120px">
                  <span style="color: red">*</span> Số tiền:
                </a-col>
                <a-col flex="auto">
                  <a-input-number
                    v-model="dataAdd.money"
                    :min="100000"
                    @change="inputMoneyAdd"
                    style="width: 100%"
                  />
                  <div style="color: red" v-if="checkInputMoney.show">
                    {{ checkInputMoney.message }}
                  </div></a-col
                >
              </a-row>
              <br />
              <a-row type="flex">
                <a-col flex="120px">
                  <span style="color: red">*</span> Trạng thái:
                </a-col>
                <a-col flex="auto">
                  <a-radio-group name="radioGroup" v-model="dataAdd.status">
                    <a-radio :value="false"> Nháp </a-radio>
                    <a-radio :value="true"> Hiệu lực </a-radio>
                  </a-radio-group></a-col
                >
              </a-row>
              <br />
              <a-row type="flex">
                <a-col flex="120px"
                  ><span style="color: red">*</span> Ngày hiệu lực:
                </a-col>
                <a-col flex="auto">
                  <a-date-picker
                    v-model="dataAdd.effectiveDate"
                    format="DD/MM/YYYY"
                    :disabled-date="disableDateStart"
                    valueFormat="YYYY-MM-DD"
                    @change="inputEffectiveDateAdd"
                    placeholder="Ngày hiệu lực"
                  >
                  </a-date-picker>
                  <div style="color: red" v-if="checkInputEffectiveDate.show">
                    {{ checkInputEffectiveDate.message }}
                  </div>
                </a-col>
              </a-row>
            </a-form-model>
          </a-modal>
          <!-- popup add -->

          <!-- popup edit-->
          <a-modal v-model="visibleEdit" title="Chỉnh sửa hình thức kỷ luật">
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
            <a-form-model>
              <span style="color: red">*</span> Tiêu đề:
              <a-form-model-item>
                <a-input
                  v-model="dataEdit.title"
                  @change="inputTitleEdit"
                  placeholder="Tiêu đề"
                />
                <div style="color: red" v-if="checkInputTitle.show">
                  {{ checkInputTitle.message }}
                </div>
              </a-form-model-item>
              <span style="color: red">*</span> Họ và tên:
              <a-form-model-item>
                <a-select
                  placeholder="Họ và tên"
                  mode="multiple"
                  disabled
                  v-model="dataEdit.listIdEmployee"
                  :filter-option="false"
                  @search="fetchEmployees"
                  @change="inputListEmployeeEdit"
                >
                  <a-select-option
                    v-for="(employee, index) in dataEmployees"
                    :value="employee.id"
                    :key="index"
                  >
                    {{ employee.fullName }}
                  </a-select-option>
                </a-select>
                <div style="color: red" v-if="checkInputListIdEmployee.show">
                  {{ checkInputListIdEmployee.message }}
                </div>
              </a-form-model-item>
              <span style="color: red">*</span> Lý do:
              <a-form-model-item>
                <a-textarea
                  placeholder="Lý do"
                  :rows="4"
                  v-model="dataEdit.reason"
                  @change="inputReasonEdit"
                />
                <div style="color: red" v-if="checkInputReason.show">
                  {{ checkInputReason.message }}
                </div>
              </a-form-model-item>
              <span style="color: red">*</span> Số tiền:
              <a-form-model-item>
                <a-input-number
                  v-model="dataEdit.money"
                  :min="100000"
                  @change="inputMoneyEdit"
                />
                <div style="color: red" v-if="checkInputMoney.show">
                  {{ checkInputMoney.message }}
                </div>
              </a-form-model-item>
              <span style="color: red">*</span> Trạng thái:
              <a-form-model-item>
                <a-radio-group name="radioGroup" v-model="dataEdit.status">
                  <a-radio :value="false"> Nháp </a-radio>
                  <a-radio :value="true"> Hiệu lực </a-radio>
                </a-radio-group>
              </a-form-model-item>
              <span style="color: red">*</span> Ngày hiệu lực:
              <a-form-model-item>
                <a-date-picker
                  v-model="dataEdit.effectiveDate"
                  :disabled-date="disableDateStart"
                  format="DD/MM/YYYY"
                  valueFormat="YYYY-MM-DD"
                  @change="inputEffectiveDateEdit"
                  placeholder="Ngày hiệu lực"
                />
                <div style="color: red" v-if="checkInputEffectiveDate.show">
                  {{ checkInputEffectiveDate.message }}
                </div>
              </a-form-model-item>
            </a-form-model>
          </a-modal>
          <!-- popup edit-->
        </div>
      </a-layout-content>
    </a-layout>
  </div>
</template>
<script>
import punishAdminService from "@/service/punishAdminService";
import moment from "moment";
export default {
  name: "PunishAdmin",
  components: {},
  data() {
    return {
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
        status: "",
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
          title: "Nhân viên chịu hình phạt",
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
      checkInputTitle: {
        show: false,
        message: "",
      },
      checkInputFullName: {
        show: false,
        message: "",
      },
      checkInputReason: {
        show: false,
        message: "",
      },
      checkInputMoney: {
        show: false,
        message: "",
      },
      checkInputEffectiveDate: {
        show: false,
        message: "",
      },
      checkInputListIdEmployee: {
        show: false,
        message: "",
      },
      visibleAdd: false,
      visibleEdit: false,
      loadingAdd: false,
      loadingEdit: false,
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
      punishAdminService
        .searchPunishAdmin(this.dataSearch)
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
      punishAdminService
        .searchPunishAdmin(this.dataSearch)
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
      punishAdminService
        .getAllEmployee(this.dataEmployee)
        .then((response) => {
          this.dataEmployees = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    showModalAdd() {
      this.dataAdd.title = "";
      this.dataAdd.money = "";
      this.dataAdd.reason = "";
      this.dataAdd.status = true;
      this.dataAdd.effectiveDate = "";
      this.dataAdd.listIdEmployee = [];
      this.getAllEmployee();
      this.checkInputTitle.show = false;
      this.checkInputTitle.message = "";
      this.checkInputReason.show = false;
      this.checkInputReason.message = "";
      this.checkInputMoney.show = false;
      this.checkInputMoney.message = "";
      this.checkInputEffectiveDate.show = false;
      this.checkInputEffectiveDate.message = "";
      this.checkInputListIdEmployee.show = false;
      this.checkInputListIdEmployee.message = "";
      this.visibleAdd = true;
    },
    checkFormAdd() {
      let check = true;
      if (this.dataAdd.title != null && this.dataAdd.title.trim() != "") {
        this.checkInputTitle.show = false;
        this.checkInputTitle.message = "";
      } else {
        check = false;
        this.checkInputTitle.show = true;
        this.checkInputTitle.message = "Bạn phải điền vào ô tiêu đề";
      }
      if (this.dataAdd.reason != null && this.dataAdd.reason.trim() != "") {
        this.checkInputReason.show = false;
        this.checkInputReason.message = "";
      } else {
        check = false;
        this.checkInputReason.show = true;
        this.checkInputReason.message = "Bạn phải điền vào ô lý do";
      }
      if (this.dataAdd.money != null && this.dataAdd.money != "") {
        this.checkInputMoney.show = false;
        this.checkInputMoney.message = "";
      } else {
        check = false;
        this.checkInputMoney.show = true;
        this.checkInputMoney.message = "Bạn phải điền vào ô số tiền";
      }
      if (
        this.dataAdd.effectiveDate != null &&
        this.dataAdd.effectiveDate.trim() != ""
      ) {
        this.checkInputEffectiveDate.show = false;
        this.checkInputEffectiveDate.message = "";
      } else {
        check = false;
        this.checkInputEffectiveDate.show = true;
        this.checkInputEffectiveDate.message =
          "Bạn phải điền vào ô ngày hiệu lực";
      }
      if (
        this.dataAdd.listIdEmployee != null &&
        this.dataAdd.listIdEmployee.length != 0
      ) {
        this.checkInputListIdEmployee.show = false;
        this.checkInputListIdEmployee.message = "";
      } else {
        check = false;
        this.checkInputListIdEmployee.show = true;
        this.checkInputListIdEmployee.message = "Bạn phải điền vào ô họ và tên";
      }
      if (check == true) {
        this.submitAdd();
      }
    },
    inputTitleAdd() {
      if (this.dataAdd.title != null && this.dataAdd.title.trim() != "") {
        this.checkInputTitle.show = false;
        this.checkInputTitle.message = "";
      } else {
        this.checkInputTitle.show = true;
        this.checkInputTitle.message = "Bạn phải điền vào ô tiêu đề";
      }
    },
    inputReasonAdd() {
      if (this.dataAdd.reason != null && this.dataAdd.reason.trim() != "") {
        this.checkInputReason.show = false;
        this.checkInputReason.message = "";
      } else {
        this.checkInputReason.show = true;
        this.checkInputReason.message = "Bạn phải điền vào ô lý do";
      }
    },
    inputMoneyAdd() {
      if (this.dataAdd.money != null && this.dataAdd.money != "") {
        this.checkInputMoney.show = false;
        this.checkInputMoney.message = "";
      } else {
        this.checkInputMoney.show = true;
        this.checkInputMoney.message = "Bạn phải điền vào ô số tiền";
      }
    },
    inputEffectiveDateAdd() {
      if (
        this.dataAdd.effectiveDate != null &&
        this.dataAdd.effectiveDate.trim() != ""
      ) {
        this.checkInputEffectiveDate.show = false;
        this.checkInputEffectiveDate.message = "";
      } else {
        this.checkInputEffectiveDate.show = true;
        this.checkInputEffectiveDate.message =
          "Bạn phải điền vào ô ngày hiệu lực";
      }
    },
    inputListEmployeeAdd() {
      if (
        this.dataAdd.listIdEmployee != null &&
        this.dataAdd.listIdEmployee.length != 0
      ) {
        this.checkInputListIdEmployee.show = false;
        this.checkInputListIdEmployee.message = "";
      } else {
        this.checkInputListIdEmployee.show = true;
        this.checkInputListIdEmployee.message = "Bạn phải điền vào ô họ và tên";
      }
    },
    submitAdd() {
      this.loadingAdd = true;
      punishAdminService
        .addPunishAdmin(this.dataAdd)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Thêm đơn phạt";
            let description = "Thêm mới đơn phạt thành công !!";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Thêm đơn phạt";
            let description =
              "Thêm mới đơn phạt không thành công vì " + response.data.message;
            this.notifi(type, message, description);
          }
          this.loadingAdd = false;
          this.visibleAdd = false;
        })
        .catch(() => {
          this.loadingAdd = false;
          this.visibleAdd = false;
        });
    },
    checkFormEdit() {
      let check = true;
      if (this.dataEdit.title != null && this.dataEdit.title.trim() != "") {
        this.checkInputTitle.show = false;
        this.checkInputTitle.message = "";
      } else {
        check = false;
        this.checkInputTitle.show = true;
        this.checkInputTitle.message = "Bạn phải điền vào ô tiêu đề";
      }
      if (this.dataEdit.reason != null && this.dataEdit.reason.trim() != "") {
        this.checkInputReason.show = false;
        this.checkInputReason.message = "";
      } else {
        check = false;
        this.checkInputReason.show = true;
        this.checkInputReason.message = "Bạn phải điền vào ô lý do";
      }
      if (this.dataEdit.money != null && this.dataEdit.money != "") {
        this.checkInputMoney.show = false;
        this.checkInputMoney.message = "";
      } else {
        check = false;
        this.checkInputMoney.show = true;
        this.checkInputMoney.message = "Bạn phải điền vào ô số tiền";
      }
      if (
        this.dataEdit.effectiveDate != null &&
        this.dataEdit.effectiveDate.trim() != ""
      ) {
        this.checkInputEffectiveDate.show = false;
        this.checkInputEffectiveDate.message = "";
      } else {
        check = false;
        this.checkInputEffectiveDate.show = true;
        this.checkInputEffectiveDate.message =
          "Bạn phải điền vào ô ngày hiệu lực";
      }
      if (
        this.dataEdit.listIdEmployee != null &&
        this.dataEdit.listIdEmployee.length != 0
      ) {
        this.checkInputListIdEmployee.show = false;
        this.checkInputListIdEmployee.message = "";
      } else {
        check = false;
        this.checkInputListIdEmployee.show = true;
        this.checkInputListIdEmployee.message = "Bạn phải điền vào ô họ và tên";
      }
      if (check == true) {
        this.submitUpdate();
      }
    },
    inputTitleEdit() {
      if (this.dataEdit.title != null && this.dataEdit.title.trim() != "") {
        this.checkInputTitle.show = false;
        this.checkInputTitle.message = "";
      } else {
        this.checkInputTitle.show = true;
        this.checkInputTitle.message = "Bạn phải điền vào ô tiêu đề";
      }
    },
    inputReasonEdit() {
      if (this.dataEdit.reason != null && this.dataEdit.reason.trim() != "") {
        this.checkInputReason.show = false;
        this.checkInputReason.message = "";
      } else {
        this.checkInputReason.show = true;
        this.checkInputReason.message = "Bạn phải điền vào ô lý do";
      }
    },
    inputMoneyEdit() {
      if (this.dataEdit.money != null && this.dataEdit.money != "") {
        this.checkInputMoney.show = false;
        this.checkInputMoney.message = "";
      } else {
        this.checkInputMoney.show = true;
        this.checkInputMoney.message = "Bạn phải điền vào ô số tiền";
      }
    },
    inputEffectiveDateEdit() {
      if (
        this.dataEdit.effectiveDate != null &&
        this.dataEdit.effectiveDate.trim() != ""
      ) {
        this.checkInputEffectiveDate.show = false;
        this.checkInputEffectiveDate.message = "";
      } else {
        this.checkInputEffectiveDate.show = true;
        this.checkInputEffectiveDate.message =
          "Bạn phải điền vào ô ngày hiệu lực";
      }
    },
    inputListEmployeeEdit() {
      if (
        this.dataEdit.listIdEmployee != null &&
        this.dataEdit.listIdEmployee.length != 0
      ) {
        this.checkInputListIdEmployee.show = false;
        this.checkInputListIdEmployee.message = "";
      } else {
        this.checkInputListIdEmployee.show = true;
        this.checkInputListIdEmployee.message = "Bạn phải điền vào ô họ và tên";
      }
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

      this.checkInputTitle.show = false;
      this.checkInputTitle.message = "";
      this.checkInputReason.show = false;
      this.checkInputReason.message = "";
      this.checkInputMoney.show = false;
      this.checkInputMoney.message = "";
      this.checkInputEffectiveDate.show = false;
      this.checkInputEffectiveDate.message = "";
      this.checkInputListIdEmployee.show = false;
      this.checkInputListIdEmployee.message = "";
      this.visibleEdit = true;
      this.getAllEmployee();
    },
    submitUpdate() {
      // this.dataEdit.effectiveDate.
      this.loadingEdit = true;
      punishAdminService
        .updatePunishAdmin(this.dataEdit)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Cập nhật";
            let description = "Cập nhật đơn phạt thành công";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Cập nhật";
            let description = "Cập nhật đơn phạt không thành công";
            this.notifi(type, message, description);
          }
          this.loadingEdit = false;
          this.visibleEdit = false;
        })
        .catch(() => {
          this.loadingEdit = false;
          this.visibleEdit = false;
        });
    },
    deletePunishAdmin(id) {
      punishAdminService
        .deletePunishAdmin(id)
        .then((response) => {
          if (response.data.data) {
            let type = "success";
            let message = "Xóa";
            let description =
              "Xóa đơn phạt " + this.dataAdd.title + " thành công";
            this.notifi(type, message, description);
            this.submitSearch();
          } else {
            let type = "error";
            let message = "Xóa";
            let description =
              "Xóa đơn phạt " + this.dataAdd.title + " không thành công";
            this.notifi(type, message, description);
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
    handleCancel() {
      this.visibleAdd = false;
      this.visibleEdit = false;
      this.visibleProfile = false;
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