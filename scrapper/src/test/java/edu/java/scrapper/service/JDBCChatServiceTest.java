package edu.java.scrapper.service;

import edu.java.configuration.DataBaseConfiguration;
import edu.java.domain.repository.ChatRepo;
import edu.java.jdbcService.JDBCChatService;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {
    ChatRepo.class,
    JDBCChatService.class,
    IntegrationTest.ManagerConfig.class,
    DataBaseConfiguration.class
})
public class JDBCChatServiceTest {

    @Autowired
    private ChatRepo chatRepo;

    @Autowired
    private JDBCChatService jdbcChatService;

    @Test
    @Transactional
    @Rollback
    void registerTest() {
        long chatId = 123L;
        jdbcChatService.register(chatId);
        assertEquals(1, chatRepo.findAll().size());
    }

    @Test
    @Transactional
    @Rollback
    void unregisterTest() {
        long chatId = 123L;
        jdbcChatService.register(chatId);
        jdbcChatService.unregister(chatId);
        assertEquals(0, chatRepo.findAll().size());
    }
}
