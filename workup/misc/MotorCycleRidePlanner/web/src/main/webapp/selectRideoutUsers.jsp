<%-- 
    Document   : ListUsers
    Created on : 26-Feb-2019, 09:16:55
    Author     : gallenc
--%>
<%@page import="java.util.Arrays"%>
<%@page import="org.solent.com600.example.journeyplanner.web.RidoutJspConstantsHelper"%>
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
    if (action == null) {
        action = "";
    }

    String[] userListSelection = request.getParameterValues("userListSelection");
    if (userListSelection == null) {
        userListSelection = new String[0];
    }

    Long rideoutId = 0L;

    String rideoutIdstr = (String) request.getParameter("rideoutId");
    try {
        if (rideoutIdstr != null) {
            rideoutId = new Long(Integer.parseInt(rideoutIdstr));
        }
    } catch (Exception e) {
        throw new RuntimeException("cannot parse parameter rideoutId=" + rideoutIdstr + " " + e.getMessage());
    }

    Rideout rideout = serviceFacade.retrieveRideout(rideoutId, sessionUserName);
    lease = serviceFacade.userHasLeaseOnRideout(rideout.getId(), sessionUserName);

    if (rideout == null) {
        throw new RuntimeException("rideout null for rideoutId " + rideoutId);
    }

    try {

        if (action == null || "".equals(action)) {
            // do nothing first time at page
        } else if (RidoutJspConstantsHelper.VIEW_RIDERS.equals(action)) {
            // just show data
        } else if (RidoutJspConstantsHelper.ADD_RIDE_LEADER.equals(action)) {
            String userName = userListSelection[0];
            serviceFacade.addRideLeaderToRideout(rideoutId, userName, sessionUserName);

        } else if (RidoutJspConstantsHelper.ADD_TO_RIDERS.equals(action)) {
            serviceFacade.addRidersToRideout(rideoutId, Arrays.asList(userListSelection), sessionUserName);

        } else if (RidoutJspConstantsHelper.ADD_TO_WAIT_LIST.equals(action)) {
            serviceFacade.addRidersToWaitList(rideoutId, Arrays.asList(userListSelection), sessionUserName);
        } else if (RidoutJspConstantsHelper.REMOVE_RIDERS.equals(action)) {
            serviceFacade.removeRidersFromRideout(rideoutId, Arrays.asList(userListSelection), sessionUserName);

        } else if (RidoutJspConstantsHelper.REMOVE_FROM_WAIT_LIST.equals(action)) {
            serviceFacade.removeRidersFromWaitList(rideoutId, Arrays.asList(userListSelection), sessionUserName);

        } else if (RidoutJspConstantsHelper.MOVE_TO_RIDERS.equals(action)) {
            serviceFacade.transferRidersFromWaitList(rideoutId, Arrays.asList(userListSelection), sessionUserName);
        } else if (RidoutJspConstantsHelper.MOVE_TO_WAIT_LIST.equals(action)) {
            serviceFacade.transferRidersToWaitList(rideoutId, Arrays.asList(userListSelection), sessionUserName);
        }else {
            // unknown action
            errorMessage = "Error - unknown action called " + action;
        }
    } catch (Exception ex) {
        errorMessage = errorMessage + "Error: " + ex.toString();
    }

    List<SysUser> userList = serviceFacade.retrieveAllUsers(sessionUserName);
    List<SysUser> rideoutWaitlist = rideout.getWaitlist();
    List<SysUser> riders = rideout.getRiders();
    SysUser rideLeader = rideout.getRideLeader();
    if (rideLeader == null) {
        rideLeader = new SysUser();
    }

    String inputControl = "";

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
            <form action="./rideoutdetails.jsp?tabSelected=ManageRideouts" method="post">
                <input type="hidden"  name="rideoutId" value ="<%=rideout.getId()%>" >
                <input type="hidden" name="action" value="<%=RidoutJspConstantsHelper.VIEW_RIDEOUT_ACTION%>">
                <input type="submit" value="Return to Rideout"  >
            </form>
            <BR>
            <table>
                <tr><td>Ride Leader </td></tr>
                <tr><td><%=rideLeader.getUserInfo().getFirstname()%> </td></tr>
                <tr><td><%=rideLeader.getUserInfo().getSurname()%> </td></tr>
                <tr><td><%=rideLeader.getUserName()%> </d</td></tr>
            </table>
        </div>



        <div class="splitcontentright">
            <form action="./selectRideoutUsers.jsp?tabSelected=ManageRideouts" method="post" <%=inputControl%> >
                <h2>Rideout Riders</h2>
                <select name="action" <%=inputControl%> >
                    <option value="<%=RidoutJspConstantsHelper.MOVE_TO_WAIT_LIST %>" selected>Move to Wait List</option>
                    <option value="<%=RidoutJspConstantsHelper.REMOVE_RIDERS %>" >Delete From Riders</option>
                </select>
                <input type="hidden"  name="rideoutId" value ="<%=rideoutId%>" >
                <input type="submit" value="Enter">
                <table>
                    <tr>
                        <th>selected</th>
                        <th>Firstname</th>
                        <th>Lastname</th>
                        <th>Username</th>
                        <th>Role</th>
                    </tr>
                    <%
                        for (SysUser user : riders) {
                            UserInfo userInfo = user.getUserInfo();
                    %>
                    <tr>
                        <td>
                            <input type="checkbox" name="userListSelection" value="<%=user.getUserName()%>">
                        </td>
                        <td><%=userInfo.getFirstname()%></td>
                        <td><%=userInfo.getSurname()%></td>
                        <th><%=user.getUserName()%></th>
                        <td><%=user.getRole()%></td>
                    </tr>
                    <%
                        }
                    %>
                </table>
            </form>
            <BR>
            <form action="./selectRideoutUsers.jsp?tabSelected=ManageRideouts" method="post" <%=inputControl%> >
                <h2>Rideout Wait List</h2>
                <select name="action" <%=inputControl%> >
                    <option value="<%=RidoutJspConstantsHelper.MOVE_TO_RIDERS %>" selected>Move to Riders List</option>
                    <option value="<%=RidoutJspConstantsHelper.REMOVE_FROM_WAIT_LIST %>" >Remove From Wait List</option>
                </select>
                <input type="hidden"  name="rideoutId" value ="<%=rideoutId%>" >
                <input type="submit" value="Enter">
                <table>
                    <tr>
                        <th>selected</th>
                        <th>Firstname</th>
                        <th>Lastname</th>
                        <th>Username</th>
                        <th>Role</th>
                    </tr>
                    <%
                        for (SysUser user : rideoutWaitlist) {
                            UserInfo userInfo = user.getUserInfo();
                    %>
                    <tr>
                        <td>
                            <input type="checkbox" name="userListSelection" value="<%=user.getUserName()%>">
                        </td>
                        <td><%=userInfo.getFirstname()%></td>
                        <td><%=userInfo.getSurname()%></td>
                        <th><%=user.getUserName()%></th>
                        <td><%=user.getRole()%></td>
                    </tr>
                    <%
                        }
                    %>
                </table>
            </form>
            <BR>
            <form action="./selectRideoutUsers.jsp?tabSelected=ManageRideouts" method="post" <%=inputControl%> >
                <h2>All Users</h2>
                <select name="action" <%=inputControl%> >
                    <option value="<%=RidoutJspConstantsHelper.ADD_TO_WAIT_LIST %>" selected>Add to Wait List</option>
                    <option value="<%=RidoutJspConstantsHelper.ADD_RIDE_LEADER %>" >Add Ride Leader</option>
                    <option value="<%=RidoutJspConstantsHelper.ADD_TO_RIDERS %>" >Add To Riders</option>
                </select>
                <input type="hidden"  name="rideoutId" value ="<%=rideoutId%>" >
                <input type="submit" value="Enter">
                <table>
                    <tr>
                        <th>selected</th>
                        <th>Firstname</th>
                        <th>Lastname</th>
                        <th>Username</th>
                        <th>Role</th>
                    </tr>
                    <%
                        for (SysUser user : userList) {
                            UserInfo userInfo = user.getUserInfo();
                    %>
                    <tr>
                        <td>
                            <input type="checkbox" name="userListSelection" value="<%=user.getUserName()%>">
                        </td>
                        <td><%=userInfo.getFirstname()%></td>
                        <td><%=userInfo.getSurname()%></td>
                        <th><%=user.getUserName()%></th>
                        <td><%=user.getRole()%></td>
                    </tr>
                    <%
                        }
                    %>
                </table>
            </form>

        </div>

    </div>
    <!-- footer.jsp injected content-->
    <jsp:include page="footer.jsp" />

    <!-- end of page -->
</html>