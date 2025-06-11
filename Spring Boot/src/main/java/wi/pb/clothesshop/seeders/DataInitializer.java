package wi.pb.clothesshop.seeders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import wi.pb.clothesshop.dao.RoleDao;
import wi.pb.clothesshop.dao.UserDao;
import wi.pb.clothesshop.entity.Role;
import wi.pb.clothesshop.entity.User;

@Component
public class DataInitializer implements CommandLineRunner {
    private final RoleDao roleDao;
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleDao roleDao, UserDao userDao, PasswordEncoder passwordEncoder) {
        this.roleDao = roleDao;
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // ROLE_USER
        roleDao.findByName("ROLE_USER").orElseGet(() -> {
            Role userRole = new Role("ROLE_USER");
            return roleDao.save(userRole);
        });

        // ROLE_MANAGER
        Role managerRole = roleDao.findByName("ROLE_MANAGER").orElseGet(() -> {
            Role role = new Role("ROLE_MANAGER");
            return roleDao.save(role);
        });


        // ROLE_ADMIN
        Role adminRole = roleDao.findByName("ROLE_ADMIN").orElseGet(() -> {
            Role role = new Role("ROLE_ADMIN");
            return roleDao.save(role);
        });

        userDao.findByEmail("manager@shop.com").orElseGet(() -> {
            User manager = new User();
            manager.setFirstName("Magda");
            manager.setLastName("Menadżer");
            manager.setEmail("manager@shop.com");
            manager.setPassword(passwordEncoder.encode("manager123"));
            manager.getRoles().add(managerRole);
            return userDao.save(manager);
        });

        // Admin user
        userDao.findByEmail("admin@admin.com").orElseGet(() -> {
            User admin = new User();
            admin.setFirstName("Super");
            admin.setLastName("Admin");
            admin.setEmail("admin@admin.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.getRoles().add(adminRole);
            return userDao.save(admin);
        });
    }
}