<%-- 
    Document   : student
    Created on : Nov 25, 2019, 4:18:45 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>       
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>


<html>
    <body bgcolor="cyan">
        <h1>
            XML transforming with jsp taglib
        </h1> 
        <c:import var="xmldoc" url="jsp_xml/multiXsl/templateDemo.xml"/>
        <c:import var="xsldoc" url="jsp_xml/multiXsl/applymutltemplate.xsl"/>
        <c:import var="xslhtmldoc" url="jsp_xml/multiXsl/templateHtml.xsl"/>
        <x:transform xslt="${xslhtmldoc}">
            <x:transform xml="${xmldoc}" xslt="${xsldoc}"/>
        </x:transform>
    </body>
</html>