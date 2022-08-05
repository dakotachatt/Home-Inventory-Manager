<%-- 
    Document   : admin
    Created on : 21-Jun-2022, 4:58:49 PM
    Author     : Dakota Chatt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="./styles/admin.css">
        <title>Home Inventory | Admin</title>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="#">Home nVentory</a>
        
            <div class="navbar-item-container collapse navbar-collapse">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="inventory">Inventory</a>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link" href="admin">Admin</a>
                    </li>
                </ul>
                
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="login?logout">Logout</a>
                    </li>
                </ul>
            </div>
        </nav>

        <div class="content-container">
            <div class="manage-table-container">
                <h2>Manage Users</h2>
                <table>
                    <tr>
                        <th>Email</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Role</th>
                        <th>Status</th>
                        <th>Delete</th>
                        <th>Edit</th>
                    </tr>

                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td>${user.email}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.role.roleName}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${user.active}">Active</c:when>
                                    <c:otherwise>Inactive</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:url value="/admin" var="deleteURL">
                                    <c:param name="action" value="delete" />
                                    <c:param name="email" value="${user.email}" />
                                </c:url>

                                <a class="btn btn-danger" role="button" href="${deleteURL}">Delete</a>
                            </td>
                            <td>
                                <%-- Gets the user's email for the particular row being edited --%>
                                <c:url value="/admin" var="editURL">
                                    <c:param name="action" value="edit" />
                                    <c:param name="email" value="${user.email}" />
                                </c:url>

                                <a class="btn btn-dark" role="button" href="${editURL}">Update</a>                        
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <c:if test="${userSelected == null}">
                <div class="add-form">
                    <form action="" method="post">
                        <h2>Add User</h2>
                        <input class="text-field form-input" type="email" name="newEmail" value="" placeholder="Email Address" required>
                        <br>
                        <input class="text-field form-input" type="password" name="newPassword" value="" placeholder="Password" required
                        <br>
                        <input class="text-field form-input" type="text" name="newFName" value="" placeholder="First Name" required>
                        <br>
                        <input class="text-field form-input" type="text" name="newLName" value="" placeholder="Last Name" required>
                        <br>
                        <input type="hidden" name="action" value="add">
                        <input class="submit-button form-input" type="submit" value="Save">

                        <c:if test="${message != null}">
                            <p>${message}</p>
                        </c:if>
                    </form>
                </div>
            </c:if>

            <c:if test="${userSelected != null}">
                <div class="update-form">
                    <form action="" method="post">
                        <h2>Edit User: ${editUser.firstName} ${editUser.lastName}</h2>
                        <input class="text-field form-input" type="password" name="editPassword" value="${editUser.password}" placeholder="Password" required>
                        <br>
                        <input class="text-field form-input" type="text" name="editFName" value="${editUser.firstName}" placeholder="First Name" required>
                        <br>
                        <input class="text-field form-input" type="text" name="editLName" value="${editUser.lastName}" placeholder="Last Name" required>
                        <br>
                        <select name="role">
                            <c:forEach var="role" items="${roles}">
                                <option value="${role.roleId}" <c:if test="${editUser.role.roleId == role.roleId}">selected="selected"</c:if>>${role.roleName}</option>
                            </c:forEach>
                        </select>
                        <br>
                        <input type="checkbox" id="editIsActive" name="editIsActive" <c:if test="${editUser.active}">checked="checked"</c:if>>
                        <label for="editIsActive">Active</label>
                        <br>
                        <input class="submit-button form-input" type="submit" name="action" value="Edit">
                    </form>

                    <form action="" method="get">
                        <input class="submit-button cancel-button form-input" type="submit" name="action" value="Cancel">
                    </form>
                </div>
            </c:if>
        </div>
        
                <ul>
                    <c:forEach var="user" items="${regUsers}">
                        <li>${user.email}</li>
                    </c:forEach>
                </ul>
                
        <br>
        <br>
                <ul>
                    <c:forEach var="user" items="${sysAdUsers}">
                        <li>${user.email}</li>
                    </c:forEach>
                </ul>
    </body>
</html>
