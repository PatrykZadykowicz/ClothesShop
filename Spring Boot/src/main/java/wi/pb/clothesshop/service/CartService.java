package wi.pb.clothesshop.service;

import wi.pb.clothesshop.entity.Cart;

import java.util.Optional;

public interface CartService {

    void clearCart(Long id);
    Cart getCartByUserId(Long userId);

}
