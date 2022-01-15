<template>
  <div id="app">
    <nav class="navbar navbar-expand navbar-dark bg-dark">
      <router-link to="/" class="navbar-brand">FitnessCenter</router-link>
      <div class="navbar-nav mr-auto">
        <li class="nav-item">
          <router-link to="/login" v-if="!loggedUser" class="nav-link"
            >Login</router-link
          >
        </li>
        <li class="nav-item">
          <router-link to="/booking" v-if="isMember" class="nav-link"
            >Book training session</router-link
          >
        </li>
        <li class="nav-item">
          <router-link to="/branch/register" v-if="isMember" class="nav-link"
            >Register in branch</router-link
          >
        </li>
        <li class="nav-item">
          <router-link to="/top-trainers" v-if="loggedUser" class="nav-link"
            >Top Trainers</router-link
          >
        </li>
        <li class="nav-item">
          <router-link to="/loyal-members" v-if="loggedUser" class="nav-link"
            >Loyal Members</router-link
          >
        </li>
        <li class="nav-item" v-if="loggedUser">
          <a @click="logout" href="#" class="nav-link">Logout </a>
        </li>
      </div>
    </nav>

    <div class="container mt-3">
      <router-view />
    </div>
  </div>
</template>

<script>
import loginService from "./services/LoginService";
import router from "./router.js";
import { Role } from "./Role";

export default {
  name: "app",
  data() {
    return {
      loggedUser: null,
    };
  },
  computed: {
    isEmployee() {
      return this.loggedUser && this.loggedUser.role === Role.Employee;
    },
    isMember() {
      return this.loggedUser && this.loggedUser.role === Role.Member;
    },
  },
  created() {
    loginService.loggedUser.subscribe((p) => (this.loggedUser = p));
  },
  methods: {
    logout() {
      loginService.logout();
      router.push("/login");
    },
  },
};
</script>