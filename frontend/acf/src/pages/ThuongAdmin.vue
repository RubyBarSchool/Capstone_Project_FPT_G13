<template>
  <div class="thuongadmin">
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
          />
          <a-input
            placeholder="Trạng thái"
            :style="{ 'margin-right': '5px', width: '200px' }"
          />
          <a-range-picker
            :placeholder="['Ngày bắt đầu', 'Ngày kết thúc']"
            :show-time="{ format: 'DD/MM/YYYY' }"
            format="DD/MM/YYYY"
          />
          <a-button
            type="primary"
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
            :style="{ 'margin-left': '5px' }"
          >
            <font-awesome-icon
              :icon="['fas', 'user-plus']"
              :style="{ 'margin-right': '5px' }"
            />
            Thêm
          </a-button>
          <!-- menu trên -->    
        </div>
      </a-layout-content>
      <Footer />
    </a-layout>
  </div>
</template>

<script>
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";
export default {
  name: "ThuongAdmin",
  components: {
    Header,
    Footer,
  },
  data() {
    return {};
  },
};
</script>

<style>
</style>