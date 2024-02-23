package java.edu.java.bot;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.Commands.CmdList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ListTest {
    private CmdList cmdList;

    @BeforeEach
    void setUp() {
        cmdList = new CmdList();
    }

    @Test
    void handler_emptyList_returnEmptyListMessage() {
        Update mockUpdate = mock(Update.class);
        Message mockMessage = mock(Message.class);
        User mockUser = mock(User.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        when(mockMessage.from()).thenReturn(mockUser);
        when(mockUser.id()).thenReturn(123L);
        LinkedList<String> emptyList = new LinkedList<>();
        HashMap<Long, LinkedList<String>> mockLinks = new HashMap<>();
        mockLinks.put(123L, emptyList);
        cmdList.setLinks(mockLinks);
        String result = cmdList.handler(mockUpdate).getParameters().get("text").toString();
        assertEquals("Список ссылок пуст, чтобы добавить ссылки воспользуйтесь /track <пробел> ссылка", result);
    }
}
