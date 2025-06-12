package wi.pb.clothesshop.dto;

import lombok.Getter;
import lombok.Setter;
import wi.pb.clothesshop.enums.OrderStatus;

@Getter
@Setter
public class UpdateOrderStatusRequest {
    private OrderStatus newStatus;
}
