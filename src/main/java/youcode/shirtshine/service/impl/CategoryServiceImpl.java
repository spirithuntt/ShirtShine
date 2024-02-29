package youcode.shirtshine.service.impl;

// ... other imports
import youcode.shirtshine.dto.request.CategoryRequestDTO;
import youcode.shirtshine.dto.response.CategoryResponseDTO;
import youcode.shirtshine.exceptionHandler.OperationException;
import youcode.shirtshine.repository.CategoryRepository;
import youcode.shirtshine.service.CategoryService;

import youcode.shirtshine.domain.Category;
import youcode.shirtshine.exceptionHandler.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private CategoryResponseDTO convertToDto(Category category) {
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .type(category.getType())
                .build();
    }


    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        try {
            List<Category> categories = categoryRepository.findAll();
            return categories.stream()
                    .map(this::convertToDto)
                    .toList();
        } catch (Exception e) {
            throw new OperationException("Error occurred while fetching categories");
        }
    }
    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
            return convertToDto(category);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new OperationException("Error occurred while fetching category with id: " + id);
        }
    }

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        try {
            Category category = new Category();
            category.setName(categoryRequestDTO.getName());
            category.setType(categoryRequestDTO.getType());
            Category savedCategory = categoryRepository.save(category);
            return convertToDto(savedCategory);
        } catch (Exception e) {
            throw new OperationException("Error occurred while creating category");
        }
    }

    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
            category.setName(categoryRequestDTO.getName());
            category.setType(categoryRequestDTO.getType());
            Category updatedCategory = categoryRepository.save(category);
            return convertToDto(updatedCategory);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new OperationException("Error occurred while updating category with id: " + id);
        }
    }

    @Override
    public void deleteCategory(Long id) {
        try {
            if (!categoryRepository.existsById(id)) {
                throw new ResourceNotFoundException("Category not found with id: " + id);
            }
            categoryRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new OperationException("Error occurred while deleting category with id: " + id);
        }
    }

}