package wi.pb.clothesshop.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wi.pb.clothesshop.dao.RoleDao;
import wi.pb.clothesshop.entity.Role;

import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Role> findByName(String name) {
        try {
            Role role = entityManager.createQuery(
                            "SELECT r FROM Role r WHERE r.name = :name", Role.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return Optional.of(role);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Role save(Role role) {
        entityManager.persist(role);
        return role;
    }
}
