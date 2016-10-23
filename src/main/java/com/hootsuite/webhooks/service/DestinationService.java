package com.hootsuite.webhooks.service;

import com.hootsuite.webhooks.domain.Destination;
import com.hootsuite.webhooks.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by emival on 23/10/16.
 */
@Service
public class DestinationService {

    @Autowired
    private DestinationRepository destinationRepository;

    public Destination saveDestination(Destination destination){
        return destinationRepository.save(destination);
    }

    public void deleteDestination(Long id){
        destinationRepository.delete(id);
    }

    public Iterable<Destination> listDestination(){
        return destinationRepository.findAll();
    }

}
