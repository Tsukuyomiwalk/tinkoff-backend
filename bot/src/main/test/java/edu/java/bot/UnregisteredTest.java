package java.edu.java.bot;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import edu.java.bot.Commands.CmdList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UnregisteredTest {
    private CmdList cmdList;

    @BeforeEach
    void setUp() {
        cmdList = new CmdList();
    }

    @Test
    void handler_unregisteredUser_returnRegistrationMessage() {
        User mockUser = mock(User.class);
        when(mockUser.id()).thenReturn(123L);
        Message mockMessage = mock(Message.class);
        when(mockMessage.from()).thenReturn(mockUser);
        Update mockUpdate = mock(Update.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        cmdList.setLinks(new HashMap<>());
        String result = cmdList.handler(mockUpdate).getParameters().get("text").toString();
        Assertions.assertEquals("Вы не зарегистрированы, введите команду /start, чтобы воспользоваться функционалом", result);
    }
}
