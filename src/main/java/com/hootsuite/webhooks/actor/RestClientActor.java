package com.hootsuite.webhooks.actor;

import akka.actor.UntypedActor;
import com.hootsuite.webhooks.domain.Message;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by soumya on 5/18/2015.
 */
@Component("RestClientActor")
@Scope("prototype")
public class RestClientActor extends UntypedActor{

    @Override
    public void onReceive(Object message) throws Exception {
        Message c = (Message) message;
        System.out.println(" IdDestination:"+c.getDestinationId()+" Msg:"+c.getMsgBody());

    }

}
