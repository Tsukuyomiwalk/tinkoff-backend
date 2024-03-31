package edu.java.jpa.repository;

import edu.java.domain.dto.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaChatRepo extends JpaRepository<Chat, Long> {
    void deleteChat(Chat chatId);

}
