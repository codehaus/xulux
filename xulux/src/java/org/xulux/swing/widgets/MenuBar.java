/*
   $Id: MenuBar.java,v 1.2 2004-01-28 15:09:23 mvdb Exp $
   
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
import javax.swing.JMenuBar;

import org.xulux.gui.ContainerWidget;
import org.xulux.gui.Widget;

/**
 * The menubar widget
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: MenuBar.java,v 1.2 2004-01-28 15:09:23 mvdb Exp $
 */
public class MenuBar extends ContainerWidget {

    private JMenuBar bar;
    
    /**
     * @param name
     */
    public MenuBar(String name) {
        super(name);
    }

    /**
     * @see org.xulux.gui.Widget#destroy()
     */
    public void destroy() {

    }

    /**
     * @see org.xulux.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget() {
        if (!initialized) {
            initialize();
        }
        return bar;
    }

    /**
     * @see org.xulux.gui.Widget#initialize()
     */
    public void initialize() {
        if (initialized) {
            return;
        }
        bar = new JMenuBar();
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
        // nothing to do yet..
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
        System.out.println("Widget : " + widget);
        if (widget.getNativeWidget() instanceof JMenu) {
            System.out.println("menu....");
            bar.add((JMenu)widget.getNativeWidget());
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
