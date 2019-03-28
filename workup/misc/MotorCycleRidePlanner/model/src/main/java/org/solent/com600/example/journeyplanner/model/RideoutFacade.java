/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com600.example.journeyplanner.model;

import java.util.List;
import javax.naming.AuthenticationException;

/**
 * @author cgallen
 */
public interface RideoutFacade {

    public Rideout createRideout(Rideout rideout, String actingSysUserName) throws AuthenticationException;

    public Rideout updateRideout(Rideout rideout, String actingSysUserName) throws AuthenticationException;

    public void deleteRideout(Long id, String actingSysUserName) throws AuthenticationException;

    public Rideout retrieveRideout(Long id, String actingSysUserName) throws AuthenticationException;

    public List<Rideout> retrieveAllRideouts(String actingSysUserName) throws AuthenticationException;

    public void deleteAllRideouts(String actingSysUserName) throws AuthenticationException;

    public List<Rideout> retrieveLikeMatchingRideouts(String title, String actingSysUserName) throws AuthenticationException;

    public List<Rideout> retrieveLikeMatchingRideouts(String title, List<RideoutState> rideoutStates, String actingSysUserName) throws AuthenticationException;

    public List<Rideout> retrieveAllRideoutsByRideLeader(SysUser rideLeader, List<RideoutState> rideoutStates, String actingSysUserName) throws AuthenticationException;

    public List<Rideout> retrieveAllRideoutsByRider(SysUser rider, List<RideoutState> rideoutStates, String actingSysUserName) throws AuthenticationException;

    public List<Rideout> retrieveAllRideouts(List<RideoutState> rideoutStates, String actingSysUserName) throws AuthenticationException;

    public List<Rideout> retrieveAllRideoutsWaitListByRider(SysUser rider, List<RideoutState> rideoutStates, String actingSysUserName) throws AuthenticationException;

    public boolean tryGetLeaseOnRideout(Long id, String actingSysUserName) throws AuthenticationException;

    public boolean userHasLeaseOnRideout(Long id, String actingSysUserName) throws AuthenticationException;

    public boolean tryReleaseLeaseOnRideout(Long id, String actingSysUserName) throws AuthenticationException;

}
