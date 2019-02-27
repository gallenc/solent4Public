<%-- 
    Document   : header.jsp
    Created on : Feb 27, 2019, 7:07:29 PM
    Author     : cgallen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
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
                <h1 style="text-align: justify;">Motor Cycle Rideout</h1>
                <h2>Rides with a difference...</h2>

            </div>
            <div id="navigation">
                <ul>
                    <li <%=("Main".equals(selected)? "class=\"selected\"" : "")%> >
                        <a href="./mainPage.jsp?selected=Main">Main</a></li>
                    <li <%=("MyProfile".equals(selected)? "class=\"selected\"" : "")%> >
                        <a href="./userInfo.jsp?selected=MyProfile&action=MyProfile">My Profile</a></li>
                    <li <%=("ManageUsers".equals(selected)? "class=\"selected\"" : "")%> > 
                        <a href="./listUsers.jsp?selected=ManageUsers">Manage Users</a></li>
                    <li <%=("ManageRideouts".equals(selected)? "class=\"selected\"" : "")%> >
                        <a href="./listRideouts.jsp?selected=ManageRideouts">Manage Rideouts</a></li>
                    <li <%=("About".equals(selected)? "class=\"selected\"" : "")%> >
                        <a href="./about.jsp?selected=About">About</a></li>
                </ul>
            </div>
 <!-- end of  content from header.jsp -->
 <!-- main JSP content will go here -->
