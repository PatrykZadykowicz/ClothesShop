import React, { Component } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css';
import ProductService from '../services/ProductService';

class ListProducts extends Component {

    constructor(props) {
        super(props);
        this.state = {
            products : []
        }
    }

    componentDidMount() {
        ProductService.getProducts().then(response => {
            console.log(response.data);
            this.setState({products: response.data});
        });
    }

    render() {
        return (
        <div>
            <h1>List of products</h1>
            <table class="table">
            <thead>
                <tr>
                    <th scope="col">Product Id</th>
                    <th scope="col">Category</th>
                    <th scope="col">Name</th>
                    <th scope="col">Price</th>
                </tr>
            </thead>
            <tbody>
                {
                    this.state.products.map(product => 
                        <tr key={product.id}>
                        <td>{product.id}</td>
                        <td>{product.category}</td>
                        <td>{product.name}</td>
                        <td>{product.price}</td>
                        </tr>
                    )
                }
            </tbody>
            </table>
        </div>
        )
    }
}

export default ListProducts