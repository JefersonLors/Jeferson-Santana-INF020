package Client;

import Server.NTPServerInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Client extends Thread implements NTPClientInterface {
    private final int serverPort = 3240;
    private final String serverName = "NTPServer";
    private String sendRequestTimestamp;
    private String getResponseTimestamp;
    private String serverTimestamp;
    private long RTP;
    private String syncClock;
    private NTPServerInterface ntpServer;

    public Client() throws RemoteException, NotBoundException {
        this.ntpServer = (NTPServerInterface) LocateRegistry.getRegistry(this.serverPort)
                                                            .lookup(this.serverName);
    }

    public static void main(String[] args) throws NotBoundException, RemoteException, InterruptedException {
        new Client().start();
    }

    public void run(){
        while(true){
            try {
                syncClock().showSyncReport();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public NTPClientInterface syncClock() throws RemoteException, InterruptedException {
        this.sendRequestTimestamp = LocalDateTime.now().toString();
        this.serverTimestamp = this.ntpServer.getTimestamp();
        this.getResponseTimestamp = LocalDateTime.now().toString();

        Duration duration = Duration.between(LocalDateTime.parse(this.sendRequestTimestamp),
                                             LocalDateTime.parse(this.getResponseTimestamp));

        this.RTP = duration.toMillis();

        this.syncClock = LocalDateTime.parse(this.serverTimestamp)
                                      .plus(this.RTP/2, ChronoUnit.MILLIS)
                                      .toString();
        return this;
    }

    @Override
    public NTPClientInterface showSyncReport() {
        String report = "";
        report += "=============================================================\n";
        report += "Server Name: " + this.serverName + "\n";
        report += "Server Port: " + this.serverPort + "\n";
        report += "Timestamp on send request: " + this.sendRequestTimestamp + "\n";
        report += "Server Timestamp: " + this.serverTimestamp + "\n";
        report += "Timestamp on get request: " + this.getResponseTimestamp + "\n";
        report += "RTP: " + this.RTP + " milliseconds\n";
        report += "Sync Clock: " + this.syncClock + "\n";
        report += "=============================================================\n";
        System.out.println(report);
        return this;
    }
}
