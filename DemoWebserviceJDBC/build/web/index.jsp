<%-- 
    Document   : index
    Created on : Dec 5, 2019, 4:20:52 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form  action ="login" method="post">
            
            <label>
           Username <input name="txtus" type="text"/>
            </label>
            <label>
            Password
            <input name="txtpa" type="text"/>
            </label>
            
            <input type="submit" value="Submit"/>
            
            
        </form>
    </body>
</html>
