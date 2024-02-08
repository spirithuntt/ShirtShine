package youcode.shirtshine.dto.response;

import java.util.List;
import youcode.shirtshine.domain.Role;
import youcode.shirtshine.domain.Authority;
import youcode.shirtshine.domain.enums.AuthorityEnum;


public record RoleResponseDTO(
        String name,
        List<AuthorityEnum> authorities,
        boolean isDefault
) {
    public static RoleResponseDTO fromRole(Role role){
        return new RoleResponseDTO(
                role.getName(),
                role.getAuthorities().stream().map(Authority::getName).toList(),
                role.isDefault()
        );
    }
}

