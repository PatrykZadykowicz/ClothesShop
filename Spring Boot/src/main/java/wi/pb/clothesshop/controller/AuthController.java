package wi.pb.clothesshop.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;
import wi.pb.clothesshop.dto.LoginRequest;
import wi.pb.clothesshop.dto.RegisterRequest;
import wi.pb.clothesshop.service.CartService;
import wi.pb.clothesshop.service.UserContextService;
import wi.pb.clothesshop.service.UserService;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final CartService cartService;
    private final UserContextService userContextService;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, CartService cartService, UserContextService userContextService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.cartService = cartService;
        this.userContextService = userContextService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        userService.register(request);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        httpRequest.getSession(true).setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                context
        );

        int userId = userContextService.getUserId();

        if (userId == 0)
            return ResponseEntity.badRequest().body("Invalid email or password");

        cartService.createCartIfNotExists(userId);

        return ResponseEntity.ok("Login successful");
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        SecurityContextHolder.clearContext();

        return ResponseEntity.ok("Logged out");
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(401).body("Nie jesteś zalogowany");
        }
        String email = auth.getName();
        return ResponseEntity.ok(Map.of("email", email));
    }
}
