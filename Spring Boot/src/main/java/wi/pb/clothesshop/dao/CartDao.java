package wi.pb.clothesshop.dao;

import wi.pb.clothesshop.entity.Cart;

import java.util.Optional;

public interface CartDao {
    Cart findByUserId(Long userId);
}
