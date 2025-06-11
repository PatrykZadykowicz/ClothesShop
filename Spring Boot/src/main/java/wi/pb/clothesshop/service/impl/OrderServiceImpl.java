package wi.pb.clothesshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wi.pb.clothesshop.dao.OrderDao;
import wi.pb.clothesshop.dao.ProductDao;
import wi.pb.clothesshop.entity.Cart;
import wi.pb.clothesshop.entity.Order;
import wi.pb.clothesshop.entity.OrderItem;
import wi.pb.clothesshop.entity.Product;
import wi.pb.clothesshop.enums.OrderStatus;
import wi.pb.clothesshop.service.CartService;
import wi.pb.clothesshop.service.OrderService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final ProductDao productDao;
    private final CartService cartService;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, ProductDao productDao, CartService cartService) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.cartService = cartService;
    }

    @Override
    public Order placeOrder(int userId) {
        Cart cart = cartService.getCart(userId);
        Order order = createOrder(cart);

        // Creates a list of order items based on items in the cart
        List<OrderItem> orderItemList = createOrderItems(order, cart);
        // Assigns order items to the order
        order.setOrderItems(new HashSet<>(orderItemList));
        // Calculates cart's total cost
        order.setTotalAmount(calculateTotalAmount(orderItemList));

        orderDao.save(order);
        cartService.clearCart(cart.getId());
        return order;
    }

    // Creates a new order assigned to a specific user. The order has no cart items assigned.
    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    // Extracts cart items form the cart and returns them as a list of order items.
    // The returned order items are already assigned to a specific order.
    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getCartItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());

            productDao.update(product);
            return new OrderItem(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice()
            );
        }).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems
                .stream()
                .map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderDao.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<Order> getUserOrders(int userId) {
        return orderDao.findByUserId(userId);
    }
}
