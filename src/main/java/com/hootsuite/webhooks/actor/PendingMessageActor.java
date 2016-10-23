package com.hootsuite.webhooks.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.japi.pf.ReceiveBuilder;
import akka.persistence.AbstractPersistentActor;
import akka.persistence.UntypedPersistentActor;
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
public class PendingMessageActor extends UntypedPersistentActor{

    private List<PendingMessageEvent> pendingMessageEventList = new LinkedList<PendingMessageEvent>();

    @Autowired
    private ActorSystem actorSystem;



    @Override
    public void onReceiveRecover(Object o) throws Throwable {
        if(o instanceof  PendingMessageEvent){
            PendingMessageEvent pendingMessageEvent = (PendingMessageEvent) o;
            pendingMessageEventList.add(pendingMessageEvent);
        }
    }

    @Override
    public void onReceiveCommand(Object o) throws Throwable {
        if(o instanceof  PendingMessageEvent){
            PendingMessageEvent pendingMessageEvent = (PendingMessageEvent) o;
            persist(pendingMessageEvent, e -> {
                pendingMessageEventList.add(e);
            });
        }else if (o instanceof ScheduleEvent){
            //FIXME Not persisting the pendingMessageEventList
            ActorRef messagingActor = actorSystem.actorOf(
                    SpringExtension.SpringExtProvider.get(actorSystem)
                            .props(MessagingActor.class.getSimpleName()));
            pendingMessageEventList
                    .stream()
                    .forEach(pendingMessageEvent -> {
                        if(pendingMessageEvent.getTimeOfArrival().getTime()
                                < (new Date().getTime() - TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS))) {
                            messagingActor.tell(pendingMessageEvent, self());
                        }
                    });
        }

    }

    @Override
    public String persistenceId() {
        return "messaging-persistence-id";
    }
}
