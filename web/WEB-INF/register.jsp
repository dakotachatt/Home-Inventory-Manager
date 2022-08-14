<%-- 
    Document   : register
    Created on : 4-Aug-2022, 3:03:47 PM
    Author     : Dakota
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="./styles/register.css">
        <link rel="stylesheet" type="text/css" href="./styles/navbarCustom.css">
        <title>Home nVentory | Create Account</title>
    </head>
    <body>
        <%@ include file="./templates/navbar.jsp"%>
        
        <div class="d-flex align-items-center justify-content-center vh-100  register-form">
            <div class="col-lg-5 col-md-8 col-10 border rounded shadow p-3 row d-flex justify-content-center text-center">
                <form class="col-lg-8 col-md-10 col-12" action="" method="post">
                    <h2>Create Account</h2>
                    <input class="form-control mb-2" type="text" name="newFName" value="${newFirstName}" placeholder="First Name" required>
                    <input class="form-control mb-2" type="text" name="newLName" value="${newLastName}" placeholder="Last Name" required>
                    <input class="form-control mb-2" type="email" name="newEmail" value="${newEmail}" placeholder="Email Address" required>
                    <input class="form-control mb-2" type="password" name="newPassword" value="" placeholder="Password" required>
                    <input type="hidden" name="action" value="add">
                    <input class="btn btn-primary form-control mb-2" type="submit" value="Create Account">

                    <c:if test="${message != null}">
                        <p class="text-center">${message}</p>
                    </c:if>
                </form>
            </div>
        </div>
                    
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    </body>
</html>
