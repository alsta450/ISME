import { BehaviorSubject } from 'rxjs';
import FitnessCenterService from "../services/FitnessCenterService.js";
import { Role } from ".././Role.js";
import router from "./../router.js";

const currentUserSubject = new BehaviorSubject(JSON.parse(localStorage.getItem('currentUser')));
export default {
    data(){
        return {
            person:null
        };
    },
    login,
    logout,
    currentUser: currentUserSubject.asObservable(), 
    get currentUserValue () { return currentUserSubject.value }
};

function login(username, password) {
    FitnessCenterService.login(username, password)
    .then((response) => {
        this.person = response.data
        if(this.person !== ""){
            var role = null
            if(this.person.role == "member"){
                 role = Role.Member
            }else{
                 role = Role.Employee
            }
            var person = {role: role, svnr: this.person.svnr, firstname: this.person.firstname, lastname: this.person.lastname}
            localStorage.setItem('currentUser', JSON.stringify(person));
            currentUserSubject.next(person)
            router.push('/')
        }else{
            alert("False Username or Password");
        }
        console.log(this.person);
    }).catch(e => console.log(e))
    return this.person;
}

function logout(){
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    currentUserSubject.next(null);
}