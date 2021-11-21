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
            v-model="dataSearch.status"
            @change="changeSearch"
            style="width: 10%"
          >
            <a-select-option key=""> Tất Cả </a-select-option>
            <a-select-option key="-1"> Đang chờ xác nhận </a-select-option>
            <a-select-option key="0"> Đang làm </a-select-option>
            <a-select-option key="1"> Chờ duyệt </a-select-option>
            <a-select-option key="2"> Hoàn thành </a-select-option>
          </a-select>
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
                <a-tag
                  :color="
                    record.status == '1'
                      ? 'green'
                      : record.status == '0'
                      ? 'orange'
                      : 'red'
                  "
                >
                  {{
                    record.status == "1"
                      ? "Đã hoàn thành"
                      : record.status == "0"
                      ? "Đang làm"
                      : "Đang chờ xác nhận"
                  }}
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
                      @click="showEditForm(record)"
                      :style="{ width: '44.25px' }"
                    >
                      <font-awesome-icon :icon="['fas', 'edit']" />
                    </a-button>
                  </a-col>
                  <a-col :span="8">
                    <a-popconfirm
                      v-if="record.status == '-1'"
                      title="Bạn có chắc chắn muốn xóa không?"
                      @confirm="deleteProductionOrder(record)"
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
import ContactService from "@/service/contactService.js";
import ProductionOrderService from "@/service/ProductionOrderService.js";
import ProductService from "@/service/productService.js";
import moment from "moment";
export default {
  name: "viewworkemployee",
  components: {
    Header,
    Footer
  },
  data() {
    return {
      pagination: {
        current: 1,
        pageSize: 10,
        total: 0,
      },
      dataSearch: {
        status: "",
        pageIndex: 1,
        pageSize: 10,
        total: 0
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
      dataSourceTable: [],
      columns: [],
    };
  },
  computed: {},
  created() {
  },
  methods: {
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
    disableDateStart(current) {
      return (
        current < moment().subtract(1, "days") ||
        current > moment(this.dateEnd).add(1, "days")
      );
    },
    disableDateEnd(current) {
      return (
        current < moment().subtract(1, "days") ||
        current > moment(this.dateEnd).add(1, "days")
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
      console.log("data date start", this.dateStart);
      console.log("data date end", this.dateEnd);
      this.disableDateStart();
      this.disableDateEnd();
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
/* back top */

.ant-back-top-inner {
  color: rgb(241, 237, 237);
  text-align: center;
}
</style>