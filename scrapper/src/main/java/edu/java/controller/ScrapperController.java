package edu.java.controller;

import edu.java.controller.dto.requests.AddLinkRequest;
import edu.java.controller.dto.requests.RemoveLinkRequest;
import edu.java.controller.dto.responses.LinkResponse;
import edu.java.controller.dto.responses.LinksResponse;
import edu.java.domain.dto.Links;
import edu.java.service.ChatService;
import edu.java.service.LinkService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ScrapperController {

    private final LinkService linkService;
    private final ChatService chatService;

    @PostMapping("/tg-chat/{id}")
    public void register(@PathVariable Long id) {
        chatService.register(id);
        log.debug("register chat id={}", id);
    }

    @DeleteMapping("/tg-chat/{id}")
    public void unregister(@PathVariable Long id) {
        chatService.unregister(id);
        log.debug("unregister chat id={}", id);
    }

    @GetMapping("/links")
    public ResponseEntity<LinksResponse> getLink(Long chatId) {
        log.debug("get track links in chat id={}", chatId);
        List<LinkResponse> linkResponses = new ArrayList<>();
        List<Links> links = linkService.findAllByChatId(chatId);

        for (Links link : links) {
            try {
                URI linkUri = new URI(link.getLink());
                LinkResponse linkResponse = new LinkResponse(chatId, linkUri);
                linkResponses.add(linkResponse);
            } catch (URISyntaxException e) {
                log.error("Invalid URI syntax for link: {}", link.getLink());
            }
        }
        int totalLinks = linkResponses.size();
        LinksResponse linksResponse = new LinksResponse(linkResponses, totalLinks);

        return ResponseEntity.ok(linksResponse);
    }

    @PostMapping("links")
    public ResponseEntity<LinksResponse> addLink(
        @RequestHeader(value = "Tg-Chat-Id") Long chatId, @RequestBody
    AddLinkRequest request

    ) {
        log.debug("Add link {} for chat id={}", request.url, chatId);
        try {
            String url = request.getUrl().toString();
            linkService.add(chatId, url);
            List<LinkResponse> linkResponses = linkService.findAllByChatId(chatId).stream()
                .map(link -> {
                    try {
                        return new LinkResponse(chatId, new URI(link.getLink()));
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

            int totalLinks = linkResponses.size();
            LinksResponse linksResponse = new LinksResponse(linkResponses, totalLinks);

            return ResponseEntity.ok(linksResponse);
        } catch (Exception e) {
            log.error("Failed to add link for chat id={}: {}", chatId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/links")
    public ResponseEntity<LinkResponse> removeLink(Long chatId, RemoveLinkRequest request) {
        linkService.remove(chatId, Integer.parseInt(String.valueOf(request.getId())));
        log.debug("Remove link {} for chat id={}", request.url, chatId);
        return new ResponseEntity<>(new LinkResponse(chatId, request.url), null, Integer.parseInt("200"));
    }
}
