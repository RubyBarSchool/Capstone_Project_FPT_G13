<template>
  <div id="Header">
    <a-layout-header>
      <a-row type="flex">
        <a-col flex="auto">
          <img
            :style="{ width: '140px', 'margin-left': '20px' }"
            src="../assets/logo_header.png"
          />
        </a-col>
        <a-col flex="250px">
          <span style="margin-right: 80px">
            <a-badge :count="5"
              ><a-icon :style="{ fontSize: '30px' }" type="bell" />
            </a-badge>
            <a-dropdown>
              <a class="ant-dropdown-link">
                <a-avatar
                  :size="55"
                  style="margin: 5px"
                  src="https://img.icons8.com/bubbles/100/000000/user.png"
                />
                {{ loadUser }}
              </a>
              <a-menu slot="overlay">
                <a-menu-item key="1">
                  <a-icon type="user" />1st menu item
                </a-menu-item>
                <a-menu-item key="2">
                  <a-icon type="user" />2nd menu item
                </a-menu-item>
                <a-menu-item key="3">
                  <a-icon type="user" />3rd item
                </a-menu-item>
                <a-menu-item key="4">
                  <a-icon type="user" />4rd item
                </a-menu-item>
                <a-menu-item key="5">
                  <a-icon type="user" />Logout
                </a-menu-item>
              </a-menu>
            </a-dropdown>
          </span>
        </a-col>
      </a-row>
    </a-layout-header>
  </div>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      user: "",
    };
  },
  computed: {
    loadUser() {
      return JSON.parse(localStorage.getItem("user")).username;
    },
  },
};
</script>
<style scoped>
.ant-layout-header {
  background-color: white;
  -webkit-box-shadow: 0px 7px 13px -1px rgba(230, 230, 230, 1);
  -webkit-box-shadow: 0px 7px 13px -4px rgba(230, 230, 230, 1);
  -moz-box-shadow: 0px 7px 13px -4px rgba(230, 230, 230, 1);
  box-shadow: 0px 7px 13px -4px rgba(230, 230, 230, 1);
}
#Header{
  position: sticky;
    z-index: 99999;
    top: 0;
}
</style>
