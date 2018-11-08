import cz.profinit.training.springadvanced.springrest.chat.model.ChatStatusType
import org.springframework.cloud.contract.spec.Contract
import org.springframework.http.HttpStatus

Contract.make {
    description("Should get available status")

    request {
        url("/chat/status")
        method(GET())
        headers {
            accept(applicationJson())
        }
    }

    response {
        status(HttpStatus.OK.value())
        headers {
            contentType(applicationJson())
        }
        body(
                status: ChatStatusType.AVAILABLE
        )
    }
}