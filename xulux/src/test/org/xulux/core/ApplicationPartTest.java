/*
   $Id: ApplicationPartTest.java,v 1.2 2004-04-15 00:05:04 mvdb Exp $
   
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
package org.xulux.core;

import org.xulux.gui.Widget;
import org.xulux.swing.util.NyxEventQueue;
import org.xulux.swing.widgets.CheckBox;
import org.xulux.swing.widgets.Combo;
import org.xulux.swing.widgets.Entry;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * The ApplicationPart test
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ApplicationPartTest.java,v 1.2 2004-04-15 00:05:04 mvdb Exp $
 */
public class ApplicationPartTest extends TestCase {

    /**
     * Constructor for ApplicationPartTest.
     * @param name the name of the test
     */
    public ApplicationPartTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ApplicationPartTest.class);
        return suite;
    }

    /**
     * Test getting the widget
     */
    public void testGetWidget() {
        Widget entry = new Entry("Entry");
        entry.setPrefix("Test");
        Widget combo = new Combo("Combo");
        Widget checkBox = new CheckBox("CheckBox");
        Widget checkBox1 = new CheckBox("CheckBox");
        checkBox1.setPrefix("Test");
        ApplicationPart part = new ApplicationPart();
        part.addWidget(entry);
        part.addWidget(combo);
        part.addWidget(checkBox);
        part.addWidget(checkBox1);
        assertNull(part.getWidget("entry"));
        assertEquals(entry, part.getWidget("Test.Entry"));
        assertEquals(combo, part.getWidget("Combo"));
        assertEquals(checkBox, part.getWidget("CheckBox"));
        assertEquals(checkBox1, part.getWidget("test.checkbox"));
    }

    /**
     * Test destroy
     */
    public void testDestroy() {
        System.out.println("testDestroy");
        // without event queue
        ApplicationPart part = new ApplicationPart("part");
        part.destroy();
        // with eventqueue
        NyxEventQueue q = new NyxEventQueue();
        part.destroy();
        // with a session object
        part.getSession();
        part.destroy();
    }
    /**
     * Test the session.
     * Just tests if we don't get a clone of the session
     * by accident.
     */
    public void testSession() {
        System.out.println("testSession");
        ApplicationPart part = new ApplicationPart("part");
        SessionPart session = part.getSession();
        assertEquals(session, part.getSession());
    }
    
    /**
     * test the provider
     */
    public void testProvider() {
        System.out.println("testProvider");
        ApplicationPart part = new ApplicationPart();
        assertNull(part.getProvider());
        part.setProvider("partProvider");
        assertEquals("partProvider", part.getProvider());
    }
}
