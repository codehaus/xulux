/*
   $Id: TabPanelTest.java,v 1.4 2004-03-16 15:04:15 mvdb Exp $
   
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

import org.xulux.core.ApplicationPart;
import org.xulux.gui.PartCreator;

import junit.framework.TestCase;

/**
 * A test for tabpanels.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: TabPanelTest.java,v 1.4 2004-03-16 15:04:15 mvdb Exp $
 */
public class TabPanelTest extends TestCase {

    /**
     * Constructor for TabPanelTest.
     * @param name the name of the test
     */
    public TabPanelTest(String name) {
        super(name);
    }

    /**
     * The test that contains everything.
     *
     */
    public void testTabs() {
        String xml = "org/xulux/gui/swing/widgets/TabPanelTest.xml";
        InputStream stream = getClass().getClassLoader().getResourceAsStream(xml);
        ApplicationPart part = PartCreator.createPart(null, stream);
        part.activate();
    }

    /**
     * @param args arguments
     */
    public static void main(String[] args) {
        new TabPanelTest("TabPanelTest").testTabs();
    }

}
