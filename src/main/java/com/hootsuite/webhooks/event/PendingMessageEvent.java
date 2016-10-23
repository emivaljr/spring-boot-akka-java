package com.hootsuite.webhooks.event;

/**
 * Created by emival on 23/10/16.
 */
public class PendingMessageEvent extends RestClientEvent {

    public PendingMessageEvent(RestClientEvent restClientEvent){
       this.uuid(restClientEvent.getUuid())
       .url(restClientEvent.getUrl())
       .body(restClientEvent.getBody())
       .content(restClientEvent.getContent())
       .destinationId(restClientEvent.getDestinationId())
       .timeOfArrival(restClientEvent.getTimeOfArrival());
    }
}
