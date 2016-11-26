package socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import proto.MyInteger;
import proto.MyIntegerProto;
import sockprotoutil.SockProtoUtil;

public class SocketServer implements Runnable {

    private Socket cliSocket;

    public SocketServer(Socket cliSocket) throws IOException {
        this.cliSocket = cliSocket;
    }

    @Override
    public void run() {
            try (
                    ObjectInputStream objectIn = new ObjectInputStream(cliSocket.getInputStream());
                    DataOutputStream dataOut = new DataOutputStream(cliSocket.getOutputStream());
                    )
            {
                try {
                    // Prepare mongoDB
                    MongoClient mongo = new MongoClient("localhost", 27017);
                    DB db = mongo.getDB("GlassfishDB");
                    DBCollection collection = db.getCollection("myIntegers");
                    // Read messages
                    Object rawMessage;
                    while ( !SockProtoUtil.isExitSignal(rawMessage = objectIn.readObject()) ) {
                        MyIntegerProto.MyInteger message = MyIntegerProto.MyInteger.parseFrom((byte[])rawMessage);
                        MyInteger myInt = new MyInteger(message.getIntValue());
                        collection.insert(myInt);
                    }

                    dataOut.writeUTF("Thank you for connecting to " + cliSocket.getLocalSocketAddress() + "\nGoodbye!");
                    System.out.println("Client handling thread is stopping...");
                } catch (ClassNotFoundException cnfe) {
                    System.err.println("Client sent illegal data type!");
                    cnfe.printStackTrace();
                }
            } catch (IOException ioe) {
                System.err.println("Could not create IO streams from socket!");
                ioe.printStackTrace();
        }
    }

}