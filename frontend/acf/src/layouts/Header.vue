<template>
  <div id="Header">
    <a-layout-header>
      <a-row type="flex">
        <a-col flex="auto">
          <img
            :style="{ width: '140px', 'margin-left': '20px' }"
            src="../assets/logo.jpg"
          />
        </a-col>
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
                  <h6>
                    Thoát ra
                    <font-awesome-icon
                      :icon="['fas', 'sign-out-alt']"
                      :style="{ 'margin-left': '80px' }"
                    />
                  </h6>
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
          name: "Quản lý tài khoản",
        },
      ],
      routerAdmin: [
        {
          path: "/attendance",
          name: "Chấm công",
        },
        {
          path: "/viewattendance",
          name: "Xem chấm công",
        },
        {
          path: "/user",
          name: "Quản lý nhân viên",
        },
        {
          path: "/position",
          name: "Quản lý chức vụ",
        },
        {
          path: "/company",
          name: "Quản lý công ty liên kết",
        },
        {
          path: "/acceptungluong",
          name: "Quản lý đơn ứng lương",
        },
        {
          path: "/acceptxinnghi",
          name: "Quản lí đơn xin nghỉ",
        },
        {
          path: "/acceptluongadmin",
          name: "Quản lý thanh toán lương",
        },
        {
          path: "/historyluongadmin",
          name: "Quản lý lương",
        },
        {
          path: "/phatadmin",
          name: "Quản lý đơn phạt",
        },
        {
          path: "/thuongadmin",
          name: "Quản lý đơn thưởng",
        },
        {
          path: "/viewthuongphat",
          name: "Xem Thưởng phạt",
        },
        {
          path: "/unitadmin",
          name: "Quản lý đơn vị đo",
        },
        {
          path: "/chieucao",
          name: "Quản lý chiều cao",
        },
        {
          path: "/groupcoverplate",
          name: "Quản lý nhóm tấm phủ",
        },
        {
          path: "/groupmaterial",
          name: "Quản lý nhóm vật liệu",
        },
        {
          path: "/frameadmin",
          name: "Quản lý khung đo",
        },
        {
          path: "/coverplate",
          name: "Quản lý tấm phủ",
        },
        {
          path: "/vatlieuadmin",
          name: "Quản lý vật liệu",
        },
        {
          path: "/materialsuggest",
          name: "Gợi ý sản phẩm",
        },
        {
          path: "/taohopdong",
          name: "Tạo hợp đồng",
        },
        {
          path: "/viewdetailcontact",
          name: "Quản lý chi tiết hợp đồng",
        },
        {
          path: "/ProductionOrder",
          name: "Tạo Lệnh sản xuất",
        },
      ],
      routerEmployee: [
        {
          path: "/viewluong",
          name: "Xem lương",
        },
        {
          path: "/ungluong",
          name: "Ứng lương",
        },
        {
          path: "/xinnghi",
          name: "Đơn xin nghỉ",
        },
        {
          path: "/viewwork",
          name: "Xem công việc",
        },
      ],
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
  background-color: #141414;
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
