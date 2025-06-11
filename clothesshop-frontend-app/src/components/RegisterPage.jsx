import React, { useState } from 'react';
import axios from 'axios';

function RegisterPage() {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    password: ''
  });

  const [message, setMessage] = useState('');

  const handleChange = (e) => {
    setFormData(prev => ({ ...prev, [e.target.name]: e.target.value }));
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8080/api/auth/register', formData, { withCredentials: true });
      setMessage('Rejestracja zakończona sukcesem!');
    } catch (err) {
      setMessage('Błąd podczas rejestracji.');
    }
  }

  return (
    <div className="container">
      <h2>Rejestracja</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label>Imię</label>
          <input name="firstName" className="form-control" onChange={handleChange} required />
        </div>
        <div className="mb-3">
          <label>Nazwisko</label>
          <input name="lastName" className="form-control" onChange={handleChange} required />
        </div>
        <div className="mb-3">
          <label>Email</label>
          <input name="email" type="email" className="form-control" onChange={handleChange} required />
        </div>
        <div className="mb-3">
          <label>Hasło</label>
          <input name="password" type="password" className="form-control" onChange={handleChange} required />
        </div>
        <button type="submit" className="btn btn-primary">Zarejestruj się</button>
      </form>
      {message && <div className="alert alert-info mt-3">{message}</div>}
    </div>
  );
}

export default RegisterPage;
