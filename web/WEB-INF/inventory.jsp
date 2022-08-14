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
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="./styles/inventory.css">
        <link rel="stylesheet" type="text/css" href="./styles/navbarCustom.css">
        <title>Home Inventory | Add Inventory</title>
    </head>
    <body>
        <%@ include file="./templates/navbar.jsp"%>
        
        <div class="row d-flex justify-content-center">
            <c:if test="${itemSelected == null}">
                <div class="col-xl-4 col-lg-4 col-10">
                    <h2>Add Item</h2>
                    <form action="" method="post">
                        <select class="form-select mb-2" name="newCategory" aria-label="Category Drop Down Menu" required>
                            <option selected>Select a Category</option>
                            <c:forEach var="category" items="${categories}">
                                <option value="${category.categoryId}">${category.categoryName}</option>
                            </c:forEach>
                        </select>
                        <input class="form-control mb-2" type="text" name="newItemName" value="" aria-label="Item Name" placeholder="Item Name" required>
                        <input class="form-control mb-2" type="text" name="newPrice" value="" aria-label="Item Price" placeholder="Price" required>
                        <input type="hidden" name="action" value="add">
                        <input class="btn btn-primary form-control mb-2" type="submit" value="Add" aria-label="New Item Submit Button">
                    </form>

                    <!-- Displays error and success messages -->
                    <c:if test="${message != null}">
                        <p class="text-center">${message}</p>
                    </c:if>            
                </div>
            </c:if>
            
            <c:if test="${itemSelected != null}">
                <div class="col-xl-4 col-lg-4 col-10">
                    <h2>Edit Item: ${editItem.itemName}</h2>
                    <form action="" method="post">
                        <select class="form-select mb-2" name="editCategory" required>
                            <c:forEach var="category" items="${categories}">
                                <option value="${category.categoryId}" <c:if test="${editItem.category.categoryId == category.categoryId}">selected="selected"</c:if>>${category.categoryName}</option>
                            </c:forEach>
                        </select>
                        <input class="form-control mb-2" type="text" name="editItemName" value="${editItem.itemName}" placeholder="Item Name" required>
                        <input class="form-control mb-2" type="text" name="editPrice" value="${editItem.price}" placeholder="Price" required>
                        <input class="btn btn-primary form-control mb-2" type="submit" name="action" value="Edit">
                    </form>
                    
                    <form action="" method="get">
                        <input class="btn btn-outline-primary form-control mb-2" type="submit" name="action" value="Cancel">
                    </form>
                </div>
            </c:if>
            
            <div class="col-xl-6 col-lg-8 col-10 text-center">
                <h2>Inventory for ${user.firstName} ${user.lastName}</h2>
                <table class="table table-striped table-bordered table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>Category</th>
                            <th>Name</th>
                            <th>Price</th>
                            <th>Delete</th>
                            <th>Edit</th>
                        </tr>
                    </thead>
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

                                <a class="btn btn-outline-danger" role="button" href="${deleteURL}">Delete</a>
                            </td>
                            <td>
                                <c:url value="/inventory" var="editURL">
                                    <c:param name="action" value="edit" />
                                    <c:param name="itemId" value="${item.itemId}" />
                                </c:url>

                                <a class="btn btn-outline-dark" role="button" href="${editURL}">Edit</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>        
            </div>
        </div>
                
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>                
    </body>
</html>
