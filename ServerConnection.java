import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by devmachine on 3/12/17.
 */
public class ServerConnection extends Thread {

    Socket socket;
    Server server;
    DataInputStream din;
    DataOutputStream dout;
    Boolean shouldRun = true;

    public ServerConnection(Socket socket, Server server){
        super("ServerConnectionThread");
        this.socket = socket;
        this.server = server;
    }

    public void sendStringToClient(String text){
        try {
            dout.writeUTF(text);
            dout.flush(); // makes sure that all of your data is out of the buffer space. It HAS to go.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendStringToAllClients(String text){
        for(int index=0; index < server.connections.size(); index++){
            ServerConnection sc = server.connections.get(index);
            sc.sendStringToClient(text);
        }
    }

    public void run() {
        try {
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());

            while(shouldRun){
                while(din.available() == 0){
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                String textIn = din.readUTF();
                sendStringToAllClients(textIn);
            }

            din.close();
            dout.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}





