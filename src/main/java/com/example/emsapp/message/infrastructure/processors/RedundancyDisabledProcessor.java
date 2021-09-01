package com.example.emsapp.message.infrastructure.processors;

import com.example.emsapp.message.domain.MessageProcessor;

import javax.jms.JMSException;
import javax.jms.Message;

public class RedundancyDisabledProcessor implements MessageProcessor {
    @Override
    public String process(Message message) {
        boolean isRedundancyEnabled = false;
        try {
            isRedundancyEnabled = ifRedundancyEnabled(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }

        if (isRedundancyEnabled == true) {
            return "";
        } else {
            return "Redundancy is disabled";
        }
    }

    private boolean ifRedundancyEnabled(Message message) throws JMSException {
        return message.getBooleanProperty("IS_REDUNDANCY_ENABLED");
    }
}