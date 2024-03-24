package edu.java.jpa;

import edu.java.domain.dto.Chat;
import edu.java.jpa.repository.JpaChatRepo;
import edu.java.service.ChatService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaChatService implements ChatService {
    private final JpaChatRepo chatRepo;

    @Override
    public void register(long chatId) {
        chatRepo.saveAndFlush(new Chat(chatId));
    }

    @Override
    public void unregister(long chatId) {
        chatRepo.deleteChat(new Chat(chatId));
    }
}
