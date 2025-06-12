package wi.pb.clothesshop;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import wi.pb.clothesshop.dao.UserDao;
import wi.pb.clothesshop.dao.RoleDao;
import wi.pb.clothesshop.dto.RegisterRequest;
import wi.pb.clothesshop.entity.User;
import wi.pb.clothesshop.entity.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import wi.pb.clothesshop.service.impl.UserServiceImpl;

import java.util.Optional;

class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @Mock
    private RoleDao roleDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private RegisterRequest registerRequest;
    private Role userRole;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("John");
        registerRequest.setLastName("Doe");
        registerRequest.setEmail("john.doe@example.com");
        registerRequest.setPassword("password123");

        userRole = new Role("ROLE_USER");
    }

    @Test
    void testRegister_UserAlreadyExists() {
        when(userDao.findByEmail(registerRequest.getEmail())).thenReturn(Optional.of(new User()));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.register(registerRequest);
        });

        assertEquals("User already exists", exception.getMessage());
    }

    @Test
    void testRegister_Success() {
        when(userDao.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(roleDao.findByName("ROLE_USER")).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");

        userService.register(registerRequest);

        verify(userDao, times(1)).save(any(User.class));  // Sprawdzenie, czy metoda save została wywołana
        verify(roleDao, times(1)).findByName("ROLE_USER");  // Sprawdzenie, czy rola została znaleziona
    }
}
