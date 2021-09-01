package com.example.emsapp.message.infrastructure.producers;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

public class MessageSenderToQueue {
    private final JmsTemplate jmsTemplate;
    private final String queUrl;

    public MessageSenderToQueue(JmsTemplate jmsTemplate, String queUrl) {
        this.jmsTemplate = jmsTemplate;
        this.queUrl = queUrl;
    }

    public void sendMessage(final String text){
        jmsTemplate.send(queUrl, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message returnedMessage = session.createTextMessage(text);
                returnedMessage.setBooleanProperty("IS_REDUNDANCY_ENABLED",randomizeTrueOrFalse());
                return returnedMessage;
            }
        });
    }

    private boolean randomizeTrueOrFalse(){
        if(Math.random()>=0.5){
            return true;
        }else return false;
    }
}
