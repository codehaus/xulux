package org.xulux.nyx.gui.swing;


import java.awt.Container;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JWindow;
import javax.swing.WindowConstants;

import org.apache.commons.lang.StringUtils;
import org.xulux.nyx.context.ApplicationContext;
import org.xulux.nyx.gui.Widget;
import org.xulux.nyx.listeners.swing.NyxWindowListener;
import org.xulux.nyx.swing.layouts.XYLayout;
import sun.awt.AppContext;
import sun.awt.SunToolkit;

/**
 * This is a swing window.
 * It supports the following properties
 * @author Martin van den Bemt
 * @version $Id: Window.java,v 1.1 2003-01-25 23:17:57 mvdb Exp $
 */
public class Window extends org.xulux.nyx.gui.Window
{
    JFrame window;
    WindowListener windowListener;

    /**
     * Constructor for Window.
     * @param name
     */
    public Window(String name)
    {
        super(name);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy()
    {
        ArrayList children = getChildWidgets();
        if (children != null)
        {
            Iterator it = children.iterator();
            while (it.hasNext())
            {
                Widget cw = (Widget)it.next();
                cw.destroy();
            }
            children.clear();
            children = null;
        }
        // remove all child widgets
        // so we don't have any leftovers.
        window.removeAll();
        window.removeWindowListener(windowListener);
        windowListener = null;
        window.setVisible(false);
        Container container = window.getParent();
        if (container != null) 
        {
            container.remove(window);
        }
        window.dispose();
        window = null;
        if (ApplicationContext.isPartApplication(getPart()))
        {
            // this will give back control to the 
            // target that instantiated it.
            ApplicationContext.exitApplication();
        }
            
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget()
    {
        initialize();
        return window;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public void initialize()
    {
        if (this.initialized)
        {
            return;
        }
        initialized = true;
        window = new JFrame(StringUtils.defaultString(getProperty("title")));
        window.getContentPane().setLayout(new XYLayout());
        this.windowListener = new NyxWindowListener(this);
        window.addWindowListener(this.windowListener);
        String windowType = getProperty("window-type");
        // don't have a clue yet what to use here
        // for swing to work correctlly
        if (StringUtils.equalsIgnoreCase("model",windowType))
        {
        }
        else if (StringUtils.equalsIgnoreCase("toolbox",windowType))
        {
        }
        else
        {
            // mdi is the default. 
        }
        window.setSize(getRectangle().getWidth(), 
                        getRectangle().getHeight());
        initializeChildren();
        if (!isRefreshing())
        {
            refresh();
        }
    }

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh()
    {
        isRefreshing = true;
        initialize();
        window.setEnabled(isEnabled());
        window.setVisible(isVisible());
        isRefreshing = false;
    }
    /**
     * @see org.xulux.nyx.gui.Widget#canContainChildren()
     */
    public boolean canContainChildren()
    {
        return true;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#canBeRootWidget()
     */
    public boolean canBeRootWidget()
    {
        return true;
    }

    /**
     * @see org.xulux.nyx.gui.Window#addToParent(Widget)
     */
    public void addToParent(Widget widget)
    {
        window.getContentPane().add((JComponent)widget.getNativeWidget(), widget);
    }

}
