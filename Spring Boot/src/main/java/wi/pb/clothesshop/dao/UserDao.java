package wi.pb.clothesshop.dao;

import wi.pb.clothesshop.entity.Product;
import wi.pb.clothesshop.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findById(int id);
    Optional<User> findByEmail(String email);
    List<User> getAll();
    User save(User user);
    User update(User user);
    void delete(User user);

}
