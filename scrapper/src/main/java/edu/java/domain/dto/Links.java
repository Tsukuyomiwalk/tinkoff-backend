package edu.java.domain.dto;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Links {
    Integer id;
    String link;
    OffsetDateTime date;
    OffsetDateTime checked;
}
