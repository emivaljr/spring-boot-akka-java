package com.hootsuite.webhooks.event;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by emival on 23/10/16.
 */
public class ErrorRestClientEvent extends RestClientEvent {

    public ErrorRestClientEvent(RestClientEvent restClientEvent){
       this.uuid(restClientEvent.getUuid())
       .url(restClientEvent.getUrl())
       .body(restClientEvent.getBody())
       .content(restClientEvent.getContent())
       .destinationId(restClientEvent.getDestinationId())
       .timeOfArrival(restClientEvent.getTimeOfArrival());
    }
}
