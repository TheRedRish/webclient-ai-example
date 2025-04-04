package com.example.aiprojectexample;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
class GPTRequest {
    private String model;
    private List<Message> messages;
    private List<Tool> tools;
    private ToolChoice tool_choice;

    // Constructor
    public GPTRequest(String model, List<Message> messages, List<Tool> tools, ToolChoice tool_choice) {
        this.model = model;
        this.messages = messages;
        this.tools = tools;
        this.tool_choice = tool_choice;
    }

    @Getter
    @Setter
    public static class Message {
        private String role;
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }

    @Getter
    @Setter
    public static class Tool {
        private String type;
        private Function function;

        public Tool(String type, Function function) {
            this.type = type;
            this.function = function;
        }
    }

    @Getter
    @Setter
    public static class Function {
        private String name;
        private Parameters parameters;

        public Function(String name, Parameters parameters) {
            this.name = name;
            this.parameters = parameters;
        }
    }

    @Getter
    @Setter
    public static class Parameters {
        private Object type = "object";
        private Properties properties = new Properties();
        private List<String> required = List.of("title", "servings", "ingredients", "steps", "prep_time_minutes", "cook_time_minutes", "tags");

        // Nested classes for properties schema
        // This is the one to edit to change the response from ChatGPT
        @Getter
        @Setter
        public static class Properties {
            public Property title = new Property("string");
            public Property servings = new Property("integer");
            public PropertyItem ingredients = new PropertyItem("array" , new Items("string"));
            public PropertyItem steps = new PropertyItem("array", new Items("string"));
            public Property prep_time_minutes = new Property("integer");
            public Property cook_time_minutes = new Property("integer");
            public PropertyItem tags = new PropertyItem("array" , new Items("string"));
        }

        @Getter
        @Setter
        public static class PropertyItem {
            private String type;
            private Items items;

            public PropertyItem(String type, Items items) {
                this.type = type;
                this.items = items;
            }
        }

        @Getter
        @Setter
        public static class Property {
            private String type;

            public Property(String type) {
                this.type = type;
            }
        }

        @Getter
        @Setter
        public static class Items {
            private String type;

            public Items(String type) {
                this.type = type;
            }
        }
    }

    @Getter
    @Setter
    public static class ToolChoice {
        private String type;
        private Function function;

        public ToolChoice(String type, Function function) {
            this.type = type;
            this.function = function;
        }

        @Getter
        @Setter
        public static class Function {
            private String name;

            public Function(String name) {
                this.name = name;
            }
        }
    }
}

