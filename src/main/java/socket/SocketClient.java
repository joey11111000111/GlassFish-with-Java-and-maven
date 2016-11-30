package socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
//source: https://www.tutorialspoint.com/java/java_networking.htm
import java.net.Socket;
import java.net.SocketException;
import java.time.Instant;

import sockprotoutil.SockProtoUtil;
import sockprotoutil.SockProtoUtil.CloseOptions;

public class SocketClient {

    private static class ThreadStatus {
        private boolean alive = true;

        public boolean isAlive() {
            return alive;
        }

        public void setAlive(boolean alive) {
            this.alive = alive;
        }
    }

    public static void main(String [] args) {
        final ThreadStatus status = new ThreadStatus();
        Runnable userReader = () -> {
            try {
                System.in.read();
                status.setAlive(false);
            } catch (IOException e) {
                System.err.println("IOException while trying to read from user!");
            }
        };
        Thread statusThread = new Thread(userReader);
        statusThread.setDaemon(true);
        statusThread.start();

//        String serverName = "192.168.43.57";
//        String serverName = "192.168.43.96";
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

            // Send protobuff messages to the server for given seconds
            Instant timeLimit = Instant.now().plusSeconds(3000000);
            objectOut = new ObjectOutputStream(cliSocket.getOutputStream());
            int counter;
            for (counter = 0; status.isAlive(); counter++) {
                objectOut.writeObject(SockProtoUtil.createProtoMessage(counter).toByteArray());
                Instant current = Instant.now();
                if (timeLimit.isBefore(current)) {
                    break;
                }

            }
            System.out.println("Sent a total number of " + (counter+1) + " objects.");
            objectOut.writeObject(SockProtoUtil.exitSignalObject());

            // Read and print message from server
            dataIn = new DataInputStream(cliSocket.getInputStream());
            System.out.println("Server says " + dataIn.readUTF());
        } catch (SocketException se) {
            System.out.println("Server shut down, stop data transfer...");
        }catch(IOException e) {
            System.err.println("IO exception happened");
            e.printStackTrace();
        } finally {
            SockProtoUtil.closeIfPossible(CloseOptions.IGNORE_FAILURE, dataIn, objectOut, cliSocket);
        }
    }

}