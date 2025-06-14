package Client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public interface NTPClientInterface {
    NTPClientInterface syncClock() throws RemoteException, NotBoundException, InterruptedException;
    NTPClientInterface showSyncReport();
}
