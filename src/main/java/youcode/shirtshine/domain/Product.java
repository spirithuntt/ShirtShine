package youcode.shirtshine.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private Integer promotion;
    private String image;
    @CreationTimestamp
    private LocalDateTime created_at;
    @UpdateTimestamp
    private LocalDateTime updated_at;


    @ManyToMany
    private List<Cart> carts;

    @ManyToOne()
    private Category category;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;




}
