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
        <a-col flex="250px" >
          <span style="margin-right: 90px">
            <a-badge :count="5"
              ><a-icon :style="{ fontSize: '30px' }" type="bell" />
            </a-badge>
            <a-dropdown :trigger="['click']" class="avatar">
              <a class="ant-dropdown-link">
                <a-avatar
                  :size="55"
                  style="margin: 5px"
                  src="https://img.icons8.com/bubbles/100/000000/user.png"
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
  background-color: white;
  -webkit-box-shadow: 0px 7px 13px -1px rgba(230, 230, 230, 1);
  -webkit-box-shadow: 0px 7px 13px -4px rgba(230, 230, 230, 1);
  -moz-box-shadow: 0px 7px 13px -4px rgba(230, 230, 230, 1);
  box-shadow: 0px 7px 13px -4px rgba(230, 230, 230, 1);
}

.menu {
  padding: 10px;
  margin: 15px;
}

.avatar{
  border: 1px solid #000;
}
</style>
