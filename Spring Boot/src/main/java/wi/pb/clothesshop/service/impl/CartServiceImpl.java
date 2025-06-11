package wi.pb.clothesshop.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wi.pb.clothesshop.dao.CartDao;
import wi.pb.clothesshop.dao.CartItemDao;
import wi.pb.clothesshop.dao.ProductDao;
import wi.pb.clothesshop.entity.Cart;
import wi.pb.clothesshop.entity.CartItem;
import wi.pb.clothesshop.entity.Product;
import wi.pb.clothesshop.entity.User;
import wi.pb.clothesshop.service.CartService;

import java.math.BigDecimal;

@Service
public class CartServiceImpl implements CartService {

    private final CartDao cartDao;
    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartServiceImpl(CartDao cartDao, CartItemDao cartItemDao, ProductDao productDao) {
        this.cartDao = cartDao;
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    @Transactional
    public void addProductToCart(int userId, int productId, int quantity) {
        Cart cart = cartDao.findByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setTotalAmount(BigDecimal.ZERO);
            User user = new User();
            user.setId(userId);
            cart.setUser(user);
            cart = cartDao.save(cart);
        }

        Product product = productDao.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getInventory() < quantity) {
            throw new RuntimeException("Not enough stock for this product");
        }

        CartItem existingItem = cartItemDao.findByCartIdAndProductId(cart.getId(), productId);

        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + quantity;
            if (product.getInventory() < newQuantity) {
                throw new RuntimeException("Not enough stock for this product");
            }
            existingItem.setQuantity(newQuantity);
            existingItem.setTotalPrice();
            cartItemDao.save(existingItem);
        } else {
            if (product.getInventory() < quantity) {
                throw new RuntimeException("Not enough stock for this product");
            }
            CartItem cartItem = new CartItem();
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(BigDecimal.valueOf(product.getPrice()));
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setTotalPrice();
            cartItemDao.save(cartItem);
        }

        updateTotalAmount(cart);
    }


    @Transactional
    public void removeProductFromCart(int userId, int productId) {
        Cart cart = cartDao.findByUserId(userId);
        if (cart == null) {
            throw new RuntimeException("Cart not found for userId: " + userId);
        }

        CartItem cartItem = cartItemDao.findByCartIdAndProductId(cart.getId(), productId);
        if (cartItem != null) {
            cartItemDao.delete(cartItem);

            updateTotalAmount(cart);
        } else {
            throw new RuntimeException("Product not found in the cart for productId: " + productId);
        }
    }

    private void updateTotalAmount(Cart cart) {
        BigDecimal totalAmount = cart.getCartItems().stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(totalAmount);
    }

    public Cart getCart(int userId) {
        Cart cart = cartDao.findByUserId(userId);
        if (cart == null) {
            throw new RuntimeException("Cart not found for userId: " + userId);
        }
        return cart;
    }

    @Transactional
    public void createCartIfNotExists(int userId) {
        Cart cart = cartDao.findByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setTotalAmount(BigDecimal.ZERO);
            User user = new User();
            user.setId(userId);
            cart.setUser(user);
            cartDao.save(cart);
        }
    }

    @Transactional
    public void clearCart(int userId) {
        Cart cart = cartDao.findByUserId(userId);
        if (cart == null) {
            throw new RuntimeException("Cart not found for userId: " + userId);
        }

        for (CartItem cartItem : cart.getCartItems()) {
            cartItemDao.delete(cartItem);
        }

        cart.setTotalAmount(BigDecimal.ZERO);
    }
}
