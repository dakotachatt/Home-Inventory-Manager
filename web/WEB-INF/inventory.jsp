<%-- 
    Document   : inventory
    Created on : 21-Jun-2022, 4:59:13 PM
    Author     : Dakota Chatt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="./styles/inventory.css">
        <title>Home Inventory | Add Inventory</title>
    </head>
    <body>
       <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="#">Home Inventory</a>
        
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
                <h2>Inventory for ${user.firstName} ${user.lastName}</h2>
                <table>
                    <tr>
                        <th>Category</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Delete</th>
                        <th>Edit</th>
                    </tr>

                    <c:forEach var="item" items="${items}">
                        <tr>
                            <td>${item.category.categoryName}</td>
                            <td>${item.itemName}</td>
                            <td><fmt:formatNumber value="${item.price}" type="currency" /></td>
                            <td>
                                <c:url value="/inventory" var="deleteURL">
                                    <c:param name="action" value="delete" />
                                    <c:param name="itemId" value="${item.itemId}" />
                                </c:url>

                                <a class="btn btn-danger" role="button" href="${deleteURL}">Delete</a>
                            </td>
                            <td>
                                <c:url value="/inventory" var="editURL">
                                    <c:param name="action" value="edit" />
                                    <c:param name="itemId" value="${item.itemId}" />
                                </c:url>

                                <a class="btn btn-dark" role="button" href="${editURL}">Edit</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>        
            </div>
        
            <c:if test="${itemSelected == null}">
                <div class="add-form">
                    <h3>Add Item</h3>
                    <form action="" method="post">
                        <select class="form-input" name="newCategory" aria-label="Category Drop Down Menu" required>
                            <c:forEach var="category" items="${categories}">
                                <option value="${category.categoryId}">${category.categoryName}</option>
                            </c:forEach>
                        </select>
                        <br>
                        <input class="text-field form-input" type="text" name="newItemName" value="" aria-label="Item Name" placeholder="Item Name" required>
                        <br>
                        <input class="text-field form-input" type="text" name="newPrice" value="" aria-label="Item Price" placeholder="Price" required>
                        <br>
                        <input type="hidden" name="action" value="add">
                        <input class="submit-button form-input" type="submit" value="Add" aria-label="New Item Submit Button">
                    </form>

                    <!-- Displays error and success messages -->
                    <c:if test="${message != null}">
                        <p>${message}</p>
                    </c:if>            
                </div>
            </c:if>
            
            <c:if test="${itemSelected != null}">
                <div class="add-form">
                    <h3>Edit Item: ${editItem.itemName}</h3>
                    <form action="" method="post">
                        <select class="form-input" name="editCategory" required>
                            <c:forEach var="category" items="${categories}">
                                <option value="${category.categoryId}" <c:if test="${editItem.category.categoryId == category.categoryId}">selected="selected"</c:if>>${category.categoryName}</option>
                            </c:forEach>
                        </select>
                        <br>
                        <input class="text-field form-input" type="text" name="editItemName" value="${editItem.itemName}" placeholder="Item Name" required>
                        <br>
                        <input class="text-field form-input" type="text" name="editPrice" value="${editItem.price}" placeholder="Price" required>
                        <br>
                        <input class="submit-button form-input" type="submit" name="action" value="Edit">
                    </form>
                    
                    <form action="" method="get">
                        <input class="submit-button cancel-button form-input" type="submit" name="action" value="Cancel">
                    </form>
                </div>
            </c:if>
        </div>
    </body>
</html>
