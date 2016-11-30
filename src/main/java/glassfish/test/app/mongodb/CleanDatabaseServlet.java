package glassfish.test.app.mongodb;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

@WebServlet(value="/mongoclean", name="CleanDatabaseServlet")
public class CleanDatabaseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MongoClient mongo = new MongoClient("localhost", 27017);
        DB db = mongo.getDB("GlassfishDB");
        DBCollection collection = db.getCollection("myIntegers");
        String message = "The number of saved integers: " + collection.count();
        resp.getWriter().println(message);
        mongo.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Prepare mongoDB
        MongoClient mongo = new MongoClient("localhost", 27017);
        DB db = mongo.getDB("GlassfishDB");
        DBCollection collection = db.getCollection("myIntegers");
        collection.drop();
        mongo.close();
    }

}
