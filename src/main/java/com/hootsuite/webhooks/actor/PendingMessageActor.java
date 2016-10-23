package com.hootsuite.webhooks.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.japi.pf.ReceiveBuilder;
import akka.persistence.AbstractPersistentActor;
import com.hootsuite.webhooks.SpringExtension;
import com.hootsuite.webhooks.event.PendingMessageEvent;
import com.hootsuite.webhooks.event.ScheduleEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import scala.PartialFunction;
import scala.concurrent.duration.Duration;
import scala.runtime.BoxedUnit;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by emival on 22/10/16.
 */
@Component("PendingMessageActor")
@Scope("prototype")
public class PendingMessageActor extends AbstractPersistentActor{

    private List<PendingMessageEvent> pendingMessageEventList = new LinkedList<PendingMessageEvent>();

    @Autowired
    private ActorSystem actorSystem;

    private ActorRef messagingActor;

    @PostConstruct
    private void init(){
        messagingActor = actorSystem.actorOf(
                SpringExtension.SpringExtProvider.get(actorSystem)
                        .props(MessagingActor.class.getSimpleName()));
        actorSystem.scheduler().schedule(Duration.create(5, TimeUnit.MINUTES),
                Duration.create(20, TimeUnit.MINUTES),self(),new ScheduleEvent(),
                actorSystem.dispatcher(), null);
    }


    @Override public PartialFunction<Object, BoxedUnit> receiveRecover() {
        return ReceiveBuilder.
                match(PendingMessageEvent.class, this::handleCommand).build();
    }

    @Override public PartialFunction<Object, BoxedUnit> receiveCommand() {
        return ReceiveBuilder.
                match(PendingMessageEvent.class, this::handleCommand).
                match(ScheduleEvent.class, this::handleSchedule).build();
    }
    private void handleCommand(PendingMessageEvent c) {
        persist(c, e -> {
            pendingMessageEventList.add(e);
        });
    }
    private void handleSchedule(ScheduleEvent c) {
        pendingMessageEventList
                .stream()
                .forEach(pendingMessageEvent -> {
                    if(pendingMessageEvent.getTimeOfArrival().getTime()
                            < (new Date().getTime() - TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS))) {
                        messagingActor.tell(pendingMessageEvent, self());
                    }
                });
    }

    @Override
    public String persistenceId() {
        return "messaging-persistence-id";
    }
}
