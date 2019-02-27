<%-- 
    Document   : Login
    Created on : 26-Feb-2019, 09:13:24
    Author     : gallenc
--%>
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
    if(sessionUserName==null) sessionUserName = "anonymous";
    
    Role sessionUserRole = (Role)  session.getAttribute("sessionUserRole");
    if(sessionUserRole==null) sessionUserRole = Role.ANONYMOUS;

    // get request values
    String action = (String) request.getParameter("action");


%>
<!DOCTYPE html>
<html>
    <!-- header.jsp injected content -->
    <%@include file="header.jsp"%>
    <!-- current jsp page content -->
    <!--BODY-->
    <div id="content">
        <!-- print error message if there is one -->
        <div style="color:red;"><%=errorMessage%></div>
        <div class="splitcontentleft">
            <h2>example.jsp</h2>
            <br>

            <h2>Hello, please log in:</h2>
            <br><br>
            <form action="j_security_check" method=post>
                <p><strong>Please Enter Your User Name: </strong>
                    <input type="text" name="j_username" size="25">
                <p><p><strong>Please Enter Your Password: </strong>
                    <input type="password" size="15" name="j_password">
                <p><p>
                    <input type="submit" value="Submit">
                    <input type="reset" value="Reset">
            </form>
        </div>
        <div class="splitcontentright">
            <p>a little text</p>
        </div>
    </div>

    <div id="subcontent">
        <div class="small box">
            <strong>A little box</a> </strong>
        </div>
        <br><h3>Favorite Links</h3>
    </div>

    <!-- footer.jsp injected content-->
    <%@include file="footer.jsp"%> 

    <!-- end of page -->
</html>
