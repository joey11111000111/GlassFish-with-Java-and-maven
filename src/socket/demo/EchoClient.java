package socket.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoClient {

    public static void main(String[] args) {
        String hostName = "localhost";
        int port = 10008;

        try (
                Socket clientSocket = new Socket(hostName, port);
                PrintWriter socWriter = new PrintWriter(clientSocket.getOutputStream());
                BufferedReader socReader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream())
                );
                BufferedReader userInReader = new BufferedReader(
                        new InputStreamReader(System.in)
                );

                ) {

            String userInput;
            while ((userInput = userInReader.readLine()) != null) {
                socWriter.println(userInput);
                System.out.println("Message from server: " + socReader.readLine());
            }
        } catch (IOException e) {
            System.err.println("Exception was thrown");
            System.err.println(e.getMessage());
        }

    }

}