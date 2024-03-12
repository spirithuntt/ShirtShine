package youcode.shirtshine.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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


    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,  fetch=FetchType.LAZY)
    private List<CartItem> cartItems;


    @ManyToOne()
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,  fetch=FetchType.LAZY)
    private List<OrderItem> orderItems;

}
