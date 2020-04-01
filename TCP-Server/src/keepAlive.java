
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

//this class will run on a dedicated thread and will periodically go through the list of clients
//and try sending the keep alive message and if that fails it will force close the connection
public class keepAlive extends Thread {

    //array reference to all the clients
    private ArrayList<Thread> clients;
    //this holds a time value for when the check is trigerred (every x ms)
    private int msInterval = 300000;
    //this specifies a delay between accessing each thrread in the list
    //this avoids smashing the server every time this class fires
    private int msDelay = 100;

    private boolean exit = false;

    keepAlive(ArrayList<Thread> clients) {
        this.clients = clients;
    }

    keepAlive(ArrayList<Thread> clients, int msInterval) {
        this.clients = clients;
        this.msInterval = msInterval;
    }

    keepAlive(ArrayList<Thread> clients, int msInterval, int msDelay) {
        this.clients = clients;
        this.msInterval = msInterval;
        this.msDelay = msDelay;
    }

    void setSocketList(ArrayList<Thread> list) {
        this.clients = list;
    }

    ArrayList<Thread> getSocketList() {
        return this.clients;
    }

    void setmsInterval(int interval) {
        this.msInterval = interval;
    }

    int getmsInterval() {
        return this.msInterval;
    }

    void setmsDelay(int delay) {
        this.msDelay = delay;
    }

    int getmsDelay() {
        return this.msDelay;
    }

    public void run() {
        //keep running loop with delay while exit hasnt been called
        while (!exit) {
            try {
                Thread.sleep(this.msInterval);
                System.out.println("Connection Watchdog: Running connection check");
                checkConnections(msDelay);

            } catch (InterruptedException ex) {
                Logger.getLogger(keepAlive.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    void stopWatchdog() {
        exit = true;
    }

    void forceCheck() {
        checkConnections(msDelay);
    }

    private void checkConnections(int msDelay) {
        for (int i = 0; i < clients.size(); i++) {
            //check connections
            Connection client = (Connection) clients.get(i);
            client.sendAlive();
            try {
                Thread.sleep(msDelay);
            } catch (InterruptedException ex) {
                Logger.getLogger(keepAlive.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
