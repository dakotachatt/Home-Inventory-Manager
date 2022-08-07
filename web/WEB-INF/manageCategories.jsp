<%-- 
    Document   : manageCategories
    Created on : 7-Aug-2022, 10:45:52 AM
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
        <link rel="stylesheet" type="text/css" href="./styles/manageCategories.css">
        <link rel="stylesheet" type="text/css" href="./styles/navbarCustom.css">
        <title>Home nVentory | Manage Categories</title>
    </head>
    <body>
        <%@ include file="./templates/navbar.jsp"%>
        
        <div class="row d-flex justify-content-center">
            <c:if test="${categorySelected == null}">
                <div class="col-xl-3 col-lg-6 col-md-6 col-10">
                    <form action="" method="post">
                        <h2>Add Category</h2>
                        <input class="form-control mb-2" type="text" name="newCategory" value="" placeholder="Category Name" required>
                        <input type="hidden" name="action" value="add">
                        <input class="btn btn-primary form-control mb-2" type="submit" value="Save">

                        <c:if test="${message != null}">
                            <p class="text-center">${message}</p>
                        </c:if>
                    </form>
                </div>
            </c:if>
            
            <c:if test="${categorySelected != null}">
                <div class="col-xl-3 col-lg-6 col-md-6 col-10">
                    <form action="" method="post">
                        <h2>Edit Category</h2>
                        <input class="form-control mb-2" type="text" name="editCategory" value="${editCategory.categoryName}" placeholder="Category Name" required>
                        <input type="hidden" name="action" value="edit">
                        <input class="btn btn-primary form-control mb-2" type="submit" value="Edit">

                        <c:if test="${message != null}">
                            <p class="text-center">${message}</p>
                        </c:if>
                    </form>
                </div>
            </c:if>
            
            <div class="col-xl-7 col-lg-8 col-md-10 col-10 text-center">
                <h2>Manage Categories</h2>
                <table class="table table-striped table-bordered table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>Category Name</th>
                            <th>Edit</th>
                        </tr>
                    </thead>
                    
                    <c:forEach var="category" items="${categories}">
                        <tr>
                            <td>${category.categoryName}</td>
                            <td>
                                <%-- Gets the user's email for the particular row being edited --%>
                                <c:url value="/manageCategories" var="editURL">
                                    <c:param name="action" value="edit" />
                                    <c:param name="categoryId" value="${category.categoryId}" />
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
