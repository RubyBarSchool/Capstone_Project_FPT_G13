<template>
  <div class="User">
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
                :icon="['fas', 'arrow-alt-circle-up']"
                :style="{ width: '100px', height: '50px' }"
              />
            </div>
          </a-back-top>
          <!-- menu trên -->
          <a-row type="flex">
            <a-col flex="auto">
              <a-input
                placeholder="Tên công ty"
                style="width: 150px"
                v-model="dataSearch.name"
              />
              <a-input
                placeholder="Địa chỉ"
                style="width: 150px"
                v-model="dataSearch.address"
              />
              <a-input
                placeholder="Số điện thoại"
                style="width: 150px"
                v-model="dataSearch.phone"
              />
              <a-button type="primary" @click="submitSearch">
                <font-awesome-icon
                  :icon="['fas', 'search']"
                  :style="{ 'margin-right': '5px' }"
                />
                Tìm kiếm
              </a-button>
            </a-col>
            <a-col flex="100px">
              <a-button type="primary" @click="showModalAdd">
                <font-awesome-icon
                  :icon="['fas', 'user-plus']"
                  :style="{ 'margin-right': '5px' }"
                />
                Thêm
              </a-button>
            </a-col>
          </a-row>

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
              <template slot="company" slot-scope="text, record">
                {{ record.name }}
              </template>
              <template slot="address" slot-scope="text, record">
                {{ record.address }}
              </template>
              <template slot="phone" slot-scope="text, record">
                {{ record.phone }}
              </template>
              <template slot="email" slot-scope="text, record">
                {{ record.email }}
              </template>
              <template slot="action" slot-scope="text, record">
                <a-row>
                  <a-col :span="9">
                    <a-button
                      id="edit"
                      @click="showModalEdit(record)"
                      :style="{ width: '44.25px' }"
                    >
                      <font-awesome-icon :icon="['fas', 'edit']" />
                    </a-button>
                  </a-col>
                  <a-col :span="9">
                    <a-popconfirm
                      v-if="dataSourceTable.length"
                      title="Bạn có chắc chắn muốn xóa không?"
                      @confirm="deleteUser(record.id)"
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

          <!-- popup add-->
          <a-modal v-model="visibleAdd" title="Thêm công ty">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary" @click="submitAdd">
                Lưu
              </a-button>
            </template>
            <div class="container">
              <a-form-model>
                <a-form-model-item label="Tên công ty">
                  <a-input v-model="dataAdd.name" />
                </a-form-model-item>
                <a-form-model-item label="Địa chỉ">
                  <a-input v-model="dataAdd.address" />
                </a-form-model-item>
                <a-form-model-item label="Số Điện Thoại">
                  <a-input v-model="dataAdd.phone" />
                </a-form-model-item>
                <a-form-model-item label="Email">
                  <a-input v-model="dataAdd.email" />
                </a-form-model-item>
              </a-form-model>
            </div>
          </a-modal>
          <!-- popup add -->

          <a-modal v-model="visibleEdit" title="Chỉnh sửa công ty">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary" @click="submitUpdate">
                Lưu
              </a-button>
            </template>
            <div class="container">
              <a-form-model>
                <a-form-model-item label="Tên công ty">
                  <a-input v-model="dataEdit.name" />
                </a-form-model-item>
                <a-form-model-item label="Địa chỉ">
                  <a-input v-model="dataEdit.address" />
                </a-form-model-item>
                <a-form-model-item label="Số điện thoại">
                  <a-input v-model="dataEdit.phone" />
                </a-form-model-item>
                <a-form-model-item label="Email">
                  <a-input v-model="dataEdit.email" />
                </a-form-model-item>
              </a-form-model>
            </div>
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
import companyService from "../service/companyService";

export default {
  name: "Company",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      dataPositions: [],
      dataPosition: {
        name: "",
        pageIndex: 1,
        pageSize: 10,
      },
      dataAdd: {
        address: "",
        email: "",
        name: "",
        phone: "",
      },
      dataSearch: {
        address: "",
        name: "",
        pageIndex: 1,
        pageSize: 10,
        phone: "",
        total: 0,
      },
      dataSourceTable: [],
      pagination: {
        current: 1,
        pageSize: 10,
        total: 0,
      },
      dataEdit: {
        address: "",
        email: "",
        id: 0,
        name: "",
        phone: "",
      },
      dataUserDetail: {
        id: "",
        address: "",
        dob: "",
        email: "",
        fullName: "",
        gender: "",
        idPosition: "",
        image: "",
        nation: "",
        phone: "",
        salary: "",
      },
      columns: [
        {
          title: "ID",
          dataIndex: "id",
          key: "id",
          width: 150,
          scopedSlots: { customRender: "id" },
        },
        {
          title: "Công ty",
          dataIndex: "company",
          key: "company",
          width: 150,
          scopedSlots: { customRender: "company" },
        },
        {
          title: "Địa chỉ",
          dataIndex: "address",
          key: "address",
          width: 150,
          scopedSlots: { customRender: "address" },
        },
        {
          title: "Số điện thoại",
          dataIndex: "phone",
          key: "phone",
          width: 150,
          scopedSlots: { customRender: "phone" },
        },
        {
          title: "Email",
          dataIndex: "email",
          key: "email",
          width: 150,
          scopedSlots: { customRender: "email" },
        },
        {
          title: "",
          dataIndex: "action",
          key: "action",
          fixed: "right",
          width: 150,
          scopedSlots: { customRender: "action" },
        },
      ],
      visibleAdd: false,
      visibleEdit: false,
    };
  },
  created() {
    this.submitSearch();
  },
  methods: {
    handleCancel() {
      this.visibleAdd = false;
      this.visibleEdit = false;
    },
    handleTableChange(pagination) {
      this.dataSearch.pageIndex = pagination.current;
      this.pagination = pagination;
      companyService
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
      companyService
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
    showModalAdd() {
      this.visibleAdd = true;
    },
    submitAdd() {
      companyService
        .addCompany(this.dataAdd)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Thêm mới";
            let description =
              "Thêm mới công ty " + this.dataAdd.name + " thành công !!";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Thêm mới";
            let description =
              "Thêm mới công ty " +
              this.dataAdd.name +
              " không thành công vì " +
              response.data.message;
            this.notifi(type, message, description);
          }
        })
        .catch((e) => {
          console.log(e);
        });
      this.visibleAdd = false;
      this.dataAdd.name = "";
      this.dataAdd.phone = "";
      this.dataAdd.email = "";
      this.dataAdd.address = "";
    },
    showModalEdit(record) {
      this.dataEdit.id = record.id;
      this.dataEdit.name = record.name;
      this.dataEdit.email = record.email;
      this.dataEdit.phone = record.phone;
      this.dataEdit.address = record.address;
      this.visibleEdit = true;
    },
    submitUpdate() {
      companyService
        .updateCompany(this.dataEdit)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Cập nhật";
            let description =
              "Sửa thông tin công ty " +
              this.dataEdit.name +
              " thành công !!";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Cập nhật";
            let description =
              "Sửa thông tin công ty " +
              this.dataEdit.name +
              " không thành công vì " +
              response.data.message;
            this.notifi(type, message, description);
          }
        })
        .catch((e) => {
          console.log(e);
        });
      this.visibleEdit = false;
    },
    deleteUser(id) {
      companyService
        .deleteCompany(id)
        .then((response) => {
          if (response.data.data) {
            let type = "success";
            let message = "Xóa";
            let description =
              "Xóa thông tin công ty " +
              this.dataEdit.name +
              " thành công !!";
            this.notifi(type, message, description);
            this.submitSearch();
          }else {
            let type = "error";
            let message = "Xóa";
            let description =
              "Xóa thông tin công ty " +
              this.dataEdit.name +
              " không thành công vì " +
              response.data.message;
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

<style>
/* back top */
.ant-back-top-inner {
  color: rgb(241, 237, 237);
  text-align: center;
}

/* button icon */
#delete {
  background-color: red;
  color: white;
}
#edit {
  background-color: rgb(17, 196, 228);
  color: white;
}
#user {
  background-color: rgb(24, 216, 24);
  color: white;
}

/* profile */
.bg-c-lite-green {
  border-radius: 5px;
  background: linear-gradient(to right, #000000, #000000);
}
.card-block {
  padding: 1.25rem;
}
.m-b-25 {
  margin-bottom: 30px;
}
.img-radius {
  border-radius: 5px;
}
h6 {
  font-size: 13.5px;
}
.card-block p {
  line-height: 25px;
}
.card-block {
  padding: 1.25rem;
}
.b-b-default {
  border-bottom: 1px solid #e0e0e0;
}
.m-b-20 {
  margin-bottom: 20px;
}
.p-b-5 {
  padding-bottom: 5px !important;
}
.m-b-10 {
  margin-bottom: 10px;
  color: black;
}
.text-muted {
  color: #919aa3 !important;
}
.text-white {
  color: white;
}
.f-w-600 {
  font-weight: 600;
}
.m-t-40 {
  margin-top: 20px;
}
</style>