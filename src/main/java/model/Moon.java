/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.util.*;

/**
 * Represents Moon and provide calculate for phase of moon
 *
 * @author Szymon Godziński
 * @version 1.3
 */
public class Moon {

    /**
     * List represent history of use function calculatePhaseOfMoon
     */
    private List<LocalDate> historyOfCalculatedPhaseOffMoon
            = new ArrayList<>();

    /**
     * Calculate phase of moon for provide date
     *
     * @param date provided date by user
     * @return phase of moon betweeen 0 and 1
     * @throws PhaseException Throw exception if phase is above 1 or below 0 or 
     * date is null
     */
    public double calculatePhaseOfMoon(LocalDate date) throws PhaseException {
                
        if (date == null) {
            throw new PhaseException("Date is null");
        }
        
        /**
         * Average value of synodic month
         */
        final double averageValueOfSynodicMonth = 29.5305902778;
        
        /**
         * Calculated date from gregorian callendar to julian callendar
         */        
        double julianDate = calculateJulianDate(date);
        /**
         * Phase of moon
         */
        double phase = (julianDate / averageValueOfSynodicMonth) - 0.3033;
        phase = phase - ((int) phase);

        checkPhaseRange(phase);

        addHistory(date);

        return phase;
    }

    /**
     * Calculcate date from gregorian callendar to julian calendar
     *
     * @param date provided date by user
     * @return date in julian callendar
     */
    private double calculateJulianDate(LocalDate date) {
        double x = (date.getMonthValue() + 9) / 12;
        double a = 4716 + date.getYear() + (int) x;
        double y = 275 * date.getMonthValue() / 9;
        double v = 7 * a / 4;
        double b = 1729279.5 + (367 * date.getYear())
                + (int) y - (int) v + date.getDayOfMonth();
        double q = (a + 83) / 100;
        double c = (int) q;
        double w = 3 * (c + 1) / 4;
        double e = (int) w;
        double julianDate = b + 38 - e;

        return julianDate;
    }

    /**
     * Check if phase is between 0 and 1 if isn't in this range throw
     * PhaseException
     *
     * @param phase Phas eof moon
     * @throws PhaseException Throw exception if phase isn't between 1 and 0
     */
    private void checkPhaseRange(double phase) throws PhaseException {
        if (phase <= 0 || phase >= 1) {
            throw new PhaseException("Phase is outside allowed value!");
        }
    }

    /**
     * Add date to history, called from calculatePhaseOfMoon
     *
     * @param date used date in function calculatePhaseOfMoon
     */
    private void addHistory(LocalDate date) {
        historyOfCalculatedPhaseOffMoon.add(date);
    }

    /**
     * Function return dates which used to calculatePhaseOfMoon
     *
     * @return list dates which used to calculatePhaseOfMoon
     */
    public List<LocalDate> getHistory() {
        return historyOfCalculatedPhaseOffMoon;
    }
}
