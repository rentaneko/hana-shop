<%-- 
    Document   : login
    Created on : Jan 4, 2021, 3:36:50 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Now!!!</title>
    </head>
    <body>
        <h1>Login Page</h1>
        
        <form action="MainController" method="POST" >
            Username: <input type="text" name="txtUsername" value="${txtUsername}"/>
            <br/>
            Password: <input type="password" name="txtPassword"/>
            <br/>
            <input type="submit" value="Login" name="action"/>
        </form>
        
        
    </body>
</html>
