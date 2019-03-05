<%-- 
    Document   : ListUsers
    Created on : 26-Feb-2019, 09:16:55
    Author     : gallenc
--%>
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
    String action = (String) request.getParameter("action");

    List<SysUser> userList = serviceFacade.retrieveAllUsers("admin");

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



        </div>



        <div class="splitcontentright">
            <h2>Users</h2>
            <!-- todo try scrolling table -->
            <!-- https://stackoverflow.com/questions/8232713/how-to-display-scroll-bar-onto-a-html-table -->
            <table>
                <tr>
                    <th>Username</th>
                    <th>Firstname</th>
                    <th>Lastname</th>
                    <th>Role</th>
                    <th></th>
                </tr>
                <%
                    for (SysUser user : userList) {
                        UserInfo userInfo = user.getUserInfo();
                %>
                <tr>
                    <th><%=user.getUserName()%></th>
                    <td><%=userInfo.getFirstname()%></td>
                    <td><%=userInfo.getSurname()%></td>
                    <td><%=user.getRole()%></td>
                    <td>
                        <form action="userInfo.jsp?selected=userInfo" method="get">
                            <input type="hidden" name="action" value="showUser">
                            <input type="hidden" name="selectedUserName" value="<%=user.getUserName()%>">
                            <input type="submit" value="Show User">
                        </form>
                        <% if (Role.ADMIN.equals(sessionUserRole)) {%>
                        <form action="userInfo.jsp?selected=userInfo" method="get">
                            <input type="hidden" name="action" value="deactivateUser">
                            <input type="hidden" name="selectedUserName" value="<%=user.getUserName()%>">
                            <input type="submit" value="Deactivate User">
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

    </div>
    <!-- footer.jsp injected content-->
    <jsp:include page="footer.jsp" />

    <!-- end of page -->
</html>