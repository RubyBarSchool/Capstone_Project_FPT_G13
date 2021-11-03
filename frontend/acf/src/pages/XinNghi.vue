<template>
  <div class="xinnghi">
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
          <a-input
            placeholder="Tiêu đề"
            :style="{ 'margin-right': '5px', width: '200px' }"
            v-model="dataSearch.title"
          />
          <a-input
            placeholder="Trạng thái"
            :style="{ 'margin-right': '5px', width: '200px' }"
            v-model="dataSearch.status"
          />
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
              <template slot="date" slot-scope="text, record">
                {{ record.date }}
              </template>
              <template slot="status" slot-scope="text, record">
                <a-tag :color="record.accept ? 'green' : 'gray'">
                  {{ record.accept ? "Đã duyệt" : "Chờ duyệt" }}
                </a-tag>
              </template>
              <template slot="titlee" slot-scope="text, record">
                {{ record.title }}
              </template>
              <template slot="content" slot-scope="text, record">
                {{ record.content }}
              </template>
              <template slot="comment" slot-scope="text, record">
                {{ record.comment }}
              </template>
              <template slot="action" slot-scope="text, record">
                <a-row>
                  <a-col :span="8">
                    <a-button
                      id="edit"
                      @click="
                        showModalEdit(
                          record.content,
                          record.date,
                          record.fileAttach,
                          record.idApplication,
                          record.title
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
                      @confirm="deletePersonalApplication(record.idApplication)"
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

          <!-- popup add -->
          <a-modal v-model="visibleAdd" title="Đơn xin nghỉ">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary" @click="submitAdd">
                Lưu
              </a-button>
            </template>
            <div class="container">
              <a-form-model>
                <a-form-model-item label="Tiêu đề">
                  <a-input v-model="dataAdd.title" />
                </a-form-model-item>
                <a-form-model-item label="Chọn ngày">
                  <a-range-picker
                    :placeholder="['Ngày bắt đầu', 'Ngày kết thúc']"
                    v-model="dataAdd.date"
                    :show-time="{ format: 'DD/MM/YYYY' }"
                    format="DD/MM/YYYY"
                  />
                </a-form-model-item>
                <a-form-model-item label="Nội dung">
                  <a-textarea
                    placeholder="Viết nội dung"
                    :rows="4"
                    v-model="dataAdd.content"
                  />
                </a-form-model-item>
              </a-form-model>
            </div>
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
           <div class="container">
              <a-form-model>
                <a-form-model-item label="Tiêu đề">
                  <a-input v-model="dataEdit.title" />
                </a-form-model-item>
                <a-form-model-item label="Chọn ngày">
                  <a-range-picker
                    :placeholder="['Ngày bắt đầu', 'Ngày kết thúc']"
                    v-model="dataEdit.date"
                    :show-time="{ format: 'DD/MM/YYYY' }"
                    format="DD/MM/YYYY"
                  />
                </a-form-model-item>
                <a-form-model-item label="Nội dung">
                  <a-textarea
                    placeholder="Viết nội dung"
                    :rows="4"
                    v-model="dataEdit.content"
                  />
                </a-form-model-item>
              </a-form-model>
            </div>
          </a-modal>
          <!-- popup edit-->

        </div>
      </a-layout-content>
      <Footer />
    </a-layout>
  </div>
</template>

<script>
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";
import xinNghiService from "../service/xinNghiService";
export default {
  name: "XinNghi",
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
        date: [],
        pageIndex: 1,
        pageSize: 10,
        status: "",
        title: "",
        total: 0,
      },
      dataSourceTable: [],
      dataAdd: {
        date: ["", ""],
        content: "",
        title: "",
        fileAttach: "",
      },
      dataEdit: {
        content: "",
        date: ["", ""],
        fileAttach: "",
        idApplication: 0,
        title: "",
      },
      columns: [
        {
          title: "ID",
          width: 100,
          dataIndex: "idApplication",
          key: "idApplication",
          fixed: "left",
        },
        {
          title: "Ngày",
          dataIndex: "date",
          key: "date",
          width: 150,
          scopedSlots: { customRender: "date" },
        },
        {
          title: "Trạng thái",
          dataIndex: "status",
          key: "status",
          width: 150,
          scopedSlots: { customRender: "status" },
        },
        {
          title: "Tiêu đề",
          dataIndex: "titlee",
          key: "titlee",
          width: 150,
          scopedSlots: { customRender: "titlee" },
        },
        {
          title: "Nội dung",
          dataIndex: "content",
          key: "content",
          width: 150,
          scopedSlots: { customRender: "content" },
        },
        {
          title: "Ghi chú",
          dataIndex: "comment",
          key: "comment",
          width: 150,
          scopedSlots: { customRender: "comment" },
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
    handleTableChange(pagination) {
      this.dataSearch.pageIndex = pagination.current;
      this.pagination = pagination;
      xinNghiService
        .searchPersonalLeaveApplication(this.dataSearch)
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
      xinNghiService
        .searchPersonalLeaveApplication(this.dataSearch)
        .then((response) => {
          this.dataSourceTable = response.data.data;
          this.dataSearch.total = response.data.total;
          this.pagination.total = response.data.total;
        })
        .catch((e) => {
          console.log(e);
        });
    },

    showModalEdit(content, date, fileAttach, idApplication, title) {
      this.dataEdit.content = content;
      this.dataEdit.date = date;
      this.dataEdit.fileAttach = fileAttach;
      this.dataEdit.idApplication = idApplication;
      this.dataEdit.title = title;
      this.visibleEdit = true;
    },
    submitUpdate() {
      xinNghiService
        .updatePersonalApplication(this.dataEdit)
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

    handleCancel() {
      this.visibleAdd = false;
      this.visibleEdit = false;
    },

    showModalAdd() {
      this.visibleAdd = true;
    },
    submitAdd() {
      xinNghiService
        .addPersonalApplication(this.dataAdd)
        .then((response) => {
          this.dataEmployees = response.data.data;
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
      this.dataAdd.date = ["", ""];
      this.dataAdd.content = "";
      this.dataAdd.title = "";
      this.dataAdd.fileAttach = "";
    },

    deletePersonalApplication(id) {
      xinNghiService
        .deletePersonalApplication(id)
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

</style>