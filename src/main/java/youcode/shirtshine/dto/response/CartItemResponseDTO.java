package youcode.shirtshine.dto.response;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import youcode.shirtshine.domain.Product;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponseDTO {
    private Long productId;
    private Integer quantity;
    private String productName;
    private Double price;
    private String image;
    private Double productTotalPrice;
    private Integer promotion;
}
