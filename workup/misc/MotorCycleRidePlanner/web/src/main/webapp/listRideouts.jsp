<%-- 
    Document   : listRideouts
    Created on : Feb 27, 2019, 7:00:43 PM
    Author     : cgallen
--%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@page import="org.solent.com600.example.journeyplanner.model.RideoutState"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="org.solent.com600.example.journeyplanner.model.RideoutDay"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.solent.com600.example.journeyplanner.model.Rideout"%>
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

    // date translation utilities
    String DATE_FORMAT = "dd/mm/yyyy";
    DateFormat df = new SimpleDateFormat(DATE_FORMAT);

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

    Set<RideoutState> selectedStates = (Set<RideoutState>) session.getAttribute("sessionSelectedStates");
    if (selectedStates==null){
        selectedStates = new HashSet<RideoutState>(Arrays.asList(RideoutState.values()));
        session.setAttribute("sessionSelectedStates", selectedStates);
    }
    
    
    if ("changeSelectedStates".equals(action)) {
        // get selected rideout values
        selectedStates = new HashSet<RideoutState>();
        for (RideoutState state : RideoutState.values()) {
            errorMessage = errorMessage+ " DEBUG: "+ state.toString(); 
            String st = (String) request.getParameter(state.toString());
            errorMessage = errorMessage+" ST "+st; 
            if (st != null) {
                selectedStates.add(state);
            }
        }
        session.setAttribute("sessionSelectedStates", selectedStates);
    }

    List<Rideout> rideouts = serviceFacade.retrieveAllRideouts(new ArrayList(selectedStates), sessionUserName);

    //String action = (String) request.getParameter("action");
    // if ()
//    SysUser user = serviceFacade.retrieveByUserName(sessionUserName, sessionUserName);
//    for (int i = 0; i < 10; i++) {
//        Rideout rideout = new Rideout();
//        rideout.setTitle("my first rideout");
//        rideout.setStartDate(new Date());
//        rideout.setDescriptionMd("my first description ");
//        rideout.setMaxRiders(22);
//        rideout.setRideLeader(user);
//        rideout.getRiders().add(user);
//        rideout.getWaitlist().add(user);
//        rideout.getRideoutDays().add(new RideoutDay());
//        rideouts.add(rideout);
//    }

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
        <h2>Rideouts</h2>
        <div>
            <form action="listRideouts.jsp?selected=ManageRideouts" method="post">
                <% for (RideoutState state : RideoutState.values()) {
                %>
                <input type="checkbox" name="<%=state%>" <%=((selectedStates.contains(state)) ? "checked" : "")%> 
                       onChange="this.form.submit()">
                <label for="<%=state%>"><%=state.toString().toLowerCase()%></label>
                <% }%>
                <input type="hidden" name="action" value="changeSelectedStates">
            </form>
        </div>
        <BR>
        <% if (Role.ADMIN.equals(sessionUserRole)) {%>
        <form action="rideoutdetails.jsp?selected=ManageRideouts" method="get">
            <input type="text" name="newRideoutName" value ="" required >
            <input type="hidden" name="action" value="addNewRideout">
            <input type="submit" value="Add New Rideout">
        </form>
        <% }%>

        <!-- todo try scrolling table -->
        <!-- https://stackoverflow.com/questions/8232713/how-to-display-scroll-bar-onto-a-html-table -->
        <table>
            <tr>
                <th>Date</th>
                <th>Title</th>
                <th>Duration</th>
                <th>Leader</th>
                <th>Spaces</th>
                <th>Booked</th>
                <th>Waitlist</th>
                <th>Status</th>
                <th>Id</th>
                <th></th>
            </tr>
            <%
                for (Rideout rideout : rideouts) {
            %>
            <tr>
                <td><%=df.format(rideout.getStartDate())%></td>
                <td><%=rideout.getTitle()%></td>
                <td><%=rideout.getRideoutDays().size()%> Days</td>
                <td><%=( (rideout.getRideLeader()==null) ? "" : rideout.getRideLeader().getUserInfo().getFirstname()+" "+ rideout.getRideLeader().getUserInfo().getSurname() )%></td>
                <td><%=rideout.getMaxRiders()%></td>
                <td><%=rideout.getRiders().size()%></td>
                <td><%=rideout.getWaitlist().size()%></td>
                <td><%=rideout.getRideoutstate()%></td>
                <td><%=rideout.getId()%></td>
                <td>
                    <form action="./rideoutdetails.jsp?selected=ManageRideouts" method="get">
                        <input type="hidden" name="action" value="viewRideout">
                        <input type="hidden"  name="rideoutId" value ="<%=rideout.getId()%>" >
                        <input type="submit" value="Details">
                    </form>
                    <% if (Role.ADMIN.equals(sessionUserRole)) {%>
                    <form action="./listRidouts.jsp?selected=ManageRideouts" method="get">
                        <input type="hidden" name="action" value="deleteRideout">
                        <input type="hidden"  name="rideoutId" value ="<%=rideout.getId()%>" >
                        <input type="submit" value="Delete">
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

    <!-- footer.jsp injected content-->
    <jsp:include page="footer.jsp" />

    <!-- end of page -->
</html>