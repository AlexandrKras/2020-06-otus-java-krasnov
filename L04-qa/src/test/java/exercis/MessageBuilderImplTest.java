package exercis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import  org.mockito.Mockito;
import ru.otus.testing.exercise.MessageBuilderImpl;
import ru.otus.testing.exercise.MessageTemplateProvider;
import ru.otus.testing.exercise.TemplateNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("Тестирование отправки сообщения")
public class MessageBuilderImplTest {

    public static final String TEMPLATE_NAME = "AnyTemplate";
    public static final String TEMPLATE_TEXT = "Hi!\n %s \n With best regards, %s";
    public static final String MESSAGE_TEXT = "How you doing?";
    public static final String SIGN = "Vasya";

    MessageBuilderImpl messageBuilder;
    MessageTemplateProvider templateProvider;

    @BeforeEach
    void setUp() {
        templateProvider = mock(MessageTemplateProvider.class);
        messageBuilder = new MessageBuilderImpl(templateProvider);
    }

    @DisplayName("Должен создавать верное сообщение для заданого шаблона, текста и подписи")
    @Test
    void buildMessageTest1() {

        when(templateProvider.getMessageTemplate(TEMPLATE_NAME))
                .thenReturn(TEMPLATE_TEXT);
        String expectedMessage = String.format(TEMPLATE_TEXT, MESSAGE_TEXT, SIGN);
        String actualMessage = messageBuilder.buildMessage(TEMPLATE_NAME, MESSAGE_TEXT, SIGN);
        assertEquals(expectedMessage, actualMessage);
    }

    @DisplayName("Должен единожды вызывать нужный метод зависимости при создании сообщения")
    @Test
    void buildMessageTest2() {
        when(templateProvider.getMessageTemplate(anyString())).thenReturn(" ");
        messageBuilder.buildMessage(TEMPLATE_NAME, "", "");
        verify(templateProvider, times(1)).getMessageTemplate(TEMPLATE_NAME);
    }

    @DisplayName("Должен кидать нужное исключение, когда зависимость возвращает null вместо шаблона")
    @Test
    void buildMessageTest3() {
        assertThrows(TemplateNotFoundException.class,
                () -> messageBuilder.buildMessage(TEMPLATE_NAME, "", "")
        );
    }


    
}

