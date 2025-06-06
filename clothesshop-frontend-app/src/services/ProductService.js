import axios from "axios";

const BASE_API = "http://localhost:8080/api/products";

class ProductService {

    getProducts() {
        return axios.get(BASE_API + "/all");
    }

    insertProduct(product) {
        return axios.post(BASE_API + "/insert",product);
    }

}

export default new ProductService();