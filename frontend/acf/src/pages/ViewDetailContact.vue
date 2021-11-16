<template>
  <div class="attendance">
    <a-layout :style="{ background: 'white' }">
      <Header />
      <a-layout-content :style="{ margin: '30px 16px 0' }">
        <div
          :style="{
            minHeight: '360px',
            background: 'white',
          }"
        >
          <!-- menu trên -->
          <a-select
            v-model="dataSearch.idContact"
            @change="changeSelect"
            placeholder="Hợp đồng"
            mode="multiple"
            style="width: 20%"
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
              :icon="['fas', 'plus-circle']"
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
              <template slot="action" slot-scope="text, record">
                <a-row>
                  <a-col :span="8">
                    <a-button id="view" @click="showModalView(record)">
                      <font-awesome-icon :icon="['fas', 'eye']" />
                    </a-button>
                  </a-col>
                  <a-col :span="8">
                    <a-button id="edit" @click="showModalEdit(record)">
                      <font-awesome-icon :icon="['fas', 'edit']" />
                    </a-button>
                  </a-col>
                  <a-col :span="8">
                    <a-button id="delete" @click="showModalDelete(record)">
                      <font-awesome-icon :icon="['fas', 'trash']" />
                    </a-button>
                  </a-col>
                </a-row>
              </template>
            </a-table>
          </div>

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

          <a-modal
            v-model="showAddProductDetail"
            title="Xem chi tiết vật liệu của sản phẩm"
          >
            <template slot="footer">
              <a-button key="back" @click="handleCancelAddProductDetail">
                Đóng
              </a-button>
              <a-button key="submit" type="primary" @click="submitAddProductDetail">
                Lưu
              </a-button>
            </template>
            <a-form-model>
              <a-form-model-item label="Tên hợp đồng">
                <a-select
                  v-model="addProductForm.idContact"
                  @change="changeSelect"
                  placeholder="Hợp đồng"
                  style="width: 80%"
                >
                  <a-select-option
                    v-for="(contact, index) in dataContactInAdd"
                    :value="contact.id"
                    :key="index"
                  >
                    {{ contact.name }}
                  </a-select-option>
                </a-select>
              </a-form-model-item>
              <a-form-model-item label="Tên sản phẩm">
                <a-input
                  v-model="addProductForm.nameProduct"
                  placeholder="Nhập tên sản phẩm"
                />
              </a-form-model-item>
              <a-form-model-item label="Số lượng">
                <a-input
                  v-model="addProductForm.countProduct"
                  placeholder="Nhập số lượng sản phẩm"
                />
              </a-form-model-item>
              <a-form-model-item label="Thông số">
                <a-input
                  v-model="addProductForm.lengthFrame"
                  placeholder="Chiều rộng"
                />
                <a-input
                  v-model="addProductForm.widthFrame"
                  placeholder="Chiều dài"
                />
                <a-input
                  v-model="addProductForm.heightFrame"
                  placeholder="Chiều cao"
                />
              </a-form-model-item>
              <a-form-model-item label="Ghi chú">
                <a-textarea
                  v-model="addProductForm.noteProduct"
                  placeholder="Nhập ghi chú"
                  :auto-size="{ minRows: 4, maxRows: 10 }"
                />
              </a-form-model-item>
              <a-form-model-item label="Giá tiền">
                <a-input v-model="addProductForm.priceProduct" disabled />
              </a-form-model-item>
              <a-form-model-item label="Bảng giá chi tiết">
                <a-button type="primary" @click="handleAddMaterialDetail">
                  Thêm vật liệu
                </a-button>
              </a-form-model-item>
            </a-form-model>
          </a-modal>

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
              <a-button key="submit" type="primary" @click="submitAddMaterialDetail">
                Lưu
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
        </div>
      </a-layout-content>
      <Footer />
    </a-layout>
  </div>
</template>
 <script>
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";
import viewDetailContactService from "@/service/viewDetailContactService.js";
export default {
  name: "viewdetailcontact",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
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
          width: 100,
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
          width: 150,
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
        },
        {
          title: "Hành động",
          dataIndex: "action",
          key: "action",
          fixed: "right",
          width: 150,
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
          title: "Giá vật liệu hợp đồng",
          dataIndex: "priceInContact",
          key: "priceInContact",
          width: 150,
        },
        {
          title: "Số lượng",
          dataIndex: "count",
          key: "count",
          width: 150,
        },
        {
          title: "Ghi chú",
          dataIndex: "note",
          key: "note",
          width: 150,
        },
      ],
      dataMaterialOfProduct: [],
      showViewMaterialDetail: false,
      showAddProductDetail: false,
      addProductForm: {
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
    };
  },
  computed: {},
  created() {
    this.searchContact();
    this.search();
  },
  methods: {
    handleCancelAddMaterialDetail() {
      this.showAddMaterialDetail = false;
    },
    handleAddMaterialDetail() {
      this.showAddMaterialDetail = true;
    },
    submitAddMaterialDetail(){

    },
    submitAddProductDetail() {},
    showModalAdd() {
      viewDetailContactService
        .searchContactInAdd()
        .then((response) => {
          this.dataContactInAdd = response.data.data;
          this.showAddProductDetail = true;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    handleCancelAddProductDetail() {
      this.showAddProductDetail = false;
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
      console.log(record);
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
      this.dataSearch.pageIndex = 1;
      this.dataSearch.total = 0;
      this.searchContactDetail();
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
</style>