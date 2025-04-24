package com.caglar.demo.Utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class NutritionApiClient {
    @Value("${nutrition.api.key}")
    private String apiKey;

    private final String API_URL = "https://api.nal.usda.gov/fdc/v1/foods/search";

    /**
     * Ingredient calorie cache.
     * Key: ingredient name (lowercased, trimmed)
     * Value: calories per unit (as returned by the API)
     *
     * We use ConcurrentHashMap for thread safety, so multiple requests can safely access/update the cache.
     * This cache is in-memory and will persist only while the application is running.
     *
     * Benefits:
     * - Prevents repeated API calls for the same ingredient, dramatically reducing latency.
     * - Reduces load on the external USDA API and helps avoid rate limiting.
     * - Improves user experience for repeated queries.
     */
    private static final Map<String, Double> CALORIE_CACHE = new ConcurrentHashMap<>();

    /**
     * Returns the calories for a given ingredient name, using cache if available.
     *
     * @param ingredient The ingredient name (e.g., "egg")
     * @return Calories per unit (as returned by the API), or null if not found.
     */
    public Double getCaloriesForIngredient(String ingredient) {
        // Normalize the ingredient name for consistent cache keys
        String key = ingredient.trim().toLowerCase();

        // 1. Check if the value is in the cache
        if (CALORIE_CACHE.containsKey(key)) {
            // Cache hit: return the cached value
            return CALORIE_CACHE.get(key);
        }

        // 2. Not in cache: call the API
        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("api_key", apiKey)
                .queryParam("query", ingredient)
                .queryParam("pageSize", 1)
                .build().toUri();
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(uri, Map.class);
            Map body = response.getBody();
            if (body == null || !body.containsKey("foods")) return null;
            Object[] foods = ((java.util.List) body.get("foods")).toArray();
            if (foods.length == 0) return null;
            Map food = (Map) foods[0];
            if (!food.containsKey("foodNutrients")) return null;
            for (Object nutrientObj : (java.util.List) food.get("foodNutrients")) {
                Map nutrient = (Map) nutrientObj;
                if (nutrient.get("nutrientName").toString().equalsIgnoreCase("Energy")) {
                    Double calories = Double.valueOf(nutrient.get("value").toString());
                    // 3. Store the result in the cache for future lookups
                    CALORIE_CACHE.put(key, calories);
                    return calories;
                }
            }
        } catch (Exception e) {
            // Optionally, you could cache failures as null to avoid repeated failed lookups
            // CALORIE_CACHE.put(key, null);
            return null;
        }
        return null;
    }
}
