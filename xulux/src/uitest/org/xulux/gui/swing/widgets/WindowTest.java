/*
   $Id: WindowTest.java,v 1.3 2004-01-28 15:40:08 mvdb Exp $
   
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

import org.xulux.context.ApplicationPart;
import org.xulux.gui.PartCreator;
import org.xulux.gui.Widget;

/**
 * Testcase for an entry field
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: WindowTest.java,v 1.3 2004-01-28 15:40:08 mvdb Exp $
 */
public class WindowTest extends TestCase {

    /**
     * Constructor for EntryTest.
     * @param name the name of the test
     */
    public WindowTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(WindowTest.class);
        return suite;
    }

    /**
     * Test a simple window
     */
    public void testSimpleWindow() {
        String xml = "org/xulux/gui/swing/widgets/WindowTest1.xml";
        //        ((SimpleLog)LogFactory.getLog(NyxWindowListener.class)).setLevel(SimpleLog.LOG_LEVEL_TRACE);
        InputStream stream = getClass().getClassLoader().getResourceAsStream(xml);
        ApplicationPart part = PartCreator.createPart(null, stream);
        part.activate();
        Widget widget = part.getWidget("WindowTest1");
        widget.setVisible(true);
    }

    /**
     * @param args arguments
     */
    public static void main(String[] args) {
        try {
            new WindowTest("WindowTest").testSimpleWindow();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(0);
        }
    }

}
