package youcode.shirtshine.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import youcode.shirtshine.domain.Product;
import youcode.shirtshine.dto.response.ProductResponseDTO;
import youcode.shirtshine.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id, @RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.update(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
