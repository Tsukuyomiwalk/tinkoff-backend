package edu.java.domain.repository;

import edu.java.domain.dto.Chat;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatRepo {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Chat> chatMapper;

    public List<Chat> findAll() {
        return jdbcTemplate.query("select chat_id from chat", chatMapper);
    }

    public void add(long chatId) {
        jdbcTemplate.update(
            "INSERT INTO chat (chat_id) VALUES (?) on conflict do nothing",
            chatId
        );
    }

    public void remove(long chatId) {
        jdbcTemplate.update("DELETE FROM chat WHERE chat_id = ?", chatId);
    }

}
