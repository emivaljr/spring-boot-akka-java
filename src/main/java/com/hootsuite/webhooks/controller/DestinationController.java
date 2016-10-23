package com.hootsuite.webhooks.controller;

import com.hootsuite.webhooks.domain.Destination;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

/**
 * Created by emival on 22/10/16.
 */
@RestController
@RequestMapping(value = "/destination")
public class DestinationController {

    @RequestMapping(method = RequestMethod.GET)

    public @ResponseBody List<Destination> list() {
        return null;
    }
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody Destination newDestination(@RequestBody Destination destination) {
        return null;
    }
    @RequestMapping(method = RequestMethod.DELETE)
    public Response<String> deleteDestination(@RequestParam Long destinationId) {
        return null;
    }

}
