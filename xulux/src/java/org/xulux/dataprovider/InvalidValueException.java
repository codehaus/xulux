package org.xulux.dataprovider;

/**
 * This exception can be used to say to the widget who's value is set
 * that the value entered by the user is invalid.
 * This method should be used in eg dataproviders, which do not have
 * any knowledge about widgets.
 * The String passed into the constructor, can eg be used to display a message
 * to the user.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: InvalidValueException.java,v 1.1 2004-04-22 12:59:02 mvdb Exp $
 */
public class InvalidValueException extends RuntimeException {

    /**
     * 
     */
    public InvalidValueException() {
      super();
    }
  
    /**
     * @param s
     */
    public InvalidValueException(String s) {
      super(s);
    }

}
