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
          Ngày bàn giao
          <a-range-picker
            @change="onChange"
            :placeholder="['Ngày bắt đầu', 'Ngày kết thúc']"
            :style="{ 'margin-right': '5px' }"
          />
          <a-input
            placeholder="Khách hàng"
            :style="{ width: '150px', 'margin-right': '5px' }"
          />
          <a-button type="primary" @click="showModalAdd">
            <font-awesome-icon
              :icon="['fas', 'file-signature']"
              :style="{ 'margin-right': '5px' }"
            />
            Thêm
          </a-button>
          <!-- menu trên -->

          <!-- table content -->
          <div :style="{ 'padding-top': '10px' }">
            <a-table :columns="columns" :data-source="data">
              <a slot="action">
                <a-button id="delete">
                  <font-awesome-icon :icon="['fas', 'trash']" />
                </a-button>
              </a>
            </a-table>
          </div>
          <!-- table content -->

          <!-- popup add-->
          <a-modal v-model="visibleAdd" title="Thêm hợp đồng">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary" @click="handleSubmit"> Lưu </a-button>
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
                <a-input v-model="fileExcel.priceContact" disabled/>
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
                    value=""
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
const columns = [
  {
    title: "Ngày tạo hợp đồng",
    dataIndex: "dateCreate",
    key: "dateCreate",
    fixed: "left",
  },
  { title: "Ngày bàn giao", dataIndex: "dateDelivery", key: "dateDelivery" },
  { title: "Khách hàng", dataIndex: "customer", key: "customer" },
  { title: "Tổng giá trị", dataIndex: "totalValue", key: "totalValue" },
  { title: "Định mức hoàn thành", dataIndex: "quota", key: "quota" },
  { title: "Trạng thái bàn giao", dataIndex: "status", key: "status" },
  { title: "Ghi chú", dataIndex: "note", key: "note" },
  {
    title: "Action",
    key: "operation",
    fixed: "right",
    width: 100,
    scopedSlots: { customRender: "action" },
  },
];

const data = [
  {
    key: "1",
    dateCreate: "14/11/2021",
    dateDelivery: "14/11/2021",
    customer: "New York Park",
    totalValue: "123456",
    quota: "10",
    status: "Nháp",
    note: "Công khai",
  },
  {
    key: "2",
    dateCreate: "14/11/2021",
    dateDelivery: "14/11/2021",
    customer: "New York Park",
    totalValue: "123456",
    quota: "10",
    status: "Nháp",
    note: "Công khai",
  },
];
export default {
  name: "TaoHopDong",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      data,
      fileList: [],
      columns,
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
      message: "",
      progress: 0,
      visibleAdd: false,
    };
  },
  created() {
    this.getAllCompany();
  },
  methods: {
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
            this.message = "Could not upload the file!";
            console.log(e);
          });
      }
    },
    handleSubmit(){
      this.dataAdd.fileExcel = this.fileExcel;
      contact.submitContact(this.dataAdd)
        .then(() => {
          this.visibleAdd = false;
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
      this.dataAdd.name = "",
      this.dataAdd.idCompany = "",
      this.dataAdd.time = "",
      this.fileExcel = []
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