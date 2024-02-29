package youcode.shirtshine.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import youcode.shirtshine.domain.Category;
import youcode.shirtshine.domain.Product;
import youcode.shirtshine.dto.request.ProductRequestDTO;
import youcode.shirtshine.dto.response.ProductResponseDTO;
import youcode.shirtshine.exceptionHandler.OperationException;
import youcode.shirtshine.exceptionHandler.ResourceNotFoundException;
import youcode.shirtshine.repository.CategoryRepository;
import youcode.shirtshine.repository.ProductRepository;
import youcode.shirtshine.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private ProductResponseDTO convertToResponseDTO(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .price(product.getPrice())
                .image(product.getImage())
                .stock(product.getStock())
                .promotion(product.getPromotion())
                .created_at(product.getCreated_at())
                .updated_at(product.getUpdated_at())
                .build();
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        try {
            List<Product> products = productRepository.findAll();

            return products.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new OperationException("Error occurred while fetching products");
        }
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        try {
            Category category = categoryRepository.findById(productRequestDTO.getCategory_id())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + productRequestDTO.getCategory_id()));

            Product product = Product.builder()
                    .name(productRequestDTO.getName())
                    .description(productRequestDTO.getDescription())
                    .price(productRequestDTO.getPrice())
                    .image(productRequestDTO.getImage())
                    .stock(productRequestDTO.getStock())
                    .promotion(productRequestDTO.getPromotion())
                    .created_at(productRequestDTO.getCreated_at())
                    .updated_at(productRequestDTO.getUpdated_at())
                    .category(category)
                    .build();

            Product savedProduct = productRepository.save(product);

            return convertToResponseDTO(savedProduct);
        } catch (Exception e) {
            throw new OperationException("Error occurred while creating product");
        }
    }


    @Override
    public ProductResponseDTO getProductById(Long id) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

            return convertToResponseDTO(product);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new OperationException("Error occurred while fetching product with id: " + id);
        }
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
            Category category = categoryRepository.findById(productRequestDTO.getCategory_id())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + productRequestDTO.getCategory_id()));

            product.setName(productRequestDTO.getName());
            product.setDescription(productRequestDTO.getDescription());
            product.setPrice(productRequestDTO.getPrice());
            product.setImage(productRequestDTO.getImage());
            product.setStock(productRequestDTO.getStock());
            product.setPromotion(productRequestDTO.getPromotion());
            product.setCreated_at(productRequestDTO.getCreated_at());
            product.setUpdated_at(productRequestDTO.getUpdated_at());
            product.setCategory(category);

            Product updatedProduct = productRepository.save(product);

            return convertToResponseDTO(updatedProduct);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new OperationException("Error occurred while updating product with id: " + id);
        }
    }

    @Override
    public void deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new OperationException("Error occurred while deleting product with id: " + id);
        }
    }
}