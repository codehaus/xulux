/*
 $Id: MenuItem.java,v 1.3 2003-12-29 14:26:42 mvdb Exp $

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
 * @version $Id: MenuItem.java,v 1.3 2003-12-29 14:26:42 mvdb Exp $
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
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy() {
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
