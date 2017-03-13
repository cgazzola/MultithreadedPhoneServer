import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by devmachine on 3/11/17.
 */
public class Server {

    ServerSocket serverSocket;
    ArrayList<ServerConnection> connections = new ArrayList<>();
    boolean shouldRun = true;

    public static void main(String[] args){
        new Server();
    }

    public Server(){

        try {
            while(shouldRun){
                serverSocket = new ServerSocket(2014);
                Socket socket = serverSocket.accept();
                ServerConnection sc = new ServerConnection(socket, this);
                sc.start();
                connections.add(sc);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
