/*
   $Id: NativeTest.java,v 1.4 2004-03-16 15:04:15 mvdb Exp $
   
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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This test the ability to add native swing widgets
 * on top of nyx.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NativeTest.java,v 1.4 2004-03-16 15:04:15 mvdb Exp $
 */
public class NativeTest extends TestCase {

    /**
     * @param name the name of the test
     */
    public NativeTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(NativeTest.class);
        return suite;
    }

    /**
     * Test showing a native widget on top
     * of a penel defined in nyx xml.
     *
     */
    public void testOnNyxPanel() {
        PersonBean person = new PersonBean("Martin", "van den Bemt");
        String xml = "org/xulux/gui/swing/widgets/NativeWithPanelTest.xml";
        InputStream stream = getClass().getClassLoader().getResourceAsStream(xml);
        ApplicationPart part = PartCreator.createPart(person, stream);
        part.activate();
    }

    /**
     * Test showing a native widget when no
     * panel is defined.
     */
    public void testWithoutPanel() {
    }

    /**
     * @param args parameters
     */
    public static void main(String[] args) {
        new NativeTest("nativeTest").testOnNyxPanel();
    }
}
