/*
   $Id: ComboTest.java,v 1.4 2004-03-16 15:04:15 mvdb Exp $
   
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
import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.xulux.core.ApplicationContext;
import org.xulux.core.ApplicationPart;
import org.xulux.gui.PartCreator;

/**
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ComboTest.java,v 1.4 2004-03-16 15:04:15 mvdb Exp $
 */
public class ComboTest extends TestCase {

    /**
     * Constructor for ComboTest.
     * @param name the name of the test
     */
    public ComboTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ComboTest.class);
        return suite;
    }

    /**
     * a simple combo in swing
     *
     */
    public void testSimpleComboSwing() {
        PersonBean person = new PersonBean("Martin", "van den Bemt");
        String xml = "org/xulux/gui/swing/widgets/ComboTest.xml";
        InputStream stream = getClass().getClassLoader().getResourceAsStream(xml);
        ApplicationPart part = PartCreator.createPart(person, stream);
        part.activate();
    }

    /**
     * A simple combo in swt
     */
    public void testSimpleComboSwt() {
        ApplicationContext.getInstance();
        ApplicationContext.getInstance().setDefaultWidgetType("swt");
        PersonBean person = new PersonBean("Martin", "van den Bemt");
        String xml = "org/xulux/gui/swing/widgets/ComboTest.xml";
        InputStream stream = getClass().getClassLoader().getResourceAsStream(xml);
        ApplicationPart part = PartCreator.createPart(person, stream);
        part.activate();
    }

    /**
     * @param args arguments
     */
    public static void main(String[] args) {
        try {
            new ComboTest("ComboTest").testSimpleComboSwing();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(0);
        }
    }

    /**
     * Convenience method for the combo
     * content type=field test..
     * @return the values
     */
    public static List getValues() {
        ArrayList list = new ArrayList();
        list.add(new PersonBean("content1", "field1"));
        list.add(new PersonBean("content2", "field2"));
        list.add(new PersonBean("content3", "field3"));
        return list;
    }

}
