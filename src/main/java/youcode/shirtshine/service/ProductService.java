package youcode.shirtshine.service;

import org.springframework.stereotype.Component;
import youcode.shirtshine.domain.Product;
import youcode.shirtshine.dto.request.ProductRequestDTO;
import youcode.shirtshine.dto.response.ProductResponseDTO;

import java.util.List;

@Component
public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO getProductById(Long id);
    ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO);
    void deleteProduct(Long id);

    List<ProductResponseDTO> getProductsByCategory(Long id);
    List<ProductResponseDTO> searchProducts(String query);
    List<ProductResponseDTO> getPromotionProducts();
}
