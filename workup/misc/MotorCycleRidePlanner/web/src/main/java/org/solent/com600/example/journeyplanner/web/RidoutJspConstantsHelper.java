/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com600.example.journeyplanner.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.solent.com600.example.journeyplanner.model.Address;
import org.solent.com600.example.journeyplanner.model.Insurance;
import org.solent.com600.example.journeyplanner.model.ItineraryItem;
import org.solent.com600.example.journeyplanner.model.ItineraryItemType;
import org.solent.com600.example.journeyplanner.model.ProcessInfo;
import org.solent.com600.example.journeyplanner.model.Rideout;
import org.solent.com600.example.journeyplanner.model.SysUser;
import org.solent.com600.example.journeyplanner.model.UserInfo;

/**
 *
 * @author gallenc Helper class to extract values from post and url parameters
 */
public class RidoutJspConstantsHelper {

    // static date formatter
    public static final String DATE_FORMAT = "dd/MM/YYYY";

    // action constants 
    public static final String ADD_NEW_USER_ACTION = "addNewUser";
    public static final String DEACTIVATE_USER_ACTION = "deactivateUser";
    public static final String SHOW_USER_ACTION = "showUser";
    public static final String LOGIN_ACTION = "login";
    public static final String CHANGE_PASSWORD_USER_ACTION = "changePassword";
    public static final String UPDATE_USER_ROLE_ACTION = "updateUserRole";
    public static final String UPDATE_USER_INFO = "updateUserInfo";
    public static final String CHANGE_SELECTED_STATES_ACTION = "changeSelectedStates";
    public static final String DELETE_RIDEOUT_ACTION = "deleteRideout";
    public static final String ADD_NEW_RIDEOUT_ACTION = "addNewRideout";
    public static final String VIEW_RIDEOUT_ACTION = "viewRideout";
    public static final String UPDATE_RIDEOUT_GENERAL_INFO_ACTION = "updateRideoutGeneralInfo";
    public static final String ADD_NEW_RIDEOUT_DAY_ACTION = "addNewRideoutDay";
    public static final String DELETE_RIDEOUT_DAY_ACTION = "deleteRideoutDay";
    public static final String INSERT_RIDEOUT_ITINERARY_ITEM_ACTION = "insertRideoutItineraryItem";
    public static final String DELETE_RIDEOUT_ITINERARY_ITEM_ACTION = "deleteRideoutItineraryItem";
    public static final String UPDATE_RIDEOUT_ITINERARY_ITEM_ACTION = "updateRideoutItineraryItem";
    
    public static final String VIEW_RIDERS ="viewRiders";
    public static final String ADD_RIDE_LEADER = "addRideLeader";
    public static final String ADD_TO_RIDERS = "addToRiders";
    public static final String ADD_TO_WAIT_LIST = "addToWaitList";
    public static final String REMOVE_RIDERS = "removeRiders";
    public static final String REMOVE_FROM_WAIT_LIST = "removeFromWaitList";
    public static final String MOVE_TO_RIDERS = "moveToRiders";
    public static final String MOVE_TO_WAIT_LIST = "moveToWaitList";

    // value constants
    // sysuser
    public static final String USERNAME = "username";
    public static final String NEW_PASSWORD = "newpassword";
    public static final String VERIFY_PASSWORD = "verifypassword";
    public static final String OLD_PASSWORD = "oldpassword";
    public static final String FIRST_NAME = "firstname";
    public static final String SURNAME = "surname";

    public static final String INSURANCE_NO = "insuranceno";
    public static final String EXPIRY_DATE = "expirydate";
    public static final String INSURANCE_VERIFIED = "insuranceverified";
    public static final String MEDICAL_MD = "medicalmd";

    // address
    public static final String NUMBER = "number";
    public static final String ADDRESS_LINE_1 = "addressline1";
    public static final String ADDRESS_LINE_2 = "addressline2";
    public static final String COUNTRY = "country";
    public static final String COUNTY = "county";
    public static final String POSTCODE = "postcode";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String MOBILE = "mobile";
    public static final String TELEPHONE = "telephone";

    //emergency address
    public static final String EMERGENCEY_CONTACT_FIRSTNAME = "emergencycontactfirstname";
    public static final String EMERGENCEY_CONTACT_SURNAME = "emergencycontactsurname";
    public static final String EMERGENCEY_CONTACT_RELATIONSHIP = "emergencycontactrelationship";
    public static final String EMERGENCEY_CONTACT_NUMBER = "emergencycontactnumber";
    public static final String EMERGENCEY_CONTACT_ADDRESS_LINE_1 = "emergencycontactaddressline1";
    public static final String EMERGENCEY_CONTACT_ADDRESS_LINE_2 = "emergencycontactaddressline2";
    public static final String EMERGENCEY_CONTACT_COUNTRY = "emergencycontactcountry";
    public static final String EMERGENCEY_CONTACT_COUNTY = "emergencycontactcounty";
    public static final String EMERGENCEY_CONTACT_POSTCODE = "emergencycontactpostcode";
    public static final String EMERGENCEY_CONTACT_LATITUDE = "emergencycontactlatitude";
    public static final String EMERGENCEY_CONTACT_LONGITUDE = "emergencycontactlongitude";
    public static final String EMERGENCEY_CONTACT_MOBILE = "emergencycontactmobile";
    public static final String EMERGENCEY_CONTACT_TELEPHONE = "emergencycontacttelephone";

    // itineary item
    public static final String ITINEARY_ITEM_BOOKING_REFERENCE = "itinearyItemBookingReference";
    public static final String ITINEARY_ITEM_DESCRIPTION_MD = "itinearyItemDescriptionMd";
    public static final String ITINERARY_ITEM_DISTANCE = "itineraryItemDistance";
    public static final String ITINERARY_ITEM_END_TIME = "itineraryItemEndTime";
    public static final String ITINERARY_ITEM_START_TIME = "itineraryItemStartTime";
    public static final String ITINERARY_ITEM_TYPE = "itineraryItemType";
    public static final String ITINERARY_ITEM_GIS_ROUTE = "itineraryItemGisRoute";

    // request to value methods
    public static SysUser updateSysUser(SysUser sysUser, HttpServletRequest request) {
        updateProcessInfo(sysUser.getProcessInfo(), request);
        sysUser.setUserName(SURNAME);

        return sysUser;
    }

    public static Insurance updateInsuranceInfo(Insurance insurance, HttpServletRequest request) throws IllegalArgumentException {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);

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
                throw new IllegalArgumentException("Error: cannot parse insurance expiry date '" + expirydate + "' to " + DATE_FORMAT);
            }
        }
        return insurance;
    }

    public static ProcessInfo updateProcessInfo(ProcessInfo processInfo, HttpServletRequest request) throws IllegalArgumentException {
        String insuranceverified = (String) request.getParameter("insuranceverified");
        if (insuranceverified != null) {
            Boolean insuranceVerified = "true".equals(insuranceverified);
            processInfo.setInsuranceVerified(insuranceVerified);
        }
        return processInfo;
    }

    public static UserInfo updateUserInfo(UserInfo userInfo, HttpServletRequest request) throws IllegalArgumentException {

        updateUserAddress(userInfo.getAddress(), request);
        updateEmergencyContactAddress(userInfo.getEmergencyContactAddress(), request);
        updateInsuranceInfo(userInfo.getInsurance(), request);

        String firstname = (String) request.getParameter("firstname");
        if (firstname != null) {
            userInfo.setFirstname(firstname);
        }
        String surname = (String) request.getParameter("surname");
        if (surname != null) {
            userInfo.setSurname(surname);
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

        String medicalmd = (String) request.getParameter("medicalmd");
        if (medicalmd != null) {
            userInfo.setMedicalMd(medicalmd);
        }

        return userInfo;
    }

    public static Address updateUserAddress(Address address, HttpServletRequest request) throws IllegalArgumentException {
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
                throw new IllegalArgumentException("cannot parse address latitude as double ", ex);
            }
        }
        String longitude = (String) request.getParameter("longitude");
        if (longitude != null) {
            try {
                address.setLongitude(Double.parseDouble(longitude));
            } catch (Exception ex) {
                throw new IllegalArgumentException("cannot parse address longitude as double ", ex);
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

        return address;
    }

    public static Address updateEmergencyContactAddress(Address emergencyContactAddress, HttpServletRequest request) throws IllegalArgumentException {
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
        if (emergencycontactcounty != null) {
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
                throw new IllegalArgumentException("cannot parse emergencyContactAddress latitude as double " + ex.getMessage());
            }
        }

        String emergencycontactlongitude = (String) request.getParameter("emergencycontactlongitude");
        if (emergencycontactlongitude != null) {
            try {
                emergencyContactAddress.setLongitude(Double.parseDouble(emergencycontactlongitude));
            } catch (Exception ex) {
                throw new IllegalArgumentException("cannot parse emergencyContactAddress longitude as double " + ex.getMessage());
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
        return emergencyContactAddress;
    }

    public static Rideout updateRideout(Rideout rideout, HttpServletRequest request) throws IllegalArgumentException {
        // TODO
        
        return rideout;
    }

    public static ItineraryItem updateItinearyItem(ItineraryItem itineraryItem, HttpServletRequest request) throws IllegalArgumentException {

        String itineraryItemTypeStr = (String) request.getParameter(ITINERARY_ITEM_TYPE);
        if (itineraryItemTypeStr != null) {
            try {
                ItineraryItemType itineraryItemType = ItineraryItemType.valueOf(itineraryItemTypeStr);
                itineraryItem.setItineraryItemType(itineraryItemType);
            } catch (Exception ex) {
                throw new IllegalArgumentException("cannot parse itineraryItemType " + itineraryItemTypeStr, ex);
            }
        }

        String bookingReference = (String) request.getParameter(ITINEARY_ITEM_BOOKING_REFERENCE);
        if (bookingReference != null) {
            itineraryItem.setBookingReference(bookingReference);
        }

        String descriptionMd = (String) request.getParameter(ITINEARY_ITEM_DESCRIPTION_MD);
        if (bookingReference != null) {
            itineraryItem.setDescriptionMd(descriptionMd);
        }

        String distanceStr = (String) request.getParameter(ITINERARY_ITEM_DISTANCE);
        if (distanceStr != null) {
            try {
                Double distance = Double.parseDouble(distanceStr);
                itineraryItem.setDistance(distance);
            } catch (Exception ex) {
                throw new IllegalArgumentException("problem parsing distance paramater " + distanceStr, ex);
            }
        }

        String startTime = (String) request.getParameter(ITINERARY_ITEM_START_TIME);
        if (startTime != null) {
            itineraryItem.setStartTime(startTime);
        }

        String endTime = (String) request.getParameter(ITINERARY_ITEM_END_TIME);
        if (endTime != null) {
            itineraryItem.setEndTime(endTime);
        }

        Address address = itineraryItem.getAddress();
        updateUserAddress(address, request);
        itineraryItem.setAddress(address);

        String gisRoute = (String) request.getParameter(ITINERARY_ITEM_GIS_ROUTE);
        if (gisRoute != null) {
            itineraryItem.setGisRoute(gisRoute);
        }

        return itineraryItem;
    }
}
