package usopshiy.is_lab1.comparators;

import usopshiy.is_lab1.entity.Route;

import java.util.Comparator;

public class RouteNameComparator implements Comparator<Route> {

    @Override
    public int compare(Route route, Route t1) {
        return route.getName().compareTo(t1.getName());
    }
}
