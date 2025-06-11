import axios from 'axios';

const API_URL = 'http://localhost:8080/api/cart';

export const getCart = () => {
  return axios.get(`${API_URL}/view`, { withCredentials: true });
};

export const addToCart = (productId, quantity) => {
  return axios.post(`${API_URL}/add`, null, {
    params: { productId, quantity },
    withCredentials: true
  });
};

export const removeFromCart = (productId) => {
  return axios.delete(`${API_URL}/remove`, {
    params: { productId },
    
  });
};

export const clearCart = (userId) => {
  return axios.delete(`${API_URL}/clear`, {
    params: { userId },
    withCredentials: true
  });
};

export default {
  getCart,
  addToCart,
  removeFromCart,
  clearCart
};
