<%-- 
    Document   : manageUsers
    Created on : 21-Jun-2022, 4:58:49 PM
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
        <link rel="stylesheet" type="text/css" href="./styles/manageUsers.css">
        <link rel="stylesheet" type="text/css" href="./styles/navbarCustom.css">
        <title>Home nVentory | Manage Users</title>
    </head>
    <body>
        <%@ include file="./templates/navbar.jsp"%>

        <div class="row d-flex justify-content-center">
            <c:if test="${userSelected == null}">
                <div class="col-xl-3 col-lg-6 col-md-6 col-10">
                    <form action="" method="post">
                        <h2>Add User</h2>
                        <input class="form-control mb-2" type="email" name="newEmail" value="" placeholder="Email Address" required>
                        <input class="form-control mb-2" type="password" name="newPassword" value="" placeholder="Password" required>
                        <input class="form-control mb-2" type="text" name="newFName" value="" placeholder="First Name" required>
                        <input class="form-control mb-2" type="text" name="newLName" value="" placeholder="Last Name" required>
                        <input type="hidden" name="action" value="add">
                        <input class="btn btn-primary form-control mb-2" type="submit" value="Save">

                        <c:if test="${message != null}">
                            <p class="text-center">${message}</p>
                        </c:if>
                    </form>
                </div>
            </c:if>

            <c:if test="${userSelected != null}">
                <div class="col-xl-3 col-lg-6 col-md-6 col-10">
                    <form action="" method="post">
                        <h2>Edit User: ${editUser.firstName} ${editUser.lastName}</h2>
                        <input class="form-control mb-2" type="password" name="editPassword" value="${editUser.password}" placeholder="Password" required>
                        <input class="form-control mb-2" type="text" name="editFName" value="${editUser.firstName}" placeholder="First Name" required>
                        <input class="form-control mb-2" type="text" name="editLName" value="${editUser.lastName}" placeholder="Last Name" required>
                        <select class="form-select mb-2" name="role">
                            <c:forEach var="role" items="${roles}">
                                <option value="${role.roleId}" <c:if test="${editUser.role.roleId == role.roleId}">selected="selected"</c:if>>${role.roleName}</option>
                            </c:forEach>
                        </select>
                        <c:if test="${loggedInRole == 1}">
                            <select class="form-select mb-2" name="company">
                                <c:forEach var="company" items="${companies}">
                                    <option value="${company.companyId}" <c:if test="${editUser.company.companyId == company.companyId}">selected="selected"</c:if>>${company.companyName}</option>
                                </c:forEach>
                            </select>
                        </c:if>

                        <div class="form-check">
                            <input class="form-check-input mb-2" type="checkbox" id="editIsActive" name="editIsActive" <c:if test="${editUser.active}">checked="checked"</c:if>>
                            <label class="form-check-label mb-2" for="editIsActive">Active</label>
                        </div>
                        <input class="btn btn-primary form-control mb-2" type="submit" name="action" value="Edit">
                    </form>

                    <form action="" method="get">
                        <input class="btn btn-outline-primary form-control mb-2" type="submit" name="action" value="Cancel">
                    </form>
                </div>
            </c:if>
            
            <div class="col-xl-7 col-lg-8 col-md-10 col-10 text-center">
                <h2>Manage Users</h2>
                <table class="table table-striped table-bordered table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Company</th>
                            <th>Role</th>
                            <th>Status</th>
                            <th>Delete</th>
                            <th>Edit</th>
                        </tr>
                    </thead>
                    
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.company.companyName}</td>
                            <td>${user.role.roleName}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${user.active}">Active</c:when>
                                    <c:otherwise>Inactive</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:url value="/manageUsers" var="deleteURL">
                                    <c:param name="action" value="delete" />
                                    <c:param name="email" value="${user.email}" />
                                </c:url>

                                <a class="btn btn-danger" role="button" href="${deleteURL}">Delete</a>
                            </td>
                            <td>
                                <%-- Gets the user's email for the particular row being edited --%>
                                <c:url value="/manageUsers" var="editURL">
                                    <c:param name="action" value="edit" />
                                    <c:param name="email" value="${user.email}" />
                                </c:url>

                                <a class="btn btn-dark" role="button" href="${editURL}">Update</a>                        
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </body>
</html>
