package murach.email;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import murach.business.User;
import murach.business.Survey;
import murach.data.Database;
import murach.data.UserDB;
import murach.data.SurveyDB;

public class EmailListServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String url = "/index.html";

        // get current action
        String action = request.getParameter("action");
        if (action == null) {
            action = "join";  // default action
        }

        // perform action and set URL to appropriate page
        if (action.equals("join")) {
            url = "/index.html";  // the "join" page
        }
        else if (action.equals("add")) {
            // get parameters from the request
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String birthDate = request.getParameter("birthDate");
            String hearAboutUs = request.getParameter("hearAboutUs");
            String contactMethod = request.getParameter("contactMethod");
            String[] receiveAnnouncements = request.getParameterValues("receiveAnnouncements");

            boolean offersSelected = false;
            boolean emailSelected = false;

            if (receiveAnnouncements != null) {
                for (String value : receiveAnnouncements) {
                    if ("offers".equals(value)) {
                        offersSelected = true;
                    }
                    if ("email".equals(value)) {
                        emailSelected = true;
                    }
                }
            }
            request.setAttribute("offersSelected", offersSelected);
            request.setAttribute("emailSelected", emailSelected);

            // store personal information and survey responses separately
            User user = new User(firstName, lastName, email, birthDate);
            Survey survey = new Survey(hearAboutUs, contactMethod, receiveAnnouncements != null);
            if (Database.isConfigured()) {
                try (Connection connection = Database.getConnection()) {
                    connection.setAutoCommit(false);
                    try {
                        UserDB.insert(connection, user);
                        SurveyDB.insert(connection, email, survey);
                        connection.commit();
                    } catch (SQLException | RuntimeException ex) {
                        try {
                            connection.rollback();
                        } catch (SQLException rollbackError) {
                            ex.addSuppressed(rollbackError);
                        }
                        throw ex;
                    }
                } catch (SQLException | RuntimeException ex) {
                    throw new ServletException("Unable to save survey to PostgreSQL.", ex);
                }
            } else {
                UserDB.insert(user);
                SurveyDB.insert(email, survey);
            }

            // set User object in request object and set URL
            request.setAttribute("user", user);
            request.setAttribute("survey", survey);
            url = "/thanks.jsp";  // the "thanks" page
        }

        // forward request and response objects to specified URL
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
