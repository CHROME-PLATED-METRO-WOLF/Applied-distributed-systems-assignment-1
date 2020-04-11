
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.ThreadLocalRandom;

public class TCPClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        long randomNum = 100;
        long count = 0;
        while (true) {
            try {
                
                Thread.sleep(randomNum);
            } catch (InterruptedException ex) {
                Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection c = new connection();
            c.setName(Long.toString(count));
            c.start();
            count++;
            randomNum = ThreadLocalRandom.current().nextLong(30000, 1800000 + 1);
        }
    }

    static class connection extends Thread {

        public void run() {
            try {
                long randomNum = ThreadLocalRandom.current().nextLong(10000, 1500000 + 1);

                ObjectOutputStream out = null;
                ObjectInputStream in = null;

                Socket clientSocket = new Socket("192.168.1.109", 8888);
                System.out.println("Connected to server: " + clientSocket.getInetAddress() + " On port: " + clientSocket.getLocalPort());
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());
                long i = 0;
                while (i < randomNum) {

                    Thread.sleep(1000);
                    // out.writeObject("test");
                    // String reply = in.readObject().toString();
                    // System.out.println(reply + " " + this.getName());

                    i++;
                }
                System.out.println("Times up exiting");
                clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
