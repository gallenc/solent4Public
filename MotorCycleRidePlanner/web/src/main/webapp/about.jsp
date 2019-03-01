<%-- 
    Document   : example.jsp
    Created on : Feb 27, 2019, 7:11:17 PM
    Author     : cgallen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.solent.com600.example.journeyplanner.model.Role"%>
<%@page import="org.solent.com600.example.journeyplanner.web.WebObjectFactory"%>
<%@page import="org.solent.com600.example.journeyplanner.model.ServiceFactory"%>
<%@page import="org.solent.com600.example.journeyplanner.model.ServiceFacade"%>

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
    <jsp:include page="header.jsp" />
<!-- current jsp page content -->
            <!--BODY-->
            <div id="content">
                <h2>About</h2>
                <br>
            </div>
            <div class="splitcontentright">
                <p>a little text</p>
            </div>

            <div id="subcontent">
                <div class="small box">
                    <strong>A little box</a> </strong>
                </div>
                <br><h3>Favorite Links</h3>
            </div>
            
<!-- footer.jsp injected content-->
<jsp:include page="footer.jsp" />

<!-- end of page -->
</html>
