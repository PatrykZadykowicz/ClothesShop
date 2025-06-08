import React, { Component } from 'react'
import ProductService from '../services/ProductService';

class UpdateProduct extends Component {

    constructor(props) {
        super(props)
        this.state = {
            id:this.props.match.params.id,
            name: '',
            price: '',
            category: '',
            quantity: '',
        }
        this.cancel = this.cancel.bind(this);
        this.changeNameHandler = this.changeNameHandler.bind(this)
        this.changePriceHandler = this.changePriceHandler.bind(this)
        this.changeCategoryHandler = this.changeCategoryHandler.bind(this)
        this.changeQuantityHandler = this.changeQuantityHandler.bind(this)
        this.update = this.update.bind(this)
    }

    changeNameHandler(event) {
        this.setState({ name: event.target.value })
    }
    changePriceHandler(event) {
        this.setState({ price: event.target.value })
    }
    changeCategoryHandler(event) {
        this.setState({ category: event.target.value })
    }
    changeQuantityHandler(event) {
        this.setState({ quantity: event.target.value })
    }

    componentDidMount() {
    ProductService.getProduct(this.state.id).then((res) => {
        let product = res.data;
        this.setState({
            id: product.id,
            name: product.name,
            price: product.price,
            quantity: product.inventory,  
            category: product.category
        });
    });
}

    update = (e) => {
        e.preventDefault()
        let product = {
            name: this.state.name,
            price: this.state.price,
            category: this.state.category,
            inventory: this.state.quantity
        }

        console.log(product)
        ProductService.updateProduct(this.state.id,product).then(res=>
            {
                this.props.history.push('/');
            });
    }

    cancel(){
        this.props.history.push('/')
    }

    render() {
        return (
            <div className='container'>
                <h1>Update Product</h1>

                <div className='row'>
                    <div className='text-center'>

                        <div className="card">
                            <div className="card-body">

                                <form>
                                    <div className="mb-3">

                                        <label htmlFor="name" className="form-label">Product Name</label>
                                        <input type="text" className="form-control" id="name"
                                            value={this.state.name} onChange={this.changeNameHandler}
                                        />

                                        <label htmlFor="price" className="form-label">Product Price</label>
                                        <input type="number" className="form-control" id="price"
                                            value={this.state.price} onChange={this.changePriceHandler}
                                        />

                                        <label htmlFor="category" className="form-label">Product Category</label>
                                        <input type="text" className="form-control" id="category"
                                            value={this.state.category} onChange={this.changeCategoryHandler}
                                        />

                                        <label htmlFor="quantity" className="form-label">Product Quantity</label>
                                        <input type="number" className="form-control" id="quantity"
                                            value={this.state.quantity} onChange={this.changeQuantityHandler}
                                        />

                                    </div>

                                    <button type="button" className="btn btn-success me-2" onClick={this.update}>Update</button>
                                    <button type="button" className="btn btn-danger" onClick={this.cancel}>Go back</button>

                                </form>

                            </div>
                        </div>

                    </div>
                </div>
            </div>
        )
    }
}

export default UpdateProduct
