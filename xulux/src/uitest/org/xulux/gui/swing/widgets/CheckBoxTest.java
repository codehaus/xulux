/*
   $Id: CheckBoxTest.java,v 1.6 2004-05-17 16:30:21 mvdb Exp $
   
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
import org.xulux.core.XuluxContext;
import org.xulux.gui.PartCreator;

/**
 * Testcase for an entry field
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: CheckBoxTest.java,v 1.6 2004-05-17 16:30:21 mvdb Exp $
 */
public class CheckBoxTest extends TestCase {

    /**
     * Constructor for EntryTest.
     * @param name the name of the test
     */
    public CheckBoxTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(CheckBoxTest.class);
        return suite;
    }

    /**
     * Test the checkbox
     */
    public void testCheckBox() {
        String xml = "org/xulux/gui/swing/widgets/CheckBoxTest.xml";
        //        ((SimpleLog)LogFactory.getLog(NyxWindowListener.class)).setLevel(SimpleLog.LOG_LEVEL_TRACE);
        InputStream stream = getClass().getClassLoader().getResourceAsStream(xml);
        CheckBoxBean bean = new CheckBoxBean();
        bean.setBar(true);
        bean.setStrBar("on");
        ApplicationPart part = PartCreator.createPart(bean, stream);
        part.activate();
        System.out.println(XuluxContext.getDictionary().getMappings());
    }

    /**
     * @param args the arguments
     */
    public static void main(String[] args) {
        try {
            new CheckBoxTest("CheckBoxTest").testCheckBox();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(0);
        }
    }

}
