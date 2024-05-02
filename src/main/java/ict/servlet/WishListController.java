/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ict.servlet;

import ict.db.DBConnection;
import ict.db.WishListDB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author ho135
 */
@WebServlet(urlPatterns = {"/AddToWishList"})
public class WishListController extends HttpServlet {

    private WishListDB db;
    private DBConnection connector;

    @Override
    public void init() {
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        this.connector = new DBConnection(dbUrl, dbUser, dbPassword);
        this.db = new WishListDB(connector);
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet WishListController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet WishListController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int equipmentID = Integer.parseInt(request.getParameter("equipmentID"));
        int userID = Integer.parseInt(request.getParameter("userID")); // Make sure the user ID is passed correctly

        boolean added = db.addToWishList(userID, equipmentID);
        if (added) {
            response.sendRedirect("wishlist.jsp"); // Redirect to wishlist page or confirmation page
        } else {
            response.getWriter().println("Error adding to wishlist");
        }
    }
        
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
