/*
   $Id: WidgetRequestImplTest.java,v 1.2 2004-01-28 15:22:04 mvdb Exp $
   
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
package org.xulux.rules.impl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.xulux.context.ApplicationContext;
import org.xulux.context.ApplicationPart;
import org.xulux.context.PartRequest;
import org.xulux.context.SessionPart;
import org.xulux.swing.layouts.MockWidget;

/**
 * The widgetRequestImplementation
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: WidgetRequestImplTest.java,v 1.2 2004-01-28 15:22:04 mvdb Exp $
 */
public class WidgetRequestImplTest extends TestCase {

    /**
     * Constructor for WidgetRequestImplTest.
     * @param name the name of the test
     */
    public WidgetRequestImplTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(WidgetRequestImplTest.class);
        return suite;
    }

    /**
     * Test the request..
     */
    public void testRequest() {
        System.out.println("testRequest");
        ApplicationPart part = new ApplicationPart("part");
        WidgetRequestImpl impl = new WidgetRequestImpl(null, -1);
        assertNull(impl.getWidget());
        assertEquals(PartRequest.NO_ACTION, impl.getType());
        MockWidget widget = new MockWidget("mock");
        impl = new WidgetRequestImpl(widget, -1);
        widget.setPart(part);
        assertEquals(part, impl.getPart());
        assertEquals(widget, impl.getWidget());
        ApplicationPart part2 = new ApplicationPart();
        part2.setName("part2");
        impl.setPart(part2);
        assertEquals(part2, impl.getPart());
        widget.setValue("value");
        assertEquals("value", impl.getValue());
        assertEquals("mock", impl.getName());
        part2.addWidget(widget);
        widget.setPart(part2);
        impl.setValue("value2");
        assertEquals("value2", impl.getValue());
        assertEquals(widget, impl.getWidget("mock"));
        assertTrue(impl.getSession() instanceof SessionPart);
        assertNull(impl.getValue("bogus.field"));
        ApplicationContext.getInstance().registerPart(part2);
        assertEquals("value2", impl.getValue("mock"));
        assertEquals("value2", impl.getValue("part2.mock"));
    }
    /**
     * Test cloning
     */
    public void testClone() {
        System.out.println("testClone");
        MockWidget widget = new MockWidget("mock");
        ApplicationPart part = new ApplicationPart("part");
        WidgetRequestImpl impl = new WidgetRequestImpl(widget, 0);
        impl.setPart(part);
        WidgetRequestImpl clone = (WidgetRequestImpl) impl.clone();
        assertEquals(impl.getWidget(), clone.getWidget());
        assertEquals(impl.getPart(), clone.getPart());
    }

}
