/*
   $Id: SessionPartTest.java,v 1.3 2004-01-28 15:22:05 mvdb Exp $
   
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
package org.xulux.context;

import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * The sessionpart test.
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SessionPartTest.java,v 1.3 2004-01-28 15:22:05 mvdb Exp $
 */
public class SessionPartTest extends TestCase {

    static String value1 = "value1";
    static String value2 = "value2";
    static String key1 = "key1";
    static String key2 = "key2";

    /**
     * Constructor for SessionPartTest.
     * @param name the name of the test
     */
    public SessionPartTest(String name) {
        super(name);
    }

    /**
     * @return the test suite.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(SessionPartTest.class);
        return suite;
    }

    /**
     * Test the sessionpart constructor
     */
    public void testSessionPart() {
        System.out.println("testSessionPart");
        SessionPart part = new SessionPart();
        assertEquals(part.size(), 0);
    }

    /**
     * Test setting and getting of value
     */
    public void testSetValue() {
        System.out.println("testSetValue");
        SessionPart part = new SessionPart();
        assertNull(part.getValue("hi"));
        part.setValue(null, null);
        part.setValue(key1, value1);
        assertEquals(part.getValue(key1), value1);
        assertNull(part.getValue(key2));
        part.setValue(key2, value2);
        assertEquals(part.getValue(key2), value2);
        assertEquals(2, part.size());
    }

    /**
     * Test clearing the session
     */
    public void testClear() {
        System.out.println("testClear");
        SessionPart part = new SessionPart();
        part.clear();
        part.setValue(key1, value1);
        part.setValue(key2, value2);
        part.clear();
        assertEquals(part.size(), 0);
    }

    /**
     * Test removing values
     */
    public void testRemove() {
        System.out.println("testRemove");
        SessionPart part = new SessionPart();
        assertNull(part.remove("test"));
        assertNull(part.remove(null));
        part.setValue(key1, value1);
        part.setValue(key2, value2);
        assertEquals(2, part.size());
        part.remove(key1);
        assertEquals(1, part.size());
        part.remove("foobar");
        assertEquals(1, part.size());
        part.remove(key2);
        assertEquals(0, part.size());
    }

    /**
     * Test the iterator
     */
    public void testIterator() {
        System.out.println("testIterator");
        SessionPart part = new SessionPart();
        part.setValue(key1, value1);
        part.setValue(key2, value2);
        Iterator it = part.iterator();
        while (it.hasNext()) {
            Object sessionValue = part.getValue(it.next());
            if (!value1.equals(sessionValue) && !value2.equals(sessionValue)) {
                fail("an incorrect value was returned " + sessionValue);
            }
        }
    }

    /**
     * Test toString.
     */
    public void testToString() {
        System.out.println("testToString");
        SessionPart part = new SessionPart();
        assertNotNull(part.toString());
        assertTrue(part.toString().length() > 0);
        part.setValue("1", "1");
        assertNotNull(part.toString());
        assertTrue(part.toString().length() > 0);
    }
}
