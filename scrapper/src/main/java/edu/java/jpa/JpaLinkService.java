package edu.java.jpa;

import edu.java.domain.dto.Chat;
import edu.java.domain.dto.LinkChat;
import edu.java.domain.dto.Links;
import edu.java.jpa.repository.JpaChatRepo;
import edu.java.jpa.repository.JpaLinkRepo;
import edu.java.service.LinkService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class JpaLinkService implements LinkService {

    private final JpaLinkRepo linkRepository;
    private final JpaChatRepo chatRepository;

    @Override
    public void add(long tgChatId, String url) {
        LinkChat link = linkRepository.findFirstByLink(url);
        Chat chat = chatRepository.getById(tgChatId);
        link.setChat(chat);
        linkRepository.saveAndFlush(link);
    }

    @Override
    public void remove(long tgChatId, int urlId) {
        LinkChat linkChat = linkRepository.getById((long) urlId);
        if (linkChat.getChat().getChatId() == tgChatId) {
            linkRepository.deleteByChat(linkChat);
        }
    }

    @Override
    public List<Links> findAllByChatId(long tgChatId) {
        List<Links> linkChats = linkRepository.findByTrackedById(tgChatId);
        return new ArrayList<>(linkChats);
    }

    @Override
    public List<Chat> findAllByLink(String url) {
        LinkChat linkChats = linkRepository.findFirstByLink(url);
        List<Chat> chats = new ArrayList<>();
        chats.add(linkChats.getChat());
        return chats;
    }

    @Override
    public List<Links> allUncheckedLinks() {
        return linkRepository.findAllUncheckedLinks();
    }

}
