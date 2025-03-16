package com.caglar.demo.Service.impl;

import com.caglar.demo.Models.Recipe;
import com.caglar.demo.Service.RecipeService;

public class RecipeServiceImpl implements RecipeService {
    public Recipe buildRecipe(){
        return new Recipe();
    }

    private long calculateCalories(){
        return 0;
    }
}
