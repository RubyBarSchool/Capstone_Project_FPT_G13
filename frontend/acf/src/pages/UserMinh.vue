<template>
  <div class="UserMinh">
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
              <a-input placeholder="Tên nhân viên" style="width: 150px" />
              <a-select
                placeholder="Chức vụ"
                mode="multiple"
                v-model="s"
                :filter-option="false"
                @search="fetchPosition"
                style="width: 120px"
              >
                <a-select-option
                  v-for="(position, index) in dataPositions"
                  :value="position.id"
                  :key="index"
                >
                  {{ position.name }}
                </a-select-option>
              </a-select>
              <a-input placeholder="Trạng thái" style="width: 150px" />
              <a-range-picker />
              <a-button type="primary" icon="search" > Tìm kiếm </a-button>
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
                      <a-input v-model="dataAdd.username"/>
                    </a-form-model-item>
                    <a-form-model-item label="Ngày Sinh">
                      <a-input v-model="dataAdd.password"/>
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
import userService from "../service/userService";

export default {
  name: "UserMinh",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      s:"",
      visibleAdd: false,
      dataPositions: [],
      dataPosition: {
        name: "",
        pageIndex: 1,
        pageSize: 10,
      },
      dataAdd: {
        code: "",
        name: ""
      },
    };
  },
  created() {
    this.getAllPosition();
  },
  methods: {
    // showModalAdd() {
    //   this.visibleAdd = true;
    // },
    handleCancel() {
      this.visibleAdd = false;
    },
    // submitAdd() {
    //   this.visibleAdd = false;
    // },
    getAllPosition() {
      userService
        .getAllPosition(this.dataPosition)
        .then((response) => {
          this.dataPositions = response.data.data;
          console.log("anh ban ak", this.dataPositions);
        })
        .catch((e) => {
          console.log(e);
        });
    },
    fetchPosition(value) {
      this.dataPosition.name = value;
      this.getAllPosition();
    },
    showModalAdd() {
      // this.dataRole.name = "";
      // this.dataEmployee.name = "";
      this.visibleAdd = true;
    },
    submitAdd() {
      userService
        .addPosition(this.dataAdd)
        .then((response) => {
          this.dataEmployees = response.data.data;
          if (response.data.data) {
            // this.submitSearch();
            var task = "success";
            var text = "Thêm";
            this.notifi(task, text);
          }
          this.submitSearch();
        })
        .catch((e) => {
          console.log(e);
        });
      this.visibleAdd = false;
      this.dataAdd.code = "";
      this.dataAdd.name = "";
    },
    notifi(task, text) {
      this.$notification[task]({
        message: "Thông báo",
        description: text + " thành công",
      });
    },
  },
};
</script>

<style>
</style>