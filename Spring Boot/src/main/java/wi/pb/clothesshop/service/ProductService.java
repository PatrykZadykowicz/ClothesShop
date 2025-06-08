package wi.pb.clothesshop.service;

import org.springframework.stereotype.Service;
import wi.pb.clothesshop.entity.Product;

import java.util.List;


public interface ProductService {

    List<Product> getAllProducts();

    Product insertProduct(Product product);

    Product getProduct(int id);

    Product updateProduct(int id, Product product);

    void deleteProduct(int id);

}
