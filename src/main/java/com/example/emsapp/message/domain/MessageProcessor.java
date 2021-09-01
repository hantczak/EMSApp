package com.example.emsapp.message.domain;

import javax.jms.JMSException;
import javax.jms.Message;

public interface MessageProcessor {
    public String process(Message message);
}
