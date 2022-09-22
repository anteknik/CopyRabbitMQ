import javax.ejb.*;
import javax.naming.*;
import javax.jms.*;

public class MessageBean implements MessageDrivenBean, MessageListener {
	private transient MessageDrivenContext mdc = null;
	private Context context;
	/**
	* Constructor, which is public and takes no arguments.
	*/
	public MessageBean() {
		System.out.println("In MessageBean.MessageBean()");
	}
	/**
	* setMessageDrivenContext method, declared as public (but
	* not final or static), with a return type of void, and
	* with one argument of type javax.ejb.MessageDrivenContext.
	*
	* @param mdc the context to set
	*/
	public void setMessageDrivenContext(MessageDrivenContext mdc) {
		System.out.println("In " + "MessageBean.setMessageDrivenContext()");
		this.mdc = mdc;
	}
	/**
	* ejbCreate method, declared as public (but not final or
	* static), with a return type of void, and with no
	* arguments.
	WRITING AND COMPILING THE APPLICATION COMPONENTS 87
	*/
	public void ejbCreate() {
		System.out.println("In MessageBean.ejbCreate()");
	}
	/**
	* onMessage method, declared as public (but not final or
	* static), with a return type of void, and with one argument
	* of type javax.jms.Message.
	*
	* Casts the incoming Message to a TextMessage and displays
	* the text.
	*
	* @param inMessage the incoming message
	*/
	public void onMessage(Message inMessage) {
		TextMessage msg = null;
		try {
			if (inMessage instanceof TextMessage) {
				msg = (TextMessage) inMessage;
				System.out.println(
					"MESSAGE BEAN: Message " + "received: " + msg.getText());
			} else {
				System.out.println(
					"Message of wrong type: " + inMessage.getClass().getName());
			}
		} catch (JMSException e) {
			System.err.println(
				"MessageBean.onMessage: " + "JMSException: " + e.toString());
			mdc.setRollbackOnly();
		} catch (Throwable te) {
			System.err.println(
				"MessageBean.onMessage: " + "Exception: " + te.toString());
		}
	}

	/**
	* ejbRemove method, declared as public (but not final or
	* static), with a return type of void, and with no
	* arguments.
	*/
	public void ejbRemove() {
		System.out.println("In MessageBean.remove()");
	}

}
