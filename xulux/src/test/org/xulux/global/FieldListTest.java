package org.xulux.global;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * 
 * @author Martin van den Bemt
 * @version $Id: FieldListTest.java,v 1.1 2003-12-23 01:20:31 mvdb Exp $
 */
public class FieldListTest extends TestCase {

    /**
     * Constructor for FieldListTest.
     * @param name the name of the test
     */
    public FieldListTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(FieldListTest.class);
        return suite;
    }

    /**
     * The inner class FieldList
     */
    public void testFieldList() {
        System.out.println("testFieldList");
        BeanMapping.FieldList list = new BeanMapping().new FieldList();
        list.add("string1");
        list.add("string2");
        list.add("string3");
        assertEquals(3, list.size());
        assertEquals(-1, list.indexOf(null));
        assertEquals(0, list.indexOf("string1"));
        assertEquals(2, list.indexOf("string3"));
        assertEquals(-1, list.indexOf("string4"));
        assertEquals(-1, list.indexOf(new Object()));
    }

}
