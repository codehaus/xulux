/*
   $Id: SwingParentWidgetHandlerTest.java,v 1.3 2004-01-28 15:22:08 mvdb Exp $
   
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
package org.xulux.swing.util;

import javax.swing.JLabel;
import javax.swing.JPanel;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SwingParentWidgetHandlerTest.java,v 1.3 2004-01-28 15:22:08 mvdb Exp $
 */
public class SwingParentWidgetHandlerTest extends TestCase {

    /**
     * Constructor for SwingParentWidgetHandlerTest.
     * @param name the name of the test
     */
    public SwingParentWidgetHandlerTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(SwingParentWidgetHandlerTest.class);
        return suite;
    }

    /**
     * Test the initialize
     */
    public void testInitialize() {
        System.out.println("testInitialize");
        SwingParentWidgetHandler h = new SwingParentWidgetHandler();
        h.initialize(null);
    }

    /**
     * Test the destroy
     */
    public void testDestroy() {
        System.out.println("testDestroy");
        SwingParentWidgetHandler h = new SwingParentWidgetHandler();
        h.destroy(null);
        h.destroy("bogus");
        JPanel panel = new JPanel();
        System.out.println("component count : " + panel.getComponentCount());
        JLabel label = new JLabel();
        panel.add(label);
        assertEquals(1, panel.getComponentCount());
        h.destroy(label);
        assertEquals(0, panel.getComponentCount());
        h.destroy(panel);
    }
}
