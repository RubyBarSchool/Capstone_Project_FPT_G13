import Vue from 'vue'
import App from './App.vue'
// import * as VeeValidate from 'vee-validate';
import Vuex from 'vuex';
import { router } from './router';
// import 'bootstrap';
// import 'bootstrap/dist/css/bootstrap.min.css';

import store from './store';
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
// import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
// import { library } from '@fortawesome/fontawesome-svg-core';
// import {
//     faHome,
//     faUser,
//     faUserPlus,
//     faSignInAlt,
//     faSignOutAlt
// } from '@fortawesome/free-solid-svg-icons';
// library.add(faHome, faUser, faUserPlus, faSignInAlt, faSignOutAlt);
Vue.config.productionTip = false
    // Vue.use(VeeValidate);
    // Vue.component('font-awesome-icon', FontAwesomeIcon);
Vue.use(Vuex);
Vue.use(Antd);

new Vue({
    router,
    store,
    render: h => h(App),
}).$mount('#app')