package wi.pb.clothesshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wi.pb.clothesshop.dto.OrderDto;
import wi.pb.clothesshop.entity.Order;
import wi.pb.clothesshop.service.MailService;
import wi.pb.clothesshop.service.OrderService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public OrderDto createOrder(@RequestParam Long userId) {
        Order placedOrder;

        try {
            placedOrder = orderService.placeOrder(userId);
        }  //TODO: better exception handling
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        orderService.sendConfirmationEmail(placedOrder);

        return orderService.mapToDto(placedOrder);
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrderById(@PathVariable Long orderId) {

        try {
            Order order = orderService.getOrder(orderId);
            return orderService.mapToDto(order);
        }
        catch (Exception e) { e.printStackTrace(); return null; }

    }

    @GetMapping("/{userId}/user-orders")
    public List<OrderDto> getUserOrders(@PathVariable Long userId) {
        try {
            List<Order> orders = orderService.getUserOrders(userId);

            List<OrderDto> orderDtos = new ArrayList<>();
            for (Order order : orders) {
                orderDtos.add(orderService.mapToDto(order));
            }
            return orderDtos;
        }
        catch (Exception e) { e.printStackTrace(); return null; }
    }
}
