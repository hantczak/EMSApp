package com.example.emsapp.message.infrastructure.processors;

import com.example.emsapp.message.domain.MessageProcessor;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

public class MessageBodyProcessor implements MessageProcessor {

    @Override
    public String process(Message message) {
 return "Message body: " + getMessageText(message);
    }

    private String getMessageText(Message message) {
        String messageText = null;
        try {
            messageText = ((TextMessage) message).getText();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return messageText;
    }
}




