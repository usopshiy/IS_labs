package usopshiy.is_lab1.comparators;

import usopshiy.is_lab1.entity.Route;

import java.util.Comparator;

public class RouteRatingComparator implements Comparator<Route> {

    @Override
    public int compare(Route route, Route t1) {
        if (route.getRating() > t1.getRating()) {
            return 1;
        }
        return (route.getRating() == t1.getRating() ? 0:-1);
    }
}
