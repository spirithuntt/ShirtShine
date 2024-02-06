package youcode.shirtshine.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import youcode.shirtshine.domain.enums.AuthorityEnum;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"roles"}, allowSetters = true)
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "authorities")
    private List<Role> roles;

    @Enumerated(EnumType.STRING)
    private AuthorityEnum name;

}
