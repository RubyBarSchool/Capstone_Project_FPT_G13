<template>
  <div class="profile">
    <a-layout :style="{ background: 'white' }">
      <a-layout-content :style="{ margin: '30px 16px 0' }">
        <div
          :style="{
            minHeight: '360px',
            background: 'white',
          }"
        >
          <a-back-top :style="{ width: '5vh', height: '15vh' }" />
          <!-- content -->

          <div class="card-container">
            <a-row type="flex">
              <a-col flex="50vh">
                <div class="col-md-4">
                  <div class="card-block text-center">
                    <div class="m-b-25">
                      <img
                        src="https://img.icons8.com/bubbles/100/000000/user.png"
                        class="img-radius"
                        :style="{ width: '40vh', height: '40vh' }"
                      />
                      <br />
                      <a href="#">
                        <font-awesome-icon
                          :style="{ 'font-size': '20px' }"
                          :icon="['fas', 'camera-retro']"
                        />
                      </a>
                    </div>
                    <h6>{{ dataProfiles.username }}</h6>
                    <h4>{{ dataProfiles.fullName }}</h4>
                  </div>
                </div>
              </a-col>
              <a-col flex="auto">
                <a-tabs default-active-key="1" class="tab">
                  <a-tab-pane key="1" tab="Thông tin">
                    <div class="col-md-8">
                      <div class="card-block">
                        <div class="row">
                          <div class="col-sm-6">
                            <h5 class="m-b-10 f-w-600">Tên tài khoản:</h5>
                            <p class="f-w-400">
                              {{ dataProfiles.username }}
                            </p>
                          </div>
                          <div class="col-sm-6">
                            <h5 class="m-b-10 f-w-600">Giới tính:</h5>
                            <p class="f-w-400">
                              {{ dataProfiles.gender ? "Nam" : "Nữ" }}
                            </p>
                          </div>
                        </div>
                        <hr />
                        <div class="row">
                          <div class="col-sm-6">
                            <h5 class="m-b-10 f-w-600">Vị trí:</h5>
                            <p
                              class="f-w-400"
                              v-for="(role, index) in dataProfiles.listRoleName"
                              :key="index"
                            >
                              {{ role }}
                            </p>
                          </div>
                          <div class="col-sm-6">
                            <h5 class="m-b-10 f-w-600">Lương:</h5>
                            <p class="f-w-400">
                              {{ dataProfiles.salary }}
                            </p>
                          </div>
                        </div>
                        <hr />
                        <div class="row">
                          <div class="col-sm-6">
                            <h5 class="m-b-10 f-w-600">Ngày sinh:</h5>
                            <p class="f-w-400">
                              {{ dataProfiles.dob }}
                            </p>
                          </div>
                          <div class="col-sm-6">
                            <h5 class="m-b-10 f-w-600">Email:</h5>
                            <p class="f-w-400">
                              {{ dataProfiles.email }}
                            </p>
                          </div>
                        </div>
                        <hr />
                        <div class="row">
                          <div class="col-sm-6">
                            <h5 class="m-b-10 f-w-600">Điện thoại:</h5>
                            <p class="f-w-400">
                              {{ dataProfiles.phone }}
                            </p>
                          </div>
                          <div class="col-sm-6">
                            <h5 class="m-b-10 f-w-600">Dân tộc:</h5>
                            <p class="f-w-400">
                              {{ dataProfiles.nation }}
                            </p>
                          </div>
                        </div>
                        <hr />
                        <div class="row">
                          <div class="col-sm-6">
                            <h5 class="m-b-10 f-w-600">Địa chỉ:</h5>
                            <p class="f-w-400">
                              {{ dataProfiles.address }}
                            </p>
                          </div>
                          <div class="col-sm-6"></div>
                        </div>
                      </div>
                    </div>
                  </a-tab-pane>
                  <a-tab-pane key="2" tab="Thay đổi mật khẩu" class="change">
                    <a-row type="flex">
                      <a-col flex="200px">
                        <strong style="font-size: 15px">
                          <span style="color: red">*</span>
                          <span style="color: black"
                            > Nhập mật khẩu cũ :
                          </span></strong
                        >
                      </a-col>
                      <a-col flex="auto">
                        <a-input-password
                          placeholder="Nhập mật khẩu cũ"
                          style="width: 300px"
                          v-model="dataChangePassword.oldPassword"
                          @change="inputOldPassword"
                        />
                        <div
                          style="color: red"
                          v-if="checkInputOldPassword.show"
                        >
                          {{ checkInputOldPassword.message }}
                        </div></a-col
                      >
                    </a-row>
                    <br />
                    <a-row type="flex">
                      <a-col flex="200px">
                        <strong style="font-size: 15px"
                          ><span style="color: red">*</span>
                          <span style="color: black"
                            > Nhập mật khẩu mới :</span
                          ></strong
                        >
                      </a-col>
                      <a-col flex="auto">
                        <a-input-password
                          placeholder="Nhập mật khẩu mới"
                          style="width: 300px"
                          v-model="dataChangePassword.newPassword"
                          @change="inputNewPassword"
                        />
                        <div
                          style="color: red"
                          v-if="checkInputNewPassword.show"
                        >
                          {{ checkInputNewPassword.message }}
                        </div></a-col
                      >
                    </a-row>
                    <br />
                    <a-row type="flex">
                      <a-col flex="200px">
                        <strong style="font-size: 15px"
                          ><span style="color: red">*</span>
                          <span style="color: black"
                            > Nhập lại mật khẩu :</span
                          ></strong
                        >
                      </a-col>
                      <a-col flex="auto">
                        <a-input-password
                          placeholder="Nhập lại mật khẩu"
                          style="width: 300px"
                          v-model="confirmNewPassword"
                          @change="inputConfirmPassword"
                        />
                        <div
                          style="color: red"
                          v-if="checkInputConfirmPassword.show"
                        >
                          {{ checkInputConfirmPassword.message }}
                        </div></a-col
                      >
                    </a-row>
                    <br />
                    <a-row type="flex">
                      <a-col flex="200px"></a-col>
                      <a-col flex="auto">
                        <a-button
                          type="primary"
                          @click="checkConfirmPassword"
                          :loading="loadingChangePass"
                        >
                          Lưu
                        </a-button></a-col
                      >
                    </a-row>
                  </a-tab-pane>
                </a-tabs>
              </a-col>
            </a-row>
          </div>

          <!-- content -->
        </div>
      </a-layout-content>
    </a-layout>
  </div>
</template>
 <script>
import profileService from "@/service/profileService.js";
export default {
  name: "Profile",
  data() {
    return {
      dataProfiles: [],
      dataChangePassword: {
        newPassword: "",
        oldPassword: "",
      },
      confirmNewPassword: "",
      loadingChangePass: false,
      checkInputNewPassword: {
        show: false,
        message: "",
      },
      checkInputOldPassword: {
        show: false,
        message: "",
      },
      checkInputConfirmPassword: {
        show: false,
        message: "",
      },
    };
  },
  created() {
    this.getProfile();
  },
  methods: {
    callback(key) {
      console.log(key);
    },
    getProfile() {
      profileService
        .getProfile()
        .then((response) => {
          this.dataProfiles = response.data.data;
          console.log("data ", this.dataProfiles);
        })
        .catch((e) => {
          console.log(e);
        });
    },
    changePassword() {
      this.loadingChangePass = true;
      profileService
        .changePassword(this.dataChangePassword)
        .then((response) => {
          if (response.data.data) {
            let type = "success";
            let message = "Thay đổi mật khẩu";
            let description = response.data.message;
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Thay đổi mật khẩu";
            let description = response.data.message;
            this.notifi(type, message, description);
          }
          this.loadingChangePass = false;
        })
        .catch((e) => {
          console.log(e);
          this.loadingChangePass = false;
        });
    },

    checkConfirmPassword() {
      let check = true;
      if (
        this.dataChangePassword.oldPassword != null &&
        this.dataChangePassword.oldPassword != ""
      ) {
        this.checkInputOldPassword.show = false;
        this.checkInputOldPassword.message = "";
      } else {
        check = false;
        this.checkInputOldPassword.show = true;
        this.checkInputOldPassword.message = "Bạn phải nhập mật khẩu cũ";
      }
      if (
        this.dataChangePassword.newPassword != null &&
        this.dataChangePassword.newPassword != ""
      ) {
        this.checkInputNewPassword.show = false;
        this.checkInputNewPassword.message = "";
      } else {
        check = false;
        this.checkInputNewPassword.show = true;
        this.checkInputNewPassword.message = "Bạn phải nhập mật khẩu mới";
      }
      if (this.confirmNewPassword != null && this.confirmNewPassword != "") {
        this.checkInputConfirmPassword.show = false;
        this.checkInputConfirmPassword.message = "";
      } else {
        check = false;
        this.checkInputConfirmPassword.show = true;
        this.checkInputConfirmPassword.message =
          "Bạn phải nhập lại mật khẩu mới";
      }
      if (this.confirmNewPassword != this.dataChangePassword.newPassword) {
        check = false;
        let type = "error";
        let message = "Thay đổi mật khẩu";
        let description = "Nhập lại mật khẩu không đúng";
        this.notifi(type, message, description);
      }
      if (
        this.dataChangePassword.oldPassword ==
        this.dataChangePassword.newPassword
      ) {
        check = false;
        let type = "error";
        let message = "Thay đổi mật khẩu";
        let description = "Mật khẩu mới và mật khẩu cũ phải khác nhau";
        this.notifi(type, message, description);
      }
      if (check) {
        this.changePassword();
      }
    },
    inputOldPassword() {
      if (
        this.dataChangePassword.oldPassword != null &&
        this.dataChangePassword.oldPassword != ""
      ) {
        this.checkInputOldPassword.show = false;
        this.checkInputOldPassword.message = "";
      } else {
        this.checkInputOldPassword.show = true;
        this.checkInputOldPassword.message = "Bạn phải nhập mật khẩu cũ";
      }
    },
    inputNewPassword() {
      if (
        this.dataChangePassword.newPassword != null &&
        this.dataChangePassword.newPassword != ""
      ) {
        this.checkInputNewPassword.show = false;
        this.checkInputNewPassword.message = "";
      } else {
        this.checkInputNewPassword.show = true;
        this.checkInputNewPassword.message = "Bạn phải nhập mật khẩu mới";
      }
    },
    inputConfirmPassword() {
      if (this.confirmNewPassword != null && this.confirmNewPassword != "") {
        this.checkInputConfirmPassword.show = false;
        this.checkInputConfirmPassword.message = "";
      } else {
        this.checkInputConfirmPassword.show = true;
        this.checkInputConfirmPassword.message =
          "Bạn phải nhập lại mật khẩu mới";
      }
    },
    notifi(type, message, description) {
      this.$notification[type]({
        message: message,
        description: description,
      });
    },
  },
};
</script>

<style scoped>
.col-md-4 {
  border-radius: 10px;
  height: 55vh;
  width: 45vh;
  box-shadow: rgba(17, 17, 26, 0.05) 0px 1px 0px,
    rgba(17, 17, 26, 0.1) 0px 0px 8px;
  margin-left: 12vh;
}

.tab {
  background-color: rgba(255, 255, 255, 0.65);
  border-radius: 10px;
  height: 70vh;
  width: 70vh;
  padding-left: 5vh;
  box-shadow: rgba(17, 17, 26, 0.05) 0px 1px 0px,
    rgba(17, 17, 26, 0.1) 0px 0px 8px;
}

.thumb-lg {
  height: 100vh;
  width: 100vh;
}

.m-b-30 {
  margin-bottom: 30px;
}

p {
  font-family: sans-serif;
  font-size: 16px;
  color: rgb(29, 27, 27);
}

h5 {
  font-family: sans-serif;
  font-size: 16px;
  font-weight: bold;
  color: rgb(8, 8, 61);
}

.change {
  padding-top: 15px;
}
</style>