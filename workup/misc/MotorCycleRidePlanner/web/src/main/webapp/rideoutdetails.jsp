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
    String DATE_FORMAT = "dd/MM/YYYY";
    DateFormat df = new SimpleDateFormat(DATE_FORMAT);

    // error message string
    String errorMessage = "";
    String goodMessage = "";
    boolean error = false;
    boolean lease = false;

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
    String tabSelected = (String) request.getParameter("tabSelected");

    String action = (String) request.getParameter("action");

    Long rideoutId = 0L;
    Integer dayIndex = 0;
    Integer itemIndex = 0;

    String rideoutIdstr = (String) request.getParameter("rideoutId");
    try {
        if (rideoutIdstr != null) {
            rideoutId = new Long(Integer.parseInt(rideoutIdstr));
        }
    } catch (Exception e) {
        throw new RuntimeException("cannot parse parameter rideoutId=" + rideoutIdstr + " " + e.getMessage());
    }
    String dayIndexstr = (String) request.getParameter("dayIndex");
    try {
        if (dayIndexstr != null) {
            dayIndex = Integer.parseInt(dayIndexstr);
        }
    } catch (Exception e) {
        throw new RuntimeException("cannot parse parameter dayIndex=" + dayIndexstr + " " + e.getMessage());
    }
    String itemIndexstr = (String) request.getParameter("itemIndex");
    try {
        if (itemIndexstr != null) {
            itemIndex = Integer.parseInt(itemIndexstr);
        }
    } catch (Exception e) {
        throw new RuntimeException("cannot parse parameter itemIndex=" + itemIndexstr + " " + e.getMessage());
    }

    // default rideout with no information so page renders
    Rideout rideout = new Rideout();
    // access
    String inputControl = ""; //or disabled;

    // check actions
    if ("addNewRideout".equals(action)) {
        String newRideoutName = (String) request.getParameter("newRideoutName");
        rideout = new Rideout();
        rideout.setRideoutstate(RideoutState.PLANNING);
        rideout.setTitle(newRideoutName);
        rideout = serviceFacade.createRideout(rideout, sessionUserName);
        rideoutId = rideout.getId();
        goodMessage = " new rideout " + rideout.getTitle() + " added";

    } else {
        rideout = serviceFacade.retrieveRideout(rideoutId, sessionUserName);
        if (rideout == null) {
            throw new RuntimeException("rideout null for rideoutId " + rideoutId);
        }
        rideoutId = rideout.getId();
        lease = serviceFacade.userHasLeaseOnRideout(rideoutId, sessionUserName);

        if ("viewRideout".equals(action)) {
            // just view

        } else if ("enableEditRideout".equals(action)) {
            lease = serviceFacade.tryGetLeaseOnRideout(rideoutId, sessionUserName);
            if (lease) {
                goodMessage = "Success you have obtained a lease to edit this rideout.";
            } else {
                errorMessage = "Error - you could not obtain lease on rideout. Rideout being edited by " + rideout.getLeaseUsername();
            }

        } else if ("disableEditRideout".equals(action)) {
            serviceFacade.tryReleaseLeaseOnRideout(rideoutId, sessionUserName);
            lease = false;
            goodMessage = "Success you have released lease on this rideout.";

        } else if ("updateRideoutGeneralInfo".equals(action)) {
            String rideoutTitle = (String) request.getParameter("rideoutTitle");
            if (rideoutTitle != null) {
                rideout.setTitle(rideoutTitle);
            }
            String rideoutDescriptionMD = (String) request.getParameter("rideoutDescription");
            if (rideoutDescriptionMD != null) {
                rideout.setDescriptionMd(rideoutDescriptionMD);
            }
            String rideoutMaxRidersStr = (String) request.getParameter("rideoutMaxRiders");
            if (rideoutMaxRidersStr != null) {
                try {
                    Integer maxriders = Integer.parseInt(rideoutMaxRidersStr);
                    rideout.setMaxRiders(maxriders);
                } catch (Exception ex) {
                    error = true;
                    errorMessage = errorMessage + "cannot parse rideoutMaxRiders to integer ";
                }
            }
            String ridoutStateStr = (String) request.getParameter("ridoutState");
            if (ridoutStateStr != null) {
                try {
                    RideoutState rideoutstate = RideoutState.valueOf(ridoutStateStr);
                    rideout.setRideoutstate(rideoutstate);
                } catch (Exception ex) {
                    error = true;
                    errorMessage = errorMessage + "cannot parse rideout state " + ridoutStateStr + " " + ex.toString();
                }
            }
            String rideoutStartDateStr = (String) request.getParameter("rideoutStartDate");
            if (rideoutStartDateStr != null) {
                try {
                    Date rideoutStartDate = df.parse(rideoutStartDateStr);
                    rideout.setStartDate(rideoutStartDate);
                } catch (Exception ex) {
                    error = true;
                    errorMessage = errorMessage + "cannot parse date "
                            + rideoutStartDateStr + " to format " + DATE_FORMAT + " " + ex.toString();
                }
            }
            if (!error) {
                rideout = serviceFacade.updateRideout(rideout, sessionUserName);
                goodMessage = "rideout " + rideout.getTitle() + " general information updated";
            } else {
                // if there is an error get original back
                rideout = serviceFacade.retrieveRideout(rideoutId, sessionUserName);
            }

        } else if ("addNewRideoutDay".equals(action)) {
            try {
                RideoutDay newRideoutDay = new RideoutDay();
                ItineraryItem firstItineraryItem = new ItineraryItem();
                firstItineraryItem.setDescriptionMd("todo - update");
                newRideoutDay.getItineraryItems().add(firstItineraryItem);
                rideout.getRideoutDays().add(newRideoutDay);
                rideout = serviceFacade.updateRideout(rideout, sessionUserName);
                goodMessage = goodMessage + "Success - rideout " + rideoutId + " added new rideout day";
            } catch (Exception ex) {
                errorMessage = errorMessage + "Problem adding new rideout day" + ex.getMessage();
            }

        } else if ("deleteRideoutDay".equals(action)) {
            try {
                // note must be int or wrong remove(object) called
                int dindex = dayIndex - 1;
                if (dindex < 0 || dindex > rideout.getRideoutDays().size() - 1) {
                    errorMessage = errorMessage + " Error cannot delete rideout day index "
                            + dayIndex + " out of range for rideoutData " + rideoutId;
                } else {
                    // debug test
                    rideout.getRideoutDays().remove(dindex);
                    rideout = serviceFacade.updateRideout(rideout, sessionUserName);

                    goodMessage = goodMessage + "Success - rideout " + rideoutId + " ridout day " + dayIndex;
                }
            } catch (Exception ex) {
                errorMessage = errorMessage + "Problem deleting rideout day" + ex.getMessage();
            }

        } else if ("insertRideoutItineraryItem".equals(action) || "deleteRideoutItineraryItem".equals(action)) {
            try {
                int dindex = dayIndex - 1;
                int iindex = itemIndex - 1;
                List<RideoutDay> days = rideout.getRideoutDays();
                if (dindex < 0 || dindex > days.size() - 1) {
                    errorMessage = errorMessage + " Error cannot find rideout day index "
                            + dayIndex + " out of range for rideoutData " + rideoutId;
                } else {
                    List<ItineraryItem> itineraryItems = days.get(dindex).getItineraryItems();
                    if (iindex < 0 || iindex >= itineraryItems.size()) {
                        errorMessage = errorMessage + " Error cannot find item " + itemIndex
                                + " for rideout day index " + dayIndex + " out of range for rideoutData " + rideoutId;
                    } else {
                        if ("deleteRideoutItineraryItem".equals(action)) {
                            itineraryItems.remove(iindex);
                            rideout = serviceFacade.updateRideout(rideout, sessionUserName);
                            goodMessage = goodMessage + "Success - rideout " + rideoutId + "  delete itinerary Item " + itemIndex;
                        } else {
                            ItineraryItem itineraryItem = new ItineraryItem();
                            itineraryItems.add(iindex, itineraryItem);
                            rideout = serviceFacade.updateRideout(rideout, sessionUserName);
                            goodMessage = goodMessage + "Success - rideout " + rideoutId + "  added new itinerary Item";
                        }
                    }
                }
            } catch (Exception ex) {
                errorMessage = errorMessage + " Error problem inserting rideout day in " + rideoutId + " " + ex.getMessage();
            }

        } else {
            // unknown action
            errorMessage = "Error - unknown action called";
        }
    }

    List<RideoutDay> days = rideout.getRideoutDays();

    if (lease) {
        inputControl = ""; //or disabled;
    } else {
        inputControl = "disabled";
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
        <div style="color:green;"><%=goodMessage%></div>
        <div class="splitcontentleft" >
            <div>
                <h2>Rideout Itinerary <%=rideout.getTitle()%></h2>
                <form action="listRideouts.jsp?tabSelected=ManageRideouts" method="get">
                    <input type="submit" value="Return to Rideout List">
                </form>
                <%if (!lease) {%>
                <form action="./rideoutdetails.jsp?tabSelected=ManageRideouts" method="get">
                    <input type="hidden" name="action" value="enableEditRideout">
                    <input type="hidden"  name="rideoutId" value ="<%=rideoutId%>" >
                    <input type="submit" value="Enable Edit Rideout">
                </form>
                <form action="./selectRideoutUsers.jsp?tabSelected=ManageRideouts" method="get">
                    <input type="hidden" name="action" value="viewRiders">
                    <input type="hidden"  name="rideoutId" value ="<%=rideoutId%>" >
                    <input type="submit" value="View Riders">
                </form>
                <% } else {%>
                <form action="./rideoutdetails.jsp?tabSelected=ManageRideouts" method="get">
                    <input type="hidden" name="action" value="disableEditRideout">
                    <input type="hidden"  name="rideoutId" value ="<%=rideoutId%>" >
                    <input type="submit" value="Disable Edit Rideout">
                </form>
                <form action="./selectRideoutUsers.jsp?tabSelected=ManageRideouts" method="get">
                    <input type="hidden" name="action" value="editRiders">
                    <input type="hidden"  name="rideoutId" value ="<%=rideoutId%>" >
                    <input type="submit" value="View Riders">
                </form>
                <% }%>
            </div>
            <BR>
            <h2>General Information</h2>
            <form action="./rideoutdetails.jsp?tabSelected=ManageRideouts" method="post">
                <table>
                    <tr>
                        <td>id </td><td><%=rideout.getId()%></td>
                    <tr>
                    <tr>
                        <td>title</td><td><input type="text" name="rideoutTitle" value ="<%=rideout.getTitle()%>" 
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
                        <td>Leader</td>
                        <% if (rideout.getRideLeader() != null) {%>
                        <td><%=rideout.getRideLeader().getUserInfo().getFirstname() + " " + rideout.getRideLeader().getUserInfo().getSurname()%></td>
                        <%
                        } else {
                        %>
                        <td></td>
                        <%
                            }
                        %>
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
            <form action="./rideoutdetails.jsp?tabSelected=ManageRideouts" method="post">
                <input type="hidden"  name="rideoutId" value ="<%=rideout.getId()%>" >
                <input type="hidden" name="action" value="updateRideoutRiders">
                <input type="submit" value="View and Update Booked Riders" <%=inputControl%> >
            </form>
        </div>
        <div class="splitcontentright" >
            <h2>Daily overview</h2>
            <form action="./rideoutdetails.jsp?tabSelected=ManageRideouts" method="post">
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
                <form action="./rideoutdetails.jsp?tabSelected=ManageRideouts" method="post" onsubmit="return confirm('are you sure you want to delete a whole day?')">
                    <input type="hidden"  name="rideoutId" value ="<%=rideout.getId()%>" >
                    <input type="hidden"  name="dayIndex" value ="<%=dayno%>" >
                    <input type="hidden" name="action" value="deleteRideoutDay">
                    <input type="submit" value="Delete Rideout Day <%=dayno%>" <%=inputControl%> >
                </form>
            </div>
            <% }%>

        </div>

    </div>
    <div class="subcontent" >
        <BR>
        <BR>
        <% for (int dayno = 1; dayno <= days.size(); dayno++) {
                RideoutDay day = days.get(dayno - 1);
        %>
        <div id="day_<%=dayno%>">
            <p>Detailed Day <%=dayno%> Itinerary</p>
            <% for (int itemno = 1; itemno <= day.getItineraryItems().size(); itemno++) {
                    ItineraryItem item = day.getItineraryItems().get(itemno - 1);
            %>
            <input type="hidden"  name="rideoutId" value ="<%=rideoutId%>" >
            <input type="hidden"  name="dayIndex" value ="<%=dayno%>" >
            <input type="hidden"  name="itemIndex" value ="<%=itemno%>" >
            <div>
                <a href="./itineraryItemDetails.jsp?tabSelected=ManageRideouts&rideoutId=<%=rideoutId%>&dayIndex=<%=dayno%>&itemIndex=<%=itemno%>"
                   method="get" title="click to view or edit details">
                    <table>
                        <tr><td><%=item.getItineraryItemType()%></td><td> <%=item.getStartTime()%> to <%=item.getEndTime()%></td></tr>
                        <tr><td><%=item.getDescriptionMd()%></td><td></td></tr>
                    </table>
                </a>

                <form action="./rideoutdetails.jsp?tabSelected=ManageRideouts" method="post" 
                      onsubmit="return confirm('are you sure?')">
                    <input type="hidden"  name="rideoutId" value ="<%=rideoutId%>" >
                    <input type="hidden"  name="dayIndex" value ="<%=dayno%>" >
                    <input type="hidden"  name="itemIndex" value ="<%=itemno%>" >
                    <input type="hidden" name="action" value="deleteRideoutItineraryItem">
                    <input type="submit" value="Delete Item <%=itemno%>" <%=inputControl%> >
                </form>
                <form action="./rideoutdetails.jsp?tabSelected=ManageRideouts" method="post">
                    <input type="hidden"  name="rideoutId" value ="<%=rideoutId%>" >
                    <input type="hidden"  name="dayIndex" value ="<%=dayno%>" >
                    <input type="hidden"  name="itemIndex" value ="<%=itemno%>" >
                    <input type="hidden" name="action" value="insertRideoutItineraryItem">
                    <input type="submit" value="Insert New Item" <%=inputControl%> >
                </form>
                <br>
            </div>
            <% }%>

        </div>
        <% }%>

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
                /* open tabSelected panel */
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