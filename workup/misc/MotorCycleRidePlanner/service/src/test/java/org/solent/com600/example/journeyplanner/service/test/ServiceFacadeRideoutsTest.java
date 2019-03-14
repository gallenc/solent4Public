/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com600.example.journeyplanner.service.test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.naming.AuthenticationException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solent.com600.example.journeyplanner.model.ItineraryItem;
import org.solent.com600.example.journeyplanner.model.ItineraryItemType;
import org.solent.com600.example.journeyplanner.model.Rideout;
import org.solent.com600.example.journeyplanner.model.RideoutDay;
import org.solent.com600.example.journeyplanner.model.RideoutState;
import org.solent.com600.example.journeyplanner.model.Role;
import org.solent.com600.example.journeyplanner.model.ServiceFacade;
import org.solent.com600.example.journeyplanner.model.ServiceFactory;
import org.solent.com600.example.journeyplanner.model.SysUser;
import org.solent.com600.example.journeyplanner.service.ServiceFactoryImpl;

/**
 *
 * @author gallenc
 */
public class ServiceFacadeRideoutsTest {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceFacadeRideoutsTest.class);

    @Test
    public void testRideout() {

        ServiceFactory serviceFactory = new ServiceFactoryImpl();
        assertNotNull(serviceFactory);

        ServiceFacade serviceFacade = serviceFactory.getServiceFacade();
        assertNotNull(serviceFacade);

        try {
            serviceFacade.deleteAllRideouts("admin");
            LOG.debug("all rideouts deleted");
        } catch (AuthenticationException ex) {
            fail("problem deleting all rideouts " + ex);
            LOG.error("problem deleting all rideouts", ex);
        }

        // create mock rideout
        for (int i = 0; i < 5; i++) {
            String title = "mock_rideout_" + i;
            LOG.debug("creating " + title);
            LOG.debug("creating test rideout :" + title);
            Rideout rideout = createMockRideout(title, serviceFacade);
            LOG.debug("test rideout created:" + rideout);
        }

        List<RideoutState> rideoutStates = Arrays.asList(RideoutState.PLANNING);
        try {
            List<Rideout> rideouts = serviceFacade.retrieveLikeMatchingRideouts("_3", rideoutStates, "admin");
            LOG.debug("retrieveLikeMatchingRideouts(\"_3\", rideoutStates, \"admin\") retreived:" + rideouts.size());
            for (Rideout rideout : rideouts) {
                LOG.debug("test rideout retreived:" + rideout);
            }
        } catch (AuthenticationException ex) {
            LOG.error("problem retrieveLikeMatchingRideouts", ex);
            fail("problem retrieveLikeMatchingRideouts");
        }

        // delete all rideouts after test so that we can delete all users in another test
        try {
            serviceFacade.deleteAllRideouts("admin");
            LOG.debug("all rideouts deleted at end of test");
        } catch (AuthenticationException ex) {
            fail("problem deleting all rideouts " + ex);
            LOG.error("problem deleting all rideouts", ex);
        }

    }

    public Rideout createMockRideout(String title, ServiceFacade serviceFacade) {
        Rideout rideout = new Rideout();

        rideout.setRideoutstate(RideoutState.PLANNING);
        rideout.setTitle(title);

        Date startDate = new Date();
        rideout.setStartDate(startDate);

        String descriptionMd = "#description " + title;
        rideout.setDescriptionMd(descriptionMd);
        rideout.setMaxRiders(10);

        // add 7 rideoutNo days
        for (int day = 0; day < 7; day++) {
            RideoutDay rideoutDay = new RideoutDay();
            rideoutDay.setDescriptionMd("#rideout " + title + "day " + day);

            // add 4 items per day
            for (int item = 0; item < 4; item++) {
                ItineraryItem itineraryItem = new ItineraryItem();
                itineraryItem.setDescriptionMd("#rideout " + title + "day " + day + "itinerary item " + item);
                itineraryItem.setItineraryItemType(ItineraryItemType.JOURNEY);
                rideoutDay.getItineraryItems().add(itineraryItem);
            }
            rideout.getRideoutDays().add(rideoutDay);
        }

        // create and add riders and rideleaders if they do not exist
        // only only create if not already defined
        try {

            String userName = "ride_leader_" + title;

            String actingSysUserName = "admin";
            SysUser rideLeader = serviceFacade.retrieveByUserName(userName, actingSysUserName);
            if (rideLeader == null) {
                String password = userName;
                String firstname = userName;
                String surname = userName;
                rideLeader = serviceFacade.createUser(userName, password, firstname, surname, Role.RIDER, actingSysUserName);
            }
            rideLeader.setRole(Role.RIDELEADER);
            serviceFacade.updateUser(rideLeader, "admin");

            userName = "rider_" + title;
            SysUser rider = serviceFacade.retrieveByUserName(userName, actingSysUserName);
            if (rider == null) {
                String password = userName;
                String firstname = userName;
                String surname = userName;
                rider = serviceFacade.createUser(userName, password, firstname, surname, Role.RIDER, actingSysUserName);
            }
            rider.setRole(Role.RIDER);
            serviceFacade.updateUser(rider, "admin");

            userName = "riderwait_" + title;
            SysUser riderwait = serviceFacade.retrieveByUserName(userName, actingSysUserName);
            if (riderwait == null) {
                String password = userName;
                String firstname = userName;
                String surname = userName;
                riderwait = serviceFacade.createUser(userName, password, firstname, surname, Role.RIDER, actingSysUserName);
            }
            riderwait.setRole(Role.RIDER);
            serviceFacade.updateUser(riderwait, "admin");

            // set users in rideout
            rideout.setRideLeader(rideLeader);
            List<SysUser> riders = rideout.getRiders();
            riders.add(rider);
            List<SysUser> waitlist = rideout.getWaitlist();
            waitlist.add(riderwait);

            // create rideout
            rideout = serviceFacade.createRideout(rideout, "admin");

        } catch (AuthenticationException ex) {
            LOG.error("problem creating sys user", ex);
        }

        return rideout;
    }

}
