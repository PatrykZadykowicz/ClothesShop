package wi.pb.clothesshop.dao;

import wi.pb.clothesshop.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {

    Optional<Order> findById(Long id);
    List<Order> getAll();
    Order save(Order order);
    Order update(Order order);
    void delete(Order order);
    List<Order> findByUserId(int userId);
}
