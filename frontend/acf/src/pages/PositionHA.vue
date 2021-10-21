<template>
  <div class="PositionHA">
    <a-layout>
      <Header />
      <a-layout-content :style="{ margin: '24px 16px 0' }">
        <div
          :style="{
            padding: '24px',
            background: '#fff',
            minHeight: '360px',
            height: '560px',
          }"
        >
          <!-- menu trên -->
          <a-row type="flex">
            <a-col flex="auto">
              <a-input placeholder="Tên chức vụ" style="width: 150px" />
              <a-input placeholder="Mã code" style="width: 150px" />
              <a-range-picker
                :placeholder="['Ngày bắt đầu', 'Ngày kết thúc', 'Chọn', 'Lưu']"
                :show-time="{ format: 'DD/MM/YYYY' }"
                format="DD/MM/YYYY"
              />
              <a-button type="primary" icon="search"> Tìm kiếm </a-button>
            </a-col>
            <a-col flex="100px">
              <a-button type="primary" icon="user-add" @click="showModalAdd">
                Thêm
              </a-button>
            </a-col>
          </a-row>

          <!-- popup add-->
          <a-modal v-model="visibleAdd" title="Thêm nhân viên">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary" @click="submitAdd">
                Lưu
              </a-button>
            </template>
            <a-form-model>
              <a-form-model-item label="Mã Code">
                <a-input />
              </a-form-model-item>
              <a-form-model-item label="Tên chức vụ">
                <a-input />
              </a-form-model-item>
            </a-form-model>
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
  name: "PositionHA",
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
    showModalAdd() {
      this.visibleAdd = true;
    },
    handleCancel() {
      this.visibleAdd = false;
    },
    submitAdd() {
      this.visibleAdd = false;
    },
  },
};
</script>

<style>
</style>