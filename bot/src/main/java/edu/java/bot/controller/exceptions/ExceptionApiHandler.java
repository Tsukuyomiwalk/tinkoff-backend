package edu.java.bot.controller.exceptions;

import edu.java.bot.controller.dto.responses.OpenApiResponses;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionApiHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<OpenApiResponses> handleException(Exception ex) {
        OpenApiResponses response = new OpenApiResponses(
            "Internal Server Error",
            HttpStatus.INTERNAL_SERVER_ERROR.toString(),
            ex.getClass().getSimpleName(),
            ex.getMessage(),
            List.of(Arrays.toString(ex.getStackTrace()))
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
