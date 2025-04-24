package com.caglar.demo.Models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Lob;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    // For H2, store lists as JSON strings. Use a converter or handle mapping in service/repository.
    @Lob
    private String ingredients; // JSON array string

    @Lob
    private String directions; // JSON array string

    private String link;
    private String source;

    @Lob
    private String ner; // JSON array string

    private String site;

    // Nutrition info per 100g
    private Double caloriesPer100g;
    private Double proteinsPer100g;
    private Double carbsPer100g;
    private Double fatPer100g;
}

