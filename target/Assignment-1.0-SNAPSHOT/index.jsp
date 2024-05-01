<%-- 
    Document   : index
    Created on : Apr 29, 2024, 4:33:52 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login - Equipment Management System</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background: #f4f4f4;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
            }
            .login-container {
                background: white;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
            }
            form {
                display: flex;
                flex-direction: column;
            }
            input[type="email"], input[type="password"] {
                padding: 10px;
                margin: 10px 0;
                border: 1px solid #ddd;
                border-radius: 5px;
            }
            input[type="submit"] {
                background: #0056b3;
                color: white;
                border: none;
                padding: 10px;
                border-radius: 5px;
                cursor: pointer;
            }
            input[type="submit"]:hover {
                background: #004494;
            }
            h2 {
                text-align: center;
            }
        </style>
    </head>
    <body>
        <div class="login-container">
            <h2>Equipment Management System</h2>
            <form action="login" method="post">
            <input type="hidden" name="action" value="authenticate"/>
                <label for="email">Login Email:</label>
                <input type="email" id="email" name="email" required>
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
                <input type="submit" value="Login">
            </form>
            TEST UPDATE 
        </div>
    </body>
</html>