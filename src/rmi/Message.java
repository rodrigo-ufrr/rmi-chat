package rmi;

import rmi.interfaces.MessageInterface;
import java.util.Date;

public class Message implements MessageInterface{
    private static final long serialVersionUID = 1;
    private final String body;
    private Date date;

    public Message(String body) {
        this.date = new Date();
        this.body = body;
    }

    @Override
    public String getBody() {
        return this.body;
    }

    @Override
    public Date getDate() {
        return this.date;
    }
    
}
