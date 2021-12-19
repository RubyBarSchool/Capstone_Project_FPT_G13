<template>
  <div class="chieucao">
    <a-back-top :style="{ width: '5vh', height: '15vh' }" />
    <!-- menu trên -->
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
            ok-text="Đồng ý"
            cancel-text="Hủy"
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
        <a-button
          key="submit"
          type="primary"
          @click="checkFormAdd"
          :loading="loadingAdd"
        >
          Lưu
        </a-button>
      </template>
      <a-form-model>
        <span style="color: red">*</span>Chiều cao :
        <a-form-model-item>
          <a-input-number
            style="width: 100%"
            v-model="frameHeight"
            :min="0"
            @change="inputNameAdd"
          />
          <div style="color: red" v-if="checkDataInputName.show">
            {{ checkDataInputName.message }}
          </div>
        </a-form-model-item>
      </a-form-model>
    </a-modal>
    <!-- popup add -->
  </div>
</template>

<script>
import heightService from "@/service/heightService";
export default {
  name: "Height",
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
      loadingAdd: false,
    };
  },
  created() {
    this.getAllFrameHeight();
  },
  methods: {
    getAllFrameHeight() {
      heightService
        .getAllFrameHeight()
        .then((response) => {
          this.dataSourceTable = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    showModalAdd() {
      this.frameHeight = "";
      this.checkDataInputName.show = false;
      this.checkDataInputName.message = "";
      this.visibleAdd = true;
    },
    checkFormAdd() {
      if (this.frameHeight != null && this.frameHeight != "") {
        this.checkDataInputName.show = false;
        this.checkDataInputName.message = "";
        this.submitAdd();
      } else {
        this.checkDataInputName.show = true;
        this.checkDataInputName.message = "Bạn phải điền vào ô chiều cao";
      }
    },
    inputNameAdd() {
      if (this.frameHeight != null && this.frameHeight != "") {
        this.checkDataInputName.show = false;
        this.checkDataInputName.message = "";
      } else {
        this.checkDataInputName.show = true;
        this.checkDataInputName.message = "Bạn phải điền vào ô chiều cao";
      }
    },
    submitAdd() {
      this.loadingAdd = true;
      heightService
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
    deleteFrameHeight(id) {
      heightService
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