/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

/**
 * FunctionalInterface which return bollean and have parameter int
 *
 * @author Szymon Godziński
 * @version 1.0
 */
@FunctionalInterface
public interface BooleanFunctionalInterface {
    
    /**
     * Function which is used to create functional interface 
     * 
     * @param i int
     * @return boolean
     */
    public boolean check(int i);
}
