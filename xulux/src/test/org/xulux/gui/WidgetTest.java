/*
   $Id: WidgetTest.java,v 1.2 2004-01-28 15:22:08 mvdb Exp $
   
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
package org.xulux.gui;

import org.xulux.swing.widgets.Entry;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test of widget class.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: WidgetTest.java,v 1.2 2004-01-28 15:22:08 mvdb Exp $
 */
public class WidgetTest extends TestCase {

    /**
     * Constructor for WidgetTest.
     * @param name the name of the test
     */
    public WidgetTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite(WidgetTest.class);
        return suite;
    }

    /**
     * Test the Widget.equals(Object)
     */
    public void testEquals() {
        System.out.println("testEquals");
        Entry entry = new Entry("Name");
        entry.setPrefix("Prefix");
        assertTrue(entry.equals("Prefix.Name"));
        assertFalse(entry.equals("Name"));
        assertTrue(entry.equals(entry));
        Entry entry1 = new Entry("Test");
        assertTrue(entry1.equals("Test"));
        assertFalse(entry1.equals("Test.Test"));
        assertTrue(entry1.equals(entry1));
        assertFalse(entry1.equals(entry));
        assertFalse(entry1.equals(null));

    }

}
