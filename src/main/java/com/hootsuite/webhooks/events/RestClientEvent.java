package com.hootsuite.webhooks.events;

import com.hootsuite.webhooks.domain.Message;

/**
 * Created by emival on 23/10/16.
 */
public class RestClientEvent {
    private Long id;

    private String url;

    private String body;

    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
