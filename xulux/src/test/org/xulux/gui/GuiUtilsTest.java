/*
   $Id: GuiUtilsTest.java,v 1.5 2004-04-01 16:15:09 mvdb Exp $
   
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

import java.util.List;

import org.xulux.core.ApplicationPart;
import org.xulux.core.PartRequest;
import org.xulux.rules.Rule;
import org.xulux.swing.layouts.MockWidget;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test case for the GuiUtils.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: GuiUtilsTest.java,v 1.5 2004-04-01 16:15:09 mvdb Exp $
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
        widget.setProperty("defaultaction", "bogus");
        assertFalse(GuiUtils.processCancel(widget));
        assertEquals(1, rule.callCount);
        assertEquals(true, part.isPartDestroyed());
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
