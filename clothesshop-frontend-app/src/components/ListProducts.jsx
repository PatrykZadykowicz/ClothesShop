import React, { Component } from 'react';
import AuthService from '../services/AuthService';
import ProductService from '../services/ProductService';
import CartService from '../services/CartService';

class ListProducts extends Component {
  constructor(props) {
    super(props);
    this.state = {
      products: [],
      isLoggedIn: false,
      quantities: {}, // quantities per product
      error: null,
    };
  }

  componentDidMount() {
    ProductService.getProducts().then(response => {
      this.setState({ products: response.data });
    });

    AuthService.me()
      .then(() => this.setState({ isLoggedIn: true }))
      .catch(() => this.setState({ isLoggedIn: false }));
  }

  handleQuantityChange(productId, event) {
    const value = event.target.value;
    // Only allow positive integers
    if (value === '' || /^[1-9]\d*$/.test(value)) {
      this.setState(prevState => ({
        quantities: {
          ...prevState.quantities,
          [productId]: value,
        },
      }));
    }
  }

  addToCart(productId) {
    const quantity = parseInt(this.state.quantities[productId]) || 1; // default to 1
    CartService.addToCart(productId, quantity)
      .then(() => {
        alert(`Added ${quantity} item(s) to cart!`);
        this.setState(prevState => ({
          quantities: {
            ...prevState.quantities,
            [productId]: '', // clear field after adding
          },
        }));
      })
      .catch(() => {
        alert('Failed to add product to cart.');
      });
  }

  render() {
    const { products, isLoggedIn, quantities } = this.state;

    return (
      <div>
        <h1>List of Products</h1>
        <table className="table">
          <thead>
            <tr>
              <th>Product ID</th>
              <th>Category</th>
              <th>Name</th>
              <th>Price</th>
              {isLoggedIn && (
                <>
                  <th>Quantity</th>
                  <th>Actions</th>
                </>
              )}
            </tr>
          </thead>
          <tbody>
            {products.map(product => (
              <tr key={product.id}>
                <td>{product.id}</td>
                <td>{product.category}</td>
                <td>{product.name}</td>
                <td>{product.price}</td>
                {isLoggedIn && (
                  <>
                    <td>
                      <input
                        type="number"
                        min="1"
                        value={quantities[product.id] || ''}
                        onChange={e => this.handleQuantityChange(product.id, e)}
                        style={{ width: '60px' }}
                      />
                    </td>
                    <td>
                      <button
                        className="btn btn-success btn-sm"
                        onClick={() => this.addToCart(product.id)}
                      >
                        Add to Cart
                      </button>
                    </td>
                  </>
                )}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  }
}

export default ListProducts;
