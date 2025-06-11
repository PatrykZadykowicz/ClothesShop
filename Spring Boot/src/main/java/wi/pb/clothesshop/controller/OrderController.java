package wi.pb.clothesshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wi.pb.clothesshop.dto.OrderDto;
import wi.pb.clothesshop.dto.UpdateOrderStatusRequest;
import wi.pb.clothesshop.entity.Order;
import wi.pb.clothesshop.enums.OrderStatus;
import wi.pb.clothesshop.service.MailService;
import wi.pb.clothesshop.service.OrderService;
import wi.pb.clothesshop.service.UserContextService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService orderService;
    private UserContextService userContextService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public OrderDto createOrder() {
        int userId = userContextService.getUserId();
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

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/{orderId}")
    public OrderDto getOrderById(@PathVariable Long orderId) {

        try {
            Order order = orderService.getOrder(orderId);
            return orderService.mapToDto(order);
        }
        catch (Exception e) { e.printStackTrace(); return null; }

    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/{userId}/user-orders")
    public List<OrderDto> getUserOrders(@PathVariable int userId) {
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

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping()
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();

        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : orders) {
            orderDtos.add(orderService.mapToDto(order));
        }

        return orderDtos;
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PutMapping("/{orderId}/status")
    public ResponseEntity<?> changeOrderStatus(
            @PathVariable Long orderId,
            @RequestParam UpdateOrderStatusRequest updateOrderStatusRequest) {

        try {
            orderService.changeOrderStatus(orderId, updateOrderStatusRequest.getNewStatus());
            return ResponseEntity.ok("Order status updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/my")
    public ResponseEntity<List<OrderDto>> getMyOrders() {
        try {
            int userId = userContextService.getUserId();

            List<Order> orders = orderService.getUserOrders(userId);

            if (orders.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            List<OrderDto> orderDtos = orders.stream()
                    .map(orderService::mapToDto)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(orderDtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonList(new OrderDto()));
        }
    }

}
