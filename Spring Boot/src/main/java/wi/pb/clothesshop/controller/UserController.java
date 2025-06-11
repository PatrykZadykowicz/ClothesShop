package wi.pb.clothesshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import wi.pb.clothesshop.dto.RoleAssignRequest;
import wi.pb.clothesshop.dto.UserDto;
import wi.pb.clothesshop.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsersWithRoles();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{userId}/roles")
    public ResponseEntity<?> assignRolesToUser(@PathVariable int userId, @RequestBody RoleAssignRequest request) {
        userService.assignRolesToUser(userId, request.getRoles());
        return ResponseEntity.ok("Roles assigned");
    }
}
