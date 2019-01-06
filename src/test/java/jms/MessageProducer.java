package jms;

import org.junit.Test;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Date;
import java.util.Properties;

/**
 * JMS message producer example class
 * <p>
 * Administered objects must exist (JMS connection and a queue). Create them before running the test.
 * Enable activemq in standalone.xml extensions (<extension module="org.wildfly.extension.messaging-activemq"/>).
 * Add messaging submodule and disable its security (<security enabled="false"/>).
 * Run wildfly with standalone-full.xml (standalone.bat -c standalone-full.xml).
 * To add a queue run />weblogic-server/bin/jboss-cli.bat --connect
 * and then />jms-queue add --queue-address=testQueue --entries=queue/test,java:jboss/exported/jms/queue/test
 * JNDI is used to get these objects (Security is needed so provide user and password).
 */
public class MessageProducer {

    @Test
    public void sendMessageToQueue() throws NamingException {
        System.out.println("sendMessageToQueue");

        // Set up the namingContext for the JNDI lookup
        final Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory"); //tested with wildfly 10.1.0
        env.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");//server url
        env.put(Context.SECURITY_PRINCIPAL, "admin"); //server credentials
        env.put(Context.SECURITY_CREDENTIALS, "admin");
        env.put("jboss.naming.client.ejb.context", true);
        Context jndiContext = new InitialContext(env);

        // Looks up the administered objects
        ConnectionFactory connectionFactory = (ConnectionFactory) jndiContext.lookup("jms/RemoteConnectionFactory");
        Destination queue = (Destination) jndiContext.lookup("jms/queue/test");

        try (JMSContext jmsContext = connectionFactory.createContext()) {
            // Creates message
            String message = "Hello! The time when the message is written is " + (new Date() + " !!!");
            // Sends an object message to the queue
            jmsContext.createProducer().setProperty("sender", "jms unit test").send(queue, message);
            System.out.println("Message was sent.");
        }
    }

}
