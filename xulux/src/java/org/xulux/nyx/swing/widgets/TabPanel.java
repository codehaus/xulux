/*
 $Id: TabPanel.java,v 1.10 2003-09-25 13:30:59 mvdb Exp $

 Copyright 2003 (C) The Xulux Project. All Rights Reserved.
 
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
package org.xulux.nyx.swing.widgets;

import java.awt.Component;
import java.awt.Container;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.nyx.gui.ContainerWidget;
import org.xulux.nyx.gui.NyxListener;
import org.xulux.nyx.gui.Widget;
import org.xulux.nyx.swing.util.SwingUtils;

/**
 * A panel that contains tabs..
 * 
 * 
 * @todo Dig deeper into tabPanels..
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: TabPanel.java,v 1.10 2003-09-25 13:30:59 mvdb Exp $
 */
public class TabPanel extends ContainerWidget {
    
    private JTabbedPane tabPanel;
    private Log log = LogFactory.getLog(TabPanel.class);
    private int tabCount;
    /**
     * The tabid key that is used internally
     * by nyx. Made it public so people can use
     * it if they want to in their rules. 
     */
    public static String TABID = "nyx-tab-id";
    
    /**
     * @param name
     */
    public TabPanel(String name) {
        super(name);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy() {
        processDestroy();
        super.destroy();
        if (tabPanel == null) {
            return;
        }
        Container container = tabPanel.getParent();
        tabPanel.setVisible(false);
        tabPanel.removeAll();
        if (container != null)
        {
            container.remove(tabPanel);
        }
        tabPanel = null;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#focus()
     * TODO: This is bad coding 
     */
    public void focus() {
        Object object = getPart().getSession().getValue("nyx.focusrequest");
        if (object != null) {
            Widget w = (Widget)object;
            w = findChildAndParentForFocus(w);
            this.tabPanel.setSelectedComponent((JComponent)w.getNativeWidget());
        }
        this.tabPanel.requestFocus();
    }
    
    /**
     * Tries to find the widget of the parent of the widget passed
     * and which is also a child of this widget
     * @param w
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
        if (initialized)
        {
            return;
        }
        // we default to XYLayout for now..
        initialized = true;
        tabPanel = new JTabbedPane();
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
        tabPanel.repaint();
        System.out.println("insets : "+tabPanel.getInsets());
        System.out.println("size : "+tabPanel.getSize());
        System.out.println("preferred size : "+tabPanel.getPreferredSize());
        System.out.println("Bounds : "+tabPanel.getBounds());
        System.out.println("PANEL PANEL : "+getName());
        // dig into panels a bit deeper..
        // for now we don't need anything here..
        isRefreshing = false;
    }

    /**
     * Adds a widget to the tabPanel if the widget
     * is a panel. For now no support for other 
     * widget types. Need to dig in deep into
     * panels to see what can be usefull here
     * TODO: Tooltips don't seem to work...
     * @see org.xulux.nyx.gui.ContainerWidget#addToParent(org.xulux.nyx.gui.Widget)
     */
    public void addToParent(Widget widget) {
        
        if (widget instanceof Panel) {
            if (log.isDebugEnabled()) {
                log.debug("Adding panel "+widget);
            }
            String tabTitle = widget.getProperty("title");
            String tabTip = widget.getProperty("tip");
            String tabIcon = widget.getProperty("icon");
            Icon icon = null;
            if (tabIcon != null) {
                try {
                    icon = SwingUtils.getIcon(tabIcon, this);
                }catch(Exception e) {
                    if (log.isWarnEnabled()) {
                        log.warn("Icon resource "+tabIcon+" cannot be found");
                    }
                }
            }
            tabPanel.addTab(tabTitle,icon,(Component)widget.getNativeWidget(), tabTip);
            // just in case, but this doesn't seem to 
            // work either. Who know jdk1.4 does..
            tabPanel.setToolTipTextAt(tabCount, tabTip);
            // add the tabId to the property of the widget.
            widget.setProperty(TABID,String.valueOf(tabCount));
            tabCount++;
        } else {
            // do not yet allow any addition of other widgets.
            // tabPanel.add((Component)widget.getNativeWidget(), widget);
            if (log.isWarnEnabled()) {
                log.warn("Only panel widgets are allowed on top of a tabPanel, skipping widget "+widget);
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
        // TODO
    }

}
