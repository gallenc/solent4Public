<%-- 
    Document   : UserInfo
    Created on : 26-Feb-2019, 09:18:13
    Author     : gallenc
--%>
<%@page import="org.solent.com600.example.journeyplanner.model.ProcessInfo"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.solent.com600.example.journeyplanner.web.WebObjectFactory"%>
<%@page import="org.solent.com600.example.journeyplanner.model.ServiceFactory"%>
<%@page import="org.solent.com600.example.journeyplanner.model.ServiceFacade"%>
<%@page import="org.solent.com600.example.journeyplanner.model.SysUser"%>
<%@page import="org.solent.com600.example.journeyplanner.model.UserInfo"%>
<%@page import="org.solent.com600.example.journeyplanner.model.Address"%>
<%@page import="org.solent.com600.example.journeyplanner.model.Insurance"%>
<%@page import="org.solent.com600.example.journeyplanner.model.Role"%>


<%
    // this is just to test jsp display
    SysUser sysUser = new SysUser();
    String userName = "User_";
    sysUser.setUserName(userName);

    Address address = new Address();
    address.setNumber("6");
    address.setAddressLine1("Plane Avenue");
    address.setAddressLine2("Oswestry");
    address.setCountry("England");
    address.setCounty("Hampshire");
    address.setPostcode("HA23TV");
    address.setLatitude(0);
    address.setLongitude(0);
    address.setMobile("0777444555444");

    sysUser.getUserInfo().setAddress(address);

    sysUser.getUserInfo().setEmergencyContactAddress(address);
    sysUser.getUserInfo().setEmergencyContactFirstName("econtactfirstName_");
    sysUser.getUserInfo().setEmergencyContactSurname("econtactSurname_");
    sysUser.getUserInfo().setEmergencyContactRelationship("friend");

    sysUser.getUserInfo().setFirstname("firstname_");
    sysUser.getUserInfo().setSurname("surname_");

    Insurance insurance = new Insurance();
    insurance.setExpirydate(new Date());
    insurance.setInsuranceNo("12345467");

    sysUser.getUserInfo().setInsurance(insurance);
    sysUser.getUserInfo().setMedicalMd("#test Markdown");
    sysUser.getProcessInfo().setInsuranceVerified(Boolean.TRUE);

    sysUser.setPassWordHash("XXX");
    sysUser.setPassword("ZZZ");  // should not persist as transient
    sysUser.setRole(Role.RIDER);

    UserInfo userInfo = sysUser.getUserInfo();
    ProcessInfo processInfo = sysUser.getProcessInfo();
    String readonly = "readonly";
    Address emergencyContactAddress = userInfo.getEmergencyContactAddress();

%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Info</title>
    </head>
    <body>
        <h1>User Information</h1>
        <table>
            <tr><td>username</td><td><input type="text" name="username" value ="<%=sysUser.getUserName()%>" <%=readonly%>  ></td></tr>
            <tr><td>first name</td><td><input type="text" name="firstname" value ="<%=userInfo.getFirstname()%>" <%=readonly%>  ></td></tr>
            <tr><td>surname</td><td><input type="text" name="surname" value ="<%=userInfo.getSurname()%>" <%=readonly%>  ></td></tr>
            <tr><td>role</td><td><input type="text" name="role" value ="<%=sysUser.getRole()%>" <%=readonly%>  ></td></tr>
        </table>
        <h1>User Address</h1>
        <table>
            <tr><td>house number</td><td><input type="text" name="number" value ="<%=address.getNumber()%>" <%=readonly%>  ></td></tr>
            <tr><td>address line1</td><td><input type="text" name="addressline1" value ="<%=address.getAddressLine1()%>" <%=readonly%>  ></td></tr>
            <tr><td>address line2</td><td><input type="text" name="addressline2" value ="<%=address.getAddressLine2()%>" <%=readonly%>  ></td></tr>
            <tr><td>country</td><td><input type="text" name="country" value ="<%=address.getCountry()%>" <%=readonly%>  ></td></tr>
            <tr><td>county</td><td><input type="text" name="county" value ="<%=address.getCounty()%>" <%=readonly%>  ></td></tr>
            <tr><td>postcode</td><td><input type="text" name="postcode" value ="<%=address.getPostcode()%>" <%=readonly%>  ></td></tr>
            <tr><td>latitude</td><td><input type="text" name="latitude" value ="<%=address.getLatitude()%>" <%=readonly%>  ></td></tr>
            <tr><td>longitude</td><td><input type="text" name="longitude" value ="<%=address.getLongitude()%>" <%=readonly%>  ></td></tr>
            <tr><td>mobile</td><td><input type="text" name="mobile" value ="<%=address.getMobile()%>" <%=readonly%>  ></td></tr>
        </table>
        <h1>Emergency Contact</h1>
        <table>
            <tr><td>emergency contact first name</td><td><input type="text" name="emergencycontactfirstname" value ="<%=userInfo.getEmergencyContactFirstName()%>" <%=readonly%>  ></td></tr>
            <tr><td>emergency contact surname</td><td><input type="text" name="emergencycontactsurname" value ="<%=userInfo.getEmergencyContactSurname()%>" <%=readonly%>  ></td></tr>
            <tr><td>emergency contact relationship</td><td><input type="text" name="emergencycontactrelationship" value ="<%=userInfo.getEmergencyContactRelationship()%>" <%=readonly%>  ></td></tr>

            <tr><td>house number</td><td><input type="text" name="number" value ="<%=emergencyContactAddress.getNumber()%>" <%=readonly%>  ></td></tr>
            <tr><td>address line1</td><td><input type="text" name="addressline1" value ="<%=emergencyContactAddress.getAddressLine1()%>" <%=readonly%>  ></td></tr>
            <tr><td>address line2</td><td><input type="text" name="addressline2" value ="<%=emergencyContactAddress.getAddressLine2()%>" <%=readonly%>  ></td></tr>
            <tr><td>country</td><td><input type="text" name="country" value ="<%=emergencyContactAddress.getCountry()%>" <%=readonly%>  ></td></tr>
            <tr><td>county</td><td><input type="text" name="county" value ="<%=emergencyContactAddress.getCounty()%>" <%=readonly%>  ></td></tr>
            <tr><td>postcode</td><td><input type="text" name="postcode" value ="<%=emergencyContactAddress.getPostcode()%>" <%=readonly%>  ></td></tr>
            <tr><td>latitude</td><td><input type="text" name="latitude" value ="<%=emergencyContactAddress.getLatitude()%>" <%=readonly%>  ></td></tr>
            <tr><td>longitude</td><td><input type="text" name="longitude" value ="<%=emergencyContactAddress.getLongitude()%>" <%=readonly%>  ></td></tr>
            <tr><td>mobile</td><td><input type="text" name="mobile" value ="<%=emergencyContactAddress.getMobile()%>" <%=readonly%>  ></td></tr>

        </table>

        <h1>Medial and Insurance</h1>
        <table>
            <tr><td>insurance policy number</td><td><input type="text" name="insuranceno" value ="<%=insurance.getInsuranceNo()%>" <%=readonly%>  ></td></tr>
            <tr><td>insurance expiry date</td><td><input type="text" name="expirydate" value ="<%=insurance.getExpirydate()%>" <%=readonly%>  ></td></tr>
            <tr><td>Additional medical Information</td><td><input type="text" name="medicalmd" value ="<%=userInfo.getMedicalMd()%>" <%=readonly%>  ></td></tr>
            <tr><td>insurance verified</td><td><input type="text" name="insuranceverified" value ="<%=processInfo.getInsuranceVerified()%>" <%=readonly%>  ></td></tr>
        </table>

    </body>
</html>
