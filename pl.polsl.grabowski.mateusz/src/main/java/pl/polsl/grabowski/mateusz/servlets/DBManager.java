package pl.polsl.grabowski.mateusz.servlets;

import pl.polsl.grabowski.mateusz.entities.HistoryInfo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServlet;
import java.sql.DatabaseMetaData;
import java.util.List;
import pl.polsl.grabowski.mateusz.entities.HistoryInfo;

/**
 * Class used to add object to the database as read from it
 *
 * @author Mateusz Grabowski
 * @version 1.0
 */
public class DBManager extends HttpServlet {

    /**
     * Add an object to the database.
     *
     * @param em the Entity Manager
     * @param hi the HistoryInfo object
     */
    void add(EntityManager em, HistoryInfo hi)
    {
        try {
            em.getTransaction().begin();
            em.persist(hi);
            em.getTransaction().commit();
        }
        catch(PersistenceException e) {
            em.getTransaction().rollback();
        }
    }

    /**
     * Read an object to the database.
     *
     * @param em the Entity Manager
     */
    void read(EntityManager em)
    {
        try {
            List<HistoryInfo> hists = em.createQuery("SELECT h FROM HistoryInfo h").getResultList();
            for(HistoryInfo h : hists) {
                System.out.println(h.getText() + h.getChoice() + h.getSeed() +h.getId() + "<br/>");
            }
        }
        catch(PersistenceException e) {
            em.getTransaction().rollback();
        }
    }

}
