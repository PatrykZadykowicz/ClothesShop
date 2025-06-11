package wi.pb.clothesshop.dao;

import org.springframework.stereotype.Repository;
import wi.pb.clothesshop.entity.Role;

import java.util.Optional;

public interface RoleDao {
    Optional<Role> findByName(String name);
    Role save(Role role);
}
