package edu.java.domain.repository;

import edu.java.domain.dto.Chat;
import edu.java.domain.dto.LinkChat;
import edu.java.domain.dto.Links;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatLinkRepo {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<LinkChat> chatLinkMapper;
    public final RowMapper<Chat> chatMapper;
    public final RowMapper<Links> linksMapper;

    public List<LinkChat> findAll() {
        return jdbcTemplate.query("SELECT * FROM linkChat", chatLinkMapper);
    }

    public List<Links> findAllByChat(long tgChatId) {
        return jdbcTemplate.query(
            "select l.* from linkChat lc join link l on l.id=lc.link_id where lc.chat_id=?",
            linksMapper, tgChatId
        );
    }

    public List<Chat> findAllByLink(String link) {
        return jdbcTemplate.query(
            "SELECT * FROM linkChat JOIN link using (link) where link = ?",
            chatMapper, link
        );
    }

    public void add(LinkChat linkChat) {
        Chat chat = linkChat.getChat();
        Links links = linkChat.getLinks();
        jdbcTemplate.update("INSERT INTO linkChat (link_id, chat_id) VALUES (?, ?)",
            links.getId(), chat.getChatId()
        );
    }

    public void remove(int linkId, long chatId) {
        jdbcTemplate.update("DELETE FROM linkChat WHERE link_id = ? AND chat_id = ?", linkId, chatId);
    }
}
