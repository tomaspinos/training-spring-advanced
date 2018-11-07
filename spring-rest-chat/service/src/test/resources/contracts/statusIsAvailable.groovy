import org.springframework.cloud.contract.spec.Contract

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
        status(200)
        headers {
            contentType(applicationJson())
        }
        body(
                status: "AVAILABLE"
        )
    }
}