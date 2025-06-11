package wi.pb.clothesshop.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import wi.pb.clothesshop.dao.UserDao;
import wi.pb.clothesshop.entity.User;
import wi.pb.clothesshop.service.UserContextService;

import java.util.Optional;

@Service
public class UserContextServiceImpl implements UserContextService {
    public final UserDao userDao;

    public UserContextServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public int getUserId() {
        String email = getCurrentUserEmail();

        Optional<User> user = userDao.findByEmail(email);
        return user.map(User::getId).orElse(0);
    }

    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return null;
    }
}
