/*
   $Id: ConverterTest.java,v 1.1 2004-03-16 14:35:15 mvdb Exp $
   
   Copyright 2002-2004 The Xulux Project

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package org.xulux.dataprovider.converters;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Tests the default converters.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ConverterTest.java,v 1.1 2004-03-16 14:35:15 mvdb Exp $
 */
public class ConverterTest extends TestCase {

    /**
     * Constructor for ConverterTest.
     * @param name the testname
     */
    public ConverterTest(String name) {
        super(name);
    }

    /**
     * @return the test suite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ConverterTest.class);
        return suite;
    }

    /**
     * Test the integer converter
     */
    public void testIntegerConverter() {
        System.out.println("testIntegerConverter");
        IntegerConverter c = new IntegerConverter();
        Integer i = new Integer(10);
        assertEquals("10", c.getGuiValue(i));
        assertEquals(new Integer(10), c.getBeanValue("10"));
        assertNull(c.getGuiValue(null));
        assertNull(c.getGuiValue("null"));
        assertNull(c.getBeanValue(null));
        assertNull(c.getBeanValue("10a"));
    }

    /**
     * Test the integer converter
     */
    public void testDoubleConverter() {
        System.out.println("testDoubleConverter");
        DoubleConverter c = new DoubleConverter();
        Double i = new Double(10);
        assertEquals("10.0", c.getGuiValue(i));
        assertEquals(new Double(10), c.getBeanValue("10"));
        assertNull(c.getGuiValue(null));
        assertNull(c.getGuiValue("null"));
        assertNull(c.getBeanValue(null));
        assertNull(c.getBeanValue("10a"));
    }

    /**
     * Test the default converter.
     *
     */
    public void testDefaultConverter() {
        System.out.println("testDefaultConverter");
        DefaultConverter c = new DefaultConverter();
        assertEquals(c, c.getBeanValue(c));
        assertNull(c.getBeanValue(null));
        assertNull(c.getGuiValue(null));
        assertEquals(c.toString(), c.getGuiValue(c));
        assertEquals(c.getType(), c.getGuiValue(c).getClass());
    }

}
