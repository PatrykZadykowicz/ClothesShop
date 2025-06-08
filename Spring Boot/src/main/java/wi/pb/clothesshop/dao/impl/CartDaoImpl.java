package wi.pb.clothesshop.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import wi.pb.clothesshop.dao.CartDao;
import wi.pb.clothesshop.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import wi.pb.clothesshop.entity.Order;

import java.util.Optional;

@Repository
public class CartDaoImpl implements CartDao {

    private EntityManager entityManager;

    @Autowired
    public CartDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Cart findByUserId(Long userId) {
        TypedQuery<Cart> query = entityManager.createQuery("select c from Cart c where user.id = :userid", Cart.class);
        query.setParameter("userid", userId);
        return query.getSingleResult();
    }
}
