/*
 $Id: Window.java,v 1.3 2003-01-26 02:43:34 mvdb Exp $

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
package org.xulux.nyx.gui.swing;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JFrame;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.xulux.nyx.gui.Widget;
import org.xulux.nyx.listeners.swing.NyxWindowListener;
import org.xulux.nyx.swing.layouts.XYLayout;

/**
 * This is a swing window.
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Window.java,v 1.3 2003-01-26 02:43:34 mvdb Exp $
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
        initializeChildren();
        boolean autoSize = BooleanUtils.toBoolean(getProperty("autosize"));
        if (autoSize)
        {
            Dimension dim = window.getContentPane().getLayout().preferredLayoutSize(window.getContentPane());
            window.pack();
        }
        else
        {
            window.setSize(getRectangle().getWidth(), 
                            getRectangle().getHeight());
        }
        
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
