package socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//source: https://www.tutorialspoint.com/java/java_networking.htm
import java.net.Socket;

import proto.MyIntegerProto;

public class SocketClient {

    public static void main(String [] args) {
        // Extracting server info from command line arguments
        String serverName = args[0];
        int port = Integer.parseInt(args[1]);

        try {
            // Print info about the connection and try to connect to the server
            System.out.println("Connecting to " + serverName + " on port " + port);
            Socket client = new Socket(serverName, port);

            System.out.println("Just connected to " + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();

            // Send protobuff messages to the server
            DataOutputStream out = new DataOutputStream(outToServer);
            for (int i=0; i<5000; i++) {
                System.out.println("sending:" + i);
                out.write(createProtoMessage(i).toByteArray());
                out.flush();
            }
            // Send exit message
            out.write(createProtoMessage(-1).toByteArray());
            out.flush();
            System.out.println("sent exit signal");

            // Prepare to read text message from server
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            System.out.println("Server says " + in.readUTF());
            client.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static MyIntegerProto.MyInteger createProtoMessage(int intValue) {
        return MyIntegerProto.MyInteger.newBuilder().setIntValue(intValue).build();
    }


}