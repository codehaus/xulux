/*
   $Id: ArrayContentHandlerTest.java,v 1.3 2004-01-28 15:22:08 mvdb Exp $
   
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
package org.xulux.global.contenthandlers;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.logging.impl.SimpleLog;

/**
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ArrayContentHandlerTest.java,v 1.3 2004-01-28 15:22:08 mvdb Exp $
 */
public class ArrayContentHandlerTest extends TestCase {

    /**
     * Set the logging property, so we can test if no logging is turned on
     */
    static {
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
    }

    /**
     * Constructor for ArrayContentHandlerTest.
     * @param name the name of the test
     */
    public ArrayContentHandlerTest(String name) {
        super(name);
    }

    /**
     * @return the test suite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ArrayContentHandlerTest.class);
        return suite;
    }

    /**
     * Test all things in the arraycontenthandler
     */
    public void testGetContent() {
        ArrayContentHandler handler = new ArrayContentHandler();
        assertEquals(Array.class, handler.getType());
        assertNull(handler.getContent());
        // test without logging..
         ((SimpleLog) ContentHandlerAbstract.log).setLevel(SimpleLog.LOG_LEVEL_OFF);
        assertNull(handler.getContent());
        ((SimpleLog) ContentHandlerAbstract.log).setLevel(SimpleLog.LOG_LEVEL_WARN);
        List list = new ArrayList();
        list.add("one");
        list.add("two");
        handler.setContent(list);
        assertNotNull(handler.getContent());
        Object content = handler.getContent();
        assertTrue(content.getClass().isArray());
        assertEquals(list.size(), ((Object[]) content).length);
        assertEquals("one", ((Object[]) content)[0]);
        String[] strArray = new String[] { "three", "four" };
        handler.setContent(strArray);
        assertNotNull(handler.getContent());
        String string = "one";
        handler.setContent(string);
        assertEquals(string, handler.getContent());
    }

}
