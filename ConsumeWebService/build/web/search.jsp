<%-- 
    Document   : search
    Created on : Dec 5, 2019, 5:03:34 PM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
            
         <form  action ="search" method="post">
            
            <label>
           Search <input name="txtse" type="text"/>
            </label>
            
            
            <input type="submit" value="Submit"/>
            
            <table>
                
                <thead>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                
            </thead>
            <tbody>
                <c:forEach items="${requestScope.INFO}" var="dto" varStatus="counter">
                <tr>
                    <td>${counter.count}</td>
                    <td>${dto.username}</td>
                    <td>${dto.fullname}</td>
                    <td>${dto.role}</td>
                </tr>
                
                </c:forEach>
            </tbody>
            </table>
        </form>
            
            
    </body>
</html>
