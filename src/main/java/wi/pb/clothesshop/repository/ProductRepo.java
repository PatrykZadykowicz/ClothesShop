package wi.pb.clothesshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wi.pb.clothesshop.entity.Product;

public interface ProductRepo extends JpaRepository<Product,Integer> {
}
