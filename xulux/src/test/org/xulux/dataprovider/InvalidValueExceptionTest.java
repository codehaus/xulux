package org.xulux.dataprovider;

import junit.framework.TestCase;

/**
 *
 * @author Martin van den Bemt
 * @version $Id: InvalidValueExceptionTest.java,v 1.1 2004-04-22 12:59:03 mvdb Exp $
 */
public class InvalidValueExceptionTest extends TestCase {

    /**
     * Constructor for InvalidValueExceptionTest.
     * @param name the name of the test
     */
    public InvalidValueExceptionTest(String name) {
      super(name);
    }

    /**
     * Test the exception
     */
    public void testException() {
        System.out.println("testException");
        InvalidValueException exception = new InvalidValueException();
        assertEquals(null, exception.getMessage());
        exception = new InvalidValueException("test");
        assertEquals("test", exception.getMessage());
    }
}
