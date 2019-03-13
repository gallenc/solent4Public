<%-- 
    Document   : header.jsp
    Created on : Feb 27, 2019, 7:07:29 PM
    Author     : cgallen
--%>

<%@page import="org.solent.com600.example.journeyplanner.model.Role"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String logout = (String) request.getParameter("logout");
    if (logout != null) {
        session.setAttribute("sessionUserRole", Role.ANONYMOUS);
        session.setAttribute("sessionUserName", "anonymous");
        response.sendRedirect("./mainPage.jsp");
    }

    String sessionUserName = (String) session.getAttribute("sessionUserName");
    if (sessionUserName == null) {
        sessionUserName = "anonymous";
        session.setAttribute("sessionUserName", sessionUserName);
    }

    Role sessionUserRole = (Role) session.getAttribute("sessionUserRole");
    if (sessionUserRole == null) {
        sessionUserRole = Role.ANONYMOUS;
        session.setAttribute("sessionUserRole", sessionUserRole);
    }

    String selected = (String) request.getParameter("selected");

%>
<!-- content from header.jsp -->
<head>
    <title>Motorcycle Club Main</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <meta name="description" content="entimoss website" />
    <meta name="keywords" content="motorcycle ride tour" />
    <link rel="stylesheet" href="./css/andreas08.css" type="text/css" />
    <!-- opengraph protocol -->
    <meta property="og:type" content="website" />
    <meta property="og:title" content="motorcycle rideout" />
    <meta property="og:description" content="Rideout site" />
</head>
<body>
    <div id="container">
        <div id="header">
            <table>
                <tr>
                    <td>
                        <h1 style="text-align: justify;">Motor Cycle Rideout</h1>
                        <h2>Rides with a difference...</h2>
                    </td>
                    <td>
                        <div style="text-align: right;">
                            <%                                if (Role.ANONYMOUS.equals(sessionUserRole)) {
                            %>
                            <h2>Not Logged In</h2>
                            <form action="login.jsp" method="post">
                                <input type="submit" value="Login">
                            </form>
                            <%
                            } else {
                            %>
                            <h2>Username: <%=sessionUserName%></h2>
                            <h2>Role    : <%=sessionUserRole%></h2>
                            <form action="mainPage.jsp" method="post">
                                <input type="hidden" name="logout" value="logout">
                                <input type="submit" value="Log Out">
                            </form>
                            <%
                                }
                            %>
                        </div>
                    </td>
                </tr>
            </table>


        </div>
        <div id="navigation">
            <ul>
                <li <%=("Main".equals(selected) ? "class=\"selected\"" : "")%> >
                    <a href="./mainPage.jsp?selected=Main">Main</a></li>

                <% if (!Role.ANONYMOUS.equals(sessionUserRole)) {%>
                <li <%=("MyProfile".equals(selected) ? "class=\"selected\"" : "")%> >
                    <a href="./userInfo.jsp?selected=MyProfile&action=myProfile&selectedUserName=<%=sessionUserName%>">My Profile</a></li>
                <li <%=("ManageRideouts".equals(selected) ? "class=\"selected\"" : "")%> >
                    <a href="./listRideouts.jsp?selected=ManageRideouts">Manage Rideouts</a></li>
                
                <% if (Role.ADMIN.equals(sessionUserRole)) {%>
                <li <%=("ManageUsers".equals(selected) ? "class=\"selected\"" : "")%> > 
                    <a href="./listUsers.jsp?selected=ManageUsers">Manage Users</a></li>

                <% }%>
                <% }%>
                <li <%=("About".equals(selected) ? "class=\"selected\"" : "")%> >
                    <a href="./about.jsp?selected=About">About</a></li>
            </ul>
        </div>
        <!-- end of  content from header.jsp -->
        <!-- main JSP content will go here -->
