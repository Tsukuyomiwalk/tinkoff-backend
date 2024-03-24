package edu.java.scrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.controller.ScrapperController;
import edu.java.controller.dto.requests.AddLinkRequest;
import edu.java.controller.dto.requests.RemoveLinkRequest;
import edu.java.service.ChatService;
import edu.java.service.LinkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.URI;
import static org.mockito.Mockito.*;

class ScrapperTest {

    @Mock
    private LinkService linkService;

    @Mock
    private ChatService chatService;
    private ScrapperController scrapperController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        scrapperController = new ScrapperController(linkService, chatService);
    }

    @Test
    void registerTest() {
        Long chatId = 123L;
        doNothing().when(chatService).register(chatId);
        scrapperController.register(chatId);
        verify(chatService, times(1)).register(chatId);
    }

    @Test
    void unregisterTest() {
        Long chatId = 123L;
        scrapperController.unregister(chatId);
        verify(chatService, times(1)).unregister(chatId);
    }

    @Test
    void getLinkTest() {
        Long chatId = 123L;
        scrapperController.getLink(chatId);
        verify(linkService, times(1)).findAllByChatId(chatId);
    }

    @Test
    void addLinkTest() throws Exception {
        Long chatId = 123L;
        AddLinkRequest request = new AddLinkRequest(new URI("https://example.com"));

        scrapperController.addLink(chatId, request);

        verify(linkService, times(1)).add(chatId, "https://example.com");
    }

    @Test
    void removeLinkTest() throws Exception {
        Long chatId = 123L;
        RemoveLinkRequest request = new RemoveLinkRequest(12, new URI("https://example.com"));

        scrapperController.removeLink(chatId, request);

        verify(linkService, times(1)).remove(chatId, 12);
    }
}
