
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Connection extends Thread {
// when a user connects this class is created for that user and handles all processing

    //In and out streams for sending and recieving data
    ObjectInputStream in;
    ObjectOutputStream out;
    //socket variable
    Socket clientSocket;
    //create array list which stores students
    List<Student> studentList = Collections.synchronizedList(new ArrayList());
    //create array list which stores all log entrys
    List<LogEntry> logList = Collections.synchronizedList(new ArrayList());
    List<Thread> clients;

    //default constructor
    public Connection(Socket clientSocket, List<Student> studentList, List<LogEntry> logList, List<Thread> clients) {
        this.studentList = studentList;
        this.logList = logList;
        this.clientSocket = clientSocket;
        this.clients = clients;

        try {
            //the socket is passed in and assigned to a variable so we can access information about the clients session

            //creating the input and output streams
            clientSocket.setKeepAlive(true);
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            //starting THIS thread (will run the "run()" method
            this.start();
        } catch (IOException e) {
            //error
            TCPServer.logger.log("Connection:" + e.getMessage());
        }
    }

    public void run() {

        //message loop, all logic will probably be done in here
        try {
            while (clientSocket.isConnected()) {

                //example of reading in an object
                // messageBox message = (messageBox) in.readObject();
                String test = (String) in.readObject();

                out.writeObject(test);

            }
        } catch (IOException ex) {

        } catch (ClassNotFoundException ex) {
            TCPServer.logger.log("Error data read cannot be converted to an object");
        }
        try {
            clientSocket.close();

        } catch (IOException e) {/*close failed*/
            TCPServer.logger.log("Error closing socket!");

        }

        closeSocket();

    }

    private void closeSocket() {
        try {
            clientSocket.close();
            clients.remove(this);
            TCPServer.logger.log("Socket closed");
            TCPServer.logger.log("num of clients: " + clients.size());
        } catch (IOException ex) {
            TCPServer.logger.log("ERROR: CANT CLOSE SOCKET");
        }
    }

}
