import { BehaviorSubject } from 'rxjs';
import FitnessCenterService from "../services/FitnessCenterService.js";
import { Role } from ".././Role.js";
import router from "./../router.js";

const loggedUserSubject = new BehaviorSubject(JSON.parse(localStorage.getItem('loggedUser')));
export default {
    data() {
        return {
            person: null
        };
    },
    login,
    logout,
    loggedUser: loggedUserSubject.asObservable(),
    get loggedUserValue() { return loggedUserSubject.value }
};

function login(username, password) {
    FitnessCenterService.login(username, password)
        .then((response) => {
            this.person = response.data
            if (this.person !== "") {
                var role = null
                if (this.person.role == "member") {
                    role = Role.Member
                } else {
                    role = Role.Employee
                }
                var person = { role: role, svnr: this.person.svnr, firstname: this.person.firstname, lastname: this.person.lastname }
                localStorage.setItem('loggedUser', JSON.stringify(person));
                loggedUserSubject.next(person)
                router.push('/')
            } else {
                alert("False Username or Password");
            }
            console.log(this.person);
        }).catch(e => console.log(e))
    return this.person;
}

function logout() {
    localStorage.removeItem('loggedUser');
    loggedUserSubject.next(null);
}