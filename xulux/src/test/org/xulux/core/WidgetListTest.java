/*
   $Id: WidgetListTest.java,v 1.1 2004-03-16 15:04:15 mvdb Exp $
   
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

import java.util.Collection;

import org.xulux.gui.Widget;
import org.xulux.swing.widgets.Entry;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test the inner ApplicationPart.WidgetList class
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: WidgetListTest.java,v 1.1 2004-03-16 15:04:15 mvdb Exp $
 */
public class WidgetListTest extends TestCase {

    /**
     * Constructor for WidgetListTest.
     * @param name the name of the test
     */
    public WidgetListTest(String name) {
        super(name);
    }

    /**
     * @return the test suite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(WidgetListTest.class);
        return suite;
    }

    /**
     * Test the get, indexof, contains  methods.
     * Really complicated
     */
    public void testWidgetList() {
        System.out.println("testWidgetList");
        ApplicationPart.WidgetList list = new ApplicationPart().new WidgetList();
        Widget widget = new Entry("Test");
        Widget widget1 = new Entry("TestEntry");
        Widget widget2 = new Entry("Entry");
        widget2.setPrefix("Test");
        Widget widget3 = new Entry("Entry");
        widget3.setPrefix("NoTest");
        Widget widget4 = new Entry("Entry");
        list.add(widget);
        list.add(widget1);
        list.add(widget2);
        list.add(widget3);
        list.add(widget4);
        assertEquals(widget, list.get("Test"));
        assertEquals(widget1, list.get("TestEntry"));
        assertEquals(widget2, list.get("Test.Entry"));
        assertEquals(widget3, list.get("NoTest.Entry"));
        assertEquals(widget4, list.get("Entry"));
        assertTrue(list.contains(widget));
        assertTrue(list.contains(widget1));
        assertTrue(list.contains(widget2));
        assertTrue(list.contains(widget3));
        assertTrue(list.contains(widget4));
        assertTrue((list.indexOf(widget) != -1));
        assertTrue((list.indexOf(widget1) != -1));
        assertTrue((list.indexOf(widget2) != -1));
        assertTrue((list.indexOf(widget3) != -1));
        assertTrue((list.indexOf(widget4) != -1));
        assertTrue(list.indexOf(null) == -1);
        assertTrue(list.indexOf("bogus thingy thing") == -1);

    }

    /**
     * Test the widgetWithField method in widgetList.
     * It should return a list of all widgets
     * that make use / reference the field specified
     */
    public void testGetWidgetsWithField() {
        System.out.println("testGetWidgetsWithField");
        ApplicationPart.WidgetList list = new ApplicationPart().new WidgetList();
        Entry entry = new Entry("test");
        entry.setField("field1");
        Entry entry1 = new Entry("test1");
        entry1.setField("field2");
        Entry entry2 = new Entry("test2");
        entry2.setField("field1");
        Entry entry3 = new Entry("test3");
        Entry entry4 = new Entry("test4");
        entry4.setField("?test3.test");

        list.add(entry);
        list.add(entry1);
        list.add(entry2);
        list.add(entry3);
        list.add(entry4);
        assertNull(list.getWidgetsWithField("bogus"));
        Collection field1List = list.getWidgetsWithField("field1");
        assertEquals(2, field1List.size());
        assertTrue(field1List.contains(entry2));
        assertTrue(field1List.contains(entry));
        Collection field2List = list.getWidgetsWithField("field2");
        assertEquals(1, field2List.size());
        assertTrue(field2List.contains(entry1));
        // check if the widgetlist can also find fields with pointers to other fields..
        Collection field3List = list.getWidgetsWithField("test3");
        assertEquals(1, field3List.size());
        assertTrue(field3List.contains(entry4));
    }
}
