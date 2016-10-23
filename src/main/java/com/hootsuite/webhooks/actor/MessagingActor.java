package com.hootsuite.webhooks.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.UntypedActor;
import com.hootsuite.webhooks.SpringExtension;
import com.hootsuite.webhooks.domain.Destination;
import com.hootsuite.webhooks.domain.Message;
import com.hootsuite.webhooks.event.ErrorRestClientEvent;
import com.hootsuite.webhooks.event.MessagingEvent;
import com.hootsuite.webhooks.event.PendingMessageEvent;
import com.hootsuite.webhooks.event.RestClientEvent;
import com.hootsuite.webhooks.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by soumya on 5/18/2015.
 */
@Component("MessagingActor")
@Scope("prototype")
public class MessagingActor extends UntypedActor{

    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private DestinationRepository destinationRepository;

    private ActorRef restClientActor;

    private ActorRef messagingPendingActor;

    @PostConstruct
    private void init(){
        restClientActor = actorSystem.actorOf(
                SpringExtension.SpringExtProvider.get(actorSystem)
                        .props(RestClientActor.class.getSimpleName()));
        messagingPendingActor = actorSystem.actorOf(
                SpringExtension.SpringExtProvider.get(actorSystem)
                        .props(PendingMessageActor.class.getSimpleName()));
    }


    @Override
    public void onReceive(Object message) throws Exception {

        if(message instanceof  MessagingEvent){
            handleMessagingEvent((MessagingEvent) message);
        }
        if(message instanceof ErrorRestClientEvent){
            ErrorRestClientEvent messagingEvent = (ErrorRestClientEvent) message;
            messagingPendingActor.tell(new PendingMessageEvent(messagingEvent),self());
            System.out.println(" IdDestination:"+messagingEvent.getUuid()+" Msg:"+messagingEvent.getBody());
        }
        if(message instanceof PendingMessageEvent){
            PendingMessageEvent messagingEvent = (PendingMessageEvent) message;
            restClientActor.tell(messagingEvent,self());
        }
    }

    private void handleMessagingEvent(MessagingEvent message) {
        MessagingEvent messagingEvent = message;
        Message c = messagingEvent.getMessage();
        Destination destination = destinationRepository.findOne(c.getDestinationId());
        RestClientEvent restClientEvent
                = new RestClientEvent()
                .uuid(messagingEvent.getUuid())
                .body(messagingEvent.getMessage().getMsgBody())
                .content(messagingEvent.getMessage().getContentType())
                .url(destination.getUrl())
                .destinationId(messagingEvent.getMessage().getDestinationId());
        restClientActor.tell(restClientEvent,self());
        System.out.println(" IdDestination:"+c.getDestinationId()+" Msg:"+c.getMsgBody());
    }

}
