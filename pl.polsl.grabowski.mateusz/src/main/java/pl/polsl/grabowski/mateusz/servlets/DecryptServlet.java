package pl.polsl.grabowski.mateusz.servlets;

import pl.polsl.grabowski.mateusz.model.IncorrectEncryptOrDecryptTextException;
import pl.polsl.grabowski.mateusz.model.Model;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * Servlet that the web app forwards to when the user wants to decrypt
 *
 * @author Mateusz Grabowski
 * @version 1.0
 */
@WebServlet("/decrypt")
public class DecryptServlet extends HttpServlet {

    /**
     * Function called by both doGet and doPost managing the logic of the program
     *
     * @param request  the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException      the io exception
     */
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=ISO-8859-2");
        PrintWriter output = response.getWriter();
        String text= request.getParameter("Text");
        String keySeed = request.getParameter("KeySeed");
        String radioBut = request.getParameter("choose action");
        Model model = new Model();
        model.setText(text);
        int newKey = 0;
        newKey = convertKeySeed(keySeed);
        model.setSeed(newKey);
        model.setKey();


        for(int i=0; i<3; i++)
        {
            if(Model.EnumChoice.values()[i].name().equalsIgnoreCase(radioBut))
            {
                model.setChoose(i);
                break;
            }
        }

        try {
            output.println(
                    "<html>\n" +
                            "<body>\n" +
                            "<p>Text: " + text + "</p>\n" +
                            "<p>Key Seed: " + model.getSeed() + "</p>\n" +
                            "<p>Decrypted: " + model.decryptText() + "</p>\n" +
                            "<p>Choice: "+ radioBut +"</p>\n"+
                            "</body>\n" +
                            "</html>"
            );
        } catch (IncorrectEncryptOrDecryptTextException e) {
            Cookie[] cookies = request.getCookies();
            int noOfErrors=0;
            if(cookies != null)
            {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("errorCount")) {
                        noOfErrors = Integer.parseInt(cookie.getValue());
                        break;
                    }
                }
            }
            noOfErrors++;
            Cookie cookie = new Cookie("errorCount", Integer.toString(noOfErrors));
            response.addCookie(cookie);
            response.sendError(response.SC_BAD_REQUEST, e.getMessage());
        }

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
