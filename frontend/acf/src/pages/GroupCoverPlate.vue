<template>
  <div class="groupcoverplate">
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
              <template slot="name" slot-scope="text, record">
                {{ record.name }}
              </template>
              <template slot="action" slot-scope="text, record">
                <a-popconfirm
                  v-if="dataSourceTable.length"
                  title="Bạn có chắc chắn muốn xóa không?"
                  @confirm="deleteGroup(record.id)"
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
              <a-button key="submit" type="primary" @click="checkFormAdd">
                Lưu
              </a-button>
            </template>
            <a-form-model>
              <a-form-model-item label="Tên nhóm tấm phủ">
                <a-input v-model="name" />
                <div style="color: red" v-if="checkDataInputGroup.show">
                  {{ checkDataInputGroup.message }}
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
import groupCoverPlateService from "@/service/groupCoverPlateService";
export default {
  name: "GroupCoverPlate",
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
        name: "",
      },
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
    this.submitSearch();
  },
  methods: {
    handleTableChange(pagination) {
      this.dataAll.pageIndex = pagination.current;
      this.pagination = pagination;
      groupCoverPlateService
        .getAllGroupS(this.dataAll)
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
      groupCoverPlateService
        .getAllGroupS(this.dataAll)
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
      groupCoverPlateService
        .addGroup(this.name)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Nhóm tấm phủ";
            let description = "Thêm nhóm tấm phủ thành công !!";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Nhóm tấm phủ";
            let description =
              "Thêm nhóm tấm phủ không thành công vì" + response.data.message;
            this.notifi(type, message, description);
          }
        })
        .catch((e) => {
          console.log(e);
        });
      this.visibleAdd = false;
      this.name = "";
    },
    showModalAdd() {
      this.visibleAdd = true;
      this.checkDataInputGroup.show = false;
      this.checkDataInputGroup.message = "";
      this.name = "";
    },
    checkFormAdd() {
      if (this.name != null && this.name != "") {
        this.checkDataInputGroup.show = false;
        this.checkDataInputGroup.message = "";
        this.submitAdd();
      } else {
        this.checkDataInputGroup.show = true;
        this.checkDataInputGroup.message = "Bạn phải điền vào chỗ trống";
      }
    },
    handleCancel() {
      this.visibleAdd = false;
    },
    deleteGroup(id) {
      groupCoverPlateService
        .deleteGroup(id)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Xóa nhóm tấm phủ";
            let description = "Xóa nhóm tấm phủ thành công";
            this.notifi(type, message, description);
            this.submitSearch();
          } else {
            let type = "error";
            let message = "Xóa nhóm tấm phủ";
            let description = "Xóa nhóm tấm phủ không thành công :(";
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