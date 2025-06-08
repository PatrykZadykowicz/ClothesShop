package wi.pb.clothesshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wi.pb.clothesshop.dao.CartDao;
import wi.pb.clothesshop.entity.Cart;
import wi.pb.clothesshop.service.CartService;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private CartDao cartDao;

    @Autowired
    public CartServiceImpl(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    @Override
    public void clearCart(Long id) {
        // TODO
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartDao.findByUserId(userId);
    }
}
