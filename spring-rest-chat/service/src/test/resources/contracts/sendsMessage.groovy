import cz.profinit.training.springadvanced.springrest.chat.model.ChatMessageDirectionType
import cz.profinit.training.springadvanced.springrest.chat.model.ChatStatusType
import cz.profinit.training.springadvanced.springrest.chat.model.ConsumerDrivenContractTestConstants
import org.springframework.cloud.contract.spec.Contract
import org.springframework.http.HttpStatus

Contract.make {
    description("Should send a message")

    request {
        url("/chat/conversation/${ConsumerDrivenContractTestConstants.SESSION_ID}/message?text=${ConsumerDrivenContractTestConstants.OUTGOING_MESSAGE}")
        method(POST())
        headers {
            accept(applicationJson())
        }
    }

    response {
        status(HttpStatus.CREATED.value())
        headers {
            contentType(applicationJson())
        }
        body("""
		{
            "status": "${ChatStatusType.RUNNING}",
            "sessionId": "${ConsumerDrivenContractTestConstants.SESSION_ID}",
            "messages":
            [
                    {"direction": "${ChatMessageDirectionType.OUTGOING}", "text": "${ConsumerDrivenContractTestConstants.OUTGOING_MESSAGE}"}
            ]
        }
        """)
    }
}