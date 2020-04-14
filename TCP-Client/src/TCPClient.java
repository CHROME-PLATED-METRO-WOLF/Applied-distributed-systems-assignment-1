
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class TCPClient {

    public static void main(String[] args) {
        Logger logger = new Logger(true, true);
        int option = 0;
        String input;
        String ipAddress;
        int port;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Socket clientSocket;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        logger.log("Welcome to TCPClient");
        while (true) {

            logger.log("Please enter server address to connect to");
            try {
                ipAddress = reader.readLine();
                logger.log("Please enter server address to connect to");
                port = Integer.parseInt(reader.readLine());

                try {
                    clientSocket = new Socket(ipAddress, port);
                    break;
                } catch (IOException ex) {
                    logger.log("Error cannot connect");
                }

            } catch (IOException ex) {
                logger.log("ERROR cannot read input");
            } catch (java.lang.NumberFormatException e) {
                logger.log("Please enter a number for the port");
            }

        }

        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException ex) {
            logger.log("Error cannot create streams from socket");
        }

        while (option != 3) {
            logger.log("Please Make Your Selection");
            logger.log("******************");
            logger.log("1. Current Student");
            logger.log("2. New Student");
            logger.log("3. Exit");
            logger.log("******************");
            try {
                input = reader.readLine();
                option = Integer.parseInt(input);
                if (option > 3 || option < 1) {
                    //throw exception to make code neater (less code)
                    throw new java.lang.NumberFormatException();
                } else if (option == 3) {
                    
                    exit();
                }else if (option == 1)
                {
                    Student student;
                    String studentNum;
                    int pinCode;
                    
                    
                    try {
                        logger.log("Enter student number");
                        studentNum = reader.readLine();
                        logger.log("Enter student pincode");
                        pinCode = Integer.parseInt(reader.readLine());
                        student = new Student(studentNum, pinCode);
                        String command = "login";
                       // out.writeObject(command);
                       // out.flush();
                        logger.log("Writing student");
                        out.writeObject(student);
                        out.flush();
                        out.close();
                        logger.log("Student written");
                        //boolean answer = (boolean) in.readObject();
                       // logger.log(Boolean.toString(answer));

                    } catch (java.lang.NumberFormatException e) {
                        logger.log("Please enter a valid number for pincode");
                        
                    } //catch (ClassNotFoundException ex) {
                     //   logger.log("Data returned not boolean");
                   // }
                }
            } catch (IOException ex) {
                logger.log("ERROR cannot read input");
            } catch (java.lang.NumberFormatException e) {
                logger.log("Error: Please enter a number between 1 and 3");
            }

        }

    }

    static void exit() {
        //preform any exit tasks
        Runtime.getRuntime().exit(0);
    }

}

//old preformance test code

/*
public class TCPClient {

 
    
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
 */
