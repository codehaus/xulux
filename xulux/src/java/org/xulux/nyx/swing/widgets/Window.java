/*
 $Id: Window.java,v 1.17 2003-11-24 16:06:58 mvdb Exp $

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
package org.xulux.nyx.swing.widgets;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.nyx.gui.IShowChildWidgets;
import org.xulux.nyx.gui.NyxListener;
import org.xulux.nyx.gui.NyxWindow;
import org.xulux.nyx.gui.Widget;
import org.xulux.nyx.swing.layouts.XYLayout;
import org.xulux.nyx.swing.listeners.NyxWindowListener;
import org.xulux.nyx.swing.util.SwingUtils;
import org.xulux.nyx.utils.BooleanUtils;

/**
 * This is a swing window.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Window.java,v 1.17 2003-11-24 16:06:58 mvdb Exp $
 */
public class Window extends NyxWindow
{
    /**
     * the native window
     */
    protected JFrame window;
    /**
     * the window listener
     */
    protected WindowListener windowListener;
    /**
     * the log instance
     */
    protected static Log log = LogFactory.getLog(Window.class);

    /**
     * Constructor for NyxWindow.
     * @param name the name of the window
     */
    public Window(String name) {
        super(name);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy()
    {
        processDestroy();
        ArrayList children = getChildWidgets();
        if (children != null)
        {
            Iterator it = children.iterator();
            while (it.hasNext())
            {
                Widget cw = (Widget) it.next();
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
        String title = getProperty("title");
        if (title == null) {
            title = "";
        }
        window = new JFrame(title);
        window.getContentPane().setLayout(new XYLayout(this));
        this.windowListener = new NyxWindowListener(this);
        window.addWindowListener(this.windowListener);
        String windowType = getProperty("window-type");
        // don't have a clue yet what to use here
        // for swing to work correctlly
        if ("model".equalsIgnoreCase(windowType))
        {
        }
        else if ("toolbox".equalsIgnoreCase(windowType))
        {
        }
        else
        {
            // TODO: Introduce MDI type of windowing
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
        processInit();
        new Thread(new RepaintComponent()).start();
    }

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh()
    {
        isRefreshing = true;
        initialize();
        String image = getProperty("icon");
        if (image != null)
        {
            try {
                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(image));
                window.setIconImage(SwingUtils.getImage(image, this));
            } catch (Exception e) {
                if (log.isWarnEnabled()) {
                    log.warn("Image resource " + image + " cannot be found");
                }
            }
            //button.setFocusPainted(true);
        }
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
     * @see org.xulux.nyx.gui.NyxWindow#addToParent(Widget)
     */
    public void addToParent(Widget widget)
    {
        if (widget instanceof IShowChildWidgets) {
            List children = widget.getChildWidgets();
            if (children != null && children.size() > 0) {
                Iterator it = children.iterator();
                while (it.hasNext()) {
                    Widget w = (Widget) it.next();
                    window.getContentPane().add((JComponent) w.getNativeWidget(), w);
                }
            }
        } else {
            window.getContentPane().add((JComponent) widget.getNativeWidget(), widget);
        }
    }

    /**
     * @see org.xulux.nyx.gui.Widget#focus()
     */
    public void focus() {
        window.requestFocus();
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getGuiValue()
     */
    public Object getGuiValue() {
        return null;
    }

    /**
     * Repaints the window when it is shown.
     */
    public class RepaintComponent implements Runnable {
        /**
         * @see java.lang.Runnable#run()
         */
        public void run() {
            if (getPart() == null) {
                return;
            }
            while (getPart().isActivating()) { };
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    /**
                     * @see java.lang.Runnable#run()
                     */
                    public void run() {
                        // TODO : Look at painting problem
                        // we for now rerun the rules, so
                        // the window will show correctly.
                        window.repaint();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }

    }
    /**
     * @see org.xulux.nyx.gui.Widget#canContainValue()
     */
    public boolean canContainValue() {
        return false;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#isValueEmpty()
     */
    public boolean isValueEmpty() {
        return true;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#addNyxListener(org.xulux.nyx.gui.NyxListener)
     */
    public void addNyxListener(NyxListener listener) {
        // TODO
    }

}
