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
    <H1>Modify User</H1>
    <!-- print error message if there is one -->
    <div style="color:red;">${errorMessage}</div>
    <div style="color:green;">${message}</div>

    <form action="./viewModifyUser" method="post">
        <table class="table">
            <thead>
            </thead>

            <tbody>
                <tr>
                    <td>User ID</td>
                    <td>${user.id}</td>
                </tr>
                <tr>
                    <td>username</td>
                    <td>${user.username}</td>
                </tr>
                <tr>
                    <td>First Name</td>
                    <td><input type="text" name="firstName" value="${user.firstName}" /></td>
                </tr>
                <tr>
                    <td>Second Name</td>
                    <td><input type="text" name="secondName" value="${user.secondName}" /></td>
                </tr>
                <tr>
                    <td>Roles</td>
                    <td>|<c:forEach var="role" items="${user.roles}"> ${role.name} |</c:forEach></td>
                    </tr>

                </tbody>

            </table>
            <div>
                <p>User Roles </p>
                <table class="table">
                    <thead>
                    </thead>
                    <tbody>
                    <c:forEach var="entry" items="${selectedRolesMap}">
                        <tr>
                            <td>
                                <div class="custom-control custom-switch">
                                    <input type="checkbox" class="custom-control-input" 
                                           id="${entry.key}" name="selectedRoles" value="${entry.key}" checked="${entry.value}">
                                    <label class="custom-control-label" for="${entry.key}">${entry.key}</label>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <input type="hidden" name="username" value="${user.username}"/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button  type="submit" >Update ${user.username}</button>
    </form>
    <form action="./users">
        <button  type="submit" >Return To Users</button>
    </form> 

</div>


<jsp:include page="footer.jsp" />
