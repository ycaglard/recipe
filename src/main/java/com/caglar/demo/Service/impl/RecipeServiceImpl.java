package com.caglar.demo.Service.impl;

import com.caglar.demo.Models.Recipe;
import com.caglar.demo.Service.RecipeService;

public class RecipeServiceImpl implements RecipeService {
    /*
    * Required inputs to build a recipe;
    * Max-min calorie
    * max-min protein/carb/fat
    * unwanted ingredients
    * desired ingredients
    *
    * */
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
