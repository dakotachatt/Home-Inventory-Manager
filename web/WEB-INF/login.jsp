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
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="./styles/login.css">
        <link rel="stylesheet" type="text/css" href="./styles/navbarCustom.css">
        <title>Home nVentory | Login</title>
    </head>
    <body>
        <%@ include file="./templates/navbar.jsp"%>
        
        <div class="d-flex align-items-center justify-content-center vh-100">
            <div class="col-lg-5 col-md-8 col-10 border rounded shadow p-3 login-form">
                <div class="row d-flex justify-content-center text-center">
                    <form class="col-lg-8 col-md-10 col-12" action="login" method="post">
                        <h2>Login</h2>
                        <input class="form-control mb-2" type="text" name="email" value="" placeholder="Email Address">
                        <input class="form-control mb-2" type="password" name="password" value="" placeholder="Password">
                        <input class="btn btn-primary form-control mb-2" type="submit" value="Login">

                        <!-- Displays error message if login credentials are incorrect or missing -->
                        <c:if test="${message != null}">
                            <p class="text-center"><i>${message}</i></p>
                        </c:if>
                    </form>
                </div>

                <div class="row d-flex justify-content-center mt-3">
                    <hr class="col-lg-6 col-md-8 col-10"/>
                </div>

                <div class="row d-flex justify-content-center text-center">
                    <div class="col-lg-8 col-md-10 col-12">
                        <h5>New to Home nVentory?</h5>
                        <a href="register"><button class="btn btn-outline-primary form-control sign-up-btn mt-2">Sign Up</button></a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
