<template>
  <div class="Slider">
    <a-layout id="layout">
      <a-layout-sider v-model="collapsed" :trigger="null" collapsible>
        <div class="logo" />
        <a-menu theme="dark" mode="inline" :default-selected-keys="['1']">
          <a-menu-item key="1">
            <a-icon>
              <font-awesome-icon :icon="['fas', 'user']" />
            </a-icon>
            <span> Quản lý tài khoản </span>
          </a-menu-item>
          <a-menu-item key="2">
            <a-icon>
              <font-awesome-icon :icon="['fas', 'users']" />
            </a-icon>
            <span> Quản lý nhân viên</span>
          </a-menu-item>
          <a-menu-item key="3">
            <a-icon>
              <font-awesome-icon :icon="['fas', 'file-contract']" />
            </a-icon>
            <span> Quản lý hợp đồng </span>
          </a-menu-item>
        </a-menu>
      </a-layout-sider>
      <a-layout>
        <a-layout-header style="background: #fff; padding: 0">
          <a-row type="flex">
            <a-col flex="auto">
              <a-icon
                class="trigger"
                :type="collapsed ? 'menu-unfold' : 'menu-fold'"
                @click="() => (collapsed = !collapsed)"
              />
            </a-col>
            <a-col flex="200px">
              <a-dropdown :trigger="['click']" class="dropdown">
                <a class="ant-dropdown-link">
                  <a-avatar
                    :size="50"
                    :style="{ 'margin-left': '10px', 'margin-bottom': '10px' }"
                    src="https://img.icons8.com/bubbles/100/000000/user.png"
                    class="avatar"
                  />
                  <span style="color: black"> {{ loadUser }} </span>
                </a>
                <a-menu slot="overlay" class="menu">
                  <a-menu-item :key="index" v-for="(data, index) in router">
                    <router-link :to="data.path">
                      {{ data.name }}
                    </router-link>
                  </a-menu-item>
                  <a-menu-divider />
                  <a-menu-item :key="router.length" @click="logout">
                    <h6>
                      Thoát ra
                      <font-awesome-icon :icon="['fas', 'sign-out-alt']" />
                    </h6>
                  </a-menu-item>
                </a-menu>
              </a-dropdown>
            </a-col>
          </a-row>
        </a-layout-header>
        <a-layout-content
          :style="{
            margin: '24px 16px',
            padding: '24px',
            background: '#fff',
            minHeight: '280px',
          }"
        >
          <router-view />
        </a-layout-content>
      </a-layout>
    </a-layout>
  </div>
</template>

<script>
export default {
  name: "Slider",
  data() {
    return {
      collapsed: false,
      user: "",
      router: [],
      routerSPAdmin: [
        {
          path: "/admin",
          name: "Quản lý tài khoản",
        },
      ],
    };
  },
  watch: {
    openKeys(val) {
      console.log("openKeys", val);
    },
  },
  created() {
    this.reloadPath();
  },
  computed: {
    loadUser() {
      return JSON.parse(localStorage.getItem("user")).username;
    },
  },
  methods: {
    logout() {
      localStorage.removeItem("user");
      this.$store.dispatch("remove");
      this.$router.push("/login");
    },
    reloadPath() {
      let users = JSON.parse(localStorage.getItem("user"));
      if (users.roles.includes("SP_ADMIN")) {
        this.router.push.apply(this.router, this.routerSPAdmin);
      }
    },
  },
};
</script>
<style scoped>
#layout .trigger {
  font-size: 18px;
  line-height: 64px;
  padding: 0 24px;
  cursor: pointer;
  transition: color 0.3s;
}

#layout .trigger:hover {
  color: #1890ff;
}

#layout .logo {
  height: 45px;
  /* background: rgba(206, 13, 13); */
  background-image: url("../assets/logo1.png");
  margin: 10px;
}

.dropdown {
  font-family: "Trebuchet MS", "Lucida Sans Unicode", "Lucida Grande",
    "Lucida Sans", Arial, sans-serif;
  color: white;
  font-size: 16px;
}

.dropdown:hover {
  font-family: "Trebuchet MS", "Lucida Sans Unicode", "Lucida Grande",
    "Lucida Sans", Arial, sans-serif;
  color: white;
  font-size: 18px;
}
</style>
