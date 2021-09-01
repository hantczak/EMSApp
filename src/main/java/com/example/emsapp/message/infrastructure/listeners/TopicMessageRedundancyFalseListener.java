package com.example.emsapp.message.infrastructure.listeners;

import com.example.emsapp.message.domain.MessageManagementFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class TopicMessageRedundancyFalseListener {

    @Autowired
    private MessageManagementFacade messageManagementFacade;

    @JmsListener(destination = "dev.sample.message.topic", selector = "IS_REDUNDANCY_ENABLED = FALSE")
    public void onMessage(Message<Object> message) {
        System.out.println(messageManagementFacade.processSpringMessage(message));
    }
}
