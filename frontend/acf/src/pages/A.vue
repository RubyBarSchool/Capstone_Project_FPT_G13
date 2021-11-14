<template>
  <div class="attendance">
    <a-layout :style="{ background: 'white' }">
      <Header />
      <a-layout-content :style="{ margin: '30px 16px 0' }">
          <img alt="example" style="width: 100%" :src="url" /> </a-layout-content>
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
    };
  },
  computed: {},
  created() {
    this.loadImage();
  },
  methods: {
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