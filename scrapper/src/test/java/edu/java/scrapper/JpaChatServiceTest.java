package edu.java.scrapper;

import edu.java.domain.dto.Chat;
import edu.java.jpa.JpaChatService;
import edu.java.jpa.repository.JpaChatRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class JpaChatServiceTest {

    @Mock
    private JpaChatRepo chatRepo;

    @InjectMocks
    private JpaChatService chatService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void register_ChatRegistered_Successfully() {
        long chatId = 12345L;
        Chat expectedChat = new Chat(chatId);
        when(chatRepo.saveAndFlush(any())).thenReturn(expectedChat);

        chatService.register(chatId);

        verify(chatRepo, times(1)).saveAndFlush(any());
    }

    @Test
    public void unregister_ChatUnregistered_Successfully() {
        long chatId = 12345L;

        chatService.unregister(chatId);

        verify(chatRepo, times(1)).deleteChat(new Chat(chatId));
    }
}
