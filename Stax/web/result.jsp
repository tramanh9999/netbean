<%-- 
    Document   : result
    Created on : Nov 27, 2019, 4:54:52 PM
    Author     : ADMIN
--%>

<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <!--//doc and string-->

        <c:set var="%doc1" value="${requestScope.DOC}"/>

        <x:parse    var="document" doc="${requestScope.STR}"/>
        <c:set var="roleFilter" value="admin"/>
        <x:set var="listUser" select="$doc1//account[role=$roleFilter]"/>
        <x:if select="$listUser">


            <table>
                <tr>
                    <th>No.</th>
                    <th>username</th>
                    <th>fullname</th>
                    <th>role</th>
                </tr>

                <x:forEach  var="user" select= "listUser" varStatus="counter">
                    <tr>
                        <td>$counter</td>
                        <td><x:out select="$user/username"/></td>
                        <td><x:out  select="$user/fullname"/></td>
                        <td><x:out  select="$user/role"/></td>
                    </tr>
                </x:forEach>
            </table>
        </x:if>
    </body>
</html>
