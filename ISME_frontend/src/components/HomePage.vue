<template>
  <div>
    <h1>Home</h1>
    <div>
      <button class="btn btn-primary" type="button" v-on:click="fillDatabase()">
        fill Database
      </button>
    </div>
    <div>
      <button
        class="btn btn-primary"
        type="button"
        v-on:click="migrateDatabase()"
      >
        migrate Database
      </button>
    </div>
  </div>
</template>

<script>
import FitnessCenterService from "../services/FitnessCenterService";
export default {
  methods: {
    fillDatabase() {
      FitnessCenterService.fillDatabase().catch((e) => {
        console.log(e);
      });
    },
    migrateDatabase() {
      FitnessCenterService.migrateDatabase().catch((e) => {
        console.log(e);
      });
    },
  },
};
</script>