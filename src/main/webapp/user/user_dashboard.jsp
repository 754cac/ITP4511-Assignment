<%-- 
    Document   : user_dashboard
    Created on : 2024年5月2日, 下午7:18:19
    Author     : ho135
--%>
<%@ page import="ict.Bean.UserBean" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            session = request.getSession(true);
            UserBean user = (UserBean) session.getAttribute("user");
            if (user != null) {
                session.setAttribute("userID", user.getUserId());
            }
        %>
        <h1>USER</h1>
            <a href="user/borrowEquipment.jsp">Borrow Equipment</a>
            <a href="user/wishlist.jsp">Wish List</a>
        
    </body>
</html>
