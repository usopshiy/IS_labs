package usopshiy.is_lab1.db;

import jakarta.ejb.Stateless;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.transaction.TransactionalException;
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
        session.merge(route);
        session.getTransaction().commit();
    }

    public void addRoutes(List<Route> routes) {
        try {
            session.getTransaction().begin();
            for (Route route : routes) {
                session.merge(route);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().isActive()){
                session.getTransaction().rollback();
            }
            throw new TransactionalException("something went wrong", e);
        }
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
