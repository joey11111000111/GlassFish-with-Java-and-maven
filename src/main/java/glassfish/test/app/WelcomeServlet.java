package glassfish.test.app;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value="/welcome", name="WelcomeServlet")
public class WelcomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<html>\n" +
                "<head>\n" +
                "\t<meta charset=\"utf-8\">\n" +
                "\t<title>GlassFishTest</title>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "\t<h1>Ha ezt látod, akkor működik!</h1>\n" +
                "<p>I don't know why it is't utf-8, I used the &ltmeta charset=\"utf-8\"&gt...</p>" +
                "</body>\n" +
                "</html>");
    }
}
