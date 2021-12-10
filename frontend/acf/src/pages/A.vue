<template>
  <div class="attendance">
    <a-layout :style="{ background: 'white' }">
      <!-- <a-layout-content :style="{ margin: '30px 16px 0' }"> -->
      <!-- <img alt="example" style="width: 100%" :src="url" /> </a-layout-content>
      <a-button
        type="primary"
        @click="showmodal"
        :style="dataStype"
        >show</a-button
      >
      <a-modal
        v-model="show"
        height="100%"
        width="80%"
        title="Xem Trước File Excel"
      >
        <template slot="footer">
          <a-button key="back" @click="handleCancelPriview"> Hủy </a-button>
        </template>
        <template>
          <a-select
            style="width: 120px"
            v-model="datamaterial"
            :disabled="disable"
            @change="handleChange"
          >
            <a-select-option value="material1"> material1 </a-select-option>
            <a-select-option value="material2"> material2 </a-select-option>
            <a-select-option value="material3"> material3 </a-select-option>
            <a-select-option value="material4"> material4 </a-select-option>
          </a-select>
        </template>
        <template>
          <a-select
            style="width: 120px"
            v-model="dataframe"
            :disabled="disable"
            @change="handleChange1"
          >
            <a-select-option value="frame1"> frame1 </a-select-option>
            <a-select-option value="frame2"> frame2 </a-select-option>
            <a-select-option value="frame3"> frame3 </a-select-option>
            <a-select-option value="frame4"> frame4 </a-select-option>
          </a-select>
        </template>
        <template>
          <a-select
            style="width: 120px"
            v-model="dataheight"
            :disabled="disable"
            @change="handleChange2"
          >
            <a-select-option value="height1"> height1 </a-select-option>
            <a-select-option value="height2"> height2 </a-select-option>
            <a-select-option value="height3"> height3 </a-select-option>
            <a-select-option value="height4"> height4 </a-select-option>
          </a-select>
        </template>
      </a-modal> -->
      <div id="main-content" class="container">
        <div class="row">
          <div class="col-md-6">
            <form class="form-inline">
              <div class="form-group">
                <label for="connect">WebSocket connection:</label>
                <button
                  id="connect"
                  class="btn btn-default"
                  type="submit"
                  :disabled="connected == true"
                  @click.prevent="connect"
                >
                  Connect
                </button>
                <button
                  id="disconnect"
                  class="btn btn-default"
                  type="submit"
                  :disabled="connected == false"
                  @click.prevent="disconnect"
                >
                  Disconnect
                </button>
              </div>
            </form>
          </div>
          <div class="col-md-6">
            <form class="form-inline">
              <div class="form-group">
                <label for="name">What is your name?</label>
                <input
                  type="text"
                  id="name"
                  class="form-control"
                  v-model="send_message"
                  placeholder="Your name here..."
                />
              </div>
              <button
                id="send"
                class="btn btn-default"
                type="submit"
                @click.prevent="send"
              >
                Send
              </button>
            </form>
          </div>
        </div>
        <div class="row">
          <div class="col-md-12">
            <table id="conversation" class="table table-striped">
              <thead>
                <tr>
                  <th>Greetings</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(item, index) in received_messages" :key="index">
                  <td>{{ item }}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </a-layout>
  </div>
</template>
 <script>
// import attendanceService from "@/service/attendanceService.js";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
export default {
  name: "A",
  components: {},
  data() {
    return {
      url: "",
      result: {},
      show: false,
      dataSelect: [],
      disable: false,
      datamaterial: "",
      dataStype: { "margin-left": "5px" },
      dataframe: "",
      dataheight: "",
      dataForm: {
        id1: 0,
        id2: 0,
        name1: "",
        name2: "",
      },
      received_messages: [],
      send_message: null,
      connected: false,
    };
  },
  computed: {},
  created() {
    // this.loadImage();
  },
  beforeDestroy() {
    console.log("beforeDestroy");
    this.disconnect();
  },
  methods: {
    send() {
      console.log("Send message:" + this.send_message);
      if (this.stompClient && this.stompClient.connected) {
        // const msg = { name: this.send_message };
        this.stompClient.send(
          "/ws/hello",
          this.send_message,
          this.send_message
        );
      }
    },
    connect() {
      let username = JSON.parse(localStorage.getItem("user")).username;
      this.socket = new SockJS("http://localhost:8080/api/wse/hello");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect(
        { username: username },
        () => {
          this.connected = true;
          this.stompClient.subscribe("/users/queue/messages", (tick) => {
            if (this != null) {
              this.received_messages.push(tick);
            }
          });
        },
        (error) => {
          console.log(error);
          this.connected = false;
        }
      );
    },
    disconnect() {
      if (this.stompClient) {
        this.stompClient.disconnect();
      }
      this.connected = false;
    },
    tickleConnection() {
      this.connected ? this.disconnect() : this.connect();
    },

    // handleChange() {
    //   let data = {
    //     type: "material",
    //     id: this.datamaterial,
    //     name: "",
    //   };
    //   this.dataSelect.push(data);
    //   if (this.dataSelect.length == 2) {
    //     // co nghia la thang dau tien co the la F hoac H va thang thu 2 laf m
    //     this.dataForm.id1 = data.id;
    //     this.dataForm.name1 = data.name;

    //     this.dataForm.id2 = this.dataSelect[0].id;
    //     this.dataForm.name2 = this.dataSelect[0].name;
    //     //
    //     if (this.dataSelect[0].type == "frame") {
    //       console.log("call api height", this.dataForm);
    //     } else {
    //       console.log("call api frame", this.dataForm);
    //     }
    //   }
    //   if (this.dataSelect.length == 3) {
    //     console.log("đóng băng lại toàn bộ");
    //     this.disable = true;
    //   }
    // },
    // handleChange1() {
    //   let data = {
    //     type: "frame",
    //     id: this.dataframe,
    //     name: "",
    //   };
    //   this.dataSelect.push(data);

    //   if (this.dataSelect.length == 2) {
    //     if (this.dataSelect[0].type == "material") {
    //       this.dataForm.id1 = this.dataSelect[0].id;
    //       this.dataForm.name1 = this.dataSelect[0].name;
    //       this.dataForm.id2 = data.id;
    //       this.dataForm.name2 = data.name;
    //       console.log("call api height", this.dataForm);
    //     } else {
    //       this.dataForm.id1 = data.id;
    //       this.dataForm.name1 = data.name;
    //       this.dataForm.id2 = this.dataSelect[0].id;
    //       this.dataForm.name2 = this.dataSelect[0].name;
    //       console.log("call api material", this.dataForm);
    //     }
    //   }
    //   if (this.dataSelect.length == 3) {
    //     console.log("đóng băng lại toàn bộ");
    //     this.disable = true;
    //   }
    // },
    // handleChange2() {
    //   let data = {
    //     type: "height",
    //     id: this.dataheight,
    //     name: "",
    //   };
    //   this.dataSelect.push(data);
    //   if (this.dataSelect.length == 2) {
    //     this.dataForm.id1 = this.dataSelect[0].id;
    //     this.dataForm.name1 = this.dataSelect[0].name;
    //     this.dataForm.id2 = data.id;
    //     this.dataForm.name2 = data.name;

    //     if (this.dataSelect[0].type == "material") {
    //       console.log("đcall api frame", this.dataForm);
    //     } else {
    //       console.log("call api material", this.dataForm);
    //     }
    //   }
    //   if (this.dataSelect.length == 3) {
    //     console.log("đóng băng lại toàn bộ");
    //     this.disable = true;
    //   }
    // },
    // submit() {
    //   // call api submit
    // },
    // showmodal() {
    //   this.show = true;
    // },
    // handleCancelPriview() {
    //   this.show = false;
    // },
    // loadImage() {
    //   attendanceService
    //     .preview()
    //     .then((response) => {
    //       this.url = window.URL.createObjectURL(response.data);
    //     })
    //     .catch((e) => {
    //       console.log(e);
    //     });
    // },
  },
};
</script>

<style scoped>
/* back top */
.ant-back-top-inner {
  color: rgb(241, 237, 237);
  text-align: center;
}
</style>