package youcode.shirtshine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import youcode.shirtshine.domain.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByIsDefaultTrue();


    Optional<Role> findByName(String name);
}
