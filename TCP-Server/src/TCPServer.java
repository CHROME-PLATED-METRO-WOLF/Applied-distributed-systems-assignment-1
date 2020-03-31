
//package tcpserver;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class TCPServer {

    //Options options = new Options();
    final static int defaultServerPort = 8888;
   
   
    static ArrayList<Student> studentList = new ArrayList();
    static ArrayList<LogEntry> logList = new ArrayList();
    public static String studentFileName;
    public static String logFileName;

    public static void main(String args[]) {

        CliOptions argOptions = new CliOptions(args);
        argOptions.setPredefined();
        argOptions.setProgramName("TCPServer");
        argOptions.parseOptions();
        

        try {
            //server initialisations
            //the port wont be hardcoded in the final product it will take a command line argument
            //and will have a default value of probs 8888

           
            ServerSocket listenSocket = new ServerSocket(argOptions.getServerPort(), argOptions.getMaxConnections());
            
            System.out.println("Server started on port: " + listenSocket.getLocalPort());
            System.out.println("Max connections set to: " + argOptions.getMaxConnections());
            while (true) {
                Socket clientSocket = listenSocket.accept();
                System.out.println("Client connected: " + clientSocket.getPort());
                Connection c = new Connection(clientSocket, studentList, logList);
            }

        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        }

    }

    

}
