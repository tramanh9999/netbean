<%-- 
    Document   : google2
    Created on : Dec 18, 2019, 3:16:58 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <c:import url="WEB-INF/Products.xml" var="xmldoc"/>
        <c:import url="WEB-INF/Products.xsl" var="xslProduct"/>
        <!--#Place code below-->
        
        
        <x:transform xml="${xmldoc}" xslt="${xslProduct}"/>
    </body>
</html>
