package socket;

//source: https://www.tutorialspoint.com/java/java_networking.htm
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import proto.MyIntegerProto;

public class SocketServer extends Thread {
    private ServerSocket serverSocket;

    public SocketServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(100000);
    }

    @Override
    public void run() {
        while(true) {
            try {
                // Print info about state and wait for connection
                System.out.println("Waiting for client on port " +
                    serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();

                // Print notification message about the established connection
                System.out.println("Just connected to " + server.getRemoteSocketAddress());

                // Read protobuff messages from client
                DataInputStream in = new DataInputStream(server.getInputStream());
                int i = 0;
                while (true) {
                    while (in.available() == 0) {

                    }
                    System.out.println("input available");
                    MyIntegerProto.MyInteger protoMessage = MyIntegerProto.MyInteger.parseFrom(in);
                    int intValue = protoMessage.getIntValue();
                    if (intValue == -1) {
                        System.out.println("breaking from while");
                        break;
                    }
                    System.out.println(intValue);
                }

                // Send goodbye message to client
                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress()
                    + "\nGoodbye!");
                server.close();

            }catch(SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            }catch(IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String [] args) {
        int port = Integer.parseInt(args[0]);
        try {
            Thread t = new SocketServer(port);
            t.start();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}