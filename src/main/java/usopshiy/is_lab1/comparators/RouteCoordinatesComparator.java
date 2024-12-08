package usopshiy.is_lab1.comparators;

import usopshiy.is_lab1.entity.Route;

import java.util.Comparator;

public class RouteCoordinatesComparator implements Comparator<Route> {

    @Override
    public int compare(Route route, Route t1) {
        return route.getCoordinates().compareTo(t1.getCoordinates());
    }
}
