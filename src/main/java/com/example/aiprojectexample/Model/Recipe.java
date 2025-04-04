package com.example.aiprojectexample.Model;

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
}
