package pl.javastart.restoffers.category;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.javastart.restoffers.offer.NoCategoryException;
import pl.javastart.restoffers.offer.OfferDto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/categories")
@RestController
public class CategoryRestController {

    private final CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/names")
    public List<String> categoryNames() {
        return categoryService.findAllNames();
    }

    @GetMapping("")
    public List<CategoryDto> findAll() {
        return categoryService.findAll();
    }

    @PostMapping("")
    public CategoryDto addOffer(@RequestBody CategoryDto categoryDto) {
        return categoryService.insert(categoryDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
