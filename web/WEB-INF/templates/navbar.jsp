<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-expand-lg navbar-light bg-light shadow mb-5">
    <a class="navbar-brand" href="login">Home nVentory</a>
    
    <%-- Only shows navbar links if user is logged in --%>
    <c:if test="${email != null}">
        <div class="navbar-item-container collapse navbar-collapse justify-content-between">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link <c:if test='${currentPage.equals("inventory")}'>active</c:if>" href="inventory">Inventory</a>
                </li>

                <%-- Only shows admin related links if user is an admin --%>
                <c:if test="${loggedInRole == 1 || loggedInRole == 3}">
                    <li class="nav-item">
                        <a class="nav-link <c:if test='${currentPage.equals("manageUsers")}'>active</c:if>" href="manageUsers">Manage Users</a>
                    </li>
                    <c:if test="${loggedInRole == 1}">
                        <li class="nav-item">
                            <a class="nav-link <c:if test='${currentPage.equals("manageCategories")}'>active</c:if>" href="manageCategories">Manage Categories</a>
                        </li>
                    </c:if>
                </c:if>
            </ul>

            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link <c:if test='${currentPage.equals("account")}'>active</c:if>" href="account">Account Info</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="login?logout">Logout</a>
                </li>
            </ul>
        </div>
    </c:if>
</nav>
