<%-- 
    Document   : Login
    Created on : 26-Feb-2019, 09:13:24
    Author     : gallenc
--%>
<%@page import="org.solent.com600.example.journeyplanner.model.SysUser"%>
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
        sessionUserName = sessionUserName;
        session.setAttribute("sessionUserName", sessionUserName);
    }

    Role sessionUserRole = (Role) session.getAttribute("sessionUserRole");
    if (sessionUserRole == null) {
        sessionUserRole = Role.ANONYMOUS;
        session.setAttribute("sessionUserRole", sessionUserRole);
    }

    // get request values
    String action = (String) request.getParameter("action");
    String username = (String) request.getParameter("username");
    if (username == null) {
        username = "";
    }
    String password = (String) request.getParameter("password");
    if (password == null) {
        password = "";
    }

    String password2 = (String) request.getParameter("password2");

    String firstname = (String) request.getParameter("firstname");
    if (firstname == null) {
        firstname = "";
    }
    String surname = (String) request.getParameter("surname");
    if (surname == null) {
        surname = "";
    }
    if(action==null || "".equals(action)){
        // do nothing first tiem at page
    } else if ("login".equals(action)) {
        // log user in
        boolean authenticated = false;
        if (username.isEmpty()) {
            errorMessage = "Error - you must enter a username";
        } else {
            try {
                authenticated = serviceFacade.checkPasswordByUserName(password, username, username);
            } catch (Exception ex) {
                errorMessage = "Error - you must enter a username" + ex.getMessage();
            }
        }
        if (authenticated){
            session.setAttribute("sessionUserName", username);
            Role role = serviceFacade.getRoleByUserName(username);
            session.setAttribute("sessionUserRole", role);
        }

    } else if ("addNewUser".equals(action)) {
        // add new user
        if (username.isEmpty()) {
            errorMessage = "Error - you must enter a username";
        } else if (password == null || password.length() < 8) {
            errorMessage = "Error - you must enter a password greater than 8 characters";
        } else if (!password.equals(password2)) {
            errorMessage = "Error - the two password entries must be equal ";
        } else {
            Role role = serviceFacade.getRoleByUserName(username);
            if (role!=null) {
                errorMessage = "Error - please choose another user name. user "+username+"already exists";
            } else {
               SysUser user = serviceFacade.createUser(username, password, firstname, surname, "admin");
               session.setAttribute("sessionUserName", username);
               session.setAttribute("sessionUserRole", user.getRole());
            }
        }
    } else {
        // unknown action
        errorMessage = "Error - unknown action called";
    }


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
            <h2>Login Page</h2>
            <br>

            <h2>If you have an account please log in:</h2>
            <br><br>
            <!--   <form action="j_security_check" method=post>-->
            <form action="login.jsp" method=post>
                <p><strong>Please Enter Your User Name: </strong>
                    <input type="text" name="username" size="25">
                <p><strong>Please Enter Your Password: </strong>
                    <input type="password" size="15" name="password">
                <p>
                    <input type="hidden" name="action" value="login">
                    <input type="submit" value="Login">
                    <input type="reset" value="Reset">
            </form>
        </div>
        <div class="splitcontentright">
            <form action="login.jsp" method=post>
                <p><strong>Create a new User Account: </strong>
                    <input type="text" name="username" size="25" value="<%=username%>">
                <p><p><strong>Please Enter A Password: </strong>
                    <input type="password" size="15" name="password">
                <p><p><strong>Please re-enter your password: </strong>
                    <input type="password" size="15" name="password2">
                <p><p>
                <p><p><strong>Please enter your first name: </strong>
                    <input type="text" size="25" name="firstname" value="<%=firstname%>">
                <p><p>
                <p><p><strong>Please enter your surname: </strong>
                    <input type="text" size="25" name="surname" value="<%=surname%>">
                <p><p>
                    <input type="hidden" name="action" value="addNewUser">
                    <input type="submit" value="Create New User">
                    <input type="reset" value="Reset">
            </form>
        </div>
    </div>

    <div id="subcontent">
    </div>

    <!-- footer.jsp injected content-->
    <jsp:include page="footer.jsp" />

    <!-- end of page -->
</html>
