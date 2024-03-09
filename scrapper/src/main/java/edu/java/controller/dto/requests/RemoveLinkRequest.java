package edu.java.controller.dto.requests;

import java.net.URI;
import java.util.List;
import lombok.Data;

@Data
public class RemoveLinkRequest {
    public long id;
    public URI url;
    public String description;
    public List<Long> tgChatIds;
}
