package jms;

import org.junit.Test;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * JMS Message consumer class
 * Create a message with MessageProducer and then run the consumeMessages
 * Method receiveBody() is blocking, so if no messages are in the queue, thread will get stuck.
 */
public class MessageConsumer {

    @Test
    public void consumeMessages() throws NamingException, InterruptedException {
        System.out.println("consumeMessages");

        // Set up the namingContext for the JNDI lookup
        final Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        env.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        env.put(Context.SECURITY_PRINCIPAL, "admin");
        env.put(Context.SECURITY_CREDENTIALS, "admin");
        env.put("jboss.naming.client.ejb.context", true);
        Context jndiContext = new InitialContext(env);

        // Looks up the administered objects
        ConnectionFactory connectionFactory = (ConnectionFactory) jndiContext.lookup("jms/RemoteConnectionFactory");
        Destination queue = (Destination) jndiContext.lookup("jms/queue/test");

        // Loops to receive the messages
        try (JMSContext jmsContext = connectionFactory.createContext()) {
            while (true) {
                System.out.println("Check for new messages");
                String message = jmsContext.createConsumer(queue).receiveBody(String.class);
                System.out.println("Message received: " + message);
            }
        }
    }

}
