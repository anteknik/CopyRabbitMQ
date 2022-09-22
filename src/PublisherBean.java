import java.rmi.RemoteException;
import java.util.Random;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;

public class PublisherBean implements SessionBean {

	SessionContext sc = null;
	TopicConnection topicConnection = null;
	Topic topic = null;
	final static String messageTypes[] =
		{
			"Nation/World",
			"Metro/Region",
			"Business",
			"Sports",
			"Living/Arts",
			"Opinion" };

	/**
	 * 
	 */
	public PublisherBean() {
		System.out.println("In PublisherBean() (constructor)");
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
	 */
	public void setSessionContext(SessionContext sc)
		throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		this.sc = sc;

	}

	public void ejbCreate() {

		Context context = null;
		TopicConnectionFactory topicConnectionFactory = null;
		System.out.println("In PublisherBean.ejbCreate()");
		try {
			context = new InitialContext();
			topic = (Topic) context.lookup("java:comp/env/jms/TopicName");
			// Create a TopicConnection
			topicConnectionFactory =
				(TopicConnectionFactory) context.lookup(
					"java:comp/env/jms/MyTopicConnectionFactory");
			topicConnection = topicConnectionFactory.createTopicConnection();
		} catch (Throwable t) {
			// JMSException or NamingException could be thrown
			System.err.println(
				"PublisherBean.ejbCreate:" + "Exception: " + t.toString());
		}
	}
	/**
	110 CHAPTER 8 A J2EE APPLICATION THAT USES THE JMS API WITH A SESSION BEAN
	* Chooses a message type by using the random number
	* generator found in java.util. Called by publishNews().
	*
	* @return the String representing the message type
	*/
	private String chooseType() {
		int whichMsg;
		Random rgen = new Random();
		whichMsg = rgen.nextInt(messageTypes.length);
		return messageTypes[whichMsg];
	}
	/**
	* Creates TopicSession, publisher, and message. Publishes
	* messages after setting their NewsType property and using
	* the property value as the message text. Messages are
	* received by MessageBean, a message-driven bean that uses a
	* message selector to retrieve messages whose NewsType
	* property has certain values.
	*/
	public void publishNews() throws EJBException {
		TopicSession topicSession = null;
		TopicPublisher topicPublisher = null;
		TextMessage message = null;
		int numMsgs = messageTypes.length * 3;
		String messageType = null;
		try {
			topicSession = topicConnection.createTopicSession(true, 0);
			topicPublisher = topicSession.createPublisher(topic);
			message = topicSession.createTextMessage();
			for (int i = 0; i < numMsgs; i++) {
				messageType = chooseType();
				message.setStringProperty("NewsType", messageType);
				message.setText("Item " + i + ": " + messageType);
				System
					.out
					.println(
					"PUBLISHER: Setting "
						+ "message text to: "
						+ message.getText());
				topicPublisher.publish(message);
			}
		} catch (Throwable t) {
			// JMSException could be thrown
			System.err.println(
				"PublisherBean.publishNews: " + "Exception: " + t.toString());
			sc.setRollbackOnly();
		} finally {
			if (topicSession != null) {
				try {
					topicSession.close();
				} catch (JMSException e) {
				}
			}
		}
	}
	/**
	* Closes the TopicConnection.
	*/
	public void ejbRemove() throws RemoteException {
		System.out.println("In PublisherBean.ejbRemove()");
		if (topicConnection != null) {
			try {
				topicConnection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbPassivate()
	 */
	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

}
