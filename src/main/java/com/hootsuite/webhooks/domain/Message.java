package com.hootsuite.webhooks.domain;

import java.io.Serializable;

/**
 * Created by emival on 22/10/16.
 */
public class Message implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long destinationId;

    private String msgBody;

    private String contentType;

    public Long getDestinationId() {
        return destinationId;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public String getContentType() {
        return contentType;
    }
}
