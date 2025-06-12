import axios from "axios";

const BASE_API = "http://localhost:8080/api/auth";

class AuthService {
  register(userData) {
    // userData to obiekt z danymi do rejestracji, np. {email, password, ...}
    return axios.post(BASE_API + "/register", userData, { withCredentials: true });
  }

  login(credentials) {
    // credentials: {email, password}
    return axios.post(BASE_API + "/login", credentials, { withCredentials: true });
  }

  logout() {
    return axios.post(BASE_API + "/logout", {}, { withCredentials: true });
  }

  me() {
    return axios.get(BASE_API + "/me", { withCredentials: true });
  }
}

export default new AuthService();
