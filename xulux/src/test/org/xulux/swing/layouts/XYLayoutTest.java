/*
   $Id: XYLayoutTest.java,v 1.2 2004-05-18 00:01:14 mvdb Exp $
   
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

import org.xulux.core.ApplicationPart;
import org.xulux.core.XuluxContext;
import org.xulux.gui.IXuluxLayout;
import org.xulux.gui.Widget;
import org.xulux.guidriver.XuluxGuiDriver;
import org.xulux.swing.widgets.Combo;
import org.xulux.swing.widgets.Label;
import org.xulux.swing.widgets.Window;


/**
 * A class to to test the layoutmanagers for swing
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: XYLayoutTest.java,v 1.2 2004-05-18 00:01:14 mvdb Exp $
 */
public class XYLayoutTest extends TestCase
{

    /**
     * Constructor for LayoutTest.
     * @param name the testname
     */
    public XYLayoutTest(String name)
    {
        super(name);
    }

    /**
     * @return The test suite
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite(XYLayoutTest.class);
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
        XuluxContext.getInstance();
        XuluxGuiDriver handler = new XuluxGuiDriver();
        String xml = "org/xulux/swing/layouts/XuluxOnNative.xml";
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(xml);
        ApplicationPart part = handler.read(stream, null);
        XYLayout layout = new XYLayout();
        JPanel panel = new JPanel(layout);
        XuluxContext.getInstance().registerPart(part);
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


    public void testAddAndRemoveWidget() {
        System.out.println("testAddAndRemoveWidget");
        XYLayout layout = new XYLayout();
        MockWidget label1 = new MockWidget("label1");
        MockWidget label2 = new MockWidget("label2");
        JLabel l1 = new JLabel("l1");
        JLabel l2 = new JLabel("l2");
        label1.setNativeWidget(l1);
        label2.setNativeWidget(l2);
        layout.addWidget(label1);
        assertEquals(1, layout.map.size());
        layout.removeWidget(label1);
        assertEquals(0, layout.map.size());
        layout.addWidget(label2);
        assertEquals(label2, layout.map.get(l2));
        label1.setNativeWidget("bogus");
        layout.addWidget(label1);
        assertEquals(1, layout.map.size());
        layout.removeWidget(label1);
        assertEquals(1, layout.map.size());
    }
    
    public void testDestroy() {
        System.out.println("testDestroy");
        // we don't do anything in destroy yet..
        IXuluxLayout layout = new XYLayout();
        layout.destroy();
    }

    public void testConstructor() {
        System.out.println("testConstructor");
        Window window = new Window("window");
        XYLayout layout = new XYLayout(window);
        assertEquals(window, layout.getParent());
        layout = new XYLayout();
        layout.setParent(window);
        assertEquals(window, layout.getParent());
    }    
    
    public static void main(String[] args) {
        new XYLayoutTest("LayoutTest").testNativeGetLayoutSize();
    }
}
