/*
   $Id: MenuItem.java,v 1.5 2004-12-01 11:37:04 mvdb Exp $
   
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
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.xulux.gui.NyxListener;
import org.xulux.gui.Widget;
import org.xulux.swing.listeners.PrePostFieldListener;

/**
 * Creates a menuitem or a seperator, based on the type of
 * menuitem
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: MenuItem.java,v 1.5 2004-12-01 11:37:04 mvdb Exp $
 */
public class MenuItem extends Widget {

    /**
     * The native menuitem
     */
    protected JMenuItem item;
    /**
     * the native seperator
     */
    protected JSeparator separator;
    /**
     * the actionlistener
     */
    protected ActionListener actionListener;
    /**
     * the nyx listeners
     */
    protected List listenerList;

    /**
     * @param name the name of the menuitem
     */
    public MenuItem(String name) {
        super(name);
    }

    /**
     * @see org.xulux.gui.Widget#destroy()
     */
    public void destroy() {
        removeAllRules();
        if (item != null && this.actionListener != null) {
            this.item.removeActionListener(this.actionListener);
        }
        this.actionListener = null;
        if (listenerList != null) {
            for (Iterator it = listenerList.iterator(); it.hasNext();) {
                this.item.removeActionListener((ActionListener) it.next());
            }
        }
        if (item != null && item.getParent() != null) {
            Container container = item.getParent();
            container.remove(item);
        }
        item = null;
        processDestroy();
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget() {
        if (!initialized) {
            initialize();
        }
        if (item != null) {
            return item;
        } else {
            return separator;
        }
    }

    /**
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public void initialize() {
        if (initialized) {
            return;
        }
        String type = getProperty("type");
        if (type == null || (type != null && !type.equals("separator"))) {
            item = new JMenuItem();
            // allways add an actionlistener..
            this.actionListener = new PrePostFieldListener(this);
            item.addActionListener(this.actionListener);
        } else {
            // not other things are needed..
            separator = new JSeparator();
        }
        initialized = true;
        refresh();
        processInit();
    }

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh() {
        if (isRefreshing()) {
            return;
        }
        isRefreshing = true;
        if (!initialized) {
            initialize();
        }
        if (item != null) {
            refreshItem();
        } else if (separator != null) {
            refreshSeperator();
        }
        isRefreshing = false;
    }

    /**
     * Refreshes the menuitem
     */
    private void refreshItem() {
        String text = getProperty("text");
        if (text != null) {
            item.setText(text);
        }
        item.setEnabled(isEnabled());
        item.setVisible(isVisible());
    }

    /**
     * Refreshes the separator (no code yet..)
     *
     */
    private void refreshSeperator() {
        // do nothing..
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getGuiValue()
     */
    public Object getGuiValue() {
        return null;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#focus()
     */
    public void focus() {
        item.requestFocus();
    }

    /**
     * @see org.xulux.nyx.gui.Widget#isValueEmpty()
     */
    public boolean isValueEmpty() {
        return false;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#canContainValue()
     */
    public boolean canContainValue() {
        return false;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#addNyxListener(org.xulux.nyx.gui.NyxListener)
     */
    public void addNyxListener(NyxListener listener) {
        if (listener instanceof ActionListener) {
            if (listenerList == null) {
                listenerList = new ArrayList();
            }
            listenerList.add(listener);
            initialize();
            this.item.addActionListener((ActionListener) listener);
        }
    }

}
