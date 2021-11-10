<template>
  <div class="groupMaterial">
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
              :rowKey="
                (record, index) => {
                  return index;
                }
              "
            >
              <template slot="material" slot-scope="text, record">
                {{ record.name }}
              </template>
              <template slot="action" slot-scope="text, record">
                <a-row>
                  <a-popconfirm
                    v-if="dataSourceTable.length"
                    title="Bạn có chắc chắn muốn xóa không?"
                    @confirm="deleteGroupMaterial(record.id)"
                  >
                    <a-button id="delete">
                      <font-awesome-icon :icon="['fas', 'trash']" />
                    </a-button>
                  </a-popconfirm>
                </a-row>
              </template>
            </a-table>
          </div>
          <!-- table content -->

          <!-- popup add-->
          <a-modal v-model="visibleAdd" title="Thêm nhóm vật liệu">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button
                key="submit"
                type="primary"
                @click="checkGroupMaterialName"
              >
                Lưu
              </a-button>
            </template>
            <a-form-model>
              <a-form-model-item label="Tên nhóm vật liệu">
                <a-input v-model="name" />
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
import groupMaterialService from "../service/groupMaterialService.js";
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";

export default {
  name: "GroupMaterial",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      dataSourceTable: [],
      name: "",
      checkDataInputName: {
        show: false,
        message: "",
      },
      columns: [
        {
          title: "ID",
          width: 100,
          dataIndex: "id",
          key: "id",
          fixed: "left",
        },
        {
          title: "Loại vật liệu",
          dataIndex: "material",
          key: "material",
          width: 150,
          scopedSlots: { customRender: "material" },
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
  computed: {},
  created() {
    this.getAllGroupMaterial();
  },
  methods: {
    getAllGroupMaterial() {
      groupMaterialService
        .getAllGroupMaterial()
        .then((response) => {
          this.dataSourceTable = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    showModalAdd() {
      this.visibleAdd = true;
      this.name = "";
    },
    submitAdd() {
      groupMaterialService
        .addGroupMaterial(this.name)
        .then((response) => {
          this.getAllGroupMaterial();
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
    deleteGroupMaterial(id) {
      groupMaterialService
        .deleteGroupMaterial(id)
        .then((response) => {
          this.getAllGroupMaterial();
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
    checkGroupMaterialName() {
      if (this.name != null && this.name != "") {
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
</style>