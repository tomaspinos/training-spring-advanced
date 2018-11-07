package cz.profinit.training.springadvanced.springrest.chat.controller;

import cz.profinit.training.springadvanced.springrest.chat.ChatServiceSecuredApplication;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChatServiceSecuredApplication.class)
@WebAppConfiguration
public class ChatSecuredControllerTest {

    private static final MediaType JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    private static final MediaType XML_UTF8 = new MediaType(MediaType.APPLICATION_XML.getType(),
            MediaType.APPLICATION_XML.getSubtype(), StandardCharsets.UTF_8);

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext applicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }

    @Test
    public void statusIsOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/chat/status")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().contentType(JSON_UTF8)).
                andExpect(MockMvcResultMatchers.jsonPath("status", Matchers.is("AVAILABLE")));
    }

    @Test
    public void statusIsOkXml() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/chat/status").accept(XML_UTF8)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().contentType(XML_UTF8)).
                andExpect(MockMvcResultMatchers.xpath("//status").string("AVAILABLE"));
    }

    @Test
    public void startReturnsSessionIdAndWelcomeMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/chat/conversation")).
                andExpect(MockMvcResultMatchers.status().isCreated()).
                andExpect(MockMvcResultMatchers.content().contentType(JSON_UTF8)).
                andExpect(MockMvcResultMatchers.jsonPath("sessionId", Matchers.not(Matchers.isEmptyOrNullString()))).
                andExpect(MockMvcResultMatchers.jsonPath("status", Matchers.is("RUNNING"))).
                andExpect(MockMvcResultMatchers.jsonPath("messages", Matchers.hasSize(1))).
                andExpect(MockMvcResultMatchers.jsonPath("messages[0]", Matchers.not(Matchers.isEmptyOrNullString())));
    }

    @Test
    public void startReturnsSessionIdAndWelcomeMessageXml() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/chat/conversation").accept(XML_UTF8)).
                andExpect(MockMvcResultMatchers.status().isCreated()).
                andExpect(MockMvcResultMatchers.content().contentType(XML_UTF8)).
                andExpect(MockMvcResultMatchers.xpath("//sessionId").string(Matchers.notNullValue())).
                andExpect(MockMvcResultMatchers.xpath("//status").string("RUNNING")).
                andExpect(MockMvcResultMatchers.xpath("//messages").nodeCount(1)).
                andExpect(MockMvcResultMatchers.xpath("//messages[position() = 1]").string(Matchers.notNullValue()));
    }

    @Test
    public void refreshReturnsNotFoundStatusForNonexistingSessionId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/chat/conversation/XXXERRRORXXX"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
