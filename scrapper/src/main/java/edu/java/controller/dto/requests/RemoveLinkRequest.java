package edu.java.controller.dto.requests;

import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RemoveLinkRequest {
    public long id;
    public URI url;
}
