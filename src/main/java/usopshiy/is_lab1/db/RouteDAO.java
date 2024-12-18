package usopshiy.is_lab1.db;

import jakarta.ejb.Stateless;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import usopshiy.is_lab1.entity.Route;

import java.util.List;

@Stateless
public class RouteDAO {

    Session session;

    public RouteDAO() {
        SessionFactory sessionFactory;
        sessionFactory = new Configuration()
                .configure()
                .buildSessionFactory();
        session = sessionFactory.openSession();
    }

    public void addRoute(Route route) {
        session.getTransaction().begin();
        session.persist(route);
        session.getTransaction().commit();
    }

    public void updateRoute(Route route) {
        session.getTransaction().begin();
        session.merge(route);
        session.getTransaction().commit();
    }

    public Route getRouteByID(Integer id) {
        return session.get(Route.class, id);
    }

    public List<Route> getAllRoutes() {
        Query<Route> query = session.createQuery("FROM Route", Route.class);
        return query.getResultList();
    }

    public void deleteRoute(Route route) {
        session.getTransaction().begin();
        session.remove(route);
        session.getTransaction().commit();
    }
}
