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
          />
          <a-input
            placeholder="Nội dung"
            :style="{ 'margin-right': '5px', width: '200px' }"
          />
          <a-range-picker
            :placeholder="['Ngày bắt đầu', 'Ngày kết thúc']"
            format="DD/MM/YYYY"
          />
          <a-button
            type="primary"
            :style="{ 'margin-left': '5px' }"
            @click="showModalAdd"
          >
            <font-awesome-icon
              :icon="['fas', 'user-edit']"
              :style="{ 'margin-right': '5px' }"
            />
            Viết đơn
          </a-button>
          <!-- menu trên -->

          <!-- table content -->
         
          <!-- table content -->

          <!-- popup add -->
          <a-modal v-model="visibleAdd" title="Đơn xin nghỉ">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary"> Lưu </a-button>
            </template>
            <div class="container">
              <a-form-model>
                <a-form-model-item label="Tiêu đề">
                  <a-input />
                </a-form-model-item>
                <a-form-model-item label="Chọn ngày">
                  <a-date-picker placeholder="Chọn ngày" />
                </a-form-model-item>
                <a-form-model-item label="Nội dung">
                  <a-textarea placeholder="Viết nội dung" :rows="4" />
                </a-form-model-item>
              </a-form-model>
            </div>
          </a-modal>
          <!-- popup add -->
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
  name: "XinNghi",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      visibleAdd: false,
    };
  },
  methods: {
    handleCancel() {
      this.visibleAdd = false;
    },

    showModalAdd() {
      this.visibleAdd = true;
    },
  },
};
</script>

<style>
</style>