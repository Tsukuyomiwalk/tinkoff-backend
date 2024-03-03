package edu.java.clients.bot.Responses;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OpenApiResponses {
    String description;
    String code;
    String exceptionName;
    String exceptionMessage;
    List<String> stacktrace;
}
