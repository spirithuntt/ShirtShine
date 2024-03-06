package youcode.shirtshine.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import youcode.shirtshine.domain.enums.StatusEnum;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {
    private String fullName;
    private String email;
    private String phone;
    private Integer zipCode;
    private String address;
    private List<OrderItemRequestDTO> orderItems;
    private StatusEnum status;

}
