import cz.profinit.training.springadvanced.springrest.chat.ContractTestConstants
import org.springframework.cloud.contract.spec.Contract
import org.springframework.http.HttpStatus

Contract.make {
    description("Should start a new conversation")

    request {
        url("/chat/conversation")
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
        // TODO Verifier doesn't verify direction && incoming. Is it right? Shall I report a bug?
        body("""
		{
            "status": "RUNNING",
            "sessionId": "${ContractTestConstants.SESSION_ID}",
            "messages":
            [
                    {"direction": "INCOMING", "text": "${ContractTestConstants.WELCOME_MESSAGE}"}
            ]
        }
        """)
    }
}