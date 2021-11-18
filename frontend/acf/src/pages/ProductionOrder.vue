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
          <a-back-top>
            <div class="ant-back-top-inner">
              <font-awesome-icon
                :icon="['fas', 'angle-double-up']"
                :style="{ width: '160px', height: '50px', color: '#15AABF' }"
              />
            </div>
          </a-back-top>
          <a-select
            v-model="dataSearch.listIdContact"
            @change="changeSearch"
            placeholder="Hợp đồng"
            mode="multiple"
            style="width: 15%"
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
            style="width: 15%"
          />
          Ngày hoàn thành:
          <a-range-picker
            @change="changeSearch"
            v-model="dataSearch.dateList"
            :placeholder="['Từ ngày', 'Đến ngày']"
            format="DD/MM/YYYY"
          />
          <a-select
            v-model="dataSearch.status"
            @change="changeSearch"
            style="width: 10%"
          >
            <a-select-option key=""> Tất Cả </a-select-option>
            <a-select-option key="false"> Chưa hoàn thành </a-select-option>
            <a-select-option key="true"> Đã hoàn thành </a-select-option>
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
              :icon="['fas', 'plus-circle']"
              :style="{ 'margin-right': '5px' }"
            />
            Thêm
          </a-button>

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
              <template slot="status" slot-scope="text, record">
                <a-tag :color="record.status ? 'green' : 'red'">
                  {{ record.status ? "Đã hoàn thành" : "Chưa hoàn thành" }}
                </a-tag>
              </template>
              <template slot="action" slot-scope="text, record">
                <a-row>
                  <a-col :span="8">
                    <a-button
                      id="view"
                      @click="showModelView(record)"
                      :style="{ width: '44.25px', 'margin-right': '100px' }"
                    >
                      <font-awesome-icon :icon="['fas', 'eye']" />
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
                      @confirm="deletePersonalApplication(record)"
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
                  key="submit"
                  type="primary"
                  :disabled="disableSaveAdd"
                  @click="submitAddProductionOrder"
                >
                  Lưu
                </a-button>
              </template>
              <a-form-model>
                <a-form-model-item label="Lệnh sản xuất">
                  <a-input
                    v-model="dataSubmit.name"
                    placeholder="Nhập tên lệnh sản xuất"
                  />
                </a-form-model-item>

                <!-- <a-form-model-item label="Tên hợp đồng">
                  <a-select
                    v-model="addProductForm.idContact"
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
                </a-form-model-item> -->
                <a-form-model-item label="Xem công việc">
                  <a-button type="primary" @click="showWorkEmployee">
                    Chi tiết công việc
                  </a-button>
                </a-form-model-item>
              </a-form-model>
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
                  :disabled="disableSaveAdd"
                  @click="submitAddProductionOrder"
                >
                  Lưu
                </a-button>
              </template>
              <a-table
                :columns="columnsViewWork"
                :data-source="dataTableViewWork"
                :pagination="false"
                :scroll="{ x: 1500 ,y: 800 }"
                :rowKey="
                  (record, index) => {
                    return index;
                  }
                "
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
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";
import viewDetailContactService from "@/service/viewDetailContactService.js";
import ProductionOrderService from "@/service/ProductionOrderService.js";
// import EditableCell from "@/components/EditableCell.vue";
export default {
  name: "productionorder",
  components: {
    Header,
    Footer,
    // EditableCell,
  },
  data() {
    return {
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
        idEmployee: [],
        note: "",
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
        },
        {
          title: "Hợp đồng",
          dataIndex: "nameContact",
          key: "nameContact",
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
        },
        {
          title: "Ngày hoàn thành",
          dataIndex: "dateEnd",
          key: "dateEnd",
          width: 150,
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
          title: "Hành động",
          dataIndex: "action",
          key: "action",
          width: 150,
          fixed: "right",
          scopedSlots: { customRender: "action" },
        },
      ],
      columnsViewWork: [],
      dataTableViewWork: [],
      showModalAdd: false,
      showModalViewWork: false,
      disableSaveAdd: false,
    };
  },
  computed: {},
  created() {
    this.getContact();
    this.beforeSearch();
  },
  methods: {
    handleCancelViewWork() {
      this.showModalViewWork = false;
    },
    showWorkEmployee() {
      let fakeData = {
        dateStart: "2022-02-10",
        dateEnd: "2022-03-01",
      };
      ProductionOrderService.viewWorkEmployee(fakeData)
        .then((response) => {
          this.dataTableViewWork = response.data.data.data;
          this.columnsViewWork = response.data.data.columns;
          this.showModalViewWork = true;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    submitAddProductionOrder() {},
    openModalAdd() {
      this.showModalAdd = true;
    },
    handleCancelAdd() {
      this.showModalAdd = false;
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
    getContact() {
      viewDetailContactService
        .searchContactInAdd()
        .then((response) => {
          this.dataContact = response.data.data;
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
/* back top */
.ant-back-top-inner {
  color: rgb(241, 237, 237);
  text-align: center;
}
</style>