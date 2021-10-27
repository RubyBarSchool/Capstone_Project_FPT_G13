import Vue from 'vue';
import Router from 'vue-router';
import Home from './pages/Home.vue';
import Login from './pages/Login.vue';
Vue.use(Router);

export const router = new Router({
    routes: [{
            path: '/',
            name: 'home',
            component: Home
        },
        {
            path: '/home',
            component: Home
        },
        {
            path: '/login',
            component: Login
        },
        {
            path: '/admin',
            name: 'admin',
            // lazy-loaded
            component: () =>
                import ('./pages/Admin.vue')
        },
        {
            path: '/user',
            name: 'User',
            // lazy-loaded
            component: () =>
                import ('./pages/User.vue')
        },
        {
            path: '/attendance',
            name: 'Attendance',
            // lazy-loaded
            component: () =>
                import ('./pages/Attendance.vue')
        },
        {
            path: '/viewattendance',
            name: 'ViewAttendance',
            // lazy-loaded
            component: () =>
                import ('./pages/ViewAttendance.vue')
        },
        {
            path: '/position',
            name: 'Position',
            // lazy-loaded
            component: () =>
                import ('./pages/Position.vue')
        },
        {
            path: '/pageforbiden',
            name: 'PageForbiden',
            // lazy-loaded
            component: () =>
                import ('./pages/PageForbiden.vue')
        },
        {
            path: '*',
            name: 'PageNotFound',
            // lazy-loaded
            component: () =>
                import ('./pages/PageNotFound.vue')
        },
        {
            path: '/company',
            name: 'Company',
            // lazy-loaded
            component: () =>
                import ('./pages/Company.vue')
        },
        {
            path: '/color',
            name: 'Color',
            // lazy-loaded
            component: () =>
                import ('./pages/Color.vue')
        },
    ]
});

router.beforeEach((to, from, next) => {
    const publicPages = ['/login'];
    const authRequired = !publicPages.includes(to.path);
    const loggedIn = localStorage.getItem('user');
    if (authRequired && !loggedIn) {
        next('/login');
    } else {
        next();
    }
});

// Router.beforeEach(async (to, from, next) => {
//     if (getToken()) {
//       let userInfo = store.state.user.userInfo;
//       if (!userInfo.name) {
//         try {
//           await store.dispatch("GetUserInfo")
//           await store.dispatch('updateAccessMenu')
//           if (to.path === '/login') {
//             next({ name: 'home_index' })
//           } else {
//             //Util.toDefaultPage([...routers], to.name, router, next);
//             next({ ...to, replace: true })//菜单权限更新完成,重新进一次当前路由
//           }
//         }  
//         catch (e) {
//           if (whiteList.indexOf(to.path) !== -1) { // 在免登录白名单，直接进入
//             next()
//           } else {
//             next('/login')
//           }
//         }
//       } else {
//         if (to.path === '/login') {
//           next({ name: 'home_index' })
//         } else {
//           if (hasPermission(to, store.getters.accessMenu)) {
//             Util.toDefaultPage(store.getters.accessMenu,to, routes, next);
//           } else {
//             next({ path: '/403',replace:true })
//           }
//         }
//       }
//     } else {
//       if (whiteList.indexOf(to.path) !== -1) { // 在免登录白名单，直接进入
//         next()
//       } else {
//         next('/login')
//       }
//     }
//     let menu = Util.getMenuByName(to.name, store.getters.accessMenu);
//     Util.title(menu.title);
//   });