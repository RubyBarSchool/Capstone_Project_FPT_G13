<template>
  <div class="acceptxinnghi">
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
            Xem đơn
          </a-button>
          <!-- menu trên -->

          <!-- table content -->

          <!-- table content -->

          <!-- popup view -->
          <a-modal v-model="visibleView" title="Xét đơn xin nghỉ">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button type="danger"> Loại bỏ </a-button>
              <a-button type="primary"> Chấp nhận </a-button>
            </template>
            <div class="container">
              <a-form-model>
                <a-form-model-item label="Tiêu đề">
                  <a-input disabled />
                </a-form-model-item>
                <a-form-model-item label="Chọn ngày">
                  <a-date-picker placeholder="Chọn ngày" disabled />
                </a-form-model-item>
                <a-form-model-item label="Nội dung">
                  <a-textarea
                    placeholder="Viết nội dung....."
                    :rows="4"
                    disabled
                  />
                </a-form-model-item>
                <a-form-model-item label="Ghi chú">
                  <a-textarea placeholder="Viết ghi chú....." :rows="4" />
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
  name: "AcceptXinNghi",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      visibleView: false,
    };
  },
  methods: {
    handleCancel() {
      this.visibleView = false;
    },

    showModalAdd() {
      this.visibleView = true;
    },
  },
};
</script>

<style>
</style>