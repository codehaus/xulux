package org.xulux.nyx.swing.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;

/**
 * A layout based on the layout specified in toCure standards
 * 
 * @author Martin van den Bemt
 * @version $Id: FormLayout.java,v 1.1 2002-10-23 00:28:44 mvdb Exp $
 */
public class FormLayout implements LayoutManager, LayoutManager2
{

    /**
     * Constructor for FormLayout.
     */
    public FormLayout()
    {
        super();
    }

    /**
     * @see java.awt.LayoutManager#addLayoutComponent(String, Component)
     */
    public void addLayoutComponent(String name, Component comp)
    {
    }

    /**
     * @see java.awt.LayoutManager#removeLayoutComponent(Component)
     */
    public void removeLayoutComponent(Component comp)
    {
    }

    /**
     * @see java.awt.LayoutManager#preferredLayoutSize(Container)
     */
    public Dimension preferredLayoutSize(Container parent)
    {
        return null;
    }

    /**
     * @see java.awt.LayoutManager#minimumLayoutSize(Container)
     */
    public Dimension minimumLayoutSize(Container parent)
    {
        return null;
    }

    /**
     * @see java.awt.LayoutManager#layoutContainer(Container)
     */
    public void layoutContainer(Container parent)
    {
    }

    /**
     * @see java.awt.LayoutManager2#addLayoutComponent(Component, Object)
     */
    public void addLayoutComponent(Component comp, Object constraints)
    {
    }

    /**
     * @see java.awt.LayoutManager2#maximumLayoutSize(Container)
     */
    public Dimension maximumLayoutSize(Container target)
    {
        return null;
    }

    /**
     * @see java.awt.LayoutManager2#getLayoutAlignmentX(Container)
     */
    public float getLayoutAlignmentX(Container target)
    {
        return 0;
    }

    /**
     * @see java.awt.LayoutManager2#getLayoutAlignmentY(Container)
     */
    public float getLayoutAlignmentY(Container target)
    {
        return 0;
    }

    /**
     * @see java.awt.LayoutManager2#invalidateLayout(Container)
     */
    public void invalidateLayout(Container target)
    {
    }

}
