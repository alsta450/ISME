<template>
  <div class="list row">
    <div class="col-md-6">
      <h4>Top Trainers</h4>
      <table data-link="row">
        <thead>
          <tr>
            <th>Trainer name</th>
            <th>Member names</th>
            <th>Branch</th>
            <th>street</th>
            <th>city</th>
            <th>zip</th>
            <th>Total Sessions</th>
          </tr>
        </thead>
        <tr v-for="(topTrainer, index) in topTrainer" :key="index">
          <td>{{ topTrainer.trainerName }}</td>
          <td>{{ memberNames[index] }}</td>
          <td>{{ topTrainer.branchName }}</td>
          <td>{{ topTrainer.street }}</td>
          <td>{{ topTrainer.city }}</td>
          <td>{{ topTrainer.zip }}</td>
          <td>{{ topTrainer.totalSessions }}</td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
import FitnessCenterService from "../services/FitnessCenterService";
export default {
  data() {
    return {
      topTrainer: [],
      memberNames: [],
    };
  },
  methods: {
    topTrainers() {
      FitnessCenterService.topTrainers()
        .then((response) => {
          this.topTrainer = response.data;
          for (const trainers of this.topTrainer) {
            var members = [];
            var countMembers = 0;
            for (const member of trainers.memberName) {
              ++countMembers;
              members.push(member);
              if (countMembers % 10 == 0) members.push("\n");
              if (trainers.memberName.length == countMembers) {
                this.memberNames.push(members + "");
              }
            }
          }
          console.log(this.topTrainer);
        })
        .catch((e) => {
          console.log(e);
        });
    },
  },
  created() {
    this.topTrainers();
  },
};
</script>

<style>
table {
  border: thin solid black;
  border-collapse: collapse;
}
th,
td {
  border: thin solid black;
  padding: 3px;
}
td:last-child {
  text-align: center;
}
</style>
