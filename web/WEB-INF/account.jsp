<%-- 
    Document   : account
    Created on : 4-Aug-2022, 9:00:52 PM
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
        <link rel="stylesheet" type="text/css" href="./styles/account.css">
        <link rel="stylesheet" type="text/css" href="./styles/navbarCustom.css">
        <title>Home nVentory | Update Account Info</title>
    </head>
    <body>
        <%@ include file="./templates/navbar.jsp"%>
        
        <div class="d-flex align-items-center justify-content-center vh-100  update-form">
            <div class="col-lg-5 col-md-8 col-10 border rounded shadow p-3 row d-flex justify-content-center text-center">
                <form class="col-lg-8 col-md-10 col-12" action="" method="post">
                    <h2>Update Account</h2>
                    <input class="form-control mb-2" type="text" name="editFName" value="${user.firstName}" placeholder="First Name" required>
                    <input class="form-control mb-2" type="text" name="editLName" value="${user.lastName}" placeholder="Last Name" required>
                    <input class="form-control mb-2" type="password" name="editPassword" value="${user.password}" placeholder="Password" required>
                    <input type="hidden" name="action" value="edit">
                    <input class="btn btn-primary form-control mb-2" type="submit" value="Update Account">
                    
                    <c:if test="${message != null}">
                        <p class="text-center">${message}</p>
                    </c:if>
                </form>
                
                <form class="col-lg-8 col-md-10 col-12" action="inventory" method="get">
                    <input class="btn btn-outline-primary form-control mb-2" type="submit" name="action" value="Back">
                </form>
                    
                <form class="col-lg-8 col-md-10 col-12 mt-3" action="" method="get">
                    <p>If you deactivate your account, you must contact an admin to get your account reactivated</p>
                    <input type="hidden" name="logout" value="">
                    <input class="btn btn-danger form-control mb-2" type="submit" name="action" value="Deactivate Account">
                </form>
            </div>
        </div>
    </body>
</html>
