package youcode.shirtshine.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import youcode.shirtshine.domain.Role;
import youcode.shirtshine.domain.Authority;
import youcode.shirtshine.domain.enums.AuthorityEnum;
import youcode.shirtshine.dto.request.RoleRequestDTO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponseDTO{
    private String name;
    private List<AuthorityEnum> authorities;
    private boolean isDefault;


    public static RoleResponseDTO fromRole(Role role){
        return RoleResponseDTO.builder()
                .name(role.getName())
                .authorities(role.getAuthorities().stream().map(Authority::getName).toList())
                .isDefault(role.isDefault())
                .build();
    }

}

