package edu.java.scrapper.service;

import edu.java.domain.dto.Chat;
import edu.java.domain.dto.Links;
import edu.java.domain.repository.ChatLinkRepo;
import edu.java.domain.repository.LinkRepo;
import edu.java.jdbcService.JDBCLinkService;
import edu.java.service.LinkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
@Transactional
public class JDBCLinkServiceTest {

    @Mock
    private LinkRepo linkRepo;

    @Mock
    private ChatLinkRepo chatLinkRepo;

    @InjectMocks
    private JDBCLinkService linkService;

    @BeforeEach
    public void setup() {
        linkService = new JDBCLinkService(linkRepo, chatLinkRepo);
    }


    @Test
    public void testAddLink() {
        long tgChatId = 12345;
        String url = "https://example.com";
        linkService.add(tgChatId, url);
        verify(linkRepo).add(url, tgChatId);
    }

    @Test
    public void testRemoveLink() {
        long tgChatId = 12345;
        int urlId = 1;
        linkService.remove(tgChatId, urlId);
        verify(chatLinkRepo).remove(urlId, tgChatId);
    }

    @Test
    public void testFindAllByChatId() {
        long tgChatId = 12345;
        List<Links> expectedLinks = new ArrayList<>();
        when(chatLinkRepo.findAllByChat(tgChatId)).thenReturn(expectedLinks);
        List<Links> actualLinks = linkService.findAllByChatId(tgChatId);
        assertEquals(expectedLinks, actualLinks);
    }
}
