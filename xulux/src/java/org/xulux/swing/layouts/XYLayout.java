/*
   $Id: XYLayout.java,v 1.5 2004-05-10 14:05:50 mvdb Exp $
   
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
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.HashMap;

import javax.swing.JComponent;

import org.xulux.gui.IXuluxLayout;
import org.xulux.gui.Widget;

/**
 * A layout manager that positions it's controls
 * using the size and the position of the control
 *
 * @todo Fix insets problem when more native components are present than 1
 * The insets seems to tamper with the bounds of native components.
 * We should have a map or list to maintain processed components, so
 * on first entry it doesn't do the bounds restore..
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: XYLayout.java,v 1.5 2004-05-10 14:05:50 mvdb Exp $
 */
public class XYLayout implements LayoutManager2, Serializable, IXuluxLayout {
    /**
     * the map with widgets
     */
    protected HashMap map;
    /**
     * This is the widget that actually created the layout..
     */
    private Widget parentWidget;
    /**
     * Is this the first time the gui is layout?
     */
    private boolean firstLayout = true;

    /**
     *
     */
    public XYLayout() {
        map = new HashMap();
    }

    /**
     * Use this contructor if used inside nyx.
     * @param parent the creator of the XYLayout
     */
    public XYLayout(Widget parent) {
        map = new HashMap();
        setParent(parent);
    }

    /**
     * Set the parent
     * @param parent the parent widget
     */
    public void setParent(Widget parent) {
        this.parentWidget = parent;
    }

    /**
     *
     * @return the current parent or null of none present
     */
    public Widget getParent() {
        return this.parentWidget;
    }

    /**
     * The contraints is the widget itself.
     * @see java.awt.LayoutManager2#addLayoutComponent(Component, Object)
     */
    public void addLayoutComponent(Component comp, Object constraints) {
        map.put(comp, constraints);
    }

    /**
     * @see java.awt.LayoutManager2#getLayoutAlignmentX(Container)
     */
    public float getLayoutAlignmentX(Container target) {
        return 0.5F;
    }

    /**
     * @see java.awt.LayoutManager2#getLayoutAlignmentY(Container)
     */
    public float getLayoutAlignmentY(Container target) {
        return 0.5F;
    }

    /**
     * @see java.awt.LayoutManager2#invalidateLayout(Container)
     */
    public void invalidateLayout(Container target) {
    }

    /**
     * @see java.awt.LayoutManager2#maximumLayoutSize(Container)
     */
    public Dimension maximumLayoutSize(Container target) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    /**
     * @see java.awt.LayoutManager#addLayoutComponent(String, Component)
     */
    public void addLayoutComponent(String name, Component comp) {
    }

    /**
     * @see java.awt.LayoutManager#layoutContainer(Container)
     */
    public void layoutContainer(Container parent) {
        if (parent == null) {
            return;
        }
        Insets insets = parent.getInsets();
        int count = parent.getComponentCount();
        for (int i = 0; i < count; i++) {
            Component component = parent.getComponent(i);
            Widget widget = (Widget) map.get(component);
            Rectangle r = null;
            if (widget != null && widget.isVisible()) {
                r = getRectangle(widget, component);
                if (component instanceof JComponent) {
                    ((JComponent) component).setPreferredSize(new Dimension(r.width, r.height));
                }
                component.setSize(r.width, r.height);
            } else {
                // if component is not a widget
                // so layed on top of nyx.
                // try to get all the info from
                // the component. It's up to the component
                // to set sizes etc and handle all other
                // logic.
                r = getRectangle(component, insets);
            }
            // r cannot be null in this scenario
            component.setBounds(insets.left + r.x, insets.top + r.y, r.width, r.height);
        }
    }

    /**
     * The preferred size of the component is used, when the widget
     * size isn't really useable...
     *
     * @param widget the widget to get the size from
     * @param component the component to get the size from
     * @return the rectangle of the component
     */
    public Rectangle getRectangle(Widget widget, Component component) {
        if (widget == null) {
            return null;
        }
        Rectangle r = widget.getRectangle().getRectangle();
        if (r.width <= 0 && r.height <= 0) {
            Dimension d = component.getPreferredSize();
            r.width = d.width;
            r.height = d.height;
            widget.getRectangle().setSize(r.width, r.height);
        }
        return r;
    }

    /**
     * This is mainly used for swing components that are
     * layed "invisibly" on top of nyx.
     * You should use setLocation(x,y) to position the
     * Swing component correctly.
     *
     * @param component the component
     * @param insets the insets
     * @return the rectangle for a native swing component
     */
    public Rectangle getRectangle(Component component, Insets insets) {

        if (component == null) {
            return null;
        }
        Rectangle r = component.getBounds();
        // we need to correct the insets when the first run alrady
        // has taken place.
        if (!isFirstLayout()) {
            if (insets != null) {
                r.x -= insets.left;
                r.y -= insets.top;
                component.setBounds(r);
            }
        } else {
            setFirstLayout(false);
        }
        Dimension d = component.getPreferredSize();
        r.width = d.width;
        r.height = d.height;
        return r;
    }

    /**
     * @see java.awt.LayoutManager#minimumLayoutSize(Container)
     */
    public Dimension minimumLayoutSize(Container parent) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    /**
     * @see java.awt.LayoutManager#preferredLayoutSize(Container)
     */
    public Dimension preferredLayoutSize(Container parent) {
        Dimension dim = getLayoutSize(parent);
        return dim;
    }

    /**
     * is almost the same as layoutContainer,
     * except using setBounds.
     * If the size of the widget is not set (0 or less than zero)
     * it will use the preferredsize of the native component, which
     * is gotten from getRectangle().
     * @todo write a helper class to give some feedback to the developer
     *       on what sizes should be preferred, to have everything fit!
     *       now it just makes the sizes of eg a panel bigger if the insets
     *       are bigger than 0
     * @todo dig into the insets stuff deeper and find scenarios where it can fail
     * @param parent the parent container
     * @return the layoutsize
     */
    protected Dimension getLayoutSize(Container parent) {
        Dimension dim = new Dimension(0, 0);
        Widget pWidget = (Widget) map.get(parent);
        int maxX = 0;
        int maxY = 0;
        for (int i = 0; i < parent.getComponentCount(); i++) {
            Component component = parent.getComponent(i);
            Widget widget = (Widget) map.get(component);
            if (widget != null && widget.isVisible()) {
                Rectangle r = getRectangle(widget, component);
                int tmpWidth = r.x + r.width;
                if (tmpWidth > maxX) {
                    maxX = tmpWidth;
                }
                int tmpHeight = r.y + r.height;
                if (tmpHeight > maxY) {
                    maxY = tmpHeight;
                }
            } else if (widget == null) { //if (widget == null) {
                // only process when there is no widget
                Dimension compDim = component.getPreferredSize();
                maxX += compDim.width;
                maxY += compDim.height;
            }
        }
        dim.width = maxX;
        dim.height = maxY;

        Insets insets = parent.getInsets();
        dim.width += insets.left + insets.right;
        dim.height += insets.top + insets.bottom;
        return dim;
    }

    /**
     * Set if this is the first time that we layout or not
     * @param firstLayout true or false
     */
    protected void setFirstLayout(boolean firstLayout) {
        this.firstLayout = firstLayout;
    }

    /**
     * @return if the layout still needs to process it's first layout
     */
    protected boolean isFirstLayout() {
        return this.firstLayout;
    }

    /**
     * @see java.awt.LayoutManager#removeLayoutComponent(Component)
     */
    public void removeLayoutComponent(Component comp) {
        map.remove(comp);
    }

    /**
     * @see org.xulux.gui.IXuluxLayout#destroy()
     */
    public void destroy() {
    }

    /**
     * @see org.xulux.gui.IXuluxLayout#addWidget(org.xulux.gui.Widget)
     */
    public void addWidget(Widget widget) {
      Object nativeWidget = widget.getNativeWidget();
      if (nativeWidget instanceof Component) {
        addLayoutComponent((Component) nativeWidget, widget);
      }
    }

    /**
     * @see org.xulux.gui.IXuluxLayout#removeWidget(org.xulux.gui.Widget)
     */
    public void removeWidget(Widget widget) {
      Object nativeWidget = widget.getNativeWidget();
      if (nativeWidget instanceof Component) {
        removeLayoutComponent((Component) nativeWidget);
      }
    }
}
