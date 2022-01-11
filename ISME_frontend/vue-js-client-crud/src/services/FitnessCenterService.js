import http from "../http-connection";
class FitnessCenterService {
  fillDatabase() {
    localStorage.setItem('db', 'SQL');
    return http.get("/create");
  }

  migrateDatabase() {
    localStorage.setItem('db', 'NOSQL');
    return http.get("/migrate");
  }

  login(username, password) {
    return http.post(`/login/?db=${localStorage.getItem('db')}`, { "username": username, "password": password });
  }

  getAllBranches() {
    return http.get(`/branch/?db=${localStorage.getItem('db')}`);
  }

  getEmployeesForBranch(city, zip, street) {
    return http.get(`/branch/${city}/${zip}/${street}/employee/?db=${localStorage.getItem('db')}`);
  }

  registerForBranch(city, zip, street, svnr) {
    return http.post(`/branch/${city}/${zip}/${street}/register/?db=${localStorage.getItem('db')}`, { "svnr": svnr });
  }

  bookTrainingSession(trainingsession) {
    return http.post(`/booking/?db=${localStorage.getItem('db')}`, trainingsession);
  }

  getMembersRegistredBranches(svnr) {
    return http.post(`/branch/member/?db=${localStorage.getItem('db')}`, { "svnr": svnr });
  }

  getMembersTrainingSessions(svnr) {
    return http.post(`/trainingsession/?db=${localStorage.getItem('db')}`, { "svnr": svnr })
  }

  topTrainers() {
    return http.get(`/top-trainers/?db=${localStorage.getItem('db')}`)
  }

  loyalstMembers() {
    return http.get(`/loyal-members/?db=${localStorage.getItem('db')}`)
  }
}

export default new FitnessCenterService();