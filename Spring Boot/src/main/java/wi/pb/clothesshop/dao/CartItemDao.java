package wi.pb.clothesshop.dao;

import wi.pb.clothesshop.entity.CartItem;

import java.util.List;

public interface CartItemDao {
    CartItem findByCartIdAndProductId(int cartId, int productId);

    List<CartItem> findByCartId(int cartId);

    CartItem save(CartItem cartItem);

    void delete(CartItem cartItem);
}