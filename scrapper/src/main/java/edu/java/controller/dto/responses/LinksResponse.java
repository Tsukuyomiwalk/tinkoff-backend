package edu.java.controller.dto.responses;

import java.util.List;
import lombok.Data;

@Data
public class LinksResponse {
    public List<LinkResponse> links;
    public int size;
}
