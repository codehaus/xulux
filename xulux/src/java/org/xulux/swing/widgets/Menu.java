/*
   $Id: Menu.java,v 1.5 2004-12-01 11:37:04 mvdb Exp $
   
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

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.xulux.gui.ContainerWidget;
import org.xulux.gui.Widget;

/**
 * The menu widget
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Menu.java,v 1.5 2004-12-01 11:37:04 mvdb Exp $
 */
public class Menu extends ContainerWidget {

    private JMenu menu;
    
    /**
     * @param name
     */
    public Menu(String name) {
        super(name);
    }

    /**
     * @see org.xulux.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget() {
        if (!initialized) {
            initialize();
        }
        return menu;
    }

    /**
     * @see org.xulux.gui.Widget#initialize()
     */
    public void initialize() {
        if (initialized) {
            return;
        }
        menu = new JMenu();
        initialized = true;
        initializeChildren();
        refresh();
    }

    /**
     * @see org.xulux.gui.Widget#refresh()
     */
    public void refresh() {
        if (isRefreshing()) {
            return;
        }
        if (!initialized) {
            initialize();
        }
        isRefreshing = true;
        String text = getProperty("text");
        if (text != null) {
            menu.setText(text);
        }
        isRefreshing = false;
    }

    /**
     * @see org.xulux.gui.Widget#getGuiValue()
     */
    public Object getGuiValue() {
        return null;
    }

    /**
     * @see org.xulux.gui.Widget#focus()
     */
    public void focus() {

    }

    /**
     * @see org.xulux.gui.Widget#isValueEmpty()
     */
    public boolean isValueEmpty() {
        return true;
    }

    /**
     * @see org.xulux.gui.Widget#canContainValue()
     */
    public boolean canContainValue() {
        return false;
    }

    /**
     * @see org.xulux.gui.ContainerWidget#addToParent(org.xulux.gui.Widget)
     */
    public void addToParent(Widget widget) {
        if (widget.getNativeWidget() instanceof JMenuItem) {
            menu.add((JMenuItem)widget.getNativeWidget());
        } else {
            System.err.println("NOT A MENU ITEM");
        }
        // we should warn the user in case a widget is not a component..
    }

    /**
     * @see org.xulux.gui.Widget#ignoreLayout()
     */
    public boolean ignoreLayout() {
        return true;
    }

}
