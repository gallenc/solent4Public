/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com600.example.journeyplanner.jpadao.test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solent.com600.example.journeyplanner.jpadao.DAOFactory;
import org.solent.com600.example.journeyplanner.model.ItineraryItem;
import org.solent.com600.example.journeyplanner.model.ItineraryItemType;
import org.solent.com600.example.journeyplanner.model.Rideout;
import org.solent.com600.example.journeyplanner.model.RideoutDAO;
import org.solent.com600.example.journeyplanner.model.RideoutDay;
import org.solent.com600.example.journeyplanner.model.RideoutState;
import org.solent.com600.example.journeyplanner.model.Role;
import org.solent.com600.example.journeyplanner.model.SysUser;
import org.solent.com600.example.journeyplanner.model.SysUserDAO;

/**
 *
 * @author cgallen
 */
public class TestRideoutDAO {

    private static final Logger LOG = LoggerFactory.getLogger(TestRideoutDAO.class);

    @Test
    public void testDAOFactory() {
        SysUserDAO userDAO = DAOFactory.getSysUserDAO();
        assertNotNull(userDAO);
        RideoutDAO rideoutDAO = DAOFactory.getRideoutDAO();
        assertNotNull(rideoutDAO);
    }

    @Test
    public void testCreateRideout() {
        // set up rideoutDAO before creating rideouts
        RideoutDAO rideoutDAO = DAOFactory.getRideoutDAO();
        assertNotNull(rideoutDAO);

        // first delete all rideouts before deleting/creating  users
        rideoutDAO.deleteAll();

        // set up sysUsers for test
        SysUserDAO userDAO = DAOFactory.getSysUserDAO();
        assertNotNull(userDAO);
        TestSysUserDAO.testCreateSysUsersDatabase(userDAO);

        // now start testing the rideout dao
        // create multiple rideouts
        createMockRideouts(rideoutDAO);
        List<Rideout> createdRideouts = rideoutDAO.retrieveAll();
        LOG.debug("createdRideouts.size:" + createdRideouts.size());

        List<SysUser> sysUsers = userDAO.retrieveAll();

        Rideout testRideout = createdRideouts.get(0);
        assertNotNull(testRideout);

        SysUser rideLeader = sysUsers.get(0);
        assertNotNull(rideLeader);

        SysUser rider = sysUsers.get(1);
        assertNotNull(rider);

        testRideout.setRideLeader(rideLeader);

        testRideout.setWaitlist(sysUsers); //TODO

        testRideout.setRiders(sysUsers);

        Long testRideoutID = testRideout.getId();

        rideoutDAO.update(testRideout);

        Rideout retrievedRideout = rideoutDAO.retrieve(testRideoutID);
        assertNotNull(retrievedRideout);
        LOG.debug("retrievedRideout:" + retrievedRideout);
        assertTrue(retrievedRideout.toString().equals(testRideout.toString()));

        // retrieve rideouts by state
        List<RideoutState> rideoutStates = Arrays.asList(RideoutState.PUBLISHED, RideoutState.PLANNING);
        List<Rideout> retrievedRideouts = rideoutDAO.retrieveAll(rideoutStates);
        LOG.debug("retrievedRideouts (published planning) size :" + retrievedRideouts.size());
        for (Rideout rideout : retrievedRideouts) {
            LOG.debug("      " + rideout);
        }

        String title = "rideoutNo";
        retrievedRideouts = rideoutDAO.retrieveLikeMatching(title, rideoutStates);
        LOG.debug("retrieveLikeMatching (published planning) size :" + retrievedRideouts.size());
        for (Rideout rideout : retrievedRideouts) {
            LOG.debug("      " + rideout);
        }

        retrievedRideouts = rideoutDAO.retrieveAllByRideLeader(rideLeader, rideoutStates);
        LOG.debug("retrieveAllByRideLeader (published planning) size :" + retrievedRideouts.size());
        for (Rideout rideout : retrievedRideouts) {
            LOG.debug("      " + rideout);
        }

        retrievedRideouts = rideoutDAO.retrieveAllByRider(rider, rideoutStates);
        LOG.debug("retrieveAllByRider (published planning) size :" + retrievedRideouts.size());
        for (Rideout rideout : retrievedRideouts) {
            LOG.debug("      " + rideout);
        }

        retrievedRideouts = rideoutDAO.retrieveAllWaitListByRider(rider, rideoutStates);
        LOG.debug("retrieveAllWaitListByRider (published planning) size :" + retrievedRideouts.size());
        for (Rideout rideout : retrievedRideouts) {
            LOG.debug("      " + rideout);
        }

    }

    public void createMockRideouts(RideoutDAO rideoutDAO) {
        // delete any pre-existing rideouts
        rideoutDAO.deleteAll();
        List<Rideout> createdRideouts = rideoutDAO.retrieveAll();
        assertTrue(createdRideouts.isEmpty());

        RideoutState[] rideoutStateValues = RideoutState.values();

        // create test rideouts from scratch
        for (int rideoutNo = 0; rideoutNo < rideoutStateValues.length; rideoutNo++) {
            Rideout rideout = createMockRideout("rideoutNo_" + rideoutNo);
            rideout.setRideoutstate(rideoutStateValues[rideoutNo]);
            rideout = rideoutDAO.createRideout(rideout);
            assertNotNull(rideout);
            LOG.debug("created rideout:rideoutStateValues[rideoutNo]" + rideoutStateValues[rideoutNo] + "  " + rideout.toString());
        }
    }

    public Rideout createMockRideout(String title) {
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

        // create and add ride leaders
        // set up sysUsers for test
        SysUserDAO userDAO = DAOFactory.getSysUserDAO();
        assertNotNull(userDAO);

        SysUser rideLeader = new SysUser();
        rideLeader.setUserName("ride_leader_" + title);
        rideLeader.setRole(Role.RIDELEADER);
        userDAO.create(rideLeader);

        SysUser rider = new SysUser();
        rider.setUserName("rider_" + title);
        rider.setRole(Role.RIDER);
        userDAO.create(rider);

        SysUser riderwait = new SysUser();
        riderwait.setUserName("riderwait_" + title);
        riderwait.setRole(Role.RIDER);
        userDAO.create(riderwait);

        rideout.setRideLeader(rideLeader);

        List<SysUser> riders = rideout.getRiders();
        riders.add(rider);
        List<SysUser> waitlist = rideout.getWaitlist();
        waitlist.add(riderwait);

        return rideout;
    }

}
