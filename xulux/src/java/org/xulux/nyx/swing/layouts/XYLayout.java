/*
 $Id: XYLayout.java,v 1.6 2003-09-11 12:20:57 mvdb Exp $

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
    permission of The Xulux Project.  For written permission,
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
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
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

import org.xulux.nyx.gui.Widget;

/**
 * A layout manager that positions it's controls
 * using the size and the position of the control
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: XYLayout.java,v 1.6 2003-09-11 12:20:57 mvdb Exp $
 */
public class XYLayout implements LayoutManager2, Serializable
{
    HashMap map;

    /**
     * Constructor for XYLayout.
     */
    public XYLayout()
    {
        map = new HashMap();
    }
    
    /**
     * The contraints is the widget itself.
     * @see java.awt.LayoutManager2#addLayoutComponent(Component, Object)
     */
    public void addLayoutComponent(Component comp, Object constraints)
    {
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
            } else if (component != null) {
                // if component is not a widget
                // so layed on top of nyx.
                // try to get all the info from 
                // the component. It's up to the component
                // to set sizes etc and handle all other
                // logic.
                r = getRectangle(component);
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

    public Rectangle getRectangle(Widget widget, Component component)
    {
        Rectangle r = widget.getRectangle().getRectangle();
        // we want the preferred size if 
        // size isn't really useable.
        if (r.width <= 0 && r.height <= 0)
        {
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
     * @param component
     * @return the rectangle for a native swing component
     */
    public Rectangle getRectangle(Component component) {
        
        Rectangle r = component.getBounds();
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
     * Returns the layoutsize.
     * is almost the same as layouContainer,
     * except using setBounds
     * @param parent
     */
    private Dimension getLayoutSize(Container parent)
    {
        Dimension dim = new Dimension(0, 0);
        for (int i = 0; i < parent.getComponentCount(); i++)
        {
            Component component = parent.getComponent(i);
            Widget widget = (Widget) map.get(component);
            if (widget != null &&widget.isVisible())
            {
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
     * @see java.awt.LayoutManager#removeLayoutComponent(Component)
     */
    public void removeLayoutComponent(Component comp)
    {
        map.remove(comp);
    }

}
