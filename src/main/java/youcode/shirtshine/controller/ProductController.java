package youcode.shirtshine.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import youcode.shirtshine.domain.Product;
import youcode.shirtshine.dto.request.ProductRequestDTO;
import youcode.shirtshine.dto.response.ProductResponseDTO;
import youcode.shirtshine.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO product) {
        ProductResponseDTO productResponseDTO = productService.createProduct(product);
        return new ResponseEntity<>(productResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO product) {
        ProductResponseDTO productResponseDTO = productService.updateProduct(id, product);
        return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        ProductResponseDTO productResponseDTO = productService.getProductById(id);
        return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByCategory(@PathVariable Long id) {
        List<ProductResponseDTO> products = productService.getProductsByCategory(id);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> searchProducts(@RequestParam String query) {
        List<ProductResponseDTO> products = productService.searchProducts(query);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/promotion")
    public ResponseEntity<List<ProductResponseDTO>> getPromotionProducts() {
        List<ProductResponseDTO> products = productService.getPromotionProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
