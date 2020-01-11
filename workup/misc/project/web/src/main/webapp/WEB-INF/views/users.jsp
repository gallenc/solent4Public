<%-- 
    Document   : content
    Created on : Jan 4, 2020, 11:19:47 AM
    Author     : cgallen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.solent.com504.project.model.user.dto.User"%>
<%@page import="org.solent.com504.project.model.user.dto.UserRoles"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp" />


<div>
    <p>showing ${userListSize} users: </p>
    <table class="table">
        <thead>
            <tr>
                <th scope="col">Id</th>
                <th scope="col">Username</th>
                <th scope="col">First Name</th>
                <th scope="col">Second Name</th>
                <th scope="col">Roles</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${userList}">
            <tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.firstName}</td>
                <td>${user.secondName}</td>
                <td>|<c:forEach var="role" items="${user.roles}"> ${role.name} |<br></c:forEach></td>
                          <td>
                        <form action="./viewModifyUser" method="get">
                            <input type="hidden" name="username" value="${user.username}">
                            <button type="submit" >Modify User</button>
                        </form> 
                    </td>
            </tr>
        </c:forEach>

        </tbody>
    </table>
</div>


<jsp:include page="footer.jsp" />
