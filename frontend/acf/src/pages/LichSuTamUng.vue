<template>
  <div class="lichsutamung">
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
          <a-button type="primary" @click="showModalAdd">
            <font-awesome-icon
              :icon="['fas', 'file-signature']"
              :style="{ 'margin-right': '5px' }"
            />
            Thêm
          </a-button>
          <!-- menu trên -->

          <!-- table content -->
          <div :style="{ 'padding-top': '10px' }">
            <a-table :columns="columns" :data-source="data">
              <a slot="action">
                <a-button id="delete">
                  <font-awesome-icon :icon="['fas', 'trash']" />
                </a-button>
              </a>
            </a-table>
          </div>
          <!-- table content -->

          <!-- popup add-->
          <a-modal v-model="visibleAdd" title="Tạm ứng">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary"> Lưu </a-button>
            </template>
            <a-form-model>
              <a-form-model-item label="Hợp đồng">
                <a-select default-value="lucy" style="width: 100%">
                  <a-select-option value="jack"> Jack </a-select-option>
                  <a-select-option value="lucy"> Lucy </a-select-option>
                  <a-select-option value="disabled" disabled>
                    Disabled
                  </a-select-option>
                  <a-select-option value="Yiminghe"> yiminghe </a-select-option>
                </a-select>
              </a-form-model-item>
              <a-form-model-item label="Tạm ứng">
                <a-input />
              </a-form-model-item>
            </a-form-model>
          </a-modal>
          <!-- popup add-->
        </div>
      </a-layout-content>
      <Footer />
    </a-layout>
  </div>
</template>

<script>
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";
const columns = [
  {
    title: "STT",
    dataIndex: "id",
    key: "id",
    fixed: "left",
  },
  { title: "Tên hợp đồng", dataIndex: "nameContract", key: "nameContract" },
  { title: "Ngày tạm ứng", dataIndex: "dateAdvance", key: "dateAdvance" },
  { title: "Số tiền tạm ứng", dataIndex: "moneyAdvance", key: "moneyAdvance" },
  { title: "Số tiền đã ứng", dataIndex: "moneyPaid", key: "moneyPaid" },
  {
    title: "Action",
    key: "operation",
    fixed: "right",
    width: 100,
    scopedSlots: { customRender: "action" },
  },
];

const data = [
  {
    key: "1",
    id: "1",
    nameContract: "Son",
    dateAdvance: "14/11/2021",
    moneyAdvance: "123456",
    moneyPaid: "10",
  },
  {
    key: "2",
    id: "2",
    nameContract: "Kane",
    dateAdvance: "14/11/2021",
    moneyAdvance: "123456",
    moneyPaid: "10",
  },
];
export default {
  name: "LichSuTamUng",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      data,
      columns,
      visibleAdd: false,
    };
  },
  created() {},
  methods: {
    onChange(date, dateString) {
      console.log(date, dateString);
    },
    showModalAdd() {
      this.visibleAdd = true;
    },
    handleCancel() {
      this.visibleAdd = false;
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