package youcode.shirtshine.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private String fullName;
    private String email;
    private String phone;
    private Integer zipCode;
    private String address;
    private String status;
}
