package com.hootsuite.webhooks.actor;

import akka.japi.pf.ReceiveBuilder;
import akka.persistence.AbstractPersistentActor;
import com.hootsuite.webhooks.domain.Message;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

/**
 * Created by emival on 22/10/16.
 */
@Component("MessagingPendingActor")
@Scope("prototype")
public class MessagingPendingActor extends AbstractPersistentActor{

    @Override public PartialFunction<Object, BoxedUnit> receiveRecover() {
        return ReceiveBuilder.
                match(Message.class, this::handleRecover).build();
    }

    @Override public PartialFunction<Object, BoxedUnit> receiveCommand() {
        return ReceiveBuilder.
                match(Message.class, this::handleCommand).build();
    }
    private void handleRecover(Message c) {

    }
    private void handleCommand(Message c) {
        persist(c, e -> {
            sender().tell(e, self());
        });
        System.out.println("Id:"+lastSequenceNr()+" IdDestination:"+c.getDestinationId()+" Msg:"+c.getMsgBody());
    }


    @Override
    public String persistenceId() {
        return "messaging-persistence-id";
    }
}
