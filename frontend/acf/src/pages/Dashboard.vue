<template>
  <div class="Dashboard">
    <a-back-top :style="{ width: '5vh', height: '15vh' }" />
    <div class="grid grid-responsive ">
      <div class="card info-1">
        info 1
        <img
          src="../assets/graph-messages.svg"
          alt="sapphire"
          class="summary-box-graph"
        />
      </div>
      <div class="card info-2">
        info 2
        <img
          src="../assets/graph-checkins.svg"
          alt="sapphire"
          class="summary-box-graph"
        />
      </div>
      <div class="card info-3">
        info 3
        <img
          src="../assets/graph-filessynced.svg"
          alt="sapphire"
          class="summary-box-graph"
        />
      </div>
      <!-- <div class="card header">header</div> -->
      <div class="card list">list</div>
      <div class="card chart">chart</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Dashboard",
  data() {
    return {};
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