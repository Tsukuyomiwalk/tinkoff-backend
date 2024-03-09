package edu.java.controller;

import edu.java.controller.dto.requests.AddLinkRequest;
import edu.java.controller.dto.requests.RemoveLinkRequest;
import edu.java.controller.dto.responses.LinkResponse;
import edu.java.controller.dto.responses.LinksResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ScrapperController {

    void register(@PathVariable Long id) {
        log.debug("register chat id={}", id);
    }

    void unregister(@PathVariable Long id) {
        log.debug("unregister chat id={}", id);
    }

    public ResponseEntity<LinksResponse> getLink(Long chatId) {
        log.debug("get track links in chat id={}", chatId);
        return null;
    }

    ResponseEntity<LinksResponse> addLink(
        @RequestHeader(value = "Tg-Chat-Id") Long chatId, @RequestBody
    AddLinkRequest request

    ) {
        log.debug("Add link {} for chat id={}", request.url, chatId);
        return null;
    }


    public ResponseEntity<LinkResponse> removeLink(Long chatId, RemoveLinkRequest request) {
        log.debug("Remove link {} for chat id={}", request.url, chatId);
        return null;
    }
}
