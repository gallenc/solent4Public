<%-- 
    Document   : content
    Created on : Jan 4, 2020, 11:19:47 AM
    Author     : cgallen
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page import="org.solent.com504.project.model.user.dto.User"%>
<%@page import="org.solent.com504.project.model.user.dto.UserRoles"%>
<c:set var = "selectedPage" value = "partys" scope="request"/>
<jsp:include page="header.jsp" />

<!-- Begin page content -->
<main role="main" class="container">

    <div>
        <H1>User Details</H1>
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
                        <td>${party.id}</td>
                    </tr>
                    <tr>
                        <td>uuid</td>
                        <td>${party.uuid}</td>
                    </tr>
                    <tr>
                        <td>First Name</td>
                        <td><input type="text" name="firstName" value="${party.firstName}" /></td>
                    </tr>
                    <tr>
                        <td>Second Name</td>
                        <td><input type="text" name="secondName" value="${party.secondName}" /></td>
                    </tr>
                    <tr>
                        <td>House Number</td>
                        <td><input type="text" name="number" value="${party.address.number}" /></td>
                    </tr>
                    <tr>
                        <td>Address Line 1</td>
                        <td><input type="text" name="addressLine1" value="${party.address.addressLine1}" /></td>
                    </tr>
                    <tr>
                        <td>Address Line 2</td>
                        <td><input type="text" name="addressLine2" value="${party.address.addressLine2}" /></td>
                    </tr>
                    <tr>
                        <td>county</td>
                        <td><input type="text" name="county" value="${party.address.county}" /></td>
                    </tr>
                    <tr>
                        <td>country</td>
                        <td><input type="text" name="country" value="${party.address.country}" /></td>
                    </tr>
                    <tr>
                        <td>postcode</td>
                        <td><input type="text" name="postcode" value="${party.address.postcode}" /></td>
                    </tr>
                    <tr>
                        <td>latitude</td>
                        <td><input type="text" name="latitude" value="${party.address.latitude}" /></td>
                    </tr>
                    <tr>
                        <td>longitude</td>
                        <td><input type="text" name="longitude" value="${party.address.longitude}" /></td>
                    </tr>                      <tr>
                        <td>telephone</td>
                        <td><input type="text" name="telephone" value="${party.address.telephone}" /></td>
                    </tr>
                    <tr>
                        <td>mobile</td>
                        <td><input type="text" name="mobile" value="${party.address.mobile}" /></td>
                    </tr>
                    <tr>
                        <td>Roles</td>
                        <td>|<c:forEach var="role" items="${party.roles}"> ${role.name} |</c:forEach></td>
                        </tr>
                    </tbody>

                </table>
                <div>
                <sec:authorize access="hasRole('ROLE_GLOBAL_ADMIN')" >
                    <p>Manage User Status </p>
                    <table class="table">
                        <thead>
                        </thead>
                        <tbody>
                            <tr>
                                <td>
                                    <div class="custom-control custom-switch">
                                        <!-- party.isEnabled= ${party.enabled} -->
                                        <input type="checkbox" class="custom-control-input" 
                                               id="partyEnabled" name="partyEnabled" 
                                               value="true" <c:if test="${party.enabled}">checked</c:if> > 
                                               <label class="custom-control-label" for="partyEnabled">USER ENABLED</label>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <p>Manage User Roles </p>
                        <table class="table">
                            <thead>
                            </thead>
                            <tbody>
                            <c:forEach var="entry" items="${selectedRolesMap}">
                                <tr>
                                    <td>
                                        <div class="custom-control custom-switch">
                                            <input type="checkbox" class="custom-control-input" 
                                                   id="${entry.key}" name="selectedRoles" value="${entry.key}" ${entry.value} >
                                            <label class="custom-control-label" for="${entry.key}">${entry.key}</label>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </sec:authorize>  
            </div>
            <input type="hidden" name="uuid" value="${party.uuid}"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button class="btn" type="submit" >Update ${party.uuid}</button>
        </form>
        <sec:authorize access="hasRole('ROLE_GLOBAL_ADMIN')">
            <BR>
            <form action="./partys">
                <button class="btn" type="submit" >Return To Partys</button>
            </form> 
        </sec:authorize> 

    </div>

</main>

<jsp:include page="footer.jsp" />
