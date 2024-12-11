package usopshiy.is_lab1.db;

import jakarta.ejb.Stateless;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import usopshiy.is_lab1.entity.Coordinates;

import java.util.List;

@Stateless
public class CoordinatesDAO {

    Session session;

    public CoordinatesDAO() {
        SessionFactory sessionFactory;
        sessionFactory = new Configuration()
                .configure()
                .buildSessionFactory();
        session = sessionFactory.openSession();
    }

    public List<Coordinates> getAllCoordinates() {
        Query<Coordinates> query = session.createQuery("FROM Coordinates ", Coordinates.class);
        return query.getResultList();
    }

    public void addCoordinates(Coordinates coordinates) {
        session.getTransaction().begin();
        session.persist(coordinates);
        session.getTransaction().commit();
    }
}
