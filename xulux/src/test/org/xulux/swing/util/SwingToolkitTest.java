/*
   $Id: SwingToolkitTest.java,v 1.3 2004-01-28 15:22:08 mvdb Exp $
   
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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Tests the swing toolkit
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SwingToolkitTest.java,v 1.3 2004-01-28 15:22:08 mvdb Exp $
 */
public class SwingToolkitTest extends TestCase {

    /**
     * Constructor for SwingToolkitTest.
     * @param name the name of the test
     */
    public SwingToolkitTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(SwingToolkitTest.class);
        return suite;
    }

    /**
     * Test everything in one method.
     */
    public void testAll() {
        System.out.println("testAll");
        SwingToolkit toolkit = new SwingToolkit();
        toolkit.beep();
        assertNull(NyxEventQueue.getInstance());
        toolkit.initialize();
        // should have created the nyx event queue.
        NyxEventQueue q = NyxEventQueue.getInstance();
        assertNotNull(NyxEventQueue.getInstance());
        // should not initialize again
        toolkit.initialize();
        assertEquals(q, NyxEventQueue.getInstance());
        toolkit.destroy();
    }
}
