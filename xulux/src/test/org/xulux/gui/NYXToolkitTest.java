/*
   $Id: NYXToolkitTest.java,v 1.6 2004-05-11 11:50:00 mvdb Exp $
   
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

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.SimpleLog;
import org.xulux.core.XuluxContext;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NYXToolkitTest.java,v 1.6 2004-05-11 11:50:00 mvdb Exp $
 */
public class NYXToolkitTest extends TestCase {

    /**
     * Set the logging property, so we can test if no logging is turned on
     */
    static {
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
    }

    /**
     * Constructor for NYXToolkitTest.
     * @param name the name of the test
     */
    public NYXToolkitTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(NYXToolkitTest.class);
        return suite;
    }

    /**
     * Test getting the instance
     */
    public void testGetInstance() {
        System.out.println("testGetInstance");
        XuluxContext c = XuluxContext.getInstance();
        // by default it is the swing toolkit that shows up
        // so before getting it, set it to something bogus
        String def = XuluxContext.getGuiDefaults().getDefaultWidgetType();
        XuluxContext.getGuiDefaults().setDefaultWidgetType("bogus");
        assertNull(XuluxToolkit.getInstance());
        ((SimpleLog) LogFactory.getLog(XuluxToolkit.class)).setLevel(SimpleLog.LOG_LEVEL_OFF);
        assertNull(XuluxToolkit.getInstance());
        ((SimpleLog) LogFactory.getFactory().getInstance(XuluxToolkit.class)).setLevel(SimpleLog.LOG_LEVEL_WARN);
        XuluxContext.getGuiDefaults().setDefaultWidgetType(def);
        XuluxToolkit kit = XuluxToolkit.getInstance();
        assertNotNull(kit);
        assertEquals(kit, XuluxToolkit.getInstance());
    }

}
