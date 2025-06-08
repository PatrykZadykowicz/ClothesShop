package wi.pb.clothesshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wi.pb.clothesshop.entity.Order;
import wi.pb.clothesshop.service.OrderService;

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
    public Order createOrder(@RequestParam Long userId) {
        try {
            return orderService.placeOrder(userId);
        }  //TODO: better exception handling
        catch (Exception e) { e.printStackTrace(); return null; }
    }

    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable Long orderId) {
        Order order = null;

        try { order = orderService.getOrder(orderId); }
        catch (Exception e) { e.printStackTrace(); }

        return order;
    }

    @GetMapping("/{userId}/user-orders")
    public List<Order> getUserOrders(@PathVariable Long userId) {
        try {
            return orderService.getUserOrders(userId);
        }
        catch (Exception e) { e.printStackTrace(); return null; }
    }
}
