<template>
  <div class="attendance">
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
          <a-button
            type="primary"
            @click="showModalAdd"
            :style="{ 'margin-left': '5px' }"
          >
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
              <template slot="height" slot-scope="text, record">
                {{ record.height }}
              </template>
              <template slot="action" slot-scope="text, record">
                <a-popconfirm
                  v-if="dataSourceTable.length"
                  title="Bạn có chắc chắn muốn xóa không?"
                  @confirm="deleteUnit(record.id)"
                >
                  <a-button id="delete">
                    <font-awesome-icon :icon="['fas', 'trash']" />
                  </a-button>
                </a-popconfirm>
              </template>
            </a-table>
          </div>
          <!-- table content -->

          <!-- popup add -->
          <a-modal v-model="visibleAdd" title="Thêm chiều cao">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary" @click="checkFormAdd">
                Lưu
              </a-button>
            </template>
            <a-form-model>
              <a-form-model-item label="Chiều cao">
                <a-input v-model="height" />
                <div style="color: red" v-if="checkDataInputHeight.show">
                  {{ checkDataInputHeight.message }}
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
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";
import chieuCaoService from "@/service/chieuCaoService";
export default {
  name: "Home",
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
      dataSourceTable: [],
      dataAll: {
        id: "",
        height: "",
      },
      height: "",
      columns: [
        {
          title: "STT",
          width: 100,
          dataIndex: "id",
          key: "id",
          fixed: "left",
        },
        {
          title: "Chiều cao",
          dataIndex: "height",
          key: "height",
          width: 150,
          scopedSlots: { customRender: "height" },
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
      checkDataInputHeight: {
        show: false,
        message: "",
      },
    };
  },
  created() {
    this.submitSearch();
  },
  methods: {
    handleTableChange(pagination) {
      this.dataAll.pageIndex = pagination.current;
      this.pagination = pagination;
      chieuCaoService
        .getAllUnits(this.dataAll)
        .then((response) => {
          this.dataSourceTable = response.data.data;
          this.dataAll.total = response.data.total;
          this.pagination.total = response.data.total;
        })
        .catch((e) => {
          console.log(e);
        });
    },

    submitSearch() {
      this.dataAll.total = 0;
      chieuCaoService
        .getAllUnits(this.dataAll)
        .then((response) => {
          this.dataSourceTable = response.data.data;
          this.dataAll.total = response.data.total;
          this.pagination.total = response.data.total;
        })
        .catch((e) => {
          console.log(e);
        });
    },

    submitAdd() {
      chieuCaoService
        .addUnit(this.height)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Chiều cao";
            let description = "Thêm chiều cao thành công !!";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Chiều cao";
            let description =
              "Thêm chiều cao không thành công vì" + response.data.message;
            this.notifi(type, message, description);
          }
        })
        .catch((e) => {
          console.log(e);
        });
      this.visibleAdd = false;
      this.height = "";
    },

    showModalAdd() {
      this.visibleAdd = true;
      this.checkDataInputHeight.show = false;
      this.checkDataInputHeight.message = "";
    //   this.height = "";
    },

    checkFormAdd() {
      if (this.height != null && this.height != "") {
        this.checkDataInputHeight.show = false;
        this.checkDataInputHeight.message = "";
        this.submitAdd();
      } else {
        this.checkDataInputHeight.show = true;
        this.checkDataInputHeight.message = "Bạn phải điền vào chỗ trống";
      }
    },

    handleCancel() {
      this.visibleAdd = false;
    },

    deleteUnit(id) {
      chieuCaoService
        .deleteUnit(id)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Xóa";
            let description = "Xóa chiều cao thành công";
            this.notifi(type, message, description);
            this.submitSearch();
          } else {
            let type = "error";
            let message = "Xóa";
            let description = "Xóa chiều cao không thành công :(";
            this.notifi(type, message, description);
            this.submitSearch();
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
</style>