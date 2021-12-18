<template>
  <div class="materialsuggest">
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
          Số lượng: 
          <a-input
            placeholder="Số lượng"
            v-model="dataSearch.count"
            :style="{ width: '150px', 'margin-right': '5px' }"
          />

          <a-select @change="searchSuggestMaterial()" default-value="thang" v-model="dataSearch.type" style="width: 120px">
            <a-select-option value="thang"> Tháng </a-select-option>
            <a-select-option value="quy"> Quý </a-select-option>
            <a-select-option value="nam"> Năm </a-select-option>
          </a-select>

          <a-button type="primary" @click="searchSuggestMaterial()" :style="{ 'margin-left': '5px' }">
            <font-awesome-icon
              :icon="['fas', 'search']"
              :style="{ 'margin-right': '5px' }"
            />
            Tìm kiếm
          </a-button>
          <!-- menu trên -->

          <!-- table content -->
          <div :style="{ 'padding-top': '10px' }">
            <a-table
              :columns="columns"
              :data-source="dataSourceTable"
              :scroll="{ x: 1000 }"
              :rowKey="
                (record, index) => {
                  return index;
                }
              "
            >
            </a-table>
          </div>
          <!-- table content -->
        </div>
      </a-layout-content>
    </a-layout>
  </div>
</template>

<script>
import materialSuggestService from "@/service/materialSuggestService";
export default {
  name: "MaterialSuggest",
  components: {
  },
  data() {
    return {
      dataSourceTable: [],
      dataSearch: {
        count: 10,
        type: "thang",
      },
      columns: [
                {
          title: "STT",
          dataIndex: "idMaterial",
          key: "idMaterial",
          width: 100,
          fixed:"left"
        },
        {
          title: "Mã vật tư",
          dataIndex: "nameMaterial",
          key: "nameMaterial",
          width: 150,
        },
        {
          title: "Nhóm vật tư",
          dataIndex: "groupMaterial",
          key: "groupMaterial",
          width: 150,
        },
        {
          title: "Công ty",
          dataIndex: "company",
          key: "company",
          width: 150,
        },
        {
          title: "Tỉ lệ lựa chọn",
          dataIndex: "percentChoose",
          key: "percentChoose",
          width: 150,
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
        .searchSuggestMaterial(this.dataSearch)
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
</style>