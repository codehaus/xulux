/*
 $Id: WidgetListTest.java,v 1.2 2003-12-18 01:17:35 mvdb Exp $

 Copyright 2002-2003 (C) The Xulux Project. All Rights Reserved.

 Redistribution and use of this software and associated documentation
 ("Software"), with or without modification, are permitted provided
 that the following conditions are met:

 1. Redistributions of source code must retain copyright
    statements and notices.  Redistributions must also contain a
    copy of this document.

 2. Redistributions in binary form must reproduce the
    above copyright notice, this list of conditions and the
    following disclaimer in the documentation and/or other
    materials provided with the distribution.

 3. The name "xulux" must not be used to endorse or promote
    products derived from this Software without prior written
    permission of The Xulux Project. For written permission,
    please contact martin@mvdb.net.

 4. Products derived from this Software may not be called "xulux"
    nor may "xulux" appear in their names without prior written
    permission of the Xulux Project. "xulux" is a registered
    trademark of the Xulux Project.

 5. Due credit should be given to the Xulux Project
    (http://xulux.org/).

 THIS SOFTWARE IS PROVIDED BY THE XULUX PROJECT AND CONTRIBUTORS
 ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 THE XULUX PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 OF THE POSSIBILITY OF SUCH DAMAGE.

 */
package org.xulux.context;

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
 * @version $Id: WidgetListTest.java,v 1.2 2003-12-18 01:17:35 mvdb Exp $
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
