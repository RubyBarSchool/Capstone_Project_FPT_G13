<template>
  <div class="admin">
    <a-layout>
      <Header />
      <a-layout-content>
        <a-range-picker
          v-model="dataSearch.date"
          :placeholder="['Ngày bắt đầu', 'Ngày kết thúc']"
          :show-time="{ format: 'DD/MM/YYYY' }"
          format="DD/MM/YYYY"
        />
        
      </a-layout-content>
      <Footer />
    </a-layout>
  </div>
</template>
 <script>
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";

export default {
  name: "Attendance",
  components: {
    Header,
    Footer,
  },
  data() {
    return {};
  },
  computed: {},
  created() {},
  methods: {},
};
</script>

<style>
</style>