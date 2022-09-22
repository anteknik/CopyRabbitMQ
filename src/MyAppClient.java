
import javax.naming.*;
import javax.rmi.PortableRemoteObject;

/**
 * The MyAppClient class is the client program for this J2EE
 * application. It obtains a reference to the home interface
 * of the Publisher enterprise bean and creates an instance of
 * the bean. After calling the publisher's publishNews method
 * twice, it removes the bean.
 */
public class MyAppClient {

	public static void main(String[] args) {
		MyAppClient client = new MyAppClient();
		client.doTest();
		System.exit(0);
	}
	public void doTest() {
		try {
			Context ic = new InitialContext();
			System.out.println("Looking up EJB reference");
			java.lang.Object objref =
				ic.lookup("java:comp/env/ejb/MyEjbReference");
			System.err.println("Looked up home");
			PublisherHome pubHome =(PublisherHome) PortableRemoteObject.narrow(
					objref,
					PublisherHome.class);
			System.err.println("Narrowed home");
			/*
			* Create bean instance, invoke business method
			* twice, and remove bean instance.
			*/
			Publisher phr = pubHome.create();
			System.err.println("Got the EJB");
			phr.publishNews();
			phr.publishNews();
			phr.remove();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

