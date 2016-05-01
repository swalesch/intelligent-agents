package verteilte.systeme.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Schnittstelle extends Remote {
    String sayHello() throws RemoteException;
}
