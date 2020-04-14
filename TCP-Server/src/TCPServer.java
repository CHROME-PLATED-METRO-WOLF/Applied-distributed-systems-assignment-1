
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
    static FileManager fileManager;
    public static String studentFileName;
    public static String logFileName;
    static Logger logger;
    static long totalClientsServed = 0;

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
        displayStartMessage();
        fileManager = new FileManager(studentList, logList, argOptions.getFileWriteDelay());
        fileManager.start();
        fileManager.loadFiles();
        ExecutorService pool = Executors.newFixedThreadPool(argOptions.getMaxConnections());
        logger.log("Max Threads: " + argOptions.getMaxConnections());

        try {
            //server initialisations
            //the port wont be hardcoded in the final product it will take a command line argument
            //and will have a default value of probs 8888

            ServerSocket listenSocket = new ServerSocket(argOptions.getServerPort());

            logger.log("Server started on port: " + listenSocket.getLocalPort());
            //logger.log("Max connections set to: " + argOptions.getMaxConnections());
            /*
            studentList.add(new Student("TestStudent", 123));
            logList.add(new LogEntry("TestLogEntry", "Test Date", 12345));
            studentList.add(new Student("TestStudent2", 1233));
            logList.add(new LogEntry("TestLogEntry2", "Test Date2", 123453));
             */
            while (true) {

                if (clients.size() < argOptions.getMaxConnections()) {
                    logger.log("Num Clients Connected: " + clients.size());
                    Socket clientSocket = listenSocket.accept();
                    logger.log("Client connected: " + clients.size() + 1 + " Return port: " + clientSocket.getPort());

                    logger.log("Creating thread");
                    Connection c = new Connection(clientSocket, studentList, logList, clients);
                    pool.execute(c);

                    clients.add(c);
                    totalClientsServed++;
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
                shutdownServer();

            }
        });
    }

    static void shutdownServer() {
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
            fileManager.forceSave();
            fileManager.exit();
            logger.log("done shutting down");
            logger.log("Total number of clients served: " + totalClientsServed);
            logger.stopLogger();
        }
    }

    static void displayStartMessage() {
        InputStream input = null;
        try {
            input = new BufferedInputStream(new FileInputStream("startMessage.txt"));
            byte[] buffer = new byte[8192];
            try {
                for (int length = 0; (length = input.read(buffer)) != -1;) {
                    System.out.write(buffer, 0, length);
                }
            } catch (IOException ex) {
                logger.log("welcome message not found startMessage.txt");
            } finally {
                try {
                    input.close();
                } catch (IOException ex) {
                    logger.log("INTERNAL ERROR: Cannot close streams");
                }
            }
        } catch (FileNotFoundException ex) {
            logger.log("INTERNAL ERROR: File not found");
        } finally {
            try {
                input.close();
            } catch (IOException ex) {
                logger.log("INTERNAL ERROR: Cannot close streams");
            } catch (java.lang.NullPointerException ex) {
                logger.log("INTERNAL ERROR: Cannot close stream");
            }
        }
    }

}
