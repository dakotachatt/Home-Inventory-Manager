<%-- 
    Document   : registerConfirmation
    Created on : 7-Aug-2022, 2:12:27 PM
    Author     : Dakota
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Home nVentory</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="./styles/registerConfirmation.css">
        <link rel="stylesheet" type="text/css" href="./styles/navbarCustom.css">
    </head>
    <body>
        <%@ include file="./templates/navbar.jsp"%>
        
        <div>
            <h2>Your account has been created.</h2>
            <p>You will receive a confirmation email. You must click the confirmation link before being able to sign into your account</p>
        </div>
    </body>
</html>
