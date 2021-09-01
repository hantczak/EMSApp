package com.example.emsapp.message.infrastructure.producers;

import com.example.emsapp.message.domain.MessageCouldNotBeSentException;
import com.tibco.tibjms.TibjmsTopic;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

public class MessageSenderToTopic {
    private final JmsTemplate jmsTemplate;
    private final String topicUrl;

    public MessageSenderToTopic(JmsTemplate jmsTemplate, String topicUrl) {
        this.jmsTemplate = jmsTemplate;
        this.topicUrl = topicUrl;
    }

    public void sendMessage(final String text) {
        TibjmsTopic tibjmsTopic = new TibjmsTopic();
        try {
            tibjmsTopic.setAddress(topicUrl);
        }catch (JMSException e){
            throw new MessageCouldNotBeSentException(e);
        }

        jmsTemplate.setPubSubDomain(true);
            jmsTemplate.send(tibjmsTopic, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    Message returnedMessage = session.createTextMessage(text);
                    returnedMessage.setBooleanProperty("IS_REDUNDANCY_ENABLED", randomizeTrueOrFalse());
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
