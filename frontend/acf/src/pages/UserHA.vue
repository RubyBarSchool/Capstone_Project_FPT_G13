<template>
  <div class="UserHA">
    <a-layout :style="{ background: 'white'}">
      <Header />
      <a-layout-content :style="{ margin: '24px 16px 0' }">
        <div
          :style="{
            minHeight: '360px',
            background: 'white',
            height: '560px',
          }"
        >
          <!-- menu trên -->
          <a-row type="flex">
            <a-col flex="auto">
              <a-input placeholder="Tên nhân viên" style="width: 150px" />
              <a-input placeholder="Chức vụ" style="width: 150px" />
              <a-input placeholder="Trạng thái" style="width: 150px" />
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
            <div class="container">
              <a-form-model>
                <div class="row">
                  <div class="col">
                    <a-form-model-item label="Họ Và Tên">
                      <a-input />
                    </a-form-model-item>
                    <a-form-model-item label="Ngày Sinh">
                      <a-input />
                    </a-form-model-item>
                    <a-form-model-item label="Giới Tính">
                      <a-input />
                    </a-form-model-item>
                    <a-form-model-item label="Chức Vụ">
                      <a-input />
                    </a-form-model-item>
                    <a-form-model-item label="Số Điện Thoại">
                      <a-input />
                    </a-form-model-item>
                  </div>
                  <div class="col">
                    <a-form-model-item label="Email">
                      <a-input />
                    </a-form-model-item>
                    <a-form-model-item label="Quê Quán">
                      <a-input />
                    </a-form-model-item>
                    <a-form-model-item label="Dân Tộc">
                      <a-input />
                    </a-form-model-item>
                    <a-form-model-item label="Lương">
                      <a-input />
                    </a-form-model-item>
                    <a-form-model-item label="Ảnh">
                      <a-input />
                    </a-form-model-item>
                  </div>
                </div>
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
  name: "UserHA",
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