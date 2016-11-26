package socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import sockprotoutil.SockProtoUtil;

public class ClientManager {

    private final ServerSocket serSocket;
    private final int PORT = 3333;
    private boolean alive;

    public ClientManager() throws IOException {
        serSocket = new ServerSocket(PORT);
        alive = true;
        System.out.println("Waiting for client on port " + serSocket.getLocalPort() + "...");
    }

    public void handleClients() {
        try {
            while (alive) {
                Socket cliSocket;
                try {
                    cliSocket = serSocket.accept();
                    if (!alive) {
                        SockProtoUtil.closeIfPossible(cliSocket);
                        break;
                    }
                    System.out.println("Just connected to " + cliSocket.getRemoteSocketAddress());
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
                    SockProtoUtil.closeIfPossible(cliSocket);
                    continue;
                }

                socketServer.start();
                System.out.println("Started client handling thread...");
            }//while

        } finally {
            SockProtoUtil.closeIfPossible(serSocket);
        }
    }

    public void stopListening() {
        alive = false;
        SockProtoUtil.closeIfPossible(serSocket);
    }


}
