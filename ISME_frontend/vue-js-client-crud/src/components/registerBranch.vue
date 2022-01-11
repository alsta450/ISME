<template>
<div id="app">	
  <h2>Register in Branch</h2>
  <div class="aselect" :data-value="value" :data-list="list">
    <div class="selector" @click="toggle()">
      <div class="label">
        <span>{{ value }}</span>
        </div>
			<div class="arrow" :class="{ expanded : visible }"></div>
      <div :class="{ hidden : !visible, visible }">
        <ul>
          <li :clsass="{ current : item === value }" v-for="item in dropDownBranches" :key="item" @click="select(item)">{{ item }}</li>
        </ul>
        </div>
      </div>
	</div>
  <button class="btn btn-primary" type="button" v-on:click="registerForBranch()">register Branch</button>
      <div class="col-md-6">
      <h4>Visitable Branches</h4>
      <table data-link="row">
        <thead>
          <tr>
            <th>Branch</th>
          </tr>
        </thead>
        <tr v-for="(branch) in registerdBranches" :key="branch">
          <td>{{branch}}</td>
        </tr>
      </table>
    </div>
</div>
</template>


<script>
import FitnessCenterService from "../services/FitnessCenterService.js";
import LoginService from "../services/LoginService";

export default {
    el: "#app",
		name: 'aselect',
		data() {
      return{  
        value: 
          'Select a Branch',
          dropDownBranches: [],
          registerdBranches: [],
          branches: null,
          branch:"",
          visible: false,
     };
    },
		methods: {
			toggle() {
				this.visible = !this.visible;
			},
			select(option) {
         this.value = option;
         this.branch = option;
			},
      getAllBranches() {
        FitnessCenterService.getAllBranches()
            .then(response => {
              this.branches = response.data
              for(const branch of this.branches){
                this.dropDownBranches.push(branch.name + "-" + branch.street + " "  + branch.zip + " " + branch.city);
              }
            }).catch(e => {
              console.log(e);
            });
      },
      getRegistredBranches() {
        FitnessCenterService.getMembersRegistredBranches(LoginService.currentUserValue.svnr)
            .then(response => {
              var registredbranchesResponse = response.data;
              for(const registerdBranch of registredbranchesResponse){
                this.registerdBranches.push(registerdBranch.name + "-" + registerdBranch.street + " "  + registerdBranch.zip + " " + registerdBranch.city);
              }
            }).catch(e => {
              console.log(e);
            });
      },
      registerForBranch() {
        if(this.branch !== ""){
            for(const branch of this.branches){
              if(this.branch.includes(branch.city) && this.branch.includes(branch.zip) && this.branch.includes(branch.street)){
                FitnessCenterService.registerForBranch(branch.city, branch.zip, branch.street, LoginService.currentUserValue.svnr)
                   .then(response =>{
                      console.log(response)
                      document.location.reload(true);
                   }).catch(e => {
                      console.log(e);
                  });
                break;
              }
            }
        }else{
          alert("not found")
        }
     
      }
		},
    created() {
      this.getAllBranches();
      this.getRegistredBranches();
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
.aselect {
  width: 200px auto;
  margin: 20px auto;  
 }
	.selector {
    border: 1px solid gainsboro;
    background: #F8F8F8;
    position: relative;
    z-index: 1;
  }
	.arrow {
    position: absolute;
    right: 10px;
    top: 40%;
    width: 0;
    height: 0;
    border-left: 7px solid transparent;
    border-right: 7px solid transparent;
    border-top: 10px solid #888;
    transform: rotateZ(0deg) translateY(0px);
    transition-duration: 0.3s;
    transition-timing-function: cubic-bezier(.59,1.39,.37,1.01);
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
			width: 100%;
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