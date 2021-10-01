<template>
  <div>
      Hello
  </div>
</template>

<script>
export default {
  name: 'Profile',
  data() {
    return {
    };
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    }
  },
  created() {
  },
  methods: {
  }
};
</script>

<style>
</style>