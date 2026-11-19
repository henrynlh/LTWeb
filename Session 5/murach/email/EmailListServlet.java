package murach.email;

import java.io.*;
import java.util.GregorianCalendar;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import murach.business.User;
import murach.data.UserDB;

public class EmailListServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                        HttpServletResponse response)
            throws ServletException, IOException {

        GregorianCalendar currentDate = new GregorianCalendar();
        int currentYear = currentDate.get(GregorianCalendar.YEAR);
        request.setAttribute("currentYear", currentYear);

        String url = "/index.jsp";

        String action = request.getParameter("action");
        if (action == null) {
            action = "join";
        }

        System.out.println("EmailListServlet action = " + action);
        getServletContext().log("EmailListServlet action = " + action);

        if (action.equals("join")) {
            url = "/index.jsp";
        }
        else if (action.equals("add")) {

            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");

            User user = new User(firstName, lastName, email);

            String message;
            if (firstName == null || lastName == null ||
                email == null ||
                firstName.isEmpty() || lastName.isEmpty() ||
                email.isEmpty()) {

                message = "Please fill out all three text boxes.";
                url = "/index.jsp";
            } else {
                message = "";
                url = "/thanks.jsp";
                UserDB.insert(user);
            }

            request.setAttribute("user", user);
            request.setAttribute("message", message);
        }

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
