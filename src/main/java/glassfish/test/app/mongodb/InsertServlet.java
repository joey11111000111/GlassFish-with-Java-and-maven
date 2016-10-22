package glassfish.test.app.mongodb;


import com.mongodb.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value="/insert", name="InsertServlet")
public class InsertServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MongoClient mongo = new MongoClient("localhost", 27017);
        User user = null;
        try {
            DB db = mongo.getDB("GlassfishDB");
            DBCollection collection = db.getCollection("users");

            user = createRandomUser();
            WriteResult result = collection.insert(createDBObject(user));
        } finally {
            mongo.close();

        }

        if (user != null) {
            resp.getWriter().println("A new user was added:");
            resp.getWriter().println(user);
        } else {
            resp.getWriter().println("No new users were added!");
        }
    }//doPost

    private User createRandomUser() {
        User user = new User();
        user.setId((int)(Math.random() * 10000 + 1));
        user.setAge((int)(Math.random() * 62 + 13));
        user.setName("Random Name " + (int)(Math.random() * 10000 + 1));

        return user;
    }

    private static DBObject createDBObject(User user) {
        BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("_id", user.getId());
        builder.append("name", user.getName());
        builder.append("age", user.getAge());

        return builder.get();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("InitServlet get method was called");
    }
}
