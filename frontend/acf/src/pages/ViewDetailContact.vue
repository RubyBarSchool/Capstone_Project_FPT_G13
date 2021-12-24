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
          <!-- menu trên -->
          <a-select
            v-model="dataSearch.idContact"
            @change="changeSelect"
            placeholder="Hợp đồng"
            mode="multiple"
            :style="{ 'margin-right': '5px', width: '20%' }"
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
            v-model="dataSearch.nameProduct"
            placeholder="Tên sản phẩm"
            style="width: 15%"
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
            Tìm Kiếm
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
                      ? "Chưa tạo lệnh sản xuất"
                      : record.status == "-1"
                      ? "Đã tạo lệnh sản xuất"
                      : record.status == "0"
                      ? "Đang làm"
                      : "Hoàn thành"
                  }}
                </a-tag>
              </template>
              <template slot="action" slot-scope="text, record">
                <a-row :gutter="[16,16]">
                  <a-col :span="8">
                    <a-button id="view" @click="showModalView(record)">
                      <font-awesome-icon :icon="['fas', 'eye']" />
                    </a-button>
                  </a-col>
                  <a-col :span="8">
                    <a-button
                      v-if="record.status == '-2' || record.status == '-1'"
                      id="edit"
                      @click="showModalEdit(record)"
                    >
                      <font-awesome-icon :icon="['fas', 'edit']" />
                    </a-button>
                  </a-col>
                  <a-col :span="8">
                    <a-popconfirm
                      title="Bạn có chắc chắn muốn xóa không?"
                      @confirm="deleteProduct(record.idProduct)"
                      ok-text="Đồng ý"
                      cancel-text="Hủy"
                    >
                      <a-button
                        v-if="record.status == '-2' || record.status == '-1'"
                        id="delete"
                      >
                        <font-awesome-icon :icon="['fas', 'trash']" />
                      </a-button>
                    </a-popconfirm>
                  </a-col>
                </a-row>
              </template>
            </a-table>
          </div>

          <!-- view detail -->
          <a-modal
            v-model="showViewMaterialDetail"
            height="100%"
            width="80%"
            title="Xem chi tiết vật liệu của sản phẩm"
          >
            <template slot="footer">
              <a-button key="back" @click="handleCancelViewMaterialDetail">
                Đóng
              </a-button>
            </template>
            <a-table
              :columns="columnsMaterialDetail"
              :data-source="dataMaterialOfProduct"
              :rowKey="
                (record, index) => {
                  return index;
                }
              "
            >
            </a-table>
          </a-modal>
          <!-- view detail -->
          <!-- form add -->
          <a-modal
            v-model="showAddProductDetail"
            title="Thêm sản phẩm cho hợp đồng"
          >
            <template slot="footer">
              <a-button key="back" @click="handleCancelAddProductDetail">
                Đóng
              </a-button>
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
                  <a-select
                    @change="changeNameCOntact"
                    v-model="addProductForm.idContact"
                    placeholder="Hợp đồng"
                    style="width: 100%"
                  >
                    <a-select-option
                      v-for="(contact, index) in dataContactInAdd"
                      :value="contact.id"
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
                  <span style="color: red">*</span>Tên sản phẩm:</a-col
                >
                <a-col flex="auto">
                  <a-input
                    @change="changeNameProduct"
                    style="width: 100%"
                    v-model="addProductForm.nameProduct"
                    placeholder="Nhập tên sản phẩm"
                  />
                  <div style="color: red" v-if="checkNameProduct.show">
                    {{ checkNameProduct.message }}
                  </div>
                </a-col>
              </a-row>
              <br />
              <a-row type="flex">
                <a-col flex="150px"
                  ><span style="color: red">*</span>Số lượng:
                </a-col>
                <a-col flex="auto">
                  <a-input-number
                    style="width: 100%"
                    v-model="addProductForm.countProduct"
                    @change="changeCountProduct"
                    :min="0"
                    placeholder="Nhập số lượng sản phẩm"
                  />
                  <div style="color: red" v-if="checkCountProduct.show">
                    {{ checkCountProduct.message }}
                  </div>
                </a-col>
              </a-row>
              <br />
              <a-row type="flex">
                <a-col flex="150px">
                  <span style="color: red">*</span>Thông số:
                </a-col>
                <a-col flex="auto">
                  <a-input-number
                    @change="changeFrameProduct"
                    style="width: 100%"
                    :min="0"
                    v-model="addProductForm.lengthFrame"
                    placeholder="Chiều rộng"
                  />
                  <br />
                  <br />
                  <a-input-number
                    @change="changeFrameProduct"
                    style="width: 100%"
                    :min="0"
                    v-model="addProductForm.widthFrame"
                    placeholder="Chiều dài"
                  />
                  <br />
                  <br />
                  <a-input-number
                    @change="changeFrameProduct"
                    style="width: 100%"
                    :min="0"
                    v-model="addProductForm.heightFrame"
                    placeholder="Chiều cao"
                  />
                  <div style="color: red" v-if="checkFrameProduct.show">
                    {{ checkFrameProduct.message }}
                  </div>
                </a-col>
              </a-row>
              <br />
              <a-row type="flex">
                <a-col flex="150px">Ghi chú: </a-col>
                <a-col flex="auto">
                  <a-textarea
                    v-model="addProductForm.noteProduct"
                    placeholder="Nhập ghi chú"
                    :auto-size="{ minRows: 4, maxRows: 10 }"
                /></a-col>
              </a-row>
              <br />
              <a-row type="flex">
                <a-col flex="150px">
                  <span style="color: red">*</span>Giá tiền:
                </a-col>
                <a-col flex="auto">
                  <a-input v-model="addProductForm.priceProduct" disabled />
                  <div style="color: red" v-if="checkMoneyProduct.show">
                    {{ checkMoneyProduct.message }}
                  </div></a-col
                >
              </a-row>
              <br />
              <a-row type="flex">
                <a-col flex="150px">
                  <span style="color: red">*</span>Bảng giá chi tiết:
                </a-col>
                <a-col flex="auto">
                  <a-button type="primary" @click="handleAddMaterialDetail">
                    Thêm vật liệu
                  </a-button>
                  <div style="color: red" v-if="checkMaterialInProduct.show">
                    {{ checkMaterialInProduct.message }}
                  </div></a-col
                >
              </a-row>
            </a-form-model>
          </a-modal>
          <!-- form add -->

          <!-- hiển thị bảng mã vật liệu theo sản phẩm -->
          <a-modal
            v-model="showAddMaterialDetail"
            height="100%"
            width="80%"
            title="Thêm vật liệu cho sản phẩm"
          >
            <template slot="footer">
              <a-button key="back" @click="handleCancelAddMaterialDetail">
                Đóng
              </a-button>
              <a-button
                key="submit"
                type="primary"
                :disabled="disableSave"
                @click="submitAddMaterialDetail"
              >
                Lưu
              </a-button>
            </template>
            <a-input
              placeholder="Mã vật liệu"
              style="width: 150px"
              v-model="dataSearchAddMaterialDetail.codeMaterial"
            />
            <a-input
              placeholder="Thông số"
              style="width: 150px"
              v-model="dataSearchAddMaterialDetail.frame"
            />
            <a-button
              type="primary"
              @click="searchAddMaterialDetail"
              :style="{ 'margin-left': '5px' }"
            >
              <font-awesome-icon
                :icon="['fas', 'search']"
                :style="{ 'margin-right': '5px' }"
              />
              Tìm kiếm
            </a-button>
            <a-table
              :columns="columnsMaterialDetail"
              :data-source="dataAddMaterialDetail"
              :rowKey="
                (record, index) => {
                  return record.idMaterial;
                }
              "
              :pagination="false"
              :scroll="{ x: 1000 }"
              :row-selection="{
                selectedRowKeys: selectedRowKeys,
                selectedRows: selectedRows,
                onChange: onSelectChange,
              }"
            >
              <template slot="count" slot-scope="text, record">
                <editable-cell
                  :text="text"
                  @change="onCellChangeCount(record.idMaterial, $event)"
                />
              </template>
              <template slot="note" slot-scope="text, record">
                <editable-cell
                  :text="text"
                  @change="onCellChangeNote(record.idMaterial, $event)"
                />
              </template>
            </a-table>
          </a-modal>

          <!-- form edit -->
          <a-modal
            v-model="showEditProductDetail"
            title="Sửa sản phẩm cho hợp đồng"
          >
            <template slot="footer">
              <a-button key="back" @click="handleCancelAddProductDetail">
                Đóng
              </a-button>
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
                  <a-select
                    @change="changeNameCOntact"
                    v-model="addProductForm.idContact"
                    disabled
                    placeholder="Hợp đồng"
                    style="width: 100%"
                  >
                    <a-select-option
                      v-for="(contact, index) in dataContactInAdd"
                      :value="contact.id"
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
                  <a-input
                    @change="changeNameProduct"
                    style="width: 100%"
                    v-model="addProductForm.nameProduct"
                    placeholder="Nhập tên sản phẩm"
                  />
                  <div style="color: red" v-if="checkNameProduct.show">
                    {{ checkNameProduct.message }}
                  </div></a-col
                >
              </a-row>
              <br />
              <a-row type="flex">
                <a-col flex="150px">
                  <span style="color: red">*</span>Số lượng:</a-col
                >
                <a-col flex="auto">
                  <a-input-number
                    @change="changeCountProduct"
                    style="width: 100%"
                    :min="0"
                    v-model="addProductForm.countProduct"
                    placeholder="Nhập số lượng sản phẩm"
                  />
                  <div style="color: red" v-if="checkCountProduct.show">
                    {{ checkCountProduct.message }}
                  </div></a-col
                >
              </a-row>
              <br />
              <a-row type="flex">
                <a-col flex="150px">
                  <span style="color: red">*</span>Thông số:</a-col
                >
                <a-col flex="auto">
                  <a-input-number
                    @change="changeFrameProduct"
                    style="width: 100%"
                    :min="0"
                    v-model="addProductForm.lengthFrame"
                    placeholder="Chiều rộng"
                  />
                  <br />
                  <br />
                  <a-input-number
                    @change="changeFrameProduct"
                    style="width: 100%"
                    :min="0"
                    v-model="addProductForm.widthFrame"
                    placeholder="Chiều dài"
                  />
                  <br />
                  <br />
                  <a-input-number
                    @change="changeFrameProduct"
                    style="width: 100%"
                    :min="0"
                    v-model="addProductForm.heightFrame"
                    placeholder="Chiều cao"
                  />
                  <div style="color: red" v-if="checkFrameProduct.show">
                    {{ checkFrameProduct.message }}
                  </div></a-col
                >
              </a-row>
              <br />

              <a-row type="flex">
                <a-col flex="150px">Ghi chú:</a-col>
                <a-col flex="auto">
                  <a-textarea
                    style="width: 100%"
                    v-model="addProductForm.noteProduct"
                    placeholder="Nhập ghi chú"
                    :auto-size="{ minRows: 4, maxRows: 10 }"
                /></a-col>
              </a-row>

              <br />
              <a-row type="flex">
                <a-col flex="150px">
                  <span style="color: red">*</span>Giá tiền:</a-col
                >
                <a-col flex="auto">
                  <a-input
                    style="width: 100%"
                    v-model="addProductForm.priceProduct"
                    disabled
                  />
                  <div style="color: red" v-if="checkMoneyProduct.show">
                    {{ checkMoneyProduct.message }}
                  </div></a-col
                >
              </a-row>
              <br />
              <a-row type="flex">
                <a-col flex="150px"
                  ><span style="color: red">*</span>Bảng giá chi tiết:
                </a-col>
                <a-col flex="auto">
                  <a-button type="primary" @click="handleEditMaterialDetail">
                    Thêm vật liệu
                  </a-button>
                  <div style="color: red" v-if="checkMaterialInProduct.show">
                    {{ checkMaterialInProduct.message }}
                  </div></a-col
                >
              </a-row>
            </a-form-model>
          </a-modal>
        </div>
      </a-layout-content>
    </a-layout>
  </div>
</template>
 <script>
import viewDetailContactService from "@/service/viewDetailContactService.js";
import EditableCell from "@/components/EditableCell.vue";
export default {
  name: "viewdetailcontact",
  components: {
    EditableCell,
  },
  data() {
    return {
      loadingAdd: false,
      dataSearch: {
        idContact: [],
        nameProduct: "",
        pageIndex: 1,
        pageSize: 10,
        total: 0,
      },
      pagination: {
        current: 1,
        pageSize: 10,
        total: 0,
      },
      dataSearchContact: {
        name: "",
        pageIndex: 1,
        pageSize: 10,
        total: 0,
      },
      dataContact: [],
      dataSourceTable: [],
      columns: [
        {
          title: "STT",
          width: 80,
          dataIndex: "idProduct",
          key: "idProduct",
          fixed: "left",
        },
        {
          title: "Tên hợp đồng",
          dataIndex: "nameContact",
          key: "nameContact",
          width: 150,
        },
        {
          title: "Tên sản phẩm",
          dataIndex: "nameProduct",
          key: "nameProduct",
          width: 150,
        },
        {
          title: "Thống số",
          dataIndex: "frame",
          key: "frame",
          width: 150,
        },
        {
          title: "Số lượng",
          dataIndex: "count",
          key: "count",
          width: 120,
        },
        {
          title: "Ghi chú",
          dataIndex: "note",
          key: "note",
          width: 150,
        },
        {
          title: "Giá tiền",
          dataIndex: "price",
          key: "price",
          width: 150,
        },
        {
          title: "Trạng thái thực hiện",
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
      columnsMaterialDetail: [
        {
          title: "STT",
          width: 100,
          dataIndex: "idMaterial",
          key: "idMaterial",
          fixed: "left",
        },
        {
          title: "Tên vật liệu",
          dataIndex: "nameMaterial",
          key: "nameMaterial",
          width: 150,
        },
        {
          title: "Thống số",
          dataIndex: "frame",
          key: "frame",
          width: 150,
        },
        {
          title: "Đơn vị đo",
          dataIndex: "unitMaterial",
          key: "unitMaterial",
          width: 150,
        },
        {
          title: "Nhóm vật liệu",
          dataIndex: "nameGroupMaterial",
          key: "nameGroupMaterial",
          width: 150,
        },
        {
          title: "Công ty",
          dataIndex: "nameCompany",
          key: "nameCompany",
          width: 150,
        },
        {
          title: "Giá vật liệu",
          dataIndex: "price",
          key: "price",
          width: 150,
        },
        {
          title: "Số lượng",
          dataIndex: "count",
          key: "count",
          width: 150,
          scopedSlots: { customRender: "count" },
        },
        {
          title: "Ghi chú",
          dataIndex: "note",
          key: "note",
          width: 150,
          scopedSlots: { customRender: "note" },
        },
      ],
      dataMaterialOfProduct: [],
      showViewMaterialDetail: false,
      showAddProductDetail: false,
      showEditProductDetail: false,
      addProductForm: {
        idProduct: "",
        idContact: "",
        nameProduct: "",
        countProduct: "",
        lengthFrame: "",
        widthFrame: "",
        heightFrame: "",
        noteProduct: "",
        priceProduct: "",
        materials: [],
      },
      dataContactInAdd: [],
      showAddMaterialDetail: false,
      dataAddMaterialDetail: [],
      dataSearchAddMaterialDetail: {
        codeMaterial: "",
        frame: "",
        listGroupID: [],
        listUnitId: [],
        listIdCompany: [],
        pageIndex: 1,
        pageSize: 10,
      },
      selectedRowKeys: [],
      selectedRows: [],
      dataNote: [],
      dataCount: [],
      disableSave: true,
      checkNameContact: {
        show: false,
        message: "",
      },
      checkNameProduct: {
        show: false,
        message: "",
      },
      checkCountProduct: {
        show: false,
        message: "",
      },
      checkFrameProduct: {
        show: false,
        message: "",
      },
      checkMoneyProduct: {
        show: false,
        message: "",
      },
      checkMaterialInProduct: {
        show: false,
        message: "",
      },
    };
  },
  watch: {
    urlState(newValue) {
      if (newValue.indexOf("/viewdetailcontact") != -1) {
        this.search();
      }
    },
  },
  computed: {
    urlState() {
      return this.$store.state.url;
    },
  },
  created() {
    this.searchContact();
    this.search();
    this.getContactInForm();
  },
  methods: {
    changeNameCOntact() {
      if (
        this.addProductForm.idContact != null &&
        this.addProductForm.idContact != ""
      ) {
        this.checkNameContact.show = false;
        this.checkNameContact.message = "";
      } else {
        this.checkNameContact.show = true;
        this.checkNameContact.message =
          "Bạn phải chọn hợp đồng muốn thêm sản phẩm";
      }
    },
    changeNameProduct() {
      if (
        this.addProductForm.nameProduct != null &&
        this.addProductForm.nameProduct.trim() != ""
      ) {
        this.checkNameProduct.show = false;
        this.checkNameProduct.message = "";
      } else {
        this.checkNameProduct.show = true;
        this.checkNameProduct.message = "Bạn phải điền tên sản phẩm";
      }
    },
    changeCountProduct() {
      if (
        this.addProductForm.countProduct != null &&
        this.addProductForm.countProduct != ""
      ) {
        this.checkCountProduct.show = false;
        this.checkCountProduct.message = "";
      } else {
        this.checkCountProduct.show = true;
        this.checkCountProduct.message = "Bạn phải điền số lượng sản phẩm";
      }
    },
    changeFrameProduct() {
      if (
        this.addProductForm.lengthFrame != null &&
        this.addProductForm.lengthFrame != "" &&
        this.addProductForm.widthFrame != null &&
        this.addProductForm.widthFrame != "" &&
        this.addProductForm.heightFrame != null &&
        this.addProductForm.heightFrame != ""
      ) {
        this.checkFrameProduct.show = false;
        this.checkFrameProduct.message = "";
      } else {
        this.checkFrameProduct.show = true;
        this.checkFrameProduct.message =
          "Bạn phải điền đầy đủ 3 thông số của sản phẩm";
      }
    },
    onCellChangeCount(key, value) {
      if (!parseInt(value)) {
        let task = "error";
        let message = "Nhập đúng giá trị số";
        let description =
          "Bạn phải nhập giá trị số lượng là một số cụ thể cho mã vật liệu có mã:" +
          key;
        this.notifi(task, message, description);
        value = 0;
      }
      for (let i = 0; i < this.addProductForm.materials.length; i++) {
        if (this.addProductForm.materials[i].id == key) {
          this.addProductForm.materials[i].count = parseInt(value);
          break;
        }
      }
      let check = true;
      for (let i = 0; i < this.dataCount.length; i++) {
        if (key == this.dataCount[i].id) {
          this.dataCount[i].count = parseInt(value);
          check = false;
          break;
        }
      }
      if (check) {
        var data = {
          id: key,
          count: parseInt(value),
        };
        this.dataCount.push(data);
      }
      if (this.addProductForm.materials.length != 0) {
        this.disableSave = false;
      }
    },
    onCellChangeNote(key, value) {
      for (let i = 0; i < this.addProductForm.materials.length; i++) {
        if (this.addProductForm.materials[i].id == key) {
          this.addProductForm.materials[i].note = value;
          break;
        }
      }
      let check = true;
      for (let i = 0; i < this.dataNote.length; i++) {
        if (key == this.dataNote[i].id) {
          this.dataNote[i].note = value;
          check = false;
          break;
        }
      }
      if (check) {
        var data = {
          id: key,
          note: value,
        };
        this.dataNote.push(data);
      }
    },

    onSelectChange(selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys;
      this.selectedRows = selectedRows;
      this.addProductForm.materials = [];
      for (let i = 0; i < selectedRows.length; i++) {
        let data = {
          id: selectedRows[i].idMaterial,
          price: selectedRows[i].price,
          note: "",
          count: 0,
        };
        for (let j = 0; j < this.dataNote.length; j++) {
          if (this.dataNote[j].id == selectedRows[i].idMaterial) {
            data.note = this.dataNote[j].note;
            break;
          }
        }
        for (let j = 0; j < this.dataCount.length; j++) {
          if (this.dataCount[j].id == selectedRows[i].idMaterial) {
            data.count = this.dataCount[j].count;

            break;
          }
        }
        this.addProductForm.materials.push(data);
      }
      if (selectedRowKeys.length != 0) {
        this.disableSave = false;
      } else {
        this.disableSave = true;
      }
    },
    submitAddMaterialDetail() {
      let price = 0;
      let checkSuccess = true;
      for (let i = 0; i < this.addProductForm.materials.length; i++) {
        if (this.addProductForm.materials[i].count == 0) {
          this.disableSave = true;
          let task = "error";
          let text = "Thiếu số lượng";
          let description =
            "Bạn bắt buộc phải chọn số lượng cho vật liệu có mã: " +
            this.addProductForm.materials[i].id;
          this.notifi(task, text, description);
          checkSuccess = false;
          break;
        }
        price +=
          this.addProductForm.materials[i].count *
          parseInt(this.addProductForm.materials[i].price);
      }
      if (checkSuccess) {
        this.addProductForm.priceProduct = price;
        this.checkMoneyProduct.show = false;
        this.checkMoneyProduct.message = "";
        this.checkMaterialInProduct.show = false;
        this.checkMaterialInProduct.message = "";
        this.showAddMaterialDetail = false;
      }
    },
    checkBeforeAdd() {
      let check = true;
      if (
        this.addProductForm.idContact != null &&
        this.addProductForm.idContact != ""
      ) {
        this.checkNameContact.show = false;
        this.checkNameContact.message = "";
      } else {
        check = false;
        this.checkNameContact.show = true;
        this.checkNameContact.message =
          "Bạn phải chọn hợp đồng muốn thêm sản phẩm";
      }

      if (
        this.addProductForm.nameProduct != null &&
        this.addProductForm.nameProduct.trim() != ""
      ) {
        this.checkNameProduct.show = false;
        this.checkNameProduct.message = "";
      } else {
        check = false;
        this.checkNameProduct.show = true;
        this.checkNameProduct.message = "Bạn phải điền tên sản phẩm";
      }

      if (
        this.addProductForm.countProduct != null &&
        this.addProductForm.countProduct != ""
      ) {
        this.checkCountProduct.show = false;
        this.checkCountProduct.message = "";
      } else {
        check = false;
        this.checkCountProduct.show = true;
        this.checkCountProduct.message = "Bạn phải điền số lượng sản phẩm";
      }

      if (
        this.addProductForm.lengthFrame != null &&
        this.addProductForm.lengthFrame != "" &&
        this.addProductForm.widthFrame != null &&
        this.addProductForm.widthFrame != "" &&
        this.addProductForm.heightFrame != null &&
        this.addProductForm.heightFrame != ""
      ) {
        this.checkFrameProduct.show = false;
        this.checkFrameProduct.message = "";
      } else {
        check = false;
        this.checkFrameProduct.show = true;
        this.checkFrameProduct.message =
          "Bạn phải điền 3 thông số của sản phẩm";
      }

      if (
        this.addProductForm.priceProduct != null &&
        this.addProductForm.priceProduct != ""
      ) {
        this.checkMoneyProduct.show = false;
        this.checkMoneyProduct.message = "";
      } else {
        check = false;
        this.checkMoneyProduct.show = true;
        this.checkMoneyProduct.message =
          "Bạn phải chọn vật liệu để lấy giá tiền tương ứng";
      }

      if (
        this.addProductForm.materials != null &&
        this.addProductForm.materials.length != 0
      ) {
        this.checkMaterialInProduct.show = false;
        this.checkMaterialInProduct.message = "";
      } else {
        check = false;
        this.checkMaterialInProduct.show = true;
        this.checkMaterialInProduct.message =
          "Bạn phải chọn vật liệu cấu thành sản phẩm";
      }

      if (check) {
        this.submitAddProductDetail();
      }
    },
    submitAddProductDetail() {
      this.loadingAdd = true;
      this.addProductForm.nameProduct = this.addProductForm.nameProduct.trim();
      viewDetailContactService
        .addProduct(this.addProductForm)
        .then((response) => {
          this.search();
          this.loadingAdd = false;
          this.handleCancelAddProductDetail();
          let task = response.data.data ? "success" : "error";
          let text = response.data.data
            ? "Thêm thành công"
            : "Thêm Không thành công";
          let description = response.data.data
            ? "Thêm thành công product " + this.addProductForm.nameProduct
            : "Thêm không thành công product " +
              this.addProductForm.nameProduct;
          this.notifi(task, text, description);
        })
        .catch((e) => {
          this.loadingAdd = false;
          console.log(e);
        });
    },
    handleCancelAddMaterialDetail() {
      this.showAddMaterialDetail = false;
    },
    handleAddMaterialDetail() {
      viewDetailContactService
        .getMaterialInAddProduct(this.dataSearchAddMaterialDetail)
        .then((response) => {
          this.dataAddMaterialDetail = response.data.data;
          this.showAddMaterialDetail = true;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    handleEditMaterialDetail() {
      this.showAddMaterialDetail = true;
    },
    searchAddMaterialDetail() {
      viewDetailContactService
        .getMaterialInAddProduct(this.dataSearchAddMaterialDetail)
        .then((response) => {
          this.dataAddMaterialDetail = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    deleteProduct(id) {
      viewDetailContactService
        .deleteProduct(id)
        .then((response) => {
          let task = response.data.data ? "success" : "error";
          let text = response.data.data
            ? "Xóa thành công"
            : "Xóa Không thành công";
          let description = response.data.data
            ? "Xóa thành công product "
            : "Xóa không thành công product ";
          this.notifi(task, text, description);
          this.search();
        })
        .catch((e) => {
          console.log(e);
        });
    },
    showModalAdd() {
      this.handleCancelAddProductDetail();
      this.showAddProductDetail = true;
    },
    getContactInForm() {
      viewDetailContactService
        .searchContactInAdd()
        .then((response) => {
          this.dataContactInAdd = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    handleCancelAddProductDetail() {
      this.showAddProductDetail = false;
      this.showEditProductDetail = false;
      this.addProductForm.idProduct = "";
      this.addProductForm.idContact = "";
      this.addProductForm.nameProduct = "";
      this.addProductForm.countProduct = "";
      this.addProductForm.lengthFrame = "";
      this.addProductForm.widthFrame = "";
      this.addProductForm.heightFrame = "";
      this.addProductForm.noteProduct = "";
      this.addProductForm.priceProduct = "";
      this.addProductForm.materials = [];
      this.selectedRowKeys = [];
      this.selectedRows = [];
      this.dataAddMaterialDetail = [];
      this.dataNote = [];
      this.dataCount = [];
      this.checkNameContact.show = false;
      this.checkNameContact.message = "";
      this.checkNameProduct.show = false;
      this.checkNameProduct.message = "";
      this.checkCountProduct.show = false;
      this.checkCountProduct.message = "";
      this.checkFrameProduct.show = false;
      this.checkFrameProduct.message = "";
      this.checkMoneyProduct.show = false;
      this.checkMoneyProduct.message = "";
      this.checkMaterialInProduct.show = false;
      this.checkMaterialInProduct.message = "";
    },
    handleCancelViewMaterialDetail() {
      this.showViewMaterialDetail = false;
      this.dataMaterialOfProduct = [];
    },
    showModalView(record) {
      viewDetailContactService
        .getMaterialOfProduct(record.idProduct)
        .then((response) => {
          this.dataMaterialOfProduct = response.data.data;
          this.showViewMaterialDetail = true;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    showModalEdit(record) {
      this.handleCancelAddProductDetail();
      this.addProductForm.idProduct = record.idProduct;
      this.addProductForm.idContact = record.idContact;
      this.addProductForm.nameProduct = record.nameProduct;
      this.addProductForm.countProduct = record.count;
      let frame = record.frame.split("x");
      this.addProductForm.lengthFrame = frame[0];
      this.addProductForm.widthFrame = frame[1];
      this.addProductForm.heightFrame = frame[2];
      this.addProductForm.noteProduct = record.note;
      this.addProductForm.priceProduct = record.price;
      this.showEditProductDetail = true;
      viewDetailContactService
        .getMaterialOfProduct(record.idProduct)
        .then((response) => {
          let dataMaterial = response.data.data;
          viewDetailContactService
            .getMaterialInAddProduct(this.dataSearchAddMaterialDetail)
            .then((response) => {
              this.dataAddMaterialDetail = response.data.data;
              for (let i = this.dataAddMaterialDetail.length - 1; i >= 0; i--) {
                for (let j = 0; j < dataMaterial.length; j++) {
                  if (
                    this.dataAddMaterialDetail[i].idMaterial ==
                    dataMaterial[j].idMaterial
                  ) {
                    this.dataAddMaterialDetail.splice(i, 1);
                  }
                }
              }
              this.selectedRowKeys = [];
              for (let j = 0; j < dataMaterial.length; j++) {
                let dataMaterialx = {
                  id: dataMaterial[j].idMaterial,
                  price: dataMaterial[j].price,
                  note: dataMaterial[j].note,
                  count: dataMaterial[j].count,
                };
                this.dataNote.push({
                  id: dataMaterial[j].idMaterial,
                  note: dataMaterial[j].note,
                });
                this.dataCount.push({
                  id: dataMaterial[j].idMaterial,
                  count: dataMaterial[j].count,
                });
                this.addProductForm.materials.push(dataMaterialx);
                this.selectedRowKeys.push(dataMaterial[j].idMaterial);
                dataMaterial[j].count = dataMaterial[j].count + "";
                this.dataAddMaterialDetail.unshift(dataMaterial[j]);
              }
            })
            .catch((e) => {
              console.log(e);
            });
        })
        .catch((e) => {
          console.log(e);
        });
    },
    showModalDelete(record) {
      console.log(record);
    },
    changeSelect(value) {
      this.dataSearchContact.name = value;
      this.searchContact();
    },
    searchContact() {
      viewDetailContactService
        .searchContactInSearch(this.dataSearchContact)
        .then((response) => {
          this.dataContact = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    searchContactDetail() {
      viewDetailContactService
        .searchDetailContact(this.dataSearch)
        .then((response) => {
          this.dataSourceTable = response.data.data;
          this.dataSearch.total = response.data.total;
          this.pagination.total = response.data.total;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    handleTableChange(pagination) {
      this.dataSearch.pageIndex = pagination.current;
      this.pagination = pagination;
      this.searchContactDetail();
    },
    search() {
      this.dataSearch.nameProduct = this.dataSearch.nameProduct.trim();
      this.dataSearch.pageIndex = 1;
      this.dataSearch.total = 0;
      this.searchContactDetail();
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