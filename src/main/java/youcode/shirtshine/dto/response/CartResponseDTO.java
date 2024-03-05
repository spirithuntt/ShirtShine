package youcode.shirtshine.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import youcode.shirtshine.domain.Product;
import youcode.shirtshine.domain.User;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDTO {
    private List<Product> products;
    private Integer quantity;
}