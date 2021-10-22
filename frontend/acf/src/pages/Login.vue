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
            <img src="../assets/logo_login.jpg" />
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
export default {
  name: "Login",
  data() {
    return {
      user: new User("", ""),
      loading: false,
      message: "",
    };
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  beforeCreate() {
    localStorage.removeItem("user");
    this.form = this.$form.createForm(this, { name: "normal_login" });
  },
  created() {
    this.showMessage();
  },
  methods: {
    showMessage() {
      let message = this.$store.state.message;
      if (message.type != "") {
        console.log("here");
        this.$notification[message.type]({
          message: message.message,
          description: message.description,
        });
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
              if (users.roles.includes("SP_ADMIN")) {
                this.$router.push("/admin");
              } else {
                this.$router.push("/home");
              }
            },
            (error) => {
              this.loading = false;
              this.message =
                (error.response &&
                  error.response.data &&
                  error.response.data.message) ||
                error.message ||
                error.toString();
            }
          );
        }
      });
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
  width: 327px;
  height: 175px;
}
</style>
