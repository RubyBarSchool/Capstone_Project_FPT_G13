<template>
  <div id="Header">
    <a-layout-header>
      <a-row type="flex">
        <a-col flex="auto"> </a-col>
        <a-col flex="180px">
          <span>
            <a-badge dot>
              <font-awesome-icon
                :icon="['far', 'bell']"
                :style="{ 'font-size': '30px', color: 'white' }"
              />
            </a-badge>
            <a-dropdown :trigger="['click']" class="dropdown">
              <a class="ant-dropdown-link">
                <a-avatar
                  :size="50"
                  :style="{ 'margin-left': '10px', 'margin-bottom': '9px' }"
                  src="https://img.icons8.com/bubbles/100/000000/user.png"
                  class="avatar"
                />
                {{ loadUser }}
              </a>
              <a-menu slot="overlay" class="menu">
                <a-menu-item :key="index" v-for="(data, index) in router">
                  <router-link :to="data.path">
                    {{ data.name }}
                  </router-link>
                </a-menu-item>
                <a-menu-divider />
                <a-menu-item :key="router.length" @click="logout">
                  Thoát ra
                  <font-awesome-icon
                    :icon="['fas', 'sign-out-alt']"
                    :style="{ 'margin-left': '40px' }"
                  />
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
      router: [],
      routerSPAdmin: [
        {
          path: "/admin",
          name: "Account Manager",
        },
      ],
      routerAdmin: [
        {
          path: "/attendance",
          name: "Attendance",
        },
        {
          path: "/viewattendance",
          name: "ViewAttendance",
        },
        {
          path: "/user",
          name: "Manager User",
        },
        {
          path: "/position",
          name: "Manager Position",
        },
      ],
      routerEmployee: [],
    };
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
      if (users.roles.includes("ADMIN")) {
        this.router.push.apply(this.router, this.routerAdmin);
      }
      if (users.roles.includes("EMPLOYEE")) {
        this.router.push.apply(this.router, this.routerEmployee);
      }
    },
  },
};
</script>
<style scoped>
.ant-layout-header {
  background-color: #24292f;
  /* -webkit-box-shadow: 0px 7px 13px -1px rgba(230, 230, 230, 1);
  -webkit-box-shadow: 0px 7px 13px -4px rgba(230, 230, 230, 1);
  -moz-box-shadow: 0px 7px 13px -4px rgba(230, 230, 230, 1);
  box-shadow: 0px 7px 13px -4px rgba(230, 230, 230, 1); */
}
.menu {
  padding: 10px;
  margin: 15px;
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
