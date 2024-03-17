package edu.java.scrapper.repository;


import edu.java.domain.repository.ChatRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.mockito.Mockito.*;

class ChatRepoTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ChatRepo chatRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAdd() {
        long chatId = 123L;

        chatRepo.add(chatId);
        verify(jdbcTemplate, times(1)).update(anyString(), eq(chatId));
    }

    @Test
    void testRemove() {
        long chatId = 123L;
        chatRepo.remove(chatId);
        verify(jdbcTemplate, times(1)).update(anyString(), eq(chatId));
    }
}
