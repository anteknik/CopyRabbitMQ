import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface PublisherHome extends EJBHome {
	Publisher create() throws RemoteException, CreateException;
}
