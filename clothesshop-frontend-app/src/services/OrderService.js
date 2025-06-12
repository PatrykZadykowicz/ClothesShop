import axios from 'axios';

const BASE_API = 'http://localhost:8080/api/orders';

class OrderService {
  placeOrder(userId) {
    // Używamy query param userId zgodnie z backendem
    return axios.post(`${BASE_API}/create`, null, {
      params: { userId },
      withCredentials: true,
    });
  }

  getOrder(orderId) {
    return axios.get(`${BASE_API}/${orderId}`, { withCredentials: true });
  }

  getOrdersByUser(userId) {
    return axios.get(`${BASE_API}/${userId}/user-orders`, {
      withCredentials: true, // jeśli masz sesje/cookies
    });
  }

  sendConfirmationEmail(orderId) {
    return axios.post(`${BASE_API}/${orderId}/send-confirmation`, null, { withCredentials: true });
  }
}

export default new OrderService();
