package wi.pb.clothesshop.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wi.pb.clothesshop.dao.OrderDao;
import wi.pb.clothesshop.entity.Order;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderDaoImpl implements OrderDao {

    EntityManager entityManager;

    @Autowired
    public OrderDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    @Override
    public List<Order> getAll() {
        TypedQuery<Order> query = entityManager.createQuery("select o from Order o", Order.class);
        return query.getResultList();
    }

    @Transactional
    @Override
    public Order save(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Transactional
    @Override
    public Order update(Order order) {
        return entityManager.merge(order);
    }

    @Transactional
    @Override
    public void delete(Order order) {
        entityManager.remove(order);
    }

    @Override
    public List<Order> findByUserId(int userId) {
        TypedQuery<Order> query = entityManager.createQuery(
                "select distinct o from Order o " +
                        "left join fetch o.orderItems oi " +
                        "left join fetch oi.product " +
                        "where o.user.id = :userid",
                Order.class);
        query.setParameter("userid", userId);
        return query.getResultList();
    }

}
