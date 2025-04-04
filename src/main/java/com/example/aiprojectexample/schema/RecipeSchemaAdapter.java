package com.example.aiprojectexample.schema;

import com.example.aiprojectexample.ChatGPT.Parameters;

import java.util.List;
import java.util.Map;

public class RecipeSchemaAdapter implements Parameters {
    @Override
    public Map<String, Object> getProperties() {
        return Map.of(
                "title", Map.of("type", "string"),
                "servings", Map.of("type", "integer"),
                "ingredients", Map.of("type", "array", "items", Map.of("type", "string")),
                "steps", Map.of("type", "array", "items", Map.of("type", "string")),
                "prep_time_minutes", Map.of("type", "integer"),
                "cook_time_minutes", Map.of("type", "integer"),
                "tags", Map.of("type", "array", "items", Map.of("type", "string"))
        );
    }

    @Override
    public List<String> getRequired() {
        return List.of("title", "servings", "ingredients", "steps", "prep_time_minutes", "cook_time_minutes", "tags");
    }
}