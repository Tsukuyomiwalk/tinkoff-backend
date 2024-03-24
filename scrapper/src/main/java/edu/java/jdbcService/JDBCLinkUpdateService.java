package edu.java.jdbcService;

import edu.java.domain.dto.Links;
import edu.java.domain.repository.LinkRepo;
import edu.java.service.LinkUpdater;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JDBCLinkUpdateService implements LinkUpdater {

    private final LinkRepo repo;

    @Override
    public void refreshUpdateDate(String link, OffsetDateTime time) {
        var timestamp = Timestamp.valueOf(time.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
        repo.addUpdate(link, timestamp);
    }

    @Override
    public void updCheckedDateForUrl(String url) {
        repo.updCheckLinks(url);
    }

    @Override
    public List<Links> getUncheckedLinks() {
        return repo.checkLinks();
    }
}
