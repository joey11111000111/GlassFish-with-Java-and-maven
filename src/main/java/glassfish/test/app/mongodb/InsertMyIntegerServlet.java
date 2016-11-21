/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glassfish.test.app.mongodb;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import proto.MyInteger;

/**
 *
 * @author Lajos
 */
@WebServlet(value = "/insertmyinteger", name = "InsertMyIntegerServlet")
public class InsertMyIntegerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MongoClient mongo = new MongoClient("localhost", 27017);
        MyInteger mInt = new MyInteger(10);
        try {
            DB db = mongo.getDB("GlassfishDB");
            DBCollection collection = db.getCollection("myIntegers");

            WriteResult result = collection.insert(mInt);
        } finally {
            mongo.close();
        }

        resp.getWriter().println("A new MyInteger was added:");
        resp.getWriter().println(mInt.bsonFromPojo());

    }//doPost
}
