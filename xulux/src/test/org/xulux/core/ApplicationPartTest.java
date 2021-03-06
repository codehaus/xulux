/*
   $Id: ApplicationPartTest.java,v 1.7 2004-08-19 16:29:13 mvdb Exp $
   
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
package org.xulux.core;

import java.io.ByteArrayInputStream;

import org.xulux.dataprovider.InvalidValueException;
import org.xulux.gui.IInvalidValueStrategy;
import org.xulux.gui.Widget;
import org.xulux.guidriver.XuluxGuiDriver;
import org.xulux.swing.util.NyxEventQueue;
import org.xulux.swing.widgets.CheckBox;
import org.xulux.swing.widgets.Combo;
import org.xulux.swing.widgets.Entry;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * The ApplicationPart test
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ApplicationPartTest.java,v 1.7 2004-08-19 16:29:13 mvdb Exp $
 */
public class ApplicationPartTest extends TestCase {

    /**
     * Constructor for ApplicationPartTest.
     * @param name the name of the test
     */
    public ApplicationPartTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ApplicationPartTest.class);
        return suite;
    }

    /**
     * Test getting the widget
     */
    public void testGetWidget() {
        Widget entry = new Entry("Entry");
        entry.setPrefix("Test");
        Widget combo = new Combo("Combo");
        Widget checkBox = new CheckBox("CheckBox");
        Widget checkBox1 = new CheckBox("CheckBox");
        checkBox1.setPrefix("Test");
        ApplicationPart part = new ApplicationPart();
        part.addWidget(entry);
        part.addWidget(combo);
        part.addWidget(checkBox);
        part.addWidget(checkBox1);
        assertNull(part.getWidget("entry"));
        assertEquals(entry, part.getWidget("Test.Entry"));
        assertEquals(combo, part.getWidget("Combo"));
        assertEquals(checkBox, part.getWidget("CheckBox"));
        assertEquals(checkBox1, part.getWidget("test.checkbox"));
    }

    /**
     * Test destroy
     */
    public void testDestroy() {
        System.out.println("testDestroy");
        // without event queue
        ApplicationPart part = new ApplicationPart("part");
        part.destroy();
        // with eventqueue
        NyxEventQueue q = new NyxEventQueue();
        part.destroy();
        // with a session object
        part.getSession();
        part.destroy();
    }
    /**
     * Test the session.
     * Just tests if we don't get a clone of the session
     * by accident.
     */
    public void testSession() {
        System.out.println("testSession");
        ApplicationPart part = new ApplicationPart("part");
        SessionPart session = part.getSession();
        assertEquals(session, part.getSession());
    }
    
    /**
     * test the provider
     */
    public void testProvider() {
        System.out.println("testProvider");
        ApplicationPart part = new ApplicationPart();
        assertNull(part.getProvider());
        part.setProvider("partProvider");
        assertEquals("partProvider", part.getProvider());
    }
    
    public void testInvalidValueStrategy() {
        System.out.println("testInvalidValueStrategy");
        ApplicationPart part = new ApplicationPart();
        part.setInvalidValueStrategy(MockInvalidValueStrategy.class.getName());
        assertEquals(true, part.getInvalidValueStrategy() instanceof MockInvalidValueStrategy);
        XuluxGuiDriver driver = new XuluxGuiDriver();
        String xml="<part name='LabelTestForm' prefix='Person'>"+
                    "<invalidvaluestrategy strategy='org.xulux.core.ApplicationPartTest$MockInvalidValueStrategy'/>" +
                    "</part>";
        ByteArrayInputStream stream = new ByteArrayInputStream(xml.getBytes());
        part = driver.read(stream, new Object());
        assertEquals(true, part.getInvalidValueStrategy() instanceof MockInvalidValueStrategy);
    }
    
    public void testBean() {
        System.out.println("testBean");
        String bean = new String("this is a bean");
        ApplicationPart part = new ApplicationPart(bean);
        assertEquals(bean, part.getBean());
        String newBean = new String("this is a new bean");
        part.setBean(newBean);
        assertEquals(newBean, part.getBean());
        part = new ApplicationPart();
        part.setBean(bean);
        assertEquals(bean, part.getBean());
    }
    
    public class MockInvalidValueStrategy implements IInvalidValueStrategy {

        /**
         * @see org.xulux.gui.IInvalidValueStrategy#checkForm(org.xulux.core.ApplicationPart)
         */
        public boolean checkForm(ApplicationPart part) {
            return false;
        }

        /**
         * @see org.xulux.gui.IInvalidValueStrategy#checkWidget(org.xulux.gui.Widget)
         */
        public boolean checkWidget(Widget widget) {
            return false;
        }

        /**
         * @see org.xulux.gui.IInvalidValueStrategy#handleInvalidValueException(org.xulux.gui.Widget, org.xulux.dataprovider.InvalidValueException)
         */
        public boolean handleInvalidValueException(Widget widget, InvalidValueException exception) {
            return false;
        }
    }
}
