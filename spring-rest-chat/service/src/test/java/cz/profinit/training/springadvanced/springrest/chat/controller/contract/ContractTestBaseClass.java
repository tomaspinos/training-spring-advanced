package cz.profinit.training.springadvanced.springrest.chat.controller.contract;

import cz.profinit.training.springadvanced.springrest.chat.ChatServiceApplication;
import cz.profinit.training.springadvanced.springrest.chat.controller.ChatController;
import cz.profinit.training.springadvanced.springrest.chat.lifecycle.ChatLifecycle;
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

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChatServiceApplication.class)
public abstract class ContractTestBaseClass {

    private static final String SESSION_ID = "AJDK23RNJ234";

    @Autowired
    private ChatController controller;

    @MockBean
    private ChatLifecycle lifecycle;

    @Before
    public void setUp() {
        RestAssuredMockMvc.standaloneSetup(controller);

        Mockito.when(lifecycle.status())
                .thenReturn(new ChatUpdate(ChatStatusType.AVAILABLE));

        Mockito.when(lifecycle.start())
                .thenReturn(new ChatUpdate(ChatStatusType.RUNNING, SESSION_ID,
                        Collections.singletonList(new ChatMessage(ChatMessageDirectionType.INCOMING, "Hello world!"))));
    }
}
