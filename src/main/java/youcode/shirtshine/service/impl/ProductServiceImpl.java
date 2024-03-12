package youcode.shirtshine.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import youcode.shirtshine.domain.Category;
import youcode.shirtshine.domain.Product;
import youcode.shirtshine.dto.request.ProductRequestDTO;
import youcode.shirtshine.dto.response.ProductResponseDTO;
import youcode.shirtshine.exceptionHandler.OperationException;
import youcode.shirtshine.exceptionHandler.ResourceNotFoundException;
import youcode.shirtshine.repository.CategoryRepository;

import youcode.shirtshine.repository.ProductRepository;
import youcode.shirtshine.service.ProductService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    private ProductResponseDTO convertToDTO(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .price(product.getPrice())
                .image(product.getImage())
                .stock(product.getStock())
                //get quantity from cartItem
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
                    .map(this::convertToDTO)
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

            String fileName;
            if (productRequestDTO.getImage().isEmpty()) {
                fileName = "default.jpg";
            } else {
                fileName = StringUtils.cleanPath(Objects.requireNonNull(productRequestDTO.getImage().getOriginalFilename()));
                Path path = Path.of("src/main/resources/static/images");
                Files.copy(productRequestDTO.getImage().getInputStream(), path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            }

            Product product = Product.builder()
                    .name(productRequestDTO.getName())
                    .description(productRequestDTO.getDescription())
                    .price(productRequestDTO.getPrice())
                    .image(fileName)
                    .image("http://localhost:8080/images/"+ fileName)
                    .stock(productRequestDTO.getStock())
                    .promotion(productRequestDTO.getPromotion())
                    .category(category)
                    .build();

            Product savedProduct = productRepository.save(product);

            return convertToDTO(savedProduct);
        } catch (Exception e) {
            throw new OperationException("Error occurred while creating product");
        }
    }


    @Override
    public ProductResponseDTO getProductById(Long id) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

            return convertToDTO(product);
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

            String fileName;
            if (productRequestDTO.getImage().isEmpty()) {
                fileName = product.getImage();
            } else {
                fileName = StringUtils.cleanPath(productRequestDTO.getImage().getOriginalFilename());
                Path path = Paths.get("src/main/resources/static/images");
                Files.copy(productRequestDTO.getImage().getInputStream(), path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            }


            product.setName(productRequestDTO.getName());
            product.setDescription(productRequestDTO.getDescription());
            product.setPrice(productRequestDTO.getPrice());
            product.setImage(fileName);
//            product.setImage(productRequestDTO.getImage());
            product.setStock(productRequestDTO.getStock());
            product.setPromotion(productRequestDTO.getPromotion());
            product.setCategory(category);

            Product updatedProduct = productRepository.save(product);

            return convertToDTO(updatedProduct);
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

    @Override
    public List<ProductResponseDTO> getProductsByCategory(Long id) {
        try {
            List<Product> products = productRepository.findAllByCategoryId(id);

            return products.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new OperationException("Error occurred while fetching products by category with id: " + id);
        }
    }

    @Override
    public List<ProductResponseDTO> searchProducts(String query) {
        try {
            List<Product> products = productRepository.findAllByNameContainingIgnoreCase(query);

            return products.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new OperationException("Error occurred while searching products with query: " + query);
        }
    }

    @Override
    public List<ProductResponseDTO> getPromotionProducts() {
        try {
            List<Product> products = productRepository.findAllByPromotionGreaterThan(0);

            return products.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new OperationException("Error occurred while fetching promotion products");
        }
    }

}