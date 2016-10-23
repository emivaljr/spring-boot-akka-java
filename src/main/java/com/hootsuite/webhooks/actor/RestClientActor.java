package com.hootsuite.webhooks.actor;

import akka.actor.UntypedActor;
import com.hootsuite.webhooks.event.ErrorRestClientEvent;
import com.hootsuite.webhooks.event.RestClientEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * Created by soumya on 5/18/2015.
 */
@Component("RestClientActor")
@Scope("prototype")
public class RestClientActor extends UntypedActor{

    private RestTemplate restTemplate;

    @PostConstruct
    private void init(){
        restTemplate = new RestTemplate();
    }

    @Override
    public void onReceive(Object message) throws Exception {
        RestClientEvent restClientEvent = (RestClientEvent) message;
        try{
            ResponseEntity<Object> responseEntity = restTemplate.postForEntity(restClientEvent.getUrl(), restClientEvent.getBody(), null);
            if(!responseEntity.getStatusCode().equals(HttpStatus.OK)){
                sender().tell(new ErrorRestClientEvent(restClientEvent),self());
            }
        }catch (Exception ex){
            sender().tell(new ErrorRestClientEvent(restClientEvent),self());
        }

    }

}
