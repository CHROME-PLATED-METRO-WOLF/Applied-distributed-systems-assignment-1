
//package tcpserver;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer {

    //Options options = new Options();
    // final static int defaultServerPort = 8888;
    static ArrayList<Student> studentList = new ArrayList();
    static ArrayList<LogEntry> logList = new ArrayList();
    static ArrayList<Thread> clients = new ArrayList();
    public static String studentFileName;
    public static String logFileName;

    public static void main(String args[]) {

        CliOptions argOptions = new CliOptions(args);
        argOptions.setPredefined();
        argOptions.setProgramName("TCPServer");
        argOptions.parseOptions();
        Logger logger;
        if (argOptions.getLoggingLevel() == 0) {
            logger = new Logger(false, false);
        } else if (argOptions.getLoggingLevel() == 1) {
            logger = new Logger(true, false);
        } else if (argOptions.getLoggingLevel() == 2) {
            logger = new Logger(false, true, argOptions.getLogFile());
        } else if (argOptions.getLoggingLevel() == 3) {
            logger = new Logger(true, true, argOptions.getLogFile());
        }

        ExecutorService pool = Executors.newFixedThreadPool(argOptions.getMaxConnections());
        System.out.println("Max Threads: " + argOptions.getMaxConnections());

        try {
            //server initialisations
            //the port wont be hardcoded in the final product it will take a command line argument
            //and will have a default value of probs 8888

            FileManager fileManager = new FileManager(studentList, logList, argOptions.getFileWriteDelay());
            fileManager.start();
            ServerSocket listenSocket = new ServerSocket(argOptions.getServerPort());

            System.out.println("Server started on port: " + listenSocket.getLocalPort());
            //System.out.println("Max connections set to: " + argOptions.getMaxConnections());

            while (true) {

                if (clients.size() < argOptions.getMaxConnections()) {
                    System.out.println("Num Clients Connected: " + clients.size());
                    Socket clientSocket = listenSocket.accept();
                    System.out.println("Client connected: " + clientSocket.getPort());

                    System.out.println("Creating thread");
                    Connection c = new Connection(clientSocket, studentList, logList, clients);
                    pool.execute(c);

                    clients.add(c);

                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {

                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        }

    }

}
