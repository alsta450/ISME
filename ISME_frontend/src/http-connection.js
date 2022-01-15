import axios from "axios";

export default axios.create({
  baseURL: "http://localhost:8081/fitness/",
  headers: {
    "Content-type": "application/json"
  }
});
