package edu.java.scrapper.service;

import edu.java.domain.dto.Chat;
import edu.java.domain.repository.ChatRepo;
import edu.java.jdbcService.JDBCChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JDBCChatServiceTest {

    @Mock
    private ChatRepo chatRepo;

    @InjectMocks
    private JDBCChatService jdbcChatService;

    @BeforeEach
    public void setUp() {
        chatRepo = Mockito.mock(ChatRepo.class);
        jdbcChatService = new JDBCChatService(chatRepo);
    }

    @Test
    @Transactional
    @Rollback
    void registerTest() {
        long chatId = 123L;
        when(chatRepo.findAll()).thenReturn(List.of(new Chat(chatId)));
        jdbcChatService.register(chatId);
        assertEquals(1, chatRepo.findAll().size());
    }

    @Test
    @Transactional
    @Rollback
    void unregisterTest() {
        long chatId = 124L;
        when(chatRepo.findAll()).thenReturn(List.of(new Chat(chatId)));
        jdbcChatService.register(chatId);
        jdbcChatService.unregister(chatId);
        assertEquals(1, chatRepo.findAll().size());
    }
}
