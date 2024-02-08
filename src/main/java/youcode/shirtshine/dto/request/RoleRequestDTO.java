package youcode.shirtshine.dto.request;

import youcode.shirtshine.domain.Authority;
import youcode.shirtshine.domain.Role;

import java.util.List;

public record RoleRequestDTO(
        String name,
        List<Authority> authorities,
        boolean isDefault
){
    public Role toRole(){
        return Role.builder()
                .name(name)
                .isDefault(isDefault)
                .authorities(authorities)
                .build();
    }
}