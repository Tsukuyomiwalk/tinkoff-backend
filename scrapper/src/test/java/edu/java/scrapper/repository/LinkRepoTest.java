package edu.java.scrapper.repository;

import edu.java.domain.dto.Links;
import edu.java.domain.repository.LinkRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class LinkRepoTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private RowMapper<Links> linksMapper;

    @InjectMocks
    private LinkRepo linkRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        List<Links> expectedLinks = new ArrayList<>();
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(expectedLinks);
        List<Links> actualLinks = linkRepo.findAll();
        assertEquals(expectedLinks, actualLinks);
        verify(jdbcTemplate, times(1)).query(anyString(), any(RowMapper.class));
    }

    @Test
    void testAdd() {
        String link = "https://example.com";
        long id = 123L;
        linkRepo.add(link, id);
        verify(jdbcTemplate, times(1)).update(anyString(), eq(link), eq(id), any(OffsetDateTime.class));
    }

    @Test
    void testRemove() {
        int id = 123;
        linkRepo.remove(id);
        verify(jdbcTemplate, times(1)).update(anyString(), eq(id));
    }

    @Test
    void testCheckLinks() {
        List<Links> expectedLinks = new ArrayList<>();
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(expectedLinks);

        List<Links> actualLinks = linkRepo.checkLinks();

        assertEquals(expectedLinks, actualLinks);
        verify(jdbcTemplate, times(1)).query(anyString(), any(RowMapper.class));
    }

    @Test
    void testGetUpdate() {
        String link = "https://example.com";
        Timestamp expectedTimestamp = Timestamp.valueOf("2022-01-01 12:00:00");
        when(jdbcTemplate.queryForObject(anyString(), eq(Timestamp.class), eq(link))).thenReturn(expectedTimestamp);

        Timestamp actualTimestamp = linkRepo.getUpdate(link);

        assertEquals(expectedTimestamp, actualTimestamp);
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), eq(Timestamp.class), eq(link));
    }

    @Test
    void testGetDate() {
        String link = "https://example.com";
        Timestamp expectedTimestamp = Timestamp.valueOf("2022-01-01 12:00:00");
        when(jdbcTemplate.queryForObject(anyString(), eq(Timestamp.class), eq(link))).thenReturn(expectedTimestamp);
        Timestamp actualTimestamp = linkRepo.getDate(link);

        assertEquals(expectedTimestamp, actualTimestamp);
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), eq(Timestamp.class), eq(link));
    }

    @Test
    void testUpdCheckLinks() {
        String link = "https://example.com";
        linkRepo.updCheckLinks(link);
        verify(jdbcTemplate, times(1)).update(anyString(), eq(link));
    }

    @Test
    void testUpdCheckDate() {
        String link = "https://example.com";
        Timestamp timestamp = Timestamp.valueOf("2022-01-01 12:00:00");
        when(jdbcTemplate.queryForObject(anyString(), eq(Timestamp.class), eq(link))).thenReturn(Timestamp.valueOf("2022-01-01 12:00:00"));

        linkRepo.updCheckDate(link, timestamp);

        verify(jdbcTemplate, never()).update(anyString(), eq(timestamp), eq(link));
    }




    @Test
    void testAddUpdate() {
        String link = "https://example.com";
        Timestamp time = Timestamp.valueOf("2022-01-01 12:00:00");
        when(jdbcTemplate.queryForObject(anyString(), eq(Timestamp.class), eq(link))).thenReturn(Timestamp.valueOf("2022-01-01 12:00:00"));
        linkRepo.addUpdate(link, time);
        verify(jdbcTemplate, never()).update(anyString(), eq(time), eq(link));
    }

}
