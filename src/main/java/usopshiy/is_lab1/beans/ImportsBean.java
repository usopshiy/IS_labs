package usopshiy.is_lab1.beans;

import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import usopshiy.is_lab1.entity.Import;
import usopshiy.is_lab1.services.ImportService;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@SessionScoped
@ManagedBean
@Named("imports_bean")
public class ImportsBean implements Serializable {

    @Inject
    private UserBean userBean;

    @EJB
    private ImportService importService;

    @ManagedProperty("imports")
    @Getter
    @Setter
    private List<Import> imports;

    @ManagedProperty("filteredImports")
    @Getter
    @Setter
    private List<Import> filteredImports;

    @PostConstruct
    public void init() {
        this.imports = importService.getImports().stream()
                .filter(item -> userBean.getUser().isAdmin() || item.getUser().getUsername().equals(userBean.getUser().getUsername()))
                .collect(Collectors.toList());
    }
}
