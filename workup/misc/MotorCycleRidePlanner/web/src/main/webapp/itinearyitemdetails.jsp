<%-- 
    Document   : itineary
    Created on : Feb 27, 2019, 7:01:46 PM
    Author     : cgallen
--%>
<%@page import="org.solent.com600.example.journeyplanner.model.RideoutState"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="org.solent.com600.example.journeyplanner.model.ItinearyItemType"%>
<%@page import="java.util.Date"%>
<%@page import="org.solent.com600.example.journeyplanner.model.UserInfo"%>
<%@page import="org.solent.com600.example.journeyplanner.model.SysUser"%>
<%@page import="java.util.List"%>
<%@page import="org.solent.com600.example.journeyplanner.model.Role"%>
<%@page import="org.solent.com600.example.journeyplanner.web.WebObjectFactory"%>
<%@page import="org.solent.com600.example.journeyplanner.model.ServiceFactory"%>
<%@page import="org.solent.com600.example.journeyplanner.model.ServiceFacade"%>
<%@page import="org.solent.com600.example.journeyplanner.model.Rideout"%>
<%@page import="org.solent.com600.example.journeyplanner.model.RideoutDay"%>
<%@page import="org.solent.com600.example.journeyplanner.model.ItinearyItem"%>
<%@page import="org.solent.com600.example.journeyplanner.model.Address"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // generic code for all JSPs to set up session
    // date translation utilities
    String DATE_FORMAT = "dd/mm/yyyy";
    DateFormat df = new SimpleDateFormat(DATE_FORMAT);

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

    Integer rideoutId = 0;
    Integer dayId = 0;
    Integer itemId = 0;

    try {
        String rideoutIdstr = (String) request.getParameter("rideoutId");
        if (rideoutIdstr != null) {
            rideoutId = Integer.parseInt(rideoutIdstr);
        }

        String dayIdstr = (String) request.getParameter("dayId");
        if (dayIdstr != null) {
            dayId = Integer.parseInt(dayIdstr);
        }

        String itemIdstr = (String) request.getParameter("itemId");
        if (itemIdstr != null) {
            itemId = Integer.parseInt(itemIdstr);
        }
    } catch (Exception e) {
        errorMessage = "cannot parse parameter " + e.getMessage();
    }
    // access
    String inputControl = ""; //or disabled;
    
    ItinearyItem itinearyItem = new ItinearyItem();

    
%>
<!DOCTYPE html>
<html>
<!-- header.jsp injected content -->
    <jsp:include page="header.jsp" />
<!-- current jsp page content -->
            <!--BODY-->
            <div id="content">
                <h2>Itineary</h2>
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