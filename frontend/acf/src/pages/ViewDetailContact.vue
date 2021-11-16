<template>
  <div class="attendance">
    <a-layout :style="{ background: 'white' }">
      <Header />
      <a-layout-content :style="{ margin: '30px 16px 0' }">
          
        </a-layout-content>
      <Footer />
    </a-layout>
  </div>
</template>
 <script>
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";
export default {
  name: "viewdetailcontact",
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
  },
  methods: {
    
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