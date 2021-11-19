<template>
  <div class="taohopdong">
    <a-layout :style="{ background: 'white' }">
      <Header />
      <a-layout-content :style="{ margin: '24px 16px 0' }">
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
          <!-- menu trên -->

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
              <template slot="name" slot-scope="text, record">
                {{ record.name }}
              </template>
              <template slot="createDate" slot-scope="text, record">
                {{ record.createDate }}
              </template>
              <template slot="dateFinish" slot-scope="text, record">
                {{ record.dateFinish }}
              </template>
              <template slot="name" slot-scope="text, record">
                {{ record.name }}
              </template>
              <template slot="totalMoney" slot-scope="text, record">
                {{ record.totalMoney }}
              </template>
              <template slot="numberFinish" slot-scope="text, record">
                {{ record.numberFinish }}
              </template>
              <template slot="statusDone" slot-scope="text, record">
                <a-tag :color="record.statusDone ? 'green' : 'blue'">
                  {{ record.statusDone ? "Đã bàn giao" : "Chưa bàn giao" }}
                </a-tag>
              </template>
              <template slot="note" slot-scope="text, record">
                {{ record.note }}
              </template>
              <template slot="action">
                <a-button id="delete">
                  <font-awesome-icon :icon="['fas', 'trash']" />
                </a-button>
              </template>
            </a-table>
          </div>
          <!-- table content -->

          <!-- popup add-->
          <a-modal v-model="visibleAdd" title="Thêm hợp đồng">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary" @click="handleSubmit">
                Lưu
              </a-button>
            </template>
            <a-form-model>
              <a-form-model-item label="Tên hợp đồng">
                <a-input v-model="dataAdd.name" />
              </a-form-model-item>
              <a-form-model-item label="Khách hàng">
                <a-select v-model="dataAdd.idCompany" style="width: 100%">
                  <a-select-option
                    v-for="(company, index) in companys"
                    :value="company.id"
                    :key="index"
                  >
                    {{ company.name }}
                  </a-select-option>
                </a-select>
              </a-form-model-item>
              <a-form-model-item label="Hạn hoàn">
                <a-date-picker v-model="dataAdd.time" />
              </a-form-model-item>
              <a-form-model-item label="Tổng giá trị">
                <a-input v-model="fileExcel.priceContact" disabled />
              </a-form-model-item>
              <a-form-model-item label="Ghi chú">
                <a-textarea
                  :auto-size="{
                    minRows: 1,
                    maxRows: 6,
                  }"
                  v-model="fileExcel.noteContact"
                  disabled
                />
              </a-form-model-item>
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
              <a-row :gutter="[16, 16]">
                <a-col :span="8"> Bảng chi tiết: </a-col>
                <a-col :span="8">
                  <input
                    type="file"
                    accept=".xls, .xlsx"
                    @change="importFile($event)"
                  />
                </a-col>
              </a-row>
            </a-form-model>
          </a-modal>
          <!-- popup add-->
        </div>
      </a-layout-content>
      <Footer />
    </a-layout>
  </div>
</template>

<script>
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";
import fileService from "@/service/fileService.js";
import company from "@/service/companyService.js";
import contact from "@/service/contactService.js";
import contactService from "@/service/contactService";

// const columns = [
//   {
//     title: "Ngày tạo hợp đồng",
//     dataIndex: "dateCreate",
//     key: "dateCreate",
//     fixed: "left",
//   },
//   { title: "Ngày bàn giao", dataIndex: "dateDelivery", key: "dateDelivery" },
//   { title: "Khách hàng", dataIndex: "customer", key: "customer" },
//   { title: "Tổng giá trị", dataIndex: "totalValue", key: "totalValue" },
//   { title: "Định mức hoàn thành", dataIndex: "quota", key: "quota" },
//   { title: "Trạng thái bàn giao", dataIndex: "status", key: "status" },
//   { title: "Ghi chú", dataIndex: "note", key: "note" },
//   {
//     title: "",
//     key: "operation",
//     fixed: "right",
//     width: 100,
//     scopedSlots: { customRender: "action" },
//   },
// ];

// const data = [
//   {
//     key: "1",
//     dateCreate: "14/11/2021",
//     dateDelivery: "14/11/2021",
//     customer: "New York Park",
//     totalValue: "123456",
//     quota: "10",
//     status: "Nháp",
//     note: "Công khai",
//   },
//   {
//     key: "2",
//     dateCreate: "14/11/2021",
//     dateDelivery: "14/11/2021",
//     customer: "New York Park",
//     totalValue: "123456",
//     quota: "10",
//     status: "Nháp",
//     note: "Công khai",
//   },
// ];
export default {
  name: "TaoHopDong",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
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
      message: "",
      progress: 0,
      visibleAdd: false,
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
        },
        {
          title: "Ngày bàn giao",
          dataIndex: "dateFinish",
          key: "dateFinish",
          width: 150,
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
          title: "Trạng thái bàn giao",
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
    };
  },
  created() {
    this.getAllCompany();
    this.submitSearch();
  },
  methods: {
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
          })
          .catch((e) => {
            this.progress = 0;
            this.message = "Không thể upload file";
            console.log(e);
          });
      }
    },
    handleSubmit() {
      this.dataAdd.fileExcel = this.fileExcel;
      contact
        .submitContact(this.dataAdd)
        .then(() => {
          this.visibleAdd = false;
          this.submitSearch();
        })
        .catch((e) => {
          console.log(e);
        });
    },
    onChange(date, dateString) {
      console.log(date, dateString);
    },
    showModalAdd() {
      this.visibleAdd = true;
      this.progress = 0;
      (this.dataAdd.name = ""),
        (this.dataAdd.idCompany = ""),
        (this.dataAdd.time = ""),
        (this.fileExcel = []);
      // this.$refs.file.files = undefined
    },
    handleCancel() {
      this.visibleAdd = false;
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
/* button icon */
#delete {
  background-color: rgb(255, 0, 0);
  color: white;
}
#delete:hover {
  background-color: rgba(233, 15, 15, 0.863);
  color: white;
}
</style>