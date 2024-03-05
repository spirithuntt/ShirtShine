package youcode.shirtshine.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import youcode.shirtshine.dto.request.CategoryRequestDTO;
import youcode.shirtshine.dto.response.CategoryResponseDTO;
import youcode.shirtshine.exceptionHandler.OperationException;
import youcode.shirtshine.service.CategoryService;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        try {
            List<CategoryResponseDTO> categories = categoryService.getAllCategories();
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (OperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id) {
        try {
            CategoryResponseDTO category = categoryService.getCategoryById(id);
            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (OperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDTO category) {
        try {
            CategoryResponseDTO categoryResponseDTO = categoryService.createCategory(category);
            return new ResponseEntity<>(categoryResponseDTO, HttpStatus.CREATED);
        } catch (OperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryRequestDTO category) {
        try {
            CategoryResponseDTO categoryResponseDTO = categoryService.updateCategory(id, category);
            return new ResponseEntity<>(categoryResponseDTO, HttpStatus.OK);
        } catch (OperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (OperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}