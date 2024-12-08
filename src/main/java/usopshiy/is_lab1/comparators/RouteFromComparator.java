package usopshiy.is_lab1.comparators;

import usopshiy.is_lab1.entity.Route;

import java.util.Comparator;

public class RouteFromComparator implements Comparator<Route> {

    @Override
    public int compare(Route route, Route t1) {
        return (route.getFrom().compareTo(t1.getFrom()));
    }
}
