import React, { Component } from 'react';
import axios from 'axios';
import { clearCart, removeFromCart, getCart } from '../services/CartService'; // importuj serwis

class Cart extends Component {
  state = {
    cart: null,
    loading: true,
    error: null,
  };

  componentDidMount() {
    this.loadCart();
  }

  loadCart = () => {
    getCart()
      .then(response => {
        this.setState({ cart: response.data, loading: false });
      })
      .catch(() => {
        this.setState({ error: 'Nie udało się pobrać koszyka.', loading: false });
      });
  };

  handleClearCart = () => {
    if (window.confirm('Czy na pewno chcesz wyczyścić cały koszyk?')) {
      clearCart()
        .then(() => {
          this.setState({ cart: { cartItems: [] } });
          alert('Koszyk został wyczyszczony.');
        })
        .catch(() => {
          alert('Nie udało się wyczyścić koszyka.');
        });
    }
  };

  handleRemoveItem = (productId) => {
    if (window.confirm('Czy na pewno chcesz usunąć ten produkt z koszyka?')) {
      removeFromCart(productId)
        .then(() => {
          // Po usunięciu przeładuj koszyk
          this.loadCart();
        })
        .catch(() => {
          alert('Nie udało się usunąć produktu z koszyka.');
        });
    }
  };

  render() {
    const { cart, loading, error } = this.state;

    if (loading) return <p>Ładowanie koszyka...</p>;
    if (error) return <p>{error}</p>;

    if (!cart || !cart.cartItems || cart.cartItems.length === 0) {
      return <p>Twój koszyk jest pusty.</p>;
    }

    const totalAmount = cart.cartItems.reduce(
      (sum, item) => sum + item.product.price * item.quantity,
      0
    );

    return (
      <div>
        <h2>Twój koszyk</h2>
        <button onClick={this.handleClearCart} style={{ marginBottom: '15px', backgroundColor: '#f44336', color: 'white', border: 'none', padding: '8px 12px', cursor: 'pointer' }}>
          Wyczyść koszyk
        </button>
        <table style={{ borderCollapse: 'collapse', width: '100%' }}>
          <thead>
            <tr>
              <th style={{ border: '1px solid #ddd', padding: '8px' }}>Nazwa produktu</th>
              <th style={{ border: '1px solid #ddd', padding: '8px' }}>Ilość</th>
              <th style={{ border: '1px solid #ddd', padding: '8px' }}>Cena za sztukę (PLN)</th>
              <th style={{ border: '1px solid #ddd', padding: '8px' }}>Łączna cena (PLN)</th>
              <th style={{ border: '1px solid #ddd', padding: '8px' }}>Akcje</th>
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
                    style={{ backgroundColor: '#f44336', color: 'white', border: 'none', padding: '6px 10px', cursor: 'pointer' }}
                  >
                    Usuń
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <p style={{ marginTop: '20px', fontWeight: 'bold' }}>
          Łączna wartość: {totalAmount.toFixed(2)} PLN
        </p>
      </div>
    );
  }
}

export default Cart;
