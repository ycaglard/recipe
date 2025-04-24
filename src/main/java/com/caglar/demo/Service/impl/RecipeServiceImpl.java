package com.caglar.demo.Service.impl;

import com.caglar.demo.Models.Recipe;
import com.caglar.demo.RecipeRepository;
import com.caglar.demo.Service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {
    /*
    * Required inputs to build a recipe;
    * Max-min calorie
    * max-min protein/carb/fat
    * unwanted ingredients
    * desired ingredients
    *
    * */

    RecipeRepository repository;

    @Autowired
    RecipeServiceImpl(RecipeRepository repository) {
        this.repository = repository;
    }

    public Recipe buildRecipe(){
        return new Recipe();
    }

    private double calculateCalories(){
        return 0.0;
    }

    private double calculateProtein(){
        return 0.0;
    }
}
