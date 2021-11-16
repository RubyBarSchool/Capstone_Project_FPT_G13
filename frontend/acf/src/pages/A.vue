<template>
  <div class="attendance">
    <a-layout :style="{ background: 'white' }">
      <Header />
      <!-- <a-layout-content :style="{ margin: '30px 16px 0' }">
          <img alt="example" style="width: 100%" :src="url" /> </a-layout-content> -->
      <a-button
        type="primary"
        @click="showmodal"
        :style="{ 'margin-left': '5px' }"
        >show</a-button
      >
      <a-modal
        v-model="show"
        height="100%"
        width="80%"
        title="Xem Trước File Excel"
      >
        <template slot="footer">
          <a-button key="back" @click="handleCancelPriview"> Hủy </a-button>
        </template>
        <template>
          <a-select
            style="width: 120px"
            v-model="datamaterial"
            @change="handleChange"
          >
            <a-select-option value="material1"> material1 </a-select-option>
            <a-select-option value="material2"> material2 </a-select-option>
            <a-select-option value="material3"> material3 </a-select-option>
            <a-select-option value="material4"> material4 </a-select-option>
          </a-select>
        </template>
        <template>
          <a-select
            style="width: 120px"
            v-model="dataframe"
            @change="handleChange1"
          >
            <a-select-option value="frame1"> frame1 </a-select-option>
            <a-select-option value="frame2"> frame2 </a-select-option>
            <a-select-option value="frame3"> frame3 </a-select-option>
            <a-select-option value="frame4"> frame4 </a-select-option>
          </a-select>
        </template>
        <template>
          <a-select
            style="width: 120px"
            v-model="dataheight"
            @change="handleChange2"
          >
            <a-select-option value="height1"> height1 </a-select-option>
            <a-select-option value="height2"> height2 </a-select-option>
            <a-select-option value="height3"> height3 </a-select-option>
            <a-select-option value="height4"> height4 </a-select-option>
          </a-select>
        </template>
      </a-modal>
      <Footer />
    </a-layout>
  </div>
</template>
 <script>
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";
import attendanceService from "@/service/attendanceService.js";
export default {
  name: "A",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      url: "",
      result: {},
      show: false,
      dataSelect: [],
      datamaterial: "",
      dataframe: "",
      dataheight: "",
      dataForm: {
        id1: 0,
        id2: 0,
        name1: "",
        name2: "",
      },
    };
  },
  computed: {},
  created() {
    // this.loadImage();
  },
  methods: {
    handleChange() {
      let data = {
        type: "material",
        id: this.datamaterial,
        name: "",
      };
      this.dataSelect.push(data);
      if (this.dataSelect.length == 2) {
        this.dataForm.id1 = data.id;
        this.dataForm.name1 = data.name;
        this.dataForm.id2 = this.dataSelect[0].id;
        this.dataForm.name2 = this.dataSelect[0].name;
        if (this.dataSelect[0].type == "frame") {
          console.log("call api height",this.dataForm);
        } else {
          console.log("call api frame",this.dataForm);
        }
      }
      if (this.dataSelect.length == 3) {
        console.log("đóng băng lại toàn bộ");
      }
    },
    handleChange1() {
      let data = {
        type: "frame",
        id: this.dataframe,
        name: "",
      };
      this.dataSelect.push(data);
      if (this.dataSelect.length == 2) {
        if (this.dataSelect[0].type == "material") {
          this.dataForm.id1 = this.dataSelect[0].id;
          this.dataForm.name1 = this.dataSelect[0].name;
          this.dataForm.id2 = data.id;
          this.dataForm.name2 = data.name;
          console.log("call api height",this.dataForm);
        } else {
          this.dataForm.id1 = data.id;
          this.dataForm.name1 = data.name;
          this.dataForm.id2 = this.dataSelect[0].id;
          this.dataForm.name2 = this.dataSelect[0].name;
          console.log("call api material",this.dataForm);
        }
      }
      if (this.dataSelect.length == 3) {
        console.log("đóng băng lại toàn bộ");
      }
    },
    handleChange2() {
      let data = {
        type: "height",
        id: this.dataheight,
        name: "",
      };
      this.dataSelect.push(data);
      if (this.dataSelect.length == 2) {
        this.dataForm.id1 = this.dataSelect[0].id;
        this.dataForm.name1 = this.dataSelect[0].name;
        this.dataForm.id2 = data.id;
        this.dataForm.name2 = data.name;

        if (this.dataSelect[0].type == "material") {
          console.log("đcall api frame",this.dataForm);
        } else {
          console.log("call api material",this.dataForm);
        }
      }
      if (this.dataSelect.length == 3) {
        console.log("đóng băng lại toàn bộ");
      }
    },
    submit() {
      // call api submit
    },
    showmodal() {
      this.show = true;
    },
    handleCancelPriview() {
      this.show = false;
    },
    loadImage() {
      attendanceService
        .preview()
        .then((response) => {
          this.url = window.URL.createObjectURL(response.data);
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