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
                placeholder="Tên màu"
                style="width: 150px"
                v-model="dataSearch.name"
              />
              <a-input
                placeholder="Mã màu"
                style="width: 150px"
                v-model="dataSearch.code"
              />
              <a-input
                placeholder="Công ty"
                style="width: 150px"
                v-model="dataSearch.nameCompany"
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
              <template slot="image" slot-scope="text, record">
                {{ record.image }}
              </template>
              <template slot="company" slot-scope="text, record">
                {{ record.nameCompany }}
              </template>
              <template slot="code" slot-scope="text, record">
                {{ record.code }}
              </template>
              <template slot="name" slot-scope="text, record">
                {{ record.name }}
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
          <a-modal v-model="visibleAdd" title="Thêm màu">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary" @click="submitAdd">
                Lưu
              </a-button>
            </template>
            <div class="container">
              <a-form-model>
                <a-form-model-item label="Tên công ty">
                  <a-select
                    placeholder="Công ty"
                    :filter-option="false"
                    @search="fetchCompany"
                    style="width: 120px"
                    v-model="dataAdd.idCompany"
                    show-search
                  >
                    <a-select-option
                      v-for="(company, index) in dataCompanys"
                      :value="company.id"
                      :key="index"
                    >
                      {{ company.name }}
                    </a-select-option>
                  </a-select>
                </a-form-model-item>
                <!-- <a-form-model-item label="Tên công ty">
                  <a-input v-model="dataAdd.fullName" />
                </a-form-model-item> -->

                <a-form-model-item label="Tên màu">
                  <a-input v-model="dataAdd.name" />
                </a-form-model-item>
                <a-form-model-item label="Mã màu">
                  <a-input v-model="dataAdd.code" />
                </a-form-model-item>
                <a-form-model-item label="Ảnh mẫu">
                  <a-input v-model="dataAdd.image" />
                </a-form-model-item>
              </a-form-model>
            </div>
          </a-modal>
          <!-- popup add -->

          <a-modal v-model="visibleEdit" title="Chỉnh sửa màu">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary" @click="submitUpdate">
                Lưu
              </a-button>
            </template>
            <div class="container">
              <a-form-model>
                <a-form-model-item label="Tên công ty">
                  <a-select
                    placeholder="Công ty"
                    :filter-option="false"
                    @search="fetchCompany"
                    style="width: 120px"
                    v-model="dataEdit.idCompany"
                    show-search
                  >
                    <a-select-option
                      v-for="(company, index) in dataCompanys"
                      :value="company.id"
                      :key="index"
                    >
                      {{ company.name }}
                    </a-select-option>
                  </a-select>
                </a-form-model-item>
                <!-- <a-form-model-item label="Tên công ty">
                  <a-input v-model="dataEdit.company" />
                </a-form-model-item> -->
                <a-form-model-item label="Tên màu">
                  <a-input v-model="dataEdit.name" />
                </a-form-model-item>
                <a-form-model-item label="Mã màu">
                  <a-input v-model="dataEdit.code" />
                </a-form-model-item>
                <a-form-model-item label="Ảnh mẫu">
                  <a-input v-model="dataEdit.image" />
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
import colorService from "../service/colorService";

export default {
  name: "Color",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      dataCompanys: [],
      dataCompany: {
        address: "",
        name: "",
        pageIndex: 1,
        pageSize: 10,
        phone: "",
      },
      dataAdd: {
        code: "",
        idCompany: "",
        image: "",
        name: "",
      },
      dataSearch: {
        code: "",
        name: "",
        nameCompany: "",
        pageIndex: 1,
        pageSize: 10,
        total: 0,
      },
      dataSourceTable: [],
      pagination: {
        current: 1,
        pageSize: 10,
        total: 0,
      },
      dataEdit: {
        code: "",
        id: 0,
        idCompany: 0,
        name: "",
        image: "",
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
          title: "Ảnh",
          dataIndex: "image",
          key: "image",
          width: 150,
          scopedSlots: { customRender: "image" },
        },
        {
          title: "Công ty",
          dataIndex: "company",
          key: "company",
          width: 150,
          scopedSlots: { customRender: "company" },
        },
        {
          title: "Mã màu",
          dataIndex: "code",
          key: "code",
          width: 150,
          scopedSlots: { customRender: "code" },
        },
        {
          title: "Tên màu",
          dataIndex: "name",
          key: "name",
          width: 150,
          scopedSlots: { customRender: "name" },
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
      visibleProfile: false,
    };
  },
  created() {
    this.submitSearch();
  },
  methods: {
    handleCancel() {
      this.visibleAdd = false;
      this.visibleEdit = false;
      this.visibleProfile = false;
    },
    getAllCompany() {
      colorService
        .getAllCompany(this.dataCompany)
        .then((response) => {
          this.dataCompanys = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    // getAllPositionEdit(id, name) {
    //   userService
    //     .getAllPosition(this.dataPosition)
    //     .then((response) => {
    //       this.dataPositions = response.data.data;
    //       let check = true;
    //       for (let i = 0; i < this.dataPositions.length; i++) {
    //         if (this.dataPositions[i].id == id) {
    //           check = false;
    //           break;
    //         }
    //       }
    //       if (check) {
    //         let dataPosition = {
    //           id: id,
    //           name: name,
    //         };
    //         this.dataPositions.push(dataPosition);
    //       }
    //     })
    //     .catch((e) => {
    //       console.log(e);
    //     });
    // },
    fetchCompany(value) {
      this.dataCompany.name = value;
      this.getAllCompany();
    },
    handleTableChange(pagination) {
      this.dataSearch.pageIndex = pagination.current;
      this.pagination = pagination;
      colorService
        .getAllColor(this.dataSearch)
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
      colorService
        .getAllColor(this.dataSearch)
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
      colorService
        .addColor(this.dataAdd)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Thêm mới";
            let description =
              "Thêm mới màu " + this.dataAdd.name + " thành công !!";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Thêm mới";
            let description =
              "Thêm mới màu " +
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
      this.dataAdd.code = "";
      this.dataAdd.idCompany = "";
      this.dataAdd.image = "";
      this.dataAdd.name = "";
    },
    showModalEdit(record) {
      this.dataEdit.id = record.id;
      this.dataEdit.name = record.name;
      this.dataEdit.idCompany = record.idCompany;
      this.getAllCompany();
      this.dataEdit.code = record.code;
      this.dataEdit.image = record.image;
      this.visibleEdit = true;
    },
    submitUpdate() {
      colorService
        .updateColor(this.dataEdit)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Cập nhật";
            let description =
              "Sửa thông tin màu " + this.dataEdit.name + " thành công !!";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Cập nhật";
            let description =
              "Sửa thông tin màu " +
              this.dataEdit.fullName +
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
    // showDetail(id) {
    //   this.visibleProfile = true;
    //   this.getUserByID(id);
    // },
    // getUserByID(id) {
    //   userService
    //     .getUserByID(id)
    //     .then((response) => {
    //       this.dataUserDetail = response.data.data;
    //     })
    //     .catch((e) => {
    //       console.log(e);
    //     });
    // },
    deleteUser(id) {
      colorService
        .deleteColor(id)
        .then((response) => {
          if (response.data.data) {
            let type = "success";
            let message = "Xóa";
            let description =
              "Xóa thông tin màu " + this.dataEdit.name + " thành công !!";
            this.notifi(type, message, description);
            this.submitSearch();
          } else {
            let type = "error";
            let message = "Xóa";
            let description =
              "Xóa thông tin màu " +
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