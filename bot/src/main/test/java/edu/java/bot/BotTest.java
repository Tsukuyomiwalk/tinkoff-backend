package java.edu.java.bot;

import edu.java.bot.BotApplication;
import org.junit.jupiter.api.Test;
import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.configuration.ApplicationConfig;

import org.springframework.context.ConfigurableApplicationContext;

import static org.mockito.Mockito.*;

public class BotTest {
    @Test
    void startBot_commandsSetSuccessfully() {
        TelegramBot mockBot = mock(TelegramBot.class);
        BotApplication botApplication = new BotApplication();
        ConfigurableApplicationContext mockContext = mock(ConfigurableApplicationContext.class);
        ApplicationConfig mockConfig = mock(ApplicationConfig.class);
        when(mockContext.getBean(ApplicationConfig.class)).thenReturn(mockConfig);
        when(mockConfig.telegramToken()).thenReturn("testToken");
        when(mockContext.getBean(BotApplication.class)).thenReturn(botApplication);
        botApplication.startBot(mockBot);
        verify(mockBot).execute(any());
    }
}
