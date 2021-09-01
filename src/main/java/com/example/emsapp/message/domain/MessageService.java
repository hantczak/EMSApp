package com.example.emsapp.message.domain;

import com.example.emsapp.message.infrastructure.processors.MessageBodyProcessor;
import com.example.emsapp.message.infrastructure.processors.RedundancyDisabledProcessor;
import com.example.emsapp.message.infrastructure.processors.RedundancyEnabledProcessor;
import com.example.emsapp.message.infrastructure.producers.MessageSenderToQueue;
import com.example.emsapp.message.infrastructure.producers.MessageSenderToTopic;
import com.example.emsapp.message.infrastructure.receivers.MessageReceiverFromQueue;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.ArrayList;
import java.util.List;

public class MessageService {
    private final MessageSenderToQueue messageSender;
    private final MessageReceiverFromQueue messageReceiver;
    private final MessageSenderToTopic messageSenderToTopic;
    private final List<MessageProcessor> messageProcessorList;

    public MessageService(MessageSenderToQueue messageSender, MessageReceiverFromQueue messageReceiver,
                          MessageSenderToTopic messageSenderToTopic) {
        this.messageProcessorList = new ArrayList<>();
        setupProcessors();

        this.messageSender = messageSender;
        this.messageReceiver = messageReceiver;
        this.messageSenderToTopic = messageSenderToTopic;

    }

    public void sendMessageToQueue(String text) {
        messageSender.sendMessage(text);
    }

    public String receiveMessageWithRedundancyEnabledFromQueue() {
        return processMessage(messageReceiver.receiveMessageWithRedundancyEnabled());
    }

    public String receiveMessageWithRedundancyDisabledFromQueue() {
        return processMessage(messageReceiver.receiveMessageWithRedundancyDisabled());
    }

    public void sendMessageToTopic(String text){
        messageSenderToTopic.sendMessage(text);
    }

    public String processSpringMessage(org.springframework.messaging.Message<Object> message){
       return "Message received: " + message.getHeaders();
    }

    private String processMessage(Message message) {
         final List<String> outputs = new ArrayList<>();
         messageProcessorList.stream()
        .forEach(messageProcessor -> {
            String output = messageProcessor.process(message);
            if(!output.equals("")){
                outputs.add(output);
            }
        });
         return outputs.toString();
    }

    private void setupProcessors(){
        messageProcessorList.add(new RedundancyDisabledProcessor());
        messageProcessorList.add(new RedundancyEnabledProcessor());
        messageProcessorList.add(new MessageBodyProcessor());
    }
}