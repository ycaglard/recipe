package com.caglar.demo;

import com.caglar.demo.Models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe,Long> {
    // Find by exact title
    Recipe findByTitle(String title);

    // Find all recipes where the ingredients JSON string contains the given ingredient substring (case-insensitive, works with @Lob)
    // Placeholder for calories range (if/when calories field is added)
    // List<Recipe> findByCaloriesBetween(Double min, Double max);
}
