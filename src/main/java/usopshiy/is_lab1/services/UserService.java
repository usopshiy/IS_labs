package usopshiy.is_lab1.services;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import usopshiy.is_lab1.db.UserDAO;
import usopshiy.is_lab1.entity.User;

@Stateless
public class UserService {

    @EJB
    private final UserDAO userDAO = new UserDAO();

    public void updateUser(User user) {
        userDAO.updateUser(user);
    }
}
