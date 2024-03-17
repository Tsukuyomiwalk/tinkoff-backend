package edu.java.scrapper.service;

import edu.java.configuration.DataBaseConfiguration;
import edu.java.domain.dto.Chat;
import edu.java.domain.dto.Links;
import edu.java.domain.repository.ChatLinkRepo;
import edu.java.domain.repository.ChatRepo;
import edu.java.domain.repository.LinkRepo;
import edu.java.jdbcService.JDBCLinkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {JDBCLinkService.class, ChatRepo.class, LinkRepo.class, ChatLinkRepo.class, DataBaseConfiguration.class})
@Transactional
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class JDBCLinkServiceTest {

    @Autowired
    private JDBCLinkService jdbcLinkService;

    @Autowired
    private ChatRepo chatRepo;

    @Autowired
    private LinkRepo linkRepo;

    @Autowired
    private ChatLinkRepo chatLinkRepo;

    @Test
    public void testAddLink() {
        long tgChatId = 12345L;
        String url = "http://example.com";

        jdbcLinkService.add(tgChatId, url);

        List<Links> links = jdbcLinkService.findAllByChatId(tgChatId);
        assertEquals(1, links.size());
        assertEquals(url, links.getFirst().getLink());
    }

    @Test
    public void testRemoveLink() {
        long tgChatId = 12345L;
        String url = "http://example.com";
        int urlId = 1;

        jdbcLinkService.add(tgChatId, url);

        jdbcLinkService.remove(tgChatId, urlId);

        List<Links> links = jdbcLinkService.findAllByChatId(tgChatId);
        assertTrue(links.isEmpty());
    }

    @Test
    public void testFindAllByChatId() {
        long tgChatId = 12345L;
        String url = "http://example.com";

        jdbcLinkService.add(tgChatId, url);

        List<Links> links = jdbcLinkService.findAllByChatId(tgChatId);
        assertEquals(1, links.size());
        assertEquals(url, links.get(0).getLink());
    }

    @Test
    public void testFindAllByLink() {
        String url = "http://example.com";

        List<Chat> chats = jdbcLinkService.findAllByLink(url);
        assertTrue(chats.isEmpty());
    }

    @Test
    public void testAllUncheckedLinks() {
        List<Links> uncheckedLinks = jdbcLinkService.allUncheckedLinks();
        assertTrue(uncheckedLinks.isEmpty());
    }
}
