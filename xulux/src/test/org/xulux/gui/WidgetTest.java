/*
   $Id: WidgetTest.java,v 1.4 2004-05-11 15:26:03 mvdb Exp $
   
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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.xulux.core.ApplicationPart;
import org.xulux.core.WidgetConfig;
import org.xulux.core.XuluxContext;
import org.xulux.guidriver.defaults.GuiDefaults;
import org.xulux.swing.widgets.Entry;

/**
 * Test of widget class.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: WidgetTest.java,v 1.4 2004-05-11 15:26:03 mvdb Exp $
 */
public class WidgetTest extends TestCase {

    /**
     * Constructor for WidgetTest.
     * @param name the name of the test
     */
    public WidgetTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite(WidgetTest.class);
        return suite;
    }

    /**
     * Test the Widget.equals(Object)
     */
    public void testEquals() {
        System.out.println("testEquals");
        Entry entry = new Entry("Name");
        entry.setPrefix("Prefix");
        assertTrue(entry.equals("Prefix.Name"));
        assertFalse(entry.equals("Name"));
        assertTrue(entry.equals(entry));
        Entry entry1 = new Entry("Test");
        assertTrue(entry1.equals("Test"));
        assertFalse(entry1.equals("Test.Test"));
        assertTrue(entry1.equals(entry1));
        assertFalse(entry1.equals(entry));
        assertFalse(entry1.equals(null));

    }
    /**
     * Test the provider functionaliyu
     */
    public void testProvider() {
        System.out.println("testProvider");
        Entry entry = new Entry("test");
        assertNull(entry.getProvider());
        ApplicationPart part = new ApplicationPart();
        part.setProvider("partProvider");
        part.addWidget(entry);
        entry.setProvider("provider");
        assertEquals("provider", entry.getProvider());
        entry.setProvider(null);
        assertEquals("partProvider", entry.getProvider());
    }
    
    /**
     * This was giving an npe..
     */
    public void testProcessInitAndDestroy() {
        System.out.println("testProcessInitAndDestroy");
        Entry entry = new Entry("entry");
        entry.processInit();
        entry.processDestroy();
        entry.setWidgetType("temp");
        GuiDefaults defaults = XuluxContext.getGuiDefaults();
        defaults.registerWidget("temp", Entry.class.getName(), "swing");
        WidgetConfig config = defaults.getWidgetConfig("temp");
        entry.processInit();
        entry.processDestroy();
        config.addWidgetTool("swing", InitAndDestroyWidgetInitializer.class.getName());
        entry.processInit();
        assertEquals(1, initCallCount);
        entry.processDestroy();
        assertEquals(1, destroyCallCount);
    }
    public static int initCallCount = 0;
    public static int destroyCallCount = 0;
    
    public class InitAndDestroyWidgetInitializer implements IWidgetInitializer {

      /**
       * @see org.xulux.gui.IWidgetInitializer#initialize(org.xulux.gui.Widget)
       */
      public void initialize(Widget widget) {
          initCallCount++;
      }

      /**
       * @see org.xulux.gui.IWidgetInitializer#destroy(org.xulux.gui.Widget)
       */
      public void destroy(Widget widget) {
          destroyCallCount++;
      }
    }
}
