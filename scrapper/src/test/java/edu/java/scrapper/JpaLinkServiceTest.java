package edu.java.scrapper;

import edu.java.domain.dto.Chat;
import edu.java.domain.dto.LinkChat;
import edu.java.domain.dto.Links;
import edu.java.jpa.JpaLinkService;
import edu.java.jpa.repository.JpaChatRepo;
import edu.java.jpa.repository.JpaLinkRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class JpaLinkServiceTest {

    @Mock
    private JpaLinkRepo linkRepo;

    @Mock
    private JpaChatRepo chatRepo;

    @InjectMocks
    private JpaLinkService linkService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void add_LinkAdded_Successfully() {
        long tgChatId = 12345L;
        String url = "http://example.com";
        Chat chat = new Chat(tgChatId);
        Links links = new Links(1, url, OffsetDateTime.now(), null);
        LinkChat link = new LinkChat(chat, links);
        when(linkRepo.findFirstByLink(url)).thenReturn(link);
        when(chatRepo.getById(tgChatId)).thenReturn(chat);

        linkService.add(tgChatId, url);

        verify(linkRepo, times(1)).saveAndFlush(link);
    }

    @Test
    public void remove_LinkRemoved_Successfully() {
        long tgChatId = 12345L;
        int urlId = 1;
        Chat chat = new Chat(tgChatId);
        Links links = new Links(1, "http://example.com", OffsetDateTime.now(), null);
        LinkChat linkChat = new LinkChat(chat, links);
        when(linkRepo.getById((long) urlId)).thenReturn(linkChat);
        when(chatRepo.getById(tgChatId)).thenReturn(chat);

        linkService.remove(tgChatId, urlId);

        verify(linkRepo, times(1)).deleteByChat(linkChat);
    }

    @Test
    public void findAllByChatId_AllLinksFound_Successfully() {
        long tgChatId = 12345L;
        List<Links> links = new ArrayList<>();
        links.add(new Links(1, "http://example.com", OffsetDateTime.now(), null));
        links.add(new Links(2, "http://example.org", OffsetDateTime.now(), null));
        when(linkRepo.findByTrackedById(tgChatId)).thenReturn(links);

        List<Links> foundLinks = linkService.findAllByChatId(tgChatId);

        assertEquals(links, foundLinks);
    }

    @Test
    public void findAllByLink_AllChatsFound_Successfully() {
        String url = "http://example.com";
        Chat chat = new Chat(12345L);
        List<Chat> chats = new ArrayList<>();
        chats.add(chat);
        Links links = new Links(1, url, OffsetDateTime.now(), null);
        LinkChat linkChat = new LinkChat(chat, links);
        when(linkRepo.findFirstByLink(url)).thenReturn(linkChat);

        List<Chat> foundChats = linkService.findAllByLink(url);

        assertEquals(chats, foundChats);
    }
}
