<template>
  <div class="login">
    <div class="demo">
      <div class="cont">
        <a-form
          id="components-form-demo-normal-login"
          :form="form"
          class="login-form"
          @submit="handleSubmit"
        >
          <a-form-item>
            <img src="../assets/logo.jpg" />
          </a-form-item>
          <a-form-item class="input-form">
            <a-input
              v-decorator="[
                'userName',
                {
                  rules: [{ required: true, message: 'Hãy nhập tài khoản!' }],
                },
              ]"
              placeholder="Nhập tài khoản"
            >
              <a-icon
                slot="prefix"
                type="user"
                style="color: rgba(0, 0, 0, 0.25)"
              />
            </a-input>
          </a-form-item>
          <a-form-item class="input-form">
            <a-input-password
              v-decorator="[
                'password',
                {
                  rules: [{ required: true, message: 'Hãy nhập mật khẩu!' }],
                },
              ]"
              type="password"
              placeholder="Nhập mật khẩu"
            >
              <a-icon
                slot="prefix"
                type="lock"
                style="color: rgba(0, 0, 0, 0.25)"
              />
            </a-input-password>
          </a-form-item>
          <a-form-item class="input-form">
            <a-button
              type="primary"
              html-type="submit"
              class="login-form-button"
            >
              Đăng nhập
            </a-button>
          </a-form-item>
        </a-form>
      </div>
    </div>
  </div>
</template>
<script>
import User from "../model/user";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
export default {
  name: "Login",
  data() {
    return {
      user: new User("", ""),
      loading: false,
      message: "",

      received_messages: [],
      send_message: null,
      connected: false,
    };
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  beforeCreate() {
    this.form = this.$form.createForm(this, { name: "normal_login" });
  },
  created() {
    this.showMessage();
    if (JSON.parse(localStorage.getItem("user"))) {
      let username = JSON.parse(localStorage.getItem("user")).username;
      this.socket = new SockJS("http://localhost:8080/api/wse/online");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect(
        { username: username },
        () => {
          this.connected = true;
          this.stompClient.subscribe("/users/queue/messages", (tick) => {
            if (this != null) {
              this.received_messages = JSON.parse(tick.body);
              console.log("account login", this.received_messages);
            }
          });
          if (this.stompClient && this.stompClient.connected) {
            this.stompClient.send("/ws/logout");
            this.disconnect();
          }
        },
        (error) => {
          console.log(error);
          this.connected = false;
        }
      );
      localStorage.removeItem("user");
    }
  },
  methods: {
    disconnect() {
      console.log("here");
      if (this.stompClient && this.stompClient.connected) {
        this.stompClient.disconnect();
      }
    },
    wsLogin() {},
    showMessage() {
      let message = this.$store.state.message;
      if (message.type != "") {
        this.$notification[message.type]({
          message: message.message,
          description: message.description,
        });
        this.$store.dispatch("remove");
      }
    },
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFields((err, values) => {
        this.user.username = values.userName;
        this.user.password = values.password;
        if (!err) {
          this.$store.dispatch("auth/login", this.user).then(
            () => {
              let users = JSON.parse(localStorage.getItem("user"));
              this.connectWebsoket(users.username);
              if (users.roles.includes("SP_ADMIN")) {
                this.$router.push("/admin");
              } else {
                this.$router.push("/");
              }
            },
            () => {
              this.$notification["error"]({
                message: "Sai tài khoản hoặc mật khẩu",
                description: "Mời đăng nhập lại",
              });
            }
          );
        }
      });
    },
    connectWebsoket(username) {
      this.socket = new SockJS("http://localhost:8080/api/wse/online");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect(
        { username: username },
        () => {
          this.connected = true;
          this.stompClient.subscribe("/users/queue/messages", (tick) => {
            if (this != null) {
              this.received_messages = JSON.parse(tick.body);
              console.log("account login", this.received_messages);
            }
          });
          if (this.stompClient && this.stompClient.connected) {
            this.stompClient.send("/ws/login");
          }
        },
        (error) => {
          console.log(error);
          this.connected = false;
        }
      );
    },
  },
};
</script>

<style scoped>
.login {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-image: url(../assets/back.jpg);
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  position: relative;
}
#components-form-demo-normal-login {
  background-color: rgba(148, 145, 145, 0.7);
  border-radius: 10px;
  height: max-content;
}
#components-form-demo-normal-login .ant-btn-primary {
  background-color: black;
  border: black;
  width: 100%;
  margin-bottom: 45px;
}
#components-form-demo-normal-login .input-form {
  padding-right: 40px;
  padding-left: 40px;
}
img {
  border-radius: 10px 10px 0 0;
  width: 340px;
  height: 150px;
}
</style>
