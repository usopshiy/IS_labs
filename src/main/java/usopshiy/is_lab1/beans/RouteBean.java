package usopshiy.is_lab1.beans;

import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import usopshiy.is_lab1.entity.Route;
import usopshiy.is_lab1.services.RoutesService;

import java.io.Serializable;
import java.util.List;

@ManagedBean
@SessionScoped
@Named("route_bean")
public class RouteBean implements Serializable {

    @ManagedProperty("route")
    @Getter
    @Setter
    private Route route;

    @ManagedProperty("routes")
    @Getter
    @Setter
    private List<Route> routes;

    @EJB
    private RoutesService routesService;

    @PostConstruct
    public void setRoutes() {
        routes = getAllRoutes();
    }

    public List<Route> getAllRoutes() {
        return routesService.getAllRoutes();
    }
}
