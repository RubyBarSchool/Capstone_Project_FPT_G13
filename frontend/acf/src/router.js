import Vue from "vue";
import Router from "vue-router";
import Login from "./pages/Login.vue";
import layout from "./layouts/Slider.vue";

Vue.use(Router);

export const router = new Router({
    routes: [{
            path: "/login",
            component: Login,
        },
        {
            path: "/",
            name: "rootLayOut",
            component: layout,
            children: [{
                    path: "admin",
                    name: "admin",
                    // lazy-loaded
                    component: () =>
                        import ("./pages/Admin.vue"),
                }, {
                    path: "user",
                    name: "User",
                    // lazy-loaded
                    component: () =>
                        import ("./pages/User.vue"),
                },
                {
                    path: "attendance",
                    name: "Attendance",
                    // lazy-loaded
                    component: () =>
                        import ("./pages/Attendance.vue"),
                },
                {
                    path: "viewattendance",
                    name: "ViewAttendance",
                    // lazy-loaded
                    component: () =>
                        import ("./pages/ViewAttendance.vue"),
                },
                {
                    path: "position",
                    name: "Position",
                    // lazy-loaded
                    component: () =>
                        import ("./pages/Position.vue"),
                }, {
                    path: "company",
                    name: "Company",
                    // lazy-loaded
                    component: () =>
                        import ("./pages/Company.vue"),
                },
                {
                    path: "advancesalary",
                    name: "AdvanceSalary",
                    // lazy-loaded
                    component: () =>
                        import ("./pages/AdvanceSalary.vue"),
                },
                {
                    path: "acceptadvancesalary",
                    name: "AcceptAdvanceSalary",
                    // lazy-loaded
                    component: () =>
                        import ("./pages/AcceptAdvanceSalary.vue"),
                },
                {
                    path: "leaveapplication",
                    name: "LeaveApplication",
                    // lazy-loaded
                    component: () =>
                        import ("./pages/LeaveApplication.vue"),
                },
                {
                    path: "acceptleaveapplication",
                    name: "AcceptLeaveApplication",
                    // lazy-loaded
                    component: () =>
                        import ("./pages/AcceptLeaveApplication.vue"),
                },
                {
                    path: "viewsalary",
                    name: "ViewSalary",
                    // lazy-loaded
                    component: () =>
                        import ("./pages/ViewSalary.vue"),
                },
                {
                    path: "acceptsalaryadmin",
                    name: "AccecptSalaryAdmin",
                    // lazy-loaded
                    component: () =>
                        import ("./pages/AccecptSalaryAdmin.vue"),
                },
                {
                    path: "historysalaryadmin",
                    name: "HistorySalaryAdmin",
                    // lazy-loaded
                    component: () =>
                        import ("./pages/HistorySalaryAdmin.vue"),
                },
                {
                    path: "punishadmin",
                    name: "PunishAdmin",
                    // lazy-loaded
                    component: () =>
                        import ("./pages/PunishAdmin.vue"),
                },
                {
                    path: "bonusadmin",
                    name: "BonusAdmin",
                    // lazy-loaded
                    component: () =>
                        import ("./pages/BonusAdmin.vue"),
                },
                {
                    path: "viewbonuspunish",
                    name: "ViewBonusPunish",
                    // lazy-loaded
                    component: () =>
                        import ("./pages/ViewBonusPunish.vue"),
                },
                {
                    path: "unitadmin",
                    name: "UnitAdmin",
                    // lazy-loaded
                    component: () =>
                        import ("./pages/UnitAdmin.vue"),
                },
                {
                    path: "groupcoverplate",
                    name: "GroupCoverPlate",
                    // lazy-loaded
                    component: () =>
                        import ("./pages/GroupCoverPlate.vue"),
                },
                {
                    path: "groupmaterial",
                    name: "GroupMaterial",
                    // lazy-loaded
                    component: () =>
                        import ("./pages/GroupMaterial.vue"),
                },
                {
                    path: "frameadmin",
                    name: "FrameAdmin",
                    // lazy-loaded
                    component: () =>
                        import ("./pages/FrameAdmin.vue"),
                }, {
                    path: "a",
                    name: "a",
                    // lazy-loaded
                    component: () =>
                        import ("./pages/A.vue"),
                },
                {
                    path: "coverplate",
                    name: "CoverPlate",
                    // lazy-loaded
                    component: () =>
                        import ("./pages/CoverPlate.vue"),
                }, {
                    path: "materialsuggest",
                    name: "MaterialSuggest",
                    // lazy-loaded
                    component: () =>
                        import ("./pages/MaterialSuggest.vue"),
                },
                {
                    path: "materialadmin",
                    name: "MaterialAdmin",
                    // lazy-loaded
                    component: () =>
                        import ("./pages/MaterialAdmin.vue"),
                },
                {
                    path: "contact",
                    name: "Contact",
                    // lazy-loaded
                    component: () =>
                        import ("./pages/Contact.vue"),
                },
                {
                    path: "viewdetailcontact",
                    name: "ViewDetailContact",
                    // lazy-loaded
                    component: () =>
                        import ("./pages/ViewDetailContact.vue"),
                },
                {
                    path: "productionorder",
                    name: "ProductionOrder",
                    // lazy-loaded
                    component: () =>
                        import ("./pages/ProductionOrder.vue"),
                },
                {
                    path: "viewwork",
                    name: "ViewWorkEmployee",
                    // lazy-loaded
                    component: () =>
                        import ("./pages/ViewWorkEmployee.vue"),
                }, {
                    path: "contactmoney",
                    name: "ContactMoney",
                    // lazy-loaded
                    component: () =>
                        import ("./pages/ContactMoney.vue"),
                },
                {
                    path: "slider",
                    name: "Slider",
                    // lazy-loaded
                    component: () =>
                        import ("./layouts/Slider.vue"),
                }, {
                    path: 'height',
                    name: 'Height',
                    component: () =>
                        import ("./pages/Height.vue")
                },
                {
                    path: 'profile',
                    name: 'profile',
                    component: () =>
                        import ("./pages/Profile.vue")
                },
                {
                    path: "dashboard",
                    name: "Dashboard",
                    // lazy-loaded
                    component: () =>
                        import ("./pages/Dashboard.vue"),
                },
            ]
        },
        {
            path: "/pageforbiden",
            name: "PageForbiden",
            // lazy-loaded
            component: () =>
                import ("./pages/PageForbiden.vue"),
        },
        {
            path: "*",
            name: "PageNotFound",
            // lazy-loaded
            component: () =>
                import ("./pages/PageNotFound.vue"),
        },

    ],
});

router.beforeEach((to, from, next) => {
    const publicPages = ["/login"];
    const authRequired = !publicPages.includes(to.path);
    const loggedIn = localStorage.getItem("user");
    if (authRequired && !loggedIn) {
        next("/login");
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