/*
   $Id: PopupMenu.java,v 1.4 2004-11-18 23:15:55 mvdb Exp $
   
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

import javax.swing.JPopupMenu;

import org.xulux.gui.ContainerWidget;
import org.xulux.gui.NyxListener;
import org.xulux.gui.Widget;

/**
 * A popopmenu
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: PopupMenu.java,v 1.4 2004-11-18 23:15:55 mvdb Exp $
 */
public class PopupMenu extends ContainerWidget {

    /**
     * the native popupmenu
     */
    protected JPopupMenu menu;

    /**
     * @param name the popupmenu name
     */
    public PopupMenu(String name) {
        super(name);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy() {
        processDestroy();

    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget() {
        if (!initialized) {
            initialize();
        }
        return this.menu;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public void initialize() {
        if (initialized) {
            return;
        }
        this.menu = new JPopupMenu();
        setVisible(false);
        initialized = true;
        initializeChildren();
        refresh();
        processInit();
    }

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh() {
        initialize();
        if (isVisible()) {
            // check if the parent is showing, else an exception will be
            // thrown
            if (((Component) getParent().getNativeWidget()).isShowing()) {
                menu.show((Component) getParent().getNativeWidget(), getRectangle().getX(), getRectangle().getY());
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
     * @see org.xulux.nyx.gui.Widget#focus()
     */
    public void focus() {
        menu.requestFocus();
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
     * @see org.xulux.nyx.gui.ContainerWidget#addToParent(org.xulux.nyx.gui.Widget)
     */
    public void addToParent(Widget widget) {
        if (widget instanceof MenuItem) {
            menu.add((Component) widget.getNativeWidget());
        } else {
            if (log.isWarnEnabled()) {
                log.warn("Widgets of type " + widget.getWidgetType() + " is not supported in a popumen");
            }
        }
    }
    /**
     * @see org.xulux.nyx.gui.Widget#setVisible(boolean)
     */
    public void setVisible(boolean visible) {
        this.visible = true;
        if (initialized) {
            refresh();
        }
    }

    /**
     * @see org.xulux.nyx.gui.Widget#addNyxListener(org.xulux.nyx.gui.NyxListener)
     */
    public void addNyxListener(NyxListener listener) {
    }

}
