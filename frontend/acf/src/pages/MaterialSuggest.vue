<template>
  <div class="materialsuggest">
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
              <template slot="type" slot-scope="text, record">
                {{ record.height }}
              </template>
              <template slot="code" slot-scope="text, record">
                {{ record.code }}
              </template>
              <template slot="frame" slot-scope="text, record">
                {{ record.frame }}
              </template>
              <template slot="unit" slot-scope="text, record">
                {{ record.unit }}
              </template>
              <template slot="company" slot-scope="text, record">
                {{ record.company }}
              </template>
              <template slot="percent" slot-scope="text, record">
                {{ record.percent }}
              </template>
            </a-table>
          </div>
          <!-- table content -->
        </div>
      </a-layout-content>
      <Footer />
    </a-layout>
  </div>
</template>

<script>
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";
import materialSuggestService from "@/service/materialSuggestService";
export default {
  name: "MaterialSuggest",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      dataSourceTable: [],
      dataMaterialSuggest: {
        count: 0,
        type: "",
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
          title: "Nhóm tấm phủ",
          dataIndex: "type",
          key: "type",
          width: 150,
          scopedSlots: { customRender: "type" },
        },
        {
          title: "Mã tấm phủ",
          dataIndex: "code",
          key: "code",
          width: 150,
          scopedSlots: { customRender: "code" },
        },
        {
          title: "Khung",
          dataIndex: "frame",
          key: "frame",
          width: 150,
          scopedSlots: { customRender: "frame" },
        },
        {
          title: "Đơn vị đo",
          dataIndex: "unit",
          key: "unit",
          width: 150,
          scopedSlots: { customRender: "unit" },
        },
        {
          title: "Công ty",
          dataIndex: "company",
          key: "company",
          width: 150,
          scopedSlots: { customRender: "company" },
        },
        {
          title: "Tỉ lệ lựa chọn",
          dataIndex: "count",
          key: "count",
          width: 150,
          scopedSlots: { customRender: "count" },
        },
      ],
    };
  },
  created() {
    this.searchSuggestMaterial();
  },
  methods: {
    searchSuggestMaterial() {
      materialSuggestService
        .searchSuggestMaterial()
        .then((response) => {
          this.dataSourceTable = response.data.data;
        })
        .catch((e) => {
          console.log(e);
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
</style>