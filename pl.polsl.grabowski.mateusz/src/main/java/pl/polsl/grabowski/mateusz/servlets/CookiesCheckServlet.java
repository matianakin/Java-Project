package pl.polsl.grabowski.mateusz.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet used to check cookies
 */
@WebServlet("/CookieCheck")
public class CookiesCheckServlet extends HttpServlet {

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
        output.println("<html>\n<body>\n");
        Cookie[] cookies = request.getCookies();
        if(cookies != null)
        {
            for (Cookie cookie : cookies) {
                output.println("<p>cookie name: "+ cookie.getName() +", value: "+ cookie.getValue()+ "</p>\n");
            }
        }
        else
        {
            output.println("<p>There are no cookies</p>\n");
        }
        output.println("</body>\n</html>");
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
}
