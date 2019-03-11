package org.solent.com600.example.journeyplanner.model;

import java.util.List;

public interface RideoutDAO {

    public Rideout createRideout(Rideout rideout);

    public Rideout update(Rideout rideout);

    public void delete(Long Id);

    public Rideout retreive(Long id);

    public List<Rideout> retreiveAll();

    public void deleteAll();

    public List<Rideout> retrieveLikeMatching(String title);

    public List<Rideout> retrieveLikeMatching(String title, List<RideoutState> rideoutStates);

    public List<Rideout> retreiveAllByRideLeader(SysUser rideLeader, List<RideoutState> rideoutStates);

    public List<Rideout> retreiveAllByRider(SysUser rider, List<RideoutState> rideoutStates);

    public List<Rideout> retreiveAll(List<RideoutState> rideoutStates);

}
