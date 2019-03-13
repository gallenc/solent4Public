<%-- 
    Document   : ListUsers
    Created on : 26-Feb-2019, 09:16:55
    Author     : gallenc
--%>
<%@page import="org.solent.com600.example.journeyplanner.model.UserInfo"%>
<%@page import="org.solent.com600.example.journeyplanner.model.SysUser"%>
<%@page import="java.util.List"%>
<%@page import="org.solent.com600.example.journeyplanner.model.Role"%>
<%@page import="org.solent.com600.example.journeyplanner.web.WebObjectFactory"%>
<%@page import="org.solent.com600.example.journeyplanner.model.ServiceFactory"%>
<%@page import="org.solent.com600.example.journeyplanner.model.ServiceFacade"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // generic code for all JSPs to set up session
    // error message string
    String errorMessage = "";

    ServiceFacade serviceFacade = (ServiceFacade) session.getAttribute("serviceFacade");

    // If the user session has no service facade, create a new one
    if (serviceFacade == null) {
        ServiceFactory serviceFactory = WebObjectFactory.getServiceFactory();
        serviceFacade = serviceFactory.getServiceFacade();
        session.setAttribute("ServiceFacade", serviceFacade);
    }

    String sessionUserName = (String) session.getAttribute("sessionUserName");
    if (sessionUserName == null) {
        sessionUserName = "anonymous";
    }

    Role sessionUserRole = (Role) session.getAttribute("sessionUserRole");
    if (sessionUserRole == null) {
        sessionUserRole = Role.ANONYMOUS;
    }

    // get request values
    String selected = (String) request.getParameter("selected");

    String action = (String) request.getParameter("action");

    String username = (String) request.getParameter("username");
    if (username == null) {
        username = "";
    }
    String password = (String) request.getParameter("password");
    if (password == null) {
        password = "";
    }

    String verifypassword = (String) request.getParameter("verifypassword");

    String firstname = (String) request.getParameter("firstname");
    if (firstname == null) {
        firstname = "";
    }
    String surname = (String) request.getParameter("surname");
    if (surname == null) {
        surname = "";
    }

    if (action == null || "".equals(action)) {
        // do nothing first time at page
    } else if ("addNewUser".equals(action)) {
        // add new user
        if (username.isEmpty()) {
            errorMessage = "Error - you must enter a username";
        } else if (password == null || password.length() < 8) {
            errorMessage = "Error - you must enter a password greater than 8 characters";
        } else if (!password.equals(verifypassword)) {
            errorMessage = "Error - the two password entries must be equal ";
        } else {
            Role role = serviceFacade.getRoleByUserName(username);
            if (role != null) {
                errorMessage = "Error - please choose another user name. user " + username + "already exists";
            } else {
                SysUser user = serviceFacade.createUser(username, password, firstname, surname, Role.RIDER, "admin");
                errorMessage = "SUCCESS created new user " + username;
                // reset form
                username = "";
                password = "";
                firstname = "";
                surname = "";
            }
        }
    } else {
        // unknown action
        errorMessage = "Error - unknown action called";
    }

    List<SysUser> userList = serviceFacade.retrieveAllUsers("admin");

%>
<!DOCTYPE html>
<html>
    <!-- header.jsp injected content -->
    <jsp:include page="header.jsp" />
    <!-- current jsp page content -->
    <!--BODY-->
    <div id="content">
        <!-- print error message if there is one -->
        <div style="color:red;"><%=errorMessage%></div>

        <div class="splitcontentleft">

            <% if (Role.ADMIN.equals(sessionUserRole) && "ManageUsers".equals(selected)) {%>
            <h2>Add New User</h2>
            <form action="listUsers.jsp?selected=ManageUsers" method=post>
                <p><strong>Create a new User Account: </strong>
                    <input type="text" name="username" size="25" value="<%=username%>">
                <p><strong>Please Enter A Password: </strong>
                    <input type="password" size="15" name="password">
                <p><strong>Please re-enter your password: </strong>
                    <input type="password" size="15" name="verifypassword">
                <p><strong>Please enter your first name: </strong>
                    <input type="text" size="25" name="firstname" value="<%=firstname%>">
                <p><strong>Please enter your surname: </strong>
                    <input type="text" size="25" name="surname" value="<%=surname%>">
                    <input type="hidden" name="action" value="addNewUser">
                    <input type="submit" value="Create New User">
                    <input type="reset" value="Reset">
            </form>
            <% } %>

        </div>



        <div class="splitcontentright">
            <h2>Users</h2>
            <!-- todo try scrolling table -->
            <!-- https://stackoverflow.com/questions/8232713/how-to-display-scroll-bar-onto-a-html-table -->
            <table>
                <tr>
                    <th>Username</th>
                    <th>Firstname</th>
                    <th>Lastname</th>
                    <th>Role</th>
                    <th></th>
                </tr>
                <%
                    for (SysUser user : userList) {
                        UserInfo userInfo = user.getUserInfo();
                %>
                <tr>
                    <th><%=user.getUserName()%></th>
                    <td><%=userInfo.getFirstname()%></td>
                    <td><%=userInfo.getSurname()%></td>
                    <td><%=user.getRole()%></td>
                    <td>
                        <form action="userInfo.jsp?selected=userInfo" method="get">
                            <input type="hidden" name="action" value="showUser">
                            <input type="hidden" name="selectedUserName" value="<%=user.getUserName()%>">
                            <input type="submit" value="Show User">
                        </form>
                        <% if (Role.ADMIN.equals(sessionUserRole)) {%>
                        <form action="userInfo.jsp?selected=userInfo" method="get">
                            <input type="hidden" name="action" value="updateUserRole">
                            <input type="hidden" name="role" value="<%=Role.DEACTIVATED%>">
                            <input type="hidden" name="selectedUserName" value="<%=user.getUserName()%>">
                            <input type="submit" value="Deactivate User">
                        </form>
                        <% }%>
                    </td>
                </tr>
                <%
                    }
                %>
            </table>
            <BR>
        </div>

    </div>
    <!-- footer.jsp injected content-->
    <jsp:include page="footer.jsp" />

    <!-- end of page -->
</html>