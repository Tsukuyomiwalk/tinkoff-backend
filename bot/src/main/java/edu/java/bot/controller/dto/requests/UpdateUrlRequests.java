package edu.java.bot.controller.dto.requests;

import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateUrlRequests {
    public URI url;
    public String description;
    public long id;
    public List<Long> tgChatIds;

    public UpdateUrlRequests(URI url) {
    }
}
