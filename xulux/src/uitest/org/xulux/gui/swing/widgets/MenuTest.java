/*
   $Id: MenuTest.java,v 1.3 2004-03-16 15:04:15 mvdb Exp $
   
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
package org.xulux.gui.swing.widgets;

import java.io.InputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.xulux.core.ApplicationPart;
import org.xulux.gui.PartCreator;

/**
 * Testcase for a menubar
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: MenuTest.java,v 1.3 2004-03-16 15:04:15 mvdb Exp $
 */
public class MenuTest extends TestCase {

    /**
     * Constructor for EntryTest.
     * @param name the name of the test
     */
    public MenuTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(MenuTest.class);
        return suite;
    }

    /**
     * Test a simple window
     */
    public void testMenu() {
        String xml = "org/xulux/gui/swing/widgets/MenuTest.xml";
        //        ((SimpleLog)LogFactory.getLog(NyxWindowListener.class)).setLevel(SimpleLog.LOG_LEVEL_TRACE);
        InputStream stream = getClass().getClassLoader().getResourceAsStream(xml);
        ApplicationPart part = PartCreator.createPart(null, stream);
        part.activate();
    }

    /**
     * @param args arguments
     */
    public static void main(String[] args) {
        try {
            new MenuTest("MenuTest").testMenu();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(0);
        }
    }

}
