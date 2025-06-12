import axios from 'axios';

const BASE_API = 'http://localhost:8080/api/cart';

class CartService {
  getCart() {
    return axios.get(`${BASE_API}/view`, { withCredentials: true });
  }

  addToCart(productId, quantity) {
    return axios.post(`${BASE_API}/add`, null, {
      params: { productId, quantity },
      withCredentials: true,
    });
  }

  removeFromCart(productId) {
    return axios.delete(`${BASE_API}/remove`, {
      params: { productId },
      withCredentials: true,
    });
  }

  clearCart() {
  return axios.delete(`${BASE_API}/clear`, { withCredentials: true });
}
}

export default new CartService();
