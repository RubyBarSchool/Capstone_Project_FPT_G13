<template>
  <div class="taohopdong">
    <a-layout :style="{ background: 'white' }">
      <a-layout-content :style="{ margin: '24px 16px 0' }">
        <div
          :style="{
            minHeight: '360px',
            background: 'white',
          }"
        >
          <a-back-top :style="{ width: '5vh', height: '15vh' }" />
          <!-- menu trên -->
          <a-input
            placeholder="Tên hợp đồng"
            :style="{ width: '150px', 'margin-right': '5px' }"
            v-model="dataSearch.name"
          />
          Ngày bàn giao
          <a-range-picker
            @change="onChange"
            :placeholder="['Ngày bắt đầu', 'Ngày kết thúc']"
            :style="{ 'margin-right': '5px' }"
            v-model="dataSearch.listDate"
            format="DD/MM/YYYY"
          />
          <a-select
            placeholder="Khách hàng"
            mode="multiple"
            v-model="dataSearch.listIdCompany"
            :filter-option="false"
            @search="fetchCompany"
            style="width: 250px"
          >
            <a-select-option
              v-for="(company, index) in companys"
              :value="company.id"
              :key="index"
            >
              {{ company.name }}
            </a-select-option>
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
              :icon="['fas', 'file-signature']"
              :style="{ 'margin-right': '5px' }"
            />
            Thêm
          </a-button>

          <a-button
            type="primary"
            @click="downloadTemplate"
            :style="{ 'margin-left': '5px' }"
          >
            <font-awesome-icon
              :icon="['fas', 'download']"
              :style="{ 'margin-right': '5px' }"
            />
            Mẫu hợp đồng
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
              <template slot="statusDone" slot-scope="text, record">
                <a-tag
                  :color="
                    record.statusDone == -2
                      ? '#5DDFDF'
                      : record.statusDone == -1
                      ? '#DF5B5B'
                      : record.statusDone == -0
                      ? '#DFDF5D'
                      : '#5D5DDF'
                  "
                >
                  {{
                    record.statusDone == -2
                      ? "Mới tạo"
                      : record.statusDone == -1
                      ? "Đang thực hiện"
                      : record.statusDone == -0
                      ? "Chờ bàn giao"
                      : "Đã bàn giao"
                  }}
                </a-tag>
              </template>
              <template slot="note" slot-scope="text, record">
                {{ record.note }}
              </template>
              <template slot="createDate" slot-scope="text, record">
                {{
                  new Date(record.createDate).toLocaleDateString("en-GB", {
                    year: "numeric",
                    month: "2-digit",
                    day: "2-digit",
                  })
                }}
              </template>
              <template slot="dateFinish" slot-scope="text, record">
                {{
                  new Date(record.dateFinish).toLocaleDateString("en-GB", {
                    year: "numeric",
                    month: "2-digit",
                    day: "2-digit",
                  })
                }}
              </template>
              <template slot="action" slot-scope="text, record">
                <a-button
                  v-if="record.statusDone == -2"
                  id="edit"
                  @click="
                    showModalEdit(
                      record.name,
                      record.id,
                      record.dateFinish,
                      record.idCompany
                    )
                  "
                  :style="{ width: '44.25px' }"
                >
                  <font-awesome-icon :icon="['fas', 'edit']" />
                </a-button>

                <a-button
                  v-if="record.statusDone == 0"
                  @click="submitExport(record.id)"
                  :style="{ width: '44.25px' }"
                >
                  <font-awesome-icon :icon="['fas', 'download']" />
                </a-button>

                <a-popconfirm
                  v-if="record.statusDone == -2"
                  title="Bạn có chắc chắn muốn xóa không?"
                  ok-text="Đồng ý"
                  cancel-text="Hủy"
                  @confirm="deleteContact(record.id)"
                >
                  <a-button id="delete">
                    <font-awesome-icon :icon="['fas', 'trash']" />
                  </a-button>
                </a-popconfirm>
              </template>
            </a-table>
          </div>
          <!-- table content -->

          <!-- popup add-->
          <a-modal v-model="visibleAdd" title="Thêm hợp đồng">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button
                :loading="loadingAdd"
                key="submit"
                type="primary"
                @click="checkBeforeAdd"
              >
                Lưu
              </a-button>
            </template>
            <a-form-model>
              <a-row type="flex">
                <a-col flex="150px">
                  <span style="color: red">*</span>Tên hợp đồng:
                </a-col>
                <a-col flex="auto">
                  <a-input @change="inputNameContact" v-model="dataAdd.name" />
                  <div style="color: red" v-if="checkNameContact.show">
                    {{ checkNameContact.message }}
                  </div>
                </a-col>
              </a-row>
              <br />
              <a-row type="flex">
                <a-col flex="150px"
                  ><span style="color: red">*</span>Khách hàng:
                </a-col>
                <a-col flex="auto">
                  <a-select
                    @change="selectCompany"
                    v-model="dataAdd.idCompany"
                    style="width: 100%"
                  >
                    <a-select-option
                      v-for="(company, index) in companys"
                      :value="company.id"
                      :key="index"
                    >
                      {{ company.name }}
                    </a-select-option>
                  </a-select>
                  <div style="color: red" v-if="checkCompany.show">
                    {{ checkCompany.message }}
                  </div></a-col
                >
              </a-row>
              <br />
              <a-row type="flex">
                <a-col flex="150px">
                  <span style="color: red">*</span>Hạn hoàn thành:
                </a-col>
                <a-col flex="auto">
                  <a-date-picker
                    :disabled-date="disableDateStart"
                    @change="selectDateEnd"
                    v-model="dataAdd.time"
                    placeholder="Hạn hoàn thành"
                    format="DD/MM/YYYY"
                    style="width: 100%"
                  />
                  <div style="color: red" v-if="checkDateEnd.show">
                    {{ checkDateEnd.message }}
                  </div>
                </a-col>
              </a-row>
              <br />
              <a-row type="flex">
                <a-col flex="150px"
                  ><span style="color: red">*</span>Tổng giá trị:
                </a-col>
                <a-col flex="auto">
                  <a-input
                    @change="changeTotalMoney"
                    v-model="fileExcel.priceContact"
                    disabled
                  />
                  <div style="color: red" v-if="checkTotalMoney.show">
                    {{ checkTotalMoney.message }}
                  </div></a-col
                >
              </a-row>
              <br />
              <a-row type="flex">
                <a-col flex="150px"> Ghi chú: </a-col>
                <a-col flex="auto">
                  <a-textarea
                    :auto-size="{
                      minRows: 1,
                      maxRows: 6,
                    }"
                    v-model="fileExcel.noteContact"
                    disabled
                /></a-col>
              </a-row>
              <br />
              <a-row type="flex">
                <a-col flex="150px">
                  <span style="color: red">*</span>Bảng chi tiết:</a-col
                >
                <a-col flex="auto">
                  <input
                    type="file"
                    ref="fileupload"
                    accept=".xls, .xlsx"
                    @change="importFile($event)"
                  />
                  <div style="color: red" v-if="checkTableDetail.show">
                    {{ checkTableDetail.message }}
                  </div>
                </a-col>
              </a-row>

              <div
                class="progress-bar progress-bar-info progress-bar-striped"
                role="progressbar"
                :aria-valuenow="progress"
                aria-valuemin="0"
                aria-valuemax="100"
                :style="{ width: progress + '%' }"
              >
                {{ progress }}%
              </div>
            </a-form-model>
          </a-modal>
          <!-- popup add-->

          <!-- popup edit-->
          <a-modal v-model="visibleEdit" title="Chỉnh sửa hợp đồng">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button
                :loading="loadingAdd"
                key="submit"
                type="primary"
                @click="submitUpdate"
              >
                Lưu
              </a-button>
            </template>
            <a-form-model>
              <a-row type="flex">
                <a-col flex="150px"
                  ><span style="color: red">*</span>Tên hợp đồng:
                </a-col>
                <a-col flex="auto">
                  <a-input
                    @change="inputEditNameContact"
                    v-model="dataEdit.name"
                  />
                  <div style="color: red" v-if="checkNameContact.show">
                    {{ checkNameContact.message }}
                  </div>
                </a-col>
              </a-row>
              <br />
              <a-row type="flex">
                <a-col flex="150px">
                  <span style="color: red">*</span>Khách hàng:
                </a-col>
                <a-col flex="auto">
                  <a-select
                    v-model="dataEditCompany.id"
                    style="width: 100%"
                    disabled
                  >
                    <a-select-option
                      v-for="(company, index) in companys"
                      :value="company.id"
                      :key="index"
                    >
                      {{ company.name }}
                    </a-select-option>
                  </a-select>
                  <div style="color: red" v-if="checkCompany.show">
                    {{ checkCompany.message }}
                  </div></a-col
                >
              </a-row>
              <br />
              <a-row type="flex">
                <a-col flex="150px"
                  ><span style="color: red">*</span>Hạn hoàn thành:
                </a-col>
                <a-col flex="auto">
                  <a-date-picker
                    @change="selectEditDateEnd"
                    :disabled-date="disableDateStart"
                    v-model="dataEdit.dateFinish"
                    format="DD/MM/YYYY"
                    style="width: 100%"
                  />
                  <div style="color: red" v-if="checkDateEnd.show">
                    {{ checkDateEnd.message }}
                  </div></a-col
                >
              </a-row>
            </a-form-model>
          </a-modal>
          <!-- popup edit-->
        </div>
      </a-layout-content>
    </a-layout>
  </div>
</template>

<script>
import fileService from "@/service/fileService.js";
import company from "@/service/companyService.js";
import contact from "@/service/contactService.js";
import contactService from "@/service/contactService";
import moment from "moment";

export default {
  name: "Contact",
  components: {},
  data() {
    return {
      loadingAdd: false,
      // data,
      pagination: {
        current: 1,
        pageSize: 10,
        total: 0,
      },
      fileList: [],
      // columns,
      fileExcel: {
        noteContact: "",
        priceContact: "",
        fileProductVOList: [],
      },
      dataAdd: {
        name: "",
        idCompany: "",
        time: "",
        fileExcel: [],
      },
      companys: [],
      dataCompany: {
        address: "",
        name: "",
        phone: "",
        pageIndex: 1,
        pageSize: 10,
      },
      dataSearch: {
        listDate: [],
        listIdCompany: [],
        name: "",
        pageIndex: 1,
        pageSize: 10,
        total: 0,
      },
      dataEdit: {
        dateFinish: "",
        id: "",
        name: "",
      },
      dataEditCompany: {
        id: "",
      },
      message: "",
      progress: 0,
      visibleAdd: false,
      visibleEdit: false,
      dataSourceTable: [],
      columns: [
        {
          title: "STT",
          width: 100,
          dataIndex: "id",
          key: "id",
          fixed: "left",
        },
        {
          title: "Tên hợp đồng",
          dataIndex: "name",
          key: "name",
          width: 150,
        },
        {
          title: "Ngày tạo hợp đồng",
          dataIndex: "createDate",
          key: "createDate",
          width: 150,
          scopedSlots: { customRender: "createDate" },
        },
        {
          title: "Ngày bàn giao",
          dataIndex: "dateFinish",
          key: "dateFinish",
          width: 150,
          scopedSlots: { customRender: "dateFinish" },
        },
        {
          title: "Khách hàng",
          dataIndex: "nameCompany",
          key: "nameCompany",
          width: 150,
        },
        {
          title: "Tổng giá trị",
          dataIndex: "totalMoney",
          key: "totalMoney",
          width: 150,
        },
        {
          title: "Định mức hoàn thành",
          dataIndex: "numberFinish",
          key: "numberFinish",
          width: 150,
        },
        {
          title: "Trạng thái",
          dataIndex: "statusDone",
          key: "statusDone",
          width: 150,
          scopedSlots: { customRender: "statusDone" },
        },
        { title: "Ghi chú", dataIndex: "note", key: "note", width: 150 },
        {
          title: "",
          key: "operation",
          width: 150,
          scopedSlots: { customRender: "action" },
        },
      ],
      checkNameContact: {
        show: false,
        message: "",
      },
      checkCompany: {
        show: false,
        message: "",
      },
      checkDateEnd: {
        show: false,
        message: "",
      },
      checkTotalMoney: {
        show: false,
        message: "",
      },
      checkNote: {
        show: false,
        message: "",
      },
      checkTableDetail: {
        show: false,
        message: "",
      },
    };
  },
  created() {
    this.getAllCompany();
    this.submitSearch();
  },
  watch: {
    urlState(newValue) {
      if (newValue.indexOf("/contact") != -1) {
        this.submitSearch();
      }
    },
  },
  computed: {
    urlState() {
      return this.$store.state.url;
    },
  },
  methods: {
    downloadTemplate() {
      contactService
        .templateContact()
        .then((response) => {
          let url = window.URL.createObjectURL(
            new Blob([response.data], {
              type: "application/vnd.ms-excel",
            })
          );
          let link = document.createElement("a");
          link.href = url;
          link.setAttribute("download", "Mẫu hợp đồng.xlsx");
          document.body.appendChild(link);
          link.click();
          URL.revokeObjectURL(link.href);
        })
        .catch((e) => {
          console.log(e);
        });
    },

    submitExport(id) {
      contactService
        .exportContact(id)
        .then((response) => {
          let url = window.URL.createObjectURL(
            new Blob([response.data], {
              type: "application/vnd.ms-excel",
            })
          );
          let link = document.createElement("a");
          link.href = url;
          link.setAttribute("download", "Hợp đồng.xlsx");
          document.body.appendChild(link);
          link.click();
          URL.revokeObjectURL(link.href);
        })
        .catch((e) => {
          console.log(e);
        });
    },
    disableDateStart(current) {
      return current < moment().subtract(1, "days");
    },
    changeNoteContact() {
      if (
        this.fileExcel.noteContact != null &&
        this.fileExcel.noteContact.trim() != ""
      ) {
        this.checkTotalMoney.show = false;
        this.checkTotalMoney.message = "";
      } else {
        this.checkTotalMoney.show = true;
        this.checkTotalMoney.message =
          "Bạn phải xem lại tổng tiền trong tệp hợp đồng";
      }
    },
    changeTotalMoney() {
      if (
        this.fileExcel.priceContact != null &&
        this.fileExcel.priceContact.trim() != ""
      ) {
        this.checkTotalMoney.show = false;
        this.checkTotalMoney.message = "";
      } else {
        this.checkTotalMoney.show = true;
        this.checkTotalMoney.message =
          "Bạn phải xem lại tổng tiền trong tệp hợp đồng";
      }
    },
    selectEditDateEnd() {
      if (this.dataEdit.dateFinish != null && this.dataEdit.dateFinish != "") {
        this.checkDateEnd.show = false;
        this.checkDateEnd.message = "";
      } else {
        this.checkDateEnd.show = true;
        this.checkDateEnd.message = "Bạn phải chọn ngày hết hạn";
      }
    },
    selectDateEnd() {
      if (this.dataAdd.time != null && this.dataAdd.time != "") {
        this.checkDateEnd.show = false;
        this.checkDateEnd.message = "";
      } else {
        this.checkDateEnd.show = true;
        this.checkDateEnd.message = "Bạn phải chọn ngày hết hạn";
      }
    },
    selectCompany() {
      if (this.dataAdd.idCompany != null && this.dataAdd.idCompany != "") {
        this.checkCompany.show = false;
        this.checkCompany.message = "";
      } else {
        this.checkCompany.show = true;
        this.checkCompany.message = "Bạn phải chọn khách hàng";
      }
    },
    inputEditNameContact() {
      if (this.dataEdit.name != null && this.dataEdit.name.trim() != "") {
        this.checkNameContact.show = false;
        this.checkNameContact.message = "";
      } else {
        this.checkNameContact.show = true;
        this.checkNameContact.message = "Bạn phải điền tên hợp đồng";
      }
    },
    inputNameContact() {
      if (this.dataAdd.name != null && this.dataAdd.name.trim() != "") {
        this.checkNameContact.show = false;
        this.checkNameContact.message = "";
      } else {
        this.checkNameContact.show = true;
        this.checkNameContact.message = "Bạn phải điền tên hợp đồng";
      }
    },
    handleTableChange(pagination) {
      this.dataSearch.pageIndex = pagination.current;
      this.pagination = pagination;
      contactService
        .searchCompany(this.dataSearch)
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
      contactService
        .searchCompany(this.dataSearch)
        .then((response) => {
          this.dataSourceTable = response.data.data;
          this.dataSearch.total = response.data.total;
          this.pagination.total = response.data.total;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    fetchCompany(value) {
      this.dataCompany.name = value;
      this.getAllCompany();
    },
    getAllCompany() {
      company
        .searchCompany(this.dataCompany)
        .then((response) => {
          this.companys = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    importFile(event1) {
      if (event1.target.files[0]) {
        this.progress = 0;
        fileService
          .importContact(event1.target.files[0], (event) => {
            this.progress = Math.round((100 * event.loaded) / event.total);
          })
          .then((response) => {
            this.message = response.data.message;
            this.fileExcel = response.data.data;
            this.checkTableDetail.show = false;
            this.checkTableDetail.message = "";
            this.changeTotalMoney();
          })
          .catch((e) => {
            this.progress = 0;
            this.message = "Không thể upload file";
            this.checkTableDetail.show = true;
            this.checkTableDetail.message = "Bạn phải thêm tệp hợp đồng";
            console.log(e);
          });
      }
    },
    checkBeforeAdd() {
      let check = true;
      if (this.dataAdd.name != null && this.dataAdd.name.trim() != "") {
        this.checkNameContact.show = false;
        this.checkNameContact.message = "";
      } else {
        check = false;
        this.checkNameContact.show = true;
        this.checkNameContact.message = "Bạn phải điền tên hợp đồng";
      }

      if (this.dataAdd.idCompany != null && this.dataAdd.idCompany != "") {
        this.checkCompany.show = false;
        this.checkCompany.message = "";
      } else {
        check = false;
        this.checkCompany.show = true;
        this.checkCompany.message = "Bạn phải chọn khách hàng";
      }

      if (this.dataAdd.time != null && this.dataAdd.time != "") {
        this.checkDateEnd.show = false;
        this.checkDateEnd.message = "";
      } else {
        check = false;
        this.checkDateEnd.show = true;
        this.checkDateEnd.message = "Bạn phải chọn ngày hết hạn";
      }

      if (
        this.fileExcel.priceContact != null &&
        this.fileExcel.priceContact.trim() != ""
      ) {
        this.checkTotalMoney.show = false;
        this.checkTotalMoney.message = "";
      } else {
        check = false;
        this.checkTotalMoney.show = true;
        this.checkTotalMoney.message =
          "Bạn phải xem lại tổng tiền trong tệp hợp đồng";
      }

      if (
        this.fileExcel.priceContact != null &&
        this.fileExcel.priceContact.trim() != "" &&
        this.fileExcel.fileProductVOList != null &&
        this.fileExcel.fileProductVOList.length != 0
      ) {
        this.checkTableDetail.show = false;
        this.checkTableDetail.message = "";
      } else {
        check = false;
        this.checkTableDetail.show = true;
        this.checkTableDetail.message = "Bạn phải thêm tệp hợp đồng";
      }
      if (check) {
        this.handleSubmit();
      }
    },
    handleSubmit() {
      this.loadingAdd = true;
      this.dataAdd.fileExcel = this.fileExcel;
      contact
        .submitContact(this.dataAdd)
        .then((response) => {
          if (response.data.data) {
            let type = "success";
            let message = "Thêm mới";
            let description = response.data.message;
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Thêm mới";
            let description = response.data.message;
            this.notifi(type, message, description);
          }
          this.loadingAdd = false;
          this.visibleAdd = false;
          this.submitSearch();
        })
        .catch((e) => {
          this.loadingAdd = false;
          console.log(e);
        });
    },
    onChange(date, dateString) {
      console.log(date, dateString);
    },
    showModalAdd() {
      this.visibleAdd = true;
      this.progress = 0;
      this.dataAdd.name = "";
      this.dataAdd.idCompany = "";
      this.dataAdd.time = "";
      this.fileExcel = [];
      if (this.$refs.fileupload != null) {
        this.$refs.fileupload.value = null;
      }
      this.checkDateEnd.show = false;
      this.checkDateEnd.message = "";
      this.checkCompany.show = false;
      this.checkCompany.message = "";
      this.checkNameContact.show = false;
      this.checkNameContact.message = "";
      this.checkTotalMoney.show = false;
      this.checkTotalMoney.message = "";
      this.checkTableDetail.show = false;
      this.checkTableDetail.message = "";
    },
    handleCancel() {
      this.visibleAdd = false;
      this.visibleEdit = false;
    },
    showModalEdit(name, id, time, idCompany) {
      this.dataEdit.dateFinish = time;
      this.dataEdit.name = name;
      this.dataEdit.id = id;
      this.dataEditCompany.id = idCompany;
      this.checkNameContact.show = false;
      this.checkNameContact.message = "";
      this.checkCompany.show = false;
      this.checkCompany.message = "";
      this.checkDateEnd.show = false;
      this.checkDateEnd.message = "";
      this.visibleEdit = true;
    },
    checkBeforeEdit() {
      let check = true;
      if (this.dataEdit.name != null && this.dataEdit.name.trim() != "") {
        this.checkNameContact.show = false;
        this.checkNameContact.message = "";
      } else {
        check = false;
        this.checkNameContact.show = true;
        this.checkNameContact.message = "Bạn phải điền tên hợp đồng";
      }

      if (this.dataAdd.idCompany != null && this.dataAdd.idCompany != "") {
        this.checkCompany.show = false;
        this.checkCompany.message = "";
      } else {
        check = false;
        this.checkCompany.show = true;
        this.checkCompany.message = "Bạn phải chọn khách hàng";
      }

      if (this.dataEdit.dateFinish != null && this.dataEdit.dateFinish != "") {
        this.checkDateEnd.show = false;
        this.checkDateEnd.message = "";
      } else {
        check = false;
        this.checkDateEnd.show = true;
        this.checkDateEnd.message = "Bạn phải chọn ngày hết hạn";
      }

      if (check) {
        this.submitUpdate();
      }
    },
    submitUpdate() {
      this.loadingAdd = true;
      contactService
        .updateContact(this.dataEdit)
        .then((response) => {
          if (response.data.data) {
            let type = "success";
            let message = "Cập nhật";
            let description = response.data.message;
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Cập nhật";
            let description = response.data.message;
            this.notifi(type, message, description);
          }
          this.submitSearch();
          this.visibleEdit = false;
          this.loadingAdd = false;
        })
        .catch((e) => {
          this.loadingAdd = false;
          this.visibleEdit = false;
          console.log(e);
        });
    },
    deleteContact(id) {
      contactService
        .deleteContact(id)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Xóa";
            let description = response.data.message;
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Xóa";
            let description = response.data.message;
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