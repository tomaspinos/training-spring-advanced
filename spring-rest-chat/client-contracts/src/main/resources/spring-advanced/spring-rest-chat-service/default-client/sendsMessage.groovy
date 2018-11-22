import org.springframework.cloud.contract.spec.Contract
import org.springframework.http.HttpStatus

Contract.make {
    description("Should send a message")

    request {
        url("/chat/conversation/AJDK23RNJ234/message?text=GotAProblem")
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
            "sessionId": "AJDK23RNJ234",
            "messages":
            [
                    {"direction": "OUTGOING", "text": "GotAProblem"}
            ]
        }
        """)
    }
}