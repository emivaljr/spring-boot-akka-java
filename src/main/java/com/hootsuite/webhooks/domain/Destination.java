package com.hootsuite.webhooks.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

/**
 * Created by emival on 22/10/16.
 */
@KeySpace("destination")
public class Destination {
    @Id
    private Long id;
    private String url;

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
