package com.gohlares.messenger.interfaces;

import java.io.Serializable;
import java.util.Date;

public interface MessageInterface extends Serializable{

    public String getBody();
    public Date getDate();
}
