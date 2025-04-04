package com.example.aiprojectexample.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    private String title;
    private int servings;
    private List<String> ingredients;
    private List<String> steps;
    private int prep_time_minutes;
    private int cook_time_minutes;
    private List<String> tags;

    public static Recipe fromJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, Recipe.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON to Recipe", e);
        }
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", servings=" + servings +
                ", ingredients=" + ingredients +
                ", steps=" + steps +
                ", prep_time_minutes=" + prep_time_minutes +
                ", cook_time_minutes=" + cook_time_minutes +
                ", tags=" + tags +
                '}';
    }
}
