package cz.profinit.training.springadvanced.springrest.chat;

import cz.profinit.training.springadvanced.springrest.chat.model.ChatStatusType;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatUpdate;
import org.assertj.core.api.BDDAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Alternative way to run the stub:
 * <pre>{@code
 *     @Rule
 *     public StubRunnerRule stubRunnerRule =
 *             new StubRunnerRule()
 *                     .downloadStub("spring-advanced:spring-rest-chat-client-contracts:+:stubs:8100")
 *                     .withPort(8100)
 *                     .stubsMode(StubRunnerProperties.StubsMode.LOCAL);
 * }</pre>
 */
@RunWith(SpringRunner.class)
@AutoConfigureStubRunner(ids = {"spring-advanced:spring-rest-chat-client-contracts:+:stubs:8100"}, stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class ContractTest {

    @Test
    public void shouldBeAvailable() {
        // given:
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // when:
        ResponseEntity<ChatUpdate> responseEntity = restTemplate
                .exchange("http://localhost:8100/chat/status", HttpMethod.GET, new HttpEntity<>(headers), ChatUpdate.class);

        // then:
        BDDAssertions.then(responseEntity.getStatusCodeValue()).isEqualTo(200);
        BDDAssertions.then(responseEntity.getBody().getStatus()).isEqualTo(ChatStatusType.AVAILABLE);
    }
}