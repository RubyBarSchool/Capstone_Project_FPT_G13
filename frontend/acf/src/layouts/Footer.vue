<template>
  <div class="footer">
    <a-divider />
    <a-row type="flex" justify="space-around" :style="{ 'font-szie': '10px' }">
      <a href="#"
        ><a-col :span="1" class="textMail"> acfurniturett@gmail.com </a-col></a
      >
      <a href="#"><a-col :span="1" class="text">TrangChủ </a-col></a>
      <a href="#"><a-col :span="1" class="text">BảoMật </a-col></a>
      <a href="#"><a-col :span="1" class="text">BảoVệ</a-col></a>
      <a href="#"><a-col :span="1" class="text">TrạngThái </a-col></a>
      <a href="#"><a-col :span="1" class="text">Docs </a-col></a>
      <a href="#"
        ><a-col :span="1">
          <font-awesome-icon
            :icon="['fas', 'sun']"
            :style="{ 'font-size': '40px', color: '#495057' }"
          /> </a-col
      ></a>
      <a href="#"><a-col :span="1" class="text">LiênLạc </a-col></a>
      <a href="#"><a-col :span="1" class="text">ĐịnhGiá </a-col></a>
      <a href="#"><a-col :span="1" class="text">API </a-col></a>
      <a href="#"><a-col :span="1" class="text">HướngDẫn </a-col></a>
      <a href="#"><a-col :span="1" class="text">Blog </a-col></a>
      <a href="#"><a-col :span="1" class="text">ThôngTin </a-col></a>
    </a-row>
  </div>
</template>

<script>
export default {
  name: "Footer",
};
</script>
<style scoped>
.footer {
  margin-bottom: 30px;
  margin-right: 60px;
  margin-left: 60px;
}
.text {
  font-size: 14px;
  font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
}
.textMail {
  font-size: 14px;
  color: black;
  font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
}
</style>
