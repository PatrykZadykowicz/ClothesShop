package wi.pb.clothesshop.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;
import wi.pb.clothesshop.dao.CartDao;
import wi.pb.clothesshop.entity.Cart;

@Repository
public class CartDaoImpl implements CartDao {

    private final EntityManager entityManager;

    public CartDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Cart findByUserId(int userId) {
        try {
            return entityManager.createQuery("SELECT c FROM Cart c WHERE c.user.id = :userId", Cart.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Cart save(Cart cart) {
        if (cart.getId() == 0) {
            entityManager.persist(cart);
        } else {
            entityManager.merge(cart);
        }
        return cart;
    }

    public void delete(Cart cart) {
        entityManager.remove(cart);
    }
}

