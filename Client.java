import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by devmachine on 3/11/17.
 */
public class Client {

    Socket s;
    DataInputStream din;
    DataOutputStream dout;

    public static void main(String[] args){
        new Client();
    }

    public Client() {
        try {
            s = new Socket("localhost", 2014);
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

            listenForInput();
        }
        catch (UnknownHostException e){
            e.printStackTrace();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listenForInput(){

        Scanner console = new Scanner(System.in);

        while(true){
            while(!console.hasNextLine()){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            String input = console.nextLine();

            if(input.toLowerCase().equals("quit")){
                break;
            }

            try {
                dout.writeUTF(input);
                dout.flush();

                while(din.available() == 0){
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                String reply = din.readUTF();
                System.out.println(reply);

            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

        try {
            din.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
