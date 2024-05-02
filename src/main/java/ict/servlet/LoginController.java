package ict.servlet;

import ict.Bean.UserBean;
import ict.db.DBConnection;
import ict.db.UserDB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    private UserDB db;
    private DBConnection connector;

    @Override
    public void init() {
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        this.connector = new DBConnection(dbUrl, dbUser, dbPassword);
        this.db = new UserDB(connector);
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("logout".equals(action)) {
            doLogout(request, response);
        } else {
            doLogin(request, response);
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (isAuthenticated(request)) {
            // Redirect the user to the welcome page unless they are logging out
            if (!"logout".equals(action)) {
                redirectToWelcomePage(request, response);
                return;
            }
        }
        if ("authenticate".equals(action)) {
            doAuthenticate(request, response);
        } else if ("logout".equals(action)) {
            doLogout(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }
    }

    private void doAuthenticate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UserBean user = db.isValidUser(email, password); 
        String targetURL;

        if (user != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user); 
            switch (user.getRole()) {
                case "Administrator":
                    targetURL = "/admin_dashboard.jsp";
                    break;
                case "Technician":
                    targetURL = "/technician_dashboard.jsp";
                    break;
                case "Courier":
                    targetURL = "/courier_dashboard.jsp";
                    break;
                case "User":
                    targetURL = "/user_dashboard.jsp";
                    break;
                default:
                    targetURL = "/loginError.jsp"; 
                    break;
            }
        } else {
            targetURL = "/loginError.jsp"; 
        }

        RequestDispatcher rd = getServletContext().getRequestDispatcher(targetURL);
        rd.forward(request, response);
    }

    private void doLogin(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException, ServletException {
        String targetURL = "login.jsp";
        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetURL);
        rd.forward(request, response);
    }

    private void doLogout(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        doLogin(request, response);
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        boolean result = false;
        HttpSession session = request.getSession(false);
        if (session.getAttribute("userId") != null) {
            result = true;
        }
        return result;
    }

    private void redirectToWelcomePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/welcome.jsp");
        rd.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Login Controller Servlet";
    }
}
