/*
   $Id: BeanParameterTest.java,v 1.1 2004-03-16 14:35:13 mvdb Exp $
   
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
package org.xulux.dataprovider;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test the beanparameter class.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: BeanParameterTest.java,v 1.1 2004-03-16 14:35:13 mvdb Exp $
 */
public class BeanParameterTest extends TestCase {

    /**
     * Constructor for BeanParameterTest.
     * @param name the name of the test
     */
    public BeanParameterTest(String name) {
        super(name);
    }

    /**
     * @return the test suite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(BeanParameterTest.class);
        return suite;
    }

    /**
     * Test the string type
     */
    public void testStringType() {
        System.out.println("testStringType");
        BeanParameter p = new BeanParameter();
        p.setType("String");
        p.setValue("test");
        assertEquals("test", p.getObject());
    }

    /**
     * Test the static type
     */
    public void testStaticType() {
        System.out.println("testStaticType");
        BeanParameter p = new BeanParameter("Static", "org.xulux.dataprovider.ParameterType.FIRST");
        assertEquals(ParameterType.FIRST, p.getObject());
        p = new BeanParameter("Static", "test");
        System.out.println("p : " + p.getObject());
        assertNull(p.getObject());
        // private non static
        p = new BeanParameter("Static", "org.xulux.dataprovider.ParameterType.type");
        assertNull(p.getObject());
        // private static
        p = new BeanParameter("Static", "org.xulux.dataprovider.ParameterType.FIFTH");
        assertEquals("fifth", p.getObject());
        // unknown static
        p = new BeanParameter("Static", "org.xulux.dataprovider.ParameterType.XXXX");
        assertNull(p.getObject());
    }

    /**
     * Test some unknown type...
     */
    public void testUnknownType() {
        System.out.println("testUnknownType");
        BeanParameter p = new BeanParameter("bogus", "bogus");
        assertNull(p.getObject());
    }

    /**
     * Test the toString()
     */
    public void testToString() {
        System.out.println("testUnknownType");
        BeanParameter p = new BeanParameter();
        assertNotNull(p.toString());
    }

}
