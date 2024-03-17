package edu.java.configuration;

import edu.java.domain.dto.Chat;
import edu.java.domain.dto.LinkChat;
import edu.java.domain.dto.Links;
import java.time.ZoneOffset;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@Configuration
public class DataBaseConfiguration {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/scrapper";
    private static final String POSTGRES_STR = "postgres";
    private static final String CHAT_ID = "chat_id";
    private static final String LINK = "link";
    private static final String UPD = "upd_at";
    private static final String CHK = "checked_at";

    @Bean
    public DataSource data() {
        return DataSourceBuilder.create()
            .driverClassName("org.postgresql.Driver")
            .url(DB_URL)
            .username(POSTGRES_STR)
            .password(POSTGRES_STR)
            .build();
    }

    @Bean
    public JdbcTemplate template(DataSource source) {
        return new JdbcTemplate(source);
    }

    @Bean
    public RowMapper<Chat> chatRowMapper() {
        return (rs, rowNum) -> new Chat((rs.getInt(CHAT_ID)));
    }

    @Bean
    public RowMapper<LinkChat> linkChatRowMapper() {
        return (rs, rowNum) -> new LinkChat(
            new Chat(rs.getInt(CHAT_ID)),
            new Links(
                rs.getInt("id"),
                rs.getString(LINK),
                rs.getTimestamp(UPD).toInstant().atOffset(ZoneOffset.UTC),
                rs.getTimestamp(CHK).toLocalDateTime().atOffset(ZoneOffset.UTC)
            )
        );
    }

    @Bean
    public RowMapper<Links> linksRowMapper() {
        return (rs, rowNum) -> new Links(
            (rs.getInt("id")),
            (rs.getString(LINK)),
            (rs.getTimestamp(UPD)).toInstant().atOffset(ZoneOffset.UTC),
            rs.getTimestamp(CHK).toLocalDateTime().atOffset(ZoneOffset.UTC)
        );
    }

}
