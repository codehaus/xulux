/*
   $Id: SwingGridBagLayoutTest.java,v 1.1 2004-05-24 18:12:34 mvdb Exp $
   
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

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.JComponent;

import org.xulux.gui.Widget;
import org.xulux.gui.WidgetFactory;

import junit.framework.TestCase;

/**
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SwingGridBagLayoutTest.java,v 1.1 2004-05-24 18:12:34 mvdb Exp $
 */
public class SwingGridBagLayoutTest extends TestCase {

    /**
     * Constructor for SwingGridBagLayoutTest.
     * @param name the name of the test
     */
    public SwingGridBagLayoutTest(String name) {
        super(name);
    }

    public void testConstructor() {
        System.out.println("testConstructor");
        SwingGridBagLayout layout = new SwingGridBagLayout();
        assertNotNull(layout.layout);
        assertNotNull(layout.c);
    }
    
    public void testWrappers() {
        System.out.println("testWrappers");
        SwingGridBagLayout layout = new SwingGridBagLayout();
        layout.addLayoutComponent((String) null, null);
        Widget label = WidgetFactory.getWidget("label", "label");
        Widget widget = WidgetFactory.getWidget("panel", "panel");
        widget.addChildWidget(label);
        layout.setParent(widget);
        Container container = (Container) widget.getNativeWidget();
        layout.layoutContainer(container);
        JComponent comp = (JComponent) label.getNativeWidget();
        Dimension minDim = new Dimension(11,12);
        comp.setMinimumSize(minDim);
        assertEquals(minDim, layout.minimumLayoutSize(container));
        // max dim is ignored..
        Dimension maxDim = new Dimension(99,98);
        comp.setMaximumSize(maxDim);
        assertEquals(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE), layout.maximumLayoutSize(container));
        Dimension preferred = new Dimension(33,34);
        comp.setPreferredSize(preferred);
        assertEquals(preferred, layout.preferredLayoutSize(container));
        // removing just removes the constraints.
        GridBagConstraints gbc = layout.layout.getConstraints(comp);
        assertNotNull(gbc);
        layout.removeLayoutComponent(comp);
        assertEquals(false, gbc == layout.layout.getConstraints(comp));
        // doesn't do anything
        layout.removeWidget(null);
        assertEquals(layout.layout.getLayoutAlignmentX(container), layout.getLayoutAlignmentX(container), 0);
        assertEquals(layout.layout.getLayoutAlignmentY(container), layout.getLayoutAlignmentY(container), 0);
        // doesn't do anything
        layout.invalidateLayout(null);
        layout.destroy();
        assertEquals(null, layout.layout);
        assertEquals(null, layout.c);
    }
    
    public void testAddWidget() {
        System.out.println("testAddWidget");
        SwingGridBagLayout layout = new SwingGridBagLayout();
        Widget widget = WidgetFactory.getWidget("panel", "panel");
        layout.setParent(widget);
        Widget label = WidgetFactory.getWidget("label", "label");
        widget.addChildWidget(label);
        layout.addLayoutComponent(null, label);
        // should have set nothing
        GridBagConstraints gbc = layout.layout.getConstraints((Component) label.getNativeWidget());
        assertEquals(0, gbc.gridx);
        assertEquals(0, gbc.gridy);
        layout = new SwingGridBagLayout();
        label = WidgetFactory.getWidget("label", "label");  
        label.setPosition(10,11);
        layout.setParent(widget);
        layout.addWidget(label);
        gbc = layout.layout.getConstraints((Component) label.getNativeWidget());
        assertEquals(10, gbc.gridx);
        assertEquals(11, gbc.gridy);
    }
}
