package youcode.shirtshine.service;

import org.springframework.stereotype.Component;
import youcode.shirtshine.domain.Category;
import youcode.shirtshine.dto.request.CategoryRequestDTO;
import youcode.shirtshine.dto.response.CategoryResponseDTO;
import java.util.List;

@Component
public interface CategoryService {
    List<CategoryResponseDTO> getAllCategories();
    CategoryResponseDTO getCategoryById(Long id);
    CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO);
    CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO);
    void deleteCategory(Long id);
}