package com.hootsuite.webhooks.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.UntypedActor;
import com.hootsuite.webhooks.SpringExtension;
import com.hootsuite.webhooks.domain.Destination;
import com.hootsuite.webhooks.domain.Message;
import com.hootsuite.webhooks.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by soumya on 5/18/2015.
 */
@Component("RestClientActor")
@Scope("prototype")
public class MessagingActor extends UntypedActor{

    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private DestinationRepository destinationRepository;

    private ActorRef restClientActor;

    @PostConstruct
    private void init(){
        restClientActor = actorSystem.actorOf(
                SpringExtension.SpringExtProvider.get(actorSystem)
                        .props(RestClientActor.class.getSimpleName()));
    }


    @Override
    public void onReceive(Object message) throws Exception {
        Message c = (Message) message;
        Destination destination = destinationRepository.findOne(c.getDestinationId());
        System.out.println(" IdDestination:"+c.getDestinationId()+" Msg:"+c.getMsgBody());

    }

}
