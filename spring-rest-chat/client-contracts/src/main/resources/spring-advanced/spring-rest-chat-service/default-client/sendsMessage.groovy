import cz.profinit.training.springadvanced.springrest.chat.ContractTestConstants
import org.springframework.cloud.contract.spec.Contract
import org.springframework.http.HttpStatus

Contract.make {
    description("Should send a message")

    request {
        url("/chat/conversation/${ContractTestConstants.SESSION_ID}/message?text=${ContractTestConstants.OUTGOING_MESSAGE}")
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
            "status": "RUNNING",
            "sessionId": "${ContractTestConstants.SESSION_ID}",
            "messages":
            [
                    {"direction": "OUTGOING", "text": "${ContractTestConstants.OUTGOING_MESSAGE}"}
            ]
        }
        """)
    }
}