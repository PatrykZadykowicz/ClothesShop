package wi.pb.clothesshop.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wi.pb.clothesshop.dao.RoleDao;
import wi.pb.clothesshop.dao.UserDao;
import wi.pb.clothesshop.dto.RegisterRequest;
import wi.pb.clothesshop.dto.UserDto;
import wi.pb.clothesshop.entity.Role;
import wi.pb.clothesshop.entity.User;
import wi.pb.clothesshop.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao, RoleDao roleDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role role = roleDao.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        user.getRoles().add(role);
        userDao.save(user);
    }

    public List<UserDto> getAllUsersWithRoles() {
        return userDao.getAll().stream()
                .map(UserDto::new)
                .toList();
    }

    @Transactional
    public void assignRolesToUser(int userId, List<String> roleNames) {
        User user = userDao.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Set<Role> newRoles = roleNames.stream()
                .map(roleName -> roleDao.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                .collect(Collectors.toSet());

        user.setRoles(newRoles);
        userDao.update(user);
    }
}