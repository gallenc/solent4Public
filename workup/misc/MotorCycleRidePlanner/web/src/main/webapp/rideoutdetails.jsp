<%-- 
    Document   : listRideouts
    Created on : Feb 27, 2019, 7:00:43 PM
    Author     : cgallen
--%>
<%@page import="org.solent.com600.example.journeyplanner.model.RideoutState"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="org.solent.com600.example.journeyplanner.model.ItineraryItemType"%>
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
<%@page import="org.solent.com600.example.journeyplanner.model.ItineraryItem"%>
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

    Long rideoutId = 0L;
    Integer dayId = 0;
    Integer itemId = 0;

    String rideoutIdstr = (String) request.getParameter("rideoutId");
    try {
        if (rideoutIdstr != null) {
            rideoutId = new Long(Integer.parseInt(rideoutIdstr));
        }
    } catch (Exception e) {
        throw new RuntimeException("cannot parse parameter rideoutId=" + rideoutIdstr + " " + e.getMessage());
    }
    String dayIdstr = (String) request.getParameter("dayId");
    try {
        if (dayIdstr != null) {
            dayId = Integer.parseInt(dayIdstr);
        }
    } catch (Exception e) {
        throw new RuntimeException("cannot parse parameter dayId=" + dayIdstr + " " + e.getMessage());
    }
    String itemIdstr = (String) request.getParameter("itemId");
    try {
        if (itemIdstr != null) {
            itemId = Integer.parseInt(itemIdstr);
        }
    } catch (Exception e) {
        throw new RuntimeException("cannot parse parameter itemId=" + itemIdstr + " " + e.getMessage());
    }
    // access
    String inputControl = ""; //or disabled;

    Rideout rideout = new Rideout();

    // check actions
    if ("addNewRideout".equals(action)) {
        String newRideoutName = (String) request.getParameter("newRideoutName");
        rideout = new Rideout();
        rideout.setRideoutstate(RideoutState.PLANNING);
        rideout.setTitle(newRideoutName);
        rideout = serviceFacade.createRideout(rideout, sessionUserName);
        rideoutId = rideout.getId();
    } else if ("viewRideout".equals(action)) {
        rideout = serviceFacade.retrieveRideout(rideoutId, sessionUserName);
        if (rideout == null) {
            throw new RuntimeException("viewRideout rideout null for rideoutId " + rideoutId);
        }
        rideoutId = rideout.getId();
    } else if ("updateRideoutGeneralInfo".equals(action)) {
        rideout = serviceFacade.retrieveRideout(rideoutId, sessionUserName);
        if (rideout == null) {
            throw new RuntimeException("updateRideoutGeneralInfo rideout null for rideoutId" + rideoutId);
        }
        rideoutId = rideout.getId();

    } else if ("addNewRideoutDay".equals(action)) {
        rideout = serviceFacade.retrieveRideout(rideoutId, sessionUserName);
        if (rideout == null) {
            throw new RuntimeException("addNewRideoutDay rideout null for rideoutId " + rideoutId);
        }
        rideoutId = rideout.getId();
        RideoutDay newRideoutDay = new RideoutDay();
        rideout.getRideoutDays().add(newRideoutDay);
        rideout = serviceFacade.updateRideout(rideout, sessionUserName);

    } else if ("deleteRideoutDay".equals(action)) {
        rideout = serviceFacade.retrieveRideout(rideoutId, sessionUserName);
        if (rideout == null) {
            throw new RuntimeException("deleteRideoutDay rideout null for rideoutId " + rideoutId);
        }
        rideoutId = rideout.getId();

// todo service facade delete rideout day
    } else if ("insertRideoutItineraryItem".equals(action)) {
        rideout = serviceFacade.retrieveRideout(rideoutId, sessionUserName);
        if (rideout == null) {
            throw new RuntimeException("insertRideoutItineraryItem rideout null for rideoutId " + rideoutId);
        }

// todo service facade insert itineary item
    } else if ("deleteRideoutItineraryItem".equals(action)) {
        rideout = serviceFacade.retrieveRideout(rideoutId, sessionUserName);
        if (rideout == null) {
            throw new RuntimeException("deleteRideoutItineraryItem rideout null for rideoutId " + rideoutId);
        }

    } else {
        // unknown action
        errorMessage = "Error - unknown action called";
    }

    List<RideoutDay> days = rideout.getRideoutDays();

//    // dummy data
//    rideout.setTitle("my first rideout");
//    rideout.setStartDate(new Date());
//    rideout.setDescriptionMd("my first description ");
//
//    rideout.setMaxRiders(22);
//    List<RideoutDay> days = rideout.getRideoutDays();
//
//    for (int x = 0; x < 10; x++) {
//        RideoutDay day = new RideoutDay();
//        days.add(day);
//        day.setDescriptionMd("this is an amazing day " + (x + 1));
//        List<ItineraryItem> itineraryItems = day.getItineraryItems();
//        for (int i = 0; i < 6; i++) {
//            ItineraryItem item = new ItineraryItem();
//            itineraryItems.add(item);
//            item.setAddress(new Address());
//            item.setBookingReference("booking " + (x + 1) + "." + i);
//            item.setDescriptionMd("day " + (x + 1) + " item " + (i + 1));
//            item.setStartTime(new Date().toString());
//            item.setEndTime(new Date().toString());
//            item.setDistance(100D);
//            item.setGisRoute("gix");
//            item.setItineraryItemType(ItineraryItemType.JOURNEY);
//
//        }
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
        <div>
            <h2>Rideout Itinerary <%=rideout.getTitle()%></h2>
            <form action="listRideouts.jsp?selected=ManageRideouts" method="get">
                <input type="submit" value="Return to Rideout List">
            </form>
        </div>
        <BR>

        <div class="splitcontentleft" >
            <h2>General Information</h2>
            <form action="./rideoutdetails.jsp?selected=ManageRideouts" method="post">
                <table>
                    <tr>
                        <td>id </td><td><%=rideout.getId()%></td>
                    <tr>
                    <tr>
                        <td>title</td><td><input type="text" name="rideoutDescription" value ="<%=rideout.getTitle()%>" 
                                                 <%=inputControl%> ></td>
                    <tr>
                    <tr>
                        <td>description</td><td><input type="text" name="rideoutDescription" value ="<%=rideout.getDescriptionMd()%>" 
                                                       <%=inputControl%> ></td>
                    <tr>
                    <tr>
                        <td>start</td><td><input type="text" name="rideoutStartDate" value ="<%=df.format(rideout.getStartDate())%>" 
                                                      <%=inputControl%> ></td>
                    <tr>
                    <tr>
                        <td>Leader</td><td><%=rideout.getRideLeader()%></td>
                    <tr>
                    <tr>
                        <td>Spaces</td><td><input type="text" name="rideoutMaxRiders" value ="<%=rideout.getMaxRiders()%>" 
                                                                    <%=inputControl%> ></td>
                    <tr>
                    <tr>
                        <td>booked</td><td><%=rideout.getRiders().size()%></td>
                    <tr>
                    <tr>
                        <td>Wait-listed</td><td><%=rideout.getWaitlist().size()%></td>
                    <tr>
                    <tr>
                        <td>Status</td><td>
                            <select name="ridoutState" <%=inputControl%> >
                                <option value="<%=RideoutState.PLANNING%>" <%= (RideoutState.PLANNING.equals(rideout.getRideoutstate())) ? "selected" : ""%> ><%=RideoutState.PLANNING%></option>
                                <option value="<%=RideoutState.PUBLISHED%>" <%= (RideoutState.PUBLISHED.equals(rideout.getRideoutstate())) ? "selected" : ""%> ><%=RideoutState.PUBLISHED%></option>
                                <option value="<%=RideoutState.INPROGRESS%>" <%= (RideoutState.INPROGRESS.equals(rideout.getRideoutstate())) ? "selected" : ""%> ><%=RideoutState.INPROGRESS%></option>
                                <option value="<%=RideoutState.FINISHED%>" <%= (RideoutState.FINISHED.equals(rideout.getRideoutstate())) ? "selected" : ""%> ><%=RideoutState.FINISHED%></option>
                                <option value="<%=RideoutState.CANCELLED%>" <%= (RideoutState.CANCELLED.equals(rideout.getRideoutstate())) ? "selected" : ""%> ><%=RideoutState.CANCELLED%></option>
                            </select>
                        </td>
                    <tr>
                </table>

                <input type="hidden"  name="rideoutId" value ="<%=rideout.getId()%>" >
                <input type="hidden" name="action" value="updateRideoutGeneralInfo">
                <input type="submit" value="Update General Information" <%=inputControl%> >
            </form>
            <form action="./rideoutdetails.jsp?selected=ManageRideouts" method="post">
                <input type="hidden"  name="rideoutId" value ="<%=rideout.getId()%>" >
                <input type="hidden" name="action" value="updateRideoutRiders">
                <input type="submit" value="View and Update Booked Riders" <%=inputControl%> >
            </form>
            <br>
            <h2>Daily overview</h2>
            <form action="./rideoutdetails.jsp?selected=ManageRideouts" method="post">
                <input type="hidden" name="action" value="addNewRideoutDay">
                <input type="hidden"  name="rideoutId" value ="<%=rideout.getId()%>" >
                <input type="submit" value="Add New Day" <%=inputControl%> >
            </form>
            <br>
            <% for (int dayno = 1; dayno <= days.size(); dayno++) {
                    RideoutDay day = days.get(dayno - 1);
            %>
            <button class="accordion" id="<%=dayno%>">Day <%=dayno%></button>
            <div class="panel">
                <p><%=day.getDescriptionMd()%></p>
                <form action="./rideoutdetails.jsp?selected=ManageRideouts" method="post">
                    <input type="hidden"  name="rideoutId" value ="<%=rideout.getId()%>" >
                    <input type="hidden"  name="dayId" value ="<%=day.getId()%>" >
                    <input type="hidden" name="action" value="deleteRideoutDay">
                    <input type="submit" value="Delete Rideout Day <%=dayno%>" <%=inputControl%> >
                </form>
            </div>
            <% }%>

        </div>
        <div class="splitcontentright" >
            <h2>Detailed Day Itinerary</h2>

            <% for (int dayno = 1; dayno <= days.size(); dayno++) {
                    RideoutDay day = days.get(dayno - 1);
            %>
            <div id="day_<%=dayno%>">
                <% for (int itemno = 1; itemno <= day.getItineraryItems().size(); itemno++) {
                        ItineraryItem item = day.getItineraryItems().get(itemno - 1);

                %>
                <!-- <button class="accordion" id="<%=itemno + 100%>">Stage <%=itemno%></button> 
                <div class="panel">
                    <p><%=item.getDescriptionMd()%></p>
                </div> -->
                <a href="./rideoutdetails.jsp?selected=ManageRideouts" method="post" title="click to view or edit details">
                    <h3><%=item.getDescriptionMd()%></h3>
                    <table>
                        <tr><td>type</td><td><%=item.getItineraryItemType()%></td></tr>
                        <tr><td>description</td><td><%=item.getDescriptionMd()%></td></tr>
                        <tr><td>start time</td><td><%=item.getStartTime()%></td></tr>
                        <tr><td>end time</td><td><%=item.getEndTime()%></td></tr>
                    </table>
                </a>

                <form action="./rideoutdetails.jsp?selected=ManageRideouts" method="post">
                    <input type="hidden"  name="rideoutId" value ="<%=rideoutId%>" >
                    <input type="hidden"  name="dayId" value ="<%=day.getId()%>" >
                    <input type="hidden"  name="itemId" value ="<%=item.getId()%>" >
                    <input type="hidden" name="action" value="deleteRideoutItineraryItem">
                    <input type="submit" value="Delete Item <%=itemno%>" <%=inputControl%> >
                </form>
                <form action="./rideoutdetails.jsp?selected=ManageRideouts" method="post">
                    <input type="hidden"  name="rideoutId" value ="<%=rideoutId%>" >
                    <input type="hidden"  name="dayId" value ="<%=day.getId()%>" >
                    <input type="hidden"  name="itemId" value ="<%=item.getId()%>" >
                    <input type="hidden" name="action" value="insertRideoutItineraryItem">
                    <input type="submit" value="Insert New Item" <%=inputControl%> >
                </form>
                <br>
                <% }%>

            </div>
            <% }%>

        </div>
    </div>

    <!-- footer.jsp injected content-->
    <jsp:include page="footer.jsp" />

    <script>
        var acc = document.getElementsByClassName("accordion");
        var i;

        for (i = 0; i < acc.length; i++) {
            // hide all elements
            var y = i + 1;
            var item = document.getElementById("day_" + y);
            item.style.display = 'none';

            acc[i].addEventListener("click", function () {
                var p = this.id;
                /* close all other panels*/
                var x;
                for (x = 0; x < acc.length; x++) {
                    acc[x].classList.remove("active");
                    var panelx = acc[x].nextElementSibling;
                    panelx.style.maxHeight = null;
                    var y = x + 1;
                    var item = document.getElementById("day_" + y);
                    item.style.display = 'none';
                }
                /* open selected panel */
                var item2 = document.getElementById("day_" + p);
                item2.style.display = '';

                this.classList.toggle("active");

                var panel = this.nextElementSibling;

                if (panel.style.maxHeight) {
                    panel.style.maxHeight = null;
                } else {
                    panel.style.maxHeight = panel.scrollHeight + "px";
                }
            });

        }
        // display first element
        // simulate a click
        acc[0].click();
    </script>
</body>
<!-- end of page -->
</html>