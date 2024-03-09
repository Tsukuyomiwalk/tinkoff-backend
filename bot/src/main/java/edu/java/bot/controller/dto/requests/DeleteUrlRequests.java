package edu.java.bot.controller.dto.requests;

import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteUrlRequests {
    public URI url;
}

