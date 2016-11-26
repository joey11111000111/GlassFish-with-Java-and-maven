package glassfish.test.app.mongodb;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import socket.ClientManager;
import socket.SocketServer;

@WebServlet(value="/socketserver", name="SocketServerServlet")
public class SocketServerServlet extends HttpServlet {

    private static SocketServer socketServer;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClientManager clientManager = new ClientManager();
        clientManager.handleClients();
    }






}//class

