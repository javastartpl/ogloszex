package pl.javastart.restoffers.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String category);

    @Query("SELECT new pl.javastart.restoffers.category.CategoryDto(c.name, c.description, c.offers.size)" +
            "FROM Category c")
    List<CategoryDto> findAllWithCount();

}
