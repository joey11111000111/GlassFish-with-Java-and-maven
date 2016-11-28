package glassfish.test.app.mongodb;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import socket.ClientManager;

@WebServlet(value="/socketserver", name="SocketServerServlet")
public class SocketServerServlet extends HttpServlet {

    private static ClientManager clientManager = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (clientManager == null) {
            clientManager = new ClientManager();
            clientManager.handleClients();
        } else {
            clientManager.close();
            clientManager = null;
        }
    }






}//class

