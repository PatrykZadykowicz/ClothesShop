import logo from './logo.svg';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import ListProducts from './components/ListProducts';
import Header from './components/Header';
import Footer from './components/Footer';
import AddProduct from './components/AddProduct'; 
import Product from './components/Product';
import UpdateProduct from './components/UpdateProduct';
import RegisterPage from './components/RegisterPage';  
import Cart from './components/Cart'

import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';

function App() {
  return (
    <Router>
      <Header />
      <div className="container mt-5">
        <Switch>
          <Route exact path="/" component={ListProducts} />
          <Route exact path="/productsList" component={ListProducts} />
          <Route exact path="/addProduct" component={AddProduct} />
          <Route exact path="/viewProduct/:id" component={Product} />
          <Route exact path="/updateProduct/:id" component={UpdateProduct} />
          <Route exact path="/register" component={RegisterPage} />   
          <Route exact path="/cart" component={Cart} />
        </Switch>
      </div>
      <Footer />
    </Router>
  );
}

export default App;
