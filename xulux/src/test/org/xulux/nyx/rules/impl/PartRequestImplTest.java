/*
 $Id: PartRequestImplTest.java,v 1.1 2003-12-15 23:38:03 mvdb Exp $

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
package org.xulux.nyx.rules.impl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.xulux.nyx.context.ApplicationContext;
import org.xulux.nyx.context.ApplicationPart;
import org.xulux.nyx.context.PartRequest;
import org.xulux.nyx.context.SessionPart;
import org.xulux.nyx.swing.layouts.MockWidget;

/**
 * The PartRequestImplementation
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: PartRequestImplTest.java,v 1.1 2003-12-15 23:38:03 mvdb Exp $
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
