<%-- 
    Document   : reset
    Created on : 10-Aug-2022, 12:10:50 PM
    Author     : Dakota
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Home nVentory | Forgot Password</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="./styles/reset.css">
        <link rel="stylesheet" type="text/css" href="./styles/navbarCustom.css">
    </head>
    <body>
        <%@ include file="./templates/navbar.jsp"%>
        
        <div class="d-flex align-items-center justify-content-center vh-100">
            <div class="col-lg-5 col-md-8 col-10 border rounded shadow p-3 reset-form">
                <div class="row d-flex justify-content-center text-center">
                    <form class="col-lg-8 col-md-10 col-12" action="reset" method="post">
                        <h2>Forgot Your Password?</h2>
                        <input class="form-control mb-2" type="text" name="email" value="" placeholder="Email Address">
                        <input type="hidden" name="action" value="emaillink">
                        <input class="btn btn-primary form-control mb-2" type="submit" value="Send Email">

                        
                        <!-- Displays error message if login credentials are incorrect or missing -->
                        <c:if test="${message != null}">
                            <p class="text-center"><i>${message}</i></p>
                        </c:if>
                    </form>
                </div>
            </div>
        </div>
        
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    </body>
</html>
