package com.caglar.demo.Controller;

import com.caglar.demo.Dto.RecipeDto;
import com.caglar.demo.Models.Recipe;
import com.caglar.demo.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.List;
import com.caglar.demo.Utils.NutritionApiClient;
import com.caglar.demo.Utils.IngredientParser;

@RestController
@RequestMapping("/recipe")

public class RecipeController {
    private final RecipeRepository recipeRepository;
    private final NutritionApiClient nutritionApiClient;

    public RecipeController(RecipeRepository recipeRepository, NutritionApiClient nutritionApiClient) {
        this.recipeRepository = recipeRepository;
        this.nutritionApiClient = nutritionApiClient;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable Long id) {
        return recipeRepository.findById(id)
            .map(recipe -> {
                RecipeDto dto = new RecipeDto();
                dto.setId(recipe.getId());
                dto.setName(recipe.getTitle());
                dto.setCaloriesPer100g(recipe.getCaloriesPer100g());
                dto.setProteinsPer100g(recipe.getProteinsPer100g());
                dto.setCarbsPer100g(recipe.getCarbsPer100g());
                dto.setFatPer100g(recipe.getFatPer100g());
                return ResponseEntity.ok(dto);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    // GET /recipe/name/{name}
    @GetMapping("/name/{name}")
    public ResponseEntity<RecipeDto> getRecipeByName(@PathVariable String name) {
        Recipe recipe = recipeRepository.findByTitle(name);
        if (recipe == null) return ResponseEntity.notFound().build();
        RecipeDto dto = new RecipeDto();
        dto.setId(recipe.getId());
        dto.setName(recipe.getTitle());
        dto.setCaloriesPer100g(recipe.getCaloriesPer100g());
        dto.setProteinsPer100g(recipe.getProteinsPer100g());
        dto.setCarbsPer100g(recipe.getCarbsPer100g());
        dto.setFatPer100g(recipe.getFatPer100g());
        return ResponseEntity.ok(dto);
    }

    // GET /recipe/calories?min=100&max=300
    @GetMapping("/calories")
    public ResponseEntity<List<RecipeDto>> getRecipesByCaloriesRange(@RequestParam Double min, @RequestParam Double max) {
        List<Recipe> allRecipes = recipeRepository.findAll();
        List<RecipeDto> dtos = new ArrayList<>();
        for (Recipe recipe : allRecipes) {
            Double calPer100g = recipe.getCaloriesPer100g();
            if (calPer100g != null && calPer100g >= min && calPer100g <= max) {
                RecipeDto dto = new RecipeDto();
                dto.setId(recipe.getId());
                dto.setName(recipe.getTitle());
                dto.setDirections(recipe.getDirections());
                dto.setCaloriesPer100g(recipe.getCaloriesPer100g());
                dto.setProteinsPer100g(recipe.getProteinsPer100g());
                dto.setCarbsPer100g(recipe.getCarbsPer100g());
                dto.setFatPer100g(recipe.getFatPer100g());
                dtos.add(dto);
            }
        }
        return ResponseEntity.ok(dtos);
    }
}

