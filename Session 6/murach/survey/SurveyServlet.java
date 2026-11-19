package murach.survey;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import murach.business.User;

@WebServlet("/survey")
public class SurveyServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String heardFrom = request.getParameter("heardFrom");
        String updates = "Yes".equals(request.getParameter("updates")) ? "Yes" : "No";
        String contactVia = request.getParameter("contactVia");
        User user = new User(email, firstName, lastName, heardFrom, updates, contactVia);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/survey.jsp").forward(request, response);
    }
}
