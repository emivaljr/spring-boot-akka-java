package com.hootsuite.webhooks.controller;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.hootsuite.webhooks.SpringExtension;
import com.hootsuite.webhooks.actor.MessagingPendingActor;
import com.hootsuite.webhooks.domain.Destination;
import com.hootsuite.webhooks.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

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
                SpringExtension.SpringExtProvider.get(actorSystem).props(MessagingPendingActor.class.getSimpleName()));
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    Destination newDestination(@RequestBody Message message) {
        messagingActor.tell(message, null);
        return null;
    }
}
