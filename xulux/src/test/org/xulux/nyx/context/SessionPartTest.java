/*
 $Id: SessionPartTest.java,v 1.2 2003-11-28 02:37:56 mvdb Exp $

 Copyright 2002-2003 (C) The Xulux Project. All Rights Reserved.
 
 Redistribution and use of this software and associated documentation
 ("Software"), with or without modification, are permitted provided
 that the following conditions are met:

 1. Redistributions of source code must retain copyright
    statements and notices.  Redistributions must also contain a
    copy of this document.
 
 2. Redistributions in binary form must reproduce the
    above copyright notice, this list of conditions and the
    following disclaimer in the documentation and/or other
    materials provided with the distribution.
 
 3. The name "xulux" must not be used to endorse or promote
    products derived from this Software without prior written
    permission of The Xulux Project.  For written permission,
    please contact martin@mvdb.net.
 
 4. Products derived from this Software may not be called "xulux"
    nor may "xulux" appear in their names without prior written
    permission of the Xulux Project. "xulux" is a registered
    trademark of the Xulux Project.
 
 5. Due credit should be given to the Xulux Project
    (http://xulux.org/).
 
 THIS SOFTWARE IS PROVIDED BY THE XULUX PROJECT AND CONTRIBUTORS
 ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 THE XULUX PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 OF THE POSSIBILITY OF SUCH DAMAGE.
 
 */
package org.xulux.nyx.context;

import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * The sessionpart test.
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SessionPartTest.java,v 1.2 2003-11-28 02:37:56 mvdb Exp $
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
