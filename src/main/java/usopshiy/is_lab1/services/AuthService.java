package usopshiy.is_lab1.services;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import usopshiy.is_lab1.crypto.PasswordHasher;
import usopshiy.is_lab1.db.UserDAO;
import usopshiy.is_lab1.entity.User;

@RequestScoped
public class AuthService {

    @EJB
    private UserDAO userDAO = new UserDAO();

    public String registerUser(User user) {
        String oldPassword = user.getPassword();
        String password = PasswordHasher.encode(user.getPassword());
        user.setPassword(password);
        try {
            userDAO.saveUser(user);
            return "ok";
        }
        catch (Exception e) {
            user.setPassword(oldPassword);
            return e.getMessage();
        }
    }

    public User loginUser(User user) {
        User loggedUser;
        String oldPassword = user.getPassword();
        loggedUser = userDAO.getUserByName(user.getUsername());
        String password = PasswordHasher.encode(user.getPassword());
        if (!(loggedUser == null) && loggedUser.getPassword().equals(password)) {
            loggedUser.setPassword(oldPassword);
            return loggedUser;
        }
        return null;
    }
}
