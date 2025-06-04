package wi.pb.clothesshop.dao;

import wi.pb.clothesshop.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {

    Optional<Product> findById(int id);
    List<Product> getAll();
    Product save(Product product);
    Product update(Product product);
    void delete(Product product);

}
