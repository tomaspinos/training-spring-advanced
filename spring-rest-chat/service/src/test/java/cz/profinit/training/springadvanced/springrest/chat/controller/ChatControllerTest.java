package cz.profinit.training.springadvanced.springrest.chat.controller;

import cz.profinit.training.springadvanced.springrest.chat.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ChatControllerTest {

    private static final MediaType JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    private static final MediaType XML_UTF8 = new MediaType(MediaType.APPLICATION_XML.getType(),
            MediaType.APPLICATION_XML.getSubtype(), StandardCharsets.UTF_8);

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext applicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }

    @Test
    public void statusIsOk() throws Exception {
        mockMvc.perform(get("/chat/status")).
                andExpect(status().isOk()).
                andExpect(content().contentType(JSON_UTF8)).
                andExpect(jsonPath("status", is("AVAILABLE")));
    }

    @Test
    public void statusIsOkXml() throws Exception {
        mockMvc.perform(get("/chat/status").accept(XML_UTF8)).
                andExpect(status().isOk()).
                andExpect(content().contentType(XML_UTF8)).
                andExpect(xpath("//status").string("AVAILABLE"));
    }

    @Test

    public void startReturnsSessionIdAndWelcomeMessage() throws Exception {
        mockMvc.perform(post("/chat/conversation")).
                andExpect(status().isCreated()).
                andExpect(content().contentType(JSON_UTF8)).
                andExpect(jsonPath("sessionId", not(isEmptyOrNullString()))).
                andExpect(jsonPath("status", is("RUNNING"))).
                andExpect(jsonPath("messages", hasSize(1))).
                andExpect(jsonPath("messages[0]", not(isEmptyOrNullString())));
    }

    @Test
    public void startReturnsSessionIdAndWelcomeMessageXml() throws Exception {
        mockMvc.perform(post("/chat/conversation").accept(XML_UTF8)).
                andExpect(status().isCreated()).
                andExpect(content().contentType(XML_UTF8)).
                andExpect(xpath("//sessionId").string(notNullValue())).
                andExpect(xpath("//status").string("RUNNING")).
                andExpect(xpath("//messages").nodeCount(1)).
                andExpect(xpath("//messages[position() = 1]").string(notNullValue()));
    }
}
