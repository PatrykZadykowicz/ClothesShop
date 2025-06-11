package wi.pb.clothesshop.dto;

import lombok.Getter;
import wi.pb.clothesshop.entity.User;
import wi.pb.clothesshop.entity.Role;

import java.util.List;

@Getter
public class UserDto {
    private int id;
    private String email;
    private List<String> roles;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.roles = user.getRoles().stream()
                .map(Role::getName)
                .toList();
    }
}
