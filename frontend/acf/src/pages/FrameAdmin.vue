<template>
  <div class="frame">
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
          <!-- menu trên -->
          <a-input
            placeholder="Chiều dài"
            style="width: 150px"
            v-model="dataSearch.length"
          />
          <a-input
            placeholder="Chiều rộng"
            style="width: 150px"
            v-model="dataSearch.width"
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
              :icon="['fas', 'plus-square']"
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
              <template slot="height" slot-scope="text, record">
                {{ record.length }}
              </template>
              <template slot="width" slot-scope="text, record">
                {{ record.width }}
              </template>
              <template slot="action" slot-scope="text, record">
                <a-row>
                  <a-col :span="8">
                    <a-popconfirm
                      v-if="dataSourceTable.length"
                      title="Bạn có chắc chắn muốn xóa không?"
                      @confirm="deleteFrame(record.id)"
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
          <!-- table content -->

          <!-- popup add-->
          <a-modal v-model="visibleAdd" title="Thêm khung">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary" @click="checkFormAdd">
                Lưu
              </a-button>
            </template>
            <a-form-model>
              <a-form-model-item label="Chiều dài">
                <a-input v-model="dataAdd.length" />
                <div style="color: red" v-if="checkInputLength.show">
                  {{ checkInputLength.message }}
                </div>
              </a-form-model-item>
              <a-form-model-item label="Chiều rộng">
                <a-input v-model="dataAdd.width" />
                <div style="color: red" v-if="checkInputWidth.show">
                  {{ checkInputWidth.message }}
                </div>
              </a-form-model-item>
            </a-form-model>
          </a-modal>
          <!-- popup add -->
        </div>
      </a-layout-content>
      <Footer />
    </a-layout>
  </div>
</template>
 <script>
import frameAdminService from "../service/frameAdminService";
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";

export default {
  name: "FrameAdmin",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      pagination: {
        current: 1,
        pageSize: 10,
        total: 0,
      },
      dataSearch: {
        idUnit: 0,
        length: "",
        pageIndex: 1,
        pageSize: 10,
        width: "",
        total: 0,
      },
      dataSourceTable: [],
      dataAdd: {
        length: "",
        width: "",
      },
      columns: [
        {
          title: "STT",
          width: 100,
          dataIndex: "id",
          key: "id",
          fixed: "left",
        },
        {
          title: "Chiều dài",
          dataIndex: "height",
          key: "height",
          width: 150,
          scopedSlots: { customRender: "height" },
        },
        {
          title: "Chiều rộng",
          dataIndex: "width",
          key: "width",
          width: 150,
          scopedSlots: { customRender: "width" },
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
      checkInputLength: {
        show: false,
        message: "",
      },
      checkInputWidth: {
        show: false,
        message: "",
      },
    };
  },
  computed: {},
  created() {
    this.submitSearch();
  },
  methods: {
    handleTableChange(pagination) {
      this.dataSearch.pageIndex = pagination.current;
      this.pagination = pagination;
      frameAdminService
        .searchFrame(this.dataSearch)
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
      this.checkInputLength.show = false;
      this.checkInputLength.message = "";
      this.checkInputWidth.show = false;
      this.checkInputWidth.message = "";
    },
    checkFormAdd() {
        if (this.dataAdd.length == null || this.dataAdd.length == "") {
        this.checkInputLength.show = true;
        this.checkInputLength.message = "Bạn phải điền chiều dài";
      } else {
        this.checkInputLength.show = false;
        this.checkInputLength.message = "";
      }
      if(this.dataAdd.width == null || this.dataAdd.width == "") {
        this.checkInputWidth.show = true;
        this.checkInputWidth.message = "Bạn phải điền chiều rộng";
      }else {
        this.checkInputWidth.show = false;
        this.checkInputWidth.message = "";
      }
      if(this.dataAdd.width != null && this.dataAdd.width != ""
      && this.dataAdd.length != null && this.dataAdd.length != ""){
        this.checkInputWidth.show = false;
        this.checkInputLength.show = false;
        this.submitAdd();
      }
    },
    submitAdd() {
      frameAdminService
        .addFrame(this.dataAdd)
        .then((response) => {
          this.dataEmployees = response.data.data;
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Thêm mới";
            let description = response.data.message;
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Thêm mới";
            let description = response.data.message;
            this.notifi(type, message, description);
          }
        })
        .catch((e) => {
          console.log(e);
        });
      this.visibleAdd = false;
      this.dataAdd.length = "";
      this.dataAdd.width = "";
    },
    handleCancel() {
      this.visibleAdd = false;
    },
    submitSearch() {
      this.dataSearch.total = 0;
      frameAdminService
        .searchFrame(this.dataSearch)
        .then((response) => {
          this.dataSourceTable = response.data.data;
          this.dataSearch.total = response.data.total;
          this.pagination.total = response.data.total;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    deleteFrame(id) {
      frameAdminService
        .deleteFrame(id)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Xóa";
            let description = response.data.message;
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Xóa";
            let description = response.data.message;
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
#edit {
  background-color: rgb(10, 208, 243);
  color: white;
}
#edit:hover {
  background-color: rgb(0, 181, 253);
  color: white;
}
#user {
  background-color: rgb(76, 238, 12);
  color: white;
}
#user:hover {
  background-color: rgb(42, 253, 0);
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
/* profile */
</style>