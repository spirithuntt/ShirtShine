package youcode.shirtshine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import youcode.shirtshine.domain.Product;
import youcode.shirtshine.dto.response.ProductResponseDTO;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
