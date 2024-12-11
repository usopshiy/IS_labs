package usopshiy.is_lab1.beans;

import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import usopshiy.is_lab1.entity.Route;
import usopshiy.is_lab1.services.RoutesService;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@SessionScoped
@Named("route_bean")
public class RouteBean implements Serializable {

    @Inject
    private UserBean userBean;

    @ManagedProperty("route")
    @Getter
    @Setter
    private Route route;

    @ManagedProperty("routes")
    @Getter
    @Setter
    private List<Route> routes;

    @ManagedProperty("filtered_routes")
    @Getter
    @Setter
    private List<Route> filteredRoutes;

    @ManagedProperty("rating")
    @Getter
    @Setter
    private int rating;

    @EJB
    private RoutesService routesService;

    @PostConstruct
    public void setRoutes() {
        routes = getAllRoutes();
    }

    public void filterUserRoutes() {
        System.out.println("Updating");
        routes = routesService.getFilteredRoutes(userBean.getUser());
    }

    public void toControl() {
        filterUserRoutes();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("control.xhtml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void toMain() {
        routes = getAllRoutes();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("main.xhtml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveRoute() {
        if (route.getId() != null && route.getId() > 0) {
            routesService.updateRoute(route);
        }
        else {
            route.setOwner(userBean.getUser());
            routesService.addRoute(route);
        }
        route = new Route();
        filterUserRoutes();
        PrimeFaces.current().executeScript("PF('manageRouteDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-routes");
    }

    public void deleteRoute() {
        routesService.deleteRoute(route);
        routes.remove(route);
    }

    public void openNew() {
        this.route = new Route();
    }

    public void newRating() {
        this.rating = 0;
    }

    public void deleteRouteByRating() {
        routesService.deleteRouteByRating(rating, userBean.getUser());
        routes = getAllRoutes();
        PrimeFaces.current().executeScript("PF('routeDeleteRatingDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-routes");
    }

    public void countHigherRating() {
        long count = routesService.countHigherRatings(rating);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Counted higher ratings routes", String.valueOf(count)));
        PrimeFaces.current().executeScript("PF('countHigherRatingDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-routes");
    }

    public List<Route> getAllRoutes() {
        return routesService.getAllRoutes();
    }
}
