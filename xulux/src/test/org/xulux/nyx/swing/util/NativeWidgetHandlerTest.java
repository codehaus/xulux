/*
 $Id: NativeWidgetHandlerTest.java,v 1.1 2003-12-11 19:57:37 mvdb Exp $

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
package org.xulux.nyx.swing.util;

import javax.swing.JLabel;
import javax.swing.JPanel;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.xulux.nyx.gui.Widget;
import org.xulux.nyx.swing.layouts.MockWidget;

/**
 * Test the Swing nativeWidgethandler
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NativeWidgetHandlerTest.java,v 1.1 2003-12-11 19:57:37 mvdb Exp $
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
