package cz.profinit.training.springadvanced.springrest.chat.controller.contract;

import cz.profinit.training.springadvanced.springrest.chat.ChatServiceApplication;
import cz.profinit.training.springadvanced.springrest.chat.controller.ChatController;
import cz.profinit.training.springadvanced.springrest.chat.controller.ChatControllerAdvice;
import cz.profinit.training.springadvanced.springrest.chat.lifecycle.ChatLifecycle;
import cz.profinit.training.springadvanced.springrest.chat.lifecycle.ChatSessionNotFoundException;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatMessage;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatMessageDirectionType;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatStatusType;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatUpdate;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.util.Collections;

import static cz.profinit.training.springadvanced.springrest.chat.model.ConsumerDrivenContractTestConstants.NON_EXISTING_SESSION_ID;
import static cz.profinit.training.springadvanced.springrest.chat.model.ConsumerDrivenContractTestConstants.OUTGOING_MESSAGE;
import static cz.profinit.training.springadvanced.springrest.chat.model.ConsumerDrivenContractTestConstants.SESSION_ID;
import static cz.profinit.training.springadvanced.springrest.chat.model.ConsumerDrivenContractTestConstants.WELCOME_MESSAGE;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChatServiceApplication.class)
public abstract class ConsumerDrivenContractControllerTestBaseClass {

    @Autowired
    private ChatController controller;

    @Autowired
    private ChatControllerAdvice controllerAdvice;

    @MockBean
    private ChatLifecycle lifecycle;

    @Before
    public void setUp() {
        StandaloneMockMvcBuilder mockMvcBuilder = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(controllerAdvice);

        // Standalone setup without MockMvcBuilder wouldn't use the controller advice (and we need it).
        RestAssuredMockMvc.standaloneSetup(mockMvcBuilder);

        Mockito.when(lifecycle.status())
                .thenReturn(new ChatUpdate(ChatStatusType.AVAILABLE));

        Mockito.when(lifecycle.start())
                .thenReturn(new ChatUpdate(ChatStatusType.RUNNING, SESSION_ID,
                        Collections.singletonList(new ChatMessage(ChatMessageDirectionType.INCOMING, WELCOME_MESSAGE))));

        Mockito.when(lifecycle.sendMessage(SESSION_ID, OUTGOING_MESSAGE))
                .thenReturn(new ChatUpdate(ChatStatusType.RUNNING, SESSION_ID,
                        Collections.singletonList(new ChatMessage(ChatMessageDirectionType.OUTGOING, OUTGOING_MESSAGE))));

        Mockito.when(lifecycle.refresh(NON_EXISTING_SESSION_ID))
                .thenThrow(new ChatSessionNotFoundException(NON_EXISTING_SESSION_ID));
    }
}
