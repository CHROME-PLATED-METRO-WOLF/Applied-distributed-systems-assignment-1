
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

class Connection extends Thread {
// when a user connects this class is created for that user and handles all processing

    //In and out streams for sending and recieving data
    ObjectInputStream in;
    ObjectOutputStream out;
    //socket variable
    Socket clientSocket;
    //create array list which stores students
    ArrayList<Student> studentList = new ArrayList();
    //create array list which stores all log entrys
    ArrayList<LogEntry> logList = new ArrayList();
    List<Thread> clients;

    //default constructor
    public Connection(Socket clientSocket, ArrayList<Student> studentList, ArrayList<LogEntry> logList, List<Thread> clients) {
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
            System.out.println("Connection:" + e.getMessage());
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
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            clientSocket.close();
            
        } catch (IOException e) {/*close failed*/
            System.out.println("Error closing socket!");

        }
        
       closeSocket(); 

    }
    
 
    private void closeSocket()
    {
        try {
            clientSocket.close();
            clients.remove(this);
            System.out.println("Socket closed");
            System.out.println("num of clients: " + clients.size());
        } catch (IOException ex) {
            System.out.println("ERROR: CANT CLOSE SOCKET");
        }
    }
    
    
}
