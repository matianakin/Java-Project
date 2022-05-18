package pl.polsl.grabowski.mateusz.servlets;

import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Web application lifecycle listener.
 *
 * @author Mateusz Grabowski
 */
@WebListener
public class DbInitListener implements ServletContextListener {

    /**
     * Function Initializing the context
     *
     * @param sce ServletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("entityManager",
                Persistence.createEntityManagerFactory("myPersistence").createEntityManager());
    }

    /**
     * Function destroying the context
     *
     * @param sce ServletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
