package edu.java.scrapper.repository;

import edu.java.domain.dto.Chat;
import edu.java.domain.dto.LinkChat;
import edu.java.domain.dto.Links;
import edu.java.domain.repository.ChatLinkRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import java.util.List;

import static org.mockito.Mockito.*;

class ChatLinkRepoTest {

    @Mock
    private JdbcTemplate jdbcTemplate;
    @Mock
    private RowMapper<LinkChat> chatLinkMapper;
    @Mock
    private RowMapper<Chat> chatMapper;
    @Mock
    private RowMapper<Links> linksMapper;
    @InjectMocks
    private ChatLinkRepo chatLinkRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        List<LinkChat> expectedLinkChats = new ArrayList<>();
        when(jdbcTemplate.query(anyString(), eq(chatLinkMapper))).thenReturn(expectedLinkChats);
        List<LinkChat> actualLinkChats = chatLinkRepo.findAll();
        Assertions.assertEquals(expectedLinkChats, actualLinkChats);
    }

    @Test
    void testFindAllByChat() {
        long tgChatId = 123L;
        List<Links> expectedLinks = new ArrayList<>();
        when(jdbcTemplate.query(anyString(), eq(linksMapper), eq(tgChatId))).thenReturn(expectedLinks);
        List<Links> actualLinks = chatLinkRepo.findAllByChat(tgChatId);
        Assertions.assertEquals(expectedLinks, actualLinks);
    }

    @Test
    void testFindAllByLink() {
        String link = "example.com";
        List<Chat> expectedChats = new ArrayList<>();
        when(jdbcTemplate.query(anyString(), eq(chatMapper), eq(link))).thenReturn(expectedChats);
        List<Chat> actualChats = chatLinkRepo.findAllByLink(link);
        Assertions.assertEquals(expectedChats, actualChats);
    }

    @Test
    void testAdd() {
        Chat chat = new Chat(12);
        Links links = new Links(12, "http.example.com", OffsetDateTime.now(), OffsetDateTime.now());
        LinkChat linkChat = new LinkChat(chat, links);

        chatLinkRepo.add(linkChat);
        verify(jdbcTemplate, times(1)).update(
            eq("INSERT INTO linkChat (link_id, chat_id) VALUES (?, ?)"),
            eq(links.getId()),
            eq(chat.getChatId())
        );
    }

    @Test
    void testRemove() {
        int linkId = 1;
        long chatId = 123L;
        chatLinkRepo.remove(linkId, chatId);
        verify(jdbcTemplate).update(anyString(), eq(linkId), eq(chatId));
    }
}
