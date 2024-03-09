package edu.java.clients.bot.Requests;

import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdatesRequests {
    Long id;
    URI url;
    String description;
    @NotNull
    List<Long> tgChatIds;
}
