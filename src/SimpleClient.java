import javax.jms.*;
import javax.naming.*;

/**
 * The SimpleClient class sends several messages to a queue.
 */

public class SimpleClient {

	public static void main(String[] args) {
		Context jndiContext = null;
		QueueConnectionFactory queueConnectionFactory = null;
		QueueConnection queueConnection = null;
		QueueSession queueSession = null;
		Queue queue = null;
		QueueSender queueSender = null;
		TextMessage message = null;
		final int NUM_MSGS = 3;
		/*
		* Create a JNDI API InitialContext object.
		*/
		try {
			jndiContext = new InitialContext();
		} catch (NamingException e) {
			System.out.println(
				"Could not create JNDI API " + "context: " + e.toString());
			System.exit(1);
		}
		/*
		* Look up connection factory and queue. If either does
		* not exist, exit.
		*/
		try {
			queueConnectionFactory =
				(QueueConnectionFactory) jndiContext.lookup(
					"java:comp/env/jms/MyQueueConnectionFactory");
			queue = (Queue) jndiContext.lookup("java:comp/env/jms/QueueName");
		} catch (NamingException e) {
			System.out.println("JNDI API lookup failed: " + e.toString());
			System.exit(1);
		}
		/*
		* Create connection.
		* Create session from connection; false means session is
		* not transacted.
		* Create sender and text message.
		* Send messages, varying text slightly.
		* Finally, close connection.
		*/
		try {
			queueConnection = queueConnectionFactory.createQueueConnection();
			queueSession =
				queueConnection.createQueueSession(
					false,
					Session.AUTO_ACKNOWLEDGE);
			queueSender = queueSession.createSender(queue);
			message = queueSession.createTextMessage();
			for (int i = 0; i < NUM_MSGS; i++) {
				message.setText("This is message " + (i + 1));
				System.out.println("Sending message: " + message.getText());
				queueSender.send(message);
			}
		} catch (JMSException e) {
			System.out.println("Exception occurred: " + e.toString());
		} finally {
			if (queueConnection != null) {
				try {
					queueConnection.close();
				} catch (JMSException e) {
				}
			}
			System.exit(0);
		}
	}

}
