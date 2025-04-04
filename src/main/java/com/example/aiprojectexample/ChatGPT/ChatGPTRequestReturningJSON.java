package com.example.aiprojectexample.ChatGPT;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatGPTRequestReturningJSON extends ChatGPTRequest {
    private List<Tool> tools;
    private ToolChoice tool_choice;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Tool {
        private String type;
        private Function function;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Function {
        private String name;
        private Parameters parameters;
        private boolean strict = true;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ToolChoice {
        private String type;
        private Function function;

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Function {
            private String name;
        }
    }
}

