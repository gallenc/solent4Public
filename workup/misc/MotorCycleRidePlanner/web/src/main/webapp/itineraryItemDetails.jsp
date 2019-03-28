<%-- 
    Document   : itinerary
    Created on : Feb 27, 2019, 7:01:46 PM
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
<%@page import="org.solent.com600.example.journeyplanner.web.RidoutJspConstantsHelper"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // generic code for all JSPs to set up session
    // date translation utilities
    String DATE_FORMAT = "dd/MM/YYYY";
    DateFormat df = new SimpleDateFormat(DATE_FORMAT);

    // error message string
    String errorMessage = "";
    boolean error = false;

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
    if (action == null) {
        action = "";
    }

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

    // default if not called
    ItineraryItem item = new ItineraryItem();
    Rideout rideout = null;
    Address address = null;
    ItineraryItemType itype = null;

    // find item
    rideout = serviceFacade.retrieveRideout(rideoutId, sessionUserName);
    if (rideout == null) {
        errorMessage = "viewRideout rideout null for rideoutId " + rideoutId;
    } else {
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
                item = days.get(dindex).getItineraryItems().get(iindex);
                address = item.getAddress();
                itype = item.getItineraryItemType();
            }
        }
    }

    // access
    String inputControl = ""; //or disabled;

    if (RidoutJspConstantsHelper.UPDATE_RIDEOUT_ITINERARY_ITEM_ACTION.equals(action)) {
        item = RidoutJspConstantsHelper.updateItinearyItem(item, request);
        rideout = serviceFacade.updateRideout(rideout, sessionUserName);
    }


%>
<!DOCTYPE html>
<html>
    <!-- header.jsp injected content -->
    <jsp:include page="header.jsp" />
    <!-- current jsp page content -->
    <!--BODY-->
    <div id="content">
        <h2>Rideout <%=rideout.getTitle()%> Day <%=dayIndex%> Itinerary <%=itemIndex%></h2>
        <br>

        <div class="splitcontentleft">
            <form action="./rideoutdetails.jsp?tabSelected=ManageRideouts" method="post">
                <input type="hidden"  name="rideoutId" value ="<%=rideout.getId()%>" >
                <input type="hidden"  name="dayIndex" value ="<%=dayIndex%>" >
                <input type="hidden"  name="itemIndex" value ="<%=itemIndex%>" >
                <input type="hidden" name="action" value="<%=RidoutJspConstantsHelper.VIEW_RIDEOUT_ACTION%>">
                <input type="submit" value="Return to Rideout" <%=inputControl%> >
            </form>
            
        </div>
        <div class="splitcontentright">

            <h3><%=item.getDescriptionMd()%></h3>
            <form action="./itineraryItemDetails.jsp?tabSelected=ManageRideouts" method="post">
                <input type="hidden"  name="rideoutId" value ="<%=rideout.getId()%>" >
                <input type="hidden"  name="dayIndex" value ="<%=dayIndex%>" >
                <input type="hidden"  name="itemIndex" value ="<%=itemIndex%>" >
                <input type="hidden" name="action" value="<%=RidoutJspConstantsHelper.UPDATE_RIDEOUT_ITINERARY_ITEM_ACTION%>">
                <input type="submit" value="Update Item" <%=inputControl%> >
                <table>
                    <tr><td>item no</td><td><%=itemIndex%></td></tr>
                    <tr>
                        <td>Type</td><td>
                            <select name="<%=RidoutJspConstantsHelper.ITINERARY_ITEM_TYPE%>" <%=inputControl%> >
                                <option value="<%=ItineraryItemType.ACCOMODATION%>" <%= (ItineraryItemType.ACCOMODATION.equals(item.getItineraryItemType())) ? "selected" : ""%> ><%=ItineraryItemType.ACCOMODATION%></option>
                                <option value="<%=ItineraryItemType.FERRY%>" <%= (ItineraryItemType.FERRY.equals(item.getItineraryItemType())) ? "selected" : ""%> ><%=ItineraryItemType.FERRY%></option>
                                <option value="<%=ItineraryItemType.JOURNEY%>" <%= (ItineraryItemType.JOURNEY.equals(item.getItineraryItemType())) ? "selected" : ""%> ><%=ItineraryItemType.JOURNEY%></option>
                                <option value="<%=ItineraryItemType.STOP%>" <%= (ItineraryItemType.STOP.equals(item.getItineraryItemType())) ? "selected" : ""%> ><%=ItineraryItemType.STOP%></option>
                            </select>
                        </td>
                    </tr>

                    <tr><td>description</td><td><input type="text" name="<%=RidoutJspConstantsHelper.ITINEARY_ITEM_DESCRIPTION_MD%>" value ="<%=item.getDescriptionMd()%>" <%=inputControl%> ></td></tr>
                    <tr> 
                        <td>start time(HH:MM)</td>
                        <td>
                            <div>
                                <input type="text" name="endTime" value="<%=item.getStartTime()%>" placeholder="Time" data-toggle="timepicker"  <%=inputControl%> >
                            </div>
                        </td> 
                    </tr>
                    <tr> 
                        <td>end time(HH:MM)</td>
                        <td>
                            <div>
                                <input type="text" name="endTime" value="<%=item.getEndTime()%>" placeholder="Time" data-toggle="timepicker"  <%=inputControl%> >
                            </div>
                        </td> 
                    </tr>
                    <!--   <tr><td>start time</td><td><%=item.getStartTime()%></td></tr> -->
                    <!--   <tr><td>end time</td><td><%=item.getEndTime()%></td></tr> -->

                    <tr><td>booking reference</td><td><input type="text" name="<%=RidoutJspConstantsHelper.ITINEARY_ITEM_BOOKING_REFERENCE%>" value ="<%=item.getBookingReference()%>" <%=inputControl%> ></td></tr>
                    <tr><td>distance</td><td><input type="text" name="<%=RidoutJspConstantsHelper.ITINERARY_ITEM_DISTANCE%>" value ="<%=item.getDistance()%>" <%=inputControl%> ></td></tr>

                </table>



                <h2>Destination Address</h2>
                <BR>
                <table>
                    <tr><td>house number</td><td><input type="text" name="<%=RidoutJspConstantsHelper.NUMBER%>" value ="<%=address.getNumber()%>" <%=inputControl%>  ></td></tr>
                    <tr><td>address line1</td><td><input type="text" name="<%=RidoutJspConstantsHelper.ADDRESS_LINE_1%>" value ="<%=address.getAddressLine1()%>" <%=inputControl%>  ></td></tr>
                    <tr><td>address line2</td><td><input type="text" name="<%=RidoutJspConstantsHelper.ADDRESS_LINE_2%>" value ="<%=address.getAddressLine2()%>" <%=inputControl%>  ></td></tr>
                    <tr><td>country</td><td><input type="text" name="<%=RidoutJspConstantsHelper.COUNTRY%>" value ="<%=address.getCountry()%>" <%=inputControl%>  ></td></tr>
                    <tr><td>county</td><td><input type="text" name="<%=RidoutJspConstantsHelper.COUNTY%>" value ="<%=address.getCounty()%>" <%=inputControl%>  ></td></tr>
                    <tr><td>postcode</td><td><input type="text" name="<%=RidoutJspConstantsHelper.POSTCODE%>" value ="<%=address.getPostcode()%>" <%=inputControl%>  ></td></tr>
                    <tr><td>latitude</td><td><input type="text" name="<%=RidoutJspConstantsHelper.LATITUDE%>" value ="<%=address.getLatitude()%>" <%=inputControl%>  ></td></tr>
                    <tr><td>longitude</td><td><input type="text" name="<%=RidoutJspConstantsHelper.LONGITUDE%>" value ="<%=address.getLongitude()%>" <%=inputControl%>  ></td></tr>
                    <tr><td>mobile</td><td><input type="text" name="<%=RidoutJspConstantsHelper.MOBILE%>" value ="<%=address.getMobile()%>" <%=inputControl%>  ></td></tr>
                    <tr><td>telephone</td><td><input type="text" name="<%=RidoutJspConstantsHelper.TELEPHONE%>" value ="<%=address.getTelephone()%>" <%=inputControl%>  ></td></tr>
                </table>

            </form>
        </div>

    </div>
    <!-- footer.jsp injected content-->
    <jsp:include page="footer.jsp" />

    <!-- end of page -->
</html>