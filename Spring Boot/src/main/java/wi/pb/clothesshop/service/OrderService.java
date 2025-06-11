package wi.pb.clothesshop.service;

import wi.pb.clothesshop.dto.OrderDto;
import wi.pb.clothesshop.entity.Order;

import java.util.List;

public interface OrderService {

    Order placeOrder(int userId);
    Order getOrder(Long orderId);

    List<Order> getUserOrders(int userId);

    OrderDto mapToDto(Order order);

    void sendConfirmationEmail(Order placedOrder);
}
