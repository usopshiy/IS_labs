package usopshiy.is_lab1.db;

import jakarta.ejb.Stateless;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import usopshiy.is_lab1.entity.Location;

import java.util.List;

@Stateless
public class LocationDAO {
    Session session;

    public LocationDAO() {
        SessionFactory sessionFactory;
        sessionFactory = new Configuration()
                .configure()
                .buildSessionFactory();
        session = sessionFactory.openSession();
    }

    public List<Location> getAllLocations() {
        Query<Location> query = session.createQuery("FROM Location ", Location.class);
        return query.getResultList();
    }

    public void addLocation(Location location) {
        session.getTransaction().begin();
        session.persist(location);
        session.getTransaction().commit();
    }
}
