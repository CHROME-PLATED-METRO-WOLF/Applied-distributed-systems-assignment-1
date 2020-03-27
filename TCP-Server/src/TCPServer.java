
//package tcpserver;
import java.net.*;
import java.io.*;

public class TCPServer {

    final int defaultServerPort = 8888;
    static int serverPort;
    int maxConnections = 100;

    public static void main(String args[]) {

        try {
            //server initialisations
            //the port wont be hardcoded in the final product it will take a command line argument
            //and will have a default value of probs 8888
            if (args[0].compareToIgnoreCase("") == 0 || args[0] == null) {
                serverPort = 8888;
            } else if(isPortValid(args[0])) {
                //port is a valid number
                if(Integer.parseInt(args[0]) < 1024)
                {
                   System.out.println("port is less than 1024 root may be required to run on that port");
                }
            }

            ServerSocket listenSocket = new ServerSocket(serverPort);
            System.out.println("Server started on port: " + serverPort);
            while (true) {
                Socket clientSocket = listenSocket.accept();
                System.out.println("Client connected: " + clientSocket.getPort());
                Connection c = new Connection(clientSocket);
            }

        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        }

    }

    static boolean isPortValid(String port) {
        try {
            int portNum;
            portNum = Integer.parseInt(port);
            if (portNum > 65535 || portNum < 1) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
