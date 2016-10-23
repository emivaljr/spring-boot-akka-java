package com.hootsuite.webhooks.controller;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.hootsuite.webhooks.SpringExtension;
import com.hootsuite.webhooks.actor.MessagingActor;
import com.hootsuite.webhooks.domain.Message;
import com.hootsuite.webhooks.event.MessagingEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.UUID;

/**
 * Created by emival on 22/10/16.
 */
@RestController
@RequestMapping(value = "/message")
public class MessageController {

    @Autowired
    private ActorSystem actorSystem;

    private ActorRef messagingActor;

    @PostConstruct
    private void init() {
        messagingActor = actorSystem.actorOf(
                SpringExtension.SpringExtProvider.get(actorSystem).props(MessagingActor.class.getSimpleName()));
    }

    @RequestMapping(method = RequestMethod.POST)
    public void newDestination(@RequestBody Message message) {
        MessagingEvent messagingEvent
                = new MessagingEvent()
                .uuid(UUID.randomUUID())
                .message(message)
                .timeOfArrival(new Date());
        messagingActor.tell(messagingEvent, null);
    }
}
