package socket.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    public static void main(String[] args) {
        int listenPort = 10008;

        // Create server socket
        ServerSocket serSocket = null;
        try {
            serSocket = new ServerSocket(listenPort);
        } catch (IOException e) {
            System.err.println("Cannot create server socket!");
            System.err.println(e.getMessage());
        }

        // Server accepts only 3 clients
//        for (int i = 0; i < 3; i++) {
            try (Socket cliSocket = serSocket.accept();
                    PrintWriter writer = new PrintWriter(cliSocket.getOutputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(cliSocket.getInputStream()));

            ) {
                while (!cliSocket.isClosed()) {
                    String cliInput = reader.readLine();
                    System.out.println("Messsage from client: " + cliInput);
                    writer.println(cliInput);

                }
            } catch (IOException e) {
                System.err.println();
            }
//        }

        // Close server socket
        if (serSocket != null) {
            try {
                serSocket.close();
            } catch (IOException e) {
                System.err.println("Cannot close server socket!");
                System.err.println(e.getMessage());
            }
        }


    }

}