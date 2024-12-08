package usopshiy.is_lab1.services;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import usopshiy.is_lab1.db.RouteDAO;
import usopshiy.is_lab1.entity.Location;
import usopshiy.is_lab1.entity.Route;
import usopshiy.is_lab1.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class RoutesService {

    @EJB
    private final RouteDAO routeDAO = new RouteDAO();

    public List<Route> getAllRoutes() {
        return routeDAO.getAllRoutes();
    }

    public void updateRoute(Route route) {
        routeDAO.updateRoute(route);
    }

    public void addRoute(Route route) {
        routeDAO.addRoute(route);
    }

    public Route getRouteById(Integer id) {
        return routeDAO.getRouteByID(id);
    }

    public void deleteRoute(Route route) {
        routeDAO.deleteRoute(route);
    }

    // Special Operations
    public void deleteRouteByRating(int rating, User owner) {
        List<Route> routes = routeDAO.getAllRoutes();
        for (Route route : routes) {
            if ((owner.isAdmin() || route.getOwner().equals(owner)) && route.getRating() == rating) {
                return;
            }
        }
    }

    public long countHigherRatings(int rating) {
        List<Route> routes = routeDAO.getAllRoutes();
        return routes.stream()
                .filter(item -> item.getRating() > rating)
                .count();
    }

    public List<Route> getRoutesByNameStart(String start) {
        List<Route> routes = routeDAO.getAllRoutes();
        return routes.stream()
                .filter(item -> item.getName().startsWith(start))
                .collect(Collectors.toList());
    }

    public Route getLongestRoute(Location from, Location to) {
        List<Route> routes = routeDAO.getAllRoutes();
        return routes.stream()
                .filter(item -> item.getFrom().equals(from) && item.getTo().equals(to))
                .reduce((a, b) -> a.getDistance() > b.getDistance() ? a:b)
                .get();
    }

    public Route getShortestRoute(Location from, Location to) {
        List<Route> routes = routeDAO.getAllRoutes();
        return routes.stream()
                .filter(item -> item.getFrom().equals(from) && item.getTo().equals(to))
                .reduce((a, b) -> a.getDistance() < b.getDistance() ? a:b)
                .get();
    }

    public List<Route> getAllRoutesFiltered(Location from, Location to, String field) {
        List<Route> routes = routeDAO.getAllRoutes();
        return routes.stream()
                .filter(item -> item.getFrom().equals(from) && item.getTo().equals(to))
                .sorted(Route.getFieldComparator(field))
                .collect(Collectors.toList());
    }
}
