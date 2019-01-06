package org.jboss.as.quickstarts.greeter.service;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * JMS Message receiver
 * Run Wildfly standalone-full.xml:
 * >standalone.bat -c standalone-full.xml
 */
@MessageDriven(mappedName = "testQueue", activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:jboss/exported/jms/queue/test"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
})
public class MessageReceiverMDB implements MessageListener {
    public void onMessage(Message message) {
        try {
            String messageBody = message.getBody(String.class);
            System.out.println("Message received: " + messageBody.toString());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
