package tcp.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPClient {

    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String[] args) {

        int count = 0;
        while(count < 1)
        {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection c = new connection();
            c.setName(Integer.toString(count));
            c.start();
            count ++;
        }
    }

    static class connection extends Thread {

        public void run() {
            try {

                ObjectOutputStream out = null;
                ObjectInputStream in = null;

                Socket clientSocket = new Socket("192.168.1.111", 8888);
                System.out.println("Connected to server: " + clientSocket.getInetAddress() + " On port: " + clientSocket.getLocalPort());
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());

                while (true) {

                    Thread.sleep(1000);
                    out.writeObject("test");
                    System.out.println(in.readObject() + " " + this.getName());
                }

                // clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
