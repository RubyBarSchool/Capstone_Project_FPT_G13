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
            <a-col flex="40px">
              <a-dropdown :trigger="['click']">
                <a-badge
                  @click="clickNotification"
                  :count="countmessage"
                  :overflow-count="30"
                >
                  <font-awesome-icon
                    :style="{ 'font-size': '40px', 'margin-top': '1vh' }"
                    :icon="['far', 'bell']"
                    class="bell"
                  />
                </a-badge>
                <a-menu
                  v-if="dataNotification.length != 0"
                  slot="overlay"
                  class="menu"
                >
                  <a-menu-item> <h6>Thông báo</h6> </a-menu-item>
                  <a-menu-divider />
                  <a-row type="flex" :style="{ 'margin-left': '10px' }">
                    <a-col flex="110px">
                      <a-menu-item key="x" @click="readAllNotification">
                        <button class="viewall">Xem tất cả</button>
                      </a-menu-item>
                    </a-col>
                    <a-col flex="auto">
                      <a-menu-item key="y" @click="deleteAllNotification">
                        <button class="viewall">Xóa tất cả</button>
                      </a-menu-item>
                    </a-col>
                  </a-row>
                  <a-menu-divider />
                  <a-menu-item
                    v-for="(data, index) in dataNotification"
                    :key="index"
                    @click="readNotification(data)"
                  >
                    <a-row type="flex">
                      <a-col
                        flex="auto"
                        :style="{ color: '#00000', 'margin-top': '36px' }"
                      >
                        <router-link :to="data.path">
                          <div class="content">
                            <a-badge :color="!data.read ? 'blue' : ''" />

                            <strong style="font-size: 16px"
                              >{{ data.usernameCreate }}
                            </strong>
                            <span style="font-size: 16px">
                              {{ data.content }}
                            </span>
                          </div>
                          <p style="color: #2e89ff">
                            {{ getdate(data.localDateTime) }}
                          </p>
                        </router-link>
                      </a-col>
                      <a-col flex="30px">
                        <a-popconfirm
                          title="Bạn có chắc chắn muốn xóa thông báo không?"
                          @confirm="deleteNotification(data)"
                          ok-text="Đồng ý"
                          cancel-text="Hủy"
                        >
                          <font-awesome-icon
                            :style="{
                              'font-size': '20px',
                              'margin-left': '15px',
                              color: '#495057',
                            }"
                            :icon="['fas', 'ellipsis-h']"
                          />
                        </a-popconfirm>
                      </a-col>
                    </a-row>
                  </a-menu-item>
                </a-menu>
              </a-dropdown>
            </a-col>
            <a-col flex="100px">
              <a-dropdown :trigger="['click']" class="menuAva">
                <a class="ant-dropdown-link">
                  <a-avatar
                    :size="60"
                    :style="{ 'margin-left': '10px', 'margin-bottom': '10px' }"
                    src="https://img.icons8.com/bubbles/100/000000/user.png"
                  />
                  <font-awesome-icon
                    :style="{ 'font-size': '16px', color: '#495057' }"
                    :icon="['fas', 'caret-down']"
                  />
                </a>
                <a-menu slot="overlay" class="menu">
                  <a-menu-item :key="1">
                    <router-link to="/profile">
                      <a-row type="flex">
                        <a-col :flex="3">
                          <a-avatar
                            :size="50"
                            src="https://img.icons8.com/bubbles/100/000000/user.png"
                          />
                        </a-col>
                        <a-col :flex="2">
                          <span style="color: black">
                            <div>
                              Xin chào, <strong>{{ loadUser }}</strong>
                            </div>

                            <div>Xem trang cá nhân</div>
                          </span></a-col
                        >
                      </a-row>
                    </router-link>
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
      countmessage: 0,
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
              path: "/contact",
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
              path: "/acceptadvancesalary",
              name: "Đơn ứng lương",
            },
            {
              path: "/acceptleaveapplication",
              name: "Đơn xin nghỉ",
            },
            {
              path: "/punishadmin",
              name: "Quyết định kỷ luật",
            },
            {
              path: "/bonusadmin",
              name: "Quyết định khen thưởng",
            },
            {
              path: "/acceptsalaryadmin",
              name: "Thanh toán lương",
            },
            {
              path: "/historysalaryadmin",
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
              path: "/height",
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
              path: "/materialadmin",
              name: "Vật liệu",
            },
          ],
        },
        {
          path: "/materialsuggest",
          icon: "file-medical-alt",
          name: "Gợi ý vật tư",
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
              path: "/viewsalary",
              name: "Xem lương",
            },
            {
              path: "/advancesalary",
              name: "Ứng lương",
            },
            {
              path: "/leaveapplication",
              name: "Đơn xin nghỉ",
            },
            {
              path: "/viewbonuspunish",
              name: "Xem Khen Thưởng (Kỷ luật)",
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
  destroyed() {
    this.disconnect();
  },
  computed: {
    loadUser() {
      return JSON.parse(localStorage.getItem("user")).username;
    },
  },
  methods: {
    deleteNotification(data) {
      let notification = {
        username: data.username,
        usernameCreate: data.usernameCreate,
        content: data.content,
        type: data.type,
        path: data.path,
        read: data.read,
        localDateTime: data.localDateTime,
      };
      if (this.stompClient && this.stompClient.connected) {
        this.stompClient.send(
          "/ws/deletenotification",
          JSON.stringify(notification)
        );
      }
    },
    clickNotification() {
      if (this.stompClient && this.stompClient.connected) {
        this.stompClient.send("/ws/notification");
      }
    },
    getdate(date) {
      let dates = Date.now() - new Date(date);
      let x = Math.floor(dates / 1000);
      let seconds = x % 60;
      x = Math.floor(x / 60);
      let minutes = x % 60;
      x = Math.floor(x / 60);
      let hours = x % 24;
      let days = Math.floor(x / 24);
      return (
        " " +
        (days != 0 ? days + " ngày " : "") +
        (hours != 0 ? hours + " giờ " : "") +
        (minutes != 0 ? minutes + " phút " : "") +
        (seconds != 0 ? seconds + " giây " : "0 giây") +
        " trước"
      );
    },
    readAllNotification() {
      if (this.stompClient && this.stompClient.connected) {
        this.stompClient.send("/ws/readallnotification");
      }
    },
    deleteAllNotification() {
      if (this.stompClient && this.stompClient.connected) {
        this.stompClient.send("/ws/deleteallnotification");
      }
    },
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
    readNotification(data) {
      let notification = {
        username: data.username,
        usernameCreate: data.usernameCreate,
        content: data.content,
        type: data.type,
        path: data.path,
        read: data.read,
        localDateTime: data.localDateTime,
      };

      console.log("data notification", notification);
      if (this.stompClient && this.stompClient.connected) {
        this.stompClient.send(
          "/ws/readnotification",
          JSON.stringify(notification)
        );
      }
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
                  console.log("come here");
                  this.$store.dispatch(
                    "urlNotification",
                    dataMess[0].localDateTime + dataMess[0].path
                  );
                }
              }
              this.dataNotification = dataMess;
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

        if (this.router[i] && this.router[i].name == "Gợi ý vật tư") {
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
.bell {
  color: #001100;
}
.bell:hover {
  background-color: rgb(224, 217, 217);
  border-radius: 10px;
}
.content {
  color: #000;
  margin-top: -40px;
}
::v-deep .ant-menu-inline .ant-menu-item {
  font-size: 17px;
}

::v-deep .ant-menu-inline .ant-menu-submenu-title {
  font-size: 17px;
}

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

::v-deep .ant-layout-sider {
  min-height: 100vh;
  min-width: 200px;
  width: 300px !important;
  flex: none !important;
  max-width: 300px !important;
}
.ant-layout-sider-collapsed {
  width: auto !important;
  max-width: 80px !important;
}

#layout .logo {
  height: 70px;
  background-image: url("../assets/logo1.png");
  margin: 10px;
}
#layout .logo1 {
  height: 60px;
  width: 65px;
  background-image: url("../assets/logo3.png");
  margin: 10px;
}
.menuAva {
  font-family: "Trebuchet MS", "Lucida Sans Unicode", "Lucida Grande",
    "Lucida Sans", Arial, sans-serif;
  font-size: 18px;
}
.menu {
  margin-bottom: 20vh;
  margin-left: 1vh;
}

.viewall {
  border: none;
  background-color: #e4e6eb;
  color: #050505;
  padding: 0px 10px;
  line-height: 30px;
  font-size: 14px;
  border-radius: 23px;
}

.viewall:hover {
  border: none;
  background-color: #deeefd;
  color: #1877f2;
  padding: 0px 10px;
  line-height: 30px;
  font-size: 16px;
  border-radius: 23px;
}
</style>
