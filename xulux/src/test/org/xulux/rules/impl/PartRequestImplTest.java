/*
   $Id: PartRequestImplTest.java,v 1.3 2004-03-16 15:04:15 mvdb Exp $
   
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

import org.xulux.core.ApplicationContext;
import org.xulux.core.ApplicationPart;
import org.xulux.core.PartRequest;
import org.xulux.core.SessionPart;
import org.xulux.swing.layouts.MockWidget;

/**
 * The PartRequestImplementation
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: PartRequestImplTest.java,v 1.3 2004-03-16 15:04:15 mvdb Exp $
 */
public class PartRequestImplTest extends TestCase {

    /**
     * Constructor for WidgetRequestImplTest.
     * @param name the name of the test
     */
    public PartRequestImplTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(PartRequestImplTest.class);
        return suite;
    }

    /**
     * Test the request..
     */
    public void testRequest() {
        System.out.println("testRequest");
        ApplicationPart part = new ApplicationPart("part");
        ApplicationPart part2 = new ApplicationPart();
        PartRequestImpl impl = new PartRequestImpl(null, -1);
        assertNull(impl.getWidget());
        assertEquals(PartRequest.NO_ACTION, impl.getType());
        MockWidget widget = new MockWidget("mock");
        impl = new PartRequestImpl(part2, -1);
        widget.setPart(part);
        assertNull(impl.getWidget());
        part2.setName("part2");
        assertEquals(part2, impl.getPart());
        widget.setValue("value");
        assertNull(impl.getValue());
        assertEquals("part2", impl.getName());
        part2.addWidget(widget);
        widget.setPart(part2);
        impl.setValue("value3");
        assertNotSame(impl.getValue(), "value3");
        widget.setValue("value2");
        assertNull(impl.getValue());
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
        PartRequestImpl impl = new PartRequestImpl(part, 0);
        PartRequestImpl clone = (PartRequestImpl) impl.clone();
        assertEquals(impl.getWidget(), clone.getWidget());
        assertEquals(impl.getPart(), clone.getPart());
    }

}
