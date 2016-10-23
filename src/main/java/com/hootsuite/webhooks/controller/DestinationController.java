package com.hootsuite.webhooks.controller;

import com.hootsuite.webhooks.domain.Destination;
import com.hootsuite.webhooks.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

/**
 * Created by emival on 22/10/16.
 */
@RestController
@RequestMapping(value = "/destination")
public class DestinationController {

    @Autowired
    private DestinationService destinationService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Destination> list() {
        return destinationService.listDestination();
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody Destination newDestination(@RequestBody Destination destination) {
        return destinationService.saveDestination(destination);
    }
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteDestination(@RequestParam Long destinationId) {
        destinationService.deleteDestination(destinationId);
    }

}
