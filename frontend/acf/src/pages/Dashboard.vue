<template>
  <div class="Dashboard">
    <a-back-top :style="{ width: '5vh', height: '15vh' }" />
    <div v-if="checkshow('SP_ADMIN')" class="grid grid-responsive">
      SP_ADMIN
      <div class="card info-1">
        Số người đang hoạt động {{ this.listUserOnline.length }}
        <img
          src="../assets/graph-messages.svg"
          alt="sapphire"
          class="summary-box-graph"
        />
      </div>
      <div class="card info-2">
        Số người chưa có tài khoản {{ countEmployeeHaveNotAccount }}
        <img
          src="../assets/graph-checkins.svg"
          alt="sapphire"
          class="summary-box-graph"
        />
      </div>
    </div>
    <div v-if="checkshow('ADMIN')" class="grid grid-responsive">
      ADMIN
      <div class="card info-1">
        {{
          dashboardAdmin.productNotProductionOrder != 0
            ? " Sản phẩm chưa được tạo lệnh sản xuất : " +
              dashboardAdmin.productNotProductionOrder
            : "Không có sản phẩm nào chưa được làm"
        }}
        <img
          src="../assets/graph-messages.svg"
          alt="sapphire"
          class="summary-box-graph"
        />
      </div>
      <div class="card info-2">
        {{
          dashboardAdmin.contactDone != 0
            ? "Số hợp đồng chờ bàn giao : " + dashboardAdmin.contactDone
            : "Không có hợp dồng nào chờ bàn giao"
        }}
        <img
          src="../assets/graph-checkins.svg"
          alt="sapphire"
          class="summary-box-graph"
        />
      </div>
      <div class="card info-3">
        {{
          dashboardAdmin.producttionOrderConfirmAdmin != 0
            ? "Số lệnh sản xuất chờ xác nhận hoàn thành: " +
              dashboardAdmin.producttionOrderConfirmAdmin
            : "Không có lệnh sản xuất nào đợi xác nhận"
        }}
        <img
          src="../assets/graph-filessynced.svg"
          alt="sapphire"
          class="summary-box-graph"
        />
      </div>
      <div class="card info-4">
        {{
          dashboardAdmin.producttionOrderConfirmEmployee != 0
            ? "Số lệnh sản xuất chờ xác nhận hoàn thành: " +
              dashboardAdmin.producttionOrderConfirmEmployee
            : "Không có lệnh sản xuất nào đợi xác nhận"
        }}
        <img
          src="../assets/graph-filessynced.svg"
          alt="sapphire"
          class="summary-box-graph"
        />
      </div>
      <div class="card info-5">
        {{
          dashboardAdmin.advaceSalaryConfirm != 0
            ? "Số đơn xin ứng lương: " +
              dashboardAdmin.producttionOrderConfirmAdmin
            : "Không có lệnh sản xuất nào đợi nhân viên xác nhận xác nhận"
        }}
        <img
          src="../assets/graph-filessynced.svg"
          alt="sapphire"
          class="summary-box-graph"
        />
      </div>
      <div class="card info-6">
        {{
          dashboardAdmin.personaLeaveApplicationConfirm != 0
            ? "Số đơn xin nghỉ đợi xác nhận: " +
              dashboardAdmin.personaLeaveApplicationConfirm
            : "Không có đơn xin nghỉ"
        }}
        <img
          src="../assets/graph-filessynced.svg"
          alt="sapphire"
          class="summary-box-graph"
        />
      </div>
    </div>
    <div v-if="checkshow('EMPLOYEE')" class="grid grid-responsive">
      Employee
      <div class="card info-1">
        {{
          dashboardEmployee.timeKeep != 0
            ? "Tổng số công trong tháng là : " + dashboardEmployee.timeKeep
            : "Không có ghi nhận chấm công"
        }}
        <img
          src="../assets/graph-messages.svg"
          alt="sapphire"
          class="summary-box-graph"
        />
      </div>
      <div class="card info-2">
        {{
          dashboardEmployee.workConfirm != 0
            ? "Số công việc cần xác nhận : " + dashboardEmployee.workConfirm
            : "Không có công việc nào được giao"
        }}
        <img
          src="../assets/graph-checkins.svg"
          alt="sapphire"
          class="summary-box-graph"
        />
      </div>
      <div class="card info-3">
        {{
          dashboardEmployee.workDone != 0
            ? "Số công việc đã hoàn thành thong tháng qua: " +
              dashboardEmployee.workDone
            : "Không có công việc nào được hoàn thành trong tháng qua"
        }}
        <img
          src="../assets/graph-filessynced.svg"
          alt="sapphire"
          class="summary-box-graph"
        />
      </div>
      <div class="card info-4">
        {{
          dashboardEmployee.bonus != 0
            ? "Số quyết định khen thưởng: " + dashboardEmployee.bonus
            : "Không có quyết định khen thưởng nào"
        }}
        <img
          src="../assets/graph-filessynced.svg"
          alt="sapphire"
          class="summary-box-graph"
        />
      </div>
      <div class="card info-5">
        {{
          dashboardEmployee.punish != 0
            ? "Số quyết định kỷ luật : " + dashboardEmployee.punish
            : "Không có quyết định kỉ luật nào"
        }}
        <img
          src="../assets/graph-filessynced.svg"
          alt="sapphire"
          class="summary-box-graph"
        />
      </div>
      <!-- <div class="card header">header</div> -->
      <div class="card list">
        <a-table
          :columns="columns"
          :data-source="dataSourceTable"
          :pagination="false"
          :rowKey="
            (record, index) => {
              return index;
            }
          "
        >
        </a-table>
      </div>
      <div class="card chart">
        <apexchart
          type="bar"
          height="350"
          :options="chartOptions"
          :series="series"
        ></apexchart>
      </div>
    </div>
  </div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import dashboardService from "../service/dashboardService";
export default {
  name: "Dashboard",
  data() {
    return {
      dataSourceTable: [],
      columns: [
        {
          title: "Tài khoản",
          width: 100,
          dataIndex: "username",
          key: "username",
          fixed: "left",
        },
        {
          title: "Tên nhân viên",
          dataIndex: "fullName",
          key: "fullName",
          width: 100,
        },
        {
          title: "Số lượng công việc",
          dataIndex: "numberWork",
          key: "numberWork",
          width: 150,
        },
      ],
      dashboardAdmin: {
        advaceSalaryConfirm: 0,
        contactDone: 0,
        personaLeaveApplicationConfirm: 0,
        productNotProductionOrder: 0,
        producttionOrderConfirmAdmin: 0,
        producttionOrderConfirmEmployee: 0,
      },
      dashboardEmployee: {
        bonus: 0,
        punish: 0,
        timeKeep: 0,
        workConfirm: 0,
        workDone: 0,
      },
      listUserOnline: [],
      countEmployeeHaveNotAccount: "Không có nhân viên nào chưa có tài khoản",

      series: [],
      chartOptions: {
        chart: {
          height: 350,
          type: "bar",
        },
        colors: ["#00E396"],
        plotOptions: {
          bar: {
            horizontal: true,
          },
        },
        title: {
          text: "Tiến độ hoàn thành công việc",
        },
        dataLabels: {
          formatter: function (val, opt) {
            const goals =
              opt.w.config.series[opt.seriesIndex].data[opt.dataPointIndex]
                .goals;

            if (goals && goals.length) {
              return `${val} / ${goals[0].value}`;
            }
            return val;
          },
        },
        legend: {
          show: true,
          showForSingleSeries: true,
          customLegendItems: ["Hiện tại", "Mục tiêu"],
          markers: {
            fillColors: ["#00E396", "#775DD0"],
          },
        },
      },
    };
  },
  created() {
    this.connectWebsoket();
    this.getNumberEmployeeHaveNotAccount();
    this.countDashboardAdmin();
    this.countDashboardEmployee();
    this.reportContact();
    this.topEmployee();
  },
  destroyed() {
    this.disconnect();
  },
  methods: {
    topEmployee() {
      dashboardService
        .topEmployee()
        .then((response) => {
          this.dataSourceTable = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    reportContact() {
      dashboardService
        .reportContact()
        .then((response) => {
          let arrData = [];
          for (let i = 0; i < response.data.data.length; i++) {
            if (response.data.data[i].y >= response.data.data[i].value) {
              let datax = {
                x:
                  response.data.data[i].x +
                  "(" +
                  response.data.data[i].total +
                  ")",
                y: response.data.data[i].y,
                goals: [
                  {
                    name: "Mục tiêu",
                    value: response.data.data[i].value,
                    strokeWidth: 10,
                    strokeHeight: 0,
                    strokeColor: "#775DD0",
                    strokeLineCap: "round",
                  },
                ],
              };
              arrData.push(datax);
            } else {
              let datax = {
                x:
                  response.data.data[i].x +
                  "(" +
                  response.data.data[i].total +
                  ")",
                y: response.data.data[i].y,
                goals: [
                  {
                    name: "Mục tiêu",
                    value: response.data.data[i].value,
                    strokeWidth: 5,
                    strokeHeight: 10,
                    strokeColor: "#775DD0",
                  },
                ],
              };
              arrData.push(datax);
            }
          }
          let data = {
            name: "Hiện tại",
            data: arrData,
          };
          this.series.push(data);
          console.log("series", this.series);
        })
        .catch((e) => {
          console.log(e);
        });
    },

    countDashboardAdmin() {
      dashboardService
        .countDashboardAdmin()
        .then((response) => {
          this.dashboardAdmin = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },

    countDashboardEmployee() {
      dashboardService
        .countDashboardEmployee()
        .then((response) => {
          this.dashboardEmployee = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },

    getNumberEmployeeHaveNotAccount() {
      dashboardService
        .getNumberEmployeeHaveNotAccount()
        .then((response) => {
          this.listUserOnline = response.data.data;
          if (response.data.data != 0) {
            this.countEmployeeHaveNotAccount = "" + response.data.data;
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
    checkshow(value) {
      let dataroles = JSON.parse(localStorage.getItem("user")).roles;
      if (dataroles.indexOf(value) != -1) {
        return true;
      }
      return false;
    },
    disconnect() {
      if (this.stompClient) {
        this.stompClient.disconnect();
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
          this.stompClient.subscribe("/users/queue/online", (tick) => {
            if (this != null) {
              this.listUserOnline = JSON.parse(tick.body);
            }
          });
          if (this.stompClient && this.stompClient.connected) {
            this.stompClient.send("/ws/online");
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
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
html {
  font-size: 62.5%;
  font-family: Arial, sans-serif;
}
.grid {
  width: 100%;
  height: 100%;
  padding: 1rem;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-template-rows: auto auto 1fr;
  grid-template-areas: "h h h" "i1 i2 i3" "l c c";
  gap: 2.5rem;
}
.card {
  padding: 4rem;
  font-size: 1.6rem;
  font-weight: bold;
  box-shadow: 0 2px 10px 0 rgb(0 0 0 / 16%), 0 2px 5px 0 rgb(0 0 0 / 26%);
}
.header {
  grid-area: h;
}

.summary-box-graph {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
}
.info-1 {
  border-top: 4px solid #0097a7;
  background-color: #2cbccf;
  grid-area: i1;
}
.info-2 {
  border-top: 4px solid #ad1457;
  background-color: #ee2b6c;
  grid-area: i2;
}
.info-3 {
  border-top: 4px solid #f47b08;
  background-color: #fab412;
  grid-area: i3;
}
.list {
  grid-area: l;
}
.chart {
  grid-area: c;
}
.header {
  min-height: 15rem;
}
.list,
.chart {
  min-height: 20rem;
}
@media (max-width: 800px) {
  .grid-responsive {
    grid-template-areas: "h h h" "i1 i2 i3" "l l l" "c c c";
  }
}
@media (max-width: 630px) {
  .grid-responsive {
    grid-template-columns: 1fr;
    grid-template-areas: "h" "i1" "i2" "i3" "l" "c";
  }
}
</style>