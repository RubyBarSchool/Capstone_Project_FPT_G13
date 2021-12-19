<template>
  <div class="unit">
    <a-layout :style="{ background: 'white' }">
      <a-layout-content :style="{ margin: '30px 16px 0' }">
        <div
          :style="{
            minHeight: '360px',
            background: 'white',
          }"
        >
          <a-back-top :style="{ width: '5vh', height: '15vh' }" />
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
              <template slot="unit" slot-scope="text, record">
                {{ record.name }}
              </template>
              <template slot="action" slot-scope="text, record">
                <a-row>
                  <a-popconfirm
                    v-if="dataSourceTable.length"
                    title="Bạn có chắc chắn muốn xóa không?"
                    @confirm="deleteUnitAdmin(record.id)"
                    ok-text="Đồng ý"
                    cancel-text="Hủy"
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
          <a-modal v-model="visibleAdd" title="Thêm đơn vị">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button
                key="submit"
                type="primary"
                :loading="loadingAdd"
                @click="checkFormAdd"
              >
                Lưu
              </a-button>
            </template>
            <a-form-model>
              <span style="color: red">*</span> Loại đơn vị đo:
              <a-form-model-item>
                <a-input v-model="name" @change="inputNameAdd" />
                <div style="color: red" v-if="checkDataInputName.show">
                  {{ checkDataInputName.message }}
                </div>
              </a-form-model-item>
            </a-form-model>
          </a-modal>
          <!-- popup add -->
        </div>
      </a-layout-content>
    </a-layout>
  </div>
</template>
 <script>
import unitService from "../service/unitService.js";

export default {
  name: "UnitAdmin",
  components: {},
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
          title: "Loại đơn vị đo",
          dataIndex: "unit",
          key: "unit",
          width: 150,
          scopedSlots: { customRender: "unit" },
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
      loadingAdd: false,
    };
  },
  created() {
    this.getAllUnits();
  },
  methods: {
    getAllUnits() {
      unitService
        .getAllUnits()
        .then((response) => {
          this.dataSourceTable = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    showModalAdd() {
      this.name = "";
      this.checkDataInputName.show = false;
      this.checkDataInputName.message = "";
      this.visibleAdd = true;
    },
    checkFormAdd() {
      if (this.name != null && this.name.trim() != "") {
        this.checkDataInputName.show = false;
        this.checkDataInputName.message = "";
        this.submitAdd();
      } else {
        this.checkDataInputName.show = true;
        this.checkDataInputName.message = "Bạn phải điền vào ô loại đơn vị đo";
      }
    },
    inputNameAdd() {
      if (this.name != null && this.name.trim() != "") {
        this.checkDataInputName.show = false;
        this.checkDataInputName.message = "";
      } else {
        this.checkDataInputName.show = true;
        this.checkDataInputName.message = "Bạn phải điền vào ô loại đơn vị đo";
      }
    },
    submitAdd() {
      this.loadingAdd = true;
      unitService
        .addUnitAdmin(this.name)
        .then((response) => {
          this.getAllUnits();
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
    deleteUnitAdmin(id) {
      unitService
        .deleteUnitAdmin(id)
        .then((response) => {
          this.getAllUnits();
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
    checkUnitAdmin() {
      if (this.name != null && this.name != "") {
        this.checkDataInputName.show = false;
        this.checkDataInputName.message = "";
        this.submitAdd();
      } else {
        this.checkDataInputName.show = true;
        this.checkDataInputName.message = "Bạn phải điền tên đơn vị";
      }
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