package Server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Server extends UnicastRemoteObject implements NTPServerInterface {
    private final int port = 3240;
    private final String name = "NTPServer";

    protected Server() throws RemoteException {
        super();
        initialize();
    }

    private void initialize() throws RemoteException {
        System.out.println(LocalDateTime.now() + " - Initializing NTP Server");
        LocateRegistry.createRegistry(this.port).rebind(this.name, this);
    }

    public static void main(String[] args) throws RemoteException {
        new Server();
    }

    @Override
    public String getTimestamp() throws RemoteException, InterruptedException {
        Random rand = new Random();
        Thread.sleep(rand.nextInt(5000));
        var now = LocalDateTime.now().toString();
        Thread.sleep(rand.nextInt(5000));
        return now;
    }
}
