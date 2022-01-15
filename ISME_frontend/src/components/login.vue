<template>
 <div id="login">
        <h1>Login</h1>
        <div class="input-group mb-4">
        <fieldset>
         <input type="text" name="username" v-model="username" placeholder="Username" />
         <input type="password" name="password" v-model="password" placeholder="Password" />
        <button class="btn btn-primary" type="button" v-on:click="login()">Login</button>
        </fieldset>
        </div>
        </div>
</template>

<script>
  import LoginService from "../services/LoginService";
  import router from "./../router.js";
  export default {
    data () {
        return {
            username: "",
            password: "",
            returnUrl: '',
            error: ''
        };
    },
    created () {
            if (LoginService.loggedUserValue) { 
                return router.push('/');
            }

            this.returnUrl = this.$route.query.returnUrl || '/';
    },
    methods: {
        login() {
            if(this.username != "" && this.password != "") {
                LoginService.login(this.username, this.password)     
            } else {
                alert("A username and password must be present!");
            }
        }
    }
};
</script>