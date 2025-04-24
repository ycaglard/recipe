package com.caglar.demo;

import com.caglar.demo.Models.Recipe;
import com.caglar.demo.RecipeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public void run(String... args) throws Exception {
        String csvFile = "src/main/resources/recipeDB/recipes_data_200.csv";
        try (BufferedReader br = Files.newBufferedReader(Paths.get(csvFile))) {
            String headerLine = br.readLine(); // skip header
            String line;
            ObjectMapper mapper = new ObjectMapper();
            while ((line = br.readLine()) != null) {
                // Split CSV respecting quoted commas
                List<String> columns = parseCSV(line);
                if (columns.size() < 7) continue;
                Recipe recipe = new Recipe();
                recipe.setTitle(columns.get(0));
                recipe.setIngredients(columns.get(1)); // JSON array string
                recipe.setDirections(columns.get(2)); // JSON array string
                recipe.setLink(columns.get(3));
                recipe.setSource(columns.get(4));
                recipe.setNer(columns.get(5)); // JSON array string
                recipe.setSite(columns.get(6));
                // Read nutrition info per 100g from CSV columns (assumed to be last 4 columns)
                // Parse as Double, handle empty or missing values as null
                recipe.setCaloriesPer100g(parseDoubleOrNull(columns, 7));
                recipe.setProteinsPer100g(parseDoubleOrNull(columns, 8));
                recipe.setCarbsPer100g(parseDoubleOrNull(columns, 9));
                recipe.setFatPer100g(parseDoubleOrNull(columns, 10));
                recipeRepository.save(recipe);
            }
            System.out.println("Recipes loaded from CSV: " + recipeRepository.count());
        }
    }

    // Helper to safely parse Double from CSV column
    private Double parseDoubleOrNull(List<String> columns, int idx) {
        if (columns.size() > idx) {
            try {
                String val = columns.get(idx).trim();
                if (!val.isEmpty()) return Double.parseDouble(val);
            } catch (Exception ignored) {}
        }
        return null;
    }

    // Helper to split CSV lines with quoted fields
    private List<String> parseCSV(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();
        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        result.add(sb.toString());
        return result;
    }
}
