/*
 $Id: PopupMenu.java,v 1.2 2003-12-23 02:00:06 mvdb Exp $

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

import java.awt.Component;

import javax.swing.JPopupMenu;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.gui.ContainerWidget;
import org.xulux.gui.NyxListener;
import org.xulux.gui.Widget;

/**
 * A popopmenu
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: PopupMenu.java,v 1.2 2003-12-23 02:00:06 mvdb Exp $
 */
public class PopupMenu extends ContainerWidget {

    /**
     * the native popupmenu
     */
    protected JPopupMenu menu;
    /**
     * the logging instance
     */
    protected static Log log = LogFactory.getLog(PopupMenu.class);

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
