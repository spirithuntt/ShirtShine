package youcode.shirtshine.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import youcode.shirtshine.domain.Authority;

import java.util.List;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    @Query(value = "select * from authority where name in %:authorities%", nativeQuery = true)
    List<Authority> findAllByName(@Param("authorities") List<String> authorities);
}
