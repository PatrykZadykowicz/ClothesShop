import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import AuthService from '../services/AuthService';

class Header extends Component {
  state = {
    email: '',
    password: '',
    loggedInUser: null,
    message: '',
  };

  componentDidMount() {
    AuthService.me()
      .then(response => {
        this.setState({ loggedInUser: response.data });
      })
      .catch(() => {
        this.setState({ loggedInUser: null });
      });
  }

  handleChange = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  }

  handleLogin = async (e) => {
    e.preventDefault();
    const { email, password } = this.state;
    try {
      await AuthService.login({ email, password });
      const userResponse = await AuthService.me();
      this.setState({ loggedInUser: userResponse.data, message: '' });
    } catch {
      this.setState({ message: 'Login failed. Please check your credentials.' });
    }
  }

  handleLogout = () => {
    AuthService.logout()
      .then(() => {
        this.setState({ loggedInUser: null, email: '', password: '' });
      });
  }

  render() {
    const { email, password, loggedInUser, message } = this.state;

    return (
      <div>
        <header className="p-3 bg-dark text-white">
          <div className="container">
            <div className="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">

              <a href="/" className="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
                {/* logo */}
              </a>

              <ul className="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                <li><a href="/" className="nav-link px-2 text-secondary">Product List</a></li>
                <Link to="/orders" className="nav-link px-2 text-secondary">Order History</Link>
              </ul>

              <div className="text-end d-flex align-items-center gap-2">
                {loggedInUser ? (
                  <>
                    <span className="me-3">Welcome, {loggedInUser.email}</span>
                    <button className="btn btn-outline-light me-2" onClick={this.handleLogout}>Logout</button>
                    <Link to="/cart" className="btn btn-primary">Cart</Link>
                  </>
                ) : (
                  <>
                    <form onSubmit={this.handleLogin} className="d-flex gap-2 align-items-center">
                      <input
                        name="email"
                        type="email"
                        placeholder="Email"
                        value={email}
                        onChange={this.handleChange}
                        className="form-control"
                        required
                        style={{ width: '180px' }}
                      />
                      <input
                        name="password"
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={this.handleChange}
                        className="form-control"
                        required
                        style={{ width: '150px' }}
                      />
                      <button type="submit" className="btn btn-outline-light">Login</button>
                    </form>
                    <Link to="/register" className="btn btn-warning ms-2">Sign up</Link>
                  </>
                )}
              </div>

            </div>
          </div>
        </header>
        {message && <div className="alert alert-danger mt-2 text-center">{message}</div>}
      </div>
    );
  }
}

export default Header;
