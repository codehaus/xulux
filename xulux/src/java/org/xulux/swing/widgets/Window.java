/*
   $Id: Window.java,v 1.14 2005-01-12 18:39:30 mvdb Exp $
   
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
package org.xulux.swing.widgets;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.WindowListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.core.XuluxContext;
import org.xulux.gui.IShowChildWidgets;
import org.xulux.gui.IXuluxLayout;
import org.xulux.gui.NyxListener;
import org.xulux.gui.NyxWindow;
import org.xulux.gui.Widget;
import org.xulux.swing.listeners.NyxWindowListener;
import org.xulux.swing.util.SwingUtils;
import org.xulux.utils.BooleanUtils;

/**
 * This is a swing window.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Window.java,v 1.14 2005-01-12 18:39:30 mvdb Exp $
 */
public class Window extends NyxWindow {
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

    boolean isInitializing = false;
    /**
     * Constructor for NyxWindow.
     * @param name the name of the window
     */
    public Window(String name) {
        super(name);
    }

    /**
     * @see org.xulux.gui.Widget#destroy()
     */
    public void destroy() {
        super.destroy();
        if (window != null) {
          window.removeAll();
          window.removeWindowListener(windowListener);
          windowListener = null;
          window.setVisible(false);
          Container container = window.getParent();
          if (container != null) {
              container.remove(window);
          }
          window.dispose();
          window = null;
        }
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget() {
        initialize();
        return window;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public void initialize() {
        if (this.initialized) {
            return;
        }
        initialized = true;
        isInitializing = true;
        String title = getProperty("title");
        if (title == null) {
            title = "";
        }
        window = new JFrame(title);
        IXuluxLayout layout = XuluxContext.getGuiDefaults().getLayout(null, getProperty("layout"));
        if (layout == null) {
            layout = XuluxContext.getGuiDefaults().getDefaultLayout();
        }
        layout.setParent(this);
        window.getContentPane().setLayout((LayoutManager) layout);
        this.windowListener = new NyxWindowListener(this);
        window.addWindowListener(this.windowListener);
        String windowType = getProperty("window-type");
        // don't have a clue yet what to use here
        // for swing to work correctlly
        if ("modal".equalsIgnoreCase(windowType)) {
        } else if ("toolbox".equalsIgnoreCase(windowType)) {
        } else {
            // @todo Introduce MDI type of windowing
            // mdi is the default.
        }
        boolean autoSize = BooleanUtils.toBoolean(getProperty("autosize"));
        if (autoSize) {
            Dimension dim = window.getContentPane().getLayout().preferredLayoutSize(window.getContentPane());
            window.pack();
        } else {
            window.setSize(getRectangle().getWidth(), getRectangle().getHeight());
        }
        if ("center".equalsIgnoreCase(getProperty("position"))) {
        	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        	Dimension prefSize = window.getPreferredSize();
        	if (prefSize.width == 0 && prefSize.height == 0) {
        	    prefSize = getRectangle().getRectangle().getSize();
        	}
        	System.out.println("screenSize : " + screenSize);
        	System.out.println("Pref Size : " + prefSize);
        	window.setLocation((screenSize.width-prefSize.width)/2, (screenSize.height-prefSize.height)/2);
        }
//        if (autoSize) {
//            Dimension dim = window.getContentPane().getLayout().preferredLayoutSize(window.getContentPane());
//        }
//        if ("center".equalsIgnoreCase(getProperty("position"))) {
//        	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        	Dimension prefSize = window.getPreferredSize();
//        	window.setLocation((screenSize.width-prefSize.width)/2, (screenSize.height-prefSize.height)/2);
//        }
        window.setVisible(false);
        initializeChildren();
        window.setVisible(false);
        processInit();
        if (getProperty("resizable") != null) {
          boolean resize = BooleanUtils.toBoolean(getProperty("resizable"));
            window.setResizable(resize);
        }
        window.setVisible(true);
        isInitializing = false;
        if (!isRefreshing()) {
            refresh();
        }
        new Thread(new RepaintComponent()).start();
    }

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh() {
    	if (isInitializing) {
    		return;
    	}
        isRefreshing = true;
        initialize();
        String image = getProperty("icon");
        if (image != null) {
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
        window.setTitle(getProperty("title"));
        window.setVisible(isVisible());
        window.setEnabled(isEnabled());
        isRefreshing = false;
    }

    /**
     * @see org.xulux.nyx.gui.NyxWindow#addToParent(Widget)
     */
    public void addToParent(Widget widget) {
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
//            System.out.println("Window widget : " + widget.getNativeWidget().getClass());
            if (widget.getNativeWidget() instanceof JMenuBar) {
                window.getRootPane().setJMenuBar((JMenuBar) widget.getNativeWidget());
            } else {
                window.getContentPane().add((JComponent) widget.getNativeWidget(), widget);
            }
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
            while (getPart().isActivating()) {
            };
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
