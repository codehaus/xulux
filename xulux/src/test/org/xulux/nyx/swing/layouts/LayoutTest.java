/*
 $Id: LayoutTest.java,v 1.8 2003-11-25 19:23:53 mvdb Exp $

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
package org.xulux.nyx.swing.layouts;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.xulux.nyx.gui.Widget;
import org.xulux.nyx.swing.widgets.Combo;
import org.xulux.nyx.swing.widgets.Label;
import org.xulux.nyx.swing.widgets.Window;


/**
 * A class to to test the layoutmanagers for swing
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: LayoutTest.java,v 1.8 2003-11-25 19:23:53 mvdb Exp $
 */
public class LayoutTest extends TestCase
{

    /**
     * Constructor for LayoutTest.
     * @param name the testname
     */
    public LayoutTest(String name)
    {
        super(name);
    }

    /**
     * @return The test suite
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite(LayoutTest.class);
        return suite;
    }
    /**
     * Test the addLayoutComponent
     */
    public void testAddLayoutCompenent() {
        MockWidget parent = new MockWidget("parent");
        XYLayout layout = new XYLayout(parent);
        assertEquals(parent, layout.getParent());
        MockWidget child1 = new MockWidget("child1");
        JLabel label = new JLabel();
        child1.setNativeWidget(label);
        layout.addLayoutComponent((Component) child1.getNativeWidget(), child1);
        assertTrue(layout.map.containsValue(child1));
        assertTrue(layout.map.containsKey(child1.getNativeWidget()));
        assertEquals(child1, layout.map.get(label));
        assertEquals(layout.map.size(), 1);
    }

    /**
     * Test the getRectangle with the widget as parameter
     */
    public void testgetRectangleWidget() {
        System.out.println("testRectangleWidget");
        MockWidget widget = new MockWidget("parent");
        assertEquals(0, widget.getRectangle().getWidth());
        assertEquals(0, widget.getRectangle().getHeight());
        // the widget has no size, so the size of the
        // widget should be set to 100 by 100.
        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(100, 200));
        XYLayout layout = new XYLayout();
        Rectangle rect = layout.getRectangle(widget, label);
        assertEquals(100, rect.width);
        assertEquals(200, rect.height);
        assertEquals(100, widget.getRectangle().getWidth());
        assertEquals(200, widget.getRectangle().getHeight());
        widget.setSize(-12, -30);
        rect = layout.getRectangle(widget, label);
        assertEquals(100, rect.width);
        assertEquals(200, rect.height);
        assertEquals(100, widget.getRectangle().getWidth());
        assertEquals(200, widget.getRectangle().getHeight());
        widget.setSize(150, 250);
        rect = layout.getRectangle(widget, label);
        assertEquals(150, rect.width);
        assertEquals(250, rect.height);
        assertEquals(100, label.getPreferredSize().width);
        assertEquals(200, label.getPreferredSize().height);
        assertEquals(150, widget.getRectangle().getWidth());
        assertEquals(250, widget.getRectangle().getHeight());
    }

    /**
     * Test the getRectangle Swing method
     */
    public void testGetRectangleSwing() {
        System.out.println("testRectangleWidget");
        MockWidget widget = new MockWidget("parent");
        assertEquals(0, widget.getRectangle().getWidth());
        assertEquals(0, widget.getRectangle().getHeight());
        // the widget has no size, so the size of the
        // widget should be set to 100 by 100.
        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(100, 200));
        XYLayout layout = new XYLayout();
        assertTrue(layout.isFirstLayout());
        layout.getRectangle(label, null);
        assertFalse(layout.isFirstLayout());
        Rectangle rect = layout.getRectangle(label, null);
        assertEquals(100, rect.width);
        assertEquals(200, rect.height);
        Insets insets = new Insets(10, 10, 0, 0);
        layout.setFirstLayout(true);
        label.setBounds(100, 200, 100, 200);
        rect = layout.getRectangle(label, insets);
        // the insets are added
        assertEquals(100, rect.width);
        assertEquals(200, rect.height);
        rect = layout.getRectangle(label, insets);
        assertEquals(90, label.getBounds().x);
        assertEquals(190, label.getBounds().y);
        assertEquals(100, rect.width);
        assertEquals(200, rect.height);
    }

    /**
     * This will only test things that are actually being used
     */
    public void testXYLayout()
    {
        System.out.println("testXYLayout");
        XYLayout xy = new XYLayout();
        JPanel panel = new JPanel(xy);
        Label label1 = new Label("label1");
        label1.setSize(10, 10);
        label1.setPosition(10, 10);
        Label label2 = new Label("label2");
        label2.setSize(10, 10);
        label2.setPosition(10, 30);
        JLabel jlabel1 = new JLabel("label1");
        JLabel jlabel2 = new JLabel("label2");
        panel.add(jlabel1, label1);
        panel.add(jlabel2, label2);
        assertEquals(label1, xy.map.get(jlabel1));
        assertEquals(label2, xy.map.get(jlabel2));
        JFrame frame = new JFrame("LayoutTest");
        frame.getContentPane().add(panel);
        frame.setSize(100, 100);
        frame.pack();
        for (int i = 0; i < panel.getComponentCount(); i++)
        {
            int y = 10;
            if (i == 1) {
                y = 30;
            }
            Component component = panel.getComponent(i);
            assertEquals(new Dimension(10, 10), component.getSize());
            Rectangle rect = component.getBounds();
            assertEquals(new Dimension(10, 10), rect.getSize());
            assertEquals(10, (int) rect.getX());
            assertEquals(y, (int) rect.getY());
        }
        assertEquals(jlabel1, panel.getComponentAt(10, 10));
        assertEquals(jlabel2, panel.getComponentAt(10, 30));
        frame.dispose();
        panel.removeAll();
        assertTrue(xy.map.isEmpty());
    }

    /**
     * the XY with widgests
     */
    public void testXYWithWidgets()
    {
        System.out.println("testXYWithWidgets");
        Window window = new Window("Window");
        Combo combo = new Combo("Combo");
        combo.setSize(120, 21);
        combo.setPosition(4, 10);
        window.addChildWidget(combo);
        window.initialize();
        XYLayout xy = (XYLayout) ((JFrame) window.getNativeWidget()).getContentPane().getLayout();
        Dimension dim = xy.preferredLayoutSize(((JFrame) window.getNativeWidget()).getContentPane());
        assertEquals(124, dim.width);
        assertEquals(31, dim.height);
    }

    /**
     * Test null values passed into all methods.
     */
    public void testNulls() {
        XYLayout layout = new XYLayout(null);
        layout.addLayoutComponent((Component) null, (Object) null);
        layout.addLayoutComponent((String) null, (Component) null);
        assertFalse(layout.equals(null));
        layout.getLayoutAlignmentX(null);
        layout.getLayoutAlignmentY(null);
        layout.getRectangle((Widget) null, (Component) null);
        layout.getRectangle((Component) null, (Insets) null);
        layout.invalidateLayout(null);
        layout.layoutContainer(null);
        layout.maximumLayoutSize(null);
        layout.minimumLayoutSize(null);
        layout.removeLayoutComponent(null);
    }

    /**
     * Test the Simple Getters.
     */
    public void testSimpleGetters() {
        Window window = new Window("window");
        XYLayout layout = new XYLayout(window);
        assertEquals(window, layout.getParent());
        layout = new XYLayout();
        assertNull(layout.getParent());
        layout.setParent(window);
        assertEquals(window, layout.getParent());
        assertEquals("Value changed. please test", 0.5F, layout.getLayoutAlignmentX(null), 0);
        assertEquals("Value changed. please test", 0.5F, layout.getLayoutAlignmentY(null), 0);
    }
}
