<%-- 
    Document   : borrowEquipment
    Created on : 2024年5月2日, 下午11:08:01
    Author     : ho135
--%>

<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="java.util.List" %>
<%@ page import="ict.db.*, ict.Bean.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Available Equipment</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
        <%
            session = request.getSession(true);
            UserBean user = (UserBean) session.getAttribute("user");
            if (user != null) {
                session.setAttribute("userID", user.getUserId());
            }
        %>
    <h2>Available Equipment</h2>
    <table>
        <thead>
            <tr>
                <th>Equipment ID</th>
                <th>Equipment Name</th>
                <th>Description</th>
                <th>Serial Number</th>
                <th>Acquisition Date</th>
                <th>Status</th>
                <th>Campus</th>
            </tr>
        </thead>
        <tbody>
            <%
                String dbUrl = this.getServletContext().getInitParameter("dbUrl");
                String dbUser = this.getServletContext().getInitParameter("dbUser");
                String dbPassword = this.getServletContext().getInitParameter("dbPassword");
                EquipmentDB db = new EquipmentDB(new DBConnection(dbUrl,dbUser,dbPassword));
                List<EquipmentBean> equipments = db.getAllAvailableEquipments();
                for (EquipmentBean equipment : equipments) {
                    out.println("<tr>");
                    out.println("<td>" + equipment.getEquipmentID() + "</td>");
                    out.println("<td>" + equipment.getEquipmentName() + "</td>");
                    out.println("<td>" + equipment.getDescription() + "</td>");
                    out.println("<td>" + equipment.getSerialNumber() + "</td>");
                    out.println("<td>" + equipment.getAcquisitionDate() + "</td>");
                    out.println("<td>" + equipment.getStatus() + "</td>");
                    out.println("<td>" + equipment.getCampus() + "</td>");
                    out.println("<td>");
                    out.println("<form action='AddToWishList' method='post'>");
                    out.println("<input type='hidden' name='equipmentID' value='" + equipment.getEquipmentID() + "' />");
                    out.println("<input type='hidden' name='userID' value='"+ session.getAttribute("userID") +"' />"); 
                    out.println("<input type='submit' value='Wish list' />");
                    out.println("</form>");
                    out.println("</td>");
                    out.println("</tr>");
                }
            %>
        </tbody>
    </table>
</body>
</html>
