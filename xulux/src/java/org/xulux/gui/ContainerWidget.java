/*
 $Id: ContainerWidget.java,v 1.1 2003-12-18 00:17:21 mvdb Exp $

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
package org.xulux.gui;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Specifies a container widget.
 * It makes overriding a bit easier.
 *
 * @author Martin van den Bemt
 * @version $Id: ContainerWidget.java,v 1.1 2003-12-18 00:17:21 mvdb Exp $
 */
public abstract class ContainerWidget extends Widget {
    /**
     * the widgets
     */
    protected ArrayList widgets;

    /**
     * Constructor for ContainerWidget.
     * @param name the name of the widget
     */
    public ContainerWidget(String name) {
        super(name);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#addChildWidget(Widget)
     */
    public void addChildWidget(Widget widget) {
        if (widgets == null) {
            widgets = new ArrayList();
        }
        if (widget != null) {
            widgets.add(widget);
            widget.setRootWidget(false);
            // add to the parent if the parent is already
            // initialized..
            if (initialized) {
                widget.setParent(this);
                addToParent(widget);
            }
        }
    }

    /**
     * @see org.xulux.nyx.gui.Widget#canBeRootWidget()
     */
    public boolean canBeRootWidget() {
        return super.canBeRootWidget();
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
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public void initializeChildren() {
        ArrayList widgets = getChildWidgets();
        if (widgets == null) {
            return;
        }
        Iterator iterator = widgets.iterator();
        while (iterator.hasNext()) {
            Widget widget = (Widget) iterator.next();
            addToParent(widget);
        }
    }

    /**
     * Adds a childwidget to the parent
     * @param widget - the child widget
     */
    public abstract void addToParent(Widget widget);

    /**
     * You probably have to override this,
     * since it only destroys the children
     * and doesn't cleanup the parent
     * object, since it doesn't know about it.
     *
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy() {
        ArrayList children = getChildWidgets();
        if (children != null) {
            Iterator it = children.iterator();
            while (it.hasNext()) {
                Widget cw = (Widget) it.next();
                cw.destroy();
            }
            children.clear();
            children = null;
        }
    }

}