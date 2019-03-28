<%-- 
    Document   : UserInfo
    Created on : 26-Feb-2019, 09:18:13
    Author     : gallenc
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>

<%@page import="org.solent.com600.example.journeyplanner.model.Role"%>
<%@page import="org.solent.com600.example.journeyplanner.web.WebObjectFactory"%>
<%@page import="org.solent.com600.example.journeyplanner.model.ServiceFactory"%>
<%@page import="org.solent.com600.example.journeyplanner.model.ServiceFacade"%>
<%@page import="org.solent.com600.example.journeyplanner.model.ProcessInfo"%>
<%@page import="org.solent.com600.example.journeyplanner.model.SysUser"%>
<%@page import="org.solent.com600.example.journeyplanner.model.UserInfo"%>
<%@page import="org.solent.com600.example.journeyplanner.model.Address"%>
<%@page import="org.solent.com600.example.journeyplanner.model.Insurance"%>

<%
    // generic code for all JSPs to set up session
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

    // date translation utilities
    String DATE_FORMAT = "dd/MM/YYYY";
    DateFormat df = new SimpleDateFormat(DATE_FORMAT);

    // access
    String inputControl = "disabled";
    String rolechangeControl = "disabled";
    String insuranceVerifiedControl = "disabled";

    if (Role.ANONYMOUS.equals(sessionUserRole)) {
        // redirect main page
    } else if (Role.RIDER.equals(sessionUserRole)) {
        // redirect main page if not this user
        inputControl = "";
        rolechangeControl = "disabled";
        insuranceVerifiedControl = "disabled";
    } else if (Role.RIDELEADER.equals(sessionUserRole)) {
        inputControl = "";
        rolechangeControl = "disabled";
        insuranceVerifiedControl = "";
    } else if (Role.ADMIN.equals(sessionUserRole)) {
        inputControl = "";
        rolechangeControl = "";
        insuranceVerifiedControl = "";
    }

    // get request values
    String action = (String) request.getParameter("action");
    String selUserName = (String) request.getParameter("selUserName");

    SysUser sysUser = serviceFacade.retrieveByUserName(selUserName, sessionUserName);
    if (sysUser == null) {
        throw new IllegalStateException("sysUser is null for selUserName=" + selUserName);
    }
    // this is just to test jsp display
//    if (sysUser == null) {
//        sysUser = new SysUser();
    //   }
    UserInfo userInfo = sysUser.getUserInfo();
    ProcessInfo processInfo = sysUser.getProcessInfo();

    Address address = (userInfo == null) ? new Address() : userInfo.getAddress();
    Address emergencyContactAddress = (userInfo == null) ? new Address() : userInfo.getEmergencyContactAddress();
    Insurance insurance = (userInfo == null) ? new Insurance() : userInfo.getInsurance();

    // test actions
    if ("changePassword".equals(action)) {
        String newpassword = (String) request.getParameter("newpassword");
        String verifypassword = (String) request.getParameter("verifypassword");
        String oldpassword = (String) request.getParameter("oldpassword");
        if (newpassword == null || newpassword.length() < 8) {
            errorMessage = "Error - you must enter a new password greater than 8 characters";
            error = true;
        } else if (!newpassword.equals(verifypassword)) {
            errorMessage = "Error - the new and verify password entries must be equal ";
            error = true;
        } else {
            try {
                if (Role.ADMIN.equals(sessionUserRole)) {
                    serviceFacade.updatePasswordByUserName(newpassword, selUserName, sessionUserName);
                } else {
                    serviceFacade.updateOldPasswordByUserName(newpassword, oldpassword, selUserName, sessionUserName);
                }
            } catch (Exception ex) {
                errorMessage = "Error - problem updating password " + ex.getMessage();
                error = true;
            }
        }
    } else if ("updateUserRole".equals(action)) {

        // only admin can change user role
        String role = (String) request.getParameter("role");
        try {
            Role newRole = Role.valueOf(role);
            serviceFacade.updateUserRoleByUserName(newRole, selUserName, sessionUserName);
        } catch (Exception ex) {
            errorMessage = "Error - problem updating user role " + ex.getMessage();
            error = true;
        }

    } else if ("updateUserInfo".equals(action)) {
        String firstname = (String) request.getParameter("firstname");
        if (firstname != null) {
            userInfo.setFirstname(firstname);
        }
        String surname = (String) request.getParameter("surname");
        if (surname != null) {
            userInfo.setSurname(surname);
        }
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

        // emergency contact
        String emergencycontactfirstname = (String) request.getParameter("emergencycontactfirstname");
        if (emergencycontactfirstname != null) {
            userInfo.setEmergencyContactFirstName(emergencycontactfirstname);
        }
        String emergencycontactsurname = (String) request.getParameter("emergencycontactsurname");
        if (emergencycontactsurname != null) {
            userInfo.setEmergencyContactSurname(emergencycontactsurname);
        }
        String emergencycontactrelationship = (String) request.getParameter("emergencycontactrelationship");
        if (emergencycontactrelationship != null) {
            userInfo.setEmergencyContactRelationship(emergencycontactrelationship);
        }

        // emergency contact address       
        String emergencycontactnumber = (String) request.getParameter("emergencycontactnumber");
        if (emergencycontactnumber != null) {
            emergencyContactAddress.setNumber(emergencycontactnumber);
        }
        String emergencycontactaddressline1 = (String) request.getParameter("emergencycontactaddressline1");
        if (emergencycontactaddressline1 != null) {
            emergencyContactAddress.setAddressLine1(emergencycontactaddressline1);
        }
        String emergencycontactaddressline2 = (String) request.getParameter("emergencycontactaddressline2");
        if (emergencycontactaddressline2 != null) {
            emergencyContactAddress.setAddressLine2(emergencycontactaddressline2);
        }
        String emergencycontactcountry = (String) request.getParameter("emergencycontactcountry");
        if (emergencycontactcountry != null) {
            emergencyContactAddress.setCountry(emergencycontactcountry);
        }
        String emergencycontactcounty = (String) request.getParameter("emergencycontactcounty");
        if (county != null) {
            emergencyContactAddress.setCounty(emergencycontactcounty);
        }
        String emergencycontactpostcode = (String) request.getParameter("emergencycontactpostcode");
        if (emergencycontactpostcode != null) {
            emergencyContactAddress.setPostcode(emergencycontactpostcode);
        }

        String emergencycontactlatitude = (String) request.getParameter("emergencycontactlatitude");
        if (emergencycontactlatitude != null) {
            try {
                emergencyContactAddress.setLatitude(Double.parseDouble(emergencycontactlatitude));
            } catch (Exception ex) {
                errorMessage = "cannot parse emergencyContactAddress latitude as double " + ex.getMessage();
                error = true;
            }
        }

        String emergencycontactlongitude = (String) request.getParameter("emergencycontactlongitude");
        if (emergencycontactlongitude != null) {
            try {
                emergencyContactAddress.setLongitude(Double.parseDouble(emergencycontactlongitude));
            } catch (Exception ex) {
                errorMessage = "cannot parse emergencyContactAddress longitude as double " + ex.getMessage();
                error = true;
            }
        }
        String emergencycontactmobile = (String) request.getParameter("emergencycontactmobile");
        if (emergencycontactmobile != null) {
            emergencyContactAddress.setMobile(emergencycontactmobile);
        }

        String emergencycontacttelephone = (String) request.getParameter("emergencycontacttelephone");
        if (emergencycontacttelephone != null) {
            emergencyContactAddress.setTelephone(emergencycontacttelephone);
        }

        String insuranceno = (String) request.getParameter("insuranceno");
        if (insuranceno != null) {
            insurance.setInsuranceNo(insuranceno);
        }

        String expirydate = (String) request.getParameter("expirydate");
        // if empty date - ignore
        if (expirydate != null && !expirydate.trim().isEmpty()) {
            Date expDate = null;
            try {
                expDate = df.parse(expirydate);

            } catch (Exception ex) {
            }
            if (expDate != null) {
                insurance.setExpirydate(expDate);
            } else {
                error = true;
                errorMessage = "Error: cannot parse insurance expiry date '" + expirydate + "' to " + DATE_FORMAT;
            }
        }

        String insuranceverified = (String) request.getParameter("insuranceverified");
        if (insuranceverified != null) {
            Boolean insuranceVerified = "true".equals(insuranceverified);
            processInfo.setInsuranceVerified(insuranceVerified);
        }
        String medicalmd = (String) request.getParameter("medicalmd");
        if (medicalmd != null) {
            userInfo.setMedicalMd(medicalmd);
        }

        if (!error) {
            try {
                serviceFacade.updateUser(sysUser, sessionUserName);
            } catch (Exception ex) {
                errorMessage = "Error updating user data " + ex.getMessage();
            }
        }

    }


%>
<!DOCTYPE html>
<html>
    <!-- header.jsp injected content -->
    <jsp:include page="header.jsp" />
    <!-- current jsp page content -->
    <!--BODY-->
    <div class="content"> 
        <!-- print error message if there is one -->
        <div style="color:red;"><%=errorMessage%></div>
        <BR>
        <h2>User Details for <%=sysUser.getUserName()%> </h2>
        <div class="splitcontentleft">
            <BR>
            <h2>Update Password</h2>
            <BR>
            <form action="userInfo.jsp?tabSelected=userInfo" method="post">
                <table>
                    <%
                        if (Role.ADMIN.equals(sessionUserRole)) {
                    %>
                    <tr><td>old password </td><td>not needed since in Admin role</td></tr>
                    <%
                    } else {
                    %>
                    <tr><td>old password </td><td><input type="password" id="oldpass" name="oldpassword"></td></tr>
                            <%
                                }
                            %>
                    <tr><td>new password </td><td><input type="password" id="newpass" name="newpassword" minlength="8" required></td></tr>
                    <tr><td>verify new password</td><td><input type="password" id="newpassverify" name="verifypassword" minlength="8" required></td></tr>
                    <tr><td></td><td><input type="submit" value="Change Password"></td></tr>
                </table>
                <input type="hidden" name="selUserName" value ="<%=sysUser.getUserName()%>">
                <input type="hidden" name="action" value="changePassword">
            </form>
            <BR>
            <form action="userInfo.jsp?tabSelected=userInfo" method="post">
                <h2>User Role</h2>
                <BR>
                <table>
                    <tr><td>role</td><td>
                            <select name="role" <%=rolechangeControl%> >
                                <option value="<%=Role.ANONYMOUS%>" <%= (Role.ANONYMOUS.equals(sysUser.getRole())) ? "selected" : ""%> ><%=Role.ANONYMOUS%></option>
                                <option value="<%=Role.RIDER%>" <%= (Role.RIDER.equals(sysUser.getRole())) ? "selected" : ""%>><%=Role.RIDER%></option>
                                <option value="<%=Role.RIDELEADER%>" <%= (Role.RIDELEADER.equals(sysUser.getRole())) ? "selected" : ""%>><%=Role.RIDELEADER%></option>
                                <option value="<%=Role.ADMIN%>" <%= (Role.ADMIN.equals(sysUser.getRole())) ? "selected" : ""%>><%=Role.ADMIN%></option>
                                <option value="<%=Role.DEACTIVATED%>" <%= (Role.DEACTIVATED.equals(sysUser.getRole())) ? "selected" : ""%>><%=Role.DEACTIVATED%></option>
                            </select>
                        </td></tr>
                </table>
                <input type="hidden"  name="selUserName" value ="<%=selUserName%>" >
                <input type="hidden" name="action" value="updateUserRole">
                <input type="submit" value="Update User Role" <%=rolechangeControl%> >
            </form>
            <BR>
            <form action="userInfo.jsp?tabSelected=userInfo" method="post">
                <h2>User Information</h2>
                <BR>
                <table>
                    <tr><td>username</td><td><%=selUserName%></td></tr>
                    <tr><td>first name</td><td><input type="text" name="firstname" value ="<%=userInfo.getFirstname()%>" <%=inputControl%>  ></td></tr>
                    <tr><td>surname</td><td><input type="text" name="surname" value ="<%=userInfo.getSurname()%>"<%=inputControl%>  ></td></tr>
                </table>
                <BR>
                <h2>User Address</h2>
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
                <BR>
                </div>

                <div class="splitcontentright">
                    <BR>
                    <h2>Emergency Contact</h2>
                    <BR>
                    <table>
                        <tr><td>emergency contact first name</td><td><input type="text" name="emergencycontactfirstname" value ="<%=userInfo.getEmergencyContactFirstName()%>" <%=inputControl%>  ></td></tr>
                        <tr><td>emergency contact surname</td><td><input type="text" name="emergencycontactsurname" value ="<%=userInfo.getEmergencyContactSurname()%>" <%=inputControl%>  ></td></tr>
                        <tr><td>emergency contact relationship</td><td><input type="text" name="emergencycontactrelationship" value ="<%=userInfo.getEmergencyContactRelationship()%>" <%=inputControl%>  ></td></tr>

                        <tr><td>house number</td><td><input type="text" name="emergencycontactnumber" value ="<%=emergencyContactAddress.getNumber()%>" <%=inputControl%>  ></td></tr>
                        <tr><td>address line1</td><td><input type="text" name="emergencycontactaddressline1" value ="<%=emergencyContactAddress.getAddressLine1()%>" <%=inputControl%>  ></td></tr>
                        <tr><td>address line2</td><td><input type="text" name="emergencycontactaddressline2" value ="<%=emergencyContactAddress.getAddressLine2()%>" <%=inputControl%>  ></td></tr>
                        <tr><td>country</td><td><input type="text" name="emergencycontactcountry" value ="<%=emergencyContactAddress.getCountry()%>" <%=inputControl%>  ></td></tr>
                        <tr><td>county</td><td><input type="text" name="emergencycontactcounty" value ="<%=emergencyContactAddress.getCounty()%>" <%=inputControl%>  ></td></tr>
                        <tr><td>postcode</td><td><input type="text" name="emergencycontactpostcode" value ="<%=emergencyContactAddress.getPostcode()%>" <%=inputControl%>  ></td></tr>
                        <tr><td>latitude</td><td><input type="text" name="emergencycontactlatitude" value ="<%=emergencyContactAddress.getLatitude()%>" <%=inputControl%>  ></td></tr>
                        <tr><td>longitude</td><td><input type="text" name="emergencycontactlongitude" value ="<%=emergencyContactAddress.getLongitude()%>" <%=inputControl%>  ></td></tr>
                        <tr><td>mobile</td><td><input type="text" name="emergencycontactmobile" value ="<%=emergencyContactAddress.getMobile()%>" <%=inputControl%>  ></td></tr>
                        <tr><td>telephone</td><td><input type="text" name="emergencycontacttelephone" value ="<%=emergencyContactAddress.getTelephone()%>" <%=inputControl%>  ></td></tr>

                    </table>

                    <BR>
                    <h2>Medical and Insurance</h2>
                    <BR>
                    <table>
                        <tr><td>insurance policy number</td><td><input type="text" name="insuranceno" value ="<%=insurance.getInsuranceNo()%>" <%=inputControl%>  ></td></tr>
                        <tr><td>insurance expiry date (<%=DATE_FORMAT%>)</td><td>
                                <input type="text" name="expirydate" 
                                       value ="<%=((insurance.getExpirydate() == null) ? "" : df.format(insurance.getExpirydate()))%>"
                                       <%=inputControl%>  ></td></tr>
                        <tr><td>Additional medical Information</td><td><input type="text" name="medicalmd" value ="<%=userInfo.getMedicalMd()%>" <%=inputControl%>  ></td></tr>
                        <tr><td>insurance verified</td><td>
                                <input type="checkbox" name="insuranceverified" value="true" <%=(processInfo.getInsuranceVerified()) ? "checked" : ""%>  <%=insuranceVerifiedControl%>  >
                                <label for="insuranceverified">Verified</label></td></tr>
                    </table>
                    <BR>
                </div>
                <input type="hidden" name="selUserName" value ="<%=selUserName%>">
                <input type="hidden" name="action" value="updateUserInfo">
                <input type="submit" value="Update User Information">
            </form>
            <form action="userInfo.jsp?tabSelected=userInfo" method="post">
                <input type="hidden" name="selUserName" value ="<%=selUserName%>">
                <input type="hidden" name="action" value="cancel">
                <input type="submit" value="Cancel">
            </form>
        </div>

        <!-- footer.jsp injected content-->
        <jsp:include page="footer.jsp" />

        <!-- end of page -->
</html>

