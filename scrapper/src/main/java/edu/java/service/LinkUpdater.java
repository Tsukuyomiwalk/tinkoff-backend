package edu.java.service;

import edu.java.domain.dto.Links;
import java.time.OffsetDateTime;
import java.util.List;

public interface LinkUpdater {

    void refreshUpdateDate(String link, OffsetDateTime time);

    void updCheckedDateForUrl(String url);

    List<Links> getUncheckedLinks();
}
