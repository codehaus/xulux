package org.xulux.nyx.swing.masks;

/**
 * The Mask interface. All masks must implement the 
 * specified methods.
 * Every IMask MUST implement an empty constructor!
 * 
 * @author Martin van den Bemt
 * @version $Id: IMask.java,v 1.1 2002-10-23 00:28:44 mvdb Exp $
 */
public interface IMask
{
    /**
     * Checks if the specfied value is ok
     * You have to implement this for generic
     * purposes, Checking of correct type
     * is up to you. No simple types yet
     * 
     * @param value - the value to check the constraints against
     */
    public boolean isValueOk(Object value);
    
    /** 
     * If the value is not ok, it should provide
     * a (localalized) message, to say what is wrong
     * @param label - the label parameter is there for convenience 
     *                 which allows to use the field label in the message
     */
    public String getMessage(String label);
    
}
