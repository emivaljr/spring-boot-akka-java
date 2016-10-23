package com.hootsuite.webhooks.util;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.hootsuite.webhooks.SpringExtension;
import com.hootsuite.webhooks.actor.PendingMessageActor;
import com.hootsuite.webhooks.event.ScheduleEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scala.concurrent.duration.Duration;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * Created by emival on 23/10/16.
 */
@Component
public class Scheduler {

    @Autowired
    private ActorSystem actorSystem;

    @PostConstruct
    public void init(){
        ActorRef messagingPendingActor = actorSystem.actorOf(
                SpringExtension.SpringExtProvider.get(actorSystem)
                        .props(PendingMessageActor.class.getSimpleName()));
        actorSystem.scheduler().schedule(
                Duration.create(2, TimeUnit.MINUTES),
                Duration.create(10, TimeUnit.MINUTES),
                messagingPendingActor,new ScheduleEvent(),
                actorSystem.dispatcher(), null);
    }
}
