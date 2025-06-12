import React, { Component } from 'react';
import OrderService from '../services/OrderService';
import AuthService from '../services/AuthService';

class OrderHistory extends Component {
  state = {
    orders: [],
    loading: true,
    error: null,
  };

  componentDidMount() {
    console.log('OrderHistory mounted');
    this.loadOrders();
  }

  loadOrders = async () => {
    try {
      // Pobierz aktualnie zalogowanego użytkownika
      const userResponse = await AuthService.me();

      if (!userResponse.data || !userResponse.data.id) {
        this.setState({ error: 'User not authenticated.', loading: false });
        return;
      }

      const userId = userResponse.data.id;

      // Pobierz zamówienia użytkownika
      const response = await OrderService.getLoggedInUserOrders();

      this.setState({ orders: response.data, loading: false });
    } catch (error) {
      console.error(error);
      this.setState({ error: 'Failed to load orders.', loading: false });
    }
  };

  render() {
    console.log('orders', this.state.orders);

    const { orders, loading, error } = this.state;

    if (loading) return <p>Loading orders...</p>;
    if (error) return <p>{error}</p>;

    if (!orders || orders.length === 0) return <p>You have no orders.</p>;

    return (
      <div>
        <h2>Your Order History</h2>
        <table style={{ borderCollapse: 'collapse', width: '100%' }}>
          <thead>
            <tr>
              <th style={{ border: '1px solid #ddd', padding: '8px' }}>Order ID</th>
              <th style={{ border: '1px solid #ddd', padding: '8px' }}>Order Date</th>
              <th style={{ border: '1px solid #ddd', padding: '8px' }}>Status</th>
              <th style={{ border: '1px solid #ddd', padding: '8px' }}>Total Amount (PLN)</th>
              <th style={{ border: '1px solid #ddd', padding: '8px' }}>Items</th>
            </tr>
          </thead>
          <tbody>
            {orders.map(order => (
              <tr key={order.orderId}>
                <td style={{ border: '1px solid #ddd', padding: '8px' }}>{order.orderId}</td>
                <td style={{ border: '1px solid #ddd', padding: '8px' }}>{order.orderDate}</td>
                <td style={{ border: '1px solid #ddd', padding: '8px' }}>{order.orderStatus}</td>
                <td style={{ border: '1px solid #ddd', padding: '8px' }}>{order.totalAmount.toFixed(2)}</td>
                <td style={{ border: '1px solid #ddd', padding: '8px' }}>
                  <ul>
                    {order.orderItems.map((item, index) => (
  <li key={index}>
    {item.productName} x {item.quantity} = {(item.price * item.quantity).toFixed(2)} PLN
  </li>
))}

                  </ul>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  }
}

export default OrderHistory;
