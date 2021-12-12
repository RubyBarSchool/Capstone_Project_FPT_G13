<template>
  <div class="Slider">
    <a-layout id="layout">
      <a-layout-sider v-model="collapsed" :trigger="null" collapsible>
        <div :class="logo" />
        <a-menu
          :open-keys="openKeys"
          @openChange="onOpenChange"
          theme="dark"
          mode="inline"
          :selected-keys="[selectKeys]"
          @click="handleClick"
        >
          <template v-for="(data, index) in router">
            <a-menu-item v-if="data.menu.length == 0" :key="data.path">
              <font-awesome-icon :icon="['fas', data.icon]" />
              <span v-if="disableTitle"> {{ data.name }}</span>
              <router-link :to="data.path">
                {{ data.name }}
              </router-link>
            </a-menu-item>

            <a-sub-menu v-if="data.menu.length != 0" :key="index">
              <span slot="title">
                <font-awesome-icon :icon="['fas', data.icon]" />
                <span v-if="disableTitle"> {{ data.name }}</span></span
              >
              <template v-for="datax in data.menu">
                <a-menu-item :key="datax.path">
                  <span> {{ datax.name }}</span>
                  <router-link :to="datax.path">
                    {{ datax.name }}
                  </router-link>
                </a-menu-item>
              </template>
            </a-sub-menu>
          </template>
        </a-menu>
      </a-layout-sider>
      <a-layout>
        <a-layout-header style="background: #fff; padding: 0">
          <a-row type="flex">
            <a-col flex="auto">
              <a-icon
                class="trigger"
                :type="collapsed ? 'menu-unfold' : 'menu-fold'"
                @click="toggleCollapsed"
              />
            </a-col>
            <a-col flex="auto">
              <a-badge :count="countmessage" :overflow-count="30">
                <font-awesome-icon
                  :style="{ 'font-size': '30px', color: '#495057' }"
                  if=""
                  :icon="['fas', 'bell']"
                />
              </a-badge>
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
                  <a-menu-item :key="1">
                    <router-link to="/detail"> Chi tiết tài khoản </router-link>
                  </a-menu-item>
                  <a-menu-divider />
                  <a-menu-item :key="2" @click="logout">
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
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
  name: "Slider",
  data() {
    return {
      countmessage: 100,
      logo: "logo",
      collapsed: false,
      disableTitle: true,
      openKeys: [],
      user: "",
      selectKeys: "/dashboard",
      router: [
        {
          path: "/dashboard",
          icon: "chart-pie",
          name: "Thống kê",
          menu: [],
        },
        {
          path: "/admin",
          icon: "users-cog",
          name: "Quản lý tài khoản",
          menu: [],
        },
        {
          path: "/",
          icon: "file-contract",
          name: "Quản lý Hợp đồng",
          menu: [
            {
              path: "/taohopdong",
              name: "Tạo hợp đồng",
            },
            {
              path: "/viewdetailcontact",
              name: "Chi tiết hợp đồng",
            },
            {
              path: "/productionorder",
              name: "Lệnh sản xuất",
            },
            {
              path: "/contactmoney",
              name: "Tạm ứng tiền hợp đồng",
            },
          ],
        },
        {
          path: "/viewwork",
          icon: "tasks",
          name: "Xem công việc",
          menu: [],
        },
        {
          path: "/",
          icon: "calendar-check",
          name: "Quản lý chấm công",
          menu: [
            {
              path: "/attendance",
              name: "Chấm công",
            },
            {
              path: "/viewattendance",
              name: "Xem chấm công",
            },
          ],
        },
        {
          path: "/",
          icon: "user",
          name: "Quản lý nhân sự",
          menu: [
            {
              path: "/position",
              name: "Chức vụ",
            },
            {
              path: "/user",
              name: "Nhân viên",
            },
            {
              path: "/acceptungluong",
              name: "Đơn ứng lương",
            },
            {
              path: "/acceptxinnghi",
              name: "Đơn xin nghỉ",
            },
            {
              path: "/phatadmin",
              name: "Đơn kỷ luật",
            },
            {
              path: "/thuongadmin",
              name: "Đơn Khen thưởng",
            },
            {
              path: "/acceptluongadmin",
              name: "Thanh toán lương",
            },
            {
              path: "/historyluongadmin",
              name: "Lịch sử thanh toán lương",
            },
          ],
        },
        {
          path: "/",
          icon: "boxes",
          name: "Quản lý vật tư",
          menu: [
            {
              path: "/unitadmin",
              name: "Đơn vị đo",
            },
            {
              path: "/chieucao",
              name: "Chiều cao",
            },
            {
              path: "/frameadmin",
              name: "Khung đo",
            },
            {
              path: "/groupcoverplate",
              name: "Nhóm tấm phủ",
            },
            {
              path: "/groupmaterial",
              name: "Nhóm vật liệu",
            },
            {
              path: "/coverplate",
              name: "Tấm phủ",
            },
            {
              path: "/vatlieuadmin",
              name: "Vật liệu",
            },
          ],
        },
        {
          path: "/materialsuggest",
          icon: "file-medical-alt",
          name: "Gợi ý vật liệu",
          menu: [],
        },
        {
          path: "/company",
          icon: "building",
          name: "Quản lý công ty liên kết",
          menu: [],
        },
        {
          path: "/",
          icon: "award",
          name: "Lương thưởng",
          menu: [
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
              name: "Xem Khen Thưởng(Kỉ luật)",
            },
          ],
        },
      ],
      dataNotification: [],
    };
  },
  created() {
    this.reloadPath();
    this.connectWebsoket();
  },
  beforeDestroy() {
    this.disconnect();
  },
  computed: {
    loadUser() {
      return JSON.parse(localStorage.getItem("user")).username;
    },
  },
  methods: {
    disconnect() {
      if (this.stompClient) {
        this.stompClient.disconnect();
      }
    },
    handleClick(e) {
      this.selectKeys = e.key;
    },
    onOpenChange(openKeys) {
      if (openKeys.length == 0) {
        this.openKeys = openKeys;
      } else {
        if (this.openKeys.length == 0) {
          this.openKeys = openKeys;
        } else {
          if (this.openKeys[0] == parseInt(openKeys[1])) {
            this.openKeys = [];
          } else {
            this.openKeys = [parseInt(openKeys[1])];
          }
        }
      }
    },
    toggleCollapsed() {
      this.collapsed = !this.collapsed;
      this.disableTitle = !this.disableTitle;
      if (!this.collapsed) {
        this.logo = "logo";
      } else {
        this.logo = "logo1";
      }
    },
    logout() {
      if (this.stompClient && this.stompClient.connected) {
        this.stompClient.send("/ws/logout");
      }
      localStorage.removeItem("user");
      this.$store.dispatch("remove");
      this.$router.push("/login");
    },

    connectWebsoket() {
      let username = JSON.parse(localStorage.getItem("user")).username;
      this.socket = new SockJS("http://localhost:8080/api/wse/online");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect(
        { username: username },
        () => {
          this.connected = true;
          this.stompClient.subscribe("/users/queue/notification", (tick) => {
            if (this != null) {
              let dataMess = JSON.parse(tick.body).data;
              if (dataMess.length > this.dataNotification.length) {
                this.dataNotification = dataMess;
                if (!dataMess[0].read) {
                  let type = dataMess[0].type;
                  let message = "Thông báo mới";
                  let description =
                    dataMess[0].usernameCreate + "\n" + dataMess[0].content;
                  this.notificationLocation(
                    type,
                    message,
                    description,
                    "bottomLeft"
                  );
                  console.log("come here")
                  this.$store.dispatch("urlNotification",dataMess[0].localDateTime + dataMess[0].path);
                }
              }
              this.countmessage = JSON.parse(tick.body).count;
            }
          });
          if (this.stompClient && this.stompClient.connected) {
            this.stompClient.send("/ws/notification");
          }
        },
        (error) => {
          console.log(error);
          this.connected = false;
        }
      );
    },
    reloadPath() {
      let users = JSON.parse(localStorage.getItem("user"));
      let b = this.router.length - 1;
      for (let i = b; i >= 0; i--) {
        if (this.router[i] && this.router[i].name == "Quản lý tài khoản") {
          if (!users.roles.includes("SP_ADMIN")) {
            this.router.splice(i, 1);
          }
        }

        if (this.router[i] && this.router[i].name == "Quản lý Hợp đồng") {
          if (!users.roles.includes("ADMIN")) {
            this.router.splice(i, 1);
          }
        }

        if (this.router[i] && this.router[i].name == "Quản lý chấm công") {
          if (!users.roles.includes("ADMIN")) {
            this.router.splice(i, 1);
          }
        }

        if (this.router[i] && this.router[i].name == "Quản lý nhân sự") {
          if (!users.roles.includes("ADMIN")) {
            this.router.splice(i, 1);
          }
        }

        if (this.router[i] && this.router[i].name == "Quản lý vật tư") {
          if (!users.roles.includes("ADMIN")) {
            this.router.splice(i, 1);
          }
        }

        if (this.router[i] && this.router[i].name == "Gợi ý vật liệu") {
          if (!users.roles.includes("ADMIN")) {
            this.router.splice(i, 1);
          }
        }

        if (
          this.router[i] &&
          this.router[i].name == "Quản lý công ty liên kết"
        ) {
          if (!users.roles.includes("ADMIN")) {
            this.router.splice(i, 1);
          }
        }

        if (this.router[i] && this.router[i].name == "Xem công việc") {
          if (!users.roles.includes("EMPLOYEE")) {
            this.router.splice(i, 1);
          }
        }
        if (this.router[i] && this.router[i].name == "Lương thưởng") {
          if (!users.roles.includes("EMPLOYEE")) {
            this.router.splice(i, 1);
          }
        }
      }

      this.selectKeys = this.$route.path;

      for (let i = 0; i < this.router.length; i++) {
        if (this.router[i].menu.length != 0) {
          for (let j = 0; j < this.router[i].menu.length; j++) {
            if (this.router[i].menu[j].path == this.selectKeys) {
              this.openKeys = [i];
              return;
            }
          }
        }
      }
    },
    notificationLocation(type, message, description, placement) {
      this.$notification[type]({
        message: message,
        description: description,
        placement,
      });
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

.ant-layout-sider {
  min-height: 100vh;
}

#layout .logo {
  height: 45px;
  /* background: rgba(206, 13, 13); */
  background-image: url("../assets/logo1.png");
  margin: 10px;
}
#layout .logo1 {
  height: 45px;
  width: 45px;
  /* background: rgba(206, 13, 13); */
  background-image: url("../assets/logo3.png");
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
