import { createWebHistory, createRouter } from "vue-router";
import { Role } from "./Role";
import loginService from "./services/LoginService.js";

const routes = [
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
  const { authorize } = to.meta;
  const loggedUser = loginService.loggedUserValue;

  if (authorize) {
    if (!loggedUser) {
      return next({ path: '/login', query: { returnUrl: to.path } });
    }

    if ((authorize.length && !authorize.includes(loggedUser.role))) {
      return next({ path: '/' });
    }
  }
  next();
})

export default router;