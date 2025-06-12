package wi.pb.clothesshop;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import wi.pb.clothesshop.entity.*;
import wi.pb.clothesshop.dao.*;
import wi.pb.clothesshop.enums.OrderStatus;
import wi.pb.clothesshop.service.*;
import wi.pb.clothesshop.service.impl.OrderServiceImpl;

import java.math.BigDecimal;

class OrderServiceImplTest {

    @Mock
    private OrderDao orderDao;

    @Mock
    private ProductDao productDao;

    @Mock
    private CartService cartService;

    @Mock
    private MailService mailService;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Cart cart;
    private User user;
    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1);

        product = new Product();
        product.setId(1);
        product.setPrice(100.0);
        product.setInventory(10);

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(2);
        cartItem.setUnitPrice(BigDecimal.valueOf(100.0));
        cartItem.setTotalPrice();

        cart = new Cart();
        cart.setId(1);
        cart.setTotalAmount(BigDecimal.valueOf(200.0));
        cart.setUser(user);
        cart.getCartItems().add(cartItem);
    }

    @Test
    void testPlaceOrder() {
        when(cartService.getCart(1)).thenReturn(cart);
        when(orderDao.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(productDao.update(any(Product.class))).thenReturn(product);

        Order order = orderService.placeOrder(1);

        verify(orderDao, times(1)).save(any(Order.class));
        verify(cartService, times(1)).clearCart(1);

        assertEquals(OrderStatus.PENDING, order.getOrderStatus());
    }
}
