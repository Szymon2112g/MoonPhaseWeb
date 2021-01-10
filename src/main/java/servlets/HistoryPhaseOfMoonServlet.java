/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet is responsible to show history of calculate phase of moon
 *
 * @author Szymon Godziński
 * @version 1.0
 */
@WebServlet(name = "historyPhaseOfMoonServlet", urlPatterns = {"/historyPhaseOfMoonServlet"})
public class HistoryPhaseOfMoonServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>History phase of moon</title>");
            out.println("</head>");
            out.println("<body>");

            out.print("<form action=\"FormDate\" method=\"GET\" >\n"
                    + "<input type=\"submit\" value=\"Calculate phase of moon\" />\n"
                    + "</form>");

            out.print("<hr><b>History phases of moon</b><br>");

            out.print("<hr>Short history<b>(Session)</b><br>");

            /**
             * Object management session
             */
            HttpSession session = request.getSession();

            /**
             * Object have history of calculate phase of moon obtained with session
             */
            List<LocalDate> phaseHistories = (List<LocalDate>) session.getAttribute("phaseHistory");

            if (phaseHistories != null) {
                phaseHistories.forEach(historyDate -> {
                    out.println("<i>" + historyDate.toString() + "</i><br>");
                });
            } else {
                out.println("<i><font color=RED>lack</font></i>");
            }

            out.print("<hr>Detailded history<b>(Cookies)</b><br>");
            
            /**
             * Object represents cookies
             */
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (checkValidationOfDate(cookie.getName())) {
                        out.println("<i>" + cookie.getName() + " = "
                                + cookie.getValue() + "</i><br>");
                    }
                }
            } else {
                out.println("<i><font color=RED>lack</font></i>");
            }

            out.println("</body>");
            out.println("</html>");

        }
    }

    /**
     * Function check if date is valid format
     * 
     * @param date date as string year-month-day for example 2021-01-07
     * @return true if is correct or false if no
     */
    private boolean checkValidationOfDate(String date) {
        return date.matches("\\d+-\\d+-\\d+");
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
