package edu.java.jpa.repository;

import edu.java.domain.dto.LinkChat;
import edu.java.domain.dto.Links;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface JpaLinkRepo extends JpaRepository<LinkChat, Long> {
    @Query("SELECT l from Links l JOIN l.trackedBy c WHERE c.chatId = :id")
    List<Links> findByTrackedById(Long id);


    @Modifying
    @Query("update LinkChat set checked = ?1 where link = ?2")
    void updateCheckedAt(OffsetDateTime time, String url);

    LinkChat findFirstByLink(String link);

    void deleteByChat(LinkChat linkChat);

    @Query("SELECT l.links FROM LinkChat l WHERE l.links.checked IS NULL")
    List<Links> findAllUncheckedLinks();

}
