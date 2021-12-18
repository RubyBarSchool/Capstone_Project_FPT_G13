<template>
  <div class="groupcoverplate">
    <a-layout :style="{ background: 'white' }">
      <a-layout-content :style="{ margin: '24px 16px 0' }">
        <div
          :style="{
            minHeight: '360px',
            background: 'white',
          }"
        >
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
              <template slot="name" slot-scope="text, record">
                {{ record.name }}
              </template>
              <template slot="action" slot-scope="text, record">
                <a-popconfirm
                  v-if="dataSourceTable.length"
                  title="Bạn có chắc chắn muốn xóa không?"
                  @confirm="deleteGroup(record.id)"
                  ok-text="Đồng ý"
                  cancel-text="Đóng"
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
          <a-modal v-model="visibleAdd" title="Thêm nhóm tấm phủ">
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
              <span style="color: red">*</span> Tên nhóm tấm phủ
              <a-input @change="inputGroup" v-model="name" />
              <div style="color: red" v-if="checkDataInputGroup.show">
                {{ checkDataInputGroup.message }}
              </div>
            </a-form-model>
          </a-modal>
          <!-- popup add -->
        </div>
      </a-layout-content>
    </a-layout>
  </div>
</template>

<script>
import groupCoverPlateService from "@/service/groupCoverPlateService";
export default {
  name: "GroupCoverPlate",
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
      dataSourceTable: [],
      name: "",
      columns: [
        {
          title: "STT",
          width: 100,
          dataIndex: "id",
          key: "id",
          fixed: "left",
        },
        {
          title: "Nhóm tấm phủ",
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
      checkDataInputGroup: {
        show: false,
        message: "",
      },
    };
  },
  created() {
    this.getAllGroupS();
  },
  methods: {
    getAllGroupS() {
      groupCoverPlateService
        .getAllGroupS()
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
      this.name = this.name.trim();
      this.loading = true;
      groupCoverPlateService
        .addGroup(this.name)
        .then((response) => {
          this.getAllGroupS();
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
          this.loading = false;
          this.visibleAdd = false;
        })
        .catch((e) => {
          console.log(e);
          this.loading = false;
          this.visibleAdd = false;
        });
    },
    handleCancel() {
      this.visibleAdd = false;
    },
    deleteGroup(id) {
      groupCoverPlateService
        .deleteGroup(id)
        .then((response) => {
          this.getAllGroupS();
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
    checkFormAdd() {
      let check = true;
      if (this.name != null && this.name.trim() != "") {
        this.checkDataInputGroup.show = false;
        this.checkDataInputGroup.message = "";
      } else {
        check = false;
        this.checkDataInputGroup.show = true;
        this.checkDataInputGroup.message = "Bạn phải điền tên nhóm tấm phủ";
      }
      if (check) {
        this.submitAdd();
      }
    },
    inputGroup() {
      if (this.name != null && this.name.trim() != "") {
        this.checkDataInputGroup.show = false;
        this.checkDataInputGroup.message = "";
      } else {
        this.checkDataInputGroup.show = true;
        this.checkDataInputGroup.message = "Bạn phải điền tên nhóm tấm phủ";
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