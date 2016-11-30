package socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import sockprotoutil.SockProtoUtil;
import sockprotoutil.SockProtoUtil.CloseOptions;

public class ClientManager {

    private static int connectedClientCount = 0;
    private static Lock lock;

    public static void clientConnected() {
        lock.lock();
        connectedClientCount++;
        System.out.println("Number of connected clients: " + connectedClientCount);
        lock.unlock();
    }


    public static void clientLeft() {
        lock.lock();
        connectedClientCount--;
        System.out.println("Number of connected clients: " + connectedClientCount);
        lock.unlock();
    }

    private final ServerSocket serSocket;
    private final int PORT = 3333;

    public ClientManager() throws IOException {
        serSocket = new ServerSocket(PORT);
        lock = new ReentrantLock();
        System.out.println("Waiting for client on port " + serSocket.getLocalPort() + "...");
    }

    public void handleClients() {
        try {
            while (true) {
                Socket cliSocket;
                try {
                    cliSocket = serSocket.accept();
                    System.out.println("Just connected to " + cliSocket.getRemoteSocketAddress());
                    ClientManager.clientConnected();
                } catch (SocketException se) {
                    System.out.println("Server is shutting down...");
                    return;
                } catch (IOException e) {
                    System.err.println("Could not accept a client! Continue listening...");
                    e.printStackTrace();
                    continue;
                }

                Thread socketServer;
                try {
                    socketServer = new Thread(new SocketServer(cliSocket));
                    socketServer.setDaemon(true);
                } catch (IOException e) {
                    System.err.println("Could not create client handling thread! Go back to listening...");
                    e.printStackTrace();
                    SockProtoUtil.closeIfPossible(CloseOptions.IGNORE_FAILURE, cliSocket);
                    continue;
                }

                socketServer.start();
                System.out.println("Started client handling thread...");
            }//while

        } finally {
            SockProtoUtil.closeIfPossible(CloseOptions.IGNORE_FAILURE, serSocket);
        }
    }

    public void stopListening() {
        SockProtoUtil.closeIfPossible(CloseOptions.NOTIFY_FAILURE, serSocket);
    }


}
