/*
 $Id: Menu.java,v 1.1 2003-12-29 14:26:42 mvdb Exp $

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

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.xulux.gui.ContainerWidget;
import org.xulux.gui.Widget;

/**
 * The menu widget
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Menu.java,v 1.1 2003-12-29 14:26:42 mvdb Exp $
 */
public class Menu extends ContainerWidget {

    private JMenu menu;
    private JMenuBar bar;
    
    /**
     * @param name
     */
    public Menu(String name) {
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
        menu = new JMenu();
        bar.add(menu);
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
        System.out.println("Widget : " + widget);
        if (widget.getNativeWidget() instanceof JMenuItem) {
            System.out.println("menu item....");
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