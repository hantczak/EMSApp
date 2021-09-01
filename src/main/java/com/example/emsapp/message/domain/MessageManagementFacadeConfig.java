package com.example.emsapp.message.domain;

import com.example.emsapp.message.infrastructure.producers.MessageSenderToQueue;
import com.example.emsapp.message.infrastructure.producers.MessageSenderToTopic;
import com.example.emsapp.message.infrastructure.receivers.MessageReceiverFromQueue;
import com.tibco.tibjms.TibjmsConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.*;

@Configuration
public class MessageManagementFacadeConfig {
    @Value(("${destination.ems.user}"))
    private String user;
    @Value(("${destination.ems.password}"))
    private String password;
    @Value(("${destination.ems.server}"))
    private String server;
    @Value(("${destination.ems.port}"))
    private String port;
    @Value(("${destination.ems.queueUrl}"))
    private String queueUrl;
    @Value(("${destination.ems.topicUrl}"))
    private String topicUrl;
    private ConnectionFactory tibjmsConnectionFactory;

    @Bean
    public MessageManagementFacade messageManagementFacade() throws JMSException {
        tibjmsConnectionFactory = configureAndGetConnectionFactory();
        ConnectionFactory connectionFactory = tibjmsConnectionFactory;

        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        MessageSenderToQueue messageSender = new MessageSenderToQueue(jmsTemplate, queueUrl);
        MessageReceiverFromQueue messageReceiver = new MessageReceiverFromQueue(jmsTemplate, queueUrl);
        MessageSenderToTopic messageSenderToTopic = new MessageSenderToTopic(jmsTemplate,topicUrl);
        MessageService messageService = new MessageService(messageSender,messageReceiver,messageSenderToTopic);
        return new MessageManagementFacade(messageService);
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() throws JMSException {

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(tibjmsConnectionFactory);
        factory.setPubSubDomain(true);

        return factory;
    }

    private ConnectionFactory configureAndGetConnectionFactory() throws JMSException {
        TibjmsConnectionFactory tibjmsConnectionFactory = new TibjmsConnectionFactory();
            tibjmsConnectionFactory.setServerUrl(buildServerUrl());
            tibjmsConnectionFactory.setUserName(user);
            tibjmsConnectionFactory.setUserPassword(password);
        return tibjmsConnectionFactory;
    }

    private String buildServerUrl(){
        return "tcp://" + server + ":" + port;
    }
}
