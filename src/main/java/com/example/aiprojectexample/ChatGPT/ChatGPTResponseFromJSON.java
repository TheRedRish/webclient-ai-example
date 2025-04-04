package com.example.aiprojectexample.ChatGPT;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatGPTResponseFromJSON {
    public List<Choice> choices;

    @Getter
    @Setter
    public static class Choice {
        public Message message;
    }

    @Getter
    @Setter
    public static class Message {
        public List<ToolCall> tool_calls;
    }

    @Getter
    @Setter
    public static class ToolCall {
        public Function function;
    }

    @Getter
    @Setter
    public static class Function {
        public String name;
        public String arguments;
    }
}
