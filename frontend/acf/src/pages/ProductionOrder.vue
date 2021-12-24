<template>
  <div class="attendance">
    <a-layout :style="{ background: 'white' }">
      <a-layout-content :style="{ margin: '30px 16px 0' }">
        <div
          :style="{
            minHeight: '360px',
            background: 'white',
          }"
        >
          <a-back-top :style="{ width: '5vh', height: '15vh' }" />
          <a-select
            v-model="dataSearch.listIdContact"
            @change="changeSearch"
            placeholder="Hợp đồng"
            mode="multiple"
            :style="{ 'margin-right': '5px', width: '15%' }"
          >
            <a-select-option
              v-for="(contact, index) in dataContact"
              :value="contact.id"
              :key="index"
            >
              {{ contact.name }}
            </a-select-option>
          </a-select>
          <a-input
            v-model="dataSearch.nameProduction"
            placeholder="Tên lệnh sản xuất"
            :style="{ 'margin-right': '10px', width: '15%' }"
          />
          Ngày hoàn thành:
          <a-range-picker
            @change="changeSearch"
            v-model="dataSearch.dateList"
            :placeholder="['Từ ngày', 'Đến ngày']"
            format="DD/MM/YYYY"
            :style="{ 'margin-right': '10px' }"
          />
          Trạng thái:
          <a-select
            v-model="dataSearch.status"
            @change="changeSearch"
            style="width: 10%"
          >
            <a-select-option key=""> Tất Cả </a-select-option>
            <a-select-option key="-2"> Chờ duyệt </a-select-option>
            <a-select-option key="-1"> Đang chờ xác nhận </a-select-option>
            <a-select-option key="0"> Đang làm </a-select-option>
            <a-select-option key="1"> Hoàn thành </a-select-option>
          </a-select>
          <a-button
            type="primary"
            @click="beforeSearch"
            :style="{ 'margin-left': '5px' }"
          >
            <font-awesome-icon
              :icon="['fas', 'search']"
              :style="{ 'margin-right': '5px' }"
            />
            Tìm Kiếm
          </a-button>
          <a-button
            type="primary"
            @click="openModalAdd"
            :style="{ 'margin-left': '5px' }"
          >
            <font-awesome-icon
              :icon="['fas', 'file-signature']"
              :style="{ 'margin-right': '5px' }"
            />
            Thêm
          </a-button>

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
              <template slot="createDate" slot-scope="text, record">
                {{
                  new Date(record.createDate).toLocaleDateString("en-GB", {
                    year: "numeric",
                    month: "2-digit",
                    day: "2-digit",
                  })
                }}
              </template>
              <template slot="dateStart" slot-scope="text, record">
                {{
                  new Date(record.dateStart).toLocaleDateString("en-GB", {
                    year: "numeric",
                    month: "2-digit",
                    day: "2-digit",
                  })
                }}
              </template>
              <template slot="dateEnd" slot-scope="text, record">
                {{
                  new Date(record.dateEnd).toLocaleDateString("en-GB", {
                    year: "numeric",
                    month: "2-digit",
                    day: "2-digit",
                  })
                }}
              </template>
              <template slot="status" slot-scope="text, record">
                <a-tag
                  :color="
                    record.status == '-2'
                      ? '#108ee9'
                      : record.status == '-1'
                      ? '#f50'
                      : record.status == '0'
                      ? '#2db7f5'
                      : '#87d068'
                  "
                >
                  {{
                    record.status == "-2"
                      ? "Đang chờ duyệt"
                      : record.status == "-1"
                      ? "Đang chờ xác nhận"
                      : record.status == "0"
                      ? "Đang làm"
                      : "Hoàn thành"
                  }}
                </a-tag>
              </template>

              <template slot="action" slot-scope="text, record">
                <a-row v-if="record.status == -2">
                  <a-col :span="8">
                    <a-popconfirm
                      v-if="record.status == '-2'"
                      title="Bạn có chắc chắn xác nhận hoàn thành không?"
                      @confirm="confirm(record)"
                    >
                      <a-button>
                        <font-awesome-icon :icon="['fas', 'check-circle']" />
                      </a-button>
                    </a-popconfirm>
                  </a-col>
                </a-row>
                <a-row v-if="record.status != -2">
                  <a-col :span="8">
                    <a-button
                      id="view"
                      @click="showModelView(record)"
                      :style="{ width: '44.25px', 'margin-right': '100px' }"
                    >
                      <font-awesome-icon :icon="['fas', 'eye']" />
                    </a-button>
                  </a-col>
                  <a-col :span="8" v-if="record.status == -1">
                    <a-button
                      id="edit"
                      @click="showEditForm(record)"
                      :style="{ width: '44.25px' }"
                    >
                      <font-awesome-icon :icon="['fas', 'edit']" />
                    </a-button>
                  </a-col>
                  <a-col :span="8" v-if="record.status == -1">
                    <a-popconfirm
                      v-if="record.status == '-1'"
                      title="Bạn có chắc chắn muốn xóa không?"
                      @confirm="deleteProductionOrder(record)"
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

            <!-- thêm mới lệnh sản xuất -->
            <a-modal v-model="showModalAdd" title="Thêm lệnh sản xuất">
              <template slot="footer">
                <a-button key="back" @click="handleCancelAdd"> Đóng </a-button>
                <a-button
                  :loading="loadingAdd"
                  key="submit"
                  type="primary"
                  @click="checkBeforeSave"
                >
                  Lưu
                </a-button>
              </template>
              <a-form-model>
                <a-row type="flex">
                  <a-col flex="200px"
                    ><span style="color: red">*</span>Lệnh sản xuất:
                  </a-col>
                  <a-col flex="auto">
                    <a-input
                      @change="changeNameProductionOrder"
                      v-model="dataSubmit.name"
                      style="width: 100%"
                      placeholder="Nhập tên lệnh sản xuất"
                    />
                    <div
                      style="color: red"
                      v-if="checkNameProductionOrder.show"
                    >
                      {{ checkNameProductionOrder.message }}
                    </div>
                  </a-col>
                </a-row>
                <br />
                <a-row type="flex">
                  <a-col flex="200px">
                    <span style="color: red">*</span>Tên hợp đồng:
                  </a-col>
                  <a-col flex="auto">
                    <a-select
                      v-model="dataSubmit.idContact"
                      @change="changeContact"
                      placeholder="Hợp đồng"
                      style="width: 100%"
                    >
                      <a-select-option
                        v-for="(contact, index) in dataContactInForm"
                        :value="contact.idContact"
                        :key="index"
                      >
                        {{ contact.name }}
                      </a-select-option>
                    </a-select>
                    <div style="color: red" v-if="checkNameContact.show">
                      {{ checkNameContact.message }}
                    </div>
                  </a-col>
                </a-row>
                <br />
                <a-row type="flex">
                  <a-col flex="200px">
                    <span style="color: red">*</span>Tên sản phẩm:
                  </a-col>
                  <a-col flex="auto">
                    <a-select
                      @change="changeNameProduct"
                      v-model="dataSubmit.idProduct"
                      :disabled="disableProduct"
                      placeholder="Hợp đồng"
                      style="width: 100%"
                    >
                      <a-select-option
                        v-for="(contact, index) in dataProductIncontact"
                        :value="contact.id"
                        :key="index"
                      >
                        {{ contact.name }}
                      </a-select-option>
                    </a-select>
                    <div style="color: red" v-if="checkNameProduct.show">
                      {{ checkNameProduct.message }}
                    </div>
                  </a-col>
                </a-row>
                <br />
                <a-row type="flex">
                  <a-col flex="200px">
                    <span style="color: red">*</span>Ngày bắt đầu:</a-col
                  >
                  <a-col flex="auto">
                    <a-date-picker
                      @change="changeDateStart"
                      style="width: 100%"
                      :disabled="disabledDate"
                      :disabled-date="disableDateStartAdd"
                      v-model="dataSubmit.dateStart"
                      placeholder="Ngày bắt đầu"
                      format="DD/MM/YYYY"
                    />
                    <div style="color: red" v-if="checkDateStart.show">
                      {{ checkDateStart.message }}
                    </div>
                  </a-col>
                </a-row>
                <br />
                <a-row type="flex">
                  <a-col flex="200px">
                    <span style="color: red">*</span>Ngày hoàn thành:
                  </a-col>
                  <a-col flex="auto">
                    <a-date-picker
                      @change="changeDateEnd"
                      style="width: 100%"
                      :disabled="disabledDate"
                      :disabled-date="disableDateEndAdd"
                      v-model="dataSubmit.dateEnd"
                      placeholder="Ngày hoàn thành"
                      format="DD/MM/YYYY"
                    />
                    <div style="color: red" v-if="checkDateEnd.show">
                      {{ checkDateEnd.message }}
                    </div></a-col
                  >
                </a-row>
                <br />
                <a-row type="flex">
                  <a-col flex="200px">
                    <span style="color: red">*</span>Xem công việc:
                  </a-col>
                  <a-col flex="auto">
                    <a-button
                      style="width: 100%"
                      :disabled="disabledDate"
                      type="primary"
                      @click="showWorkEmployee"
                    >
                      Chi tiết công việc
                    </a-button>
                    <div style="color: red" v-if="checkEmployees.show">
                      {{ checkEmployees.message }}
                    </div>
                  </a-col>
                </a-row>
              </a-form-model>
            </a-modal>

            <!-- show work of employee -->
            <a-modal
              v-model="showModalViewWork1"
              height="100%"
              width="80%"
              title="Xem công việc của nhân viên"
            >
              <template slot="footer">
                <a-button key="back" @click="handleCancelViewWork">
                  Đóng
                </a-button>
                <a-button
                  key="submit"
                  type="primary"
                  :disabled="disableSaveAdd1"
                  @click="submitAddEmployee"
                >
                  Lưu
                </a-button>
              </template>
              <a-table
                :columns="columnsViewWork"
                :data-source="dataTableViewWork"
                :pagination="false"
                :scroll="{ x: 1000 }"
                :rowKey="
                  (record, index) => {
                    return record.id;
                  }
                "
                :row-selection="{
                  selectedRowKeys: selectedRowKeys,
                  selectedRows: selectedRows,
                  onChange: onSelectChange,
                }"
              >
                <template slot="average" slot-scope="text, record">
                  <a-tag
                    :color="
                      record.average < 2
                        ? '#108ee9'
                        : record.average < 3
                        ? '#e6c000'
                        : '#f50'
                    "
                  >
                    {{ record.average }}
                  </a-tag>
                </template>
              </a-table>
            </a-modal>

            <!-- chỉnh sửa lệnh sản xuất -->
            <a-modal v-model="showModalEdit" title="Chỉnh sửa lệnh sản xuất">
              <template slot="footer">
                <a-button key="back" @click="handleCancelEdit"> Đóng </a-button>
                <a-button key="submit" type="primary" @click="checkBeforeSave">
                  Lưu
                </a-button>
              </template>

              <a-form-model>
                <a-row type="flex">
                  <a-col flex="150px">
                    <span style="color: red">*</span>Lệnh sản xuất:
                  </a-col>
                  <a-col flex="auto">
                    <a-input
                      v-model="dataSubmit.name"
                      @change="changeNameProductionOrder"
                      style="width: 100%"
                      placeholder="Nhập tên lệnh sản xuất"
                    />
                    <div
                      style="color: red"
                      v-if="checkNameProductionOrder.show"
                    >
                      {{ checkNameProductionOrder.message }}
                    </div>
                  </a-col>
                </a-row>
                <br />
                <a-row type="flex">
                  <a-col flex="150px">
                    <span style="color: red">*</span>Tên hợp đồng:
                  </a-col>
                  <a-col flex="auto">
                    <a-select
                      v-model="nameConatctEdit"
                      disabled
                      @change="changeContact"
                      placeholder="Hợp đồng"
                      style="width: 100%"
                    >
                      <a-select-option
                        v-for="(contact, index) in dataContactInForm"
                        :value="contact.idContact"
                        :key="index"
                      >
                        {{ contact.name }}
                      </a-select-option>
                    </a-select>
                    <div style="color: red" v-if="checkNameContact.show">
                      {{ checkNameContact.message }}
                    </div>
                  </a-col>
                </a-row>
                <br />
                <a-row type="flex">
                  <a-col flex="150px">
                    <span style="color: red">*</span>Tên sản phẩm:
                  </a-col>
                  <a-col flex="auto">
                    <a-select
                      @change="changeNameProduct"
                      v-model="nameProductEdit"
                      disabled
                      placeholder="Hợp đồng"
                      style="width: 100%"
                    >
                      <a-select-option
                        v-for="(contact, index) in dataProductIncontact"
                        :value="contact.id"
                        :key="index"
                      >
                        {{ contact.name }}
                      </a-select-option>
                    </a-select>
                    <div style="color: red" v-if="checkNameProduct.show">
                      {{ checkNameProduct.message }}
                    </div>
                  </a-col>
                </a-row>
                <br />
                <a-row type="flex">
                  <a-col flex="150px">
                    <span style="color: red">*</span>Ngày bắt đầu:
                  </a-col>
                  <a-col flex="auto">
                    <a-date-picker
                      :disabled="disabledDate"
                      style="width: 100%"
                      @change="changeDateStart"
                      :disabled-date="disableDateStart"
                      v-model="dataSubmit.dateStart"
                      placeholder="Ngày bắt đầu"
                      format="DD/MM/YYYY"
                    />
                    <div style="color: red" v-if="checkDateStart.show">
                      {{ checkDateStart.message }}
                    </div>
                  </a-col>
                </a-row>
                <br />
                <a-row type="flex">
                  <a-col flex="150px">
                    <span style="color: red">*</span>Ngày hoàn thành:
                  </a-col>
                  <a-col flex="auto">
                    <a-date-picker
                      :disabled="disabledDate"
                      @change="changeDateEnd"
                      style="width: 100%"
                      :disabled-date="disableDateEnd"
                      v-model="dataSubmit.dateEnd"
                      placeholder="Ngày hoàn thành"
                      format="DD/MM/YYYY"
                    />
                    <div style="color: red" v-if="checkDateEnd.show">
                      {{ checkDateEnd.message }}
                    </div>
                  </a-col>
                </a-row>
                <br />
                <a-row type="flex">
                  <a-col flex="150px">
                    <span style="color: red">*</span>Xem công việc:
                  </a-col>
                  <a-col flex="auto">
                    <a-button
                      :disabled="disabledDate"
                      type="primary"
                      @click="showWorkEmployeeEdit"
                    >
                      Chi tiết công việc
                    </a-button>
                    <div style="color: red" v-if="checkEmployees.show">
                      {{ checkEmployees.message }}
                    </div>
                  </a-col>
                </a-row>
              </a-form-model>
            </a-modal>

            <!-- view detail -->
            <a-modal v-model="showModalView" title="Xem nhân viên thực hiện">
              <template slot="footer">
                <a-button key="back" @click="handleModalView"> Đóng </a-button>
              </template>
              <a-table
                :columns="columnsEmployee"
                :data-source="dataSourceEmployee"
                :pagination="false"
                :rowKey="
                  (record, index) => {
                    return record.idEmployee;
                  }
                "
              >
              </a-table>
            </a-modal>

            <!-- show work of employee -->
            <a-modal
              v-model="showModalViewWork"
              height="100%"
              width="80%"
              title="Xem công việc của nhân viên"
            >
              <template slot="footer">
                <a-button key="back" @click="handleCancelViewWork">
                  Đóng
                </a-button>
                <a-button
                  key="submit"
                  type="primary"
                  :disabled="disableSaveAdd1"
                  @click="submitAddEmployee"
                >
                  Lưu
                </a-button>
              </template>
              <a-table
                :columns="columnsViewWork"
                :data-source="dataTableViewWork"
                :pagination="false"
                :scroll="{ x: 1000 }"
                :rowKey="
                  (record, index) => {
                    return record.id;
                  }
                "
                :row-selection="{
                  selectedRowKeys: selectedRowKeys,
                  selectedRows: selectedRows,
                  onChange: onSelectChange,
                }"
              >
                <template slot="average" slot-scope="text, record">
                  <a-tag
                    :color="
                      record.average < 2
                        ? '#108ee9'
                        : record.average < 3
                        ? '#e6c000'
                        : '#f50'
                    "
                  >
                    {{ record.average }}
                  </a-tag>
                </template>
              </a-table>
            </a-modal>
          </div>
        </div>
      </a-layout-content>
      <Footer />
    </a-layout>
  </div>
</template>
 <script>
import viewDetailContactService from "@/service/viewDetailContactService.js";
import ContactService from "@/service/contactService.js";
import ProductionOrderService from "@/service/ProductionOrderService.js";
import ProductService from "@/service/productService.js";
import moment from "moment";
// import EditableCell from "@/components/EditableCell.vue";
export default {
  name: "productionorder",
  components: {
    // EditableCell,
  },
  data() {
    return {
      loadingAdd: false,
      pagination: {
        current: 1,
        pageSize: 10,
        total: 0,
      },
      dataSearch: {
        listIdContact: [],
        nameProduction: "",
        dateList: [],
        status: "",
        pageIndex: 1,
        pageSize: 10,
      },
      dataSubmit: {
        id: "",
        name: "",
        idContact: "",
        idProduct: "",
        dateStart: "",
        dateEnd: "",
        idEmployees: [],
      },
      dataContact: [],
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
          title: "Lệnh sản xuất",
          dataIndex: "name",
          key: "name",
          width: 150,
        },
        {
          title: "Ngày tạo",
          dataIndex: "createDate",
          key: "createDate",
          width: 150,
          scopedSlots: { customRender: "createDate" },
        },
        {
          title: "Hợp đồng",
          dataIndex: "nameContact",
          key: "nameContact",
          width: 150,
        },
        {
          title: "Mã Sản phẩm",
          dataIndex: "idProduct",
          key: "idProduct",
          width: 150,
        },
        {
          title: "Sản phẩm",
          dataIndex: "nameProduct",
          key: "nameProduct",
          width: 150,
        },
        {
          title: "Số lượng",
          dataIndex: "count",
          key: "count",
          width: 150,
        },
        {
          title: "Ngày bắt đầu",
          dataIndex: "dateStart",
          key: "dateStart",
          width: 150,
          scopedSlots: { customRender: "dateStart" },
        },
        {
          title: "Ngày hoàn thành",
          dataIndex: "dateEnd",
          key: "dateEnd",
          width: 150,
          scopedSlots: { customRender: "dateEnd" },
        },
        {
          title: "Trạng thái",
          dataIndex: "status",
          key: "status",
          width: 150,
          scopedSlots: { customRender: "status" },
        },
        {
          title: "Tiến độ công việc",
          dataIndex: "numberFinish",
          key: "numberFinish",
          width: 150,
        },
        {
          title: "",
          dataIndex: "action",
          key: "action",
          width: 180,
          fixed: "right",
          scopedSlots: { customRender: "action" },
        },
      ],
      columnsEmployee: [
        {
          title: "Số thứ tự",
          dataIndex: "idEmployee",
          key: "idEmployee",
          width: 150,
        },
        {
          title: "Tên nhân viên",
          dataIndex: "nameEmployee",
          key: "nameEmployee",
          width: 150,
        },
      ],
      columnsViewWork: [],
      dataTableViewWork: [],
      showModalAdd: false,
      showModalViewWork: false,
      showModalViewWork1: false,
      disableSaveAdd: true,
      disableProduct: true,
      disableContact: true,
      selectedRowKeys: [],
      selectedRows: [],
      idEmployeeChoose: [],
      disableSaveAdd1: true,
      dataContactInForm: [],
      dataProductIncontact: [],
      disabledDate: true,
      datestart: "2021-11-11",
      dateEnd: "2021-11-11",
      dataSourceEmployee: [],
      showModalView: false,
      showModalEdit: false,
      disableSaveEdit: true,
      dataSearchContact: {
        name: "",
        pageIndex: 1,
        pageSize: 10,
        total: 0,
      },
      checkNameProductionOrder: {
        show: false,
        message: "",
      },
      checkNameContact: {
        show: false,
        message: "",
      },
      checkNameProduct: {
        show: false,
        message: "",
      },
      checkDateStart: {
        show: false,
        message: "",
      },
      checkDateEnd: {
        show: false,
        message: "",
      },
      checkEmployees: {
        show: false,
        message: "",
      },
      nameConatctEdit: "",
      nameProductEdit: "",
    };
  },
  created() {
    this.getContact();
    this.beforeSearch();
  },
  watch: {
    urlState(newValue) {
      if (newValue.indexOf("/productionorder") != -1) {
        this.search();
      }
    },
  },
  computed: {
    urlState() {
      return this.$store.state.url;
    },
  },
  methods: {
    changeNameProductionOrder() {
      if (this.dataSubmit.name != null && this.dataSubmit.name.trim() != "") {
        this.checkNameProductionOrder.show = false;
        this.checkNameProductionOrder.message = "";
      } else {
        this.checkNameProductionOrder.show = true;
        this.checkNameProductionOrder.message =
          "Bạn phải điền tên lệnh sản xuất";
      }
    },
    changeNameProduct() {
      if (
        this.dataSubmit.idProduct != null &&
        this.dataSubmit.idProduct != ""
      ) {
        this.checkNameProduct.show = false;
        this.checkNameProduct.message = "";
      } else {
        this.checkNameProduct.show = true;
        this.checkNameProduct.message = "Bạn phải chọn sản phẩm";
      }
    },
    changeDateStart() {
      if (
        this.dataSubmit.dateStart != null &&
        this.dataSubmit.dateStart != ""
      ) {
        this.checkDateStart.show = false;
        this.checkDateStart.message = "";
      } else {
        this.checkDateStart.show = true;
        this.checkDateStart.message = "Bạn phải chọn ngày bắt đầu";
      }
    },
    changeDateEnd() {
      if (this.dataSubmit.dateEnd != null && this.dataSubmit.dateEnd != "") {
        this.checkDateEnd.show = false;
        this.checkDateEnd.message = "";
      } else {
        this.checkDateEnd.show = true;
        this.checkDateEnd.message = "Bạn phải chọn ngày hoàn thành";
      }
    },
    confirm(record) {
      ProductionOrderService.confirmProductionOrder(record.id)
        .then((response) => {
          let task = response.data.data ? "success" : "error";
          let text = response.data.data
            ? "Xác nhận lệnh sản xuất thành công"
            : "Xác nhận lệnh sản xuất không thành công";
          let description = response.data.data
            ? "Xác nhận lệnh sản xuất thành công:  " + record.name
            : "Xác nhận lệnh sản xuất không thành công: " + record.name;
          this.notifi(task, text, description);
          this.beforeSearch();
        })
        .catch((e) => {
          console.log(e);
        });
    },
    showEditForm(record) {
      this.cleanData();
      this.dataSubmit.id = record.id;
      this.dataSubmit.name = record.name;
      this.dataSubmit.idContact = record.idContact;
      this.dataSubmit.idProduct = record.idProduct;
      this.dataSubmit.dateStart = record.dateStart;
      this.dataSubmit.dateEnd = record.dateEnd;

      this.nameConatctEdit = record.nameContact;
      this.nameProductEdit = record.nameProduct;
      this.getEmployeeInProductionOrder(record.id);
      this.getContactEdit();
      this.disabledDate = false;
      this.showModalEdit = true;
    },
    getEmployeeInProductionOrder(id) {
      ProductionOrderService.getDetailProduction(id)
        .then((response) => {
          let data = response.data.data;
          for (let i = 0; i < data.length; i++) {
            this.dataSubmit.idEmployees.push(data[i].idEmployee);
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
    handleModalView() {
      this.showModalView = false;
    },
    onSelectChange(selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys;
      this.selectedRows = selectedRows;
      this.idEmployeeChoose = [];
      for (let i = 0; i < selectedRows.length; i++) {
        this.idEmployeeChoose.push(selectedRows[i].id);
      }
      if (selectedRowKeys.length != 0) {
        this.disableSaveAdd1 = false;
      } else {
        this.disableSaveAdd1 = true;
      }
    },
    handleCancelViewWork() {
      this.showModalViewWork = false;
      this.showModalViewWork1 = false;
    },
    showWorkEmployee() {
      this.selectedRowKeys = this.dataSubmit.idEmployees;
      let countCheck = 0;
      if (this.dataSubmit.dateStart == "") {
        let type = "error";
        let message = "Chưa chọn ngày bắt đầu";
        let description = "Bạn hãy chọn ngày bắt đầu của lệnh sản xuất";
        this.notifi(type, message, description);
        countCheck++;
      }
      if (this.dataSubmit.dateEnd == "") {
        let type = "error";
        let message = "Chưa chọn ngày kết thúc";
        let description = "Bạn hãy chọn ngày kết thúc của lệnh sản xuất";
        this.notifi(type, message, description);
        countCheck++;
      }
      if (countCheck != 0) {
        return;
      }
      let data = {
        dateStart: this.dataSubmit.dateStart,
        dateEnd: this.dataSubmit.dateEnd,
      };
      ProductionOrderService.viewWorkEmployee(data)
        .then((response) => {
          this.dataTableViewWork = response.data.data.data;
          this.columnsViewWork = response.data.data.columns;
          this.showModalViewWork1 = true;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    showWorkEmployeeEdit() {
      this.selectedRowKeys = this.dataSubmit.idEmployees;
      let countCheck = 0;
      if (this.dataSubmit.dateStart == "") {
        let type = "error";
        let message = "Chưa chọn ngày bắt đầu";
        let description = "Bạn hãy chọn ngày bắt đầu của lệnh sản xuất";
        this.notifi(type, message, description);
        countCheck++;
      }
      if (this.dataSubmit.dateEnd == "") {
        let type = "error";
        let message = "Chưa chọn ngày kết thúc";
        let description = "Bạn hãy chọn ngày kết thúc của lệnh sản xuất";
        this.notifi(type, message, description);
        countCheck++;
      }
      if (countCheck != 0) {
        return;
      }
      let data = {
        dateStart: this.dataSubmit.dateStart,
        dateEnd: moment(this.dataSubmit.dateEnd).add(1, "days"),
      };
      ProductionOrderService.viewWorkEmployee(data)
        .then((response) => {
          this.dataTableViewWork = response.data.data.data;
          this.columnsViewWork = response.data.data.columns;
          this.showModalViewWork = true;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    checkBeforeSave() {
      let check = true;
      if (this.dataSubmit.name != null && this.dataSubmit.name.trim() != "") {
        this.checkNameProductionOrder.show = false;
        this.checkNameProductionOrder.message = "";
      } else {
        check = false;
        this.checkNameProductionOrder.show = true;
        this.checkNameProductionOrder.message =
          "Bạn phải điền tên lệnh sản xuất";
      }

      if (
        this.dataSubmit.idContact != null &&
        this.dataSubmit.idContact != ""
      ) {
        this.checkNameContact.show = false;
        this.checkNameContact.message = "";
      } else {
        check = false;
        this.checkNameContact.show = true;
        this.checkNameContact.message = "Bạn phải điền tên hợp đồng";
      }

      if (
        this.dataSubmit.idProduct != null &&
        this.dataSubmit.idProduct != ""
      ) {
        this.checkNameProduct.show = false;
        this.checkNameProduct.message = "";
      } else {
        check = false;
        this.checkNameProduct.show = true;
        this.checkNameProduct.message = "Bạn phải chọn sản phẩm";
      }

      if (
        this.dataSubmit.dateStart != null &&
        this.dataSubmit.dateStart != ""
      ) {
        this.checkDateStart.show = false;
        this.checkDateStart.message = "";
      } else {
        check = false;
        this.checkDateStart.show = true;
        this.checkDateStart.message = "Bạn phải chọn ngày bắt đầu";
      }

      if (this.dataSubmit.dateEnd != null && this.dataSubmit.dateEnd != "") {
        this.checkDateEnd.show = false;
        this.checkDateEnd.message = "";
      } else {
        check = false;
        this.checkDateEnd.show = true;
        this.checkDateEnd.message = "Bạn phải chọn ngày hoàn thành";
      }

      if (
        this.dataSubmit.idEmployees != null &&
        this.dataSubmit.idEmployees.length != 0
      ) {
        this.checkEmployees.show = false;
        this.checkEmployees.message = "";
      } else {
        check = false;
        this.checkEmployees.show = true;
        this.checkEmployees.message = "Bạn phải chọn nhân viên thực hiện";
      }
      if (check) {
        this.submitAddProductionOrder();
      }
    },
    submitAddProductionOrder() {
      this.dataSubmit.name = this.dataSubmit.name.trim();
      this.dataSubmit.dateEnd = moment(this.dataSubmit.dateEnd).add(
        "1",
        "days"
      );
      this.loadingAdd = true;
      ProductionOrderService.addOrUpdateProductOrder(this.dataSubmit)
        .then((response) => {
          this.showModalAdd = false;
          this.beforeSearch();
          let type = "success";
          let message = "Thêm mới thành công";
          let description = response.data.message;
          this.notifi(type, message, description);
          this.showModalEdit = false;
          this.loadingAdd = false;
        })
        .catch((e) => {
          this.loadingAdd = false;
          console.log(e);
        });
    },
    submitUpdateProductionOrder() {
      this.dataSubmit.dateEnd = moment(this.dataSubmit.dateEnd).add(
        "1",
        "days"
      );
      this.loadingAdd = true;
      ProductionOrderService.addOrUpdateProductOrder(this.dataSubmit)
        .then((response) => {
          this.showModalAdd = false;
          this.beforeSearch();
          let type = "success";
          let message = "sửa thành công";
          let description = response.data.message;
          this.notifi(type, message, description);
          this.loadingAdd = false;
        })
        .catch((e) => {
          this.loadingAdd = false;
          console.log(e);
        });
    },
    submitAddEmployee() {
      this.dataSubmit.idEmployees = this.idEmployeeChoose;
      if (this.dataSubmit.idEmployees.length != 0) {
        this.disableSaveAdd = false;
      } else {
        this.disableSaveAdd = true;
      }
      this.showModalViewWork = false;
      this.showModalViewWork1 = false;
    },
    cleanData() {
      this.dataSubmit.id = "";
      this.dataSubmit.name = "";
      this.dataSubmit.idContact = "";
      this.dataSubmit.idProduct = "";
      this.dataSubmit.dateStart = "";
      this.dataSubmit.dateEnd = "";
      this.dataSubmit.idEmployees = [];
      this.dataContactInForm = [];
      this.dataProductIncontact = [];
      this.disableProduct = true;
      this.disabledDate = true;
      this.disableSaveEdit = true;
      this.checkNameProductionOrder.show = false;
      this.checkNameProductionOrder.message = "";
      this.checkNameContact.show = false;
      this.checkNameContact.message = "";
      this.checkNameProduct.show = false;
      this.checkNameProduct.message = "";
      this.checkDateStart.show = false;
      this.checkDateStart.message = "";
      this.checkDateEnd.show = false;
      this.checkDateEnd.message = "";
      this.checkEmployees.show = false;
      this.checkEmployees.message = "";
    },
    deleteProductionOrder(record) {
      ProductionOrderService.deleteProductOrder(record.id)
        .then(() => {
          this.beforeSearch();
        })
        .catch((e) => {
          console.log(e);
        });
    },
    showModelView(record) {
      // data detail
      this.dataSourceEmployee = [];
      ProductionOrderService.getDetailProduction(record.id)
        .then((response) => {
          this.dataSourceEmployee = response.data.data;
          this.showModalView = true;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    openModalAdd() {
      this.showModalAdd = true;
      this.cleanData();
      this.getContactInForm();
    },
    handleCancelAdd() {
      this.showModalAdd = false;
    },
    handleCancelEdit() {
      this.showModalEdit = false;
    },
    changeSearch() {
      this.beforeSearch();
    },
    handleTableChange(pagination) {
      this.dataSearch.pageIndex = pagination.current;
      this.pagination = pagination;
      this.search();
    },
    beforeSearch() {
      this.dataSearch.nameProduction = this.dataSearch.nameProduction.trim();
      this.dataSearch.pageIndex = 1;
      this.pagination.current = 1;
      this.search();
    },
    search() {
      ProductionOrderService.searchProductOrder(this.dataSearch)
        .then((response) => {
          this.dataSourceTable = response.data.data;
          this.dataSearch.total = response.data.total;
          this.pagination.total = response.data.total;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    changeSelect(value) {
      this.dataSearchContact.name = value;
      this.getContact();
    },
    getContact() {
      viewDetailContactService
        .searchContactInSearch(this.dataSearchContact)
        .then((response) => {
          this.dataContact = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    getContactInForm() {
      ContactService.searchContact()
        .then((response) => {
          this.dataContactInForm = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    getContactEdit() {
      ContactService.searchContact()
        .then((response) => {
          this.dataContactInForm = response.data.data;
          this.changeContact();
        })
        .catch((e) => {
          console.log(e);
        });
    },
    disableDateStartAdd(current) {
      return (
        current < moment().subtract(1, "days") ||
        current > moment(this.dateEnd).add(1, "days")
      );
    },
    disableDateEndAdd(current) {
      return (
        current < moment().subtract(1, "days") ||
        current > moment(this.dateEnd).add(1, "days")
      );
    },
    disableDateStart(current) {
      return (
        current < moment().subtract(1, "days") || current > moment(this.dateEnd)
      );
    },
    disableDateEnd(current) {
      return (
        current < moment().subtract(1, "days") || current > moment(this.dateEnd)
      );
    },
    changeContact() {
      this.dataProductIncontact = [];
      this.getProductInContact(this.dataSubmit.idContact);

      this.disableProduct = false;
      this.disabledDate = false;
      for (let i = 0; i < this.dataContactInForm.length; i++) {
        if (this.dataContactInForm[i].idContact == this.dataSubmit.idContact) {
          this.dateStart = this.dataContactInForm[i].dateCreate;
          this.dateEnd = this.dataContactInForm[i].dateFinish;
          break;
        }
      }
      this.disableDateStart();
      this.disableDateEnd();
      if (
        this.dataSubmit.idContact != null &&
        this.dataSubmit.idContact != ""
      ) {
        this.checkNameContact.show = false;
        this.checkNameContact.message = "";
      } else {
        this.checkNameContact.show = true;
        this.checkNameContact.message = "Bạn phải điền tên hợp đồng";
      }
    },
    getProductInContact(id) {
      ProductService.getProductInContact(id)
        .then((response) => {
          this.dataProductIncontact = response.data.data;
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
#view {
  background-color: rgb(76, 238, 12);
  color: white;
}
#view:hover {
  background-color: rgb(42, 253, 0);
  color: white;
}
</style>