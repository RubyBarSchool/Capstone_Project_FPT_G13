<template>
  <div id="menu">
    <a-layout-sider
      breakpoint="lg"
      collapsed-width="0"
    >
      <div class="logo" />
      <a-menu theme="dark" mode="inline" :default-selected-keys="['4']">
        <a-menu-item key="1">
          <a-icon type="user" />
          <span class="nav-text">Tài khoản</span>
        </a-menu-item>
      </a-menu>
    </a-layout-sider>
  </div>
</template>

<script>
export default {
  name: "Menu",
};
</script>
<style>
.logo {
  height: 100px;
  width: 197px;
  background-image: url('../assets/logoProject.png');
}
.ant-layout-sider-children {
    background-color: black;
    height: 722px;
}
</style>
