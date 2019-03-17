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
    String selected = (String) request.getParameter("selected");

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
    Rideout rideout = new Rideout();
    Address address = new Address();
    ItineraryItemType itype = ItineraryItemType.STOP;

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

    if ("updateItinearyItem".equals(action)) {

        address = new Address();

        String itemDescriptionMd = (String) request.getParameter("itemDescriptionMd");

        String number = (String) request.getParameter("number");

        // address
        if (number != null) {
            address.setNumber(number);
        }
        String addressline1 = (String) request.getParameter("addressline1");
        if (addressline1 != null) {
            address.setAddressLine1(addressline1);
        }
        String addressline2 = (String) request.getParameter("addressline2");
        if (addressline2 != null) {
            address.setAddressLine2(addressline2);
        }
        String country = (String) request.getParameter("country");
        if (country != null) {
            address.setCountry(country);
        }
        String county = (String) request.getParameter("county");
        if (county != null) {
            address.setCounty(county);
        }
        String postcode = (String) request.getParameter("postcode");
        if (postcode != null) {
            address.setPostcode(postcode);
        }
        String latitude = (String) request.getParameter("latitude");
        if (latitude != null) {
            try {
                address.setLatitude(Double.parseDouble(latitude));
            } catch (Exception ex) {
                errorMessage = "cannot parse address latitude as double " + ex.getMessage();
                error = true;
            }
        }
        String longitude = (String) request.getParameter("longitude");
        if (longitude != null) {
            try {
                address.setLongitude(Double.parseDouble(longitude));
            } catch (Exception ex) {
                errorMessage = "cannot parse address longitude as double " + ex.getMessage();
                error = true;
            }
        }
        String mobile = (String) request.getParameter("mobile");
        if (mobile != null) {
            address.setMobile(mobile);
        }
        String telephone = (String) request.getParameter("telephone");
        if (telephone != null) {
            address.setTelephone(telephone);
        }

        String bookingReference = "reference";
        Double distance = 100D;
        String endTime = "12:10";
        String startTime = "12:10";
        String gisRoute = "asdasdasd";
        itemDescriptionMd = "first item description";

        item.setAddress(address);
        item.setDescriptionMd(itemDescriptionMd);
        item.setBookingReference(bookingReference);
        item.setDistance(distance);

        item.setEndTime(endTime);

        item.setGisRoute(gisRoute);
        ItineraryItemType itineraryItemType = ItineraryItemType.ACCOMODATION;
        item.setItineraryItemType(itineraryItemType);

        item.setStartTime(startTime);
    }


%>
<!DOCTYPE html>
<html>
    <!-- header.jsp injected content -->
    <jsp:include page="header.jsp" />
    <!-- current jsp page content -->
    <!--BODY-->
    <div id="content">
        <h2>Itinerary</h2>
        <br>

        <div class="splitcontentleft">


        </div>
        <div class="splitcontentright">

            <h3><%=item.getDescriptionMd()%></h3>
            <table>
                <tr><td>item no</td><td><%=itemIndex %></td></tr>
                <tr><td>type</td><td><%=item.getItineraryItemType()%></td></tr>
                <tr><td>description</td><td><%=item.getDescriptionMd()%></td></tr>
                <tr> 
                    <td>start time(HH:MM)</td>

                    <td>
                        <div>
                            <input type="text" name="endTime" value="<%=item.getStartTime()%>" placeholder="Time" data-toggle="timepicker">
                        </div>
                    </td> 
                </tr>
                <tr> 
                    <td>end time(HH:MM)</td>
                    <td>
                        <div>
                            <input type="text" name="endTime" value="<%=item.getEndTime()%>" placeholder="Time" data-toggle="timepicker">
                        </div>
                    </td> 
                </tr>
             <!--   <tr><td>start time</td><td><%=item.getStartTime()%></td></tr> -->
            <!--    <tr><td>end time</td><td><%=item.getEndTime()%></td></tr> -->
                <tr><td>booking reference</td><td><%=item.getBookingReference()%></td></tr>
                <tr><td>distance</td><td><%=item.getDistance()%></td></tr>
            </table>



            <h2>Destination Address</h2>
            <BR>
            <table>
                <tr><td>house number</td><td><input type="text" name="number" value ="<%=address.getNumber()%>" <%=inputControl%>  ></td></tr>
                <tr><td>address line1</td><td><input type="text" name="addressline1" value ="<%=address.getAddressLine1()%>" <%=inputControl%>  ></td></tr>
                <tr><td>address line2</td><td><input type="text" name="addressline2" value ="<%=address.getAddressLine2()%>" <%=inputControl%>  ></td></tr>
                <tr><td>country</td><td><input type="text" name="country" value ="<%=address.getCountry()%>" <%=inputControl%>  ></td></tr>
                <tr><td>county</td><td><input type="text" name="county" value ="<%=address.getCounty()%>" <%=inputControl%>  ></td></tr>
                <tr><td>postcode</td><td><input type="text" name="postcode" value ="<%=address.getPostcode()%>" <%=inputControl%>  ></td></tr>
                <tr><td>latitude</td><td><input type="text" name="latitude" value ="<%=address.getLatitude()%>" <%=inputControl%>  ></td></tr>
                <tr><td>longitude</td><td><input type="text" name="longitude" value ="<%=address.getLongitude()%>" <%=inputControl%>  ></td></tr>
                <tr><td>mobile</td><td><input type="text" name="mobile" value ="<%=address.getMobile()%>" <%=inputControl%>  ></td></tr>
                <tr><td>telephone</td><td><input type="text" name="telephone" value ="<%=address.getTelephone()%>" <%=inputControl%>  ></td></tr>
            </table>
        </div>

    </div>
    <!-- footer.jsp injected content-->
    <jsp:include page="footer.jsp" />

    <!-- end of page -->
</html>