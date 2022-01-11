<template>
  <div id="app">
    <nav class="navbar navbar-expand navbar-dark bg-dark">
      <router-link to="/" class="navbar-brand">FitnessCenter</router-link>
      <div class="navbar-nav mr-auto">
         <li class="nav-item">
          <router-link to="/login" v-if="!currentUser" class="nav-link">Login</router-link>
        </li>
        <li class="nav-item">
          <router-link to="/booking" v-if="isMember" class="nav-link">Book training session</router-link>
        </li>
        <li class="nav-item">
          <router-link to="/branch/register" v-if="isMember" class="nav-link">Register in branch</router-link>
        </li>
        <li class="nav-item">
          <router-link to="/top-trainers" v-if="currentUser" class="nav-link">Top Trainers</router-link>
        </li>
        <li class="nav-item">
          <router-link to="/loyal-members" v-if="currentUser" class="nav-link">Loyal Members</router-link>
        </li>
        <li class="nav-item" v-if="currentUser">
           <a @click="logout" href=# class="nav-link">Logout </a>
        </li>
      </div>
    </nav>

    <div class="container mt-3">
      <router-view/>
    </div>
  </div>
</template>

<script>
import loginService from './services/LoginService';
import router from "./router.js";
import { Role } from './Role';

export default {
  name: "app",
  data() {
    return{
      currentUser: null
    };
  },
  computed: {
    isEmployee() {
      return this.currentUser && this.currentUser.role === Role.Employee;
    },
    isMember(){
      return this.currentUser && this.currentUser.role === Role.Member;
    }
  },
  created () {
    loginService.currentUser.subscribe(p => this.currentUser = p);
  },
  methods: {
    logout () {
      loginService.logout();
      router.push('/login');
    }
  }
};
</script>