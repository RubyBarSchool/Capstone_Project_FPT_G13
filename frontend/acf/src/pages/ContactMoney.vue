<template>
  <div class="taohopdong">
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
            placeholder="Tên hợp đồng"
            :style="{ width: '150px', 'margin-right': '5px' }"
            v-model="dataSearch.nameContact"
          />
          <a-select
            v-model="dataSearch.statusDone"
            @change="beforeSearch"
            style="width: 10%"
          >
            <a-select-option key=""> Tất Cả </a-select-option>
            <a-select-option key="-2"> Mới tạo </a-select-option>
            <a-select-option key="-1"> Đang Thực hiện </a-select-option>
            <a-select-option key="0"> Chờ bàn giao </a-select-option>
            <a-select-option key="1"> Đã bàn giao </a-select-option>
          </a-select>
          <a-button
            type="primary"
            @click="beforeSearch"
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
            @click="showModalAddF"
            :style="{ 'margin-left': '5px' }"
          >
            <font-awesome-icon
              :icon="['fas', 'file-signature']"
              :style="{ 'margin-right': '5px' }"
            />
            Thêm
          </a-button>
          <!-- table content -->
          <div :style="{ 'padding-top': '10px' }">
            <a-table
              :columns="columns"
              :data-source="dataSourceTable"
              :pagination="pagination"
              :scroll="{ x: 1500 }"
              :rowKey="
                (record, index) => {
                  return index;
                }
              "
              @change="handleTableChange"
            >
              <template slot="statusDone" slot-scope="text, record">
                <a-tag
                  :color="
                    record.statusDone == -2
                      ? '#5DDFDF'
                      : record.statusDone == -1
                      ? '#DF5B5B'
                      : record.statusDone == -0
                      ? '#DFDF5D'
                      : '#5D5DDF'
                  "
                >
                  {{
                    record.statusDone == -2
                      ? "Mới tạo"
                      : record.statusDone == -1
                      ? "Đang thực hiện"
                      : record.statusDone == -0
                      ? "Chờ bàn giao"
                      : "Đã bàn giao"
                  }}
                </a-tag>
              </template>
              <template slot="action" slot-scope="text, record">
                <a-row>
                  <a-col :span="8">
                    <a-button id="view" @click="showModalViewF(record)">
                      <font-awesome-icon :icon="['fas', 'eye']" />
                    </a-button>
                  </a-col>

                  <a-col :span="8" v-if="record.statusDone == 0">
                    <a-popconfirm
                      title="Bạn có chắc chắn xác nhận bàn giao hợp đồng?"
                      @confirm="confirm(record)"
                    >
                      <a-button id="confirm">
                        <font-awesome-icon :icon="['fas', 'check-circle']" />
                      </a-button>
                    </a-popconfirm>
                  </a-col>
                </a-row>
              </template>
            </a-table>
          </div>
        </div>

        <!-- Thêm ứng tiền hợp đồng -->
        <a-modal v-model="showModalAdd" title="Thêm lịch sử tạm ứng">
          <template slot="footer">
            <a-button key="back" @click="handleCancelAdd"> Đóng </a-button>
            <a-button key="submit" type="primary" @click="submitCotactMoney">
              Lưu
            </a-button>
          </template>
          <a-form-model>
            <a-form-model-item label="Tên hợp đồng">
              <a-select
                v-model="dataSubmit.contact"
                placeholder="Hợp đồng"
                style="width: 80%"
              >
                <a-select-option
                  v-for="(contact, index) in dataContact"
                  :value="contact.id"
                  :key="index"
                >
                  {{ contact.name }}
                </a-select-option>
              </a-select>
            </a-form-model-item>
            <a-form-model-item label="Số tiền tạm ứng">
              <a-input-number
                v-model="dataSubmit.money"
                placeholder="Nhập số tiền tạm ứng"
                style="width: 80%"
                :min="100000"
              />
            </a-form-model-item>
          </a-form-model>
        </a-modal>

        <!-- Xem chi tiết ứng tiền hợp đồng -->
        <a-modal
          v-model="showModalView"
          width="80%"
          title="Thêm lịch sử tạm ứng"
        >
          <template slot="footer">
            <a-button key="back" @click="handleCancelView"> Đóng </a-button>
          </template>
          <a-table
            :columns="columnsTableView"
            :data-source="dataSourceTableView"
            :pagination="false"
            :scroll="{ x: 1500 }"
            :rowKey="
              (record, index) => {
                return index;
              }
            "
          >
            <template slot="action" slot-scope="text, record">
              <a-col :span="8" v-if="disableEditAndDelete">
                <a-button
                  id="edit"
                  @click="showEditForm(record)"
                  :style="{ width: '44.25px' }"
                >
                  <font-awesome-icon :icon="['fas', 'edit']" />
                </a-button>
              </a-col>
              <a-col :span="8" v-if="disableEditAndDelete">
                <a-popconfirm
                  title="Bạn có chắc chắn muốn xóa không?"
                  @confirm="deleteProductionOrder(record)"
                >
                  <a-button id="delete">
                    <font-awesome-icon :icon="['fas', 'trash']" />
                  </a-button>
                </a-popconfirm>
              </a-col>
            </template>
          </a-table>
        </a-modal>

        <!-- Chỉnh sửa số tiền tạm ứng -->
        <a-modal v-model="showModalEdit" title="Sửa tiền tạm ứng">
          <template slot="footer">
            <a-button key="back" @click="handleCancelEdit"> Đóng </a-button>
            <a-button
              key="submit"
              type="primary"
              @click="submitEditCotactMoney"
            >
              Lưu
            </a-button>
          </template>
          <a-form-model>
            <a-form-model-item label="Số tiền tạm ứng">
              <a-input-number
                v-model="dataSubmit.money"
                placeholder="Nhập số tiền tạm ứng"
                style="width: 80%"
                :min="100000"
              />
            </a-form-model-item>
          </a-form-model>
        </a-modal>
      </a-layout-content>
      <Footer />
    </a-layout>
  </div>
</template>

<script>
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";
import contactMoneyService from "@/service/contactMoneyService";
import viewDetailContactService from "@/service/viewDetailContactService";

export default {
  name: "ContatcMoney",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      dataSubmit: {
        contact: "",
        money: "",
      },
      pagination: {
        current: 1,
        pageSize: 10,
        total: 0,
      },
      dataSearch: {
        nameContact: "",
        statusDone: "",
        pageIndex: 1,
        pageSize: 10,
        total: 0,
      },
      columns: [
        {
          title: "Tên hợp đồng",
          dataIndex: "nameContact",
          key: "nameContact",
          width: 250,
          fixed: "left",
        },
        {
          title: "Ngày tạo hợp đồng",
          dataIndex: "dateStart",
          key: "dateStart",
          width: 150,
        },
        {
          title: "Ngày bàn giao",
          dataIndex: "dateEnd",
          key: "dateEnd",
          width: 150,
        },
        {
          title: "Tiền tạm ứng",
          dataIndex: "moneyAdvance",
          key: "moneyAdvance",
          width: 150,
        },
        {
          title: "Tổng giá trị",
          dataIndex: "moneyContact",
          key: "moneyContact",
          width: 150,
        },
        {
          title: "Định mức hoàn thành",
          dataIndex: "numberFinish",
          key: "numberFinish",
          width: 150,
        },
        {
          title: "Trạng thái",
          dataIndex: "statusDone",
          key: "statusDone",
          width: 150,
          scopedSlots: { customRender: "statusDone" },
        },
        {
          title: "Hành động",
          key: "operation",
          width: 150,
          scopedSlots: { customRender: "action" },
        },
      ],
      dataSourceTableView: [],
      columnsTableView: [
        {
          title: "STT",
          dataIndex: "id",
          key: "id",
          width: 150,
          fixed: "left",
        },
        {
          title: "Ngày ứng tiền",
          dataIndex: "date",
          key: "date",
          width: 150,
        },
        {
          title: "Số tiền tạm ứng",
          dataIndex: "money",
          key: "money",
          width: 150,
        },
        {
          title: "Hành động",
          key: "operation",
          width: 150,
          scopedSlots: { customRender: "action" },
        },
      ],
      dataSourceTable: [],
      showModalAdd: false,
      dataContact: [],
      showModalView: false,
      showModalEdit: false,
      disableEditAndDelete: true,
    };
  },
  created() {
    this.beforeSearch();
    this.getDataContact();
  },
  methods: {
    confirm(record) {
      let data = {
        contact: record.idContact,
        money: 0,
      };
      contactMoneyService
        .confirmContactMoney(data)
        .then(() => {
          this.showModalView = false;
          this.showModalEdit = false;
          this.beforeSearch();
        })
        .catch((e) => {
          console.log(e);
        });
    },
    submitEditCotactMoney() {
      contactMoneyService
        .updateContactMoney(this.dataSubmit)
        .then(() => {
          this.showModalView = false;
          this.showModalEdit = false;
          this.beforeSearch();
        })
        .catch((e) => {
          console.log(e);
        });
    },
    handleCancelEdit() {
      this.showModalEdit = false;
    },
    showEditForm(record) {
      this.dataSubmit.contact = record.id;
      this.dataSubmit.money = record.money;
      this.showModalEdit = true;
    },
    deleteProductionOrder(record) {
      contactMoneyService
        .deleteContactMoney(record.id)
        .then(() => {
          this.showModalView = false;
          this.beforeSearch();
        })
        .catch((e) => {
          console.log(e);
        });
    },
    handleCancelView() {
      this.showModalView = false;
    },
    showModalViewF(record) {
      this.dataSourceTableView = record.contactMoneyDetail;
      this.showModalView = true;
      if (record.statusDone == 1) {
        this.disableEditAndDelete = false;
      }
    },
    showModalAddF() {
      this.getDataContact();
      this.showModalAdd = true;
    },
    getDataContact() {
      viewDetailContactService
        .searchContactInAdd()
        .then((response) => {
          this.dataContact = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    submitCotactMoney() {
      contactMoneyService
        .addContactMoney(this.dataSubmit)
        .then(() => {
          this.beforeSearch();
          this.showModalAdd = false;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    handleCancelAdd() {
      this.showModalAdd = false;
    },
    handleTableChange(pagination) {
      this.dataSearch.pageIndex = pagination.current;
      this.pagination = pagination;
      this.search();
    },
    search() {
      contactMoneyService
        .searchContactMoney(this.dataSearch)
        .then((response) => {
          this.dataSourceTable = response.data.data;
          this.dataSearch.total = response.data.total;
          this.pagination.total = response.data.total;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    beforeSearch() {
      this.dataSearch.pageIndex = 1;
      this.dataSearch.total = 0;
      this.pagination.current = 1;
      this.search();
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