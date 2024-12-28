package usopshiy.is_lab1.db;

import jakarta.ejb.Stateless;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import usopshiy.is_lab1.entity.Import;
import usopshiy.is_lab1.entity.Route;

import java.util.List;


@Stateless
public class ImportDAO {
    Session session;

    public ImportDAO() {
        SessionFactory sessionFactory;
        sessionFactory = new Configuration()
                .configure()
                .buildSessionFactory();
        session = sessionFactory.openSession();
    }

    public void addImport(Import imp) {
        session.getTransaction().begin();
        session.persist(imp);
        session.getTransaction().commit();
    }

    public List<Import> getImports() {
        Query<Import> query = session.createQuery("FROM Import ", Import.class);
        return query.getResultList();
    }
}
