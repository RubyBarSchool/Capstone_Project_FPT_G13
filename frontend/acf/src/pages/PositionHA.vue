<template>
  <div class="PositionHA">
    <a-layout :style="{ background: 'white' }">
      <Header />
      <a-layout-content :style="{ margin: '24px 16px 0' }">
        <div
          :style="{
            background: 'white',
            minHeight: '360px',
            height: '560px',
          }"
        >
          <!-- menu trên -->
          <a-row type="flex">
            <a-col flex="auto">
              <a-input
                placeholder="Tên chức vụ"
                style="width: 150px"
                v-model="dataSearch.name"
              />
              <a-input placeholder="Mã code" style="width: 150px" />
              <a-button type="primary" icon="search"> Tìm kiếm </a-button>
            </a-col>
            <a-col flex="100px">
              <a-button type="primary" icon="user-add" @click="showModalAdd">
                Thêm
              </a-button>
            </a-col>
          </a-row>

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
                      type="dashed"
                      icon="edit"
                      @click="showModalEdit(record.id, record.name)"
                    />
                  </a-col>
                  <a-col :span="8">
                    <a-popconfirm
                      v-if="dataSourceTable.length"
                      title="Bạn có chắc chắn muốn xóa không?"
                      @confirm="deletePosition(record.id)"
                    >
                      <a-button id="delete" type="dashed" icon="delete" />
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
              <a-button key="submit" type="primary" @click="submitAdd">
                Lưu
              </a-button>
            </template>
            <a-form-model>
              <a-form-model-item label="Tên chức vụ">
                <a-input v-model="dataAdd.name" />
              </a-form-model-item>
            </a-form-model>
          </a-modal>
          <!-- popup add -->
          <a-modal v-model="visibleEdit" title="Chỉnh sửa chức vụ">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary" @click="submitUpdate">
                Lưu
              </a-button>
            </template>
            <a-form-model>
              <a-form-model-item label="Tài khoản">
                <a-input v-model="dataEdit.name" />
              </a-form-model-item>
            </a-form-model>
          </a-modal>
        </div>
      </a-layout-content>
      <Footer />
    </a-layout>
  </div>
</template>

<script>
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";
import positionService from "../service/positionService";

export default {
  name: "PositionHA",
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
          title: "ID",
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
          width: 150,
          scopedSlots: { customRender: "action" },
        },
      ],
      visibleAdd: false,
      visibleEdit: false,
    };
  },
  created() {
    this.submitSearch();
  },
  methods: {
    // showModalAdd() {
    //   this.visibleAdd = true;
    // },
    handleCancel() {
      this.visibleAdd = false;
    },
    // submitAdd() {
    //   this.visibleAdd = false;
    // },
    handleTableChange(pagination) {
      this.dataSearch.pageIndex = pagination.current;
      this.pagination = pagination;
      positionService
        .getAllPosition(this.dataSearch)
        .then((response) => {
          this.dataSourceTable = response.data.data;
          this.dataSearch.total = response.data.total;
          this.pagination.total = response.data.total;
          console.log("datasearch", this.dataSourceTable);
        })
        .catch((e) => {
          console.log(e);
        });
    },
    submitSearch() {
      this.dataSearch.total = 0;
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
      this.visibleAdd = true;
    },
    submitAdd() {
      positionService
        .addPosition(this.dataAdd)
        .then((response) => {
          this.dataPosition = response.data.data;
          if (response.data.data) {
            this.submitSearch();
            var task = "success";
            var text = "Thêm";
            this.notifi(task, text);
          }
          this.submitSearch();
        })
        .catch((e) => {
          console.log(e);
        });
      this.visibleAdd = false;
      this.dataAdd.name = "";
    },
    notifi(task, text) {
      this.$notification[task]({
        message: "Thông báo",
        description: text + " thành công",
      });
    },
    deletePosition(id) {
      positionService
        .deletePosition(id)
        .then((response) => {
          if (response.data.data) {
            var task = "success";
            var text = "Xóa";
            this.notifi(task, text);
            this.submitSearch();
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
    showModalEdit(id, name) {
      this.dataEdit.id = id;
      this.dataEdit.username = name;
      this.visibleEdit = true;
    },
    submitUpdate() {
      positionService
        .updatePosition(this.dataEdit)
        .then((response) => {
          if (response.data.data) {
            this.submitSearch();
            var task = "success";
            var text = "Sửa";
            this.notifi(task, text);
            this.submitSearch();
          }
        })
        .catch((e) => {
          console.log(e);
        });
      this.visibleEdit = false;
    },
  },
};
</script>

<style>
#delete:hover {
  background-color: #f56c6c;
  color: white;
}
#edit:hover {
  background-color: #18cfc9;
  color: white;
}
#user:hover {
  background-color: #13ce66;
  color: white;
}
</style>