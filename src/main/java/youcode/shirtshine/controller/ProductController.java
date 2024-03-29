package youcode.shirtshine.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import youcode.shirtshine.dto.request.ProductRequestDTO;
import youcode.shirtshine.dto.response.ProductResponseDTO;
import youcode.shirtshine.exceptionHandler.OperationException;
import youcode.shirtshine.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        try {
            List<ProductResponseDTO> products = productService.getAllProducts();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (OperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCTS')")
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ProductResponseDTO> createProduct(ProductRequestDTO product) {
        try {
            ProductResponseDTO productResponseDTO = productService.createProduct(product);
            return new ResponseEntity<>(productResponseDTO, HttpStatus.CREATED);
        } catch (OperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCTS')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @ModelAttribute ProductRequestDTO product) {
        try {
            ProductResponseDTO productResponseDTO = productService.updateProduct(id, product);
            return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
        } catch (OperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCTS')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (OperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        try {
            ProductResponseDTO productResponseDTO = productService.getProductById(id);
            return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
        } catch (OperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByCategory(@PathVariable Long id) {
        try {
            List<ProductResponseDTO> products = productService.getProductsByCategory(id);
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (OperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> searchProducts(@RequestParam String query) {
        try {
            List<ProductResponseDTO> products = productService.searchProducts(query);
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (OperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/promotion")
    public ResponseEntity<List<ProductResponseDTO>> getPromotionProducts() {
        try {
            List<ProductResponseDTO> products = productService.getPromotionProducts();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (OperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}