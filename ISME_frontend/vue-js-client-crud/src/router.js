import {createWebHistory, createRouter} from "vue-router";
import { Role } from "./Role";
import loginService from "./services/LoginService.js";

const routes =  [
  {
    path: "/login",
    name: "login",
    component: () => import("./components/login"),
  },
  {
    path: "/",
    name: "home",
    component: () => import("./components/HomePage"),
  },
  {
    path: "/branch/register",
    name: "registerBranch",
    component: () => import("./components/registerBranch"),
    meta: { authorize: [Role.Member] }
  },
  {
    path: "/booking",
    name: "bookTrainingSession",
    component: () => import("./components/bookTrainingSession"),
    meta: { authorize: [Role.Member] }
  },
  {
    path: "/top-trainers",
    name: "topTrainers",
    component: () => import("./components/topTrainers"),
    meta: { authorize: [] }
  },
  {
    path: "/loyal-members",
    name: "loyalMembers",
    component: () => import("./components/loyalMembers"),
    meta: { authorize: [] }
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  // redirect to login page if not logged in and trying to access a restricted page
  const { authorize } = to.meta;
  const currentUser = loginService.currentUserValue;

  if (authorize) {
      if (!currentUser) {
          // not logged in so redirect to login page with the return url
          return next({ path: '/login', query: { returnUrl: to.path } });
      }

      // check if route is restricted by role
      if ((authorize.length && !authorize.includes(currentUser.role))) {
          // role not authorised so redirect to home page
          return next({ path: '/' });
      }
  }
  next();
})


export default router;