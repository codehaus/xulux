/*
 $Id: GuiUtilsTest.java,v 1.3 2003-12-15 23:37:45 mvdb Exp $

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
package org.xulux.nyx.gui;

import java.util.List;

import org.xulux.nyx.context.ApplicationPart;
import org.xulux.nyx.context.PartRequest;
import org.xulux.nyx.rules.Rule;
import org.xulux.nyx.swing.layouts.MockWidget;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test case for the GuiUtils.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: GuiUtilsTest.java,v 1.3 2003-12-15 23:37:45 mvdb Exp $
 */
public class GuiUtilsTest extends TestCase {

    /**
     * Constructor for GuiUtilsTest.
     * @param name the name of the test
     */
    public GuiUtilsTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(GuiUtilsTest.class);
        return suite;
    }

    /**
     * Test the constructor
     */
    public void testConstructor() {
        new GuiUtils();
    }

    /**
     * Test processCancel when something
     * other than a defaultaction is calling
     * the processCancel
     */
    public void testProcessCancelWithoutDefaultAction() {
        System.out.println("testProcessCancelWithoutDefaultAction");
        GuiUtils.processCancel(null);
        MockWidget widget = new MockWidget("window");
        ApplicationPart part = new ApplicationPart("test");
        widget.setPart(part);
        part.addWidget(widget);
        assertFalse(GuiUtils.processCancel(widget));
        MockWidget w1 = new MockWidget("w1");
        MockWidget w2 = new MockWidget("w2");
        part.addWidget(w1);
        part.addWidget(w2);
        w1.setPart(part);
        w2.setPart(part);
        w1.setProperty("defaultaction", "cancel");
        w2.setProperty("defaultaction", "ok");
        InternalRule rule = new InternalRule();
        w1.registerRule(rule);
        assertTrue(GuiUtils.processCancel(widget));
        assertEquals(1, rule.callCount);
    }

    /**
     * Test processCancel when something
     * other than a defaultaction is calling
     * the processCancel
     */
    public void testProcessCancelWithDefaultAction() {
        System.out.println("testProcessCancelWithDefaultAction");
        GuiUtils.processCancel(null);
        MockWidget widget = new MockWidget("window");
        widget.setProperty("defaultaction", "cancel");
        ApplicationPart part = new ApplicationPart("test");
        widget.setPart(part);
        part.addWidget(widget);
        assertTrue(GuiUtils.processCancel(widget));
        MockWidget parent = new MockWidget("parent");
        MockWidget child = new MockWidget("child");
        part.addWidget(parent);
        part.addWidget(child);
        parent.setPart(part);
        widget.setParent(child);
        child.setParent(parent);
        child.setPart(part);
        child.setProperty("defaultaction", "ok");
        InternalRule rule = new InternalRule();
        widget.registerRule(rule);
        assertTrue(GuiUtils.processCancel(widget));
        assertEquals(1, rule.callCount);
    }

    /**
     * Test the GetWidgetsWithProperty method
     */
    public void testGetWidgetsWithProperty() {
        System.out.println("testGetWidgetsWithProperty");
        assertNull(GuiUtils.getWidgetsWithProperty(null, null));
        MockWidget widget = new MockWidget("mockwidget");
        assertNull(GuiUtils.getWidgetsWithProperty("martin", widget));
        ApplicationPart part = new ApplicationPart("part");
        widget.setPart(part);
        assertNull(GuiUtils.getWidgetsWithProperty("martin", widget));
        part.addWidget(widget);
        MockWidget bogusWidget = new MockWidget("bogus");
        MockWidget martinWidget = new MockWidget("martin");
        part.addWidget(bogusWidget);
        part.addWidget(martinWidget);
        assertNull(GuiUtils.getWidgetsWithProperty("test", widget));
        martinWidget.setProperty("martin", "martin");
        MockWidget also = new MockWidget("also");
        also.setProperty("martin", "jan");
        part.addWidget(also);
        also.setPart(part);
        List list = GuiUtils.getWidgetsWithProperty("martin", widget);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals(martinWidget, list.get(0));
        assertEquals(also, list.get(1));
    }

    /**
     * Internal rule to see if post rules are fired.
     */
    public class InternalRule extends Rule {

        /**
         * The calcount to post
         */
        protected int callCount = 0;

        /**
         * @see org.xulux.nyx.rules.Rule#pre(org.xulux.nyx.context.PartRequest)
         */
        public void pre(PartRequest request) {
        }

        /**
         * @see org.xulux.nyx.rules.Rule#post(org.xulux.nyx.context.PartRequest)
         */
        public void post(PartRequest request) {
            callCount++;
        }
    }
}
