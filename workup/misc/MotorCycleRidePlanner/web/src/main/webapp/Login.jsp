<%-- 
    Document   : Login
    Created on : 26-Feb-2019, 09:13:24
    Author     : gallenc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>

    <h2>Hello, please log in:</h2>
    <br><br>
    <form action="j_security_check" method=post>
        <p><strong>Please Enter Your User Name: </strong>
            <input type="text" name="j_username" size="25">
        <p><p><strong>Please Enter Your Password: </strong>
            <input type="password" size="15" name="j_password">
        <p><p>
            <input type="submit" value="Submit">
            <input type="reset" value="Reset">
    </form>
</html>