package youcode.shirtshine.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {
    private String name;
    private String description;
    private Double price;
//    private MultipartFile image;
    private String image;
    private Integer stock;
    private Integer promotion;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private Long category_id;
}
