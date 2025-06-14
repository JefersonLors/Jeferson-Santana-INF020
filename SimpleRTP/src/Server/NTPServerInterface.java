package Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NTPServerInterface extends Remote {
    String getTimestamp() throws RemoteException, InterruptedException;;
}
