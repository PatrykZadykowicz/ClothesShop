import React, { Component } from 'react'
import ProductService from '../services/ProductService'

class Product extends Component {

    constructor(props) {
    super(props)
    console.log('Received id:', this.props.match.params.id); 
    this.state = {
        id: this.props.match.params.id,
        product: {}
    }

    this.cancel=this.cancel.bind(this);
}


    componentDidMount(){
        ProductService.getProduct(this.state.id).then(
            response=>{this.setState({product:response.data})}
        )
    }

    cancel(){
        this.props.history.push('/')
    }

  render() {
    return (
      <div className='container'>
        <h1>Product Details</h1>
        <div className="card">
            <div className="card-body">
                <div className='row'>
                    <label>Product Id</label>
                    <div>{this.state.product.id}</div>

                </div>
                <div className='row'>
                    <label>Product Name</label>
                    <div>{this.state.product.name}</div>

                </div>
                <div className='row'>
                    <label>Product Price</label>
                    <div>{this.state.product.price}</div>

                </div>
                <div className='row'>
                    <label>Product Stock</label>
                    <div>{this.state.product.inventory}</div>

                </div>


            </div>


        </div>
        <button className="btn btn-danger mt-3" onClick={this.cancel}>Go back</button>

      </div>
    )
  }
}

export default Product
