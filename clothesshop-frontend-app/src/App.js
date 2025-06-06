import logo from './logo.svg';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import ListProducts from './components/ListProducts';
import Header from './components/Header';
import Footer from './components/Footer';
import AddProduct from './components/AddProduct'; // <--- Dodaj import!
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';

function App() {
  return (
    <div>
      <Header />
      <Router>
        <div className="container mt-5">
          <Switch>
            <Route exact path="/" component={ListProducts} />
            <Route exact path="/productsList" component={ListProducts} />
            <Route exact path="/addProduct" component={AddProduct} />
          </Switch>
        </div>
        <Footer />
      </Router>
    </div>
  );
}

export default App;
