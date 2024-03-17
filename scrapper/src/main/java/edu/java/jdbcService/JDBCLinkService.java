package edu.java.jdbcService;

import edu.java.domain.dto.Chat;
import edu.java.domain.dto.Links;
import edu.java.domain.repository.ChatLinkRepo;
import edu.java.domain.repository.LinkRepo;
import edu.java.service.LinkService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class JDBCLinkService implements LinkService {

    private final LinkRepo repo;
    private final ChatLinkRepo chatLinkRepo;

    @Override
    public void add(long tgChatId, String url) {
        repo.add(url, tgChatId);
    }

    @Override
    public void remove(long tgChatId, int urlId) {
        chatLinkRepo.remove(urlId, tgChatId);
    }

    @Override
    public List<Links> findAllByChatId(long tgChatId) {
        return chatLinkRepo.findAllByChat(tgChatId);
    }

    @Override
    public List<Chat> findAllByLink(String url) {
        return chatLinkRepo.findAllByLink(url);
    }

    @Override
    public List<Links> allUncheckedLinks() {
        return repo.checkLinks();
    }

}
