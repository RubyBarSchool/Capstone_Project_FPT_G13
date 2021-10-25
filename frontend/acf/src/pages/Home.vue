<template>
  <div class="attendance">
    <a-layout :style="{ background: 'white' }">
      <Header />
      <a-layout-content :style="{ margin: '24px 16px 0' }">
        <a-back-top />
        
      </a-layout-content>
      <Footer />
    </a-layout>
  </div>
</template>

<script>
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";
export default {
  name: "Home",
  components: {
    Header,
    Footer,
  },
  data() {
    return {};
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  created() {},
  methods: {},
};
</script>

<style>
</style>