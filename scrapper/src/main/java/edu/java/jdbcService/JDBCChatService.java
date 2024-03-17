package edu.java.jdbcService;

import edu.java.domain.repository.ChatRepo;
import edu.java.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JDBCChatService implements ChatService {

    private final ChatRepo repo;

    @Override
    public void register(long chatId) {
        repo.add(chatId);
    }

    @Override
    public void unregister(long chatId) {
        repo.remove(chatId);
    }
}
