/*
 $Id: LayoutTest.java,v 1.2 2004-01-28 12:24:03 mvdb Exp $

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
package org.xulux.swing.layouts;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.io.InputStream;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.xulux.context.ApplicationContext;
import org.xulux.context.ApplicationPart;
import org.xulux.context.ApplicationPartHandler;
import org.xulux.gui.Widget;
import org.xulux.swing.widgets.Combo;
import org.xulux.swing.widgets.Label;
import org.xulux.swing.widgets.Window;


/**
 * A class to to test the layoutmanagers for swing
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: LayoutTest.java,v 1.2 2004-01-28 12:24:03 mvdb Exp $
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
    public void testAddLayoutComponent() {
        System.out.println("testAddLayoutComponent");
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
        System.out.println("testRectangleSwing");
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
     * Test getLayoutSize method and the getPreferredLayoutSize
     * @todo this test needs redoing. Testing with only native widgets is useless,
     *       since this is an XYLayout and not anothe layout that automatically
     *       figures out a nice position for native widgets.
     */
    public void testGetLayoutSize() {
        System.out.println("testGetLayoutSize");
        XYLayout layout = new XYLayout();
        JPanel nativePanel = new JPanel();
        nativePanel.setPreferredSize(new Dimension(100, 100));
        JLabel nativeLabel1 = new JLabel();
        JLabel nativeLabel2 = new JLabel();
        JLabel nativeLabel3 = new JLabel();
        nativeLabel1.setPreferredSize(new Dimension(25, 25));
        //nativeLabel1.setLocation(0, 0);
        nativeLabel2.setPreferredSize(new Dimension(25, 25));
        nativeLabel3.setPreferredSize(new Dimension(25, 25));
        MockWidget panel = new MockWidget("panel");
        panel.setNativeWidget(nativePanel);
        panel.setSize(100, 100);
        MockWidget label1 = new MockWidget("label1");
        label1.setNativeWidget(nativeLabel1);
        //label1.setSize(50, 50);
        MockWidget label2 = new MockWidget("label2");
        label2.setNativeWidget(nativeLabel2);
        //label2.setSize(50, 50);
        nativePanel.setLayout(layout);
        nativePanel.add(nativeLabel1, label1);
        nativePanel.add(nativeLabel2, label2);
        // we now have 2 widgets in the layout mapping..
        assertEquals(2, layout.map.size());
        Dimension dim = layout.getLayoutSize(nativePanel);
        assertEquals(25, dim.width);
        assertEquals(25, dim.height);
        dim = layout.preferredLayoutSize(nativePanel);
        assertEquals(25, dim.width);
        assertEquals(25, dim.height);
        // just add a native component, without any nyx magic
        nativePanel.add(nativeLabel3);
        dim = layout.getLayoutSize(nativePanel);
        assertEquals(50, dim.width);
        assertEquals(50, dim.height);
        dim = layout.preferredLayoutSize(nativePanel);
        assertEquals(50, dim.width);
        assertEquals(50, dim.height);
        TitledBorder border = new TitledBorder("title");
        nativePanel.setBorder(border);
        int height = nativePanel.getInsets().bottom + nativePanel.getInsets().top;
        int width = nativePanel.getInsets().left + nativePanel.getInsets().right;
        dim = layout.getLayoutSize(nativePanel);
        assertEquals(50 + width, dim.width);
        assertEquals(50 + height, dim.height);
        dim = layout.preferredLayoutSize(nativePanel);
        assertEquals(50 + width, dim.width);
        assertEquals(50 + height, dim.height);
        label1.setVisible(false);
        label2.setVisible(false);
        dim = layout.preferredLayoutSize(nativePanel);
        assertEquals(25 + width, dim.width);
        assertEquals(25 + height, dim.height);
    }

    /**
     * Test the method layoutContainer()
     */
    public void testLayoutContainer() {
        XYLayout layout = new XYLayout();
        JPanel nativePanel = new JPanel(layout);
        JLabel nativeLabel1 = new JLabel();
        JLabel nativeLabel2 = new JLabel();
        JLabel nativeLabel3 = new JLabel();
        MockWidget label1 = new MockWidget("label1");
        label1.setNativeWidget(nativeLabel1);
        nativePanel.add(nativeLabel1, label1);
        MockWidget label2 = new MockWidget("label2");
        nativePanel.add(nativeLabel2, label2);
        assertEquals(2, layout.map.size());
        label1.setSize(100, 75);
        label2.setSize(110, 85);
        layout.layoutContainer(nativePanel);
        assertEquals(100, nativeLabel1.getPreferredSize().width);
        assertEquals(75, nativeLabel1.getPreferredSize().height);
        assertEquals(110, nativeLabel2.getPreferredSize().width);
        assertEquals(85, nativeLabel2.getPreferredSize().height);
        assertEquals(100, nativeLabel1.getBounds().width);
        assertEquals(75, nativeLabel1.getBounds().height);
        assertEquals(110, nativeLabel2.getBounds().width);
        assertEquals(85, nativeLabel2.getBounds().height);
        nativeLabel3.setPreferredSize(new Dimension(10, 11));
        nativePanel.add(nativeLabel3);
        layout.layoutContainer(nativePanel);
        assertEquals(100, nativeLabel1.getPreferredSize().width);
        assertEquals(75, nativeLabel1.getPreferredSize().height);
        assertEquals(110, nativeLabel2.getPreferredSize().width);
        assertEquals(85, nativeLabel2.getPreferredSize().height);
        assertEquals(100, nativeLabel1.getBounds().width);
        assertEquals(75, nativeLabel1.getBounds().height);
        assertEquals(110, nativeLabel2.getBounds().width);
        assertEquals(85, nativeLabel2.getBounds().height);
        assertEquals(10, nativeLabel3.getBounds().width);
        assertEquals(11, nativeLabel3.getBounds().height);
        assertEquals(0, nativeLabel1.getBounds().x);
        assertEquals(0, nativeLabel1.getBounds().y);
        assertEquals(0, nativeLabel2.getBounds().x);
        assertEquals(0, nativeLabel2.getBounds().y);
    }
    /**
     * test layoutContainer method using insets.
     */
    public void testLayoutContainerWithInsets() {
        System.out.println("testLayoutContainerWithInsets");
        // start with a new layout and fresh widgest
        XYLayout layout = new XYLayout();
        JPanel nativePanel = new JPanel(layout);
        LineBorder border = new LineBorder(Color.black);
        nativePanel.setBorder(border);
        JLabel nativeLabel1 = new JLabel();
        JLabel nativeLabel2 = new JLabel();
        JLabel nativeLabel3 = new JLabel();
        MockWidget label1 = new MockWidget("label1");
        label1.setNativeWidget(nativeLabel1);
        nativePanel.add(nativeLabel1, label1);
        MockWidget label2 = new MockWidget("label2");
        nativePanel.add(nativeLabel2, label2);
        assertEquals(2, layout.map.size());
        label1.setSize(100, 75);
        label2.setSize(110, 85);
        layout.layoutContainer(nativePanel);
        assertEquals(100, nativeLabel1.getPreferredSize().width);
        assertEquals(75, nativeLabel1.getPreferredSize().height);
        assertEquals(110, nativeLabel2.getPreferredSize().width);
        assertEquals(85, nativeLabel2.getPreferredSize().height);
        assertEquals(100, nativeLabel1.getBounds().width);
        assertEquals(75, nativeLabel1.getBounds().height);
        assertEquals(110, nativeLabel2.getBounds().width);
        assertEquals(85, nativeLabel2.getBounds().height);
        assertEquals(1, nativeLabel1.getBounds().x);
        assertEquals(1, nativeLabel1.getBounds().y);
        assertEquals(1, nativeLabel2.getBounds().x);
        assertEquals(1, nativeLabel2.getBounds().y);
        // layout again and x,y should still be 1,1
        layout.layoutContainer(nativePanel);
        assertEquals(1, nativeLabel1.getBounds().x);
        assertEquals(1, nativeLabel1.getBounds().y);
        assertEquals(1, nativeLabel2.getBounds().x);
        assertEquals(1, nativeLabel2.getBounds().y);
    }

    /**
     * Test layoutContainer with Component which isn't a JComponent
     */
    public void testLayoutContainerWithComponent() {
        System.out.println("testLayoutContainerWithComponent");
        XYLayout layout = new XYLayout();
        JPanel nativePanel = new JPanel(layout);
        java.awt.Label nativeLabel = new java.awt.Label();
        MockWidget label1 = new MockWidget("label1");
        label1.setNativeWidget(nativeLabel);
        label1.setSize(10, 9);
        nativePanel.add(nativeLabel, label1);
        layout.layoutContainer(nativePanel);
        assertEquals(10, nativeLabel.getSize().width);
        assertEquals(9, nativeLabel.getSize().height);
        assertEquals(10, nativeLabel.getPreferredSize().width);
        assertEquals(9, nativeLabel.getPreferredSize().height);
    }

    /**
     * This will only test things that are actually being used
     * @todo move this test to gui test, since if fires up a frame
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
        System.out.println("testNulls");
        XYLayout layout = new XYLayout(null);
        layout.addLayoutComponent((Component) null, (Object) null);
        layout.addLayoutComponent((String) null, (Component) null);
        assertFalse(layout.equals(null));
        layout.getLayoutAlignmentX(null);
        layout.getLayoutAlignmentY(null);
        assertNull(layout.getRectangle((Widget) null, (Component) null));
        assertNull(layout.getRectangle((Component) null, (Insets) null));
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
        System.out.println("testSimpleGetters");
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

    /**
     * Test the layoutsize when just using a native widget as the parent.
     */
    public void testNativeGetLayoutSize() {
        System.out.println("testNativeGetLayoutSize");
        // x,y : 0,0 w,h : 100,21 layoutsize : 100,21
        // x,Y : 0,
        ApplicationContext.getInstance();
        ApplicationPartHandler handler = new ApplicationPartHandler();
        String xml = "org/xulux/swing/layouts/XuluxOnNative.xml";
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(xml);
        ApplicationPart part = handler.read(stream, null);
        XYLayout layout = new XYLayout();
        JPanel panel = new JPanel(layout);
        ApplicationContext.getInstance().registerPart(part);
        part.setParentWidget(panel);
        part.activate();
        JFrame frame = new JFrame("XuluxOnNative");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.show();
        Dimension dim = ((Component) part.getRootWidget()).getPreferredSize();
        dim.width += frame.getBounds().getWidth();
        dim.height += frame.getBounds().getHeight();
        frame.setSize(dim.width, dim.height);
        frame.getContentPane().add((Component) part.getRootWidget());
        frame.pack();
        Widget bC = part.getWidget("Button:Save");
        JComponent nC = (JComponent) bC.getNativeWidget();
        System.out.println("Preferred size : "  + nC.getPreferredSize());
        System.out.println("Location : " + nC.getLocation());
    }

    public static void main(String[] args) {
        new LayoutTest("LayoutTest").testNativeGetLayoutSize();
    }
}
