<template>
  <div class="ContatcMoney">
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
          <a-input
            placeholder="Tên hợp đồng"
            :style="{ width: '150px', 'margin-right': '5px' }"
            v-model="dataSearch.nameContact"
          />
          Trạng thái:
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
              :scroll="{ x: 1000 }"
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
                      ok-text="Đồng ý"
                      cancel-text="Đóng"
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
            <a-button
              key="submit"
              type="primary"
              :loading="loadingAdd"
              @click="checkFormAddCotactMoney"
            >
              Lưu
            </a-button>
          </template>
          <a-row type="flex">
            <a-col flex="150px">
              <span style="color: red">*</span> Tên hợp đồng</a-col
            >
            <a-col flex="auto">
              <a-select
                v-model="dataSubmit.contact"
                placeholder="Hợp đồng"
                style="width: 80%"
                @change="inputContract"
              >
                <a-select-option
                  v-for="(contact, index) in dataContact"
                  :value="contact.id"
                  :key="index"
                >
                  {{ contact.name }}
                </a-select-option>
              </a-select>
              <div style="color: red" v-if="checkDataInputContract.show">
                {{ checkDataInputContract.message }}
              </div>
            </a-col>
          </a-row>
          <br />
          <a-row type="flex">
            <a-col flex="150px">
              <span style="color: red">*</span> Số tiền tạm ứng</a-col
            >
            <a-col flex="auto">
              <a-input-number
                @change="inputMoney"
                v-model="dataSubmit.money"
                placeholder="Nhập số tiền tạm ứng"
                style="width: 80%"
                :min="100000"
              />
              <div style="color: red" v-if="checkDataInputMoney.show">
                {{ checkDataInputMoney.message }}
              </div>
            </a-col>
          </a-row>
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
            :scroll="{ x: 800 }"
            :rowKey="
              (record, index) => {
                return index;
              }
            "
          >
            <template slot="date" slot-scope="text, record">
              {{
                new Date(record.date).toLocaleDateString("en-GB", {
                  year: "numeric",
                  month: "2-digit",
                  day: "2-digit",
                })
              }}
            </template>
            <template slot="action" slot-scope="text, record">
              <a-row :gutter="[16, 16]">
                <a-col :span="12" v-if="disableEditAndDelete">
                  <a-button id="edit" @click="showEditForm(record)">
                    <font-awesome-icon :icon="['fas', 'edit']" />
                  </a-button>
                </a-col>
                <a-col :span="12" v-if="disableEditAndDelete">
                  <a-popconfirm
                    title="Bạn có chắc chắn muốn xóa không?"
                    @confirm="deleteProductionOrder(record)"
                    ok-text="Đồng ý"
                    cancel-text="Đóng"
                  >
                    <a-button id="delete">
                      <font-awesome-icon :icon="['fas', 'trash']" />
                    </a-button>
                  </a-popconfirm>
                </a-col>
              </a-row>
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
              @click="checkFormEditMoney"
              :loading="loadingEdit"
            >
              Lưu
            </a-button>
          </template>
          <a-form-model>
            <span style="color: red">*</span> Số tiền tạm ứng
            <a-input-number
              v-model="dataSubmit.money"
              placeholder="Nhập số tiền tạm ứng"
              style="width: 80%"
              :min="100000"
              @change="inputEditMoney"
            />
            <div style="color: red" v-if="checkDataInputEditMoney.show">
              {{ checkDataInputEditMoney.message }}
            </div>
          </a-form-model>
        </a-modal>
      </a-layout-content>
    </a-layout>
  </div>
</template>

<script>
import contactMoneyService from "@/service/contactMoneyService";
import viewDetailContactService from "@/service/viewDetailContactService";

export default {
  name: "ContatcMoney",
  components: {},
  data() {
    return {
      loadingEdit: false,
      loadingAdd: false,
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
          scopedSlots: { customRender: "dateStart" },
        },
        {
          title: "Ngày bàn giao",
          dataIndex: "dateEnd",
          key: "dateEnd",
          width: 150,
          scopedSlots: { customRender: "dateEnd" },
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
          title: "",
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
          width: 80,
          fixed: "left",
        },
        {
          title: "Ngày ứng tiền",
          dataIndex: "date",
          key: "date",
          width: 100,
          scopedSlots: { customRender: "date" },
        },
        {
          title: "Số tiền tạm ứng",
          dataIndex: "money",
          key: "money",
          width: 100,
        },
        {
          title: "",
          key: "operation",
          width: 80,
          scopedSlots: { customRender: "action" },
        },
      ],
      dataSourceTable: [],
      showModalAdd: false,
      dataContact: [],
      showModalView: false,
      showModalEdit: false,
      disableEditAndDelete: true,
      checkDataInputContract: {
        show: false,
        message: "",
      },
      checkDataInputMoney: {
        show: false,
        message: "",
      },
      checkDataInputEditMoney: {
        show: false,
        message: "",
      },
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

    //edit
    submitEditCotactMoney() {
      this.loadingEdit = true;
      contactMoneyService
        .updateContactMoney(this.dataSubmit)
        .then(() => {
          this.showModalView = false;
          this.showModalEdit = false;
          this.beforeSearch();
          this.loadingEdit = false;
        })
        .catch((e) => {
          console.log(e);
          this.showModalEdit = false;
          this.loadingEdit = false;
        });
    },
    inputEditMoney() {
      if (this.dataSubmit.money != null && this.dataSubmit.money != "") {
        this.checkDataInputEditMoney.show = false;
        this.checkDataInputEditMoney.message = "";
      } else {
        this.checkDataInputEditMoney.show = true;
        this.checkDataInputEditMoney.message = "Bạn phải điền số tiền tạm ứng";
      }
    },
    checkFormEditMoney() {
      let check = true;
      if (this.dataSubmit.money != null && this.dataSubmit.money != "") {
        this.checkDataInputEditMoney.show = false;
        this.checkDataInputEditMoney.message = "";
      } else {
        check = false;
        this.checkDataInputEditMoney.show = true;
        this.checkDataInputEditMoney.message = "Bạn phải điền số tiền tạm ứng";
      }
      if (check) {
        this.submitEditCotactMoney();
      }
    },

    handleCancelEdit() {
      this.showModalEdit = false;
    },
    showEditForm(record) {
      this.checkDataInputEditMoney.show = false;
      this.checkDataInputEditMoney.message = "";
      this.dataSubmit.money = "";
      this.dataSubmit.contact = record.id;
      this.dataSubmit.money = record.money;
      this.showModalEdit = true;
    },
    //edit

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
    //add contract
    showModalAddF() {
      this.checkDataInputContract.show = false;
      this.checkDataInputContract.message = "";
      this.checkDataInputMoney.show = false;
      this.checkDataInputMoney.message = "";
      this.dataSubmit.contact = [];
      this.dataSubmit.money = "";
      this.getDataContact();
      this.showModalAdd = true;
    },
    submitCotactMoney() {
      this.loadingAdd = true;
      contactMoneyService
        .addContactMoney(this.dataSubmit)
        .then(() => {
          this.beforeSearch();
          this.showModalAdd = false;
          this.loadingAdd = false;
        })
        .catch((e) => {
          console.log(e);
          this.showModalAdd = false;
          this.loadingAdd = false;
        });
    },
    inputContract() {
      if (
        this.dataSubmit.contact != null &&
        this.dataSubmit.contact.length != 0
      ) {
        this.checkDataInputContract.show = false;
        this.checkDataInputContract.message = "";
      } else {
        this.checkDataInputContract.show = true;
        this.checkDataInputContract.message = "Bạn phải chọn tên hợp đồng";
      }
    },
    inputMoney() {
      if (this.dataSubmit.money != null && this.dataSubmit.money != "") {
        this.checkDataInputMoney.show = false;
        this.checkDataInputMoney.message = "";
      } else {
        this.checkDataInputMoney.show = true;
        this.checkDataInputMoney.message = "Bạn phải điền số tiền tạm ứng";
      }
    },
    checkFormAddCotactMoney() {
      let check = true;
      if (
        this.dataSubmit.contact != null &&
        this.dataSubmit.contact.length != 0
      ) {
        this.checkDataInputContract.show = false;
        this.checkDataInputContract.message = "";
      } else {
        check = false;
        this.checkDataInputContract.show = true;
        this.checkDataInputContract.message = "Bạn phải chọn tên hợp đồng";
      }
      if (this.dataSubmit.money != null && this.dataSubmit.money != "") {
        this.checkDataInputMoney.show = false;
        this.checkDataInputMoney.message = "";
      } else {
        check = false;
        this.checkDataInputMoney.show = true;
        this.checkDataInputMoney.message = "Bạn phải điền số tiền tạm ứng";
      }
      if (check) {
        this.submitCotactMoney();
      }
    },
    //add contract
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
      this.dataSearch.nameContact = this.dataSearch.nameContact.trim();
      this.dataSearch.pageIndex = 1;
      this.dataSearch.total = 0;
      this.pagination.current = 1;
      this.search();
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