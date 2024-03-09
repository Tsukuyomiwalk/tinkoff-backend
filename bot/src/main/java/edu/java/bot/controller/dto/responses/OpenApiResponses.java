package edu.java.bot.controller.dto.responses;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OpenApiResponses {
    public String description;
    public String code;
    public String exceptionName;
    public String exceptionMessage;
    public List<String> stackTrace;
}
