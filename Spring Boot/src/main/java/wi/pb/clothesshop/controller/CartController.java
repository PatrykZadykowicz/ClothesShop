package wi.pb.clothesshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import wi.pb.clothesshop.entity.Cart;
import wi.pb.clothesshop.service.CartService;
import wi.pb.clothesshop.service.UserContextService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final UserContextService userContextService;

    public CartController(CartService cartService, UserContextService userContextService) {
        this.cartService = cartService;
        this.userContextService = userContextService;
    }

    //@PreAuthorize("hasRole('USER')")
    @PostMapping("/add")
    public ResponseEntity<?> addProductToCart(@RequestParam int productId, @RequestParam int quantity) {
        int userId = userContextService.getUserId();

        if (userId == 0)
            return ResponseEntity.noContent().build();

        cartService.addProductToCart(userId, productId, quantity);
        return ResponseEntity.ok("Product added to cart");
    }

    //@PreAuthorize("hasRole('USER')")
    @DeleteMapping("/remove")
    public ResponseEntity<?> removeProductFromCart(@RequestParam int productId) {
        int userId = userContextService.getUserId();

        if (userId == 0)
            return ResponseEntity.noContent().build();

        cartService.removeProductFromCart(userId, productId);
        return ResponseEntity.ok("Product removed from cart");
    }

    //@PreAuthorize("hasRole('USER')")
    @GetMapping("/view")
    public ResponseEntity<Cart> getCart() {
        int userId = userContextService.getUserId();

        if (userId == 0)
            return ResponseEntity.noContent().build();

        Cart cart = cartService.getCart(userId);
        return ResponseEntity.ok(cart);
    }

    //@PreAuthorize("hasRole('USER')")
    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart(@RequestParam int userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared successfully");
    }
}
