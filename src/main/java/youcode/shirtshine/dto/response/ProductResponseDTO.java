package youcode.shirtshine.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import youcode.shirtshine.domain.Category;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Category category;
    private Double price;
    private String image;
    private Integer stock;
    private Integer promotion;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;




}
