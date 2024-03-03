package edu.java.bot.controller.dto.responses;

import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LinkResponse {
    public long id;
    public URI url;
}
