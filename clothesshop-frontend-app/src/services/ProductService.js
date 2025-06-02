import axios from "axios";

const BASE_API = "http://localhost:8080/api/products";

class ProductService {

    getProducts() {
        return axios.get(BASE_API + "/all");
    }

}

export default new ProductService();