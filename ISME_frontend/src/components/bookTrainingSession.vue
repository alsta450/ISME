<template>
  <div id="app">
    <h2>Book Training Session</h2>
    <div class="aselect" :data-value="branchValue" :data-list="list">
      <div class="selector" @click="toggleBranchSelector()">
        <div class="label">
          <span>{{ branchValue }}</span>
        </div>
        <div class="arrow" :class="{ expanded: visibleBranchArrow }"></div>
        <div :class="{ hidden: !visibleBranchArrow, visibleBranchArrow }">
          <ul>
            <li
              :clsass="{ current: item === branchValue }"
              v-for="item in dropDownBranches"
              :key="item"
              @click="selectBranch(item)"
            >
              {{ item }}
            </li>
          </ul>
        </div>
      </div>
    </div>

    <div class="aselect" :data-value="employeeValue" :data-list="list">
      <div class="selector" @click="toggleEmployeeSelector()">
        <div class="label">
          <span>{{ employeeValue }}</span>
        </div>
        <div class="arrow" :class="{ expanded: visibleEmployeeArrow }"></div>
        <div :class="{ hidden: !visibleEmployeeArrow, visibleEmployeeArrow }">
          <ul>
            <li
              :clsass="{ current: item === employeeValue }"
              v-for="item in dropDownEmployees"
              :key="item"
              @click="selectEmployee(item)"
            >
              {{ item }}
            </li>
          </ul>
        </div>
      </div>
    </div>
    <div>
      <input
        id="duration"
        name="duration"
        type="number"
        class="form-control"
        placeholder="duration"
      />
      <label id="price">Kosten: €0 </label>
    </div>
    <button
      class="btn btn-primary"
      type="button"
      v-on:click="bookTrainingSession()"
    >
      book Session
    </button>
    <div class="col-md-6">
      <h4>Training Sessions</h4>
      <table data-link="row">
        <thead>
          <tr>
            <th>Member svnr</th>
            <th>Employee svnr</th>
            <th>Price</th>
            <th>Duration</th>
          </tr>
        </thead>
        <tr v-for="trainingsession in trainingsessions" :key="trainingsession">
          <td>{{ trainingsession.member_name }}</td>
          <td>{{ trainingsession.trainer_name }}</td>
          <td>{{ trainingsession.price }}</td>
          <td>{{ trainingsession.duration }}</td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
import FitnessCenterService from "../services/FitnessCenterService.js";
import LoginService from "../services/LoginService";

var duration = null;
var price = null;
export default {
  el: "#app",
  name: "aselect",
  data() {
    return {
      branchValue: "Select a Branch",
      dropDownBranches: [],
      branches: null,
      branch: "",
      visibleBranchArrow: false,

      employeeValue: "Select an Employee",
      dropDownEmployees: [],
      employees: [],
      employee: "",
      visibleEmployeeArrow: false,

      trainingsessions: [],
    };
  },
  methods: {
    toggleBranchSelector() {
      this.visibleBranchArrow = !this.visibleBranchArrow;
    },
    toggleEmployeeSelector() {
      this.visibleEmployeeArrow = !this.visibleEmployeeArrow;
    },
    selectBranch(option) {
      this.branchValue = option;
      this.branch = option;
      this.getEmployeesForBranch();
    },
    selectEmployee(option) {
      this.employeeValue = option;
      this.employee = option;
    },
    getMembersRegistredBranches() {
      FitnessCenterService.getMembersRegistredBranches(
        LoginService.loggedUserValue.svnr
      )
        .then((response) => {
          this.branches = response.data;
          for (const branch of this.branches) {
            this.dropDownBranches.push(
              branch.name +
                "-" +
                branch.street +
                " " +
                branch.zip +
                " " +
                branch.city
            );
            console.log(this.branches);
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
    getEmployeesForBranch() {
      if (this.branch !== "") {
        for (const branch of this.branches) {
          if (
            this.branch.includes(branch.city) &&
            this.branch.includes(branch.zip) &&
            this.branch.includes(branch.street)
          ) {
            FitnessCenterService.getEmployeesForBranch(
              branch.city,
              branch.zip,
              branch.street
            )
              .then((response) => {
                this.employees = response.data;
                this.dropDownEmployees = [];
                for (const employee of this.employees) {
                  this.dropDownEmployees.push(
                    employee.svnr + " " + employee.firstName
                  );
                }
              })
              .catch((e) => {
                console.log(e);
              });
            break;
          }
        }
      }
      this.employeeValue = "Select an Employee";
      this.employee = "";
    },
    bookTrainingSession() {
      if (
        this.employee !== "" &&
        this.duration !== null &&
        this.price !== null
      ) {
        for (const employee of this.employees) {
          if (this.employee.includes(employee.svnr)) {
            var trainingsession = {
              member_svnr: LoginService.loggedUserValue.svnr,
              employee_svnr: employee.svnr,
              price: price,
              duration: duration,
            };
            FitnessCenterService.bookTrainingSession(
              JSON.stringify(trainingsession)
            )
              .then((response) => {
                console.log(response);
                document.location.reload(true);
              })
              .catch((e) => {
                console.log(e);
              });
          }
        }
      } else {
        alert("Please fill all fields.");
      }
    },
    getMembersTrainingSessions() {
      FitnessCenterService.getMembersTrainingSessions(
        LoginService.loggedUserValue.svnr
      )
        .then((response) => {
          this.trainingsessions = response.data;
          console.log(this.trainingsessions);
        })
        .catch((e) => {
          console.log(e);
        });
    },
  },
  created() {
    if (!window.location.hash) {
      window.location = window.location + "#loaded";
      window.location.reload();
    }
    this.getMembersRegistredBranches();
    this.getMembersTrainingSessions();
  },
};
function calculatePrice() {
  duration = document.getElementById("duration").value;
  price = duration * 10;
  document.getElementById("price").innerHTML = "Kosten: €" + price;
}

window.onload = function () {
  document
    .getElementById("duration")
    .addEventListener("change", calculatePrice);
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
.aselect {
  width: 150px auto;
  margin: 25px auto;
}
.selector {
  border: 1px solid gainsboro;
  background: #f8f8f8;
  z-index: 1;
}
.arrow {
  position: relative;
  right: -98%;
  top: -26.5px;
  width: 0;
  height: 0;
  border-left: 7px solid transparent;
  border-right: 7px solid transparent;
  border-top: 10px solid #888;
  transform: rotateZ(0deg) translateY(0px);
  transition-duration: 0.2s;
  transition-timing-function: cubic-bezier(0.59, 1.39, 0.37, 1.01);
}
.expanded {
  transform: rotateZ(180deg) translateY(2px);
}
.label {
  display: block;
  padding: 15px;
  font-size: 16px;
  color: rgb(0, 0, 0);
}

ul {
  width: 84.3%;
  list-style-type: none;
  padding: 0;
  margin: 0;
  font-size: 16px;
  border: 1px solid gainsboro;
  position: absolute;
  z-index: 1;
  background: #fff;
}

li {
  padding: 12px;
}
li:hover {
  color: white;
  background: rgb(0, 114, 167);
}
.current {
  background: #eaeaea;
}
.hidden {
  visibility: hidden;
}
.visible {
  visibility: visible;
}
</style>