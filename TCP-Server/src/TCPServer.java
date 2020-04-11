
//package tcpserver;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer {

    //Options options = new Options();
    // final static int defaultServerPort = 8888;
    static List<Student> studentList = Collections.synchronizedList(new ArrayList());
    static List<LogEntry> logList = Collections.synchronizedList(new ArrayList());
    //static ArrayList<Thread> clients = new ArrayList();
    static List<Thread> clients = Collections.synchronizedList(new ArrayList());
    public static String studentFileName;
    public static String logFileName;
    static Logger logger;

    public static void main(String args[]) {

        CliOptions argOptions = new CliOptions(args);
        argOptions.setPredefined();
        argOptions.setProgramName("TCPServer");
        argOptions.parseOptions();
        //creates the shutdown hook after the logger has been created
        createShutdownHook();

        if (argOptions.getLoggingLevel() == 0) {
            logger = new Logger(false, false);
        } else if (argOptions.getLoggingLevel() == 1) {
            logger = new Logger(true, false);
        } else if (argOptions.getLoggingLevel() == 2) {
            if (checkIfNull(argOptions.getLogFile()) == true) {
                logger = new Logger(false, true);
            } else {
                logger = new Logger(false, true, argOptions.getLogFile());
            }

        } else if (argOptions.getLoggingLevel() == 3) {
            if (checkIfNull(argOptions.getLogFile()) == true) {
                logger = new Logger(true, true);
            } else {
                logger = new Logger(true, true, argOptions.getLogFile());
            }

        } else {
            logger = new Logger(false, false);
        }

        ExecutorService pool = Executors.newFixedThreadPool(argOptions.getMaxConnections());
        logger.log("Max Threads: " + argOptions.getMaxConnections());

        try {
            //server initialisations
            //the port wont be hardcoded in the final product it will take a command line argument
            //and will have a default value of probs 8888

            FileManager fileManager = new FileManager(studentList, logList, argOptions.getFileWriteDelay());
            fileManager.start();
            ServerSocket listenSocket = new ServerSocket(argOptions.getServerPort());

            logger.log("Server started on port: " + listenSocket.getLocalPort());
            //logger.log("Max connections set to: " + argOptions.getMaxConnections());

            while (true) {

                if (clients.size() < argOptions.getMaxConnections()) {
                    logger.log("Num Clients Connected: " + clients.size());
                    Socket clientSocket = listenSocket.accept();
                    logger.log("Client connected: " + clientSocket.getPort());

                    logger.log("Creating thread");
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
            logger.log("Listen socket:" + e.getMessage());
        }

    }

    static boolean checkIfNull(Object a) {
        if (a == null) {
            return true;
        }
        return false;
    }

    static void createShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                if (logger == null) {
                    //maybe preform a check to see if the lists have been loaded yet
                    System.out.println("System interupt detected!");
                    System.out.println("preforming shutdown tasks");
                    System.out.println("Logger object not created, most likely early shutdown");
                    System.out.println("No need to save lists");
                } else {
                    //add code to actually save all the data
                    logger.log("System interupt detected!");
                    logger.log("preforming shutdown task");
                    logger.log("saving lists");
                    logger.log("done shutting down");
                }

            }
        });
    }

}
