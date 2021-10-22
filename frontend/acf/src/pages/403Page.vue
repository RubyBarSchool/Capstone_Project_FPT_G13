<template>
  <div class="main">
    <div class="error-main">
      <h1>Oops!</h1>
      <div class="error-heading">403</div>
      <p>
        You do not have permission to access the document or program that you
        requested.
      </p>
      <router-link class="link_404" to="/home"> Go to Home </router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "403Page",
  data() {
    return {};
  },
  computed: {},
  created() {
    this.show();
  },
  methods: {
    show() {
      const style = "font-weight: bold; font-size: 150px;color: red;";
      console.log("%c Em Xin Cac Anh !!!!", style);
    },
  },
};
</script>

<style>
.main {
  font-family: "Ropa Sans", sans-serif;
  margin-top: 30px;
  background-color: #f0ca00;
  background-color: #f3661c;
  text-align: center;
  color: #fff;
}
.error-heading {
  margin: 50px auto;
  width: 250px;
  border: 5px solid #fff;
  font-size: 126px;
  line-height: 126px;
  border-radius: 30px;
  text-shadow: 6px 6px 5px #000;
}
.error-heading img {
  width: 100%;
}
.error-main h1 {
  font-size: 72px;
  margin: 0px;
  color: #f3661c;
  text-shadow: 0px 0px 5px #fff;
}
</style>