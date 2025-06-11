package wi.pb.clothesshop.service;

import wi.pb.clothesshop.dto.OrderDto;
import wi.pb.clothesshop.entity.Order;

import java.util.List;

public interface OrderService {

    Order placeOrder(Long userId);
    Order getOrder(Long orderId);

    List<Order> getUserOrders(Long userId);

    OrderDto mapToDto(Order order);
}
