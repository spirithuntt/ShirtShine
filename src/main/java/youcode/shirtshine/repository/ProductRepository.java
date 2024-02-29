package youcode.shirtshine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import youcode.shirtshine.domain.Product;
import youcode.shirtshine.dto.response.ProductResponseDTO;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
//findAllByCategoryId
    List<Product> findAllByCategoryId(Long id);

    List<Product> findAllByPromotionGreaterThan(int promotion);

    List<Product> findAllByNameContainingIgnoreCase(String query);
}
