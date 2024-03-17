package edu.java.domain.repository;

import edu.java.domain.dto.Links;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class LinkRepo {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Links> linksMapper;

    public List<Links> findAll() {
        return jdbcTemplate.query("SELECT * FROM links", linksMapper);
    }

    public void add(String links, long id) {
        jdbcTemplate.update("INSERT INTO links (link, id, created_at) VALUES (?, ?, ?)",
            links, id, OffsetDateTime.now()
        );
    }

    public void remove(int id) {
        jdbcTemplate.update("DELETE FROM links WHERE id = ?", id);
    }

    public List<Links> checkLinks() {
        String request = "SELECT * FROM links WHERE previous_check < timezone('utc', now()) - INTERVAL '2 minutes'";
        return jdbcTemplate.query(request, linksMapper);
    }

    public Timestamp getUpdate(String link) {
        return jdbcTemplate.queryForObject("SELECT upd_at FROM links WHERE link = ?", Timestamp.class, link);
    }

    public Timestamp getDate(String link) {
        return jdbcTemplate.queryForObject("SELECT checked_at FROM link where link = ?", Timestamp.class, link);
    }

    public void updCheckLinks(String link) {
        jdbcTemplate.update("UPDATE link SET checked_at = timezone('utc', now()) where link = ?", link);
    }

    public void updCheckDate(String link, Timestamp timestamp) {
        if (timestamp.after(getDate(link))) {
            jdbcTemplate.update("UPDATE link SET checked_at = ? WHERE link = ?", timestamp, link);
        }
    }

    public void addUpdate(String link, Timestamp time) {
        Timestamp upd = getUpdate(link);
        if (time.after(upd)) {
            jdbcTemplate.update("UPDATE link SET upd_at = ? WHERE link = ?", time, link);
        }
    }

}
