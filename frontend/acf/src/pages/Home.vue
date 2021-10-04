<template>
  <div>xin chao man home</div>
</template>

<script>
export default {
  name: "Home",
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