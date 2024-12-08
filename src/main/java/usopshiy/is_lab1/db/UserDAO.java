package usopshiy.is_lab1.db;

import jakarta.ejb.Stateless;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import usopshiy.is_lab1.entity.User;

@Stateless
public class UserDAO {

    Session session;

    public UserDAO() {
        SessionFactory sessionFactory;
        sessionFactory = new Configuration()
                .configure()
                .buildSessionFactory();
        session = sessionFactory.openSession();
    }

    public User getUserByName(String name) {
        try {
            User user = session.createQuery("FROM User where username = :name", User.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return user;
        }
        catch (Exception e) {
            return null;
        }
    }

    public void saveUser(User user) throws Exception {
        try {
            session.getTransaction().begin();
            session.persist(user);
            session.getTransaction().commit();
        }
        catch (Exception e) {
            if (session.getTransaction().isActive()){
                session.getTransaction().rollback();
            }
            throw new Exception("password or username is not unique");
        }
    }

    public void updateUser(User user) {
        session.getTransaction().begin();
        session.merge(user);
        session.getTransaction().commit();
    }
}
