/*
   $Id: TabPanel.java,v 1.6 2004-12-01 11:37:04 mvdb Exp $
   
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

import java.awt.Component;
import java.awt.Container;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.util.Iterator;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.gui.ContainerWidget;
import org.xulux.gui.NyxListener;
import org.xulux.gui.Widget;
import org.xulux.swing.util.SwingUtils;

/**
 * A panel that contains tabs..
 *
 *
 * @todo Dig deeper into tabPanels..
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: TabPanel.java,v 1.6 2004-12-01 11:37:04 mvdb Exp $
 */
public class TabPanel extends ContainerWidget {

    /**
     * The native tabbedPane
     */
    private JTabbedPane tabPanel;
    /**
     * The log instance
     */
    private Log log = LogFactory.getLog(TabPanel.class);
    /**
     * The tabCount
     */
    private int tabCount;
    /**
     * The tabid key that is used internally
     * by nyx. Made it public so people can use
     * it if they want to in their rules.
     */
    public static final String TABID = "nyx-tab-id";

    /**
     * Which tab has initialfocus
     */
    private String initialFocus;
    /**
     * The repaintcomponent class
     */
    private RepaintComponent repaintComponent;
    /**
     * the repaintthreads
     */
    private Thread repaintThread;

    /**
     * @param name the name of the tabPanel
     */
    public TabPanel(String name) {
        super(name);
    }

    /**
     * @see org.xulux.gui.Widget#destroy()
     */
    public void destroy() {
        super.destroy();
        if (tabPanel == null) {
            return;
        }
        if (repaintComponent != null) {
            tabPanel.removeHierarchyListener(repaintComponent);
            repaintComponent = null;
        }
        Container container = tabPanel.getParent();
        tabPanel.setVisible(false);
        tabPanel.removeAll();
        if (container != null) {
            container.remove(tabPanel);
        }
        tabPanel = null;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#focus()
     * @todo This is bad coding
     */
    public void focus() {
        Object object = getPart().getSession().getValue("nyx.focusrequest");
        if (object != null) {
            Widget w = (Widget) object;
            System.out.println("tabcount : " + w.getProperty(TABID));
            w = findChildAndParentForFocus(w);
            this.tabPanel.setSelectedComponent((JComponent) w.getNativeWidget());
        }
        this.tabPanel.requestFocus();
    }

    /**
     * Tries to find the widget of the parent of the widget passed
     * and which is also a child of this widget
     * @param w the widget
     * @return null when it is not found.
     */
    private Widget findChildAndParentForFocus(Widget w) {
        Widget widget = null;
        while (w != null) {
            if (getChildWidgets().contains(w)) {
                return w;
            } else {
                w = w.getParent();
            }
        }
        return null;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget() {
        initialize();
        return this.tabPanel;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public void initialize() {
        if (initialized) {
            return;
        }
        // we default to XYLayout for now..
        initialized = true;
        tabPanel = new JTabbedPane();
//        repaintComponent = new RepaintComponent();
//        tabPanel.addHierarchyListener(repaintComponent);
//        repaintThread = new Thread(repaintComponent);
//        repaintThread.start();
//        System.err.println("Still running");
        initializeChildren();
        refresh();
        processInit();
    }

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh() {
        isRefreshing = true;
        initialize();
//        tabPanel.setVisible(isVisible());
        isRefreshing = false;
    }

    /**
     * Adds a widget to the tabPanel if the widget
     * is a panel. For now no support for other
     * widget types. Need to dig in deep into
     * panels to see what can be usefull here
     * @todo Tooltips don't seem to work in jdk1.3
     * @see org.xulux.nyx.gui.ContainerWidget#addToParent(org.xulux.nyx.gui.Widget)
     */
    public void addToParent(Widget widget) {

        if (widget instanceof Panel || widget instanceof Tab) {
            if (log.isDebugEnabled()) {
                log.debug("Adding panel " + widget);
            }
            String tabTitle = widget.getProperty("title");
            String tabTip = widget.getProperty("tip");
            String tabIcon = widget.getProperty("icon");
            Icon icon = null;
            if (tabIcon != null) {
                try {
                    icon = SwingUtils.getIcon(tabIcon, this);
                } catch (Exception e) {
                    if (log.isWarnEnabled()) {
                        log.warn("Icon resource " + tabIcon + " cannot be found");
                    }
                }
            }
            // always add the parent to the added widget..
            widget.setParent(this);
            tabPanel.addTab(tabTitle, icon, (Component) widget.getNativeWidget(), tabTip);
            // just in case, but this doesn't seem to
            // work either. Who know jdk1.4 does..
            tabPanel.setToolTipTextAt(tabCount, tabTip);
            // add the tabId to the property of the widget.
            widget.setLazyProperty(TABID, String.valueOf(tabCount));
            // Set the selectedIndex to the first tab that is not disabled.
            if (initialFocus == null && widget.isEnabled() && widget.isVisible()) {
                initialFocus = String.valueOf(tabCount);
                tabPanel.setSelectedIndex(tabCount);
            }
            //widget.refresh();
            tabCount++;
        } else {
            // do not yet allow any addition of other widgets.
            // tabPanel.add((Component)widget.getNativeWidget(), widget);
            if (log.isWarnEnabled()) {
                log.warn("Only panel widgets are allowed on top of a tabPanel, skipping widget " + widget);
            }
        }
    }
    /**
     * @see org.xulux.nyx.gui.Widget#getGuiValue()
     */
    public Object getGuiValue() {
        return null;
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
    }

    /**
     * Fixes painting issues with the tabPanel.
     * Eg buttons from another panel would shine through
     * the first panel. After selecting the panel with the shine
     * through buttons on it, the problems would never appear again.
     * The selection of other tabs needs to be done in seperate runnables,
     * since else the painting doesn't complete of other components.
     * Probably calling the listeners with fireStatChanged will do,
     * but couldn't figure that out yet..
     *
     * @todo : dig in this deeper, probably fixable some other way!
     */
    public class RepaintComponent implements Runnable, HierarchyListener {
        /**
         * The index
         */
        private int index = 0;
        /**
         * if the component is running
         */
        private boolean isRunning;

        /**
         * the constructor
         */
        public RepaintComponent() {
        }

        /**
         * @see java.lang.Runnable#run()
         */
        public void run() {
            if (getPart() == null) {
                return;
            }
            while (!tabPanel.isShowing()) {
                //System.err.println("isShowing ? "+tabPanel.isShowing());
                boolean sleep = true;
                if (sleep) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ie) {
                        // please repaint everything..
                        sleep = false;
                        break;
                    }
                }
            }
            int selected = tabPanel.getSelectedIndex();
            for (int i = 0; i < tabPanel.getTabCount(); i++) {
                selectIndex(i);
            }
            selectIndex(selected);
            //tabPanel.setVisible(true);
            tabPanel.validate();
            tabPanel.repaint();
        }

        /**
         * Select the specified index
         * @param index the index
         */
        public void selectIndex(final int index) {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {

                    /**
                     * @see java.lang.Runnable#run()
                     */
                    public void run() {
                        tabPanel.setSelectedIndex(index);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }

        /**
         * @see java.awt.event.HierarchyListener#hierarchyChanged(java.awt.event.HierarchyEvent)
         */
        public void hierarchyChanged(HierarchyEvent e) {
            if (isRunning) {
                return;
            }
            Iterator it = getPart().getWidgets().iterator();
            while (it.hasNext()) {
                Widget w = (Widget) it.next();
                if (e.getChanged().equals(w.getNativeWidget())) {
                    break;
                }
            }

            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
                repaintThread.interrupt();
            }
        }

    }
}
