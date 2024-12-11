package usopshiy.is_lab1.services;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import usopshiy.is_lab1.crypto.PasswordHasher;
import usopshiy.is_lab1.db.UserDAO;
import usopshiy.is_lab1.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class UserService {

    @EJB
    private final UserDAO userDAO = new UserDAO();

    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public List<User> getUsersWithRequest() {
        List<User> users = getAllUsers();
        return users.stream()
                .filter(User::isRoleRequested)
                .collect(Collectors.toList());
    }

    public List<User> getAdmins() {
        List<User> users = getAllUsers();
        return users.stream()
                .filter(User::isAdmin)
                .collect(Collectors.toList());
    }
}
