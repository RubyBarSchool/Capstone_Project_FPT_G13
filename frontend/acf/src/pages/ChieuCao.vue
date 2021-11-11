<template>
  <div class="chieucao">
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
              :rowKey="
                (record, index) => {
                  return index;
                }
              "
            >
              <template slot="frameHeight" slot-scope="text, record">
                {{ record.frameHeight }}
              </template>
              <template slot="action" slot-scope="text, record">
                <a-popconfirm
                  v-if="dataSourceTable.length"
                  title="Bạn có chắc chắn muốn xóa không?"
                  @confirm="deleteFrameHeight(record.id)"
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
              <a-button key="submit" type="primary" @click="checkForm">
                Lưu
              </a-button>
            </template>
            <a-form-model>
              <a-form-model-item label="Chiều cao">
                <a-input v-model="frameHeight" />
                <div style="color: red" v-if="checkDataInputName.show">
                  {{ checkDataInputName.message }}
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
  name: "ChieuCao",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      dataSourceTable: [],
      frameHeight: "",
      checkDataInputName: {
        show: false,
        message: "",
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
          title: "Chiều cao",
          dataIndex: "frameHeight",
          key: "frameHeight",
          width: 150,
          scopedSlots: { customRender: "frameHeight" },
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
    };
  },
  created() {
    this.getAllFrameHeight();
  },
  methods: {
    getAllFrameHeight() {
      chieuCaoService
        .getAllFrameHeight()
        .then((response) => {
          this.dataSourceTable = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    showModalAdd() {
      this.visibleAdd = true;
      this.frameHeight = "";
    },
    submitAdd() {
      chieuCaoService
        .addFrameHeight(this.frameHeight)
        .then((response) => {
          this.getAllFrameHeight();
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
    },
    handleCancel() {
      this.visibleAdd = false;
    },
    deleteFrameHeight(id) {
      chieuCaoService
        .deleteFrameHeight(id)
        .then((response) => {
          this.getAllFrameHeight();
          if (response.data.data) {
            let type = "success";
            let message = "Xóa";
            let description = "Xóa đơn vị thành công";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Xóa";
            let description = "Đơn vị đang sử dụng, không được xóa";
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
    checkForm() {
      if (this.frameHeight != null && this.frameHeight != "") {
        this.checkDataInputName.show = false;
        this.checkDataInputName.message = "";
        this.submitAdd();
      } else {
        this.checkDataInputName.show = true;
        this.checkDataInputName.message = "Bạn phải điền vào chỗ trống";
      }
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