package usopshiy.is_lab1.comparators;

import usopshiy.is_lab1.entity.Route;

import java.util.Comparator;

public class RouteDistanceComparator implements Comparator<Route> {

    @Override
    public int compare(Route route, Route t1) {
        return (route.getDistance().compareTo(t1.getDistance()));
    }
}
