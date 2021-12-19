<template>
  <div class="attendance">
    <a-layout :style="{ background: 'white' }">
      <div id="chart">
        <apexchart
          type="bar"
          height="350"
          :options="chartOptions"
          :series="series"
        ></apexchart>
      </div>
    </a-layout>
  </div>
</template>
 <script>
// import attendanceService from "@/service/attendanceService.js";
import dashboardService from "../service/dashboardService";
export default {
  name: "A",
  components: {},
  data() {
    return {
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
  computed: {},
  created() {
    // this.loadImage();
    this.reportContact();
  },
  beforeDestroy() {},
  methods: {
    reportContact() {
      dashboardService
        .reportContact()
        .then((response) => {
          let arrData = [];
          for (let i = 0; i < response.data.data.length; i++) {
            if (response.data.data[i].y >= response.data.data[i].value) {
              let datax = {
                x: response.data.data[i].x+'('+response.data.data[i].total+')',
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
                x: response.data.data[i].x+'('+response.data.data[i].total+')',
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
            data: arrData
          };
          this.series.push(data);
          console.log("series",this.series);
        })
        .catch((e) => {
          console.log(e);
        });
    },
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