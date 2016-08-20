package com.gohlares.messenger;

import com.gohlares.messenger.interfaces.MessageInterface;
import java.util.Date;

public class Message implements MessageInterface{
    private final String body;

    public Message(String body) {
        this.body = body;
    }

    @Override
    public String getBody() {
        return this.body;
    }

    @Override
    public Date getDate() {
        return null;
    }
    
}
