package wi.pb.clothesshop.service;

import wi.pb.clothesshop.dto.OrderDto;
import wi.pb.clothesshop.entity.Order;
import wi.pb.clothesshop.enums.OrderStatus;

import java.util.List;

public interface OrderService {

    Order placeOrder(int userId);
    Order getOrder(Long orderId);

    List<Order> getUserOrders(int userId);
    List<Order> getAllOrders();

    OrderDto mapToDto(Order order);

    void sendConfirmationEmail(Order placedOrder);
    void changeOrderStatus(Long orderId, OrderStatus newStatus);
}
