/*
   $Id: NativeWidgetHandlerTest.java,v 1.2 2004-01-28 15:22:08 mvdb Exp $
   
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
package org.xulux.swing.util;

import javax.swing.JLabel;
import javax.swing.JPanel;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.xulux.gui.Widget;
import org.xulux.swing.layouts.MockWidget;

/**
 * Test the Swing nativeWidgethandler
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NativeWidgetHandlerTest.java,v 1.2 2004-01-28 15:22:08 mvdb Exp $
 */
public class NativeWidgetHandlerTest extends TestCase {

    /**
     * Constructor for NativeWidgetHandlerTest.
     * @param name the name of the test
     */
    public NativeWidgetHandlerTest(String name) {
        super(name);
    }
    /**
     * @return The test suite
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite(NativeWidgetHandlerTest.class);
        return suite;
    }

    /**
     * Test the getWidget method
     */
    public void testGetWidget() {
        System.out.println("testGetWidget");
        NativeWidgetHandler handler = new NativeWidgetHandler();
        assertNull(handler.getWidget(null, null));
        MockWidget widget = new MockWidget("mockwidget");
        assertFalse(widget.canContainChildren());
        JLabel label = new JLabel();
        assertNull(handler.getWidget(label, widget));
        assertNull(handler.getWidget(new Object(), widget));
        assertNull(handler.getWidget("javax.swing.JButton", widget));
        assertNull(handler.getWidget("bogus.widget.clazz", widget));
        assertNull(handler.getWidget(label, widget));
        JPanel panel = new JPanel();
        widget.setNativeWidget(panel);
        widget.setRootWidget(true);
        assertEquals(widget, handler.getWidget(label, widget));
        int compCount = panel.getComponentCount();
        assertEquals(label, panel.getComponents()[0]);
        assertNull(handler.getWidget(new Object(), widget));
        assertEquals(compCount, panel.getComponentCount());
        widget.setNativeWidget("bogus");
        assertNull(handler.getWidget(label, widget));
    }

    /**
     * Test the setLocation method
     */
    public void testLocation() {
        System.out.println("testLocation");
        NativeWidgetHandler handler = new NativeWidgetHandler();
        handler.setLocationOnWidget((Object) null, 0, 0);
        handler.setLocationOnWidget((Widget) null, 0, 0);
        handler.setLocationOnWidget("bogus", 0, 0);
        // set a direct location on the component
        JLabel label = new JLabel();
        handler.setLocationOnWidget(label, 10, 11);
        assertEquals(10, label.getLocation().x);
        assertEquals(11, label.getLocation().y);
        MockWidget widget = new MockWidget("mockwidget");
        handler.setLocationOnWidget(widget, 0, 0);
        JPanel panel = new JPanel();
        widget.setNativeWidget(panel);
        widget.setRootWidget(true);
        // we don't have any children, so nothing should change
        handler.setLocationOnWidget(widget, 12, 13);
        assertEquals(10, label.getLocation().x);
        assertEquals(11, label.getLocation().y);
        // use our own dogfood, so add the label to the widget
        assertEquals(widget, handler.getWidget(label, widget));
        handler.setLocationOnWidget(widget, 12, 13);
        assertEquals(12, label.getLocation().x);
        assertEquals(13, label.getLocation().y);
    }

    /**
     * Test the addWidgetToParent method
     */
    public void testAddWidgetToParent() {
        System.out.println("testAddWidgetToParent");
        NativeWidgetHandler handler = new NativeWidgetHandler();
        handler.addWidgetToParent(null, null);
        handler.addWidgetToParent(null, "bogus");
        JPanel panel = new JPanel();
        handler.addWidgetToParent(null, panel);
        MockWidget widget = new MockWidget("mockwidget");
        handler.addWidgetToParent(widget, panel);
        widget.setNativeWidget("bogus");
        handler.addWidgetToParent(widget, panel);
        JLabel label = new JLabel();
        widget.setNativeWidget(label);
        handler.addWidgetToParent(widget, panel);
        assertEquals(label, panel.getComponents()[0]);
    }
}
