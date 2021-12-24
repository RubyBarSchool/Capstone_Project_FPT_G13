<template>
  <div class="User">
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
          <a-input
            placeholder="Tên công ty"
            :style="{ width: '150px', 'margin-right': '5px' }"
            v-model="dataSearch.name"
          />
          <a-input
            placeholder="Địa chỉ"
            :style="{ width: '150px', 'margin-right': '5px' }"
            v-model="dataSearch.address"
          />
          <a-input
            placeholder="Số điện thoại"
            style="width: 150px"
            v-model="dataSearch.phone"
          />
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
              :icon="['fas', 'building']"
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
          </div>

          <!-- popup add-->
          <a-modal v-model="visibleAdd" title="Thêm công ty">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button
                key="submit"
                type="primary"
                :loading="loading"
                @click="checkFormAdd"
              >
                Lưu
              </a-button>
            </template>
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Tên công ty</a-col
              >
              <a-col flex="auto">
                <a-input @change="inputCompany" v-model="dataAdd.name" />
                <div style="color: red" v-if="checkDataInputCompany.show">
                  {{ checkDataInputCompany.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Địa chỉ</a-col
              >
              <a-col flex="auto">
                <a-input @change="inputAddress" v-model="dataAdd.address" />
                <div style="color: red" v-if="checkDataInputAddress.show">
                  {{ checkDataInputAddress.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Số điện thoại</a-col
              >
              <a-col flex="auto">
                <a-input-number
                  v-model="dataAdd.phone"
                  @change="inputPhone"
                  :min="0"
                  style="width: 100%"
                />
                <div style="color: red" v-if="checkDataInputPhone.show">
                  {{ checkDataInputPhone.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Email</a-col
              >
              <a-col flex="auto">
                <a-input @change="inputEmail" v-model="dataAdd.email" />
                <div style="color: red" v-if="checkDataInputEmail.show">
                  {{ checkDataInputEmail.message }}
                </div>
              </a-col>
            </a-row>
          </a-modal>
          <!-- popup add -->

          <!-- popup update -->
          <a-modal v-model="visibleEdit" title="Chỉnh sửa công ty">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button
                key="submit"
                type="primary"
                :loading="loadingUpdate"
                @click="checkFormUpdate"
              >
                Lưu
              </a-button>
            </template>
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Tên công ty</a-col
              >
              <a-col flex="auto">
                <a-input @change="inputEditCompany" v-model="dataEdit.name" />
                <div style="color: red" v-if="checkDataInputCompany.show">
                  {{ checkDataInputCompany.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Địa chỉ</a-col
              >
              <a-col flex="auto">
                <a-input
                  @change="inputEditAddress"
                  v-model="dataEdit.address"
                />
                <div style="color: red" v-if="checkDataInputAddress.show">
                  {{ checkDataInputAddress.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Số điện thoại</a-col
              >
              <a-col flex="auto">
                <a-input-number
                  v-model="dataEdit.phone"
                  @change="inputEditPhone"
                  :min="0"
                  style="width: 100%"
                />
                <div style="color: red" v-if="checkDataInputPhone.show">
                  {{ checkDataInputPhone.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Email</a-col
              >
              <a-col flex="auto">
                <a-input @change="inputEditEmail" v-model="dataEdit.email" />
                <div style="color: red" v-if="checkDataInputEmail.show">
                  {{ checkDataInputEmail.message }}
                </div>
              </a-col>
            </a-row>
          </a-modal>
          <!-- popup update -->
        </div>
      </a-layout-content>
    </a-layout>
  </div>
</template>

<script>
import companyService from "@/service/companyService";

export default {
  name: "Company",
  components: {},
  data() {
    return {
      loading: false,
      loadingUpdate: false,
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
          title: "STT",
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
          width: 180,
          scopedSlots: { customRender: "action" },
        },
      ],
      visibleAdd: false,
      visibleEdit: false,
      checkDataInputCompany: {
        show: false,
        message: "",
      },
      checkDataInputAddress: {
        show: false,
        message: "",
      },
      checkDataInputPhone: {
        show: false,
        message: "",
      },
      checkDataInputEmail: {
        show: false,
        message: "",
      },
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
      this.checkDataInputCompany.show = false;
      this.checkDataInputCompany.message = "";
      this.checkDataInputAddress.show = false;
      this.checkDataInputAddress.message = "";
      this.checkDataInputPhone.show = false;
      this.checkDataInputPhone.message = "";
      this.checkDataInputEmail.show = false;
      this.checkDataInputEmail.message = "";
      this.dataAdd.name = "";
      this.dataAdd.phone = "";
      this.dataAdd.email = "";
      this.dataAdd.address = "";
      this.visibleAdd = true;
    },
    submitAdd() {
      this.dataAdd.name = this.dataAdd.name.trim();
      this.dataAdd.email = this.dataAdd.email.trim();
      this.dataAdd.address = this.dataAdd.address.trim();
      this.loading = true;
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
          this.loading = false;
          this.visibleAdd = false;
        })
        .catch((e) => {
          console.log(e);
          this.loading = false;
          this.visibleAdd = false;
        });
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
      this.dataEdit.name = this.dataEdit.name.trim();
      this.dataEdit.email = this.dataEdit.email.trim();
      this.dataEdit.address = this.dataEdit.address.trim();
      this.loadingUpdate = true;
      companyService
        .updateCompany(this.dataEdit)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Cập nhật";
            let description =
              "Sửa thông tin công ty " + this.dataEdit.name + " thành công !!";
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
          this.loadingUpdate = false;
          this.visibleEdit = false;
        })
        .catch((e) => {
          console.log(e);
          this.loadingUpdate = false;
          this.visibleEdit = false;
        });
    },
    deleteUser(id) {
      companyService
        .deleteCompany(id)
        .then((response) => {
          if (response.data.data) {
            let type = "success";
            let message = "Xóa";
            let description =
              "Xóa thông tin công ty " + this.dataEdit.name + " thành công !!";
            this.notifi(type, message, description);
            this.submitSearch();
          } else {
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
    inputCompany() {
      if (this.dataAdd.name != null && this.dataAdd.name.trim() != "") {
        this.checkDataInputCompany.show = false;
        this.checkDataInputCompany.message = "";
      } else {
        this.checkDataInputCompany.show = true;
        this.checkDataInputCompany.message = "Bạn phải điền công ty";
      }
    },
    inputAddress() {
      if (this.dataAdd.address != null && this.dataAdd.address.trim() != "") {
        this.checkDataInputAddress.show = false;
        this.checkDataInputAddress.message = "";
      } else {
        this.checkDataInputAddress.show = true;
        this.checkDataInputAddress.message = "Bạn phải điền địa chỉ";
      }
    },
    inputPhone() {
      if (this.dataAdd.phone != null && this.dataAdd.phone != "") {
        this.checkDataInputPhone.show = false;
        this.checkDataInputPhone.message = "";
      } else {
        this.checkDataInputPhone.show = true;
        this.checkDataInputPhone.message = "Bạn phải điền số điện thoại";
      }
    },
    inputEmail() {
      if (this.dataAdd.email != null && this.dataAdd.email.trim() != "") {
        this.checkDataInputEmail.show = false;
        this.checkDataInputEmail.message = "";
      } else {
        this.checkDataInputEmail.show = true;
        this.checkDataInputEmail.message = "Bạn phải điền email";
      }
    },

    inputEditCompany() {
      if (this.dataEdit.name != null && this.dataEdit.name.trim() != "") {
        this.checkDataInputCompany.show = false;
        this.checkDataInputCompany.message = "";
      } else {
        this.checkDataInputCompany.show = true;
        this.checkDataInputCompany.message = "Bạn phải điền công ty";
      }
    },
    inputEditAddress() {
      if (this.dataEdit.address != null && this.dataEdit.address.trim() != "") {
        this.checkDataInputAddress.show = false;
        this.checkDataInputAddress.message = "";
      } else {
        this.checkDataInputAddress.show = true;
        this.checkDataInputAddress.message = "Bạn phải điền địa chỉ";
      }
    },
    inputEditPhone() {
      if (this.dataEdit.phone != null && this.dataEdit.phone != "") {
        this.checkDataInputPhone.show = false;
        this.checkDataInputPhone.message = "";
      } else {
        this.checkDataInputPhone.show = true;
        this.checkDataInputPhone.message = "Bạn phải điền số điện thoại";
      }
    },
    inputEditEmail() {
      if (this.dataEdit.email != null && this.dataEdit.email.trim() != "") {
        this.checkDataInputEmail.show = false;
        this.checkDataInputEmail.message = "";
      } else {
        this.checkDataInputEmail.show = true;
        this.checkDataInputEmail.message = "Bạn phải điền email";
      }
    },
    checkFormAdd() {
      let check = true;
      if (this.dataAdd.name != null && this.dataAdd.name.trim() != "") {
        this.checkDataInputCompany.show = false;
        this.checkDataInputCompany.message = "";
      } else {
        check = false;
        this.checkDataInputCompany.show = true;
        this.checkDataInputCompany.message = "Bạn phải điền công ty";
      }

      if (this.dataAdd.address != null && this.dataAdd.address.trim() != "") {
        this.checkDataInputAddress.show = false;
        this.checkDataInputAddress.message = "";
      } else {
        check = false;
        this.checkDataInputAddress.show = true;
        this.checkDataInputAddress.message = "Bạn phải điền địa chỉ";
      }

      if (this.dataAdd.phone != null && this.dataAdd.phone != "") {
        this.checkDataInputPhone.show = false;
        this.checkDataInputPhone.message = "";
      } else {
        check = false;
        this.checkDataInputPhone.show = true;
        this.checkDataInputPhone.message = "Bạn phải điền số điện thoại";
      }

      if (this.dataAdd.email != null && this.dataAdd.email.trim() != "") {
        this.checkDataInputEmail.show = false;
        this.checkDataInputEmail.message = "";
      } else {
        check = false;
        this.checkDataInputEmail.show = true;
        this.checkDataInputEmail.message = "Bạn phải điền email";
      }
      if (check) {
        this.submitAdd();
      }
    },

    checkFormUpdate() {
      let check = true;

      if (this.dataEdit.name != null && this.dataEdit.name.trim() != "") {
        this.checkDataInputCompany.show = false;
        this.checkDataInputCompany.message = "";
      } else {
        check = false;
        this.checkDataInputCompany.show = true;
        this.checkDataInputCompany.message = "Bạn phải điền công ty";
      }

      if (this.dataEdit.address != null && this.dataEdit.address.trim() != "") {
        this.checkDataInputAddress.show = false;
        this.checkDataInputAddress.message = "";
      } else {
        check = false;
        this.checkDataInputAddress.show = true;
        this.checkDataInputAddress.message = "Bạn phải điền địa chỉ";
      }

      if (this.dataEdit.phone != null && this.dataEdit.phone != "") {
        this.checkDataInputPhone.show = false;
        this.checkDataInputPhone.message = "";
      } else {
        check = false;
        this.checkDataInputPhone.show = true;
        this.checkDataInputPhone.message = "Bạn phải điền số điện thoại";
      }

      if (this.dataEdit.email != null && this.dataEdit.email.trim() != "") {
        this.checkDataInputEmail.show = false;
        this.checkDataInputEmail.message = "";
      } else {
        check = false;
        this.checkDataInputEmail.show = true;
        this.checkDataInputEmail.message = "Bạn phải điền email";
      }

      if (check) {
        this.submitUpdate();
      }
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
</style>