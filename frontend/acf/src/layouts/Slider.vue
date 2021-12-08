<template>
  <div class="Slider">
    <a-layout id="layout">
      <a-layout-sider v-model="collapsed" :trigger="null" collapsible>
        <div class="logo" />
        <a-menu theme="dark" mode="inline" :default-selected-keys="['1']">
          <a-menu-item :key="index" v-for="(data, index) in router">
            <router-link :to="data.path">
              {{ data.name }}
            </router-link>
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
          path: "/productionorder",
          name: "Tạo Lệnh sản xuất",
        },
        {
          path: "/contactmoney",
          name: "Tạm ứng tiền hợp đồng",
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
          path: "/viewthuongphat",
          name: "Xem Thưởng phạt",
        },
        {
          path: "/viewwork",
          name: "Xem công việc",
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
