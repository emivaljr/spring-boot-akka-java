package com.hootsuite.webhooks.event;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by emival on 23/10/16.
 */
public class RestClientEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID uuid;

    private String url;

    private String body;

    private String content;

    private Long destinationId;

    private Date timeOfArrival;


    public UUID getUuid() {
        return uuid;
    }

    public RestClientEvent uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public RestClientEvent url(String url) {
        this.url = url;
        return this;
    }

    public String getBody() {
        return body;
    }

    public RestClientEvent body(String body) {
        this.body = body;
        return this;
    }

    public String getContent() {
        return content;
    }

    public RestClientEvent content(String content) {
        this.content = content;
        return this;
    }

    public Long getDestinationId() {
        return destinationId;
    }

    public RestClientEvent destinationId(Long destinationId) {
        this.destinationId = destinationId;
        return this;
    }

    public Date getTimeOfArrival() {
        return timeOfArrival;
    }

    public RestClientEvent timeOfArrival(Date timeOfArrival) {
        this.timeOfArrival = timeOfArrival;
        return this;
    }
}
