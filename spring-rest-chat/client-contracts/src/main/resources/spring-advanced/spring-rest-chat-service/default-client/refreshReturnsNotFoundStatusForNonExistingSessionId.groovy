import cz.profinit.training.springadvanced.springrest.chat.ContractTestConstants
import org.springframework.cloud.contract.spec.Contract
import org.springframework.http.HttpStatus

Contract.make {
    description("Refresh should return not found status for non-existing session id")

    request {
        url("/chat/conversation/${ContractTestConstants.NON_EXISTING_SESSION_ID}")
        method(GET())
        headers {
            accept(applicationJson())
        }
    }

    response {
        status(HttpStatus.NOT_FOUND.value())
        headers {
            contentType(applicationJson())
        }
        body("""
		{
            "status": "ERROR",
            "sessionId": "${ContractTestConstants.NON_EXISTING_SESSION_ID}"
        }
        """)
    }
}