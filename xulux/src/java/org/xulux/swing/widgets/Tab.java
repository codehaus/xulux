/*
   $Id: Tab.java,v 1.2 2004-10-20 13:11:22 mvdb Exp $
   
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
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.xulux.core.XuluxContext;
import org.xulux.gui.ContainerWidget;
import org.xulux.gui.IShowChildWidgets;
import org.xulux.gui.IXuluxLayout;
import org.xulux.gui.NyxListener;
import org.xulux.gui.Widget;
import org.xulux.gui.utils.ColorUtils;

/**
 * A panel widget
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Tab.java,v 1.2 2004-10-20 13:11:22 mvdb Exp $
 */
public class Tab extends ContainerWidget {

    /**
     * the native panel
     */
    private JPanel panel;

    /**
     * Constructor for Panel.
     * @param name the panel name
     */
    public Tab(String name) {
        super(name);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy() {
        processDestroy();
        super.destroy();
        if (panel == null) {
            return;
        }
        Container container = panel.getParent();
        panel.setVisible(false);
        panel.removeAll();
        if (container != null) {
            container.remove(panel);
        }
        panel = null;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget() {
        initialize();
        return panel;
    }

    /**
     * @todo Make layouts flexible.
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public void initialize() {
        if (initialized) {
            return;
        }
        // we default to XYLayout for now..
        initialized = true;
        IXuluxLayout layout = XuluxContext.getGuiDefaults().getLayout(null, getProperty("layout"));
        if (layout == null) {
            layout = XuluxContext.getGuiDefaults().getDefaultLayout();
        }
        layout.setParent(this);
        panel = new JPanel((LayoutManager) layout);
        initializeChildren();
        refresh();
        processInit();
    }

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh() {
        initialize();
        isRefreshing = true;
        String backgroundColor = getProperty("default-background-color");

        if (backgroundColor != null) {
            panel.setBackground(ColorUtils.getSwingColor(backgroundColor));
        }
        String tabId = getProperty(TabPanel.TABID);
        if (tabId == null) {
            panel.setEnabled(isEnabled());
        } else {
            int tabInt = Integer.parseInt(tabId);
            if (tabInt == ((JTabbedPane) getParent().getNativeWidget()).getSelectedIndex()) {
                ((JTabbedPane) getParent().getNativeWidget()).setEnabledAt(Integer.parseInt(tabId), isEnabled());
            }
        }
        isRefreshing = false;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#canContainChildren()
     */
    public boolean canContainChildren() {
        return true;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getChildWidgets()
     */
    public ArrayList getChildWidgets() {
        return widgets;
    }

    /**
     * @see org.xulux.nyx.gui.ContainerWidget#addToParent(Widget)
     */
    public void addToParent(Widget widget) {
        if (widget instanceof IShowChildWidgets) {
            List children = widget.getChildWidgets();
            if (children != null && children.size() > 0) {
                Iterator it = children.iterator();
                while (it.hasNext()) {
                    Widget w = (Widget) it.next();
                    panel.add((JComponent) w.getNativeWidget(), w);
                    w.refresh();
                }
            }
        } else {
            panel.add((Component) widget.getNativeWidget(), widget);
            widget.refresh();
        }
    }

    /**
     * @see org.xulux.nyx.gui.Widget#focus()
     */
    public void focus() {
      isRefreshing = true;
      panel.requestFocus();
      isRefreshing = false;
      // if widget is not showing we have
      // to make it showing..
      // if we have a tab, we need to call the parent too..
      if (!panel.isShowing() && getParent() != null || 
          getProperty(TabPanel.TABID) != null) {
          // set the session variable, so controls
          // can look who requested focus..
          getPart().getSession().setValue("nyx.focusrequest", this);
          getParent().focus();
          // remove session variable again.
          getPart().getSession().remove("nyx.focusrequest");
          panel.requestFocus();
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

}
