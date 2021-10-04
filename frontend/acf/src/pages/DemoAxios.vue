<template>
  <div>xin chao man profile</div>
</template>

<script>
import DemoAxios from "../service/demoaxios";
export default {
  name: "Profile",
  data() {
    return {};
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  created() {
    this.retrieveTutorials();
  },
  methods: {
    retrieveTutorials() {
      let form = {
        pageIndex: 0,
        pageSize: 10,
      };
      DemoAxios.getAll(form)
        .then((response) => {
          console.log(response.data);
        })
        .catch((e) => {
          console.log(e);
        });
    },
  },
};
</script>

<style>
</style>