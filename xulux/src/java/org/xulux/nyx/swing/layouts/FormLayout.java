/*
 $Id: FormLayout.java,v 1.2 2002-10-29 00:10:02 mvdb Exp $

 Copyright 2002 (C) The Xulux Project. All Rights Reserved.
 
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
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * A layout based on the layout specified in toCure standards
 * 
 * @author Martin van den Bemt
 * @version $Id: FormLayout.java,v 1.2 2002-10-29 00:10:02 mvdb Exp $
 */
public class FormLayout implements LayoutManager2
{
    
    ArrayList containers;
    /**
     * The vertical Margin
     */
    private int vMargin = 0;
    private int hMargin = 0;
    
    private int x=0;
    private int y=0;
    
    private int xMax=0;
    private int yMax=0;

    /**
     * Constructor for FormLayout.
     */
    public FormLayout()
    {
    }
    
    public FormLayout(int hMargin, int vMargin)
    {
        this.hMargin = hMargin;
        this.vMargin = vMargin;
    }

    /**
     * @see java.awt.LayoutManager#addLayoutComponent(String, Component)
     */
    public void addLayoutComponent(String name, Component comp)
    {
        System.out.print("addLayouComponent");
        System.out.println(" name :"+name);
        System.out.println(" comp : "+comp);
    }
    /**
     * @see java.awt.LayoutManager2#addLayoutComponent(Component, Object)
     */
    public void addLayoutComponent(Component comp, Object constraints)
    {
        System.out.println("addLayoutComponent");
        System.out.println("comp : "+comp);
        System.out.println("parent : "+comp.getParent().getClass().getName());
        System.out.println("comp pref size ; "+comp.getPreferredSize());
        if (constraints != null)
        {
            System.out.println(" constraints : "+constraints.getClass().getName());
        }
        else
        {
            System.out.println();
        }
    }

    

    /**
     * @see java.awt.LayoutManager#removeLayoutComponent(Component)
     */
    public void removeLayoutComponent(Component comp)
    {
        System.out.println("removeLayoutComponent");
    }

    /**
     * @see java.awt.LayoutManager#preferredLayoutSize(Container)
     */
    public Dimension preferredLayoutSize(Container parent)
    {
        System.out.println("preferredLayoutSize");
        return new Dimension(200,200);
    }

    /**
     * @see java.awt.LayoutManager#minimumLayoutSize(Container)
     */
    public Dimension minimumLayoutSize(Container parent)
    {
        System.out.println("minimumLayoutSize");
        return null;
    }
    
    public void layoutContainer(Container container)
    {
        int addY = 0+this.vMargin;
        int cWidth = (int) container.getBounds().getWidth();
        int cHeight = (int) container.getBounds().getHeight();
        Component[] comps = container.getComponents();
        for (int i=0; i < comps.length; i++)
        {
            Component component = (Component) comps[i];
            System.err.println("Parent : "+component.getParent().getClass().getName());
            int width = (int) component.getSize().getWidth();
            int height = (int) component.getSize().getHeight();
            if (width <= 0 && height <= 0)
            {
                width = (int) component.getPreferredSize().getWidth();
                height = (int) component.getPreferredSize().getHeight();
            }
            int xCentered = (cWidth - width) /2;
            component.setBounds(0, addY, width, height);
            addY+=height+this.vMargin;
        }
///        container.setSize((int)container.getPreferredSize().getHeight(), addY);
    }
    /**
     * @see java.awt.LayoutManager#layoutContainer(Container)
     */
    public void xlayoutContainer(Container parent)
    {
        System.err.println("layoutContainer");
        Component[] comps = parent.getComponents();
        Insets insets = parent.getInsets();
        int top = insets.top;
        //int bottom = insets.bottom;
        int left = insets.left;
        //int right = insets.right;
        System.out.println("Insets ; "+insets);
        
        for (int i = 0; i < comps.length; i++)
        {
            Component component = comps[i];
            System.out.println("component : "+component.getClass().getName());
            x = top;
            y = top;
            Dimension d = component.getPreferredSize();
            int width = (int) component.getSize().getWidth();
            int height = (int) component.getSize().getHeight();
            if (width <= 0 && height <= 0)
            {
                width = (int) component.getPreferredSize().getWidth();
                height = (int) component.getPreferredSize().getHeight();
            }
            System.out.println("Using width,height "+width+","+height);
            System.out.println("Using x,y "+x+","+y);
            component.setBounds(x, y, width, height);
            y+=height;
        }
        //parent.setSize(100,100);
    }

    /**
     * @see java.awt.LayoutManager2#maximumLayoutSize(Container)
     */
    public Dimension maximumLayoutSize(Container target)
    {
        System.out.println("maximumLayoutSize");
        return null;
    }

    /**
     * @see java.awt.LayoutManager2#getLayoutAlignmentX(Container)
     */
    public float getLayoutAlignmentX(Container target)
    {
        System.out.println("getLayoutAlignmentX");
        return 0;
    }

    /**
     * @see java.awt.LayoutManager2#getLayoutAlignmentY(Container)
     */
    public float getLayoutAlignmentY(Container target)
    {
        System.out.println("getLayoutAlignmentY");
        return 0;
    }

    /**
     * @see java.awt.LayoutManager2#invalidateLayout(Container)
     */
    public void invalidateLayout(Container target)
    {
        System.out.println("invalidateLayout");
    }

}
