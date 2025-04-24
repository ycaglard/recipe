package com.caglar.demo.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IngredientParser {
    // Simple pattern: amount (optional decimal/fraction), unit (optional), ingredient name
    private static final Pattern pattern = Pattern.compile("([0-9./]+)?\\s*([a-zA-Z]+)?\\s*(.*)");

    public static ParsedIngredient parse(String ingredientLine) {
        Matcher matcher = pattern.matcher(ingredientLine);
        if (matcher.matches()) {
            String amountStr = matcher.group(1);
            String unit = matcher.group(2);
            String name = matcher.group(3);
            double amount = 1.0;
            try {
                if (amountStr != null && !amountStr.isEmpty()) {
                    amount = parseAmount(amountStr);
                }
            } catch (Exception ignored) {}
            return new ParsedIngredient(amount, unit, name.trim());
        }
        return new ParsedIngredient(1.0, null, ingredientLine.trim());
    }

    private static double parseAmount(String str) {
        // Handle fractions like 1/2
        if (str.contains("/")) {
            String[] parts = str.split("/");
            return Double.parseDouble(parts[0]) / Double.parseDouble(parts[1]);
        }
        return Double.parseDouble(str);
    }

    public static class ParsedIngredient {
        public double amount;
        public String unit;
        public String name;
        public ParsedIngredient(double amount, String unit, String name) {
            this.amount = amount;
            this.unit = unit;
            this.name = name;
        }
    }
}
