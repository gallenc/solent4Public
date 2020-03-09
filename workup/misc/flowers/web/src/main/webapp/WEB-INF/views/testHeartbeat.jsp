<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="org.solent.com504.project.model.flower.service.ServiceFacade"%>


<%
    // used to place error message at top of page 
    String errorMessage = "";
    String message = "";

    // used to set html header autoload time. This automatically refreshes the page
    // Set refresh, autoload time every 20 seconds
    response.setIntHeader("Refresh", 20);

    // accessing service 
    ServiceFacade serviceFacade = (ServiceFacade) request.getAttribute("serviceFacade");

    // accessing request parameters
    String actionStr = request.getParameter("action");

    // basic error checking before making a call
    if (actionStr == null || actionStr.isEmpty()) {
        // do nothing

    } else if ("XXX".equals(actionStr)) {
        // put your actions here
    } else {
        errorMessage = "ERROR: page called for unknown action";
    }

%>

<c:set var = "selectedPage" value = "testHeartbeat" scope="request" />
<!-- start of home.jsp selectedPage=${selectedPage}-->
<jsp:include page="header.jsp" />
<!-- Begin page content -->
<main role="main" class="container">
        <!-- works with http://localhost:8080/basicfacadeweb/testHeartbeat.jsp -->
        <H1>JSP Server Page for heart beat</H1>
        <!-- print error message if there is one -->
        <div style="color:red;"><%=errorMessage%></div>
        <div style="color:green;"><%=message%></div>

        <p>The time is: <%= new Date().toString()%> (note page is auto refreshed every 20 seconds)</p>

        <p>Getting heartbeat message: <%= serviceFacade.getHeartbeat()%> (note message is auto refreshed every 20 seconds)</p>

    </body>
</main>

<jsp:include page="footer.jsp" />
