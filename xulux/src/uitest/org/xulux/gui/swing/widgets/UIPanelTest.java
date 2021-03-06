/*
   $Id: UIPanelTest.java,v 1.1 2004-05-18 00:01:14 mvdb Exp $
   
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
 * Tests the panel. This is a visual test btw..
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: UIPanelTest.java,v 1.1 2004-05-18 00:01:14 mvdb Exp $
 */
public class UIPanelTest extends TestCase {

    /**
     * Constructor for PanelTest.
     * @param name the name of the test
     */
    public UIPanelTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(UIPanelTest.class);
        return suite;
    }

    /**
     * a panel test
     */
    public void testPanel() {
        PersonBean bean = new PersonBean("Martin", "van den Bemt");
        String xml = "org/xulux/gui/swing/widgets/PanelTest.xml";
        InputStream stream = getClass().getClassLoader().getResourceAsStream(xml);
        ApplicationPart part = PartCreator.createPart(bean, stream);
        part.activate();
    }

    /**
     * @param args arguments
     */
    public static void main(String[] args) {
        try {
            new UIPanelTest("PanelTest").testPanel();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(0);
        }
    }

}
