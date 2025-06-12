import React, { Component } from 'react';
import AuthService from '../services/AuthService';
import OrderService from '../services/OrderService';
import CartService from '../services/CartService';  // poprawiony import - dodałem '/' po '..'

class Cart extends Component {
  state = {
    cart: null,
    loading: true,
    error: null,
    ordering: false,
  };

  componentDidMount() {
    this.loadCart();
  }

  loadCart = () => {
    CartService.getCart()
      .then(response => {
        this.setState({ cart: response.data, loading: false });
      })
      .catch(() => {
        this.setState({ error: 'Failed to load the cart.', loading: false });
      });
  };

  handleClearCart = () => {
    if (window.confirm('Are you sure you want to clear the entire cart?')) {
      CartService.clearCart()
        .then(() => {
          this.setState({ cart: { cartItems: [] } });
          alert('Cart has been cleared.');
        })
        .catch(() => {
          alert('Failed to clear the cart.');
        });
    }
  };

  handleRemoveItem = (productId) => {
    if (window.confirm('Are you sure you want to remove this product from the cart?')) {
      CartService.removeFromCart(productId)
        .then(() => {
          this.loadCart();
        })
        .catch(() => {
          alert('Failed to remove product from the cart.');
        });
    }
  };

  handlePlaceOrder = async () => {
    this.setState({ ordering: true });
    try {
      // Pobierz info o użytkowniku
      const userResponse = await AuthService.me();

      if (!userResponse.data || !userResponse.data.id) {
        alert('User not authenticated.');
        this.setState({ ordering: false });
        return;
      }

      const userId = userResponse.data.id;

      await OrderService.placeOrder(userId);

      alert('Order placed successfully!');
      this.loadCart();
    } catch (error) {
      console.error('Error placing order:', error.response || error.message || error);
      alert('Failed to place order.');
    } finally {
      this.setState({ ordering: false });
    }
  };

  render() {
    const { cart, loading, error, ordering } = this.state;

    if (loading) return <p>Loading cart...</p>;
    if (error) return <p>{error}</p>;

    if (!cart || !cart.cartItems || cart.cartItems.length === 0) {
      return <p>Your cart is empty.</p>;
    }

    const totalAmount = cart.cartItems.reduce(
      (sum, item) => sum + item.product.price * item.quantity,
      0
    );

    const buttonStyle = {
      marginBottom: '15px',
      backgroundColor: '#f44336',
      color: 'white',
      border: 'none',
      padding: '8px 12px',
      cursor: 'pointer',
      borderRadius: '8px',
      transition: 'background-color 0.3s ease',
    };

    const orderButtonStyle = {
      ...buttonStyle,
      backgroundColor: ordering ? '#999' : '#4CAF50',
      cursor: ordering ? 'not-allowed' : 'pointer',
      marginTop: '20px',
    };

    return (
      <div>
        <h2>Your Cart</h2>
        <button onClick={this.handleClearCart} style={buttonStyle}>
          Clear Cart
        </button>
        <table style={{ borderCollapse: 'collapse', width: '100%' }}>
          <thead>
            <tr>
              <th style={{ border: '1px solid #ddd', padding: '8px' }}>Product Name</th>
              <th style={{ border: '1px solid #ddd', padding: '8px' }}>Quantity</th>
              <th style={{ border: '1px solid #ddd', padding: '8px' }}>Price per Unit (PLN)</th>
              <th style={{ border: '1px solid #ddd', padding: '8px' }}>Total Price (PLN)</th>
              <th style={{ border: '1px solid #ddd', padding: '8px' }}>Actions</th>
            </tr>
          </thead>
          <tbody>
            {cart.cartItems.map(item => (
              <tr key={item.product.id}>
                <td style={{ border: '1px solid #ddd', padding: '8px' }}>{item.product.name}</td>
                <td style={{ border: '1px solid #ddd', padding: '8px' }}>{item.quantity}</td>
                <td style={{ border: '1px solid #ddd', padding: '8px' }}>{item.product.price.toFixed(2)}</td>
                <td style={{ border: '1px solid #ddd', padding: '8px' }}>
                  {(item.product.price * item.quantity).toFixed(2)}
                </td>
                <td style={{ border: '1px solid #ddd', padding: '8px' }}>
                  <button
                    onClick={() => this.handleRemoveItem(item.product.id)}
                    style={buttonStyle}
                  >
                    Remove
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        <p style={{ marginTop: '20px', fontWeight: 'bold' }}>
          Total Amount: {totalAmount.toFixed(2)} PLN
        </p>

        <button
          onClick={this.handlePlaceOrder}
          style={orderButtonStyle}
          disabled={ordering}
        >
          {ordering ? 'Placing order...' : 'Place Order'}
        </button>
      </div>
    );
  }
}

export default Cart;
