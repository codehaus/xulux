/*
 $Id: XYLayout.java,v 1.13 2003-11-25 19:23:54 mvdb Exp $

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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.HashMap;

import javax.swing.JComponent;

import org.xulux.nyx.gui.Widget;

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
 * @version $Id: XYLayout.java,v 1.13 2003-11-25 19:23:54 mvdb Exp $
 */
public class XYLayout implements LayoutManager2, Serializable
{
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
    public XYLayout()
    {
        map = new HashMap();
    }

    /**
     * Use this contructor if used inside nyx.
     * @param parent the creator of the XYLayout
     */
    public XYLayout(Widget parent)
    {
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
    public float getLayoutAlignmentX(Container target)
    {
        return 0.5F;
    }

    /**
     * @see java.awt.LayoutManager2#getLayoutAlignmentY(Container)
     */
    public float getLayoutAlignmentY(Container target)
    {
        return 0.5F;
    }

    /**
     * @see java.awt.LayoutManager2#invalidateLayout(Container)
     */
    public void invalidateLayout(Container target)
    {
    }

    /**
     * @see java.awt.LayoutManager2#maximumLayoutSize(Container)
     */
    public Dimension maximumLayoutSize(Container target)
    {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    /**
     * @see java.awt.LayoutManager#addLayoutComponent(String, Component)
     */
    public void addLayoutComponent(String name, Component comp)
    {
    }

    /**
     * @see java.awt.LayoutManager#layoutContainer(Container)
     */
    public void layoutContainer(Container parent)
    {
        if (parent == null) {
            return;
        }
        Insets insets = parent.getInsets();
        int count = parent.getComponentCount();
        for (int i = 0; i < count; i++)
        {
            Component component = parent.getComponent(i);
            Widget widget = (Widget) map.get(component);
            Rectangle r = null;
            if (widget != null && widget.isVisible())
            {
                r = getRectangle(widget, component);
                if (component instanceof JComponent) {
                    ((JComponent) component).setPreferredSize(new Dimension(r.width, r.height));
                }
                component.setSize(r.width, r.height);
            } else if (component != null) {
                // if component is not a widget
                // so layed on top of nyx.
                // try to get all the info from
                // the component. It's up to the component
                // to set sizes etc and handle all other
                // logic.

//                if (parentWidget != null) {
//                    if (parent.equals(parentWidget.getNativeWidget())) {
//                        // this component has a nyx widget as parent
////                        System.err.println("PARENT IS THE WIDGET");
//                        // first layout has been processed..
//                    } else {
////                        System.err.println("REMOVING INSETS!");
//                    }
//                } else {
////                    System.err.println("PARENT IS NOT THE WIDGET");
//                }
                r = getRectangle(component, insets);
            }
            if (r != null) {
                component.setBounds(
                    insets.left + r.x,
                    insets.top + r.y,
                    r.width,
                    r.height);
            }
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
    public Dimension minimumLayoutSize(Container parent)
    {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    /**
     * @see java.awt.LayoutManager#preferredLayoutSize(Container)
     */
    public Dimension preferredLayoutSize(Container parent)
    {
        Dimension dim = getLayoutSize(parent);
        return dim;
    }

    /**
     * is almost the same as layoutContainer,
     * except using setBounds
     * @param parent the parent container
     * @return the layoutsize
     */
    protected Dimension getLayoutSize(Container parent)
    {
        Dimension dim = new Dimension(0, 0);
        for (int i = 0; i < parent.getComponentCount(); i++)
        {
            Component component = parent.getComponent(i);
            Widget widget = (Widget) map.get(component);
            if (widget != null && widget.isVisible()) {
                Rectangle r = getRectangle(widget, component);
                if (r.width <= 0 && r.height <= 0)
                {
                    Dimension d = component.getPreferredSize();
                    r.width = d.width;
                    r.height = d.height;
                }
                dim.width = r.x + r.width;
                dim.height = r.y + r.height;
            }
        }
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

}
