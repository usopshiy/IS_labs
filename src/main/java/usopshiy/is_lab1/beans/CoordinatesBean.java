package usopshiy.is_lab1.beans;

import org.primefaces.PrimeFaces;
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
import usopshiy.is_lab1.entity.Coordinates;
import usopshiy.is_lab1.services.CoordinatesService;

import java.io.Serializable;
import java.util.List;

@ViewScoped
@ManagedBean
@Named("coordinates_bean")
public class CoordinatesBean implements Serializable {

    @EJB
    private CoordinatesService coordinatesService;

    @Getter
    @Setter
    @ManagedProperty("coordinates")
    private Coordinates coordinates;

    @Getter
    @Setter
    @ManagedProperty("all_coordinates")
    private List<Coordinates> allCoordinates;

    @PostConstruct
    public void init() {
        allCoordinates = coordinatesService.getAllCoordinates();
        coordinates = new Coordinates();
    }

    public void addNewCoordinatesIfNeccessary(AjaxBehaviorEvent event) {
        if (coordinates.equals(((UIInput) event.getComponent()).getValue())) {
            PrimeFaces faces = PrimeFaces.current();
            faces.ajax().update("addNewCoordinatesDialog");
            faces.executeScript("PF('widget_addNewCoordinatesDialog').show()");
        }
    }

    public void saveCoordinates() {
        coordinatesService.addCoordinates(coordinates);
        allCoordinates.add(coordinates);
        PrimeFaces faces = PrimeFaces.current();
        faces.ajax().update("cordsSelector");
        faces.executeScript("PF('widget_addNewCoordinatesDialog').hide()");
        coordinates = new Coordinates();
    }
}
