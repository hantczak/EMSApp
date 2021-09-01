package com.example.emsapp.message.infrastructure.processors;

import com.example.emsapp.message.domain.MessageProcessor;

import javax.jms.JMSException;

public class RedundancyEnabledProcessor implements MessageProcessor {

    @Override
    public String process(javax.jms.Message message) {
        boolean isRedundancyEnabled = false;
        try {
            isRedundancyEnabled = ifRedundancyEnabled(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }

        if (isRedundancyEnabled == false) {
            return "";
        } else {
            return "Redundancy is enabled";
        }
    }

    private boolean ifRedundancyEnabled(javax.jms.Message message) throws JMSException {
        return message.getBooleanProperty("IS_REDUNDANCY_ENABLED");
    }
}
