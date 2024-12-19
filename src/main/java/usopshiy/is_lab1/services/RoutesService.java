package usopshiy.is_lab1.services;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.faces.push.Push;
import jakarta.faces.push.PushContext;
import jakarta.inject.Inject;
import org.primefaces.component.message.Message;
import usopshiy.is_lab1.db.RouteDAO;
import usopshiy.is_lab1.entity.Location;
import usopshiy.is_lab1.entity.Route;
import usopshiy.is_lab1.entity.User;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Stateless
public class RoutesService {

    @EJB
    private final RouteDAO routeDAO = new RouteDAO();

    @Inject @Push(channel = "pushContext")
    private PushContext pushContext;

    public List<Route> getAllRoutes() {
        return routeDAO.getAllRoutes();
    }

    public void updateViews(){
        String message = "update";
        System.out.println("Sending an update to a websocket");
        pushContext.send(message);
    }

    public List<Route> getFilteredRoutes(User owner) {
        List<Route> routes = routeDAO.getAllRoutes();
        return routes.stream()
                .filter(item -> owner.isAdmin() || owner.getUsername().equals(item.getOwner().getUsername()))
                .collect(Collectors.toList());
    }

    public void updateRoute(Route route) {
        routeDAO.updateRoute(route);
        updateViews();
    }

    public void addRoute(Route route) {
        routeDAO.addRoute(route);
        updateViews();
    }

    public Route getRouteById(Integer id) {
        return routeDAO.getRouteByID(id);
    }

    public void deleteRoute(Route route) {
        routeDAO.deleteRoute(route);
        updateViews();
    }

    // Special Operations
    public void deleteRouteByRating(int rating, User owner) {
        List<Route> routes = routeDAO.getAllRoutes();
        for (Route route : routes) {
            if ((owner.isAdmin() || route.getOwner().getUsername().equals(owner.getUsername())) && route.getRating() == rating) {
                deleteRoute(route);
                updateViews();
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
        Route longestRoute = null;
        try {
            List<Route> routes = routeDAO.getAllRoutes();
            longestRoute = routes.stream()
                    .filter(item -> item.getFrom().equals(from) && item.getTo().equals(to))
                    .reduce((a, b) -> a.getDistance() > b.getDistance() ? a : b)
                    .get();
        }
        catch (NoSuchElementException e) {
            return null;
        }
        return longestRoute;
    }

    public Route getShortestRoute(Location from, Location to) {
        List<Route> routes = routeDAO.getAllRoutes();
        Route shortestRoute;
        try {
            shortestRoute = routes.stream()
                    .filter(item -> item.getFrom().equals(from) && item.getTo().equals(to))
                    .reduce((a, b) -> a.getDistance() < b.getDistance() ? a:b)
                    .get();
            return shortestRoute;
        }
        catch (NoSuchElementException e) {
            return null;
        }
    }

    public List<Route> getAllRoutesFiltered(Location from, Location to, String field) {
        List<Route> routes = routeDAO.getAllRoutes();
        return routes.stream()
                .filter(item -> item.getFrom().equals(from) && item.getTo().equals(to))
                .sorted(Route.getFieldComparator(field))
                .collect(Collectors.toList());
    }
}
