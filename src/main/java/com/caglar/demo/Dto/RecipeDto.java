package com.caglar.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecipeDto {
    // Nutrition info per 100g
    private Double caloriesPer100g;
    private Double proteinsPer100g;
    private Double carbsPer100g;
    private Double fatPer100g;

    private Long id;
    private String name;
    private String directions;
    private String imageUrl;
    private Double calories;
}
