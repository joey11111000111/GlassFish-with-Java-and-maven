//source: https://www.tutorialspoint.com/java/java_networking.htm
import java.net.*;
import java.io.*;

public class SocketClient {

    public static void main(String [] args) {
        String serverName = args[0];
        int port = Integer.parseInt(args[1]);
        try {
            System.out.println("Connecting to " + serverName + " on port " + port);
            Socket client = new Socket(serverName, port);

            System.out.println("Just connected to " + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();

            DataOutputStream out = new DataOutputStream(outToServer);
            for (int i=0; i<5000; i++) {
                System.out.println("sending:" + i);

                out.writeUTF(Integer.toString(i));


            }
            out.writeUTF("exit");
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            System.out.println("Server says " + in.readUTF());
            client.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}