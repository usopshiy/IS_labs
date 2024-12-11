package usopshiy.is_lab1.services;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import usopshiy.is_lab1.db.CoordinatesDAO;
import usopshiy.is_lab1.entity.Coordinates;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Stateless
public class CoordinatesService {

    @EJB
    private CoordinatesDAO coordinatesDAO;

    public List<Coordinates> getAllCoordinates() {
        return coordinatesDAO.getAllCoordinates();
    }

    public void addCoordinates(Coordinates coordinates) {
        coordinatesDAO.addCoordinates(coordinates);
    }

    public Map<Long, Coordinates> getAllCoordinatesAsMap() {
        return coordinatesDAO.getAllCoordinates().stream().collect(Collectors.toMap(Coordinates::getId, x -> x));
    }
}
