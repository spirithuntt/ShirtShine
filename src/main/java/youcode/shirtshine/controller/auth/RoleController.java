package youcode.shirtshine.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import youcode.shirtshine.domain.Role;
import youcode.shirtshine.dto.request.RoleRequestDTO;
import youcode.shirtshine.dto.response.RoleResponseDTO;
import youcode.shirtshine.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleResponseDTO>> getAll(){
        List<Role> roles = roleService.getAll();
        if (roles.isEmpty()) return ResponseEntity.badRequest().build();
        else return new ResponseEntity<>(roles.stream().map(RoleResponseDTO::fromRole).toList(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RoleResponseDTO> save(@RequestBody RoleRequestDTO roleToSave){
        Role role = roleService.save(roleToSave.toRole(), false);
        if (role == null) return ResponseEntity.badRequest().build();
        else return new ResponseEntity<>(RoleResponseDTO.fromRole(role), HttpStatus.OK);
    }

    @PutMapping("/grant_authorities/{id}")
    public ResponseEntity<RoleResponseDTO> grantAuthorities(@RequestBody RoleRequestDTO rolesAuthorities, @PathVariable Long id){
        Role role = roleService.grantAuthorities(rolesAuthorities.toRole().getAuthorities(), id);
        if (role == null) return ResponseEntity.badRequest().build();
        else return new ResponseEntity<>(RoleResponseDTO.fromRole(role), HttpStatus.OK);
    }

    @PutMapping("/revoke_authorities/{id}")
    public ResponseEntity<RoleResponseDTO> revokeAuthorities(@RequestBody RoleRequestDTO rolesAuthorities, @PathVariable Long id){
        Role role = roleService.revokeAuthorities(rolesAuthorities.toRole().getAuthorities(), id);
        if (role == null) return ResponseEntity.badRequest().build();
        else return new ResponseEntity<>(RoleResponseDTO.fromRole(role), HttpStatus.OK);
    }

}

