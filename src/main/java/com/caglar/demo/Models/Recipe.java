package com.caglar.demo.Models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    private long id;
    private String recipeText;
    private String type;
}
