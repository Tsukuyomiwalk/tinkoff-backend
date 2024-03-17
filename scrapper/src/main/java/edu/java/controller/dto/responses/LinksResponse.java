package edu.java.controller.dto.responses;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LinksResponse {
    public List<LinkResponse> links;
    public int size;
}
