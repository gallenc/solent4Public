<%-- 
    Document   : UserInfo
    Created on : 26-Feb-2019, 09:18:13
    Author     : gallenc
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="org.solent.com600.example.journeyplanner.model.ProcessInfo"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>

<%@page import="org.solent.com600.example.journeyplanner.web.WebObjectFactory"%>
<%@page import="org.solent.com600.example.journeyplanner.model.ServiceFactory"%>
<%@page import="org.solent.com600.example.journeyplanner.model.ServiceFacade"%>
<%@page import="org.solent.com600.example.journeyplanner.model.SysUser"%>
<%@page import="org.solent.com600.example.journeyplanner.model.UserInfo"%>
<%@page import="org.solent.com600.example.journeyplanner.model.Address"%>
<%@page import="org.solent.com600.example.journeyplanner.model.Insurance"%>
<%@page import="org.solent.com600.example.journeyplanner.model.Role"%>


<%@page import="org.solent.com600.example.journeyplanner.model.Role"%>
<%@page import="org.solent.com600.example.journeyplanner.web.WebObjectFactory"%>
<%@page import="org.solent.com600.example.journeyplanner.model.ServiceFactory"%>
<%@page import="org.solent.com600.example.journeyplanner.model.ServiceFacade"%>

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
    if(sessionUserName==null) sessionUserName = "anonymous";
    
    Role sessionUserRole = (Role)  session.getAttribute("sessionUserRole");
    if(sessionUserRole==null) sessionUserRole = Role.ANONYMOUS;

    // get request values
    String action = (String) request.getParameter("action");
    String selectedUserName = (String) request.getParameter("selectedUserName");
    
    // this is just to test jsp display
    SysUser sysUser = serviceFacade.retrieveByUserName(selectedUserName, "admin");
    

    
//    String userName = "User_";
//    sysUser.setUserName(userName);
//
//    Address address = new Address();
//    address.setNumber("6");
//    address.setAddressLine1("Plane Avenue");
//    address.setAddressLine2("Oswestry");
//    address.setCountry("England");
//    address.setCounty("Hampshire");
//    address.setPostcode("HA23TV");
//    address.setLatitude(0);
//    address.setLongitude(0);
//    address.setMobile("0777444555444");
//
//    sysUser.getUserInfo().setAddress(address);
//
//    sysUser.getUserInfo().setEmergencyContactAddress(address);
//    sysUser.getUserInfo().setEmergencyContactFirstName("econtactfirstName_");
//    sysUser.getUserInfo().setEmergencyContactSurname("econtactSurname_");
//    sysUser.getUserInfo().setEmergencyContactRelationship("friend");
//
//    sysUser.getUserInfo().setFirstname("firstname_");
//    sysUser.getUserInfo().setSurname("surname_");
//
//    Insurance insurance = new Insurance();
//    insurance.setExpirydate(new Date());
//    insurance.setInsuranceNo("12345467");
//
//    sysUser.getUserInfo().setInsurance(insurance);
//    sysUser.getUserInfo().setMedicalMd("#test Markdown");
//    sysUser.getProcessInfo().setInsuranceVerified(Boolean.TRUE);
//
//    sysUser.setPassWordHash("XXX");
//    sysUser.setPassword("ZZZ");  // should not persist as transient
//    sysUser.setRole(Role.RIDER);

    UserInfo userInfo = sysUser.getUserInfo();
    ProcessInfo processInfo = sysUser.getProcessInfo();

    Address address = userInfo.getAddress();
    Address emergencyContactAddress = userInfo.getEmergencyContactAddress();
    Insurance insurance = userInfo.getInsurance();
    

// access
    String control = "disabled";
    String rolechangeControl = "";   //  String rolechangeControl = "disabled";
    String insuranceVerifiedControl = "disabled";

%>
<!DOCTYPE html>
<html>
    <!-- header.jsp injected content -->
    <%@include file="header.jsp"%>
    <!-- current jsp page content -->
    <!--BODY-->
    <div class="content"> 
        <!-- print error message if there is one -->
        <div style="color:red;"><%=errorMessage%></div>
        <BR>
        <h2>User Details for <%=sysUser.getUserName() %> </h2>
        <div class="splitcontentleft">
            <BR>
            <h2>Update Password</h2>
            <BR>
            <form action="userInfo.jsp?selected=userInfo" method="post">
                <table>
                    <tr><td>old password </td><td><input type="password" id="oldpass" name="oldpassword"></td></tr>
                    <tr><td>new password </td><td><input type="password" id="newpass" name="newpassword" minlength="8" required></td></tr>
                    <tr><td>verify new password</td><td><input type="password" id="newpassverify" name="verifypassword" minlength="8" required></td></tr>
                    <tr><td></td><td><input type="submit" value="Change Password"></td></tr>
                </table>
                <input type="hidden" name="action" value="changePassword">
            </form>
            <BR>
            <form action="userInfo.jsp?selected=userInfo" method="post">
                <h2>User Information</h2>
                <BR>
                <table>
                    <tr><td>role</td><td>
                            <select name="role" <%=rolechangeControl%> >
                                <option value="<%=Role.ANONYMOUS%>"><%=Role.ANONYMOUS%></option>
                                <option value="<%=Role.RIDER%>"><%=Role.RIDER%></option>
                                <option value="<%=Role.RIDELEADER%>"><%=Role.RIDELEADER%></option>
                                <option value="<%=Role.ADMIN%>"><%=Role.ADMIN%></option>
                            </select>
                        </td></tr>

                </table>
                <BR>
                <table>
                    <tr><td>username</td><td><input type="text" name="username" value ="<%=sysUser.getUserName()%>" <%=control%>  ></td></tr>
                    <tr><td>first name</td><td><input type="text" name="firstname" value ="<%=userInfo.getFirstname()%>" <%=control%>  ></td></tr>
                    <tr><td>surname</td><td><input type="text" name="surname" value ="<%=userInfo.getSurname()%>"<%=control%>  ></td></tr>
                </table>
                <BR>
                <h2>User Address</h2>
                <BR>
                <table>
                    <tr><td>house number</td><td><input type="text" name="number" value ="<%=address.getNumber()%>" <%=control%>  ></td></tr>
                    <tr><td>address line1</td><td><input type="text" name="addressline1" value ="<%=address.getAddressLine1()%>" <%=control%>  ></td></tr>
                    <tr><td>address line2</td><td><input type="text" name="addressline2" value ="<%=address.getAddressLine2()%>" <%=control%>  ></td></tr>
                    <tr><td>country</td><td><input type="text" name="country" value ="<%=address.getCountry()%>" <%=control%>  ></td></tr>
                    <tr><td>county</td><td><input type="text" name="county" value ="<%=address.getCounty()%>" <%=control%>  ></td></tr>
                    <tr><td>postcode</td><td><input type="text" name="postcode" value ="<%=address.getPostcode()%>" <%=control%>  ></td></tr>
                    <tr><td>latitude</td><td><input type="text" name="latitude" value ="<%=address.getLatitude()%>" <%=control%>  ></td></tr>
                    <tr><td>longitude</td><td><input type="text" name="longitude" value ="<%=address.getLongitude()%>" <%=control%>  ></td></tr>
                    <tr><td>mobile</td><td><input type="text" name="mobile" value ="<%=address.getMobile()%>" <%=control%>  ></td></tr>
                </table>
                <BR>
                </div>

                <div class="splitcontentright">
                    <BR>
                    <h2>Emergency Contact</h2>
                    <BR>
                    <table>
                        <tr><td>emergency contact first name</td><td><input type="text" name="emergencycontactfirstname" value ="<%=userInfo.getEmergencyContactFirstName()%>" <%=control%>  ></td></tr>
                        <tr><td>emergency contact surname</td><td><input type="text" name="emergencycontactsurname" value ="<%=userInfo.getEmergencyContactSurname()%>" <%=control%>  ></td></tr>
                        <tr><td>emergency contact relationship</td><td><input type="text" name="emergencycontactrelationship" value ="<%=userInfo.getEmergencyContactRelationship()%>" <%=control%>  ></td></tr>

                        <tr><td>house number</td><td><input type="text" name="number" value ="<%=emergencyContactAddress.getNumber()%>" <%=control%>  ></td></tr>
                        <tr><td>address line1</td><td><input type="text" name="addressline1" value ="<%=emergencyContactAddress.getAddressLine1()%>" <%=control%>  ></td></tr>
                        <tr><td>address line2</td><td><input type="text" name="addressline2" value ="<%=emergencyContactAddress.getAddressLine2()%>" <%=control%>  ></td></tr>
                        <tr><td>country</td><td><input type="text" name="country" value ="<%=emergencyContactAddress.getCountry()%>" <%=control%>  ></td></tr>
                        <tr><td>county</td><td><input type="text" name="county" value ="<%=emergencyContactAddress.getCounty()%>" <%=control%>  ></td></tr>
                        <tr><td>postcode</td><td><input type="text" name="postcode" value ="<%=emergencyContactAddress.getPostcode()%>" <%=control%>  ></td></tr>
                        <tr><td>latitude</td><td><input type="text" name="latitude" value ="<%=emergencyContactAddress.getLatitude()%>" <%=control%>  ></td></tr>
                        <tr><td>longitude</td><td><input type="text" name="longitude" value ="<%=emergencyContactAddress.getLongitude()%>" <%=control%>  ></td></tr>
                        <tr><td>mobile</td><td><input type="text" name="mobile" value ="<%=emergencyContactAddress.getMobile()%>" <%=control%>  ></td></tr>

                    </table>

                    <BR>
                    <h2>Medical and Insurance</h2>
                    <BR>
                    <table>
                        <tr><td>insurance policy number</td><td><input type="text" name="insuranceno" value ="<%=insurance.getInsuranceNo()%>" <%=control%>  ></td></tr>
                        <tr><td>insurance expiry date</td><td><input type="text" name="expirydate" value ="<%=insurance.getExpirydate()%>" <%=control%>  ></td></tr>
                        <tr><td>Additional medical Information</td><td><input type="text" name="medicalmd" value ="<%=userInfo.getMedicalMd()%>" <%=control%>  ></td></tr>
                        <tr><td>insurance verified</td><td>
                                <input type="checkbox" name="insuranceverified" value="true" <%=(processInfo.getInsuranceVerified()) ? "checked" : ""%>  <%=insuranceVerifiedControl%>  >
                                <label for="insuranceverified">Verified</label></td></tr>
                    </table>
                    <BR>
                </div>

                <input type="hidden" name="action" value="updateUserInfo">
                <input type="submit" value="update user information">
            </form>
            <form action="userInfo.jsp?selected=userInfo" method="post">
                <input type="hidden" name="action" value="cancel">
                <input type="submit" value="Cancel">
            </form>
        </div>

        <!-- footer.jsp injected content-->
        <%@include file="footer.jsp"%> 

        <!-- end of page -->
</html>

