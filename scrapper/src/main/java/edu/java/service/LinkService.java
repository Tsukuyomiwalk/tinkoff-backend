package edu.java.service;

import edu.java.domain.dto.Chat;
import edu.java.domain.dto.Links;
import java.util.List;

public interface LinkService {
    void add(long tgChatId, String url);

    void remove(long tgChatId, int urlId);

    List<Links> findAllByChatId(long tgChatId);

    List<Chat> findAllByLink(String url);

    List<Links> allUncheckedLinks();

}
