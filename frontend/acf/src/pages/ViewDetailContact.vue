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
            placeholder="Khách hàng"
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
                    <a-button id="edit" @click="showModalEdit(record)">
                      <font-awesome-icon :icon="['fas', 'edit']" />
                    </a-button>
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
    };
  },
  computed: {},
  created() {
    this.searchContact();
    this.search();
  },
  methods: {
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