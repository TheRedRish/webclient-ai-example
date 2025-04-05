package com.example.aiprojectexample.schema;

import com.example.aiprojectexample.ChatGPT.Parameters;

import java.util.List;
import java.util.Map;

public class WeatherSchemaAdapter implements Parameters {

    @Override
    public Map<String, Object> getProperties() {
        return Map.of(
                "latitude", Map.of("type", "number"),
                "longitude", Map.of("type", "number")
        );
    }

    @Override
    public List<String> getRequired() {
        return List.of("latitude", "longitude");
    }
}
