import java.rmi.RemoteException;

import javax.ejb.EJBObject;


public interface Publisher extends EJBObject {
	
	void publishNews() throws RemoteException;

}
