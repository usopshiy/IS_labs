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
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import usopshiy.is_lab1.entity.User;
import usopshiy.is_lab1.services.AuthService;
import usopshiy.is_lab1.services.UserService;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@SessionScoped
@Named("userBean")
public class UserBean implements Serializable {

    @EJB
    private UserService userService;

    @Inject
    private AuthService authService;

    @ManagedProperty("user")
    @Getter
    @Setter
    private User user;

    @ManagedProperty("requests")
    @Getter
    @Setter
    private List<User> users;

    @ManagedProperty("filteredRequests")
    @Getter
    @Setter
    private List<User> filteredRequests;

    public UserBean() {
       user = new User();
    }

    @PostConstruct
    public void init() {
        users = userService.getUsersWithRequest();
    }

    public void login() {
        user = authService.loginUser(user);
        if (user == null) {
           user = new User();
           FacesContext.getCurrentInstance().addMessage("login_form", new FacesMessage(FacesMessage.SEVERITY_INFO, "incorrect email or password", null));
        }
        else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("main.xhtml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void register() {
        String response = authService.registerUser(user);
        if (response.equals("ok")) {
            try {
                user = new User();
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            FacesContext.getCurrentInstance().addMessage("register_form", new FacesMessage(FacesMessage.SEVERITY_ERROR, response, null));
        }
    }

    public void logout() {
        try {
            user = new User();
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void requestAdmin() {
        List<User> admins = userService.getAdmins();
        if (admins.isEmpty()) {
            user.setAdmin(true);
        }
        else {
            user.setRoleRequested(true);
        }
        userService.updateUser(user);
    }

    public void answerRequest(boolean answer, User requester) {
        users.remove(requester);
        requester.setRoleRequested(false);
        requester.setAdmin(answer);
        userService.updateUser(requester);
    }
}
