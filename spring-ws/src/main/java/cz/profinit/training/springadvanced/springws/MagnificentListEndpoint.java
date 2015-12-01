package cz.profinit.training.springadvanced.springws;

import cz.profinit.springadvanced.schemas.GetListIdsRequest;
import cz.profinit.springadvanced.schemas.GetListIdsResponse;
import cz.profinit.training.springadvanced.service.MagnificentListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.math.BigInteger;

@Endpoint
public class MagnificentListEndpoint {

    private static final String SPRING_ADVANCED_NAMESPACE = "http://profinit.cz/springadvanced/schemas";

    private final MagnificentListService magnificentListService;

    @Autowired
    public MagnificentListEndpoint(MagnificentListService magnificentListService) {
        this.magnificentListService = magnificentListService;
    }

    @PayloadRoot(namespace = SPRING_ADVANCED_NAMESPACE, localPart = "GetListIdsRequest")
    public
    @ResponsePayload
    GetListIdsResponse getListIds(@RequestPayload GetListIdsRequest request) {
        GetListIdsResponse getListIdsResponse = new GetListIdsResponse();
        GetListIdsResponse.ListId listId = new GetListIdsResponse.ListId();
        listId.setId(BigInteger.valueOf(10L));
        getListIdsResponse.getListId().add(listId);
        return getListIdsResponse;
    }


}
