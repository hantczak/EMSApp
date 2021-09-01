package com.example.emsapp.message.infrastructure.receivers;

import org.springframework.jms.core.JmsTemplate;

import javax.jms.Message;


public class MessageReceiverFromQueue {
    private final JmsTemplate jmsTemplate;
    private final String queUrl;

    public MessageReceiverFromQueue(JmsTemplate jmsTemplate, String queUrl) {
        this.jmsTemplate = jmsTemplate;
        this.queUrl = queUrl;
    }

    public Message receiveMessageWithRedundancyEnabled() {
        return jmsTemplate.receiveSelected(queUrl, "IS_REDUNDANCY_ENABLED=TRUE");
    }

    public Message receiveMessageWithRedundancyDisabled() {
        return jmsTemplate.receiveSelected(queUrl, "IS_REDUNDANCY_ENABLED=FALSE");
    }
}