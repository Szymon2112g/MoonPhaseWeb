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
import model.Moon;
import model.PhaseException;

/**
 * Servlet is responsible for calculate phase of moon
 *
 * @author Szymon Godzinski
 * @version 1.1
 */
@WebServlet(name = "FormDate", urlPatterns = {"/FormDate"})
public class CalculatePhaseOfMoonServlet extends HttpServlet {

    /**
     * Object moon represents Moon class to calculate phase of moon
     */
    private Moon moon = new Moon();

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

            showTopOfWebsiteHtml(out);

            /**
             * Object have patameter of date
             */
            String paramDate = request.getParameter("date");

            if (paramDate == null) {
                out.println("<font color=RED size=20px>Parameters are empty!</font>");
                showBottomOfWebsiteHtml(out);
                return;
            }

            /**
             * Object have date given by parameter if parameter is not valid
             * this object have null and the program ends
             */
            LocalDate date = null;

            try {
                date = getDate(paramDate);
            } catch (PhaseException ex) {
                out.println("<font color=RED size=20px>" + ex.getMessage() + "</font>");
                showBottomOfWebsiteHtml(out);
                return;
            }

            /**
             * Object have phase of Moon
             */
            double phaseOfMoon;

            try {
                phaseOfMoon = calculatePhase(date);
            } catch (PhaseException ex) {
                out.println("<font color=RED size=20px>" + ex.getMessage() + "</font>");
                showBottomOfWebsiteHtml(out);
                return;
            }

            /**
             * Object have phase of moon as String to make it understandable for
             * the user
             */
            String phaseOfMoonString = getPhaseOfMoonAsString(phaseOfMoon);

            out.println("<h1>Result:" + phaseOfMoonString + "</h1><hr>");

            out.print("<form action=\"historyPhaseOfMoonServlet\" method=\"GET\" >\n"
                    + "<input type=\"submit\" value=\"Show history\" />\n"
                    + "</form>");

            /**
             * Object is list of date for which calculated phase of moon
             */
            List<LocalDate> phaseHistories = moon.getHistory();

            /**
             * Object management session
             */
            HttpSession session = request.getSession();
            session.setAttribute("phaseHistory", phaseHistories);

            /**
             * Object management cookies
             */
            Cookie cookie = new Cookie(date.toString(), phaseOfMoonString);
            response.addCookie(cookie);

            showBottomOfWebsiteHtml(out);
        }
    }

    /**
     * Function generate top of webside html it was created for clean code
     *
     * @param out response stream
     */
    private void showTopOfWebsiteHtml(PrintWriter out) {
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Phase Of Moon</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Input date to calculate phase of moon!</h1>\n"
                + "format year month day for example \"1996 8 21\"\n"
                + "<form action=\"FormDate\" method=\"GET\">\n"
                + "<p>Date:<input type=text size=20 name=date></p>\n"
                + "<input type=\"submit\" value=\"calculate\" />\n"
                + "</form><hr>");
    }

    /**
     * Function generate bottom of webside html it was created for clean code
     *
     * @param out response stream
     */
    private void showBottomOfWebsiteHtml(PrintWriter out) {
        out.println("</body>");
        out.println("</html>");
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

    /**
     * Function calculate phase of moon
     *
     * @param date date
     * @return phase of moon
     * @throws PhaseException if phase is above 1 or below 0
     * @throws DateException if date is not correct
     */
    private double calculatePhase(LocalDate date) throws PhaseException {
        return moon.calculatePhaseOfMoon(date);
    }

    /**
     * Function swap phase of moon from double to String
     * 
     * @param phaseOfMoon phase of moon
     * @return phase of moon as String
     */
    private String getPhaseOfMoonAsString(double phaseOfMoon) {
        /**
         * Object represents phase of moon as String
         */
        String phaseOfMoonString = "";

        if (phaseOfMoon < 0.125 || phaseOfMoon > 0.875) {
            phaseOfMoonString = "new moon";
        } else if (phaseOfMoon < 0.375) {
            phaseOfMoonString = "first quarter";
        } else if (phaseOfMoon < 0.625) {
            phaseOfMoonString = "full";
        } else if (phaseOfMoon <= 0.875) {
            phaseOfMoonString = "second quarter";
        }

        return phaseOfMoonString;
    }

    /**
     * Check if dateString is date, if dateString is date processes her to
     * LocalDate object, if dateString isn't date program throw exception
     *
     * @param dateString Date as string
     * @return LocalDate object selected by user
     * @throws PhaseException if date is not correct
     */
    private LocalDate getDate(String dateString) throws PhaseException {

        /**
         * Array of string represents date,index 0=year 1=month 2=day
         */
        String[] dateAsStringArray = dateString.split(" ");
        /**
         * Object localDate represents processes date
         */
        LocalDate localDate;

        /**
         * Objects represents date
         */
        int day, month, year;

        //dateAsStringArray = dateString.split(" ");
        if (dateAsStringArray.length < 3) {
            throw new PhaseException("Date have less than three numbers");
        }

        if (!validateInt(dateAsStringArray)) {
            throw new PhaseException("Date have invalid data type");
        }

        year = Integer.parseInt(dateAsStringArray[0]);
        month = Integer.parseInt(dateAsStringArray[1]);
        day = Integer.parseInt(dateAsStringArray[2]);

        if (!validateDate(year, month, day)) {
            throw new PhaseException("Date have value out of range of calendar");
        }

        localDate = LocalDate.of(year, month, day);

        return localDate;
    }

    /**
     * Function check whether imput strings are numeral decimal
     *
     * @param args strings to check
     * @return false if strings isn't numeral decimal or true if strings is
     * decimal
     */
    private boolean validateInt(String... args) {
        for (String tmp : args) {
            if (!tmp.matches("\\d+")) {
                return false;
            }
        }
        return true;
    }

    /**
     * Function check whether imput arguments can create date
     *
     * @param year year
     * @param month month
     * @param day day
     * @return true if date can be created or false if can't be
     */
    private boolean validateDate(int year, int month, int day) {

        BooleanFunctionalInterface checkDayOfMonth
                = (maxDays) -> {
                    return maxDays >= day;
                };

        switch (month) {
            case 1:
                return checkDayOfMonth.check(31);
            case 2:
                if (year != 0 && year % 4 == 0) {
                    return checkDayOfMonth.check(29);
                } else {
                    return checkDayOfMonth.check(28);
                }
            case 3:
                return checkDayOfMonth.check(31);
            case 4:
                return checkDayOfMonth.check(30);
            case 5:
                return checkDayOfMonth.check(31);
            case 6:
                return checkDayOfMonth.check(30);
            case 7:
                return checkDayOfMonth.check(31);
            case 8:
                return checkDayOfMonth.check(31);
            case 9:
                return checkDayOfMonth.check(30);
            case 10:
                return checkDayOfMonth.check(31);
            case 11:
                return checkDayOfMonth.check(30);
            case 12:
                return checkDayOfMonth.check(31);
        }
        return false;
    }

}
