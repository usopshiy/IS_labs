package usopshiy.is_lab1.beans;

import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
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

    @ManagedProperty("location_flag")
    @Getter
    @Setter
    private boolean locationFlag;

    @ManagedProperty("filter")
    @Getter
    @Setter
    private String filter;

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

    public void toOperation() {
        routes = getAllRoutes();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("operation.xhtml");
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
        PrimeFaces.current().ajax().update("main:messages", "main:dt-routes");
    }

    public void deleteRoute() {
        routesService.deleteRoute(route);
        routes.remove(route);
        PrimeFaces.current().ajax().update("main:messages", "main:dt-routes");
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
        PrimeFaces.current().ajax().update("main:messages", "main:dt-routes");
    }

    public void countHigherRating() {
        long count = routesService.countHigherRatings(rating);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Counted higher ratings routes", String.valueOf(count)));
        PrimeFaces.current().executeScript("PF('countHigherRatingDialog').hide()");
        PrimeFaces.current().ajax().update("main:messages", "main:dt-routes");
    }

    public void routesByNameStart() {
        routes = routesService.getRoutesByNameStart(route.getName());
        openNew();
        PrimeFaces.current().executeScript("PF('routeNameStartDialog').hide()");
        PrimeFaces.current().ajax().update("main:messages", "main:dt-routes");
    }

    public void getBiggestRoute() {
        if (locationFlag) {
            route = routesService.getLongestRoute(route.getFrom(), route.getTo());
        }
        else {
            route = routesService.getShortestRoute(route.getFrom(), route.getTo());
        }

        if (route != null) {
            routes.clear();
            routes.add(route);
        }
        else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No such routes", "Route between these locations doesn't exist"));
        }
        PrimeFaces.current().executeScript("PF('biggestRouteDialog').hide()");
        PrimeFaces.current().ajax().update("main:messages", "main:dt-routes");
    }

    public void addMessage(AjaxBehaviorEvent event) {
        UIComponent component = event.getComponent();
        if (component instanceof UIInput) {
            UIInput inputComponent = (UIInput) component;
            Boolean value = (Boolean) inputComponent.getValue();
            String summary = value ? "Longest" : "Shortest";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
        }
    }

    public void getAllRoutesFiltered() {
        routes = routesService.getAllRoutesFiltered(route.getFrom(), route.getTo(), filter);
        PrimeFaces.current().executeScript("PF('allRoutesFilteredDialog').hide()");
        PrimeFaces.current().ajax().update("main:messages", "main:dt-routes");
    }

    public List<Route> getAllRoutes() {
        return routesService.getAllRoutes();
    }
}
