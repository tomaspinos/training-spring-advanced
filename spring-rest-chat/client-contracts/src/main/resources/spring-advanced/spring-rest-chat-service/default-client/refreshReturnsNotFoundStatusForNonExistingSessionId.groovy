import org.springframework.cloud.contract.spec.Contract
import org.springframework.http.HttpStatus

Contract.make {
    description("Refresh should return not found status for non-existing session id")

    request {
        url("/chat/conversation/XXXERRRORXXX")
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
            "sessionId": "XXXERRRORXXX"
        }
        """)
    }
}