package youcode.shirtshine.dto.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestDTO {
    private String name;
    private Integer type;
}
