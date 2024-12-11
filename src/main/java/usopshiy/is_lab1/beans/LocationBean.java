package usopshiy.is_lab1.beans;

import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.component.UIInput;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import usopshiy.is_lab1.entity.Location;
import usopshiy.is_lab1.services.LocationService;

import java.io.Serializable;
import java.util.List;

@ViewScoped
@ManagedBean
@Named("location_bean")
public class LocationBean implements Serializable {

    @EJB
    private LocationService locationService;

    @Getter
    @Setter
    @ManagedProperty("location")
    private Location location;

    @Getter
    @Setter
    @ManagedProperty("all_locations")
    private List<Location> allLocations;

    @PostConstruct
    public void init() {
        allLocations = locationService.getAllLocations();
        location = new Location();
    }

    public void addNewLocationIfNeccessary(AjaxBehaviorEvent event) {
        if (location.equals(((UIInput) event.getComponent()).getValue())) {
            PrimeFaces faces = PrimeFaces.current();
            faces.ajax().update("addNewLocationDialog");
            faces.executeScript("PF('widget_addNewLocationDialog').show()");
        }
    }

    public void saveLocation() {
        locationService.addLocation(location);
        allLocations.add(location);
        PrimeFaces faces = PrimeFaces.current();
        faces.ajax().update("toSelector");
        faces.ajax().update("fromSelector");
        faces.executeScript("PF('widget_addNewLocationDialog').hide()");
        location = new Location();
    }
}
