<%-- 
    Document   : index
    Created on : Jun 24, 2019, 5:01:58 PM
    Author     : NhanTT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hi</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <c:set var="mappings" value="${applicationScope.CATEGORY_MAPPINGS.categoryMapping}"/>
        <c:forEach items="${mappings}" var="mapping">
            <c:out value="${mapping.name}"/><br/>
        </c:forEach>

        <c:set var="name" value="Piece of Heart &#038; Heart Container"/>
        <h2>${name}</h2>
    </body>
</html>
