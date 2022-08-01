<%-- 
    Document   : login
    Created on : 21-Jun-2022, 4:59:28 PM
    Author     : Dakota Chatt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="./styles/login.css">
        <title>Home Inventory | Login</title>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="#">Home Inventory</a>
        </nav>
        
        <div class="content-container">
            <form action="login" method="post">
                <h2>Login</h2>
                <input class="text-field form-input" type="text" name="username" value="" placeholder="Username">
                <br>
                <input class="text-field form-input" type="password" name="password" value="" placeholder="Password">
                <br>
                <input class="btn btn-primary" type="submit" value="Login">

                <!-- Displays error message if login credentials are incorrect or missing -->
                <c:if test="${message != null}">
                    <p><i>${message}</i></p>
                </c:if>
            </form>
        </div>
    </body>
</html>
