/*
   $Id: SwingBoxLayoutTest.java,v 1.1 2004-05-24 18:12:34 mvdb Exp $
   
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

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JComponent;

import junit.framework.TestCase;

import org.xulux.gui.Widget;
import org.xulux.gui.WidgetFactory;
import org.xulux.swing.widgets.Label;
import org.xulux.swing.widgets.Panel;

/**
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SwingBoxLayoutTest.java,v 1.1 2004-05-24 18:12:34 mvdb Exp $
 */
public class SwingBoxLayoutTest extends TestCase {

    /**
     * Constructor for SwingBoxLayoutTest.
     * @param name the name of the test
     */
    public SwingBoxLayoutTest(String name) {
        super(name);
    }
    
    public void testInvalidateLayout() {
        System.out.println("testInvalidateLayout");
        SwingBoxLayout layout = new SwingBoxLayout();
        // no layout has been initialized..
        Widget widget = new Panel("panel");
        layout.setParent(widget);
        layout.invalidateLayout((Container) widget.getNativeWidget());
        Widget label = new Label("label");
        layout.addWidget(label);
        layout.invalidateLayout((Container) widget.getNativeWidget());
        layout.removeWidget(null);
    }
    
    public void testWrapperMethods() {
        System.out.println("testWrapperMethods");
        SwingBoxLayout layout = new SwingBoxLayout();
        // no layout has been initialized..
        Widget widget = new Panel("panel");
        layout.setParent(widget);
        Container cont = (Container) widget.getNativeWidget();
        Widget w = new Label("label");
        widget.addChildWidget(w);
        w.setSize(100, 100);
        layout.addWidget(w);
        layout.addLayoutComponent(null, w);
        assertEquals("equals", layout.layout.getLayoutAlignmentX(cont),layout.getLayoutAlignmentX(cont), 0);
        assertEquals("equals", layout.layout.getLayoutAlignmentY(cont),layout.getLayoutAlignmentY(cont), 0);
        //((JComponent)w.getNativeWidget()).setPreferredSize(new Dimension(100,100));
        //((JComponent)w.getNativeWidget()).setSize(new Dimension(100,100));
        ((JComponent)w.getNativeWidget()).setMaximumSize(new Dimension(100,100));
        //((JComponent)w.getNativeWidget()).setMinimumSize(new Dimension(111,111));
        layout.invalidateLayout(cont);
        layout.layoutContainer(cont);
        System.out.println("layout : " +layout.maximumLayoutSize(cont));
        assertEquals(layout.layout.maximumLayoutSize(cont), layout.maximumLayoutSize(cont));
        ((JComponent)w.getNativeWidget()).setMinimumSize(new Dimension(111,111));
        assertEquals(layout.layout.minimumLayoutSize(cont), layout.minimumLayoutSize(cont));
        layout.destroy();
        layout.addLayoutComponent((String) null, null);
        layout.removeLayoutComponent(null);
        layout.preferredLayoutSize(cont);
    }

    public void testInitXAndYAxis() {
        System.out.println("testInitXAndYAxis");
        Widget widget = WidgetFactory.getWidget("panel", "panel");
        widget.setProperty("layout-orientation","y");
        SwingBoxLayout layout = new SwingBoxLayout();
        layout.setParent(widget);
        Widget label = WidgetFactory.getWidget("label", "label");
        label.setSize(25, 50);
        widget.addChildWidget(label);
        layout.addWidget(label);
        Container container = (Container) widget.getNativeWidget();
        assertEquals(0, (int) layout.getLayoutAlignmentX(container));
        assertEquals(true, layout.getLayoutAlignmentY(container) > 0);
        widget.setProperty("layout-orientation", "x");
        layout = new SwingBoxLayout();
        layout.setParent(widget);
        layout.addWidget(label);
        assertEquals(0, (int) layout.getLayoutAlignmentY(container));
        assertEquals(true, layout.getLayoutAlignmentX(container) > 0);
    }
}
