package youcode.shirtshine.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import youcode.shirtshine.domain.Category;
import youcode.shirtshine.dto.request.CategoryRequestDTO;
import youcode.shirtshine.dto.response.CategoryResponseDTO;
import youcode.shirtshine.repository.CategoryRepository;
import youcode.shirtshine.service.impl.CategoryServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CategoryCrudTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateCategory() {
        CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO();
        categoryRequestDTO.setName("ttttttttttttt");

        Category category = new Category();
        category.setName(categoryRequestDTO.getName());

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryResponseDTO createdCategory = categoryService.createCategory(categoryRequestDTO);

        assertEquals(category.getName(), createdCategory.getName());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    public void testUpdateCategory() {
        CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO();
        categoryRequestDTO.setName("test category");

        Category category = new Category();
        category.setName(categoryRequestDTO.getName());

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryResponseDTO updatedCategory = categoryService.updateCategory(1L, categoryRequestDTO);

        assertEquals(category.getName(), updatedCategory.getName());
        verify(categoryRepository, times(1)).findById(anyLong());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }


}