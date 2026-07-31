package com.example.ngoma;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DashboardController {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public DashboardController(UserRepository userRepository,
                               ProductRepository productRepository,
                               CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard() {
        long totalMembers = userRepository.count();
        long verifiedMembers = userRepository.countByEnabled(true);
        long pendingMembers = userRepository.countByEnabled(false);

        long totalProducts = productRepository.count();

        List<Category> categories = categoryRepository.findAll();
        Map<String, Long> productsPerCategory = new LinkedHashMap<>();
        for (Category category : categories) {
            long count = productRepository.findByCategoryId(category.getId()).size();
            productsPerCategory.put(category.getName(), count);
        }

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("totalMembers", totalMembers);
        response.put("verifiedMembers", verifiedMembers);
        response.put("pendingMembers", pendingMembers);
        response.put("totalProducts", totalProducts);
        response.put("totalCategories", categories.size());
        response.put("productsPerCategory", productsPerCategory);

        return ResponseEntity.ok(response);
    }
}