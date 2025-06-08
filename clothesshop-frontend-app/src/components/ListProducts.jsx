import React, { Component } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css';
import ProductService from '../services/ProductService';

class ListProducts extends Component {

    constructor(props) {
        super(props);
        this.state = {
            products : []
        }

        this.addProduct=this.addProduct.bind(this);
    }

    componentDidMount() {
        ProductService.getProducts().then(response => {
            console.log(response.data);
            this.setState({products: response.data});
        });
    }
    addProduct(){

        this.props.history.push('/addProduct');
    }

    updateProduct(id){
    this.props.history.push(`/updateProduct/${id}`);
}


    deleteProduct(id) {
    const confirmDelete = window.confirm("Are you sure you want to delete this product?");
    if (confirmDelete) {
        ProductService.deleteProduct(id).then(res => {
            this.setState({ products: this.state.products.filter(product => product.id !== id) });
        }).catch(error => {
            console.error("Delete failed:", error);
        });
    }
}


    render() {
        return (
        <div>
            <h1>List of products</h1>
            <button className='btn btn-dark text-white' onClick={this.addProduct}>Add Product</button>
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
                        
                         <td>
                            <button
                    className="btn btn-primary btn-sm me-2"
                    onClick={() => this.updateProduct(product.id)}
                    >
                    Update
                    </button>
                    <button 
                        className="btn btn-danger btn-sm"
                        onClick={() => this.deleteProduct(product.id)}
                    >
                        Delete
                    </button>
                </td>
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