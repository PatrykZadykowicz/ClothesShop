package wi.pb.clothesshop.service;

import wi.pb.clothesshop.dto.RegisterRequest;
import wi.pb.clothesshop.dto.UserDto;

import java.util.List;

public interface UserService {
    void register(RegisterRequest request);
    List<UserDto> getAllUsersWithRoles();
    void assignRolesToUser(int userId, List<String> roleNames);
}
