
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class Connection extends Thread {
// when a user connects this class is created for that user and handles all processing

    //In and out streams for sending and recieving data
    ObjectInputStream in;
    ObjectOutputStream out;
    //socket variable
    Socket clientSocket;

    //default constructor
    public Connection(Socket aClientSocket) {

        try {
            //the socket is passed in and assigned to a variable so we can access information about the clients session
            clientSocket = aClientSocket;
            //creating the input and output streams
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
        while (clientSocket.isConnected()) {
            //example of reading in an object
            // messageBox message = (messageBox) in.readObject();
        }
        try {
            clientSocket.close();
        } catch (IOException e) {/*close failed*/
            System.out.println("Error closing socket!");
        }

    }
}
