package com.example.ngoma;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryController(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            return ResponseEntity.status(409).body("Category already exists");
        }

        Category category = new Category();
        category.setName(request.getName());
        categoryRepository.save(category);

        return ResponseEntity.ok(category);
    }

    @GetMapping
    public ResponseEntity<List<Category>> listCategories() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequest request) {
        var categoryOpt = categoryRepository.findById(id);
        if (categoryOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Category not found");
        }

        Category category = categoryOpt.get();
        category.setName(request.getName());
        categoryRepository.save(category);

        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        if (!categoryRepository.existsById(id)){
            return ResponseEntity.status(404).body("Category not found");
        }

        if (!productRepository.findByCategoryId(id).isEmpty()){
            return ResponseEntity.status(409).body("Cannot delete category with existing products. Remove or reassign its products first.");
        }

        categoryRepository.deleteById(id);
        return ResponseEntity.ok().body("Category deleted");
    }
}