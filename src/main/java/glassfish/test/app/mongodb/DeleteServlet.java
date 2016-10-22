package glassfish.test.app.mongodb;

import com.mongodb.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value="/delete", name="DeleteServlet")
public class DeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MongoClient mongo = new MongoClient("localhost", 27017);
        try {
            DB db = mongo.getDB("GlassfishDB");
            DBCollection collection = db.getCollection("users");
            collection.drop();
        } finally {
            mongo.close();
        }

        resp.getWriter().println("All users were successfully deleted.");
    }//doPost


}//class
