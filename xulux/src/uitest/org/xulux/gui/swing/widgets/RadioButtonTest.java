/*
   $Id: RadioButtonTest.java,v 1.3 2004-01-28 15:40:08 mvdb Exp $
   
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

/**
 * Testcase for a radiobutton
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: RadioButtonTest.java,v 1.3 2004-01-28 15:40:08 mvdb Exp $
 */
public class RadioButtonTest extends TestCase {

    /**
     * Constructor for RadioTest.
     * @param name the name of the test
     */
    public RadioButtonTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(RadioButtonTest.class);
        return suite;
    }

    /**
     * Test the checkbox
     */
    public void testCheckBox() {
        String xml = "org/xulux/gui/swing/widgets/RadioButtonTest.xml";
        InputStream stream = getClass().getClassLoader().getResourceAsStream(xml);
        RadioButtonBean bean = new RadioButtonBean();
        bean.setBar(true);
        bean.setGroup(true);
        ApplicationPart part = PartCreator.createPart(bean, stream);
        part.activate();
    }

    /**
     * @param args arguments
     */
    public static void main(String[] args) {
        try {
            new RadioButtonTest("RadioButtonTest").testCheckBox();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(0);
        }
    }

}
