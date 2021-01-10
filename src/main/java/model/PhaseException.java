/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Exception class for objects thrown when phase is above 1 or below 0
 *
 * @author Szymon Godziński
 * @version 1.0
 */
public class PhaseException extends Exception {

    /**
     * Default constructor
     */
    public PhaseException() {
        super();
    }

    /**
     * Constructor with message
     *
     * @param message message of exception
     */
    public PhaseException(String message) {
        super(message);
    }
}
