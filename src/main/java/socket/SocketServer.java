package socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import sockprotoutil.SockProtoUtil;

public class SocketServer extends Thread {
    private ServerSocket serverSocket;

    public SocketServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(100000);
    }

    @Override
    public void run() {
        while(true) {
            Socket cliSocket = null;
            ObjectInputStream objectIn = null;
            DataOutputStream dataOut = null;
            try {
                // Print info about state and wait for connection
               System.out.println("Waiting for client on port " +
                    serverSocket.getLocalPort() + "...");
                cliSocket = serverSocket.accept();

                // Print notification message about the established connection
                System.out.println("Just connected to " + cliSocket.getRemoteSocketAddress());

                // Read protobuff messages from client
                objectIn = new ObjectInputStream(cliSocket.getInputStream());
                try {
                    // Prepare mongoDB
                    MongoClient mongo = new MongoClient("localhost", 27017);
                    DB db = mongo.getDB("GlassfishDB");
                    DBCollection collection = db.getCollection("myIntegers");

                    Object rawMessage;
//                    while ( !SockProtoUtil.isExitSignal(rawMessage = objectIn.readObject()) ) {
//                        MyIntegerProto.MyInteger message = MyIntegerProto.MyInteger.parseFrom((byte[])rawMessage);
//                        MyInteger myInt = new MyInteger(message.getIntValue());
//                        collection.insert(myInt);
//
//                        System.out.println("message from client: " + message.getIntValue());
//                    }
                    rawMessage = objectIn.readObject();

                    System.out.println("----- end of input from client -----");
                    dataOut = new DataOutputStream(cliSocket.getOutputStream());
                    dataOut.writeUTF("Thank you for connecting to " + cliSocket.getLocalSocketAddress()
                    + "\nGoodbye!");
                } catch (Exception e) {
                    e.printStackTrace(System.out);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                SockProtoUtil.closeIfPossible(dataOut, objectIn, cliSocket, serverSocket);
                System.out.println("Done!");
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