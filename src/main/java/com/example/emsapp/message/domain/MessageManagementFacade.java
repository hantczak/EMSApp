package com.example.emsapp.message.domain;

import org.springframework.messaging.Message;

import javax.jms.JMSException;

public class MessageManagementFacade {
    private final MessageService messageService;

    public MessageManagementFacade(MessageService messageService){
        this.messageService=messageService;
    }

    public void sendMessageToQueue(String text){
        messageService.sendMessageToQueue(text);
    }

    public String receiveMessageWithRedundancyEnabledFromQueue(){
       return messageService.receiveMessageWithRedundancyEnabledFromQueue();
    }

    public String receiveMessageWithRedundancyDisabledFromQueue(){
        return messageService.receiveMessageWithRedundancyDisabledFromQueue();
    }

    public void sendMessageToTopic(String text){
        messageService.sendMessageToTopic(text);
    }

    public String processSpringMessage(Message<Object> message){
       return messageService.processSpringMessage(message);
    }
}
