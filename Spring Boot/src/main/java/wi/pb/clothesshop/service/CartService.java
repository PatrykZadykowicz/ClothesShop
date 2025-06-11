package wi.pb.clothesshop.service;

import wi.pb.clothesshop.entity.Cart;

import java.util.Optional;

public interface CartService {
    void addProductToCart(int userId, int productId, int quantity);
    void removeProductFromCart(int userId, int productId);
    Cart getCart(int userId);
    void clearCart(int userId);
    void createCartIfNotExists(int userId);
}
