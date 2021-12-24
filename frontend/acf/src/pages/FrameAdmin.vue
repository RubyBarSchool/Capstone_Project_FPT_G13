<template>
  <div class="frame">
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
            placeholder="Chiều dài"
            :style="{ width: '150px', 'margin-right': '5px' }"
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
          <!-- table content -->

          <!-- popup add-->
          <a-modal v-model="visibleAdd" title="Thêm khung">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button
                key="submit"
                type="primary"
                @click="checkFormAdd"
                :loading="loadingAdd"
              >
                Lưu
              </a-button>
            </template>
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span>Chiều dài :</a-col
              >
              <a-col flex="auto">
                <a-input-number
                  style="width: 100%"
                  v-model="dataAdd.length"
                  :min="0"
                  @change="inputLengthAdd"
                />
                <div style="color: red" v-if="checkInputLength.show">
                  {{ checkInputLength.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span>Chiều rộng :</a-col
              >
              <a-col flex="auto">
                <a-input-number
                  style="width: 100%"
                  v-model="dataAdd.width"
                  :min="0"
                  @change="inputWidthAdd"
                />
                <div style="color: red" v-if="checkInputWidth.show">
                  {{ checkInputWidth.message }}
                </div>
              </a-col>
            </a-row>
          </a-modal>
          <!-- popup add -->
        </div>
      </a-layout-content>
    </a-layout>
  </div>
</template>
 <script>
import frameAdminService from "../service/frameAdminService";

export default {
  name: "FrameAdmin",
  components: {},
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
      loadingAdd: false,
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
      this.dataAdd.length = "";
      this.dataAdd.width = "";
      this.checkInputLength.show = false;
      this.checkInputLength.message = "";
      this.checkInputWidth.show = false;
      this.checkInputWidth.message = "";
      this.visibleAdd = true;
    },
    checkFormAdd() {
      let check = true;
      if (this.dataAdd.length != null && this.dataAdd.length != "") {
        this.checkInputLength.show = false;
        this.checkInputLength.message = "";
      } else {
        check = false;
        this.checkInputLength.show = true;
        this.checkInputLength.message = "Bạn phải điền chiều dài";
      }
      if (this.dataAdd.width != null && this.dataAdd.width != "") {
        this.checkInputWidth.show = false;
        this.checkInputWidth.message = "";
      } else {
        check = false;
        this.checkInputWidth.show = true;
        this.checkInputWidth.message = "Bạn phải điền chiều rộng";
      }
      if (check == true) {
        this.submitAdd();
      }
    },
    inputLengthAdd() {
      if (this.dataAdd.length != null && this.dataAdd.length != "") {
        this.checkInputLength.show = false;
        this.checkInputLength.message = "";
      } else {
        this.checkInputLength.show = true;
        this.checkInputLength.message = "Bạn phải điền chiều dài";
      }
    },
    inputWidthAdd() {
      if (this.dataAdd.width != null && this.dataAdd.width != "") {
        this.checkInputWidth.show = false;
        this.checkInputWidth.message = "";
      } else {
        this.checkInputWidth.show = true;
        this.checkInputWidth.message = "Bạn phải điền chiều rộng";
      }
    },
    submitAdd() {
      this.loadingAdd = true;
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
          this.loadingAdd = false;
          this.visibleAdd = false;
        })
        .catch(() => {
          this.loadingAdd = false;
          this.visibleAdd = false;
        });
    },
    handleCancel() {
      this.visibleAdd = false;
    },
    submitSearch() {
      this.dataSearch.total = 0;
      this.dataSearch.length = this.dataSearch.length.trim();
      this.dataSearch.width = this.dataSearch.width.trim();
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
</style>