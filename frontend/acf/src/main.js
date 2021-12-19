import Vue from 'vue'
import App from './App.vue'
import Vuex from 'vuex';
import { router } from './router';
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import store from './store';
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { fas } from '@fortawesome/free-solid-svg-icons';
import { far } from '@fortawesome/free-regular-svg-icons';
import { SpreadsheetPlugin } from "@syncfusion/ej2-vue-spreadsheet";
import VueResource from 'vue-resource'
import VueApexCharts from 'vue-apexcharts'
Vue.use(VueApexCharts)
Vue.component('apexchart', VueApexCharts)
Vue.use(VueResource)
Vue.use(SpreadsheetPlugin);
library.add(fas, far);

Vue.config.productionTip = false
Vue.component('font-awesome-icon', FontAwesomeIcon);
Vue.use(Vuex);
Vue.use(Antd);

new Vue({
    router,
    store,
    render: h => h(App),
}).$mount('#app')