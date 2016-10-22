package glassfish.test.app.mongodb;

import com.mongodb.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value="/read", name="ReadServlet")
public class ReadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MongoClient mongo = new MongoClient("localhost", 27017);
        StringBuilder sb = new StringBuilder();
        try {
            DB db = mongo.getDB("GlassfishDB");
            DBCollection collection = db.getCollection("users");
            sb.append("Number of users: " + collection.getCount()).append("\n");

            DBCursor cursor = collection.find();
            while (cursor.hasNext()) {
                DBObject element = cursor.next();
                User currentUser = new User();
                currentUser.setName((String)element.get("name"));
                currentUser.setAge((Integer)element.get("age"));
                currentUser.setId((Integer)element.get("_id"));
                sb.append("---------------------------------");
                sb.append("\n");
                sb.append(currentUser.toString());
                sb.append("\n");
            }

            resp.getWriter().println(sb.toString());

        } finally {
            mongo.close();
        }

    }
}
