package com.hootsuite.webhooks.event;

import com.hootsuite.webhooks.domain.Message;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by emival on 23/10/16.
 */
public class MessagingEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID uuid;

    private Message message;

    private Date timeOfArrival;

    public UUID getUuid() {
        return uuid;
    }

    public MessagingEvent uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public Message getMessage() {
        return message;
    }

    public MessagingEvent message(Message message) {
        this.message = message;
        return this;
    }

    public Date getTimeOfArrival() {
        return timeOfArrival;
    }

    public MessagingEvent timeOfArrival(Date timeOfArrival) {
        this.timeOfArrival = timeOfArrival;
        return this;
    }
}
