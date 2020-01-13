<%-- 
    Document   : update_student
    Created on : Nov 26, 2019, 12:34:29 PM
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
        <jsp:useBean id="INFO" scope="request" class="com.domdemo.dto.StudentDTO"  />

        <c:set var="INFO" value="${requestScope.INFO}"/>
        <form action="update" method="post">

            Student ID<input type="text" value="${INFO.id}" name="txtid"  />
            <br/>
            First name<input type="text" value="${INFO.firstname}" name="txtfn"/>
            <br/>

            Middle name<input type="text" value="${INFO.middlename}" name="txtmn"/>
            <br/>

            Last name<input type="text" value="${INFO.lastname}"name="txtln"/>
            <br/>

            Sex name<input type="text" value="${INFO.sex}"name="txtsex"/>
            <br/>

            Address name<input type="text" value="${INFO.address}"name="txtadd"/>
            <br/>

            Status name<input type="text" value="${INFO.status}" disabled name="txtst"/>
            <br/>



            <input type="submit" value="Save"/>
        </form>

    </body>
</html>
