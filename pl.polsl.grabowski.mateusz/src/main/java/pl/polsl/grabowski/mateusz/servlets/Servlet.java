package pl.polsl.grabowski.mateusz.servlets;
import pl.polsl.grabowski.mateusz.entities.HistoryInfo;
import pl.polsl.grabowski.mateusz.model.BadKeySeedException;
import pl.polsl.grabowski.mateusz.model.IncorrectEncryptOrDecryptTextException;
import pl.polsl.grabowski.mateusz.model.Model;
import pl.polsl.grabowski.mateusz.model.Model.EnumChoice;
import java.io.*;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


/**
 * Main servlet responsible for managing the web app
 *
 * @author Mateusz Grabowski
 * @version 1.0
 */
@WebServlet("/Homophonic Servlet")
public class Servlet  extends HttpServlet {

    /**
     * The entity manager
     */
    private EntityManager em;


    /**
     * Object of DBManager class that is used to add and read from the database
     */
    private DBManager db;


    /**
     *  Initializing function creating Entity Manager
     *
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override
    public void init() throws ServletException {
        em = (EntityManager) getServletContext().getAttribute("entityManager");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Function called by both doGet and doPost managing the logic of the program
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        db = new DBManager();
        var hi = new HistoryInfo();

        response.setContentType("text/html; charset=ISO-8859-2");
        String radioBut = request.getParameter("choose action");
        Model model = new Model();

        if(radioBut.equalsIgnoreCase("history"))
        {
            db.read(em);
        }
        else {

            for (int i = 0; i < 3; i++) {
                if (EnumChoice.values()[i].name().equalsIgnoreCase(radioBut)) {
                    model.setChoose(i);
                    break;
                }
            }
            hi.setChoice(model.getChoice());
            String text= request.getParameter("Text");
            String keySeed = request.getParameter("KeySeed");
            hi.setSeed(convertKeySeed(keySeed));
            hi.setText(text);
            db.add(em, hi);
            switch (model.getChoose()) {
                case ENCRYPT: {
                    getServletContext().getRequestDispatcher("/encrypt").forward(request, response);
                    break;
                }
                case DECRYPT: {
                    getServletContext().getRequestDispatcher("/decrypt").forward(request, response);
                    break;
                }
                case BOTH: {
                    getServletContext().getRequestDispatcher("/both").forward(request, response);
                    break;
                }
            }
        }
        model.destroyKey();
    }

    /**
     * Function converting Key seed in String form into an int
     *
     * @param keySeed the Key Seed in the String form
     * @return the converted seed
     */
    private int convertKeySeed(String keySeed)
    {
        int ret;
        StringBuilder newText= new StringBuilder();
        for(int i=0; i<keySeed.length(); i++)
        {
            if(keySeed.charAt(i)>=48&&keySeed.charAt(i)<=57)
            {
                newText.append(keySeed.charAt(i));
            }
        }
        ret = Integer.parseInt(newText.toString());
        return ret;
    }
}
