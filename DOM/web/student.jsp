<%-- 
    Document   : student
    Created on : Nov 25, 2019, 4:18:45 PM
    Author     : ADMIN
--%>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@page contentType="text/html" pageEncoding="UTF-8"%>
            <!DOCTYPE html>
            <html>

            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>JSP Page</title>

                <link rel="stylesheet" href="WEB-INF/css/student_css.css" />

                <script lang="text/javascript" src="WEB-INF/js/student_js.js"></script>
            </head>


            <body style="display: inline">



                <a value="Load xml file to database" type="button" href="load"></a>

                <form action="search" method="post">
                    Address <input type="text" name="txtadd" />
                    <input type="submit" value="Search" />
                </form>

                <form action="search_stax" method="post">
                    Address <input type="text" name="txtadd" />
                    <input type="submit" value="Search stax" />
                </form>
                <div>
                    <div>
                        <c:if test="${not empty requestScope.INFO}">
                            <h2>
                                Search result
                            </h2>
                            <table style="border-style: solid">
                                <thead>
                                    <th>No.</th>
                                    <th>StudentId</th>
                                    <th>Full name</th>
                                    <th>Address</th>
                                    <th>Status</th>
                                    <th>Sex</th>
                                    <th>Delete</th>
                                    <th>Update</th>
                                </thead>
                                <tbody>
                                    <c:forEach items="${requestScope.INFO}" var="dto" varStatus="counter">
                                        <tr>
                                            <td>${counter.count}</td>
                                            <td>${dto.id}</td>
                                            <td>${dto.firstname} ${dto.middlename} ${dto.lastname}</td>
                                            <td>${dto.address}</td>
                                            <td>${dto.status}</td>
                                            <td>${dto.sex}</td>
                                            <td><a href="delete?id=${dto.id}">Delete</a></td>
                                            <td><a href="update?id=${dto.id}">Update</a></td>
                                            <td><a href="delete_stax?id=${dto.id}">Delete using stax</a></td>
                                            <td><a href="update_stax?id=${dto.id}">Update using stax</a></td>
                                        </tr>
                                    </c:forEach>




                                </tbody>
                            </table>
                        </c:if>
                    </div>
                    <div>
                        <table>
                            <tbody>
                                <tr>
                                    <form action="add" method="post">
                                        AutoID <input type="text" name="txtfn" placeholder="FirstName" style="::placeholder{opacity: 0.5}" />
                                        <input type="text" name="txtmn" placeholder="MiddleName" style="::placeholder{opacity: 0.5}" />
                                        <input type="text" name="txtln" placeholder="LastName" style="::placeholder{opacity: 0.5}" />
                                        <input type="text" placeholder="Address" name="txtadd" />
                                        <input type="text" placeholder="Status" name="txtst" />
                                        <input type="text" placeholder="Sex" name="txtsex" />
                                        <input value="Add" type="submit" />
                                    </form>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                </div>


            </body>

            </html>