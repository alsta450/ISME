<template>
 <div class="list row">
    <div class="col-md-6">
      <h4>Loyal Members</h4>
      <table data-link="row">
        <thead>
          <tr>
            <th>Member name</th>
            <th>Trainer names</th>
            <th>Branch</th>
            <th>street</th>
            <th>city</th>
            <th>zip</th>
            <th>Total Sessions</th>
            <th>Total Price</th>
          </tr>
        </thead>
        <tr v-for="(loyalMember, index) in loyalMembers" :key="index">
          <td>{{loyalMember.memberName}}</td>
          <td>{{trainerNames[index]}}</td>
          <td>{{loyalMember.branchName}}</td>
          <td>{{loyalMember.street}}</td>
          <td>{{loyalMember.city}}</td>
          <td>{{loyalMember.zip}}</td>
          <td>{{loyalMember.totalSessions}}</td>
          <td>{{loyalMember.totalPrice}}</td>
        </tr>
      </table>
    </div>
 </div>
</template>

<script>
import FitnessCenterService from '../services/FitnessCenterService';
export default {
  data() {
    return {
      loyalMembers: [],
      trainerNames: []
    };
  },
  methods: {
    loyalstMembers() {
      FitnessCenterService.loyalstMembers()
        .then((response) => {
          this.loyalMembers = response.data;
          for(const trainer of this.loyalMembers){
            this.trainerNames.push(trainer.trainerName + " ");
          }
          console.log(this.loyalMembers);
        })
        .catch((e) => {
          console.log(e);
        });
    },
  },
  created() {
    this.loyalstMembers();
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
