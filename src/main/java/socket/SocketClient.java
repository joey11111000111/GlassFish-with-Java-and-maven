package socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
//source: https://www.tutorialspoint.com/java/java_networking.htm
import java.net.Socket;
import java.time.Instant;

import sockprotoutil.SockProtoUtil;

public class SocketClient {

    public static void main(String [] args) {
        // Extracting server info from command line arguments
        String serverName = "localhost";
        int port = 3333;

        Socket cliSocket = null;
        ObjectOutputStream objectOut = null;
        DataInputStream dataIn = null;
        try {
            // Print info about the connection and try to connect to the server
            System.out.println("Connecting to " + serverName + " on port " + port);
            cliSocket = new Socket(serverName, port);
            System.out.println("Just connected to " + cliSocket.getRemoteSocketAddress());

            // Send protobuff messages to the server for 1 second
            Instant timeLimit = Instant.now().plusSeconds(1);
            objectOut = new ObjectOutputStream(cliSocket.getOutputStream());
            for (int i = 0;true; i++) {
                System.out.println("sending to server: " + i);
                objectOut.writeObject(SockProtoUtil.createProtoMessage(i).toByteArray());
                Instant current = Instant.now();
                if (timeLimit.isBefore(current)) {
                    break;
                }

            }
            objectOut.writeObject(SockProtoUtil.exitSignalObject());

            // Read and print message from server
            dataIn = new DataInputStream(cliSocket.getInputStream());
            System.out.println("Server says " + dataIn.readUTF());
        }catch(IOException e) {
            e.printStackTrace();
        } finally {
            SockProtoUtil.closeIfPossible(dataIn, objectOut, cliSocket);
        }
    }



}