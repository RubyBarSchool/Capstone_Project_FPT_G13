<template>
  <div class="Position">
    <a-layout :style="{ background: 'white' }">
      <a-layout-content :style="{ margin: '30px 16px 0' }">
        <div
          :style="{
            background: 'white',
            minHeight: '360px',
          }"
        >
        <a-back-top :style="{ width: '5vh', height: '15vh' }" />
          <a-row type="flex">
            <a-col flex="auto">
              <a-carousel autoplay>
                <div><img src="../assets/1.png" /></div>
                <div><img src="../assets/2.png" /></div>
                <div><img src="../assets/3.png" /></div>
                <div><img src="../assets/4.png" /></div>
                <div><img src="../assets/5.png" /></div>
              </a-carousel>
            </a-col>
            <a-col flex="400px">
              <!-- menu trên -->
              <a-input
                placeholder="Tên chức vụ"
                :style="{ width: '150px', 'margin-right': '5px' }"
                v-model="dataSearch.name"
              />
              <a-button
                type="primary"
                @click="submitSearch"
                :style="{ 'margin-right': '5px' }"
              >
                <font-awesome-icon
                  :icon="['fas', 'search']"
                  :style="{ 'margin-right': '5px' }"
                />
                Tìm kiếm
              </a-button>
              <a-button type="primary" @click="showModalAdd">
                <font-awesome-icon
                  :icon="['fas', 'user-plus']"
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
                  :scroll="{ x: 600 }"
                  :rowKey="
                    (record, index) => {
                      return index;
                    }
                  "
                  @change="handleTableChange  "
                >
                  <template slot="id" slot-scope="text, record">
                    {{ record.id }}
                  </template>
                  <template slot="position" slot-scope="text, record">
                    {{ record.name }}
                  </template>
                  <template slot="action" slot-scope="text, record">
                    <a-row>
                      <a-col :span="8">
                        <a-button
                          id="edit"
                          @click="showModalEdit(record.id, record.name)"
                          :style="{ width: '44.25px', 'margin-right': '100px' }"
                        >
                          <font-awesome-icon :icon="['fas', 'edit']" />
                        </a-button>
                      </a-col>
                      <a-col :span="8">
                        <a-popconfirm
                          v-if="dataSourceTable.length"
                          title="Bạn có chắc chắn muốn xóa không?"
                          @confirm="deletePosition(record.id)"
                          ok-text="Đồng ý"
                          cancel-text="Hủy"
                        >
                          <a-button id="delete">
                            <!-- :loading = "loadingDelete" -->
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
              <a-modal v-model="visibleAdd" title="Thêm chức vụ">
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
                <a-form-model>
                  <span style="color: red">*</span> Tên chức vụ:
                  <a-form-model-item>
                    <a-input @change="inputNameAdd" v-model="dataAdd.name" />
                    <div style="color: red" v-if="checkDataInputName.show">
                      {{ checkDataInputName.message }}
                    </div>
                  </a-form-model-item>
                </a-form-model>
              </a-modal>
              <!-- popup add -->
              <!-- popup update -->
              <a-modal v-model="visibleEdit" title="Chỉnh sửa chức vụ">
                <template slot="footer">
                  <a-button key="back" @click="handleCancel"> Hủy </a-button>
                  <a-button
                    :loading="loading"
                    key="submit"
                    type="primary"
                    @click="checkFormEdit"
                  >
                    Lưu
                  </a-button>
                </template>
                <a-form-model>
                  <span style="color: red">*</span> Tên chức vụ:
                  <a-form-model-item>
                    <a-input @change="inputNameEdit" v-model="dataEdit.name" />
                    <div style="color: red" v-if="checkDataInputName.show">
                      {{ checkDataInputName.message }}
                    </div>
                  </a-form-model-item>
                </a-form-model>
              </a-modal>
              <!-- popup update -->
            </a-col>
          </a-row>
        </div>
      </a-layout-content>
    </a-layout>
  </div>
</template>

<script>
import positionService from "../service/positionService";

export default {
  name: "Position",
  components: {
  },
  data() {
    return {
      loading: false,
      pagination: {
        current: 1,
        pageSize: 10,
        total: 0,
      },
      dataSearch: {
        name: "",
        pageIndex: 1,
        pageSize: 10,
        total: 0,
      },
      dataSourceTable: [],
      dataAdd: {
        code: "",
        name: "",
      },
      dataPosition: [],
      dataEdit: {
        code: "",
        id: 0,
        name: "",
      },
      columns: [
        {
          title: "STT",
          width: 100,
          dataIndex: "id",
          key: "id",
          fixed: "left",
          scopedSlots: { customRender: "id" },
        },
        {
          title: "Tên chức vụ",
          dataIndex: "position",
          key: "position",
          width: 150,
          scopedSlots: { customRender: "position" },
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
      checkDataInputName: {
        show: false,
        message: "",
      },
      loadingDelete: false,
    };
  },
  created() {
    this.submitSearch();
  },
  methods: {
    handleTableChange(pagination) {
      this.dataSearch.pageIndex = pagination.current;
      this.pagination = pagination;
      positionService
        .getAllPosition(this.dataSearch)
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
      this.dataSearch.name = this.dataSearch.name.trim();
      positionService
        .getAllPosition(this.dataSearch)
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
      this.dataAdd.name = "";
      this.visibleAdd = true;
      this.checkDataInputName.show = false;
      this.checkDataInputName.message = "";
    },
    checkFormAdd() {
      if (this.dataAdd.name != null && this.dataAdd.name.trim() != "") {
        this.checkDataInputName.show = false;
        this.checkDataInputName.message = "";
        this.submitAdd();
      } else {
        this.checkDataInputName.show = true;
        this.checkDataInputName.message = "Bạn phải điền vào ô tên chức vụ";
      }
    },
    checkFormEdit() {
      if (this.dataEdit.name != null && this.dataEdit.name.trim() != "") {
        this.checkDataInputName.show = false;
        this.checkDataInputName.message = "";
        this.submitUpdate();
      } else {
        this.checkDataInputName.show = true;
        this.checkDataInputName.message = "Bạn phải điền vào ô tên chức vụ";
      }
    },
    inputNameAdd() {
      if (this.dataAdd.name != null && this.dataAdd.name.trim() != "") {
        this.checkDataInputName.show = false;
        this.checkDataInputName.message = "";
      } else {
        this.checkDataInputName.show = true;
        this.checkDataInputName.message = "Bạn phải điền vào ô tên chức vụ";
      }
    },
    inputNameEdit() {
      if (this.dataEdit.name != null && this.dataEdit.name.trim() != "") {
        this.checkDataInputName.show = false;
        this.checkDataInputName.message = "";
      } else {
        this.checkDataInputName.show = true;
        this.checkDataInputName.message = "Bạn phải điền vào ô tên chức vụ";
      }
    },
    submitAdd() {
      this.dataAdd.name = this.dataAdd.name.trim();
      this.loading = true;
      positionService
        .addPosition(this.dataAdd)
        .then((response) => {
          if (response.data.data) {
            let type = "success";
            let message = "Thêm chức vụ";
            let description =
              "Thêm chức vụ : " + this.dataAdd.name + " thành công";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Thêm chức vụ";
            let description =
              "Thêm chức vụ : " + this.dataAdd.name + " không thành công";
            this.notifi(type, message, description);
          }
          this.submitSearch();
          this.visibleAdd = false;
          this.dataAdd.name = "";
          this.loading = false;
        })
        .catch(() => {
          let type = "error";
          let message = "Thêm chức vụ";
          let description =
            "Thêm chức vụ : " + this.dataAdd.name + " không thành công";
          this.notifi(type, message, description);
          this.visibleAdd = false;
          this.dataAdd.name = "";
          this.loading = false;
        });
    },
    notifi(type, message, description) {
      this.$notification[type]({
        message: message,
        description: description,
      });
    },
    deletePosition(id) {
      this.loadingDelete = true;
      positionService
        .deletePosition(id)
        .then((response) => {
          if (response.data.data) {
            let type = "success";
            let message = "Xóa chức vụ";
            let description = "Xóa chức vụ thành công";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Xóa chức vụ";
            let description = "Xóa chức vụ không thành công";
            this.notifi(type, message, description);
          }
          this.submitSearch();
          this.loadingDelete = false;
        })
        .catch(() => {
          let type = "error";
          let message = "Xóa chức vụ";
          let description = "Xóa chức vụ không thành công";
          this.notifi(type, message, description);
          this.loadingDelete = false;
        });
    },
    showModalEdit(id, name) {
      this.dataEdit.id = id;
      this.dataEdit.name = name;
      this.visibleEdit = true;
      this.checkDataInputName.show = false;
      this.checkDataInputName.message = "";
    },
    submitUpdate() {
      this.dataEdit.name = this.dataEdit.name.trim();
      this.loading = true;
      positionService
        .updatePosition(this.dataEdit)
        .then((response) => {
          if (response.data.data) {
            let type = "success";
            let message = "Sửa chức vụ";
            let description =
              "Sửa chức vụ : " + this.dataEdit.name + " thành công";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Sửa chức vụ";
            let description =
              "Sửa chức vụ : " + this.dataEdit.name + " không thành công";
            this.notifi(type, message, description);
          }
          this.submitSearch();
          this.loading = false;
        })
        .catch(() => {
          let type = "error";
          let message = "Sửa chức vụ";
          let description =
            "Sửa chức vụ : " + this.dataEdit.name + " không thành công";
          this.notifi(type, message, description);
          this.loading = false;
        });
      this.visibleEdit = false;
    },
    handleCancel() {
      this.visibleAdd = false;
      this.visibleEdit = false;
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
#user {
  background-color: rgb(76, 238, 12);
  color: white;
}
#user:hover {
  background-color: rgb(42, 253, 0);
  color: white;
}

/* slide image */
.ant-carousel {
  width: 800px;
}
.ant-carousel :deep(.slick-slide) {
  text-align: center;
  height: 160px;
  line-height: 160px;
  background: #364d79;
  overflow: hidden;
}
.ant-carousel :deep(.slick-slide h3) {
  color: #fff;
}
</style>