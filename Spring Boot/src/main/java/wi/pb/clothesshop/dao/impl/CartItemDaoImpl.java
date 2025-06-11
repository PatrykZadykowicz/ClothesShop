package wi.pb.clothesshop.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;
import wi.pb.clothesshop.entity.CartItem;
import wi.pb.clothesshop.dao.CartItemDao;

import java.util.List;

@Repository
public class CartItemDaoImpl implements CartItemDao {

    private final EntityManager entityManager;

    public CartItemDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public CartItem findByCartIdAndProductId(int cartId, int productId) {
        try {
            return entityManager.createQuery(
                            "SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.product.id = :productId",
                            CartItem.class)
                    .setParameter("cartId", cartId)
                    .setParameter("productId", productId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<CartItem> findByCartId(int cartId) {
        return entityManager.createQuery(
                        "SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId",
                        CartItem.class)
                .setParameter("cartId", cartId)
                .getResultList();
    }

    public CartItem save(CartItem cartItem) {
        if (cartItem.getId() == null) {
            entityManager.persist(cartItem);
        } else {
            entityManager.merge(cartItem);
        }
        return cartItem;
    }

    public void delete(CartItem cartItem) {
        entityManager.remove(cartItem);
    }
}
