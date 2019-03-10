package org.solent.com600.example.journeyplanner.model;
import java.util.List;

public interface RideoutDAO {

    public Rideout createRideout(Rideout rideout);

    public void delete(Long Id);

    public Rideout retreive(Long id);

    public void untitledMethod();

    public List<Rideout> retreiveAll();

    public void deleteAll();

    public Rideout retrieveLikeMatching(String title);
}
