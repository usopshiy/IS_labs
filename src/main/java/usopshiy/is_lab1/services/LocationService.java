package usopshiy.is_lab1.services;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import usopshiy.is_lab1.db.LocationDAO;
import usopshiy.is_lab1.entity.Location;

import java.util.List;

@Stateless
public class LocationService {

    @EJB
    private LocationDAO locationDAO;

    public List<Location> getAllLocations() {
        return locationDAO.getAllLocations();
    }

    public void addLocation(Location location) {
        locationDAO.addLocation(location);
    }
}
