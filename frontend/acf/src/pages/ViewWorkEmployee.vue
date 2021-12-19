<template>
  <div class="attendance">
    <a-layout :style="{ background: 'white' }">
      <a-layout-content :style="{ margin: '30px 16px 0' }">
        <div
          :style="{
            minHeight: '360px',
            background: 'white',
          }"
        >
          <a-back-top :style="{ width: '5vh', height: '15vh' }" />
          Trạng thái:
          <a-select
            v-model="dataSearch.status"
            @change="changeSearch"
            style="width: 10%"
          >
            <a-select-option key=""> Tất Cả </a-select-option>
            <a-select-option key="-2"> Chờ duyệt </a-select-option>
            <a-select-option key="-1"> Đang chờ xác nhận </a-select-option>
            <a-select-option key="0"> Đang làm </a-select-option>
            <a-select-option key="1"> Hoàn thành </a-select-option>
          </a-select>
          <div :style="{ 'padding-top': '10px' }">
            <a-table
              :columns="columns"
              :data-source="dataSourceTable"
              :pagination="pagination"
              :scroll="{ x: 1000 }"
              :rowKey="
                (record, index) => {
                  return index;
                }
              "
              @change="handleTableChange"
            >
              <template slot="status" slot-scope="text, record">
                <a-tag
                  :color="
                    record.status == '-2'
                      ? '#108ee9'
                      : record.status == '-1'
                      ? '#f50'
                      : record.status == '0'
                      ? '#2db7f5'
                      : '#87d068'
                  "
                >
                  {{
                    record.status == "-2"
                      ? "Đang chờ duyệt"
                      : record.status == "-1"
                      ? "Đang chờ xác nhận"
                      : record.status == "0"
                      ? "Đang làm"
                      : "Hoàn thành"
                  }}
                </a-tag>
              </template>
              <template slot="dateStart" slot-scope="text, record">
                {{
                  new Date(record.dateStart).toLocaleDateString("en-GB", {
                    year: "numeric",
                    month: "2-digit",
                    day: "2-digit",
                  })
                }}
              </template>
              <template slot="dateEnd" slot-scope="text, record">
                {{
                  new Date(record.dateEnd).toLocaleDateString("en-GB", {
                    year: "numeric",
                    month: "2-digit",
                    day: "2-digit",
                  })
                }}
              </template>
              <template slot="action" slot-scope="text, record">
                <a-row v-if="record.status == -1">
                  <a-col :span="8">
                    <a-popconfirm
                      v-if="record.status == '-1'"
                      title="Bạn có chắc chắn xác nhận thực hiện không?"
                      @confirm="confirmWork(record)"
                    >
                      <a-button>
                        <font-awesome-icon :icon="['fas', 'check-circle']" />
                      </a-button>
                    </a-popconfirm>
                  </a-col>
                </a-row>

                <a-row v-if="record.status != -1 && record.status != -2">
                  <a-col :span="8">
                    <a-button
                      id="view"
                      @click="showModelView(record)"
                      :style="{ width: '44.25px', 'margin-right': '100px' }"
                    >
                      <font-awesome-icon :icon="['fas', 'eye']" />
                    </a-button>
                  </a-col>

                  <a-col :span="8" v-if="record.status == 0">
                    <a-button
                      id="edit"
                      @click="showEditForm(record)"
                      :style="{ width: '44.25px' }"
                    >
                      <font-awesome-icon :icon="['fas', 'edit']" />
                    </a-button>
                  </a-col>
                </a-row>
              </template>
            </a-table>

            <a-modal
              width="80%"
              v-model="showModalView"
              title="Xem vật liệu của sản phẩm"
            >
              <template slot="footer">
                <a-button key="back" @click="handleCancelview"> Đóng </a-button>
              </template>
              <a-table
                :columns="columnsView"
                :data-source="dataSourceTableView"
                :pagination="false"
                :scroll="{ x: 1000 }"
                :rowKey="
                  (record, index) => {
                    return index;
                  }
                "
              >
              </a-table>
            </a-modal>

            <a-modal
              v-model="showModalEdit"
              title="Cập nhật số lượng sản phẩm đã làm xong"
            >
              <template slot="footer">
                <a-button key="back" @click="handleCancelEdit"> Đóng </a-button>
                <a-button
                  key="submit"
                  type="primary"
                  @click="checkFormEdit"
                  :loading="loadingEdit"
                >
                  Lưu
                </a-button>
              </template>
              <a-input
                type="number"
                v-model="dataEdit.number"
                :min="0"
                :max="maxNumber"
                @change="inputEdit"
              />
              <div style="color: red" v-if="checkDataInputEdit.show">
                {{ checkDataInputEdit.message }}
              </div>
            </a-modal>
          </div>
        </div>
      </a-layout-content>
    </a-layout>
  </div>
</template>
<script>
import ViewWorkEmployee from "@/service/viewWorkEmployeeService.js";
export default {
  name: "ViewWorkEmployee",
  components: {},
  data() {
    return {
      loadingEdit: false,
      pagination: {
        current: 1,
        pageSize: 10,
        total: 0,
      },
      dataSearch: {
        status: "",
        pageIndex: 1,
        pageSize: 10,
        total: 0,
      },
      dataSubmit: {
        id: "",
        name: "",
        idContact: "",
        idProduct: "",
        dateStart: "",
        dateEnd: "",
        idEmployees: [],
      },
      dataSourceTable: [],
      dataSourceTableView: [],
      columns: [
        {
          title: "STT",
          width: 100,
          dataIndex: "id",
          key: "id",
          fixed: "left",
        },
        {
          title: "Lệnh sản xuất",
          dataIndex: "nameProductionOrder",
          key: "nameProductionOrder",
          width: 150,
        },
        {
          title: "Sản phẩm",
          dataIndex: "nameProduct",
          key: "nameProduct",
          width: 150,
        },
        {
          title: "Thông số",
          dataIndex: "frame",
          key: "frame",
          width: 150,
        },
        {
          title: "Số lượng",
          dataIndex: "countProduct",
          key: "countProduct",
          width: 150,
        },
        {
          title: "Tiến độ",
          dataIndex: "numberFinish",
          key: "numberFinish",
          width: 150,
        },
        {
          title: "Ngày bắt đầu",
          dataIndex: "dateStart",
          key: "dateStart",
          width: 150,
          scopedSlots: { customRender: "dateStart" },
        },
        {
          title: "Ngày hoàn thành",
          dataIndex: "dateEnd",
          key: "dateEnd",
          width: 150,
          scopedSlots: { customRender: "dateEnd" },
        },
        {
          title: "Trạng thái",
          dataIndex: "status",
          key: "status",
          width: 150,
          scopedSlots: { customRender: "status" },
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
      columnsView: [
        {
          title: "STT",
          width: 100,
          dataIndex: "idMaterial",
          key: "idMaterial",
          fixed: "left",
        },
        {
          title: "Mã vật liệu",
          dataIndex: "nameMaterial",
          key: "nameMaterial",
          width: 150,
        },
        {
          title: "Thông số",
          dataIndex: "frameMaterial",
          key: "frameMaterial",
          width: 150,
        },
        {
          title: "Đơn vị đo",
          dataIndex: "unitMaterial",
          key: "unitMaterial",
          width: 150,
        },
        {
          title: "Số lượng",
          dataIndex: "count",
          key: "count",
          width: 150,
        },
        {
          title: "Tên nhóm vật liệu",
          dataIndex: "nameGroupMaterial",
          key: "nameGroupMaterial",
          width: 150,
        },
        {
          title: "Tên công ty",
          dataIndex: "nameCompany",
          key: "nameCompany",
          width: 150,
        },
        {
          title: "Ghi chú",
          dataIndex: "note",
          key: "note",
          width: 150,
        },
      ],
      showModalView: false,
      showModalEdit: false,
      dataEdit: {
        id: "",
        number: "",
      },
      maxNumber: "0",
      nameProductEdit: "",
      checkDataInputEdit: {
        show: false,
        message: "",
      },
    };
  },
  created() {
    this.beforeSearch();
  },

  watch: {
    urlState(newValue) {
      if (newValue.indexOf("/viewwork") != -1) {
        this.search();
      }
    },
  },
  computed: {
    urlState() {
      return this.$store.state.url;
    },
  },
  methods: {
    //edit
    showEditForm(record) {
      this.checkDataInputEdit.show = false;
      this.checkDataInputEdit.message = "";
      this.dataEdit.id = record.id;
      this.maxNumber = record.countProduct;
      this.nameProductEdit = record.nameProduct;
      let number = record.numberFinish.split("/");
      this.dataEdit.number = number[0];
      this.showModalEdit = true;
    },
    editNumberProduct() {
      this.loadingEdit = true;
      ViewWorkEmployee.updateWorkEmployee(this.dataEdit)
        .then((response) => {
          let task = response.data.data ? "success" : "error";
          let text = response.data.data
            ? "Chỉnh sửa khối lượng công việc thành công"
            : "Chỉnh sửa khối lượng công việc không thành công";
          let description = response.data.data
            ? "Chỉnh sửa khối lượng công việc thành công:  " +
              this.nameProductEdit
            : "Chỉnh sửa khối lượng công việc không thành công: " +
              this.nameProductEdit;
          this.notifi(task, text, description);
          this.showModalEdit = false;
          this.loadingEdit = false;
          this.beforeSearch();
        })
        .catch((e) => {
          console.log(e);
          this.showModalEdit = false;
          this.loadingEdit = false;
        });
    },
    checkFormEdit() {
      let check = true;
      if (this.dataEdit.number != null && this.dataEdit.number != "") {
        this.checkDataInputEdit.show = false;
        this.checkDataInputEdit.message = "";
      } else {
        check = false;
        this.checkDataInputEdit.show = true;
        this.checkDataInputEdit.message = "Bạn phải điền số tiền tạm ứng";
      }
      if (check) {
        this.editNumberProduct();
      }
    },
    inputEdit() {
      if (this.dataEdit.number != null && this.dataEdit.number != "") {
        this.checkDataInputEdit.show = false;
        this.checkDataInputEdit.message = "";
      } else {
        this.checkDataInputEdit.show = true;
        this.checkDataInputEdit.message = "Bạn phải điền số tiền tạm ứng";
      }
    },
    //edit
    showModelView(record) {
      ViewWorkEmployee.searchMaterialInWork(record.id)
        .then((response) => {
          this.dataSourceTableView = response.data.data;
          this.showModalView = true;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    handleCancelview() {
      this.showModalView = false;
    },
    handleCancelEdit() {
      this.showModalEdit = false;
    },
    changeSearch() {
      this.beforeSearch();
    },
    handleTableChange(pagination) {
      this.dataSearch.pageIndex = pagination.current;
      this.pagination = pagination;
      this.search();
    },
    beforeSearch() {
      this.dataSearch.pageIndex = 1;
      this.pagination.current = 1;
      this.search();
    },
    confirmWork(record) {
      ViewWorkEmployee.confirmWork(record.id)
        .then((response) => {
          let task = response.data.data ? "success" : "error";
          let text = response.data.data
            ? "Xác nhận công việc thành công"
            : "Xác nhận công việc không thành công";
          let description = response.data.data
            ? "Xác nhận công việc thành công:  " + record.id
            : "Xác nhận công việc không thành công: " + record.id;
          this.notifi(task, text, description);
          this.beforeSearch();
        })
        .catch((e) => {
          console.log(e);
        });
    },
    search() {
      ViewWorkEmployee.searchWorkEmployee(this.dataSearch)
        .then((response) => {
          this.dataSourceTable = response.data.data;
          this.dataSearch.total = response.data.total;
          this.pagination.total = response.data.total;
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
#edit {
  background-color: rgb(10, 208, 243);
  color: white;
}
#edit:hover {
  background-color: rgb(0, 181, 253);
  color: white;
}
#view {
  background-color: rgb(76, 238, 12);
  color: white;
}
#view:hover {
  background-color: rgb(42, 253, 0);
  color: white;
}
</style>