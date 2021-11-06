<template>
  <div class="ungluong">
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
          <!-- menu trên -->
          <a-input
            placeholder="Tiêu đề"
            style="width: 150px"
            v-model="dataSearch.title"
          />
          <a-select
            placeholder="Trạng thái"
            v-model="dataSearch.status"
            style="width: 150px"
          >
            <a-select-option value="-1"> Chờ duyệt </a-select-option>
            <a-select-option value="0"> Hủy bỏ </a-select-option>
            <a-select-option value="1"> Đã duyệt </a-select-option>
          </a-select>
          <a-range-picker
            v-model="dataSearch.date"
            :placeholder="['Ngày bắt đầu', 'Ngày kết thúc']"
            :show-time="{ format: 'DD/MM/YYYY' }"
            format="DD/MM/YYYY"
          />
          <a-button
            type="primary"
            @click="submitSearch"
            :style="{ 'margin-left': '5px' }"
          >
            <font-awesome-icon
              :icon="['fas', 'search']"
              :style="{ 'margin-right': '5px' }"
            />
            Tìm kiếm
          </a-button>
          <a-button
            type="primary"
            @click="showModalAdd"
            :style="{ 'margin-left': '5px' }"
          >
            <font-awesome-icon
              :icon="['fas', 'envelope-open-text']"
              :style="{ 'margin-right': '5px' }"
            />
            Viết đơn
          </a-button>

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
              <template slot="titlee" slot-scope="text, record">
                {{ record.title }}
              </template>
              <template slot="date" slot-scope="text, record">
                {{ record.date }}
              </template>
              <template slot="advanceSalary" slot-scope="text, record">
                {{ record.advanceSalary }}
              </template>
              <template slot="status" slot-scope="text, record">
                <a-tag
                  :color="
                    record.status == '-1'
                      ? 'red'
                      : record.status == '0'
                      ? 'gray'
                      : 'green'
                  "
                >
                  {{
                    record.status == "-1"
                      ? "Hủy bỏ"
                      : record.status == "0"
                      ? "Chờ duyệt"
                      : "Đã duyệt"
                  }}
                </a-tag>
              </template>
              <template slot="action" slot-scope="text, record">
                <a-row>
                  <a-col :span="8">
                    <a-button
                      id="view"
                      @click="getDetailAdvanceSalaryEmployeeByID(record.id)"
                      :style="{ width: '44.25px', 'margin-right': '100px' }"
                    >
                      <font-awesome-icon :icon="['fas', 'eye']" />
                    </a-button>
                  </a-col>
                  <a-col :span="8">
                    <a-button
                      id="edit"
                      @click="
                        showModalEdit(
                          record.id,
                          record.title,
                          record.content,
                          record.advanceSalary
                        )
                      "
                      :style="{ width: '44.25px' }"
                    >
                      <font-awesome-icon :icon="['fas', 'edit']" />
                    </a-button>
                  </a-col>
                  <a-col :span="8">
                    <a-popconfirm
                      v-if="dataSourceTable.length"
                      title="Bạn có chắc chắn muốn xóa không?"
                      @confirm="deleteAdvanceSalary(record.id)"
                    >
                      <a-button id="delete">
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
          <a-modal v-model="visibleAdd" title="Viết đơn">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary" @click="submitAdd">
                Lưu
              </a-button>
            </template>
            <a-form-model>
              <a-form-model-item label="Tiêu đề">
                <a-input v-model="dataAdd.title" />
              </a-form-model-item>
              <a-form-model-item label="Số tiền">
                <a-input v-model="dataAdd.advanceSalary" />
              </a-form-model-item>

              <a-form-model-item label="Nội dung">
                <a-textarea
                  v-model="dataAdd.content"
                  placeholder="Lý do như nào thì viết vào đây"
                  :row="4"
                />
              </a-form-model-item>
            </a-form-model>
          </a-modal>
          <!-- popup add -->

          <!-- popup edit-->
          <a-modal v-model="visibleEdit" title="Chỉnh sửa đơn">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary" @click="submitUpdate">
                Lưu
              </a-button>
            </template>
            <a-form-model>
              <a-form-model-item label="Tiêu đề">
                <a-input v-model="dataEdit.title" />
              </a-form-model-item>
              <a-form-model-item label="Số tiền">
                <a-input v-model="dataEdit.advanceSalary" />
              </a-form-model-item>
              <a-form-model-item label="Nội dung">
                <a-textarea
                  v-model="dataEdit.content"
                  placeholder="Lý do như nào thì viết vào đây"
                  :row="4"
                />
              </a-form-model-item>
            </a-form-model>
          </a-modal>
          <!-- popup edit-->

          <!-- popup view-->
          <a-modal v-model="visibleView" class="view">
            <template slot="footer">
              <a-button key="a" hidden></a-button>
              <a-button key="submit" type="primary" @click="handleCancel"
                >Lưu</a-button
              >
            </template>
            <a-form-model>
              <a-form-model-item label="Tiêu đề">
                <a-input
                  v-model="dataAdvanceSalaryEmployeeDetail.title"
                  disabled
                />
              </a-form-model-item>
              <a-form-model-item label="Số tiền">
                <a-input
                  v-model="dataAdvanceSalaryEmployeeDetail.advanceSalary"
                  disabled
                />
              </a-form-model-item>
              <a-form-model-item label="Nội dung">
                <a-textarea
                  v-model="dataAdvanceSalaryEmployeeDetail.content"
                  :row="4"
                  disabled
                />
              </a-form-model-item>
            </a-form-model>
          </a-modal>
          <!-- popup view-->
        </div>
      </a-layout-content>
      <Footer />
    </a-layout>
  </div>
</template>
 <script>
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";
import ungLuongService from "../service/ungLuongService";

export default {
  name: "UngLuong",
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
        content: "",
        date: [],
        pageIndex: 1,
        pageSize: 10,
        status: "",
        title: "",
        total: 0,
      },
      dataSourceTable: [],
      dataAdd: {
        advanceSalary: "",
        content: "",
        title: "",
      },
      dataEdit: {
        advanceSalary: "",
        content: "",
        id: 0,
        title: "",
      },
      dataAdvanceSalaryEmployeeDetail: {
        id: "",
        title: "",
        content: "",
        advanceSalary: "",
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
          title: "Tiêu đề",
          dataIndex: "titlee",
          key: "titlee",
          width: 150,
          scopedSlots: { customRender: "titlee" },
        },
        {
          title: "Ngày tạo",
          dataIndex: "date",
          key: "date",
          width: 150,
          scopedSlots: { customRender: "date" },
        },
        {
          title: "Số tiền",
          dataIndex: "advanceSalary",
          key: "advanceSalary",
          width: 150,
          scopedSlots: { customRender: "advanceSalary" },
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
      visibleAdd: false,
      visibleEdit: false,
      visibleView: false,
    };
  },
  created() {
    this.submitSearch();
  },
  methods: {
    handleTableChange(pagination) {
      this.dataSearch.pageIndex = pagination.current;
      this.pagination = pagination;
      ungLuongService
        .searchAdvanceSalaryEmployee(this.dataSearch)
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
      ungLuongService
        .searchAdvanceSalaryEmployee(this.dataSearch)
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
      ungLuongService
        .addAdvanceSalaryEmployee(this.dataAdd)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Viết đơn";
            let description =
              "Thêm mới đơn " + this.dataAdd.title + " thành công !!";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Viết đơn";
            let description =
              "Thêm mới đơn " +
              this.dataAdd.title +
              " không thành công vì " +
              response.data.message;
            this.notifi(type, message, description);
          }
        })
        .catch((e) => {
          console.log(e);
        });
      this.visibleAdd = false;
      this.dataAdd.title = "";
      this.dataAdd.advanceSalary = "";
      this.dataAdd.content = "";
    },

    handleCancel() {
      this.visibleAdd = false;
      this.visibleEdit = false;
      this.visibleView = false;
    },
    showModalEdit(id, title, content, advanceSalary) {
      this.dataEdit.id = id;
      this.dataEdit.title = title;
      this.dataEdit.content = content;
      this.dataEdit.advanceSalary = advanceSalary;
      this.visibleEdit = true;
    },
    submitUpdate() {
      ungLuongService
        .updateAdvanceSalaryEmployee(this.dataEdit)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Cập nhật";
            let description = "Cập nhật đơn thành công";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Cập nhật";
            let description = "Cập nhật đơn không thành công";
            this.notifi(type, message, description);
          }
        })
        .catch((e) => {
          console.log(e);
        });
      this.visibleEdit = false;
    },

    deleteAdvanceSalary(id) {
      ungLuongService
        .deleteAdvanceSalaryEmployee(id)
        .then((response) => {
          if (response.data.data) {
            let type = "success";
            let message = "Xóa";
            let description = "Xóa đơn thành công";
            this.notifi(type, message, description);
            this.submitSearch();
          } else {
            let type = "error";
            let message = "Xóa";
            let description = "Xóa đơn không thành công :(";
            this.notifi(type, message, description);
            this.submitSearch();
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
    getDetailAdvanceSalaryEmployeeByID(id) {
      ungLuongService
        .getAdvanceSalaryEmployeeByID(id)
        .then((response) => {
          this.dataAdvanceSalaryEmployeeDetail = response.data.data;
          this.visibleView = true;
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
#view {
  background-color: rgb(76, 238, 12);
  color: white;
}
#view:hover {
  background-color: rgb(42, 253, 0);
  color: white;
}
</style>