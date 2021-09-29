<%-- 
    Document   : homepage
    Created on : Jan 6, 2021, 2:43:33 PM
    Author     : DELL
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
    </head>
    <body>
        <h2>Welcome ${sessionScope.LOGIN} </h2>
        <form action="MainController" method="POST">
            Category:  <select name="txtCategory">
                <option value="DRINK" >DRINK</option>
                <option value="SNACK" >SNACK</option>
                <option value="BEER" >BEER</option>
                <option value="SANDWICH" >SANDWICH</option>
                <option value="CAKE" >CAKE</option>
            </select> &nbsp; <input type="submit" name="action" value="SearchByCategory"/>   <br/> <br/>
            <input type="hidden" name="pageIndex" value="1"/>
        </form>
        <form action="MainController" method="POST">
            <input type="hidden" name="pageIndex" value="1"/>
            From Price: <input type="text" name="txtFromPrice" value="${param.txtFromPrice}"/>
            To Price:   <input type="text" name="txtToPrice" value="${param.txtToPrice}"/>
            <br/> <br/>
            Name:
            <input type="text" name="txtSearch"  value="${param.txtSearch}"/>
            <input type="submit" name="action" value="Search"/>
            <br/>
            <br/>
        </form>
        <c:if test="${sessionScope.LIST_PRODUCT_ADMIN != null}">
            <c:set var="all" value="${sessionScope.LIST_PRODUCT_ADMIN}" />
        </c:if>
        <c:if test="${sessionScope.INFO != null}">
            <c:set var="all" value="${sessionScope.INFO}" />
        </c:if>

        <c:if test="${all != null}">
            <table border="1">
                <thead>
                    <tr>
                        <th>Category</th>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Image</th>
                        <th>Create Date</th>
                        <th>Update</th>
                        <th>Delete</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${all}" var="dto">
                        <tr>
                            <td>${dto.categoryID}</td>
                            <td>${dto.productID}</td>
                            <td>${dto.productName}</td>
                            <td>${dto.price}</td>
                            <td>${dto.quantity}</td>
                            <td>
                                <img src="${dto.urlImg}" style="width: 50px; height: 50px;" />
                            </td>
                            <td>${dto.createDate}</td>
                            <td>
                                <form action="MainController" method="POST">
                                    <input type="hidden" value="${dto.productID}" name="id"/>
                                    <input type="hidden" value="${param.txtSearch}" name="search"/>
                                    <input type="submit" name="action" value="Update"/>
                                </form>
                            </td>
                            <td>
                                <form action="MainController" method="POST">
                                    <input type="hidden" value="${dto.productID}" name="id"/>
                                    <input type="hidden" value="${param.txtSearch}" name="search"/>
                                    <input type="submit" name="action" value="Delete"/>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        </c:if>




        <c:if test="${sessionScope.LIST_PRODUCT_ADMIN == null}">
            NO RECORD FOUND
        </c:if>

    </body>
</html>
