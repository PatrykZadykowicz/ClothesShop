package wi.pb.clothesshop.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleAssignRequest {
    private List<String> roles;
}